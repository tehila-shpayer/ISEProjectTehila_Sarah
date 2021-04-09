package unittests;

import primitives.*;
import static org.junit.Assert.*;
import org.junit.Test;
public class Point3DTests {

	/**
     * Test method for {@link primitives.Point3D#add(primitives.Vector)}.
     */
	@Test
	public void testAdd() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple addition
		Point3D p1 = new Point3D(1, 0, -4);
        assertEquals("Bad vector to point addition", p1.add(new Vector(-5,-2,7)), new Point3D(-4, -2, 3));
	
		// ============ Boundary Partitions Tests ==============
        // TC02: There is an addition between a point and the opposite vector.
        Point3D p2 = new Point3D(4, 5, -2);
        assertEquals("Bad vector to point addition when the point and the vector are opposite", p2.add(new Vector(-4,-5,2)), new Point3D(0, 0, 0));
	
	}
	
	@Test
	public void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple subtraction
		Point3D p1 = new Point3D(1, 0, -4);
        assertEquals("Bad points subtraction", p1.subtract(new Point3D(-4, -2, 3)), new Vector(5,2,-7));
	
		// ============ Boundary Partitions Tests ==============
        // TC02: There is an subtraction between equals points
        Point3D p2 = new Point3D(4, 5, -2);
        assertThrows("subtraction does not throw an exception for vector zero",
                IllegalArgumentException.class, () -> p2.subtract(new Point3D(4,5,-2)));
	
	}

	@Test
	public void testDistanceSquared() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple distance squared calculation
		Point3D p1 = new Point3D(1, 0, -4);
        assertTrue("Bad points distance squared", Util.isZero(p1.distanceSquared(new Point3D(-4, -2, 3)) - 78 ));
	
		// ============ Boundary Partitions Tests ==============
        // TC02: the points are equals
        Point3D p2 = new Point3D(4, 5, -2);
        assertTrue("Bad distance squared calculation for equals points", Util.isZero(p2.distanceSquared(new Point3D(4,5,-2))));
	}

}
