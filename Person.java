package project_3;
import java.util.Random;

public class Person 
{
	private long privateKey;
	private long[] publicKey;
	private long n;
	
	public Person(long p, long q, long e)
	{
		// Public keys consist of a modulus, 
		// m, and an encryption exponent, e.
		publicKey = new long[2];
		publicKey[0] = p*q;
		publicKey[1] = e;
		privateKey = RSA.inverse(e, p*q);
	}
	
	/**
	 * Added by Frank
	 * Example driver has constructors with no parameters, 
	 * I made a quick one, but feel free to change it however you like, or throw it away
	 */
	public Person()
	{
		// Public keys consist of a modulus, 
		// m, and an encryption exponent, e.
		publicKey = new long[2];
		Random ran = new Random();
		long p = 0, q = 0; 
		while(p == q) {
			p =  RSA.randPrime(0, 100000, ran);
			q =  RSA.randPrime(0, 100000, ran);
		}
		long m = p*q;
		long e =  RSA.relPrime(m, ran);
		publicKey[0] = m;
		publicKey[1] = e;
		long n = (p - 1) * (q - 1);
		privateKey = RSA.inverse(e, p*q);
	}
	
	public long[] getPublicKey()
	{
		return publicKey;
	}
	
	/**
	 * Added by Frank
	 * Changed the name to encryptTo from encrypTo
	 */
	public long[] encryptTo(String msg, Person recipient)
		{
			//Get the public key and modulus of the recipient
			long[] repicientPubKey = recipient.getPublicKey();
			long recipientM = repicientPubKey[0];
			long recipientE = repicientPubKey[1];
			
			char[] chars = msg.toCharArray();
			
			/** We are encrypting 2 bytes per long, 
			* 	so our array needs to be half the size.
			*  	But if the length is odd, we will miss one byte. 
			*   So we add 1 to account for this, and send a 0 in as the second byte.
			*/ 
			long[] cipher = new long[chars.length/2+1];
			for (int i = 0, j = 0; i < chars.length; i+=2, j++) {
				char c = (i + 1) < chars.length ? chars[i+1] : 0;
					long num = RSA.toLong(chars[i], c);
					cipher[j] = RSA.modPower(num, recipientE, recipientM);
				}
			return cipher;
		}
	
	public String decrypt(long[] msg)
	{
		//decrypt the cipher
		long[] msgArray = new long[msg.length];
		for(int i = 0; i < msgArray.length; i++){
			long l = RSA.modPower(msg[i], privateKey, publicKey[0]);
			msgArray[i] = l;
		}
		String message = "";
		for(int i = 0; i < msgArray.length; i++)
			message += RSA.longToChars(msgArray[i]);
		return message;
	}
}
