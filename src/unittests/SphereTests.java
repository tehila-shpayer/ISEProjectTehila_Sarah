package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class SphereTests {

	/**
     * Test method for
     * {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple test here
        Sphere s = new Sphere(new Point3D(1, 3, 0), 2);
        assertEquals("Bad normal to sphere", new Vector(0, -1, 0), s.getNormal(new Point3D(1, 2, 0)));
	}
	
	/**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere",
                sphere.findGeoIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findGeoIntersections(new Ray(new Point3D(-1, 0, 0),
                                                                new Vector(3, 1, 0)));
        List<Point3D> pointsList = List.of(result.get(0).point, result.get(1).point);
        
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).point.getX() > result.get(1).point.getX()) {
        	result = List.of(result.get(1), result.get(0));
            pointsList = List.of(result.get(1).point, result.get(0).point);
        }
        assertEquals("Ray crosses sphere", List.of(p1, p2), pointsList);

        // TC03: Ray starts inside the sphere (1 point)
        double sqrt3 = Math.sqrt(3);
        Point3D p3 = new Point3D(0.5, 0, sqrt3/2);
        result = sphere.findGeoIntersections(new Ray(new Point3D(0.5,0,0),
                new Vector(0, 0, sqrt3/2)));
        pointsList = List.of(result.get(0).point);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p3), pointsList);

        // TC04: Ray starts after the sphere (0 points)
        assertNull("Ray starts after the sphere",
                sphere.findGeoIntersections(new Ray(new Point3D (0.5, 0.0, -1.7320508075688774) , new Vector(0, 0, -sqrt3/2))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC05: Ray starts at sphere and goes inside (1 points)
        double sqrt2 = Math.sqrt(2);
        Point3D p4 = new Point3D(0.5, 0.5, 1/sqrt2);
        result = sphere.findGeoIntersections(new Ray(new Point3D(2,0,0),
                new Vector(-1.5, 0.5, 1/sqrt2)));
        pointsList = List.of(result.get(0).point);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p4), pointsList);
        
        // TC06: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray starts at sphere and goes outside",
                sphere.findGeoIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1.5, -0.5, -1/sqrt2))));

        // **** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        Point3D pZero = new Point3D(0,0,0);
        Point3D p6 = new Point3D(2,0,0);
        result = sphere.findGeoIntersections(new Ray(new Point3D(-1, 0, 0),
                                                                new Vector(1, 0, 0)));
        pointsList = List.of(result.get(0).point, result.get(1).point);
        assertEquals("Wrong number of points", 2, result.size());
        
        if (result.get(0).point.getX() > result.get(1).point.getX()) {
        	result = List.of(result.get(1), result.get(0));
            pointsList = List.of(result.get(1).point, result.get(0).point);
        }
            
        assertEquals("Ray crosses sphere before it and through center", List.of(pZero, p6), pointsList);
        
        // TC08: Ray starts at sphere and goes inside (1 points)
        result = sphere.findGeoIntersections(new Ray(new Point3D(0,0,0),
                new Vector(1,0,0)));
        pointsList = List.of(result.get(0).point);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere, starts at it and goes through center", List.of(p6), pointsList);
        
        // TC09: Ray starts inside (1 points)
        result = sphere.findGeoIntersections(new Ray(new Point3D(0.5,0,0),
                new Vector(1,0,0)));
        pointsList = List.of(result.get(0).point);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere, starts inside it and goes through center", List.of(p6), pointsList);
        
        // TC10: Ray starts at the center (1 points)
        result = sphere.findGeoIntersections(new Ray(new Point3D(1,0,0),
                new Vector(-1,0,0)));
        pointsList = List.of(result.get(0).point);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere, starts at center", List.of(pZero), pointsList);        
        
        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray starts at sphere and goes outside (oposite direction goes through center)",
                sphere.findGeoIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1,0,0))));
        
        // TC12: Ray starts after sphere (0 points)
        assertNull("Ray starts outside the sphere and goes outside (oposite direction goes through center)",
                sphere.findGeoIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1,0,0))));
        
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull("Ray starts before the tangent point",
                sphere.findGeoIntersections(new Ray(new Point3D(2, -1, 0), new Vector(0,1,0))));
        
        // TC14: Ray starts at the tangent point
        assertNull("Ray starts at the tangent point",
                sphere.findGeoIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0,1,0))));
        
        // TC15: Ray starts after the tangent point
        assertNull("Ray starts after the tangent point",
                sphere.findGeoIntersections(new Ray(new Point3D(2, 1, 0), new Vector(0,1,0))));

        // **** Group: Special cases
        // TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull("Ray starts after the tangent point",
                sphere.findGeoIntersections(new Ray(new Point3D(3, 0, 0), new Vector(0,1,0))));
        
    }


}
