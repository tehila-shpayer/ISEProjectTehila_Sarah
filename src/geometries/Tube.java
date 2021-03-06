package geometries;

import static primitives.Util.isZero;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Class Tube is the basic class representing an infinite cylinder by an axis ray and a radius
 * of Euclidean geometry in Cartesian 3-Dimensional coordinate system.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

public class Tube extends Geometry {
	Ray axisRay;
	double radius;
	
	/**
	 * The constructor receives the axis ray and the radius to create the tube
	 * @param ray - axis ray of the tube
	 * @param r - the radius
	 */
	public Tube(Ray ray, double r) {
		this.axisRay = ray;
		this.radius = r;
	}
	
	/**
	 * Returns the axis ray of the tube
	 * @return axisRay - axis ray of the tube
	 */
	public Ray getAxisRay() {
		return axisRay;
	}
	
	/**
	 * Returns the protected radius field
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	} 
	
	@Override
	public String toString() {
		return "Axis Ray: " + this.axisRay.toString() + " Radius: " + this.radius + "";
	}
	
	/**
	 * Returns normal to tube
	 * @param point3D - normal from that point
	 * @return normalized normal to tube in point3D
	 */
	@Override
	public Vector getNormal(Point3D point) {
		Point3D q0 = axisRay.getQ0();
		Vector dirVector = axisRay.getDir();
		Vector toPoint = point.subtract(q0);
		double t = dirVector.dotProduct(toPoint);
		Point3D o = q0;
		if(!isZero(t))
			o = q0.add(dirVector.scale(t));  
		return (point.subtract(o)).normalized(); 
    }

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance)
	{
		// TODO Auto-generated method stub
				return null;
	}
	
}
