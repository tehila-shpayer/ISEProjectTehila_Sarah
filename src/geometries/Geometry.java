package geometries;

import primitives.*;
/**
 * Interface Geometry is to represent geometrical shapes 
 * the shapes must have a normal vector in to each point in the Cartesian 3D coordinate system. 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public abstract class Geometry implements Intersectable{
	protected Color emission = Color.BLACK;
	private Material material = new Material();
	/**
	 * javadoc com.
	 * @return
	 */
	public Color getEmission() {
		return this.emission;
	}
	
	public Material getMaterial() {
		return this.material;
	} 
	
	/**
	 * javadoc com.
	 * @param color
	 * @return
	 */
	public Geometry setEmission(Color color) {
		this.emission = color;
		return this;
	}
	
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}
		
	public abstract Vector getNormal(Point3D point);
}

