package geometries;

import static primitives.Util.isZero;
import primitives.*;
import primitives.Util.*;

/**
 * Class Cylinder is the basic class representing a geometrical shape of cylinder
 *  - a surface consisting of all the points on all the lines which are parallel to a given line (axis ray)
 * and distanced from it at a fixed radius in a Cartesian 3-Dimensional coordinate system.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

public class Cylinder extends Tube {

	double height;
	
	/**
	 * This constructor receives  all cylinder parameters - center ray, radius and height
	 * @param axisRay - ray from center of the Tube
	 * @param radius - the distance between axis ray and the shell of the Tube
	 * @param _height - length of the Tube
	 */
	public Cylinder(Ray axisRay, double radius, double _height) {
		super(axisRay, radius);
		this.height = _height;
	}
	
	/**
	 * Returns the protected parameter height of the tube
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Height: " + this.height;
	}
	
	
	public Vector getNormal(Point3D point3D) {
		Point3D q0 = axisRay.getQ0();
		Vector dirVector = axisRay.getDir();
		Point3D secondBasePoint = q0.add(dirVector.scale(height));
		if(isZero((q0.subtract(point3D)).dotProduct(dirVector))||
				isZero((secondBasePoint.subtract(point3D)).dotProduct(dirVector)))
			return dirVector;
		return super.getNormal(point3D);
	}
}
