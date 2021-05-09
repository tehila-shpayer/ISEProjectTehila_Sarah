package primitives;

/**
 * 
 * @author tehil
 *
 */
public class Material {
	public double kD = 0;
	public double kS = 0;
	public int nShininess = 0;
	
	public Material setkD(double d) {
		kD = d;
		return this;
	}

	public Material setkS(double s) {
		kS = s;
		return this;
	}
	
	public Material setnShininess(int n) {
		nShininess = n;
		return this;
	}
}
