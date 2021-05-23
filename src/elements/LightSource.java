package elements;
import primitives.*;
/**
 * LightSource class represents light sources that can have
 * their distance to point, vector to point and intensity of point calculated
 * @author tehil
 *
 */
public interface LightSource {

	/**
	 * intensity of specific point effected with light source
	 * @param p
	 * @return
	 */
	public Color getIntensity(Point3D p);
	
	/**
	 * Vector l is the vector from light source location to point
	 * @param p - point
	 * @return  - Vector l
	 */
	public Vector getL(Point3D p);
	
	/**
	 * Calculates the distance between the light source and the point
	 * @param p - point
	 * @return - distance to point (double)
	 */
	public double getDistance(Point3D p);

}
