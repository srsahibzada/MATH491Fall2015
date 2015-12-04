#include <fstream>
#include <cmath>
#include <algorithm>
#include "nt_algorithms.h"
#include <sstream>
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
//const int64 n = 4171;//570939058931LL;
//const int64 e = 3299;//62743509091LL;
//const int64 n = 509758268111LL;
//const int64 e = 441062571571LL;

ifstream fin("output.txt");
ofstream fout("output.csv");

int64 n, e;

// Test for correctness of k, d.
// Described in original Wiener paper.
int wiener_test(int n, int e, int k, int d, int d_actual)
{
    // candidate for phi(n)
    long long phi_n = (e*d - 1)/k;
    long long g = e*d % k;
    if(g == 0) g = 1;

    // a = (p + q)
    long long a = n - phi_n + 1;
    if(a % 2 == 1) return 1;

    // a/2 = (p + q)/2
    // b = (p - q)/2
    long long b = a*a/4 - n;
    long long sq_b = (long long) floor(sqrt(double(b)));

    if(sq_b * sq_b != b) return 1;

    // we've found p and q!!
    long long p = abs(a/2 + sq_b), q = abs(a/2 - sq_b);
    if(p * q != n) return 1;

    if(d/g != d_actual) return 1;

    fout << p << "," << q << ',';
    fout << k << "," << d/g << ',';
    return 0;
}

int go(int64 n, int64 e, int64 d_actual)
{
    // u = sqrt(n)/4
    double u = sqrt(n);
    u /= 4;

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
    i = max(i, 0LL);

    // now find i-th and (i+1)-th convergent
    pair<int, int> c1 = convergent(e, n, i);
    pair<int, int> c2 = convergent(e, n, i + 1);
    // a_(i + 1)
    int aiplus1 = partial_quotient(e, n, i + 1);

    // now exhaustively search on u, v, del(ta)
    // to get estimates for k/d
    // note that del can't be too big, and u >= v always
    for(int del = 0; del < 50; ++del) {
        for(int u1 = 0; u1 <= 200; ++u1) {
            for(int v1 = 0; v1 <= u1; ++v1) {
                int k = c2.first * u1 + c1.first * (u1 * del + v1);
                int d = c2.second * u1 + c1.second * (u1 * del + v1);
                if(k == 0 || d == 0) continue;

                int res = wiener_test(n, e, k, d, d_actual);
                if(res == 0) {
                    fout << del << "," << u1 << "," << v1 << ',' << 1 << '\n';
                    return 1;
                    //system("pause");
                }
            }
        }
    }

    fout << "0\n";
    return 0;
}

main()
{
    int64 n, phin;
    n = 570939058931LL, phin = 570937542000LL;

    //fout << inverse(3, 19) << endl;
    //while(fin >> n >> phin) {
        //stringstream ss;
        //ss << "output_" << n << ".csv";
        //cout << ss.str().c_str() << endl;
        //fout.close();
        //fout.clear();
        //fout.open(ss.str().c_str());

        int uctr = 0;
        for(int d = 3; d < phin; d += max(2LL, d / 100 - (d / 100) % 2)) {
            while(gcd(d, phin) != 1) d += 2;

            int e = inverse(d, phin);
            fout << n << ',' << e << ',' << d << ',';
            int succ = go(n, e, d);

            // if unsuccessful 20 times in a row, quit
            if(!succ) ++uctr;
            else uctr = 0;
            if(uctr == 20) break;
        }
    //}
}
