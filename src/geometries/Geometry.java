package geometries;

import primitives.*;
/**
 * Interface Geometry is to represent geometrical shapes 
 * the shapes must have a normal vector in to each point in the Cartesian 3D coordinate system. 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public interface Geometry extends Intersectable{
	public Vector getNormal(Point3D point);
}

