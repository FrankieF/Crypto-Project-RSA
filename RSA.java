package project_3;

import java.util.Random;

/**
 * RSA is a class that mimics the RSA cryptosystem which is used for secure
 * data transmission.
 * @author Travis Buff, Frankie Fasola, Eric Carpizo
 * 11-22-2016
 */

public class RSA
{
	public static void main(String[] args) {

		Person Alice = new Person();
		Person Bob = new Person();

		/**
		 * Test 1
		 */
		String msg = new String ("Bob, let's have lunch.");   // message to be sent to Bob
		long []  cipher;
		cipher =  Alice.encryptTo(msg, Bob);      // encrypted, with Bob's public key

		System.out.println ("Message is: " + msg);
		System.out.println ("Alice sends:");
		show (cipher);

		System.out.println ("Bob decodes and reads: " + Bob.decrypt (cipher));	// decrypted,
		// with Bob's private key.
		System.out.println ();

		msg = new String ("No thanks, I'm busy");
		cipher = Bob.encryptTo (msg, Alice);

		System.out.println ("Message is: " + msg);
		System.out.println ("Bob sends:");
		show (cipher);
		System.out.println ("Alice decodes and reads: " + Alice.decrypt (cipher));
		
		/**
		 * Test 2
		 */
		Person frank = new Person();
		Person travis = new Person();
		
		msg = new String ("Can you send me the crypto homework?");   // message to be sent to Bob
		cipher =  frank.encryptTo(msg, travis);      // encrypted, with Bob's public key

		System.out.println ("Message is: " + msg);
		System.out.println ("Frank sends:");
		show (cipher);

		System.out.println ("Travis decodes and reads: " + travis.decrypt (cipher));	// decrypted,
		// with Bob's private key.
		System.out.println ();

		msg = new String ("No ask Eric.");
		cipher = travis.encryptTo (msg, frank);

		System.out.println ("Message is: " + msg);
		System.out.println ("Travis sends:");
		show (cipher);
		System.out.println ("Frank decodes and reads: " + frank.decrypt (cipher));
		
		/***
		 * Test 3
		 */
		Person eric = new Person();
		msg = new String ("Eric, what is the crypto homework?");   // message to be sent to Bob
		cipher =  frank.encryptTo(msg, eric);      // encrypted, with Bob's public key

		System.out.println ("Message is: " + msg);
		System.out.println ("Frank sends:");
		show (cipher);

		System.out.println ("Eric decodes and reads: " + eric.decrypt (cipher));	// decrypted,
		// with Bob's private key.
		System.out.println ();

		msg = new String ("Problems 1 and 2, in chapte 7.");
		cipher = eric.encryptTo (msg, frank);

		System.out.println ("Message is: " + msg);
		System.out.println ("Eric sends:");
		show (cipher);
		System.out.println ("Frank decodes and reads: " + frank.decrypt (cipher));
	}

	/***
	 * Displays all the longs in an array.
	 * @author Francis Fasola
	 * @param cipher The array of cipher text, made up of longs.
	 */
	private static void show(long[] cipher) {
		String s = "";
		for (long l : cipher)
			s += l + " ";
		System.out.println(s);
	}

	public RSA(){}
/**
	 * Finds the multiplicative inverse of a long int e (mod m).
	 * @author Travis Buff
	 * @param e is the number that the method will find the inverse of.
	 * @param m is the mod.
	 * @return inverse of e mod m
	 */
	public static long inverse(long e, long m){
		if(e < 1 || m < 1){
			throw new IllegalArgumentException("Inverse does not allow a mod or base less than 1");
		}
		//r1 is used as the numerator to find the next quotient.
		//r2 is used as the denominator to find the next quotient.
		long r1 = m,r2 = e, swap = 0;
		
		//used for the euclidean algorithm equation U = uMin2 - q * uMin1.  
		long u = 0, uMin2 = 0, uMin1 = 1;
		
		//used for the euclidean algorithm equation V = vMin2 - q * vMin2.
		long v = 0, vMin2 = 1, vMin1 = 0;
		
		//The quotient.
		long q = 0;
		
		//finds the inverse using euclidean algorithm.
		while(r2 != 1){
			//gets the quotient
			q = r1/r2;
			//swap holds value r2 for use later.
			swap = r2;		
			
			//gets the next r2 value
			r2 = modPower(r1,1,r2);
			//the previous r2 becomes r1.
			r1 = swap;
			
			u = uMin2 - q * uMin1;
			//updates the uMin2 and uMin1 for the next loop.
			uMin2 = uMin1;
			uMin1 = u;
			
			v = vMin2 - q * vMin1;
			//updates the vMin2 and vMin1 for the next loop
			vMin2 = vMin1;
			vMin1 = v;	
			
			//for error checking to make sure euclidean alg is working correctly.
			if(r2 != (u*e + v*m)){
				System.err.println("The value for: "+ r2 + " has the wrong u or v");
			}
			
		}
		if(u < 0){
			u = u + m;
			return u;
		}else{
		return u;
		}
	}
	
