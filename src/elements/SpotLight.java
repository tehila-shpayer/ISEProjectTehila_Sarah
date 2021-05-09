package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {
	private Vector direction;

	public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction;
	}
	
	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction;
	}
	
	@Override
	public Color getIntensity(Point3D p) {
		double dotProduct = direction.dotProduct(getL(p));
		if (dotProduct < 0)
			return Color.BLACK;
		return getIntensity().scale(dotProduct/(double)calcScalar(p));
	}
}
