#include <iostream>
#include <cmath>
#include "nt_algorithms.h"
using namespace std;

// slight "hack" to make pairs easier to define and use
typedef pair<int, int> pii;
#define A first
#define B second

main()
{
    // read in n & e
    long long n, e;
    cin >> n >> e;
    
    // main Wiener's attack
    int i = 0;
    while(true) {
        ++i;
        
        // compute i-th convergent of e/n, this gives us potential k/d
        pii conv = convergent(e, n, i);
        long long k = conv.A, d = conv.B;
        if(d % 2 == 0 || d == 1) continue;
        if((e*d - 1) % k != 0) continue;
        
        // candidate for phi(n)
        long long phi_n = (e*d - 1)/k;
        
        // quadratic formula
        long long a = 1, b = n - phi_n + 1, c = n;
        // checking if rational roots
        long long dis = b*b-4*a*c;
        double ddis = (double) dis;
        long long sq_dis = (long long) floor(sqrt(ddis));
        
        if(sq_dis * sq_dis != dis) continue;
        else if((b + sq_dis) % 2 != 0) continue;
        
        // we've found p and q!!
        long long p = (b + sq_dis) / 2, q = (b - sq_dis) / 2;
        cout << p << ' ' << q << endl;
        system("pause");
        return 0;
    }
}
