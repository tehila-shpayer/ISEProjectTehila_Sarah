package unittests;
import geometries.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

import org.junit.Test;

public class CylinderTests {

	@Test
	public void testGetNormal() {
		Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(new Point3D(0,0,1)));
		Cylinder cylinder = new Cylinder(ray, 2, 10);
		assertTrue("Bad normal to cylinder when the point is on the", cylinder.getNormal(new Point3D(0.2, 0.4, 0)).equals(new Vector(0,0,1)) || cylinder.getNormal(new Point3D(0.2, 0.4, 0)).equals(new Vector(0,0,-1)));
	}

}
