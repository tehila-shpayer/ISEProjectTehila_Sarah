package unittests;

import static org.junit.Assert.*;
import primitives.Vector;
import primitives.*;
import primitives.Util.*;
import org.junit.Test;
/**
 * Unit tests for primitives.Vector class
 * @authors Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public class VectorTests {

	@Test
	public void testConstructor() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that vector zero throws an exception
        assertThrows("constructor does not throw an exception for vector zero",
                IllegalArgumentException.class, () -> new Vector(0, 0, 0));
        
	}
	
	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubtract() {
		fail("Not yet implemented");
	}

	@Test
	public void testScale() {
		fail("Not yet implemented");
	}

	@Test
	public void testDotProduct() {
		fail("Not yet implemented");
	}

	/**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows("crossProduct() for parallel vectors does not throw an exception",
                IllegalArgumentException.class, () -> v1.crossProduct(v3));
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}
    }


	@Test
	public void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, 4.5, 7);
        
        // ============ Equivalence Partitions Tests ==============
        
        // TC01: Test that squared length of vector is proper 
        assertTrue("lengthSquared() wrong value",isZero(v1.lengthSquared() - 14));
        assertTrue("lengthSquared() wrong value",isZero(v2.lengthSquared() - 73.25));
	}

	@Test
	public void testLength() {
        Vector v1 = new Vector(0, 3, 4);
        Vector v2 = new Vector(-2, 4.5, 7)
        
        // ============ Equivalence Partitions Tests ==============
        
        // TC01: Test that length of vector is proper 
        assertTrue("length() wrong value",isZero(v1.length() - 5));
        assertEquals("lengthSquared() wrong value",v2.length(), 8.558621, 0.00001);

	}

	@Test
	public void testNormalize() {
		fail("Not yet implemented");
	}

	@Test
	public void testNormalized() {
		fail("Not yet implemented");
	}

}
