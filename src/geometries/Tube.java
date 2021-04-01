package geometries;

import primitives.*;

/**
 * Class Tube is the basic class representing an infinite cylinder by an axis ray and a radius
 * of Euclidean geometry in Cartesian 3-Dimensional coordinate system.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

public class Tube implements Geometry {
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
	public String toString()
	{
		return "Axis Ray: " + this.axisRay.toString() + " Radius: " + this.radius + "";
	}
	
	@Override
	public Vector getNormal(Point3D point)
	{
		return null;
    }
	
}
