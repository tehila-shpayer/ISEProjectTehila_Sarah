package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Tube;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class TubeTests {

	/**
     * Test method for
     * {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
	@Test
	public void testGetNormal() { 
		
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		Tube tube = new Tube(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 1);
		double sqrt3 = Math.sqrt(3);
		assertEquals("Bad normal to tube", new Vector(sqrt3/2, 0.5, 0), tube.getNormal(new Point3D(sqrt3/2, 0.5, 5)));	
		
		// ============ Boundary Partitions Tests ==============
        // TC02: Connection of the point to the ray head of the cylinder axis 
		// produces a right angle with the axis
		assertEquals("Bad normal to tube", new Vector(1, 0, 0), tube.getNormal(new Point3D(1, 0, 0)));	
	}

}
