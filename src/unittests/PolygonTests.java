/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Colocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to polygon", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }
    
//    /**
//     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
//     */
//    @Test
//    void testFindGeoIntersections() {
//        Polygon p = new Polygon(new Point3D(4.0, 4.0, 0.0), new Point3D(4.0, 4.0, 4.0), 
//        		new Point3D(-4.0, 4.0, 4.0), new Point3D(-4.0, 4.0, 0.0));
//
//        // ============ Equivalence Partitions Tests ==============
//
//        //TC01: ray intersects with polygon
//        Ray r = new Ray(new Point3D(1.0, -5.0, 3.0), new Vector(0.0, 3.0, 0.0));
//        List<GeoPoint> l = p.findGeoIntersections(r);
//        List<Point3D> expectList = new ArrayList<Point3D>();
//        expectList.add(new Point3D(1.0, 4.0, 3.0));
//        assertEquals(expectList,List.of(l.get(0).point));
//
//        //TC02: ray intersects with plane but outside the polygon against edge
//        r = new Ray(new Point3D(6.0, -1.0, 0.0), new Vector(0.0, 3.0, 0.0));
//        l = p.findGeoIntersections(r);
//        assertNull("", l);
//
//        //TC03: ray intersects with plane but outside the polygon against vertex
//        r = new Ray(new Point3D(5.0, 4.0, 4.0), new Vector(0.0, 3.0, 0.0));
//        l = p.findGeoIntersections(r);
//        assertNull("", l);
//
//        // =============== Boundary Values Tests ==================
//
//        //TC01: the ray begins before the plane on the edge of polygon
//        r = new Ray(new Point3D(4.0, 3.0, 0.0), new Vector(0.0, 1.0, 0.0));
//        l = p.findGeoIntersections(r);
//        assertNull("", l);
//
//        //TC02: the ray begins before the plane on vertex
//        r = new Ray(new Point3D(4.0, 3.0, 4.0), new Vector(0.0, 1.0, 0.0));
//        l = p.findGeoIntersections(r);
//        assertNull("", l);
//
//        //TC03: the ray begins before the plane on edge's continuation
//        r = new Ray(new Point3D(8.0, 2.0, 0.0), new Vector(0.0, 1.0, 0.0));
//        l = p.findGeoIntersections(r);
//        assertNull("", l);
//    } 
}
