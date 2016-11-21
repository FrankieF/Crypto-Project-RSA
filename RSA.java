package project_3;

/**
 * RSA is a class that mimics the RSA cryptosystem which is used for secure
 * data transmission.
 * @author Travis Buff
 */
public class RSA {
	
	public static void main(String[] args){
		System.out.print(modPower(43,593239810,73));
	}
	
	public RSA(){}
	
	/**
	 * Finds the multiplicative inverse of a long int e (mod m).
	 * @param e is the number that the method will find the inverse of.
	 * @param m is the mod.
	 * @return inverse of e mod m
	 */
	public static long inverse(long e, long m){
		return 0;
	}
	
	/**
	 * Raise a number, b, to a power , p, mod m
	 * @param b is the base number
	 * @param p is the power for the base number
	 * @param m is the mod
	 * @return the number for b^p (mod m)
	 */
	public static long modPower(long b, long p, long m){
		long result = 1;
		long base = b;
		while (p > 0) {
			if (p % 2 == 1) {
				result = (result * base) % m;
			}
			base = (base * base) % m;
			p = p/2;
		}
		return result % m;
	}
}
