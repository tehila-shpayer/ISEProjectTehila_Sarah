package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * PointLight class is a representation class for a light source that is located in the 
 * the 3D space and is spread equally to all directions 
 * It extends Light and implements LightSource
 * The point light is an element in a scene object
 * The point light is represented by a position point and attenuation factors.
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */
public class PointLight extends Light implements LightSource {

	/**
	 * @param position - location of point light object on scene
	 */
	private Point3D position;
	
	/**
	 * @param kC - the constant attenuation factor
	 */
	private double kC;
	
	/**
	 * @param kL - the linear attenuation factor
	 */
	private double kL;
	
	/**
	 * @param kQ - the square attenuation factor
	 */
	private double kQ;
	

	/**
	 * ctor for point light object
	 * @param intensity - intensity of point light source
	 * @param position - location of point light object on scene
	 * @param kC - the constant attenuation factor
	 * @param kL - the linear attenuation factor
	 * @param kQ - the square attenuation factor
	 */
	public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
		super(intensity);
		this.position = position;
		this.kC = kC;
		this.kL = kL;
		this.kQ = kQ;
	}
	
	/**
	 * Default ctor for point light object
	 * attenuation factor get default values - 1,0,0
	 * @param intensity - intensity of point light source
	 * @param position - location of point light object on scene
	 */
	public PointLight(Color intensity, Point3D position) {
		super(intensity);
		this.position = position;
		this.kC = 1;
		this.kL = 0;
		this.kQ = 0;
	}

	/**
	 * builder pattern set - set the kC parameter - the constant attenuation factor
	 * @param kC - constant attenuation factor
	 * @return - point light object  
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * builder pattern set - set the kL parameter - the linear attenuation factor
	 * @param kL - linear attenuation factor
	 * @return - point light object  
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}
	
	/**
	 * builder pattern set - set the kq parameter - the square attenuation factor
	 * @param kQ - square attenuation factor
	 * @return - point light object  
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	/**
	 * Implementation of getIntesity of interface LightSource
	 * intensity multiplied with the attenuation coefficients of point light
	 */
	@Override
	public Color getIntensity(Point3D p) {
		return getIntensity().scale(calcScalar(p));
	}

	/**
	 * Implementation of getL of interface LightSource
	 * The vector from position point to specific point.
	 */
	@Override
	public Vector getL(Point3D p) {
		return (p.subtract(position)).normalized();
	}
	
	/**
	 * private method for DRY principle 
	 * calculation of scalar attenuation factor
	 * 1/ kc + kl*d + kq*d^2
	 * @param p - specific point
	 * @return scalar attenuation factor (double)
	 */
	public double calcScalar(Point3D p) {
		double d = p.distance(position);
		return  (double)(1/(double)(kC+ kL*d + kQ*d*d));
	}
	
	/**
	 * Implementation of getDistance of interface LightSource
	 * The distance from position to specific point.
	 */
	@Override
	public double getDistance(Point3D p){
		return position.distance(p);
	}
}
