import java.math.*;
//it is extremely important to note that bitCount() only returns
//the number of bits that differ from sign bit
//System.out.println(new BigInteger("8")); <-- prints 1
public class BigIntegerDemo {

   public static void main(String[] args) {

	// create 2 BigInteger objects
	BigInteger bi1, bi2;  

	// create 2 int objects
	int i1, i2;
	
	// assign values to bi1, bi2
	bi1 = new BigInteger("8"); 
	bi2 = new BigInteger("-7"); 

	// perform bitcount operation on bi1, bi2
	i1 = bi1.bitCount();
	i2 = bi2.bitCount();
	  
	String str1 = "Result of bitcount operation on " + bi1 +" is " +i1;
	String str2 = "Result of bitcount operation on " + bi2 +" is " +i2;

	// print i1, i2 values
	System.out.println( str1 );
	System.out.println( str2 );
	System.out.println(bi1.toString(3));
   }
}