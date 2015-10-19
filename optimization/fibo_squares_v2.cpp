// New version of file. Uses optimization methods I described
// in email.

#include <iostream>
#include <fstream>
#include <cstring>
#include "nt_algorithms.h"
#include <ctime>
using namespace std;

// I do lots of competitive programming so please excuse me if the "tricks"
// I perform here including in the next two lines seem "hacky"...
#define MAX 9072002 // REPLACE BY (file name + 2)

typedef long long int64;

int primes[205];

// possible[i] = 0 if i still possible to be square fibonacci #
bool possible[MAX + 5];
bool possible2[MAX + 5]; // temp copy

int main()
{
    ifstream fin("fib_periods_9072000.out");
    ofstream fout("fib_squares.out");
    
    clock_t c1 = clock();
    
    memset(possible, 0, sizeof(possible));
    
    int i = 0;
    while(fin >> primes[i]) i++;
     
    // checking everything until only ones left are 1, 2, 12
    int ind = 0;
    int j = 0;
    
    int lcm = 1;
    while(ind < i) {
        int cur = primes[ind++];
        
        string s = qr_mod_p(cur);
        int f1 = 0;
        int f2 = 1;
        
        string new_s = "";
        
        do {
            int f3 = (f1 + f2) % cur;
            new_s += s[f3];
            
            f1 = f2;
            f2 = f3;
        } while(!(f1 == 0 && f2 == 1));
        
        int sz = new_s.size();
        string last = new_s.substr(0, sz - 1);
        new_s = "";
        new_s += new_s[sz - 1];
        new_s += last;
        
        int k = lcm;
        
        int d = gcd(k, sz);
        lcm = int((long long)(k) * (long long)(sz) / (long long)(d));
        
        for(int i = 0; i < lcm; ++i) {
            int x = possible2[i % k];
            int y = 1 - (new_s[i % sz] - '0');
            
            int z = (x || y) ? 1 : 0;
            possible[i] = z;
            //possible[i] = (x || y) ? 1 : 0;
            //cout << possible[i];
        }
        
        for(int i = 0; i < lcm; ++i) possible2[i] = possible[i];
    }
    
    for(int i = 1; i < MAX; ++i) {
        if(possible[i] == 0)
            fout << "F_" << (i + 1) << " may be a perfect square" << endl;
    }
    
    fout << "Nothing else works.\n";
    
    clock_t c2 = clock();
    
    fout << "Execution time: " << double(c2 - c1) / CLOCKS_PER_SEC << endl;
}
