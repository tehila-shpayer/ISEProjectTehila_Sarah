package primitives;

/**
 * Class Material is the class representing the material of the geometry
 * 
 * @authors Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public class Material {
	/**
	 * kD - attenuation factor for Diffuse Reflection- the reflection of light from a surface
	 * kS - attenuation factor for Specular Reflection, the mirror-like reflection of light from a surface.
	 * nShininess - the shining level of the material
	 * kT - Transparency level
	 * kR - level of reflection
	 */
	public double kD = 0;
	public double kS = 0;
	public int nShininess = 0;
	public double kT = 0;
	public double kR = 0;


	// ***************** Getters ********************** //
	public int getShininess() {
		return nShininess;
	}
	
	public double getKD() {
		return kD;
	}
	
	public double getKS() {
		return kS;
	}
	
	public double getkT() {
		return kT;
	}
	
	public double getkR() {
		return kR;
	}
	
	// ***************** Setters ********************** //
		// ** all setters implements the Builder Design Pattern **//
		
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
