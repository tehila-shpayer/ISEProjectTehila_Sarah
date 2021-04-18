/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
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
    
    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
    	Polygon polygon = new Polygon(new Point3D(1, 1, 0) , new Point3D(5, 1, 0), new Point3D(1, 4, 0), new Point3D(5, 4, 0));

    	// ============ Equivalence Partitions Tests ==============

    	// TC01: The ray has a simple intersection with the polygon
    	assertEquals("Bad intersaction point", List.of(new Point3D(2,2,0)), polygon.findIntersections(new Ray(new Point3D(2, 2, 1), new Vector(0, 0, -1))));
    	
    	// TC02: Ray is outside the polygon against edge
        assertNull("Ray is outside the polygon against edge",
        		polygon.findIntersections(new Ray(new Point3D(2, 5, 1), new Vector(0, 0, -1))));
    	
        // TC03: Ray is outside the polygon against vertex
        assertNull("Ray is outside the polygon against vertex",
        		polygon.findIntersections(new Ray(new Point3D(6, 0.5, 1), new Vector(0, 0, -1))));
        
        // =============== Boundary Values Tests ==================
        
        // TC04: Ray is on the edge of the triangle
        assertNull("Ray is outside the triangle against vertex",
        		polygon.findIntersections(new Ray(new Point3D(2, 1, 1), new Vector(0, 0, -1))));
        
        // TC05: Ray is on the vertex of the triangle
        assertNull("Ray is outside the triangle against vertex",
        		polygon.findIntersections(new Ray(new Point3D(5, 1, 1), new Vector(0, 0, -1))));
        
        // TC06: Ray is on the edge's continuation of the triangle
        assertNull("Ray is outside the triangle against vertex",
        		polygon.findIntersections(new Ray(new Point3D(6, 1, 1), new Vector(0, 0, -1))));

    }
}
