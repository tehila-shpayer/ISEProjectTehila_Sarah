package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

	private Point3D position;
	private double kC;
	private double kL;
	private double kQ;
	

	
	public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
		super(intensity);
		this.position = position;
		this.kC = kC;
		this.kL = kL;
		this.kQ = kQ;
	}
	
	public PointLight(Color intensity, Point3D position) {
		super(intensity);
		this.position = position;
		this.kC = 1;
		this.kL = 0;
		this.kQ = 0;
	}

	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}

	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}
	
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	@Override
	public Color getIntensity(Point3D p) {
		return getIntensity().scale(calcScalar(p));
	}

	@Override
	public Vector getL(Point3D p) {
		return (p.subtract(position)).normalized();
	}
	
	public double calcScalar(Point3D p) {
		double d = p.distance(position);
		return  (double)(1/(double)(kC+ kL*d + kQ*d*d));
	}
	
	@Override
	public double getDistance(Point3D p){
		return position.distance(p);
	}
}
