package primitives;

import java.util.List;

/**
 * Class Ray is the basic class representing a ray -
 * a direction in space that starts in a specific point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/
public class Ray {

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
	
	public Point3D findClosestPoint(List<Point3D> lst) {
		Point3D closestPoint = lst.get(0);
		for (Point3D p: lst) {
			if(q0.distance(p) < q0.distance(closestPoint))
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
