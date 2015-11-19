
import java.io.*;
import java.math.BigInteger;

import javax.xml.bind.DatatypeConverter;

/*
ALL NAMES ALPHA BY SURNAME
CSCE 315 Project 4
Matthew Ball, Katherine Davis, Chance Eckert, Byron Haver, Sarah Sahibzada & Matthew Wiecek
Texas A&M University Computer Science
A simple RSA implementation to be verified against an openSSL file

MATH 491 Final Project
Sarah Sahibzada, Daniel Whatley & Taylor Wilson
A simple RSA implementation to be extended for Wiener's attack
*/


class RSA {
	private PrimeGenerator primes = new PrimeGenerator(); //used to calculate info
	private BigInteger pVal;
	private BigInteger qVal;
	private BigInteger dVal;
	public BigInteger modulus;
	public BigInteger exponent = new BigInteger("65537");
	public BigInteger publicKey;
	public BigInteger privateKey;
	public BigInteger phin;
	public BigInteger n;
	public int numBits;
    public BigInteger nKey;
	public BigInteger eKey;
	


	RSA() {

		this.pVal = null;
		this.qVal = null;
	}
	RSA(BigInteger p, BigInteger q) {
		this.pVal = p;
		this.qVal = q;
	}
	

	//before anything else, get the setters out of the way

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
	public BigInteger generateRandomP(int bits) {
		BigInteger newP = primes.generatePrimeOfSize(bits/2);
		this.pVal = newP;
		return newP;
	}
	public BigInteger generateRandomQ(int bits) {
		BigInteger newQ = primes.generatePrimeOfSize(bits/2);
		this.qVal = newQ;
		return newQ;      
	}
	public BigInteger calcDVal(BigInteger exp, BigInteger mod) {
		return PrimeGenerator.modInverse(exp, mod);//handwritten routine
		//return exp.modInverse(mod);
	}
       public BigInteger computePrivateKey(BigInteger publicKey, BigInteger mod) {
		return primes.inverseModuloQ(publicKey, mod);
	}
        
        public String simpleEncrypt(String data) throws UnsupportedEncodingException {
           //byte[] bytes = data.getBytes();
		byte[] bytes = data.getBytes();
               // System.out.println("-------");
		nKey = calcNVal();
               // System.out.println("-------");
		BigInteger phin = calcPhiN();
                //ystem.out.println("-------");
		eKey = primes.randomCoprimeTo(phin);
                ///System.out.println("-------");
		dVal = computePrivateKey(eKey, phin);
                //System.out.println("-------");
		BigInteger m = new BigInteger(bytes); 
               // System.out.println("-------");
		String c = primes.toByteString(PrimeGenerator.modExp(m, eKey, nKey));
               // System.out.println("-------");
		return c;
        }

	public String encrypt(String data) throws FileNotFoundException, UnsupportedEncodingException 
	{

		byte[] bytes = data.getBytes(); //String->byte[]
		byte[] trimmed = PrimeGenerator.trim(bytes);
		this.modulus = this.pVal.multiply(this.qVal);
		this.n = this.calcNVal();
		this.phin = this.calcPhiN();
		
		this.dVal = this.calcDVal(exponent, this.phin);
		
		System.out.println("Base64 of original message: " + DatatypeConverter.printBase64Binary(bytes));
		
		BigInteger m = new BigInteger(trimmed); //byte[]->BigInteger
		BigInteger cipher = PrimeGenerator.modExp(m, this.exponent, this.n);
		byte[] ciph = cipher.toByteArray();//BigInteger->byte[]
		String c = DatatypeConverter.printBase64Binary(ciph);//byte[]->StringBase64
		PrintWriter writer = new PrintWriter("ciphertext", "UTF-8");
		writer.println(ciph);
		writer.close();
		return c;
	}
	
        public String simpleDecrypt(String cyphertext) throws UnsupportedEncodingException  {
        System.out.println(cyphertext);
        BigInteger c = new BigInteger(cyphertext, 2); //convert byte string to BigInt	
        //System.out.println("-------=");
        BigInteger result= new BigInteger("0");
        result = PrimeGenerator.modExp(c, this.dVal, this.nKey);
        //System.out.println("-------=");
        byte[] bytes = result.toByteArray();
       // System.out.println("-------=");
        String m = new String(bytes);
        //System.out.println("-------=");
        return m;
        }
	public String decrypt(String cyphertext) throws UnsupportedEncodingException  
	{

		//System.out.println("------");
		byte[] bytesBase64 = DatatypeConverter.parseBase64Binary(cyphertext);
		
		System.out.println(cyphertext);
		//StringBase64->byte[]
		BigInteger c = new BigInteger(bytesBase64);	//byte[]->BigInteger
		BigInteger result = PrimeGenerator.modExp(c, this.dVal, this.n);
		byte[] bytes = result.toByteArray(); //BigInteger->byte[]
		String msgHex = primes.bytesToHex(bytes);
		String m = primes.fromHex(msgHex); //byte[] -> String
               // System.out.println("------");
		return m;
	}
	
