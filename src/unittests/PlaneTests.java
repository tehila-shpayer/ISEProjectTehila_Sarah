package unittests;
import geometries.*;
import static org.junit.Assert.*;

import org.junit.Test;

import primitives.Point3D;
import primitives.Vector;

public class PlaneTests {
	
	/**
     * Test method for
     * {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
	@Test
	public void testConstructor() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: the simple case of three points in a plane
		//try

		// ============ Boundary Partitions Tests ==============
        // TC02: There is two equals points
        Point3D p1 = new Point3D(4, 5, -2);
        Point3D p2 = new Point3D(4, 5, -2);
        Point3D p3 = new Point3D(0, 3, 2);
        assertThrows("does not throw an exception for equals points",
                IllegalArgumentException.class, () -> new Plane(p1,p2,p3));
        
		// ============ Boundary Partitions Tests ==============
        // TC03: The points are on the same line
        p1 = new Point3D(1,1,1);
        p2 = new Point3D(2,2,2);
        p3 = new Point3D(3,3,3);
        assertThrows("does not throw an exception for points on the same line",
                IllegalArgumentException.class, () -> new Plane(p1,p2,p3));
	}
	
	/**
     * Test method for
     * {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
	@Test
	public void testGetNormalPoint3D() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(1, 1, -1);
        Point3D p3 = new Point3D(0, 1, 1);
        Plane plane = new Plane(p1,p2,p3);
        assertEquals("Bad normal", plane.getNormal(p3), new Vector(0,1,0));

	}

}
