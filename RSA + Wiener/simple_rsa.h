/*
	c++ translation of java-based simple rsa (sarah sahibzada csce 315 spring 2015)

*/

#include <iostream>
#include <cstdlib>
#include <gmp.h>
#include "globals.h"
//#include "rsa_functions.h" //not right now

//for organization purposes, so we're not stuck with a bunch of mpzt's	

typedef struct private_key {
	mpz_t p_val,
		q_val,
		d_val;

} private_key;

typedef struct public_key {
	mpz_t n_val,
		e_val;
} public_key;

class RSA {

	private:
		private_key priv;
		public_key pub;
		mpz_t prime_seed_1;
		mpz_t prime_seed_2;
		int len_modulus;
		int block_size;
		int buffer_size;
	public:
		RSA();
		~RSA(); //destructor
		void encode(string file, mpz_t a, mpz_t b);
		void decode(string file, mpz_t a, mpz_t b);
		public_key get_pub_key() {
			return this->pub;
		}

		


};