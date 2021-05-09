package elements;
import primitives.*;

/**
 * Class AmbientLight is the class representing the default color of an image, 
 * the ambient light color is needed in order to distinguish between the background and the objects on the scene.
 * The ambient light is represented by the wrapper class Color.
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/
public class AmbientLight extends Light{
	
	/**
	 * the ctor creates the intensity by multiplying the given color with the given attenuation factor.
	 * the result is sent to the super ctor
	 * @param color - the color
	 * @param kA - the attenuation factor
	 */
	public AmbientLight(Color color, double kA) {
		super(color.scale(kA));
	}
	
	/**
	 * Default constructor
	 * initialize the intensity with black
	 */
	public AmbientLight() {
		super(Color.BLACK);
	}
}
