package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CylinderTests {
	
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
	    // TC01: There is a simple single test here
		Cylinder cylinder = new Cylinder(new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1)), 1, 10);
		
		assertEquals("Bad normal to cylinder", cylinder.getNormal(new Point3D(4, 1, 1)),  new Vector(0, 1, 0));	
		
		// ============ Boundary Partitions Tests ==============
	    // TC02: Connection of the point to the ray head of the cylinder axis 
		// produces a right angle with the axis
		assertEquals("Bad normal to cylinder when the point is parallel to the ray's point", new Vector(0, 1, 0), cylinder.getNormal(new Point3D(0, 1, 1)));
		
		// TC03: The point is on one of the bases of the cylinder
		Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(0,0,1));
		cylinder = new Cylinder(ray, 2, 10);
		assertTrue("Bad normal to cylinder when the point is on the base", cylinder.getNormal(new Point3D(0.2, 0.4, 0)).equals(new Vector(0,0,1)) || cylinder.getNormal(new Point3D(0.2, 0.4, 0)).equals(new Vector(0,0,-1)));
	}

}