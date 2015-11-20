/*
	Simple Wiener's attack in Java
	Written by Sarah Sahibzada, BS Comp Sci, BS Applied Math, Texas A&M 
	'16
	MATH 491 Computational Number Theory Group:
	Sarah Sahibzada, Daniel Whatley & Taylor Wilson
*/

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.*;



public class WienerAttack {
	private BigInteger publicModulus;
	private BigInteger publicExponent;
	private BigInteger phiN;
	private static PrimeGenerator primes = new PrimeGenerator();
	private ArrayList<BigDecimal> computedRoots = new ArrayList<BigDecimal>(); //initialize

	public WienerAttack(BigInteger N, BigInteger e)	{
		this.publicModulus = N;
		this.publicExponent = e;
	}
		boolean isWholeFrac(Fraction f) {	
		if (f.getNum().compareTo(BigInteger.ZERO) <= 0 || f.getNum().compareTo(BigInteger.ZERO) <= 0) {
			return false;
		}
		if (f.getNum().mod(f.getDen()).equals(BigInteger.ZERO)) {
			return true;
		}	
		else {
			return false;
		}
	}


	void setPhiN(Fraction f) {

		BigInteger numerator = publicExponent.multiply(f.getDen()).subtract(BigInteger.ONE);
		BigInteger denominator = f.getNum();
		//ConfinuedFraction cf  = new ContinuedFraction(new Fraction(numerator,denominator));
		//System.out.println(cf); //to test
		if (isWholeFrac(new Fraction(numerator,denominator))) {
			this.phiN =  numerator.divide(denominator);
		}
		//return 
	}
	Fraction fracPhiN(Fraction f) {
		BigInteger numerator = publicExponent.multiply(f.getDen()).subtract(BigInteger.ONE);
		BigInteger denominator = f.getNum();
		//System.out.println(denominator.toString());
		System.out.println(f.toString() + " as frac phi n");
		//ConfinuedFraction cf  = new ContinuedFraction(new Fraction(numerator,denominator));
		//System.out.println(cf); //to test
		if (isWholeFrac(new Fraction(numerator,denominator))) {
			this.phiN =  numerator.divide(denominator);
			return new Fraction(numerator, denominator);
		}
		else {
			System.out.println("Remainder of " + numerator.mod(denominator));
			return new Fraction(Globals.INTEGER_ZERO, Globals.INTEGER_ZERO);
		}


	}

public static BigDecimal squareRoot(BigDecimal arg, BigDecimal min, BigDecimal max) {
	
	BigDecimal midpoint = min.add(max).divide(new BigDecimal("2.0"));	
	BigDecimal testSquare = midpoint.multiply(midpoint);
	testSquare = testSquare.setScale(10,BigDecimal.ROUND_HALF_UP);
	int found = testSquare.compareTo(arg);
	if (found > 0) {
		max = midpoint;
		return squareRoot(arg,min,max);
	}
	else if (found < 0) {
		//System.out.println(midpoint.toString());
		min = midpoint;
		return squareRoot(arg,min,max);
	}
	else {
		//System.out.println(midpoint.toString());
		return midpoint;
	}


}

public static BigDecimal fourthRoot(BigDecimal arg, BigDecimal min, BigDecimal max) {
	BigDecimal midpoint = min.add(max).divide(new BigDecimal("2.0"));	
	BigDecimal testSquare = midpoint.multiply(midpoint).multiply(midpoint).multiply(midpoint);
	testSquare = testSquare.setScale(10,BigDecimal.ROUND_HALF_UP);
	int found = testSquare.compareTo(arg);
	if (found > 0) {
		max = midpoint;
		return fourthRoot(arg,min,max);
	}
	else if (found < 0) {
		//System.out.println(midpoint.toString());
		min = midpoint;
		return fourthRoot(arg,min,max);
	}
	else {
		//System.out.println(midpoint.toString());
		return midpoint;
	}


}



