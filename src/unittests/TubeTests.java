package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Tube;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class TubeTests {

	@Test
	public void testGetNormal() {
		
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		Tube tube = new Tube(new Ray(new Point3D(0,  0, 1), new Vector(0, 0, 1)), 1);
		assertEquals("Bad normal to tube", new Vector(0, 1, 0), tube.getNormal(new Point3D(4, 1, 1)));	
		
		// ============ Boundary Partitions Tests ==============
        // TC02: Connection of the point to the ray head of the cylinder axis 
		// produces a right angle with the axis
		assertEquals("Bad normal to tube", new Vector(0, 1, 0), tube.getNormal(new Point3D(0, 1, 1)));	
	}

}
