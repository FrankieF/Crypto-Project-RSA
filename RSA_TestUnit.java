package project_3;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * JUnit testing for the RSA class and Person class
 * @author Travis Buff, Frankie Fasola, Eric Carpizo
 *
 */
public class RSA_TestUnit {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * @author Travis Buff
	 */
	@Test
	public void inverse() {
		assertEquals(9,RSA.inverse(3,26));
		assertEquals(53,RSA.inverse(17, 75));
		assertEquals(1190,RSA.inverse(590, 1293));
		assertEquals(17,RSA.inverse(28, 25));
		assertEquals(1,RSA.inverse(21, 5));
		
		//code after this comment is expected to throw the specified exception;
		thrown.expect(IllegalArgumentException.class);
		RSA.inverse(-5,10);
		RSA.inverse(0, 15);
		RSA.inverse(6, 0);
		RSA.inverse(12, -8);
		RSA.inverse(0, 0);
		RSA.inverse(-250, -29);
	}
	
	@Test
	public void modPower(){
		assertEquals(7,RSA.modPower(75, 1, 17));
		assertEquals(8,RSA.modPower(5, 32543, 13));
		assertEquals(1,RSA.modPower(6456,857490,43));
		assertEquals(47,RSA.modPower(93, 15, 115));
		assertEquals(565,RSA.modPower(985, 9324828, 2110));
		
		//code after this comment is expected to throw the specified exception;
		thrown.expect(IllegalArgumentException.class);
		RSA.modPower(-1, 0, 2);
		RSA.modPower(15, -5, 23);
		RSA.modPower(3, 5, 0);
		RSA.modPower(11, 8, -10);
		
		
	}

}
