import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.*;

	public class WienerTester implements Runnable {
		private class FileData {
			String nVal,
					phiN,
					eVal,
					dVal,
					dCap,
					success;
			public FileData(String n, String phi, String e, String d, String fraction, String successful) {
				nVal = n;
				phiN = phi;
				eVal = e;
				dVal = d;
				dCap = fraction;
				success = successful;
			}
			@Override
			public String toString() {
				return new String(nVal  + Globals.COMMA 
					+  phiN + Globals.COMMA 
					+  eVal + Globals.COMMA 
					+  dVal  + Globals.COMMA 
					+ dCap + Globals.COMMA 
					+ success + Globals.NEWLINE);
			}

		}
		private static PrimeGenerator primes = new PrimeGenerator();
		public static int pSize = 7; //default set to 7
		public static int qSize = 7; //default set to 7
		public static int numTests = 100; //default set to 100
		public static String fileName = "output.txt";
		
		private static ArrayList<FileData> toWrite = new ArrayList<FileData>();

		public WienerTester(int p, int q, int n, String file) {
			pSize = p;
			qSize = q;
			numTests = n;
			fileName = file;
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

	public void WienerTest() {

		//while(true) {
		BigInteger p = primes.generatePrimeOfSize(pSize);
		BigInteger q = primes.generatePrimeOfSize(qSize);
		BigInteger publicMod = p.multiply(q);
		BigInteger actualPhi = primes.phiOfPrimes(p,q);

		//System.out.println("My public modulus is " + publicMod.toString() + " and has " + publicMod.toString(2).length() + " bits");
	   // System.out.println("My actual phi of N is " + actualPhi.toString() );
		BigDecimal capOnDVal = fourthRoot(new BigDecimal(p.multiply(q)), new BigDecimal("0"), new BigDecimal(publicMod)).divide(Globals.DECIMAL_THREE, 10, BigDecimal.ROUND_HALF_UP);
		String strRepCapOnDVal = capOnDVal.toString();
		//System.out.println("1/3 N^(1/4) = " + capOnDVal.toString());
		
		//BigInteger integralCap = capOnDVal.toBigInteger();
		BigInteger integralCap = actualPhi;
		//int numBits = (publicMod.toString(2)).length()/4;
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
			String test = publicExponent.toString();
			BigInteger dVal = wa2.weinerAttack();
			if (!dVal.equals(Globals.INTEGER_NEGATIVE_ONE)) {
				//public FileData(String n, String phi, String e, String d, String fraction, String successful)
				//System.out.println("Attack succeeded for d = " + t.toString());
				//System.out.println(" e = " + publicExponent.toString());
				FileData fd = new FileData(publicMod.toString(),actualPhi.toString(),test, t.toString(), strRepCapOnDVal, "1");
				toWrite.add(fd);

			}
			else {
			//	System.out.println("Attack failed for d = " + t.toString());
			//	System.out.println("Failed e = " + publicExponent.toString());
				FileData fd = new FileData(publicMod.toString(),actualPhi.toString(),test, t.toString(), strRepCapOnDVal, "0");
				toWrite.add(fd);
			}
	}
	//}
	}

	@Override
	public void run() {
		WienerTest();
		//System.out.println("completed a test\n");
	}

	public static void main(String[] args) throws IOException {
		int p=0,q=0,numTests=0;
		String file = "";
		if (args.length != 4) {
			System.out.println("Error: bad number of arguments\n");
			System.out.println("Please run as java WienerTester <pSize> <qSize> <numTests> <ofilename>");
			return;
		}
		else {
			 p = Integer.parseInt(args[0]);
			 q = Integer.parseInt(args[1]);
			 numTests = Integer.parseInt(args[2]);
			
			if (args[3].contains(Globals.DOTCSV)) {
				//args[3] = "/MATH491Project3/RSA + Wiener/Implementation/OUTPUT" +  args[3];
				file = args[3];
			}
			else {
				System.out.println("Invalid file name; using 'output.csv' instead.");
				file = "output.csv";
			}
			
			
		}
		//write everything to a directory
		//jk bash script
		/*File directory = new File("/Desktop/MATH491Project3/RSA + Wiener/Implementation/OUTPUT");
		if (!directory.exists()) {
			directory.mkdir();
		}*/
		//files
		BufferedWriter bwriter = null;
		long start = System.nanoTime();
		ArrayList<FileData> globalData = new ArrayList<FileData>();

		for (int i = 0; i < numTests; i++) {
			WienerTester w = new WienerTester(p,q,numTests,file);
			//w.run();
			w.WienerTest();

		}
		long end = System.nanoTime();
		System.out.println("Ran in " + (end - start)/1000000 + "ms ");
		File outputFile = new File(fileName);
		for (FileData f: toWrite) {
			//System.out.println(f.toString());
			globalData.add(f);
		}
		try {
			File output = new File(file);
			FileWriter write = new FileWriter(output);
			bwriter = new BufferedWriter(write);
			for (FileData f: globalData) {
				bwriter.write(f.toString());
			}
		}
		catch(IOException e) {
			System.out.println("Error in writing to file\n");
			e.printStackTrace();
		}
		finally {
			try {
				if (bwriter != null) {
					bwriter.close();
				}
			}
			catch(Exception e) {
				System.out.println("Couldn't close BufferedWriter\n");
			}
		}
	//	wienerAttack wa = new wienerAttack(new BigInteger("4717"), new BigInteger("199"));
	//	wa.weinerAttack();

	}
}
