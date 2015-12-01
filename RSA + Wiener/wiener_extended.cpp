#include <iostream>
#include <cmath>
#include "nt_algorithms.h"
using namespace std;

// change these when you want to test...
// these integers are too big for long long,
// can use these to test when converted to Java
//const int64 n = 1227144449615933LL;
//const int64 e = 1158077559523991LL;
//const int64 e = 248607392952065LL;

// use these for now, d = 5
//const int64 n = 8927LL;
//const int64 e = 2621LL;

// these work beautifully. d = 2011
const int64 n = 570939058931LL;
const int64 e = 62743509091LL;

// Test for correctness of k, d.
// Described in original Wiener paper.
int wiener_test(int k, int d)
{
    // candidate for phi(n)
    long long phi_n = (e*d)/k;
    long long g = e*d % k;

    // a = (p + q)
    long long a = n - phi_n + 1;
    if(a % 2 == 1) return 1;

    // a/2 = (p + q)/2
    // b = (p - q)/2
    long long b = a*a/4 - n;
    long long sq_b = (long long) floor(sqrt(double(b)));

    if(sq_b * sq_b != b) return 1;

    // we've found p and q!!
    long long p = (a/2 + sq_b), q = (a/2 - sq_b);
    cout << "p == " << p << ", q == " << q << endl;
    cout << "k == " << k << ", d == " << d/g << endl;
    return 0;
}

main()
{
    // u = sqrt(n)/4
    double u = sqrt(n);
    //u /= 4;

    int i = 1;
    // find largest odd i s.t. 1/u <= |x - x_i|
    // where x = e/n and x_i is i-th convergent of x
    while(true) {
        pair<int, int> c = convergent(e, n, i);
        double k = abs(double(e) / double(n) - double(c.first) / double(c.second));
        if(k < 1 / u) break;
        i += 2;
    }
    i -= 2;

    // now find i-th and (i+1)-th convergent
    pair<int, int> c1 = convergent(e, n, i);
    pair<int, int> c2 = convergent(e, n, i + 1);
    // a_(i + 1)
    int aiplus1 = partial_quotient(e, n, i + 1);

    // now exhaustively search on u, v, del(ta)
    // to get estimates for k/d
    // note that del can't be too big, and u >= v always
    for(int del = 0; del < 100; ++del) {
        for(int u1 = 1; u1 < 5000; ++u1) {
            for(int v1 = 1; v1 <= u1; ++v1) {
                int k = c2.first * u1 + c1.first * (u1 * del + v1);
                int d = c2.second * u1 + c1.second * (u1 * del + v1);

                int res = wiener_test(k, d);
                if(res == 0) {
                    cout << "Success!\n";
                    cout << "u == " << u1 << ", v == " << v1 << endl;
                    return 0;
                }
            }
        }
    }

    cout << "I've tried more than 2 billion iterations. I give up.\n";
}
