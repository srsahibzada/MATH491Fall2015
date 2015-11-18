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
		BigInteger num = this.privateExponent.multiply(prospectiveD).subtract(BigInteger.ONE);
		BigInteger den = f.getNum();
		Fraction ff = new Fraction(num,den);
		if (isWholeNumber(ff)) {
				return num.divide(den);
			}
			else return new BigInteger("-1");
	


	}

	BigInteger weinerAttack() {
		ContinuedFraction toAttack = new ContinuedFraction(new Fraction(this.privateExponent,this.publicModulus));
		toAttack.generate(); //generate coef list
		int maxConvergents = toAttack.getMaxConvergentSize();
		for (int i = 1; i <= maxConvergents; i++) {
			Fraction toTest = toAttack.evaluate(i); //get ith convergent
			if ((toTest.getDen().mod(new BigInteger("2"))).equals(BigInteger.ZERO)) {
				continue;
			}
			
			else {
			//	System.out.println("----------------");
				System.out.println(toTest.toString());
				//boolean t = isWholeNumber(toTest);
				BigInteger possiblePhi = calcPhiN(toTest);
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
					System.out.println(product.toString());
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
		WienerAttack wa = new WienerAttack(new BigInteger("64741"), new BigInteger("42667"));
		wa.weinerAttack();
		//wa.phiN = new BigInteger("64000");
		//wa.findRoots();
		BigDecimal sixtyfour = new BigDecimal("43534.0");
		BigDecimal zero = new BigDecimal("0");
		int maxIters = (sixtyfour.toBigInteger()).bitCount();
		squareRoot(sixtyfour,zero,sixtyfour);

	}



}
