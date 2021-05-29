package elements;

import primitives.Point3D;

public class Aperture {
	double distanceToFocal;
	double length;
	
	public Aperture(double distance, double length) {
		this.distanceToFocal = distance;
		this.length = length;		
	}
}
