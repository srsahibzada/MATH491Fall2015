package fiboandlucas;

/*
MATH 491: Computational Number Theory Fall 2015
Sarah Sahibzada, Taylor Wilson, & Daniel Whatley
Verifying Identities and Conjectures Proposed in "Sums of Certain Products of Fibonacci and Lucas Numbers"
Useful Reference: http://www.shsu.edu/~ldg005/data/mth164/F2.pdf
*/

import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SerialFiboAndLucas {
ArrayList<BigInteger> FibonacciList = new ArrayList<BigInteger>();
ArrayList<BigInteger> LucasList = new ArrayList<BigInteger>();
int maxIndex = 0; //default to a 0 value in case bad file

BigInteger ERROR = new BigInteger("-1");
BigInteger FIVE = new BigInteger("5"); //class constant for five, used in identities 8, 10, and 11
BigInteger MINUSTWO = new BigInteger("-2");
BigInteger MINUSFOUR = new BigInteger("-4");
BigInteger ONE = new BigInteger("1");



//call  constructor as : FiboAndLucas newFiboAndLucas = new FiboAndLucas("file1.txt", "file2.txt");
public SerialFiboAndLucas(String fiboName, String lucasName) {

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
boolean identOne(int n, int k) {
        //System.out.println(toString(maxIndex));
	if (n + k > maxIndex || n - k < 0) {
            System.out.println("Bad case");
		return false; //bad case
	}
	else {
		BigInteger product = LucasList.get(k).multiply(FibonacciList.get(n));
                System.out.println(product.toString());
		BigInteger arg = FibonacciList.get(n+k).add(FibonacciList.get(n-k));
                System.out.println(arg.toString());
		if (product.compareTo(arg) == 0) {
                    
                        System.out.println("Identity 1:TRUE");
			return true;
		}
		else {
                        System.out.println("Identity 1:FALSE");
			return false;
		}
	}
}


//depends on k being odd
boolean identTwo(int n, int k) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	} 
	else {
	BigInteger product = LucasList.get(n).multiply(FibonacciList.get(k));
	BigInteger arg = FibonacciList.get(n+k).add(FibonacciList.get(n-k));
	if (product.compareTo(arg) == 0) {
		return true;
	}
	else {
		return false;
	}
	}
}

boolean identThree(int n, int k) {
	if (n + k > maxIndex || n - k < 0) {
		return false; //bad case
	}
	else {
		BigInteger product = LucasList.get(k).multiply(FibonacciList.get(n));
		BigInteger arg = FibonacciList.get(n+k).subtract(FibonacciList.get(n-k));
		if (product.compareTo(arg) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

}

boolean identFour(int n, int k) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	} 
	else {
	BigInteger product = LucasList.get(n).multiply(FibonacciList.get(k));
	BigInteger arg = FibonacciList.get(n+k).subtract(FibonacciList.get(n-k));
	if (product.compareTo(arg) == 0) {
		return true;
	}
	else {
		return false;
	}
	}

}

boolean identFive(int n, int k) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	} 
	else {
		BigInteger product = LucasList.get(n).multiply(LucasList.get(k));
		BigInteger arg = LucasList.get(n+k).add(LucasList.get(n-k));
		if (product.compareTo(arg) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

}

boolean identSix(int n, int k) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	}
	else {
		BigInteger FIVE = new BigInteger("5");
		BigInteger product = FIVE.multiply(FibonacciList.get(n).multiply(FibonacciList.get(k)));
		BigInteger arg = LucasList.get(n+k).add(LucasList.get(n-k));
		if (product.compareTo(arg) == 0) {
			return true;
		}
		else {
			return false;
		}

	}

}

boolean identSeven(int n, int k) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	}
	else {
		BigInteger product = LucasList.get(n).multiply(LucasList.get(k));
		BigInteger arg = LucasList.get(n+k).subtract(LucasList.get(n-k));
		if (product.compareTo(arg) == 0) {
			return true;
		}
		else {
			return false;
		}

	}
}

boolean identEight(int n, int k) {
	if (n + k > maxIndex || n - k < 0) {
		return false;
	}
	else {
		BigInteger product = this.FIVE.multiply(FibonacciList.get(n).multiply(FibonacciList.get(k)));
		BigInteger arg = LucasList.get(n+k).subtract(LucasList.get(n-k));
		if (product.compareTo(arg) == 0) {
			return true;
		}
		else {
			return false;
		}
	}
}

boolean identNine(int n, int k) {
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
			return true;
		}
		else {
			return false;
		}

	}
}

boolean identTen(int n, int k) {
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
			return true;
		}
		else {
			return false;
		}

	}

}
boolean identEleven(int n, int k) {
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
		return true;
	}
	else {
		return false;
	}
}


//construct it with million_fibo.txt and million_lucas.txt
//call all functions in a nested loop
public static void main(String[] args) {
    SerialFiboAndLucas newFiboAndLucas = new SerialFiboAndLucas("million_fibo.txt", "million_lucas.txt");
    for(int n=0; n<=10; n++){
     for(int k=0; k<=10; k+=2){   
   
    newFiboAndLucas.identOne(n, k);
    }
    }
}





}