package fiboandlucas;

/*
MATH 491: Computational Number Theory Fall 2015
Sarah Sahibzada, Taylor Wilson & Daniel Whatley
Verifying Identities and Conjectures Proposed in "Sums of Certain Products of Fibonacci and Lucas Numbers"
Useful Reference: http://www.shsu.edu/~ldg005/data/mth164/F2.pdf
*/

import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FiboAndLucas {
ArrayList<BigInteger> FibonacciList = new ArrayList<BigInteger>();
ArrayList<BigInteger> LucasList = new ArrayList<BigInteger>();
int maxIndex = 0; //default to a 0 value in case bad file

BigInteger ERROR = new BigInteger("-1");
BigInteger FIVE = new BigInteger("5"); //class constant for five, used in identities 8, 10, and 11
BigInteger MINUSTWO = new BigInteger("-2");
BigInteger MINUSFOUR = new BigInteger("-4");
BigInteger ONE = new BigInteger("1");



//call  constructor as : FiboAndLucas newFiboAndLucas = new FiboAndLucas("file1.txt", "file2.txt");
public FiboAndLucas(String fiboName, String lucasName) {

	BufferedReader br_fibo = null;
	BufferedReader br_lucas = null;
	try {
		String currentLine = null;
		br_fibo = new BufferedReader(new FileReader(fiboName));
		br_lucas = new BufferedReader(new FileReader(lucasName));
		while ((currentLine = br_fibo.readLine()) != null) {
			BigInteger toAdd = new BigInteger(currentLine);
			FibonacciList.add(toAdd);
		}
		while ((currentLine = br_lucas.readLine()) != null) {
			BigInteger toAdd = new BigInteger(currentLine);
			LucasList.add(toAdd);
		}
	}
	catch (IOException e) {
		e.printStackTrace();
	}
	finally {
		try {
			if (br_fibo != null) {
				br_fibo.close();
				maxIndex = FibonacciList.size();
			}
			if (br_lucas != null) {
				br_lucas.close();

			}
		}
		catch (IOException ee) {
			ee.printStackTrace();
		}
	}

}


//useful theorem on the sum of first few fibonacci numbers
BigInteger sumOfFibonacciNumbers(int to) {
	if (to > maxIndex - 2) {
		return ERROR; //if it ever returns -1 in one of these, that's bad
	}
	else {
		return FibonacciList.get(to+2).subtract(ONE);
	}

}
//the sum of the first few squares of fibonacci numbers satisfies
// f_n * f_n+1
BigInteger summationOfFibonacciSquares(int to) {
if (to > maxIndex) {
	return ERROR;
}
else {
	return FibonacciList.get(to).multiply(FibonacciList.get(to+1));
}

}

//F_(n+k) + F_(n-k) = F_n L_k
//depeds on k being even
boolean identOne(int n, int k, int max) {
	if (n + k > max || n - k < 0) {
		return false; //bad case
	}
	else {
		BigInteger product = LucasList.get(k).multiply(FibonacciList.get(n));
		BigInteger arg = FibonacciList.get(n+k).add(FibonacciList.get(n-k));
		if (product.compareTo(arg) == 0) {
			System.out.println("id1: good for " + n + " and " + k);
			return true;
		}
		else {
			System.out.println("id1: bad for " + n + " and " + k);
			return false;
		}
	}
}


//depeds on k being odd
boolean identTwo(int n, int k, int max) {
	if (n + k > max|| n - k < 0) {
		return false;
	} 
	else {
	BigInteger product = LucasList.get(n).multiply(FibonacciList.get(k));
	BigInteger arg = FibonacciList.get(n+k).add(FibonacciList.get(n-k));
	if (product.compareTo(arg) == 0) {
		System.out.println("id2: good for " + n + " and " + k);
		return true;
	}
	else {
		System.out.println("id2: bad for " + n + " and " + k);
		return false;
	}
	}
}

boolean identThree(int n, int k,int max) {
	if (n + k > max || n - k < 0) {
		return false; //bad case
	}
	else {
		BigInteger product = LucasList.get(k).multiply(FibonacciList.get(n));
		BigInteger arg = FibonacciList.get(n+k).subtract(FibonacciList.get(n-k));
		if (product.compareTo(arg) == 0) {
			System.out.println("id3: good for " + n + " and " + k);
			return true;
		}
		else {
			System.out.println("id3: bad for " + n + " and " + k);
			return false;
		}
	}

}

boolean identFour(int n, int k, int max) {
	if (n + k > max || n - k < 0) {
		return false;
	} 
	else {
	BigInteger product = LucasList.get(n).multiply(FibonacciList.get(k));
	BigInteger arg = FibonacciList.get(n+k).subtract(FibonacciList.get(n-k));
	if (product.compareTo(arg) == 0) {
		System.out.println("id4: good for " + n + " and " + k);
		return true;
	}
	else {
		System.out.println("id4: bad for " + n + " and " + k);
		return false;
	}
	}

}

boolean identFive(int n, int k, int max) {
	if (n + k > max || n - k < 0) {
		return false;
	} 
	else {
		BigInteger product = LucasList.get(n).multiply(LucasList.get(k));
		BigInteger arg = LucasList.get(n+k).add(LucasList.get(n-k));
		if (product.compareTo(arg) == 0) {

		System.out.println("id5: good for " + n + " and " + k);
			return true;
		}
		else {

		System.out.println("id5: bad for " + n + " and " + k);
			return false;
		}
	}

}

boolean identSix(int n, int k, int max) {
	if (n + k > max || n - k < 0) {
		return false;
	}
	else {
		BigInteger FIVE = new BigInteger("5");
		BigInteger product = FIVE.multiply(FibonacciList.get(n).multiply(FibonacciList.get(k)));
		BigInteger arg = LucasList.get(n+k).add(LucasList.get(n-k));
		if (product.compareTo(arg) == 0) {
			System.out.println("id6: good for " + n + " and " + k);
			return true;
		}
		else {
			System.out.println("id6: bad for " + n + " and " + k);
			return false;
		}

	}

}

boolean identSeven(int n, int k, int max) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	}
	else {
		BigInteger product = LucasList.get(n).multiply(LucasList.get(k));
		BigInteger arg = LucasList.get(n+k).subtract(LucasList.get(n-k));
		if (product.compareTo(arg) == 0) {
                        System.out.println("id7: good for " + n + " and " + k);
			return true;
		}
		else {
                        System.out.println("id7: bad for " + n + " and " + k);
			return false;
		}

	}
}

