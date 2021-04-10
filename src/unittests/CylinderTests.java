package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CylinderTests {
	
	/**
     * Test method for
     * {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
	@Test
	public void testGetNormal() {
		
		Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(0,0,1));
		Cylinder cylinder = new Cylinder(ray, 1, 10);
		double sqrt3 = Math.sqrt(3);
		
		// ============ Equivalence Partitions Tests ==============
	    // TC01: normal to point on shell		
		assertEquals("Bad normal to cylinder from surface", cylinder.getNormal(new Point3D(sqrt3/2,0.5,5)),  new Vector(sqrt3/2, 0.5, 0));	
		
	    // TC02: normal to point on first base (where the ray shoots from)		
		assertEquals("Bad normal to cylinder from first base", cylinder.getNormal(new Point3D(0.25, 0.25, 0)),  new Vector(0, 0, 1));	
		
	    // TC03: normal to point on second base		
		assertEquals("Bad normal to cylinder from second base", cylinder.getNormal(new Point3D(0.5,0.5,10)),  new Vector(0, 0, 1));	
		
		// ============ Boundary Partitions Tests ==============
	    // TC04: normal to point on the center of first base (where the ray shoots from)
		assertEquals("Bad normal to point on the center of first base (where the ray shoots from)", cylinder.getNormal(new Point3D(0, 0, 0)), new Vector(0, 0, 1));
			
	    // TC05: normal to point on the center of second base 	
		assertEquals("Bad normal to point on the center of second base", cylinder.getNormal(new Point3D(0, 0, 10)), new Vector(0, 0, 1));
	}

}