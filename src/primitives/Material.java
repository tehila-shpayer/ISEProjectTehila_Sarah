package primitives;

/**
 * 
 * @author 
 *
 */
public class Material {
	public double kD = 0;
	public double kS = 0;
	public int nShininess = 0;
	public double kT = 0;
	public double kR = 0;
	
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;


	
	public int getShininess() {
		return nShininess;
	}
	
	public double getKD() {
		return kD;
	}
	
	public double getKS() {
		return kS;
	}
	
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
	
	public Material setkT(double t) {
		kT = t;
		return this;
	}
	
	public Material setkR(double r) {
		kR = r;
		return this;
	}
}
