/*
	Simple Wiener's attack in Java
	Written by Sarah Sahibzada, BS Comp Sci, BS Applied Math, Texas A&M 
	'16
	MATH 491 Computational Number Theory Group:
	Sarah Sahibzada, Daniel Whatley & Taylor Wilson
<<<<<<< HEAD
=======

>>>>>>> master
*/

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.*;
import java.io.File;





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
		//System.out.println(f.toString() + " as frac phi n");
		//ConfinuedFraction cf  = new ContinuedFraction(new Fraction(numerator,denominator));
		//System.out.println(cf); //to test
		if (isWholeFrac(new Fraction(numerator,denominator))) {
			this.phiN =  numerator.divide(denominator);
			return new Fraction(numerator, denominator);
		}
		else {
			//System.out.println("Remainder of " + numerator.mod(denominator));
			return new Fraction(Globals.INTEGER_ZERO, Globals.INTEGER_ZERO);
		}


	}
<<<<<<< HEAD
=======

public static BigDecimal squareRoot(BigDecimal arg, BigDecimal min, BigDecimal max) {
	
	BigDecimal midpoint = min.add(max).divide(new BigDecimal("2.0"));	
	BigDecimal testSquare = midpoint.multiply(midpoint);
	testSquare = testSquare.setScale(5,BigDecimal.ROUND_HALF_UP);
	int found = testSquare.compareTo(arg);
	if (found > 0) {
		max = midpoint;
		return squareRoot(arg,min,max);
	}
	else if (found < 0) {
		System.out.println(midpoint.toString());
		min = midpoint;
		return squareRoot(arg,min,max);
	}
	else {
		System.out.println(midpoint.toString());
		return midpoint;
	}


}
//http://stackoverflow.com/questions/13649703/square-root-of-bigdecimal-in-java
//todo: try to write our own for more precision
public static BigDecimal sqrt(BigDecimal A) {
    BigDecimal x0 = new BigDecimal("0");
   BigDecimal x1 = new BigDecimal(Math.sqrt(A.floatValue()));
    while (!x0.equals(x1)) {
        x0 = x1;
        x1 = A.divide(x0,  BigDecimal.ROUND_HALF_UP);
        x1 = x1.add(x0);
        x1 = x1.divide(new BigDecimal("2"),  BigDecimal.ROUND_HALF_UP);
>>>>>>> master

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
<<<<<<< HEAD
		if (bSquared.subtract(fourAC).compareTo(Globals.DECIMAL_ZERO) < 0) {
			//System.out.println(bSquared.subtract(fourAC));
			return;
		}
	//	if (bSquared.subtract)
		//System.out.println(a.toString() + "x^2 + " + b.toString() + "x + " + c.toString());
=======
		System.out.println(a.toString() + "x^2 + " + b.toString() + "x + " + c.toString());
>>>>>>> master
		root1 = (b).add(squareRoot(bSquared.subtract(fourAC), new BigDecimal("0"), bSquared.subtract(fourAC)));
		root1 = root1.divide(twoA);
		computedRoots.add(root1.setScale(5,BigDecimal.ROUND_HALF_UP));
		root2 = (b).subtract(squareRoot(bSquared.subtract(fourAC), new BigDecimal("0"), bSquared.subtract(fourAC)));
		root2 = root2.divide(twoA);
		computedRoots.add(root2.setScale(5,BigDecimal.ROUND_HALF_UP));
<<<<<<< HEAD
		/*for (BigDecimal r : computedRoots) {
			//System.out.println(r.toString());
		}*/
=======
		for (BigDecimal r : computedRoots) {
			System.out.println(r.toString());
		}
>>>>>>> master

	}

	//candidate to be moved to fraction or cfrac class
	boolean isWholeNumber(Fraction f) {
		BigInteger num = f.getNum();
		BigInteger den = f.getDen();
		if (num.compareTo(BigInteger.ZERO) <= 0 || den.compareTo(BigInteger.ZERO) <= 0) {
			return false;
		}
		if (num.mod(den).equals(BigInteger.ZERO)) {
			return true;
		}

		else {
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
		//System.out.println(ff);
		if (isWholeNumber(ff)) {
				return num.divide(den);
			}

			else return Globals.INTEGER_NEGATIVE_ONE;
	


	}



	BigInteger weinerAttack() {
		try {
		//System.out.println(this.publicExponent.toString() + " is the public exponent");
		//System.out.println(this.publicModulus.toString() + " is the public modulus");
		ContinuedFraction toAttack = new ContinuedFraction(new Fraction(this.publicExponent,this.publicModulus));
		toAttack.generate(); //generate coef list
		int maxConvergents = toAttack.getMaxConvergentSize();
		for (int i = 1; i <= maxConvergents; i++) {
			//System.out.println(" I am in iteration " + (i - 1) + " out of " + (maxConvergents-1));
			Fraction toTest = toAttack.evaluate(i); //get ith convergent
			//System.out.println(toTest.toString() + " == k/d");
			if ((toTest.getDen().mod(new BigInteger("2"))).equals(BigInteger.ZERO)) {
				//System.out.println("Err: even value for d");
				continue;
			}
			
			else {
				BigInteger possiblePhi = calcPhiN(toTest);
				if (possiblePhi.equals(Globals.INTEGER_NEGATIVE_ONE)) {
					if (maxConvergents != i) {
						continue;
					}


				}
				//System.out.println("My possible phi is" + possiblePhi.toString());
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
					//System.out.println(product.toString() + " == " + publicModulus.toString());
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
<<<<<<< HEAD

				}
			}

		}
=======
					//quadratic formula test
				}
			}

			}
