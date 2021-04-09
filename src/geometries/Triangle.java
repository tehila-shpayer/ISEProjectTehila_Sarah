package geometries;
/**
 * Class Triangle is the class representing a triangle in a space of 3 dimensions
 * The triangle extends the Polygon class and is represented by the 3 triangular vertices
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

import java.util.List;

import primitives.Point3D;
import primitives.Vector;

public class Triangle extends Polygon
{
	/**
	 * Triangle constructor based on the 3 vertices.
	 * the constructor use the super constructor of the Polygon.
	 * 
	 * @param p1 - the first vertex
	 * @param p2 - the second vertex
	 * @param p3 - the third vertex
	 */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {
		super(p1, p2, p3);
	}
	
	/**
     * Returns the list of the 3 triangle points
     * @return vertices - the list of the triangle's points
     */
	public List<Point3D> getVertices() {
		return this.vertices;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	@Override
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}
}
