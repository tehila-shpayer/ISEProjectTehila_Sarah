package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * SpotLight class is a representation class for a light source that is located in the 
 * the 3D space and its light is directed to a certain direction  
 * It extends PointLight
 * The spot light is an element in a scene object
 * The spot light is represented by a position point, a direction vector and attenuation factors.
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public class SpotLight extends PointLight {
	
	/**
	 * @param direction - direction vector for the light of spot light object on scene
	 */
	private Vector direction;

	/**
	 * ctor for spot light object
	 * @param intensity - intensity of point light source
	 * @param position - location of point light object on scene
	 * @param kC - the constant attenuation factor
	 * @param kL - the linear attenuation factor
	 * @param kQ - the square attenuation factor
	 * @param direction - direction vector for the light of spot light object on scene
	 */
	public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction.normalized();
	}
	
	/**
	 * Default ctor for point light object
	 * attenuation factor get default values - 1,0,0
	 * @param intensity - intensity of point light source
	 * @param position - location of point light object on scene
	 * @param direction - direction vector for the light of spot light object on scene
	 */

	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalized();
	}
	
	/**
	 * Implementation of getIntesity of interface LightSource
	 * intensity multiplied with the attenuation coefficients of point light
	 * and if the multiplication of l*v is positive - multiply by it 
	 */
	@Override
	public Color getIntensity(Point3D p) {
		double dotProduct = Util.alignZero(direction.dotProduct(getL(p)));
		if (dotProduct < 0)
			dotProduct = 0;
		return super.getIntensity(p).scale(dotProduct);
	}
}
