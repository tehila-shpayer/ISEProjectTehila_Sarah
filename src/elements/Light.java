package elements;

import primitives.Color;

/**
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */
public abstract class Light {
	/**
	 * the intensity is the color of light
	 */
	private Color intensity;

	/**
	 * constructor of a light object
	 * @param intensity - color of light
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}
	
	/**
	 * simple intensity getter
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
	
	


}
