package geometries;

/**
 * Class Sphere is the class representing a sphere in a space of 3 dimensions
 * The Sphere is represented by a center and a radius
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

import primitives.*;

public class Sphere implements Geometry
{
	/**
     * center - the center point of the sphere
     * radius - the radius of the sphere
     */
	Point3D center;
	double radius;
	
	/**
     * Sphere constructor receiving 2 values
     * 
     * @param center - the center point of the sphere
     * @param radius - the radius of the sphere
     */
	public Sphere(Point3D center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	/**
     * Returns the field center of the sphere
     * @return center - the center point of the sphere
     */
	public Point3D getCenter() {
		return center;
	}
	
	/**
     * Returns the field radius of the sphere
     * @return radius - the radius of the sphere
     */
	public double getRadius() {
		return radius;
	}


	@Override
	public Vector getNormal(Point3D point) {
		return (point.subtract(center)).normalized();
	}
	
	@Override
	public String toString() {
		return "center: " + this.center.toString() + ", radius: " + this.radius;
	}
}


