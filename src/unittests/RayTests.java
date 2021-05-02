package unittests;

import static org.junit.Assert.*;
import primitives.*;

import org.junit.Test;

import primitives.Point3D;
import primitives.Util;

public class RayTests {

	@Test
	public void testGetPoint() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple get point calculation
		Point3D p1 = new Point3D(8, 9, -1);
		Ray ray1 = new Ray(new Point3D(2, 1, -1) , new Vector(0.6,0.8,0));
		assertEquals("Bad get point calculation", p1, ray1.getPoint(10));
		
		// ============ Boundary Partitions Tests ==============
        // TC02: t scale is zero and q0 is zero
		Ray ray2 = new Ray(Point3D.ZERO, new Vector(0,0,1));
        assertThrows("get point does not throw vector zero", IllegalArgumentException.class, () -> ray2.getPoint(0));
	}

	@Test
	public void testFindClosestPoint() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple get point calculation
		Point3D p1 = new Point3D(8, 9, -1);
		Ray ray1 = new Ray(new Point3D(2, 1, -1) , new Vector(0.6,0.8,0));
		assertEquals("Bad get point calculation", p1, ray1.getPoint(10));
		
		// ============ Boundary Partitions Tests ==============
        // TC02: t scale is zero and q0 is zero
		Ray ray2 = new Ray(Point3D.ZERO, new Vector(0,0,1));
        assertThrows("get point does not throw vector zero", IllegalArgumentException.class, () -> ray2.getPoint(0));
	}

}
