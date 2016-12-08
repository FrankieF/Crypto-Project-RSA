package project_3;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Person is a class that represents a person using the RSA cryptosystem.
 * A Person has a private and public key, and is able to encrypt and decrypt.
 * @author Travis Buff, Eric Carpizo, Frankie Fasola
 * 8 December 2016
 */
public class Person 
{
	/**
	 * The private key of this Person
	 */
	private long privateKey;

	/**
	 * The public modulus of this Person
	 */
	private long publicModulus;

	/**
	 * The public exponent of this Person
	 */
	private long publicExponent;

	/**
	 * 
	 */
	private long n;

	/**
	 * Person class constructor. Initializes a Person's public 
	 * and private keys. A public key consists of a modulus,
	 * m, and an encryption exponent, e. The private key, d, 
	 * from the public values.
	 * @author Eric Carpizo, Frankie Fasola
	 */
	public Person()
	{
		//Generate p and q by creating a randon prime number
		//for each
		Random ran = new Random();
		long  p = 0, q = 0;
		
		while(p == q)
		{
			p =  RSA.randPrime(0, 10000, ran);
			q =  RSA.randPrime(0, 10000, ran);
		}

		//the public modulus, m, is calculated by multiplying
		//p and q
		long m = p * q;
		
		//the public exponent, e, is calculated by finding a 
		//number between 2 -(n-1) that is relatively prime
		//to n.

		publicModulus = m;
		

		//caluclate n
		n = (p-1)*(q-1);
		
		long e = (int) RSA.relPrime(n, ran);
		publicExponent = e;
		//Private key is calculated by calculating the
		//multiplicative inverse of e (mod m)
		privateKey = RSA.inverse(e, n);
	}

	/**
	 * Access the public encryption exponent
	 */
	public long getE()
	{
		return publicExponent;
	}

	/**
	 * Access the public modulus
	 * @return this person's public key
	 */
	public long getM()
	{
		return publicModulus;
	}

	/**
	 * Encrypt a plain text message to she.
	 * @author Eric Carpizo
	 * @param msg The message to be encrypted
	 * @param recipient The intended recipient of the message
	 * @return the message encrypted using RSA
	 */
	public long[] encryptTo(String msg, Person recipient)
	{
		if(msg.length() % 2 != 0)
			msg += " ";

		//Convert the message into an array of chars
		char[] chars = msg.toCharArray();

		//Create the cipher
		long[] cipher = new long[chars.length/2];
		for(int i = 0, j=0; i < chars.length; i+=2, j++)
		{
			long num = RSA.toLong(chars[i], chars[i+1]);
			cipher[j] = RSA.modPower(num, recipient.getE(), recipient.getM());
		}
		return cipher;
	}

	/**
	 * Decrypt the cipher text
	 * @author Eric Carpizo, Francis Fasola
	 * @param cipher The cipher to be decrypted
	 * @return the decrypted message
	 */
	public String decrypt(long[] cipher)
	{
		//convert each long of cipherArray into an array of char,
		//and convert the bytes to a String
		String message = "";

		for(int i = 0; i < cipher.length; i++)
			message += RSA.longToChars(RSA.modPower(cipher[i], privateKey, publicModulus));

		return message;
	}
}