	public void findRoots(BigInteger possiblePhi) {
		if (computedRoots.size() > 0) {
			computedRoots.clear(); 
		}
		BigDecimal a = new BigDecimal(BigInteger.ONE);
		BigDecimal miniB = new BigDecimal(publicModulus).subtract(new BigDecimal(possiblePhi)).add(new BigDecimal("1"));
		BigDecimal b = miniB;
		BigDecimal c = new BigDecimal(publicModulus);
		BigDecimal bSquared = b.multiply(b);
		BigDecimal four = new BigDecimal("4.0");
		BigDecimal fourAC = four.multiply(a.multiply(c));
		BigDecimal twoA = a.multiply(new BigDecimal("2.0"));
		BigDecimal root1;
		BigDecimal root2;
		if (bSquared.subtract(fourAC).compareTo(Globals.DECIMAL_ZERO) < 0) {
			System.out.println(bSquared.subtract(fourAC));
			return;
		}
	//	if (bSquared.subtract)
		System.out.println(a.toString() + "x^2 + " + b.toString() + "x + " + c.toString());
		root1 = (b).add(squareRoot(bSquared.subtract(fourAC), new BigDecimal("0"), bSquared.subtract(fourAC)));
		root1 = root1.divide(twoA);
		computedRoots.add(root1.setScale(5,BigDecimal.ROUND_HALF_UP));
		root2 = (b).subtract(squareRoot(bSquared.subtract(fourAC), new BigDecimal("0"), bSquared.subtract(fourAC)));
		root2 = root2.divide(twoA);
		computedRoots.add(root2.setScale(5,BigDecimal.ROUND_HALF_UP));
		for (BigDecimal r : computedRoots) {
			System.out.println(r.toString());
		}

	}

	//candidate to be moved to fraction or cfrac class
	boolean isWholeNumber(Fraction f) {
		BigInteger num = f.getNum();
		BigInteger den = f.getDen();
		if (num.compareTo(BigInteger.ZERO) <= 0 || den.compareTo(BigInteger.ZERO) <= 0) {
			return false;
		}
		//Fraction ff = new Fraction(num,den);
		if (num.mod(den).equals(BigInteger.ZERO)) {
			//System.out.println(f.toString());
			return true;
		}

		else {
			//System.out.println("no");
			return false;
		}
	
	}
	BigInteger calcPhiN(Fraction f) {
	//if (isWholeNumber(f)) {
		BigInteger prospectiveD = f.getDen();
		BigInteger num = this.publicExponent.multiply(prospectiveD).subtract(BigInteger.ONE);
		BigInteger den = f.getNum();
		if (den.equals(Globals.INTEGER_ZERO)) {
			return Globals.INTEGER_NEGATIVE_ONE;
		}
		Fraction ff = new Fraction(num,den);
		System.out.println(ff);
		if (isWholeNumber(ff)) {
				return num.divide(den);
			}

			else return Globals.INTEGER_NEGATIVE_ONE;
	


	}

	BigInteger weinerAttack() {
		ContinuedFraction toAttack = new ContinuedFraction(new Fraction(this.publicExponent,this.publicModulus));
		toAttack.generate(); //generate coef list
		int maxConvergents = toAttack.getMaxConvergentSize();
		for (int i = 1; i <= maxConvergents; i++) {
			System.out.println(" I am in iteration " + (i - 1) + " out of " + (maxConvergents-1));
			Fraction toTest = toAttack.evaluate(i); //get ith convergent
			System.out.println(toTest.toString() + " == k/d");
			if ((toTest.getDen().mod(new BigInteger("2"))).equals(BigInteger.ZERO)) {
				System.out.println("Err: even value for d");
				continue;
			}
			
			else {
			//	System.out.println("----------------");
				//boolean t = isWholeNumber(toTest);
				BigInteger possiblePhi = calcPhiN(toTest);
				if (possiblePhi.equals(Globals.INTEGER_NEGATIVE_ONE)) {
					if (maxConvergents != i) {
						continue;
					}


				}
				System.out.println("My possible phi is" + possiblePhi.toString());
				if (toTest.getNum().equals(BigInteger.ONE) && toTest.getDen().equals(BigInteger.ONE)) {
					continue;
				}
				if (!possiblePhi.equals(new BigInteger("-1"))) {
					//this.phiN = possiblePhi;
					findRoots(possiblePhi);
					BigDecimal product = new BigDecimal(BigInteger.ONE);
					for (BigDecimal potentialRoot : computedRoots) {
						product = product.multiply(potentialRoot);
					}
					System.out.println(product.toString() + " == " + publicModulus.toString());
					//System.out.println(possiblePhi.toString());
					//
					if (product.equals((new BigDecimal(publicModulus)).setScale(10,BigDecimal.ROUND_HALF_UP))) {
						phiN = possiblePhi;
						//System.out.println("!1");
						break;
					} 
					else {
						continue;
					}
					//quadratic formula test
				}
			}

			}
		
		System.out.println("phiN is here : "  + phiN);

		System.out.println("We may calculate the decryption key, which is: " + (publicExponent.modInverse(phiN)).toString());
			
		for (BigDecimal b : computedRoots) {
			System.out.println("Root : " + b.toString());
		}
		return publicExponent.modInverse(phiN);
	}
	


	

