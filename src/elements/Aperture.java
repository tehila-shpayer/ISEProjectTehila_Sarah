package elements;

import primitives.Point3D;

/**
 * class Aperture is a piece in the camera 
 * used to determine the focus factor of the objects in the scene 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */
public class Aperture {
	/**
	 * @param distance from the view plane to the focal plane
	 */
	double distanceToFocal;
	
	/**
	 * @param length - length is the width and height of the aperture
	 */
	double length;
	
	/**
	 * ctor to the Aperture class
	 * @param distance - distance from the view plane to the focal plane
	 * @param length - length is the width and height of the aperture
	 */
	public Aperture(double distance, double length) {
		this.distanceToFocal = distance;
		this.length = length;
	}
}
