/* Created using algorithm of Wunderlich paper.
PLEASE COMPILE WITH FLAGS -lpthread -lgmpxx -lgmp


*/

//includes and namespace
#include <iostream>
#include <pthread.h> //PARALLEL
#include <fstream> //read from file of primes
#include <gmpxx.h> //gmp
//#include <Python.h> //run the fibo script
#include <time.h>
using namespace std;


//global constants 
#define MAX_PRIMES 5005
#define MAX_THREADS 5005 //one thread per computation i guess
#define MAX_FIBONACCI 5005 //cap the size of fibo array
#define MAX_LOOP 1000000

//lock the shared globals
pthread_mutex_t lock_possible;
pthread_mutex_t lock_prime;
//pthread_mutex_t lock_mod_4;
//pthread_mutex_t lock_fibonacci;
pthread_mutex_t lock_total;
//more thready stuff
int list_size;
int num_threads;
int thread_id[MAX_THREADS];
pthread_t pthreads[MAX_THREADS];
pthread_attr_t attributes;

//data structures and lockable globals for program
bool primes[MAX_PRIMES]; // primes[i] = 1 if i not prime, 0 otherwise
bool possible[1000005]; // possible[i] = 0 if i still possible to be square fibonacci #
bool mod_4_primes[MAX_PRIMES]; //dynamically allocate so i don't actually have to count myself
mpz_class fibonacci[MAX_FIBONACCI]; //save space
int f1, f2, f3; //global f1, f2, f3 for global updating
int total = 0;
//thread routine


//functions
// standard powmod algo
int powmod(int base, int exp, int mod)
{
    if(exp == 0) return 1;
    if(exp == 1) return base;
    
    // if exponent even, square result of exponent exp/2
    if(exp % 2 == 0) {
        int k = powmod(base, exp/2, mod);
        return (k*k) % mod;
    } else {
        int k = powmod(base, exp/2, mod);
        int r = (k*k) % mod;
        return (r*base) % mod;
    }
}

//set the mod four array with the primes themselves
//takes up more
void set_mod_four() {
    for (int i = 0; i < MAX_PRIMES; i++) {
    //     while(cur % 4 != 3 || primes[cur] == 1) ++cur; //mod 4 primes only? or just generate those from the get go (sarah)
   //    cout << "Current prime: " << cur << endl;
       if (primes[i] == 1 && (i % 4) == 3) {
            mod_4_primes[i] = 1;
        }
        else {
            mod_4_primes[i] = 0;
        }
    }
}

//small aux function for parsing, used in quick_parse()
bool is_number(string line) {
    bool numeric = true;
    int index = 0;
    while (index != line.size() && numeric) {
        if (!isdigit(line[index])) {
            numeric = false; 
        }
        index += 1;
    }
    return numeric;
}


//parse lists and grab prime numbers that we need from them
//O(n/p), p = num threads
void quick_parse(int which) {
    string filename = "primes" + to_string(which) + ".txt";
   // cout << filename << endl;
    ifstream read_file;
    long int prime;
    string line;
    int counter = 0; //for primes array

    read_file.open(filename, fstream::out);
    while(read_file>>line) {
        //getline(read_file,line);
        if (!is_number(line)) {
            continue;
        }
        else {
          prime = stoi(line);
          //cout << prime << endl;
        }
        if (prime < MAX_PRIMES) { //i am not taking this out until i have verified all of 
           primes[prime] = 1;
        }
    }
    read_file.close();
}

//gmp test
void quick_fibo(int max) {
    mpz_class fibo = 1;
    mpz_class prev = 0;
    mpz_class prev_2 = 1;
    for (int i = 0; i < max; i++) {
        fibonacci[i] = fibo;
        fibo = prev + prev_2;
        prev = prev_2;
        prev_2 = fibo;
        
    }

 }


