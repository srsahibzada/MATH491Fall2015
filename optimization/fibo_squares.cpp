// This is an old version of a new file. Still useful for checking
// whether Fibonacci numbers are actually squares when in doubt,
// though... (I did so when testing fibo2_squares.cpp file)
// ~ Daniel

#include <iostream>
#include "nt_algorithms.h"
using namespace std;

#define MAX 1000000

// primes[i] = 1 if i not prime, 0 otherwise
bool primes[5005];
// possible[i] = 0 if i still possible to be square fibonacci #
bool possible[MAX + 5];

int main()
{
    primes[0] = 1;
    primes[1] = 1;
    
    // sieve of Eratosthenes
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
        ++cur;
        
        // Only using primes 3 mod 4 to check QR/NQR.
        while(cur % 4 != 3 || primes[cur] == 1) ++cur;
        
        string s = qr_mod_p(cur);
        int f1 = 1;
        int f2 = 1;
        
        // REWRITE THIS
        for(int i = 3; i < MAX; ++i) {
            int f3 = (f1 + f2) % cur;
            if(s[f3] == '0' && possible[i] != 1) {
                if(i == 302400) cout << cur << " ruled it out\n";
                possible[i] = 1;
                ++total;
            }
            
            f1 = f2;
            f2 = f3;
        }
        
        if(total > MAX - 5) break;
    }
    
    for(int i = 1; i < MAX; ++i) {
        if(possible[i] == 0)
            cout << "F_" << i << " may be a perfect square" << endl;
    }
    
    cout << "Nothing else up to 999999 works.\n";
    
    system("pause");
}
