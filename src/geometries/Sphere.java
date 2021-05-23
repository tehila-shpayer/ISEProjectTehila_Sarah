package geometries;

import java.util.List;


import static primitives.Util.*;

/**
 * Class Sphere is the class representing a sphere in a space of 3 dimensions
 * The Sphere is represented by a center and a radius
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

import primitives.*;

public class Sphere extends Geometry
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

	/**
	 * Returns normal to sphere
	 * @param point3D - normal from that point
	 * @return normalized normal to sphere in point3D
	 */
	@Override
	public Vector getNormal(Point3D point) {
		return (point.subtract(center)).normalized();      // known calculation
	}
	
	@Override
	public String toString() {
		return "center: " + this.center.toString() + ", radius: " + this.radius;
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){
		Point3D rayQ0 = ray.getQ0();
		Vector rayDir = ray.getDir();
		if(rayQ0.equals(center))
			return List.of(new GeoPoint(this, ray.getPoint(radius)));
		Vector q0ToCenter = center.subtract(rayQ0);
		double tm = alignZero(rayDir.dotProduct(q0ToCenter)); ;
		double d = alignZero(Math.sqrt(q0ToCenter.lengthSquared()-tm*tm));
		if(d>=radius)
			return null;
		double th = alignZero(Math.sqrt(radius*radius - d*d));
		double t1 = alignZero(tm - th);
		double t2 = alignZero(tm + th);
		
		List<GeoPoint> res;
		if(t1 <= 0 && t2 <= 0 )
			return null;
		if(t1 <= 0 && t2 > 0)
			res = List.of(new GeoPoint(this,ray.getPoint(t2)));
			
		if(t2 <= 0 && t1 > 0)
			res = List.of(new GeoPoint(this,ray.getPoint(t1)));
		res =  List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));
		
		for (int i = 0; i<res.size(); i++)
		{
			if (alignZero(res.get(i).point.distance(rayQ0) - maxDistance) > 0)
				res.remove(i);
		}
		return res;
		//A list of conditions that verify that the points are indeed intersection points and are within the desired range
		/**if((t1 <= 0 && t2 <= 0 )&&((alignZero(t1 - maxDistance) > 0) && (alignZero(t2 - maxDistance) > 0)))
			return null;
		if((t1 <= 0 && t2 > 0)&&((alignZero(t1 - maxDistance) > 0) && (alignZero(t2 - maxDistance) <= 0)))
			return List.of(new GeoPoint(this,ray.getPoint(t2)));
		if((t2 <= 0 && t1 > 0)&&((alignZero(t1 - maxDistance) <= 0) && (alignZero(t2 - maxDistance) > 0)))
			return List.of(new GeoPoint(this,ray.getPoint(t1)));
		if((t2 > 0 && t1 > 0)&&((alignZero(t1 - maxDistance) <= 0) && (alignZero(t2 - maxDistance) <= 0))) {
			return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));	
		}
		return null;*/
	}
	
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray){
		Point3D rayQ0 = ray.getQ0();
		Vector rayDir = ray.getDir();
		if(rayQ0.equals(center))
			return List.of(new GeoPoint(this, ray.getPoint(radius)));
		Vector q0ToCenter = center.subtract(rayQ0);
		double tm = alignZero(rayDir.dotProduct(q0ToCenter)); ;
		double d = alignZero(Math.sqrt(q0ToCenter.lengthSquared()-tm*tm));
		if(d>=radius)
			return null;
		double th = alignZero(Math.sqrt(radius*radius - d*d));
		double t1 = alignZero(tm - th);
		double t2 = alignZero(tm + th);
		if(t1 <= 0 && t2 <= 0 )
			return null;
		if(t1 <= 0 && t2 > 0)
			return List.of(new GeoPoint(this,ray.getPoint(t2)));
		if(t2 <= 0 && t1 > 0)
			return List.of(new GeoPoint(this,ray.getPoint(t1)));
		return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));		
	}
}


