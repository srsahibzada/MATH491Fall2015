// nt_algorithms.h :
// Basic NT algorithms.
// @author Daniel

#include <iostream>

// gcd algo
int gcd(int a, int b)
{
    if(b == 0) return a;
    return gcd(b, a%b);
}

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

// Using quadratic reciprocity and other Jacobi symbol identities to
// determine if a number is a quadratic residue mod another.
int is_qr(int y, int mod)
{
    if(y == 0 || y == 1) return 1;
    if(y > mod) return is_qr(y % mod, mod);
    
    if(y % 2 == 1 && mod % 2 == 1) {
        if(y % 4 == 3 && mod % 4 == 3)
            return -(is_qr(mod, y));
        else return is_qr(mod, y);
    } else {
        int pow = 0;
        while(y % 2 == 0) {pow++; y /= 2;}
        
        if(pow % 2 == 0) return is_qr(y, mod);
        else {
            if(mod % 8 == 1 || mod % 8 == 7) return is_qr(y, mod);
            else return -(is_qr(y, mod));
        }
    }
}

// Precondition: p is a prime contained in text input file.
std::string qr_mod_p(int p)
{    
    std::string ans = "";
    
    for(int i = 0; i < p; ++i) {
        int res = is_qr(i, p);
        if(res == 1) ans += '1';
        else ans += '0';
    }
    
    return ans;
}
