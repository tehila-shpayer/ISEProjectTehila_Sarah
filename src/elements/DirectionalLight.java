package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight class is a representation class for a light source that is an infinite distance away
 * It extends Light and implements LightSource
 * The directional light is an element in a scene object
 * The directional light is represented by a direction vector.
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */
public class DirectionalLight extends Light implements LightSource {
	
	/**
	 * The direction vector for the directional light object
	 */
	private Vector direction;
	
	/**
	 * Constructor to a directional light object
	 * @param intensity Color - color parameter of directional light source
	 * @param direction Vector - direction parameter of directional light source
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalized();
	}

	/**
	 * Implementation of getIntesity of interface LightSource
	 */
	@Override
	public Color getIntensity(Point3D p) {
		return getIntensity();
	}

	/**
	 * Implementation of getL of interface LightSource
	 * direction of directional light
	 */
	@Override
	public Vector getL(Point3D p) {
		return direction;
	}
	
	/**
	 * Implementation of getDistance of interface LightSource
	 * distance is infinite - const variable Double.POSITIVE_INFINITY
	 */
	@Override
	public double getDistance(Point3D p){
		return Double.POSITIVE_INFINITY;
	}
}