	/**
	 * Raise a number, b, to a power , p, mod m
	 * @author Travis Buff
	 * @param b is the base number
	 * @param p is the power for the base number
	 * @param m is the mod
	 * @return the number for b^p (mod m)
	 */
	public static long modPower(long b, long p, long m){
		if(p < 0 || m < 1){
			throw new IllegalArgumentException();
		}
		long result = 1;
		long base = b;
		while (p > 0) {
			//used for finding and getting rid of low order bit.
			if (p % 2 == 1) {
				result = (result * base) % m;
			}
			base = (base * base) % m;
			p = p/2;
		}
		return result % m;
	}

	/***
	 * Generates a random prime number in the range of m - n.
	 * @author Francis Fasola
	 * @param m The lower bound.
	 * @param The upper bound.
	 * @return Random prime number.
	 */
	public static long randPrime(int m, int n, Random rand) {
		boolean isPrime = false;
		int number = 0;
		// nextInt takes an upper bound, so we calculate the range where our number can be
		// Then we add the lower bound to make our number in the specified range.
		int range = n - m;
		while(!isPrime) {
			number = rand.nextInt(range) + m;
			isPrime = isPrime(number);
		}
		return number;			
	}

	/***
	 * @author Francis Fasola
	 * Checks if a number is prime.
	 * @param number The number to check for primality.
	 * @return true if number is prime.
	 */
	public static boolean isPrime(int number) {

		// Cache the square root of the number,
		// then check all values of 2 - root
		double root = Math.sqrt(number);
		//eliminate all even numbers
		if(number%2 == 0)
			return false;
		//Just check odd numbers
		for(int i = 3; i <= root +1; i+=2) {
			if(number % i == 0)
				return false;
		}
		return true;
	}

	/***
	 * @author Francis Fasola
	 * Finds a number between 2 - n-1 that is relatively prime to n.
	 * @param n The upper bound for searching.
	 * @return A number relatively prime to n.
	 */
	public static long relPrime(long n, Random rand) {
		long number = 0;
		long bound = n;
		boolean isRelPrime = false;
		// Generate a random number
		// Then check if the number is relatively prime to n
		while(!isRelPrime) {
			number = (long)(rand.nextDouble() * bound);
			//System.out.println("num: " + number + " n: " + n);
			if(number > 1 && gcf(number, n) == 1) isRelPrime = true;
		}
		return number;
	}

	/***
	 * @author Francis Fasola
	 * Determins the greatest common factor of two numbers.
	 * @param a First number.
	 * @param b Second number.
	 * @return The greatest common factor.
	 */
	protected static long gcf(long a, long b) {
		long c = 0;
		while(b != 0) {
			c = a;
			a = b;
			b = c%b;
		}
		return a;
	}	
	/***
	 * @author Francis Fasola
	 * Stores two characters inside of a long.
	 * @param a The first character to put inside the long.
	 * @param b The second character to be put inside the long.
	 * @return A long holding the value of both characters.
	 */
	public static long toLong(char a, char b) {
		long l = 0;
		int left = (int)a;
		int right = (int)b;
		// Store the value of the first character
		// Then move the value to the left and store the second character
		l += left;
		l = l << 8;
		l += right;
		return l;
	}

	/***
	 * @author Francis Fasola
	 * Converts a long to a string by extracting the characters inside of it.
	 * @param x The long storing the value of characters.
	 * @return A string representation of the characters.
	 */
	public static String longToChars (long x) {
		// First shift the long 8 to the right and store the value in a char
		char left = (char)(x >> 8);
		// Next move the long to the left 8 bits removing the first char,
		// Then shift the long to the right 8 bits and store the value in a char
		//
		char right = (char)(x & 0x00ff);
		String s = "";
		// If the left value is 0, we know the right value is also 0,
		// So we do not check the right

		s  +=""+ left + right;
		return s;
	}
}
