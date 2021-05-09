package unittests;
import geometries.*;
import geometries.Intersectable.GeoPoint;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import primitives.Point3D;
import primitives.Ray;
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
        // Point3D p1 = new Point3D(4, 5, -2);
        // Point3D p2 = new Point3D(4, 5, -2);
        // Point3D p3 = new Point3D(0, 3, 2);
        assertThrows("does not throw an exception for equals points",
                IllegalArgumentException.class, () -> new Plane(new Point3D(4, 5, -2), new Point3D(4, 5, -2), new Point3D(0, 3, 2)));
        
		// ============ Boundary Partitions Tests ==============
        // TC03: The points are on the same line
        // p1 = new Point3D(1,1,1);
        // p2 = new Point3D(2,2,2);
        // p3 = new Point3D(3,3,3);
        assertThrows("does not throw an exception for points on the same line",
                IllegalArgumentException.class, () -> new Plane(new Point3D(1,1,1),new Point3D(2,2,2),new Point3D(3,3,3)));
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

	/**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
    	Plane plane = new Plane(new Point3D(0, 0, 1) , new Vector(1,1,1));

    	// ============ Equivalence Partitions Tests ==============

    	// TC01: The ray has a simple intersection with the plane
    	List<GeoPoint> result = plane.findGeoIntersections(new Ray(new Point3D(-1, 0, 1), new Vector(1, 0, 0)));
        List<Point3D> pointsList = List.of(result.get(0).point);
        
    	assertEquals("Wrong number of points", 1, result.size());
    	assertEquals("Bad intersaction point", List.of(new Point3D(0, 0, 1)), pointsList);
    	
    	// TC02: Ray's line doesn't cross the plane
        assertNull("Ray's line doesn't cross the plane",
                plane.findGeoIntersections(new Ray(new Point3D(1, 0, 1), new Vector(1, 0, 0))));
    	
    	// =============== Boundary Values Tests ==================
    	
        // TC03: Ray is parallel to the plane
        assertNull("Ray's line parallel to the plane",
                plane.findGeoIntersections(new Ray(new Point3D(0, 0, 2), new Vector(1, -1, 0))));
        
        // TC04: Ray is on the plane
        assertNull("Ray's line is on the plane",
                plane.findGeoIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, -1, 0))));
        
        // TC05: Ray is perpendicular to the plane
        result = plane.findGeoIntersections(new Ray(new Point3D(0, 0, -1), new Vector(1, 1, 1)));
        pointsList = List.of(result.get(0).point);
        assertEquals("Ray's line perpendicular to the plane", List.of(new Point3D((double)2/3, (double)2/3, -(double)1/3)),
                pointsList);
        
        // TC06: Ray's point is on the plane
        assertNull("Ray's point is on the plane",
                plane.findGeoIntersections(new Ray(new Point3D(1, 0, 0), new Vector(1, 2, 3))));
        // TC06: Ray's point is on the plane and equals to the plane's point
        assertNull("Ray's point is on the plane and is equals to the plane's point",
                plane.findGeoIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 2, 3))));
    }
 }
