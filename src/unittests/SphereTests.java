package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Vector;

public class SphereTests {

	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere s = new Sphere(new Point3D(1,  3, 0), 2);
        assertEquals("Bad normal to sphere", new Vector(0, -1, 0), s.getNormal(new Point3D(1, 2, 0)));
	}

}