>>>>>>> master
		
		return publicExponent.modInverse(phiN);
	}
	catch(NullPointerException npe) {
		//System.out.println(this.publicExponent + "== e\n" + this.publicModulus + "==N\n");
		return Globals.INTEGER_NEGATIVE_ONE;
		}
	}
	



	

	public static void main(String[] args) {
<<<<<<< HEAD
//try {
	
		//while(true) {
		BigInteger p = primes.generatePrimeOfSize(7);
		BigInteger q = primes.generatePrimeOfSize(7);
		BigInteger publicMod = p.multiply(q);
		BigInteger actualPhi = primes.phiOfPrimes(p,q);

		System.out.println("My public modulus is " + publicMod.toString() + " and has " + publicMod.toString(2).length() + " bits");
	    System.out.println("My actual phi of N is " + actualPhi.toString() );
		BigDecimal capOnDVal = fourthRoot(new BigDecimal(publicMod), new BigDecimal("0"), new BigDecimal(publicMod)).divide(Globals.DECIMAL_THREE, 10, BigDecimal.ROUND_HALF_UP);
		System.out.println("1/3 N^(1/4) = " + capOnDVal.toString());
		
		//BigInteger integralCap = capOnDVal.toBigInteger();
		BigInteger integralCap = actualPhi;
		int numBits = (publicMod.toString(2)).length()/4;
		//generate appropriate d value
		BigInteger testD = new BigInteger("0");
		boolean found = false;
		ArrayList<BigInteger> toTest = new ArrayList<BigInteger>();
			int upper = integralCap.intValue();

			/*if (upper <= 2) {
				continue;
			}*/
			for (int i = 2; i < upper; i++) {
				BigInteger testVal = new BigInteger(i+"");
				if (testVal.gcd(actualPhi).equals(Globals.INTEGER_ONE)) {
					//System.out.println(testVal.toString() + "   " + actualPhi.toString());
					//System.out.println(testVal.modInverse(actualPhi).toString());
					if (testVal.modInverse(actualPhi).compareTo(actualPhi) < 0) {
						found = true;
						testD = testVal;
						toTest.add(testD);
					//	break;
					}
				}

			}
			/*if (found == false) {
				continue; //couldn't find appropriate key in range, starting over
			}*/
			
		for(BigInteger t : toTest) {
		BigInteger publicExponent = t.modInverse(actualPhi);


		WienerAttack wa2 = new WienerAttack(publicMod, publicExponent);

		
		BigInteger dVal = wa2.weinerAttack();
		if (!dVal.equals(Globals.INTEGER_NEGATIVE_ONE)) {
			System.out.println("Attack succeeded for d = " + dVal.toString());
			System.out.println("Successful e = " + publicExponent.toString());

		}
		else {
			System.out.println("Attack failed for d = " + t.toString());
			System.out.println("Failed e = " + publicExponent.toString());
		}
	}
	//}
//}
}
=======
		WienerAttack wa = new WienerAttack(new BigInteger("64741"), new BigInteger("42667"));
		wa.weinerAttack();
		//wa.phiN = new BigInteger("64000");
		//wa.findRoots();
		BigDecimal sixtyfour = new BigDecimal("43534.0");
		BigDecimal zero = new BigDecimal("0");
		int maxIters = (sixtyfour.toBigInteger()).bitCount();
		squareRoot(sixtyfour,zero,sixtyfour);

	}
>>>>>>> master

}


<<<<<<< HEAD
=======
}
>>>>>>> master
