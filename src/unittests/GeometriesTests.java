package unittests;

import static org.junit.Assert.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import org.junit.Test;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;

public class GeometriesTests {

	@Test
	public void testAdd() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: the simple case of adding geometries
		Sphere sphere = new Sphere(Point3D.ZERO, 1);
		Point3D point3d = new Point3D(1, 2, 3);
		Plane plane = new Plane(point3d, new Vector(point3d));
		//Plane plane2 = new Plane(point3d, new Vector(0,0,1));
		Geometries geometries1 = new Geometries(sphere);
		Geometries geometries2 = new Geometries();
		geometries1.add(plane);
		assertEquals("wrong number of geometries", 2, geometries1.getNumberOfGeometries());
		
		//TC02: add geometries to an empty object
		geometries2.add(sphere, plane);
		assertEquals("wrong number of geometries", 2, geometries2.getNumberOfGeometries());
	}

	@Test
	public void testFindIntersections() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: some geometries (not all of them) are intersected (3 points)
		Sphere sphere1 = new Sphere(Point3D.ZERO, 1);
		Sphere sphere2 = new Sphere(new Point3D(0, 0, 8), 1);
		Sphere sphere3 = new Sphere(new Point3D(0, -3, 0), 1);
		Ray ray1 = new Ray(new Point3D(0, 0, 0.5), new Vector(0, -1, 0));
		Geometries geometries1 = new Geometries(sphere1, sphere2, sphere3);
		assertEquals("wrong number of intersections", 3, geometries1.findIntersections(ray1).size());
		
		// ============ Boundary Partitions Tests ==============
        // TC02: empty collection of geometries (0 points)
		Geometries geometries2 = new Geometries();
		assertEquals("intersection with empty collection not empty", null, geometries2.findIntersections(ray1));
		
        // TC03: no intersections with any geometry (0 points)
		Ray ray2 = new Ray(new Point3D(0, 0, 4), new Vector(0, -1, 0));
		assertEquals("no intersections with any geometry not 0", null, geometries1.findIntersections(ray2));
		
        // TC04: intersections with one geometry (1 point)
		Geometries geometries3 = new Geometries(sphere1,sphere2);
		assertEquals("wrong number of intersections with one geometry", 1, geometries3.findIntersections(ray1).size());

        // TC05: intersections with all geometries (5 point)
		Plane plane = new Plane(Point3D.ZERO, new Vector(0,0,1));
		Sphere sphere4 = new Sphere(new Point3D(0, 0, 2), 1);
		Sphere sphere5 = new Sphere(new Point3D(0, 0, 20), 4);
		Ray ray3 = new Ray(new Point3D(0, 0, -2), new Vector(0, 0, 1));
		Geometries geometries4 = new Geometries(plane, sphere4, sphere5);
		assertEquals("wrong number of intersections with all geometries", 5, geometries4.findIntersections(ray3).size());
		
		
	}

}
