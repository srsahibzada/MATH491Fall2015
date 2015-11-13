#include "simple_rsa.h"


//RSA SETUP !!! 
RSA::RSA() {
	//https://gmplib.org/list-archives/gmp-bugs/2008-March/000972.html
	mpz_t randnum1;
	mpz_t randnum2;
	mpz_t one;
	//initialize the 1
	mpz_init(one);
	mpz_set_ui(global_one);


	mpz_t rand_seed;
	unsigned long int i, seed;
	gmp_randstate_t r_state;
	seed = 123456;
	gmp_randinit_default(r_state);
	gmp_randseed_ui(r_state,seed);
	mpz_init(randnum1);
	mpz_init(randnum2);

	mpz_urandomb(randnum1,r_state,100); //2^100 -1 range
	mpz_urandomb(randnum2,r_state,100); //2^100 -1 range
	mpz_t p, q, n;
	mpz_t p_sub_1, q_sub_1;
	mpz_init(p);
	mpz_init(q);
	mpz_init(n);
	mpz_nextprime(p,randnum1);
	mpz_nextprime(q,randnum2);
	mpz_mul(n,p,q); //compute n = pq
	//phi(n) = (p-1)(q-1)
	mpz_init(p_sub_1);
	mpz_init(q_sub_1);
	mpz_sub(p_sub_1,p,one);
	mpz_sub(q_sub_1,q,one);
	//randomly choose e in range(1,phi(n))
	//modular inversion magic




}