package geometries;

import primitives.*;
import java.util.List;
/**
 * Interface Intersectable is to represent intersectable shapes 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public interface Intersectable {
	/**
	 * The method receives a ray and returns all intersection points with the geometry
	 * @param ray
	 * @return list of intersection points
	 */
	List<Point3D> findIntersections(Ray ray);

}
