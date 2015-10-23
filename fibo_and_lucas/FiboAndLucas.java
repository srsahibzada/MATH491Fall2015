

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

public class FiboAndLucas implements Runnable {
ArrayList<BigInteger> FibonacciList = new ArrayList<BigInteger>();
ArrayList<BigInteger> LucasList = new ArrayList<BigInteger>();
int maxIndex = 0; //default to a 0 value in case bad file
int functionNumber;
int range;
BigInteger ERROR = new BigInteger("-1");
BigInteger FIVE = new BigInteger("5"); //class constant for five, used in identities 8, 10, and 11
BigInteger MINUSTWO = new BigInteger("-2");
BigInteger MINUSFOUR = new BigInteger("-4");
BigInteger ONE = new BigInteger("1");



//call  constructor as : FiboAndLucas newFiboAndLucas = new FiboAndLucas("file1.txt", "file2.txt");
public FiboAndLucas() {
	this.functionNumber = 0;
	this.range = 0;
}
public FiboAndLucas(int fNum, int ran) {
	this.functionNumber = fNum;
	this.range = ran;

}
public void setFNum(int fnum) {
	this.functionNumber = fnum;
}
public void setRange(int r) {
	this.range = r;
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
		//	System.out.println("id1: good for " + n + " and " + k);
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
		//System.out.println("id2: good for " + n + " and " + k);
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
		//	System.out.println("id3: good for " + n + " and " + k);
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
		//System.out.println("id4: good for " + n + " and " + k);
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

		//System.out.println("id5: good for " + n + " and " + k);
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
			//System.out.println("id6: good for " + n + " and " + k);
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
                     //   System.out.println("id7: good for " + n + " and " + k);
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
                   //     System.out.println("id8: good for " + n + " and " + k);
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
                    //    System.out.println("id9: good for " + n + " and " + k);
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
                     //  System.out.println("id10: good for " + n + " and " + k);
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
             //   System.out.println("id11: good for " + n + " and " + k);
		return true;
	}
	else {
                System.out.println("id11: bad for " + n + " and " + k);
		return false;
	}
}

public void run() {
	int f = this.functionNumber;
	switch(f){
		case 1:
			for (int i = 0; i < this.range; i++) {
				for (int j = 0; j < this.range; j+=2) {
					this.identOne(i,j,range);
				}
			}
			break;


		case 2:
		for (int i = 0; i < this.range; i++) {
				for (int j = 0; j < this.range; j++) {
					if (j % 2 == 1) {
						this.identTwo(i,j,this.range);
					} 
				}
			}
			break;
		case 3:
		for (int i = 0; i < this.range; i++) {
				for (int j = 0; j < this.range; j++) {
					if (j % 2 == 1) {
						this.identThree(i,j,this.range);
					}
					
				}
			}
			break;
		case 4:
		for (int i = 0; i < this.range; i++) {
				for (int j = 0; j < range; j+=2) {
					this.identFour(i,j,this.range);
				}
			}
			break;
		case 5:
		for (int i = 0; i < this.range; i++) {
				for (int j = 0; j < this.range; j+=2) {
					this.identFive(i,j,this.range);
				}
			}
			break;
		case 6:
		for (int i = 0; i < this.range; i++) {
				for (int j = 0; j < this.range; j++) {
					if (j % 2 == 1) {
						this.identSix(i,j,this.range);
					}
				}
			}
			break;
		case 7:
		for (int i = 0; i < this.range; i++) {
				for (int j = 0; j < this.range; j++) {
					if (j % 2 == 1) {
						this.identSeven(i,j,this.range);
					}
				}
			}
			break;
		case 8:
		for (int i = 0; i < this.range; i++) {
				for (int j = 0; j < this.range; j+=2) {
					this.identEight(i,j,this.range);
				}
			}
		case 9:
		for (int i = 0; i < this.range; i = (2*i) + 1) {
				
				for (int j = 0; j < this.range; j++) {
					this.identNine(i,j,this.range);
				}
				
			}
			break;
		case 10:
		for (int i = 0; i < range; i++) {
				for (int j = 0; j < range; j++) {
					this.identTen(i,j,range);
				}
			}
			break;
		case 11:
		for (int i = 0; i < range; i++) {
				for (int j = 0; j < range; j++) {
					this.identEleven(i,j,range);
				}
			}
			break;
		default:
			System.exit(0);
	}

}


public static void main(String[] args) {

	FiboAndLucas[] identArray = new FiboAndLucas[11];
	for (int i = 0; i < identArray.length; i++ ) {
		identArray[i] = new FiboAndLucas(i+1,275);
	}

	String fiboName = "million_fibo.txt";
	String lucasName = "million_lucas.txt";
	BufferedReader br_fibo = null;
	BufferedReader br_lucas = null;

	//read into the arrays from files, once and for all (O(n) no threads)
	try {
		String currentLine = null;
		br_fibo = new BufferedReader(new FileReader(fiboName));
		br_lucas = new BufferedReader(new FileReader(lucasName));
		//read into the data structure for each FL object
		while ((currentLine = br_fibo.readLine()) != null) {
			BigInteger toAdd = new BigInteger(currentLine);
			for (int i = 0; i < identArray.length; i++) {
				identArray[i].FibonacciList.add(toAdd);
			}
			
		}
		while ((currentLine = br_lucas.readLine()) != null) {
			BigInteger toAdd = new BigInteger(currentLine);
			for (int i = 0; i < identArray.length; i++) {
				identArray[i].LucasList.add(toAdd);
			}
		}
	}
	catch (IOException e) {
		e.printStackTrace();
	}
	finally {
		try {
			if (br_fibo != null) {
				br_fibo.close();
				//maxIndex = FibonacciList.size();
			}
			if (br_lucas != null) {
				br_lucas.close();
			}
		}
		catch (IOException ee) {
			ee.printStackTrace();
		}
	}

	for (int i = 0; i < identArray.length; i++) {
		(new Thread(identArray[i])).start();
		System.out.println("Starting " + i + " th element");
		}
	}
}