package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * Class Ray is the basic class representing a ray -
 * a direction in space that starts in a specific point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/
public class Ray {

	/**
	 * DELTA - Determines the distance the head of the ray moves in the constructor that receive a normal
     * q0 - the ray head
     * dir - the direction of the ray
     */
	private static final double DELTA = 0.1;

	Point3D q0;
	Vector dir;
	
	/**
	 * The constructor receives a 3D point and a vector
	 * @param point3d - determines the starting point of the ray 
	 * @param dirVector - determines the direction of the ray 
	 */
	public Ray(Point3D point3d, Vector dirVector) {
		this.q0 = point3d;
		this.dir = dirVector.normalized();
	}
	
	/**
	 * The constructor receives a 3D point, a vector and a normal.
	 * It creates a ray using the direction vector and the point, but moves the point by DELTA in the normal direction.
	 * The reason: to avoid self-shadowing and self-reflection
	 * @param head - the basic head point
	 * @param direction - ray direction
	 * @param normal - the normal to the point on the geometry
	 */
	public Ray(Point3D head, Vector direction, Vector normal) {
		Vector dVector = direction.normalized();
		Vector delta = normal.scale(normal.dotProduct(dVector) > 0 ? DELTA : - DELTA);
		Point3D point = head.add(delta);
		this.q0 = point;
		this.dir = dVector;
		
	}
	
	/**
	 * Returns the starting point of the ray
	 * @return the point q0
	 */
	public Point3D getQ0() {
		return q0;
	}
	
	/**
	 * Returns the direction vector of the ray
	 * @return dir - direction vector
	 */
	public Vector getDir() {
		return dir;
	}
	
	public Point3D getPoint(double t) {
		return q0.add(dir.scale(t));
	}
	
	/**
	 * The function receive a list of point,
	 * calculates the distances of each one from the ray and return the closest one
	 * @param lst - a list of intersection point between the ray and the geometries
	 * @return closestPoint - the closest point to the ray
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> lst) {
		if(lst == null)
			return null;
		GeoPoint closestPoint = lst.get(0);
		for (GeoPoint p: lst) {
			if(q0.distance(p.point) < q0.distance(closestPoint.point))
				closestPoint = p;
		}
		return closestPoint;	
	}

	@Override
	public boolean equals(Object obj) {
	   if (this == obj) return true;
	   if (obj == null) return false;
	   if (!(obj instanceof Ray)) return false;
	   Ray other = (Ray)obj;
	   return this.q0.equals(other.q0) && this.dir.equals(other.dir);
	}
	
	@Override
	public String toString() {
		return "q0: " + this.q0.toString() + " dir: " + this.dir.toString();
	}
}