	public static void main(String[] args) {
		//WienerAttack wa = new WienerAttack(new BigInteger("160523347"), new BigInteger("60728973"));
		//WienerAttack wa = new WienerAttack(new BigInteger("47897"), new BigInteger("38831"));
		//wa.weinerAttack();
		//wa2.weinerAttack();
		//wa.phiN = new BigInteger("64000");
		//wa.findRoots();
		BigDecimal sixtyfour = new BigDecimal("273349.0");
		BigDecimal zero = new BigDecimal("0");
		int maxIters = (sixtyfour.toBigInteger()).bitCount();
		//WienerAttack wa2 = new WienerAttack(new BigInteger("619369"), new BigInteger("427705"));
		//wa2.weinerAttack();
		//System.out.println("The square root of 64 is "+squareRoot(sixtyfour,zero,sixtyfour))		;
		//System.out.println("The fourth root of 64 is" + fourthRoot(sixtyfour,zero,sixtyfour));
		BigInteger p = primes.generatePrimeOfSize(300);
		BigInteger q = primes.generatePrimeOfSize(300);
		BigInteger publicMod = p.multiply(q);
		BigInteger actualPhi = primes.phiOfPrimes(p,q);
		//BigInteger publicMod = new BigInteger("47897");
		//BigInteger actualPhi = new BigInteger("47460");
		System.out.println("My public modulus is " + publicMod.toString() + " and has " + publicMod.toString(2).length() + " bits");
		System.out.println("My actual phi of N is " + actualPhi.toString() );
		BigDecimal capOnDVal = fourthRoot(new BigDecimal(publicMod.divide(new BigInteger("3"))), new BigDecimal("0"), new BigDecimal(publicMod.divide(new BigInteger("3"))));
		BigInteger integralCap = capOnDVal.toBigInteger();
		int numBits = (publicMod.toString(2)).length()/4;
		//generate appropriate d value
		BigInteger testD = new BigInteger("0");
		Random r = new Random();
		while(true) {
			testD = new BigInteger(numBits,r);
			if (testD.equals(Globals.INTEGER_ONE) || testD.equals(Globals.INTEGER_ZERO)) {
				System.out.println("Err: testD(" + testD.toString() + " ) ==  " + Globals.INTEGER_ONE.toString());
				continue;
			}
			if (testD.compareTo(integralCap) > 0) {
				System.out.println("Err: testD(" + testD.toString() + ") > integralCap(" + integralCap.toString()+")");
				continue;
			}
			if (!testD.gcd(actualPhi).equals(Globals.INTEGER_ONE)) {
				System.out.println("Err: not coprime: " + testD.toString());
				continue;
			}
			if (testD.gcd(actualPhi).equals(Globals.INTEGER_ONE)) {
				if (testD.modInverse(actualPhi).compareTo(actualPhi) >= 0) {
					System.out.println("Err: e > phi(n)");
					continue;
				}
			}
			System.out.println(testD.toString());
			break;
		}
		System.out.println("My d value is " + testD);
		System.out.println("Largest possible d value is " + capOnDVal.toString() +  " which has " +  numBits + " bits");
		BigInteger publicExponent = testD.modInverse(actualPhi);
		System.out.println("Public exponent is now : " + publicExponent.toString());

		WienerAttack wa2 = new WienerAttack(publicMod, publicExponent );
		wa2.publicModulus = publicMod;
		wa2.publicExponent = publicExponent;
		
		BigInteger dVal = wa2.weinerAttack();
		if (dVal.equals(testD)) {
			System.out.println("Attack succeeded");
		}
		else {
			System.out.println("Attack failed");
		}
		//now, a simple algo to choose e such that we get d

	}



}