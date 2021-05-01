package elements;
import primitives.*;

public class AmbientLight {
	
	Color intensity;
	public AmbientLight(Color color, double kA) {
		intensity = color.scale(kA);
	}
	
	public Color getIntensity() {
		return intensity;
	}
}
