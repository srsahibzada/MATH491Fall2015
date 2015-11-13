// Created using algorithm of Wunderlich paper.

#include <iostream>
using namespace std;

// primes[i] = 1 if i not prime, 0 otherwise
bool primes[5005];
// possible[i] = 0 if i still possible to be square fibonacci #
bool possible[1000005];

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

// standard (p+1)/4 algo for qr
// Precondition: p = 3 mod 4.
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

int main()
{
    primes[0] = 1;
    primes[1] = 1;
    
    // sieve of Eratosthenes
    //to do: more efficient prime generator than sieve (sarah)
    for(int i = 2; i < 5000; ++i) {
        if(primes[i] == 0) {
            for(int j = 2*i; j < 5000; j += i)
                primes[j] = 1;
        }
    }
    
    // checking everything until only ones left are 1, 2, 12
    int cur = 1;
    int total = 0;
    while(true) {
        //todo: parallelize this (sarah)
        
        ++cur;
        while(cur % 4 != 3 || primes[cur] == 1) ++cur; //mod 4 primes only? or just generate those from the get go (sarah)
        cout << "Current prime: " << cur << endl;
        
        string s = qr_mod_p(cur); 
        int f1 = 1;
        int f2 = 1;
        for(int i = 3; i < 1000000; ++i) { //todo: parallelze this (sarah)
            int f3 = (f1 + f2) % cur;
            if(s[f3] == '0' && possible[i] != 1) {
                possible[i] = 1;
                ++total;
            }
            
            f1 = f2;
            f2 = f3;
        }
        
        if(total > 999995) break;
    }
    
    for(int i = 1; i < 1000000; ++i) {
        if(possible[i] == 0)
            cout << "F_" << i << " may be a perfect square" << endl; //make sure these are ordered with pthreads (sarah)
    }
    
    cout << "Nothing else up to 999999 works.\n";
    
    system("pause");
}
