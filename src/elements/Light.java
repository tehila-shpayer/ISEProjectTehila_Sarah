package elements;

import primitives.Color;

public abstract class Light {
	/**
	 * the intensity is the outcome color of the ambient light
	 */
	private Color intensity;

	/**
	 * 
	 * @param intensity
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