boolean identEight(int n, int k, int max) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	}
	else {
		BigInteger product = this.FIVE.multiply(FibonacciList.get(n).multiply(FibonacciList.get(k)));
		BigInteger arg = LucasList.get(n+k).subtract(LucasList.get(n-k));
		if (product.compareTo(arg) == 0) {
                        System.out.println("id8: good for " + n + " and " + k);
			return true;
		}
		else {
                        System.out.println("id8: bad for " + n + " and " + k);
			return false;
		}
	}
}

boolean identNine(int n, int k, int max) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	}
	else if (2*n > maxIndex) {
		return false;
	}
	else {
		BigInteger arg = this.MINUSTWO;
		BigInteger lucasArg = LucasList.get(n).multiply(LucasList.get(n));
		BigInteger lucasArgTwo = LucasList.get(2*n);
		BigInteger toSubtract = lucasArg.subtract(lucasArgTwo);
		if (toSubtract.compareTo(arg) == 0) {
                        System.out.println("id9: good for " + n + " and " + k);
			return true;
		}
		else {
                        System.out.println("id9: bad for " + n + " and " + k);
			return false;
		}

	}
}

boolean identTen(int n, int k, int max) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	}
	else if (n*2 > maxIndex) {
		return false;
	}
	else {
		BigInteger arg = this.MINUSFOUR;
		BigInteger lhs = this.FIVE.multiply(FibonacciList.get(2*n)).multiply(FibonacciList.get(2*n));
		BigInteger rhs = LucasList.get(2*n).multiply(LucasList.get(2*n));
		BigInteger toCompare = lhs.subtract(rhs);
		if (toCompare.compareTo(arg) == 0) {
                        System.out.println("id10: good for " + n + " and " + k);
			return true;
		}
		else {
                        System.out.println("id10: bad for " + n + " and " + k);
			return false;
		}

	}

}
boolean identEleven(int n, int k, int max) {
	if (n + k > maxIndex || n - k < 0) {
			return false;
		}
	//fall through cases
	else if (n*2 > maxIndex) {
		return false;
	}
	else if (n*4 > maxIndex) {
		return false;
	}
	BigInteger lhs = this.FIVE.multiply(FibonacciList.get(2*n).multiply(FibonacciList.get(2*n)));
	BigInteger rhs = LucasList.get(4*n);
	BigInteger arg = lhs.subtract(rhs);
	if (arg.compareTo(this.MINUSTWO) == 0) {
                System.out.println("id11: good for " + n + " and " + k);
		return true;
	}
	else {
                System.out.println("id11: bad for " + n + " and " + k);
		return false;
	}
}


//construct it with million_fibo.txt and million_lucas.txt
//call all functions in a nested loop
public static void main(String[] args) {
FiboAndLucas lfib = new FiboAndLucas("million_fibo.txt", "million_lucas.txt");
for (int i = 0; i < 180; i++) {
	System.out.println("" + i + ":" + lfib.FibonacciList.get(i).toString());
}
for (int i = 0; i < 180; i++) {
	System.out.println("" + i + ":" + lfib.LucasList.get(i).toString());
}
for (int i = 1; i < 175; i++) {
	for (int j = 1; j < 175; j++) {
		//j even
		if (j % 2 == 0) {
			lfib.identOne(i,j,175);
			lfib.identFour(i,j,175);
			lfib.identFive(i,j,175);
                        lfib.identEight(i,j,175);
                      
		}
		//j odd
		else {
			lfib.identTwo(i,j,175);
			lfib.identThree(i,j,175);
			lfib.identSix(i,j,175);
                        lfib.identSeven(i,j,175);
                        
		}
                //n odd
                if (i % 2 == 1){
                        lfib.identNine(i, j, 175);
                }
                
                lfib.identTen(i,j,175);
                lfib.identEleven(i, j, 175);
	}

}

}





}
