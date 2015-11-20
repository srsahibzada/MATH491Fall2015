/*
	Simple Wiener's attack in Java
	Written by Sarah Sahibzada, BS Comp Sci, BS Applied Math, Texas A&M 
	'16
	MATH 491 Computational Number Theory Group:
	Sarah Sahibzada, Daniel Whatley & Taylor Wilson

	TODO:
		Newton-Raphson square root vs other options--our maximum 
		values are limited to 2^64
		I need to comment this..... sorry!!!!


*/

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.*;

public class WienerAttack {
	BigInteger publicModulus;
	BigInteger privateExponent;
	BigInteger phiN;
	ArrayList<BigDecimal> computedRoots = new ArrayList<BigDecimal>(); //initialize

	public WienerAttack(BigInteger N, BigInteger e)	{
		this.publicModulus = N;
		this.privateExponent = e;
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

		BigInteger numerator = privateExponent.multiply(f.getDen()).subtract(BigInteger.ONE);
		BigInteger denominator = f.getNum();
		//ConfinuedFraction cf  = new ContinuedFraction(new Fraction(numerator,denominator));
		//System.out.println(cf); //to test
		if (isWholeFrac(new Fraction(numerator,denominator))) {
			this.phiN =  numerator.divide(denominator);
		}
		//return 
	}
	Fraction fracPhiN(Fraction f) {
		BigInteger numerator = privateExponent.multiply(f.getDen()).subtract(BigInteger.ONE);
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
			return new Fraction(BigInteger.ZERO, BigInteger.ZERO);
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

    }
    System.out.println(x1.toString());
    return x1;
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
		System.out.println(a.toString() + "x^2 + " + b.toString() + "x + " + c.toString());
		root1 = (b).add(sqrt(bSquared.subtract(fourAC)));
		System.out.println(root1.toString());
		root1 = root1.divide(twoA);
		System.out.println(root1.toString());
		computedRoots.add(root1);
		root2 = (b).subtract(sqrt(bSquared.subtract(fourAC)));
		System.out.println(root2.toString());
		root2 = root2.divide(twoA);
		System.out.println(root2.toString());
		computedRoots.add(root2);
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
	//f = k/d
	BigInteger calcPhiN(Fraction f) {
	//if (isWholeNumber(f)) {
		BigInteger prospectiveD = f.getDen();
		System.out.println("I am testing the fraction k/d = "+ f);
		System.out.println("my public exponent is " + this.privateExponent.toString());
		System.out.println("verifying prospective D val of " + prospectiveD.toString());
		BigInteger num = this.privateExponent.multiply(prospectiveD).subtract(BigInteger.ONE);
		BigInteger den = f.getNum();
		Fraction ff = new Fraction(num,den);
		if (isWholeNumber(ff)) {
				System.out.println("PASSED THE WHOLE NUMBER TEST");
				System.out.println("I AM RETURNING THE VALUE : " + (num.divide(den)).toString());
				return num.divide(den);
			}
			else return new BigInteger("-1");
	


	}

	BigInteger weinerAttack() {
		ContinuedFraction toAttack = new ContinuedFraction(new Fraction(this.privateExponent,this.publicModulus));
		toAttack.generate(); //generate coef list
		int maxConvergents = toAttack.getMaxConvergentSize();
		for (int i = 1; i <= maxConvergents; i++) {
			System.out.println("phiN is currently: " +phiN);
			Fraction toTest = toAttack.evaluate(i); //get ith convergent
			if ((toTest.getDen().mod(new BigInteger("2"))).equals(BigInteger.ZERO)) {
				continue;
			}
			
			else {
			//	System.out.println("----------------");
				//System.out.println(toTest.toString());
				//boolean t = isWholeNumber(toTest);
				BigInteger possiblePhi = calcPhiN(toTest);
				System.out.println(possiblePhi.toString() + " is my potential phi");
				//System.out.println(possiblePhi.toString() + " is a possible phi");
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
				if (bSquared.subtract(fourAC).compareTo(BigDecimal.ZERO) < 0) {
					continue;
				}
				if (toTest.getNum().equals(BigInteger.ONE) && toTest.getDen().equals(BigInteger.ONE)) {
					System.out.println("bad solution");
					continue;
				}

				//System.out.println(possiblePhi.toString()  + ": -1 : null");

				if (possiblePhi.compareTo(new BigInteger("-1")) != 0) {
					//this.phiN = possiblePhi;
					findRoots(possiblePhi);
					System.out.println("possible phi??");
					BigDecimal product = new BigDecimal(BigInteger.ONE);
					for (BigDecimal potentialRoot : computedRoots) {
						product = product.multiply(potentialRoot);
					}
					System.out.println(product.toString() + " is product");
					//System.out.println(possiblePhi.toString());

					if (product.toBigInteger().equals(publicModulus)) {
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

		System.out.println("We may calculate the decryption key, which is: " + (privateExponent.modInverse(phiN)).toString());
			
		return phiN;
	}
	


	

	public static void main(String[] args) {

		//87685 /29
		//993151 / 5
		WienerAttack wa1 = new WienerAttack(new BigInteger("90581"), new BigInteger("17993"));
		wa1.weinerAttack();
		/*
			In order to correctly test this, it is necessary to test with random numbers such that len(e ^-1 mod phi(n)) = n^1/4 where


		*/
		//System.out.println((new BigInteger("5")).modInverse(new BigInteger("981904")).toString()+ " is e^-1 mod phi(n)");
		//WienerAttack wa = new WienerAttack(new BigInteger("993151"), new BigInteger("5"));
		//wa.weinerAttack();
		//wa.phiN = new BigInteger("64000");
		//wa.findRoots();
	}

	/*



	}*/



}