	public String publicKey() throws FileNotFoundException, UnsupportedEncodingException{
		byte[] nu = this.n.toByteArray();
		//System.out.println("------");
		byte[] exp = this.exponent.toByteArray();
//		String nHex = primes.bytesToHex(nu);
//		String binaryE = primes.toByteString(this.exponent);
		byte[] pubkey = new byte[nu.length + exp.length];
		System.arraycopy(nu, 0, pubkey, 0, nu.length);
		System.arraycopy(exp, 0, pubkey, nu.length, exp.length);
	
		String pub = DatatypeConverter.printBase64Binary(pubkey);
		
		System.out.println("Publickey should be: " + pub ); // just to check and display value
		PrintWriter writer = new PrintWriter("pubkey.pem", "UTF-8");
		writer.println(pubkey);
		writer.close();
               // System.out.println("------");
		return pub;
	}
	
	public String privateKey() throws FileNotFoundException, UnsupportedEncodingException
	{
		byte[] modu = this.modulus.toByteArray();
		
		byte[] dBytes = this.dVal.toByteArray();
		
		byte[] privkey = new byte[modu.length + dBytes.length];
		System.arraycopy(modu, 0, privkey, 0, modu.length);
		System.arraycopy(dBytes, 0, privkey, modu.length, dBytes.length);
		 
		String priv = DatatypeConverter.printBase64Binary(privkey);
		System.out.println("Privatekey should be: " + priv ); // just to check and display value
		PrintWriter writer = new PrintWriter("privkey.pem", "UTF-8");
		writer.println(privkey);
		writer.close();
		return priv;
	}
	
        	public BigInteger generateSimpleRandomP() {
		BigInteger newP = primes.generatePrime();
		this.pVal = newP;
		return newP;
	}
	public BigInteger generateSimpleRandomQ() {
		BigInteger newQ = primes.generatePrime();
		this.qVal = newQ;
		return newQ;      

        }

	// main function can be changed as needed. only needed to confirm functioning encrypt and decrypt
	public static void main(String []args) throws UnsupportedEncodingException, FileNotFoundException
	{
		
		RSA test = new RSA();
		test.numBits = 64;
		test.setPVal(test.generateRandomP(test.numBits));
		test.setQVal(test.generateRandomQ(test.numBits));
		String words = "sarah!!";
		System.out.println("");
		System.out.println(words);
		System.out.println("");
		String cyphertext = test.encrypt(words);
		System.out.println(cyphertext);
		System.out.println("");
		System.out.println("modulus: " + test.modulus);
		System.out.println("e exponent: " + test.exponent);
		System.out.println("");
		String msg = test.decrypt(cyphertext);
		System.out.println(msg);
		System.out.println("");
		System.out.println("Public Key: " + test.publicKey());
		System.out.println("");
		System.out.println("Private Key: " + test.privateKey());
		System.out.println("");
		System.out.println("p: " + test.pVal);
		System.out.println("q: " + test.qVal);
		System.out.println("d: " + test.dVal);
		System.out.println("phi(n): " + test.phin);
                System.out.println("OpenSSL confirmation:");
        BigInteger p = new BigInteger("3961221227");
        BigInteger q = new BigInteger("3441305459");
        RSA ssl = new RSA(p,q);
        ssl.numBits = 64;
        String msgssl = "howdy!!\r";
        System.out.println("message is " + msgssl);
        System.out.println("ciphertext given by OpenSSl is: F5BOQBbNO6g=");
        String cipher = ssl.encrypt(msgssl);
        System.out.println("our RSA implementation returns the ciphertext: " + cipher);
		System.out.println("");
		byte[] ciph = DatatypeConverter.parseBase64Binary(cipher);
		BigInteger cip = new BigInteger(ciph);
		System.out.println("our cipher in BigInteger form: " + cip);
		System.out.println("modulus: " + ssl.modulus);
		System.out.println("e exponent: " + ssl.exponent);
		System.out.println("");
		String decmsg = ssl.decrypt(cipher);
		System.out.println(decmsg);
		System.out.println("");
		System.out.println("Public Key: " + ssl.publicKey());
		System.out.println("");
		System.out.println("Private Key: " + ssl.privateKey());
		System.out.println("");
		System.out.println("p: " + ssl.pVal);
		System.out.println("q: " + ssl.qVal);
		System.out.println("d: " + ssl.dVal);
		System.out.println("phi(n): " + ssl.phin);
        
		String sslcipher = "F5BOQBbNO6g=";
		byte[] sslciph = DatatypeConverter.parseBase64Binary(sslcipher);
		BigInteger opssl = new BigInteger(sslciph);
		System.out.println("openssl cipher in BigInteger form: " + opssl);
		
        System.out.println("Now testing encrypt/decrypt with multiple words");
    	RSA test2 = new RSA();
		test2.numBits = 512;
		test2.setPVal(test2.generateRandomP(test2.numBits));
		test2.setQVal(test2.generateRandomQ(test2.numBits));
		String words2 = "This is a message.";
        System.out.println("Original message: " + words2);

		String cyphertext2 = test2.encrypt(words2);
		System.out.println(words2);
		System.out.println("");
		String msg2 = test2.decrypt(cyphertext2);
		System.out.println("Decrypted message:" + msg2);


                        

	}


}