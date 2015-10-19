#include <fstream>
#include <iostream>
#include "nt_algorithms.h"
using namespace std;

// primes[i] = 1 if i not prime, 0 otherwise
bool primes[100005];

// The Fibonacci period of an integer n is the smallest integer k so that
// the Fibonacci sequence mod n is periodic every k steps. For example, the
// Fibonacci period of 7 is 16.

// A "nice" Fibonacci period is one that has exponent of 2 at most 7,
// exponent of 3 at most 3, exponent of 5 at most 2, and exponent of 7 at
// most 1. This is a somewhat "crude" interpretation, very subject to changes
// once we run on a supercomputer or w/e.

// It ensures that Fibonacci periods will have LCM at most 604,800. Right now
// I'm figuring out what's the largest number of Fibonacci squares we can rule
// out with this LCM. If it's significantly above 604,800, then great.

// There are 97 such n below 50,000: see the output file.
// We expect to rule out "half" of the Fibonacci numbers every time, so we
// expect to use at most 40 or 50 of these primes to rule out all Fibonacci
// numbers below F_{10^7} or F_{10^8}. F_{10^6} was done using 32 primes.

// UPDATE: The explanation above is somewhat out of date... the program
// is now variable to a whole bunch of max LCMs, see all those files
// fib_periods_zzzz. Those numbers at the end describe max LCM. (604,800
// is the smallest one)

// Also, Sarah, with your primality testing algo we can speed this up a ton
// (using only primes <= 100,000 is somewhat wasteful).

int main()
{
    ofstream fout("fib_periods_9081072000.out");
    
    primes[0] = 1;
    primes[1] = 1;
    
    // sieve of Eratosthenes
    for(int i = 2; i < 100000; ++i) {
        if(primes[i] == 0) {
            for(int j = 2*i; j < 100000; j += i)
                primes[j] = 1;
        }
    }
    
    int acc = 0;
    int lcm = 1;
    for(int i = 3; i < 100000; i += 2) {
        if(primes[i] == 1) continue;
        
        int f1 = 0, f2 = 1;
        
        int pd = 0;
        do {
            int f3 = (f1 + f2) % i;
            f1 = f2;
            f2 = f3;
            pd++;
        } while(!(f1 == 0 && f2 == 1));
        
        int t = pd;
        
        // METHOD 1
        
        /*int d = gcd(pd, lcm);
        long long tmp = (long long)lcm * (long long)pd / (long long)d;
        if(tmp < 1000000 && tmp % 13 && tmp % 17 && tmp % 19 && tmp % 29) {
            lcm = tmp;
            fout << i << endl;
            ++acc;
        }*/
        
        // METHOD 2
        int pow2 = 0, pow3 = 0, pow5 = 0, pow7 = 0, pow11 = 0, pow13 = 0;
        while(pd % 2 == 0) {pd /= 2; pow2++;}
        while(pd % 3 == 0) {pd /= 3; pow3++;}
        while(pd % 5 == 0) {pd /= 5; pow5++;}
        while(pd % 7 == 0) {pd /= 7; pow7++;}
        while(pd % 11 == 0) {pd /= 11; pow11++;}
        while(pd % 13 == 0) {pd /= 13; pow13++;}
        
        if(pd == 1 && pow13 <= 1 && pow11 <= 1 && pow7 <= 2 && pow5 <= 3
           && pow3 <= 4 && pow2 <= 7) {
            fout << i << /*"\t" << t 
                 << "\t2^" << pow2
                 << " * 3^" << pow3
                 << " * 5^" << pow5 
                 << " * 7^" << pow7
                 << */"\n";
            ++acc;
        }
    }
    
    //fout << acc << " acceptable integers\n";
}
