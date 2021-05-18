package geometries;
/**
 * Class Triangle is the class representing a triangle in a space of 3 dimensions
 * The triangle extends the Polygon class and is represented by the 3 triangular vertices
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

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
		
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		List<GeoPoint> resultOfPlane = plane.findGeoIntersections(ray);
		if (resultOfPlane == null)
			return null;
		List<GeoPoint> result = new LinkedList<GeoPoint>();

		Vector v1 = getVertices().get(0).subtract(ray.getQ0());
		Vector v2 = getVertices().get(1).subtract(ray.getQ0());
		Vector v3 = getVertices().get(2).subtract(ray.getQ0());
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();
		
		Vector v = ray.getDir();
		double r1 = alignZero(v.dotProduct(n1));
		double r2 = alignZero(v.dotProduct(n2));
		double r3 = alignZero(v.dotProduct(n3));
		
		if ((r1 > 0 && r2 > 0 && r3 > 0) || (r1 < 0 && r2 < 0 && r3 < 0)) {
			for (GeoPoint point: resultOfPlane) {
				result.add(new GeoPoint(this, point.point));
			}
			return result;
		}

		return null;
	}
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){

		List<GeoPoint> resultOfPlane = plane.findGeoIntersections(ray, maxDistance);
		if (resultOfPlane == null)
			return null;
		List<GeoPoint> result = new LinkedList<GeoPoint>();

		Vector v1 = getVertices().get(0).subtract(ray.getQ0());
		Vector v2 = getVertices().get(1).subtract(ray.getQ0());
		Vector v3 = getVertices().get(2).subtract(ray.getQ0());
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();
		
		Vector v = ray.getDir();
		double r1 = alignZero(v.dotProduct(n1));
		double r2 = alignZero(v.dotProduct(n2));
		double r3 = alignZero(v.dotProduct(n3));
		
		if ((r1 > 0 && r2 > 0 && r3 > 0) || (r1 < 0 && r2 < 0 && r3 < 0)) {
			for (GeoPoint point: resultOfPlane) {
				result.add(new GeoPoint(this, point.point));
			}
			return result;
		}

		return null;
	}
}
