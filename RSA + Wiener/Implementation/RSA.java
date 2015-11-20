
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.xml.bind.DatatypeConverter;

/*
ALL NAMES ALPHA BY SURNAME
CSCE 315 Project 4
Matthew Ball, Katherine Davis, Chance Eckert, Byron Haver, Sarah Sahibzada & Matthew Wiecek
Texas A&M University Computer Science
A simple RSA implementation to be verified against an openSSL file

MATH 491 Final Project
Sarah Sahibzada, Daniel Whatley & Taylor Wilson
A simple RSA implementation with extensions for Wiener's Attack
*/


class RSA {
	private PrimeGenerator primes = new PrimeGenerator(); //used to calculate info
	private BigInteger pVal = new BigInteger("-1");
	private BigInteger qVal = new BigInteger("-1");
	private BigInteger dVal;
	private BigInteger publicModulus;
	private BigInteger publicExponent;
	private BigInteger privateExponent; //d value to be computed
	private BigInteger phiN;

	private int numBits;
	private ArrayList<BigInteger> coprimes = new ArrayList<BigInteger>();

	


	RSA() {
		this.pVal = null;
		this.qVal = null;
	}

	RSA(BigInteger p, BigInteger q) {
		this.pVal = p;
		this.qVal = q;
	}
	public void setPVal(BigInteger p) {
		this.pVal = p;
	}
	public void setQVal(BigInteger q) {
		this.qVal = q;
	}
	public void setPAndQVal(BigInteger p, BigInteger q) {
		this.pVal = p;
		this.qVal = q;
	}

	public BigInteger calcNVal() {
		return this.pVal.multiply(this.qVal);
	}

	public BigInteger calcPhiN() {
		return primes.phiOfPrimes(pVal, qVal);
	}
	public BigInteger generateRandomP(int bitSize) {
		BigInteger newP = primes.generatePrimeOfSize(bitSize/2);
		//this.pVal = newP;
		return newP;
	}
	public BigInteger generateRandomQ(int bitSize) {
		BigInteger newQ = primes.generatePrimeOfSize(bitSize/2);
		//this.qVal = newQ;
		return newQ;      
	}
	public BigInteger calcDVal(BigInteger exp, BigInteger mod) {
		//return PrimeGenerator.modInverse(exp, mod);//handwritten routine
		//return exp.modInverse(mod);
		//d = e^(-1) mod phi(n)
		return exp.modInverse(mod);
	}
    
  
    public BigInteger generateSimpleRandomP(int numBits) {
		BigInteger newP = primes.generatePrimeOfSize(numBits);
		//this.pVal = newP;
		return newP;
	}
	public BigInteger generateSimpleRandomQ(int numBits) {
		BigInteger newQ = primes.generatePrimeOfSize(numBits);
		//this.qVal = newQ;
		return newQ;      

        }
    public BigInteger genRandomOdd(int numBits) {
    	BigInteger testOdd = primes.generateRandomOddOfSize(numBits);
    	while (testOdd.isProbablePrime(99)) {
    		testOdd = primes.generateRandomOddOfSize(numBits);
    	}
    	return testOdd;

    }
	public static void main(String[] args) {
		RSA r = new RSA();
		BigInteger p =  r.generateRandomP(20);
		BigInteger q = r.generateRandomQ(20);
		BigInteger testMod = r.genRandomOdd(20);
		BigInteger testPrime2 = r.generateSimpleRandomQ(3);
		BigInteger testLargePrime1 = r.generateSimpleRandomP(12);
		BigInteger testLargePrime2 = r.generateSimpleRandomQ(12);
		BigInteger testMod2 = testLargePrime2.multiply(testLargePrime1);

		System.out.println(testMod.toString());
		
		System.out.println(testPrime2.toString());
		WienerAttack wa = new WienerAttack(testMod2, testPrime2);
		wa.weinerAttack();
			}  


}