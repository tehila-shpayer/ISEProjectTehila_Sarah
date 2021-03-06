package geometries;
/**
 * Class Plane is the class representing a plane in a space of 3 dimensions
 * The plane is represented by a point on the plane and normal vector to the plane
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

import static primitives.Util.isZero;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

public class Plane extends Geometry
{
	/**
     * q0 - a point on the plane
     * normal - a normal vector to the plane
     */
	Point3D q0;
	Vector normal;

	/**
     * Plane constructor receiving 2 values
     * 
     * @param p - a point on the plane
     * @param v - a normal vector to the plane
     */
	public Plane(Point3D p, Vector v) {
		this.q0 = p;
		this.normal = v.normalized();
	}
	
	/**
     * Plane constructor receiving 3 points on the plane
     * 
     * @param p1 - a point on the plane
     * @param p2 - a point on the plane
     * @param p3 - a point on the plane
     */
	public Plane(Point3D p1, Point3D p2, Point3D p3) {
		this.q0 = p1; //we take the first point to be the point of the plane
		//calculating the normal vector using the 3 points:
		Vector v1 = p1.subtract(p2);
		Vector v2 = p1.subtract(p3);
		Vector normal = v1.crossProduct(v2);
		this.normal = normal.normalized();
	}
	
	/**
     * Returns the field q0 of the plane
     * @return q0 - the point of the plane
     */
	public Point3D getQ0() {
		return q0;
	}
	/**
     * Returns the field normal of the plane
     * @return normal - the normal vector to the plane
     */
	public Vector getNormal() {
		return normal;
	}
	
	@Override
	public String toString() {
		return "q0: " + this.q0.toString() + ", normal: " + this.normal.toString();
	}
	
	/**
	 * Returns normal to plane
	 * @param point3D - normal from that point
	 * @return normalized normal to plane in point3D
	 */
	@Override
	public Vector getNormal(Point3D point) {
		return normal;
	}


	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		
		if(isZero(ray.getDir().dotProduct(normal)))
		{
			return null;
		}
		double t;
		try {
			t = (double)normal.dotProduct(q0.subtract(ray.getQ0()))/normal.dotProduct(ray.getDir());
		}
		catch (IllegalArgumentException e) {
			return null;
		}
		if (t<=0) return null;
		//A test that verifies that the point is indeed a point of intersection and is within the desired range
		if (alignZero(t - maxDistance) > 0)
			return null;
		return List.of(new GeoPoint(this, ray.getPoint(t)));
	}
	
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		
		if(isZero(ray.getDir().dotProduct(normal)))
		{
			return null;
		}
		double t;
		try {
			t = (double)normal.dotProduct(q0.subtract(ray.getQ0()))/normal.dotProduct(ray.getDir());
		}
		catch (IllegalArgumentException e) {
			return null;
		}
		if (t<=0) return null;
		return List.of(new GeoPoint(this, ray.getPoint(t)));
	}
	
}
 