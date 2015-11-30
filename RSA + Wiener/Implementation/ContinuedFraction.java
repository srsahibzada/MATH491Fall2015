/*
	Simple continued fraction generator/evaluator for use in 
	a Wiener's attack implementation
	Written by Sarah Sahibzada, BS Comp Sci, BS Applied Math, Texas A&M 
	16
	MATH 491 Computational Number Theory Group:
	Sarah Sahibzada, Daniel Whatley & Taylor Wilson
	TODO:
		CFrac should extend fraction (bad practice of writing code sans planning)
		
		

*/

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.*;
import java.lang.StringBuilder;

class Fraction {
	private BigInteger numerator;
	private BigInteger denominator;

	public Fraction(BigInteger n, BigInteger d) {
		this.numerator = n;
		this.denominator = d;
	}
	public BigInteger getNum() {
		return this.numerator;
	}
	public BigInteger getDen() {
		return this.denominator;
	}
	public void setNum(BigInteger n) {
		this.numerator = n;
	}
	public void setDen(BigInteger d) {
		this.denominator = d;
	}

	@Override
	//this is ugly sorry
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(numerator.toString() + " / " + denominator.toString() + "\n");
		/*for (BigInteger b : cFracExpansion) {
			toReturn.append(b.toString() + "\n");
		}*/
		return toReturn.toString();

	}
	@Override 
	public boolean equals(Object o) {
		if (!(o instanceof Fraction)) {
			return false;
		}
		else {
			Fraction ff = (Fraction)o;
			if (!ff.numerator.equals(this.numerator)) {
				return false;
			}
			if (!ff.denominator.equals(this.denominator) ){
				return false;
			}
			return true;
		}
	}
	@Override
	public int hashCode() {
		String bits = (this.numerator.multiply(this.denominator)).toString(2);

		int hash = Integer.parseInt(bits);
		//System.out.println(""+hash);
		return hash;
	}

}

public class ContinuedFraction {
	Fraction f;
	ArrayList<BigInteger> cFracExpansion = new ArrayList<BigInteger>();
	int maxExpansionSize;
	//BigInteger cFracMod = new BigInteger("-1");
	public BigInteger getNumerator() {
		return f.getNum();
	}
	public BigInteger getDenominator() {
		return f.getDen();
	}
	public void setNumerator(BigInteger n) {
		f.setNum(n);
	}
	public void setDenominator(BigInteger d) {
		f.setDen(d);
	}

	public int getMaxConvergentSize() {
		return this.maxExpansionSize;
	}
	
	public ContinuedFraction(Fraction f) {
		BigInteger n = f.getNum();
		BigInteger d = f.getDen();
		/*if (n.compareTo(d) < 0) {
			BigInteger temp;
			temp = n;
			n = d;
			d = temp;
		}*/
		this.f = new Fraction(n,d);

	}

	public ContinuedFraction(int nBits) {
		Random r1 = new Random();
		Random r2 = new Random();
		BigInteger n  = new BigInteger(nBits,r1);
		BigInteger d = new BigInteger(nBits,r2);
		if (n.compareTo(d) < 0) {
			BigInteger temp;
			temp = n;
			n = d;
			d = temp;
		}
		this.f = new Fraction(n,d);


	}

	//Generate the continued fraction expansion for an arbitraty fraction
	void generate() {
		//while (cFracMod.compareTo(BigInteger.ZERO) != 0) {
		 BigInteger storeNumerator = f.getNum();
		 BigInteger storeDenominator = f.getDen();
		 BigInteger cF;
		 BigInteger nextDenom;
		 while (storeNumerator.compareTo(Globals.INTEGER_ONE) != 0 && storeDenominator.compareTo(Globals.INTEGER_ZERO) != 0) {
		 		cF = storeNumerator.divide(storeDenominator);
		 		nextDenom = storeNumerator.mod(storeDenominator);
		 		storeNumerator = storeDenominator; //47 <- 17
		 		storeDenominator = nextDenom;

				this.cFracExpansion.add(cF);
		 }
		 this.maxExpansionSize = cFracExpansion.size();
		/* for (BigInteger b : cFracExpansion) {
			System.out.println(b.toString());
		}*/



	}	

