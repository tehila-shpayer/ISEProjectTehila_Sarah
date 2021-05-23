package geometries;

import primitives.*;
/**
 * Interface Geometry is to represent geometric shapes 
 * the shapes must have a normal vector in to each point in the Cartesian 3D coordinate system. 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public abstract class Geometry implements Intersectable{
	protected Color emission = Color.BLACK;
	private Material material = new Material();
	/**
	 * Simple getter to Emission param.
	 * @return Emission param. (Color)
	 */
	public Color getEmission() {
		return this.emission;
	}
	
	/**
	 * Simple getter to Material param.
	 * @return Material param. (Color)
	 */
	public Material getMaterial() {
		return this.material;
	} 
	
	/**
	 * Builder pattern setter to Emission param.
	 * @param color - Emission color of geometry
	 * @return Geometry object
	 */
	public Geometry setEmission(Color color) {
		this.emission = color;
		return this;
	}

	/**
	 * Builder pattern setter to Material param.
	 * @param material - Material settings of geometry
	 * @return Geometry object
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}
	
	/**
	 * abstract method getNormal for extending classes
	 * @param point - the specific point
	 * @return Vector normal to specific point
	 */
	public abstract Vector getNormal(Point3D point);
}

