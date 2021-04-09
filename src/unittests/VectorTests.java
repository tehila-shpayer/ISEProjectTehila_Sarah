package unittests;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

import primitives.Vector;
import primitives.*;
import primitives.Util.*;
import org.junit.Test;
/**
 * Unit tests for primitives.Vector class
 * @authors Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public class VectorTests {

	/**
     * Test method for {@link primitives.Vector#Vector(primitives.point3D, primitives.point3D, primitives.point3D)}.
	 */
	@Test
	public void testConstructor() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that vector zero throws an exception
        assertThrows("constructor does not throw an exception for vector zero",
                IllegalArgumentException.class, () -> new Vector(0, 0, 0));
        
	}
	
	/**
     * Test method
     * for
     * {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);	
        
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that add operation is proper
        assertEquals("add() wrong result vector", v1.add(v2), new Vector(1, 5, 1));
        assertEquals("add() wrong result vector", v1.add(v1), new Vector(2, 4, 6));
        
        // =============== Boundary Values Tests ==================
        // TC02: test zero vector from add opposite vectors
        Vector v3 = new Vector(-1, -2, -3);
        assertThrows("add() for opposite vectors does not throw an exception",
                IllegalArgumentException.class, () -> v1.add(v3));

    }

	/**
     * Test method for
     * {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);	
        
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that subtract operation is proper
        assertEquals("subtract() wrong result vector", v1.subtract(v2), new Vector(1, -1, 5));
        
        // =============== Boundary Values Tests ==================
        // TC02: test zero vector from subtract vector with it-self
        assertThrows("subtract() for vector with it-self does not throw an exception",
                IllegalArgumentException.class, () -> v1.subtract(v1));
	}

	/**
     * Test method for
     * {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		Vector v1 = new Vector(1, 2, 3);
		
		// ============ Equivalence Partitions Tests ==============
        // TC01: Test that scale operation is proper
        assertEquals("scale() wrong result vector", v1.scale(5), new Vector(5, 10, 15));
        assertEquals("scale() wrong result vector", v1.scale(-1), new Vector(-1, -2, -3));
        
        // =============== Boundary Values Tests ==================
        // TC02: test zero vector from scaling with zero
        assertThrows("scale() for scale vector with zero does not throw an exception",
                IllegalArgumentException.class, () -> v1.scale(0));

	}

	/**
     * Test method for
     * {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, 7, 1);
        
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that dot-product operation is proper
        assertTrue("dotProduct() wrong value", isZero(v1.dotProduct(v2) - 15));       

        // =============== Boundary Values Tests ==================
        // TC04: test zero value from dot-product of orthogonal vectors
        Vector v3 = new Vector(0, 3, -2);
        assertTrue("dotProduct() for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));       
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
        
        // TC01: Test that cross-product operation is proper
        assertEquals("crossProduct() wrong result vector", v1.crossProduct(v2), new Vector(-13, 2, 3));

        // TC02: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC03: Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC04: test zero vector from cross-product of co-lined vectors
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

	/**
     * Test method for
     * {@link primitives.Vector#length(primitives.Vector)}.
	 */
	@Test
	public void testLength() {
        Vector v1 = new Vector(0, 3, 4);
        Vector v2 = new Vector(-2, 4.5, 7);
        
        // ============ Equivalence Partitions Tests ==============       
        // TC01: Test that length of vector is proper 
        assertTrue("length() wrong value", isZero(v1.length() - 5));
        assertEquals("lengthSquared() wrong value",v2.length(), 8.558621, 0.00001);

	}

	@Test
	public void testNormalize() {
		Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        
        // ============ Equivalence Partitions Tests ============== 
        // TC01: Test that normalize() changes original vector 
        assertEquals("normalize() function creates a new vector", vCopy, vCopyNormalize);
        
        // TC02: Test that normalize() returns a unit vector
        assertTrue("normalize() result is not a unit vector", isZero(vCopyNormalize.length() - 1));
        
    }

	@Test
	public void testNormalized() {
        Vector v = new Vector(1, 2, 3);
        
        // ============ Equivalence Partitions Tests ============== 
        // TC01: Test that normalize() creates a new vector and doesn't change the original vector 
        Vector u = v.normalized();
        assertFalse("normalized() function does not create a new vector", v.equals(u));

	}

}