string qr_mod_p(int p) //quadratic residues represented as binary string
{
    int k = (p + 1)/4;
    
    string ans = "";
    
    for(int i = 0; i < p; ++i) {
        int res = powmod(i, k, p);
        if((res * res) % p == i) ans += '1';
        else ans += '0';
    }
    
    return ans;
}

void* search_for_squares(void* where) {

    int index = *((int*)where); //thread id helps partition list
    if (mod_4_primes[index] == 0) {
        pthread_exit(0);
    }
    pthread_mutex_lock(&lock_total);
    if (total >999995) {
      //  cout << " total greater than 99995" << endl;
        pthread_mutex_unlock(&lock_total);
      //  cout << " just unlocked total" << endl;
        pthread_exit(0);
    }
     pthread_mutex_unlock(&lock_total);
    // cout << " unlocking total for real"<< endl;
   

    int curr = index; //cut out the linear search and instead directly access it
    string s = qr_mod_p(curr);
    int fib1 = 1;
    int fib2 = 1;
    for (int i = 3; i < 1000000; i++) {
        int fib3 = (f1 + f2) % curr;
        pthread_mutex_lock(&lock_possible);
      //  cout << " locking possible " << endl;
        if (s[fib3] == '0' && possible[i] != 1) {
            possible[i] = 1;
            pthread_mutex_lock(&lock_total);
     //       cout << " in total lock " << endl;
            ++total;
     //       cout << " about to unlock total " << endl;
            pthread_mutex_unlock(&lock_total);
        }       
     //   cout << " unlocking possible " << endl;
        pthread_mutex_unlock(&lock_possible);
        fib1 = fib2;
        fib2 = fib3; 
    }

    pthread_exit(NULL); //DO NOT FORGET TO TERMINATE!!!
}




//right now just gives you a number of threads
//todo: extend it so that argv contains the num threads and range of test
int main(int argc, char *argv[]) 
{

    if (argc != 2) {
        cout << "too many or too few arguments" << endl << " please use: g++ fibosquares.cpp -lpthread -lgmp -lgmpxx <num_threads>";
        cout << argc << endl;
        exit(0);
    }
    num_threads = (int)atoi(argv[argc-1]);
   // cout << " running with " << num_threads << " threads, yay" << endl;
    //Change #1: instead of inplace generating the primes (O(n^2) runtime)
    //grab from a list of primes that already exists
    primes[0] = 1;
    primes[1] = 1;
    quick_parse(1); //read in things
    set_mod_four(); //set the array of mod 4 primes
  //  quick_fibo(MAX_FIBONACCI);
  /*  for (int i = 0; i < MAX_FIBONACCI; i++) {
        cout << i << ":" << fibonacci[i] << endl;

    }*/
    //initialize mutexes
    pthread_mutex_init(&lock_possible,NULL);
    pthread_mutex_init(&lock_prime,NULL);
   // pthread_mutex_init(&lock_mod_4,NULL);
  //  pthread_mutex_init(&lock_fibonacci,NULL);

    //thready things
    pthread_attr_init(&attributes);
    pthread_attr_setdetachstate(&attributes,PTHREAD_CREATE_JOINABLE);

    for (int i = 0; i < num_threads; i++) {
        thread_id[i] = i; //keep tabs on the index 
        pthread_create(&pthreads[i], &attributes, search_for_squares, (void*)&thread_id[i]);
    }

    for (int i = 0; i < num_threads; i++) {
       // cout << " joining threads " << endl;
        pthread_join(pthreads[i], NULL); //end everything
    }
      for(int i = 1; i < 1000000; ++i) {
        if(possible[i] == 0)
            cout << "F_" << i << " may be a perfect square" << endl; //make sure these are ordered with pthreads (sarah)
    }
    
    cout << "Nothing else up to 999999 works.\n";
    
    

    pthread_mutex_destroy(&lock_possible);
    pthread_mutex_destroy(&lock_prime);
  //  pthread_mutex_destroy(&lock_mod_4);
  //  pthread_mutex_destroy(&lock_fibonacci);
    system("pause");

}