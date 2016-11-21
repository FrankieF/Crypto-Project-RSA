package project_3;

/**
 * RSA is a class that mimics the RSA cryptosystem which is used for secure
 * data transmission.
 * @author Travis Buff, Frankie Fasola, Eric Carpizo
 */
public class RSA {
	
	public static void main(String[] args){
		System.out.println(modPower(75,1,17));
		System.out.println(inverse(17,75));
	}
	
	public RSA(){}
	
	/**
	 * Finds the multiplicative inverse of a long int e (mod m).
	 * @param e is the number that the method will find the inverse of.
	 * @param m is the mod.
	 * @return inverse of e mod m
	 */
	public static long inverse(long e, long m){
		//r1/r2 = q and next r2
		long r1 = m,r2 = e, swap = 0;
		long u = 0, uMin2 = 0, uMin1 = 1;
		long v = 0, vMin2 = 1, vMin1 = 0;
		long q = 0;
		while(r2 != 1){
			//gets the quotient
			q = r1/r2;
			//swap holds value r2 for use later.
			swap = r2;		
			//gets the next r2 value
			r2 = modPower(r1,1,r2);
			r1 = swap;
			
			u = uMin2 - q * uMin1;
			uMin2 = uMin1;
			uMin1 = u;
			v = vMin2 - q * vMin1;
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