	Fraction evaluate(int until) {
		if (until > cFracExpansion.size() || until <= 0) {
			return new Fraction(BigInteger.ZERO, BigInteger.ZERO); //error case
		}
		BigInteger cFVal = this.cFracExpansion.get(0);
		BigInteger pK = BigInteger.ONE;
		BigInteger qK = BigInteger.ONE;
		BigInteger pKPrev = BigInteger.ONE;
		BigInteger qKPrev = BigInteger.ONE;
		boolean firstRow = true;
		boolean secondRow = true;
		Fraction currentConvergent = new Fraction(cFVal,BigInteger.ONE);
		int count = 0;
		//ArrayList<BigInteger> merger = new ArrayList<BigInteger>(this.cFracExpansion);
		for (BigInteger b : cFracExpansion) {
			//exit once nth convergent has been found
			if (count == until) {
				break;
			}
			//special cases for "chart"
			if (firstRow == true) {
				//current conv is already 1
				//System.out.println("Set everything to one");
				pK = b;
				qK = BigInteger.ONE;
				pKPrev = b;
				qKPrev = BigInteger.ONE;
				firstRow = false;
				//System.out.println(b.toString()  + "    PK is " + pK.toString() + " and qK is " + qK.toString() + " in first row");

			}
			else if (secondRow == true && firstRow == false) {

				pKPrev = pK;
				qKPrev = qK;
				pK = b.multiply(pK).add(BigInteger.ONE);
				qK = b;
				//System.out.println(b.toString()  + "    PK is " + pK.toString() + " and qK is " + qK.toString() + " in second row");

				currentConvergent = new Fraction(pK,qK);
				secondRow = false;
			}
			else {
				//pKPrev = pK;
				//qKPrev = qK;k
				BigInteger oldPK = pK;
				BigInteger oldQK = qK;
				pK = b.multiply(pK).add(pKPrev);
				qK = b.multiply(qK).add(qKPrev);
				//System.out.println(b.toString()  + "    PK is " + pK.toString() + " and qK is " + qK.toString() + " in this row");
				//System.out.println(oldPK.toString());
				currentConvergent = new Fraction(pK,qK);
				pKPrev = oldPK;
				qKPrev = oldQK;
			}
			count += 1;
			//System.out.println(currentConvergent);

		}
		Fraction f = new Fraction(pK,qK);
		//System.out.println(f.getNum() + "/" + f.getDen());
		return f;

	}
		



	@Override
	//this is ugly sorry
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(f.getNum().toString() + " / " + f.getDen().toString() + "\n");
		for (BigInteger b : cFracExpansion) {
			toReturn.append(b.toString() + "\n");
		}
		return toReturn.toString();

	}

	@Override 
	public boolean equals(Object o) {
		if (!(o instanceof ContinuedFraction)) {
			return false;
		}
		else {
			ContinuedFraction ff = (ContinuedFraction)o;
			if (!ff.getNumerator().equals( f.getNum())) {
				return false;
			}
			if (!ff.getDenominator().equals(f.getDen())) {
				return false;
			}
			return true;
		}
	}
	@Override
	public int hashCode() {
		String bits = (f.getNum().multiply(f.getDen())).toString(2);
		int sum = 0;
		for (char c : bits.toCharArray()) {
			sum += (int)c;
		}
		//System.out.println(sum);
		return sum;
		//int hash = Integer.parseInt(bits);
		//System.out.println(""+hash);
		//return hash;
	}
	public static void main(String[] args) {
		System.out.println("hello!!");
		Fraction test = new Fraction(new BigInteger("32857"), new BigInteger("47897"));
		//Fraction test2 = new Fraction(new BigInteger("42667"), new BigInteger("64741"));
		ContinuedFraction cf = new ContinuedFraction(test);
		cf.generate();
		/*for (BigInteger b : cf.cFracExpansion) {
			System.out.println(b.toString());
		}*/
	/*	for (int i = 0; i < cf.getMaxConvergentSize(); i++) {
			System.out.println(cf.evaluate(i));
		}
		System.out.println(cf);*/
		cf.evaluate(cf.getMaxConvergentSize());
		//System.out.println(test.equals(test2) + "!");
		//cf.hashCode();

	}
}

