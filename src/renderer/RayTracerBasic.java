package renderer;

import java.util.List;
import static primitives.Util.*;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;
/**
 * the RayTracerBasic implements the class RayTracerBase and implements the traceRay method.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */
public class RayTracerBasic extends RayTracerBase{
	
	private static final double INITIAL_K = 1.0;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	/**
	 * ctor - initializing the scene parameter
	 * uses super ctor
	 * @param _scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	
	/**
	 * implementation of super class trace ray method
	 */
	public Color TraceRay(Ray ray) {
		GeoPoint clossestGeoPoint = findClosestIntersection(ray);
		if (clossestGeoPoint == null)
			return scene.background;
		return calcColor(clossestGeoPoint, ray);
	}
	
	/**
	 * Calculation of intensity of shadow on pixel's color with consideration of the transparency
	 * @param light - Light source that effects the color and thus the shadow
	 * @param l - Vector from light source to point
	 * @param n - Normal to point
	 * @param geopoint - The point of which the color is calculated
	 * @return - The attenuation coefficient of light - determines the intensity of the shadow 
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geopoint.point, lightDirection, n); //build ray with delta
		double lightDistance = light.getDistance(geopoint.point);
		var intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) return 1.0; //no intersections
		double ktr = 1.0;
		for (GeoPoint gp : intersections) {
			if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
				ktr *= gp.geometry.getMaterial().kT; //the more transparency the less shadow
				if (ktr < MIN_CALC_COLOR_K) return 0.0;
			}
		}
		return ktr;
	}
	
	/**
	 * Calculation of color of a specific point from a shooted ray (without ambient light)
	 * color is calculated based on the implementation of Phong model of light.
	 * private method - used by main calcColor
	 * @param point3d - the specific point of which the color is calculated
	 * @param ray - the shooted ray to the point
	 * @param level - level of depth in recursion
	 * @param k - the intensity of impact of secondary rays
	 * @return Color - final color of point without ambient light
	 */
	private Color calcColor(GeoPoint point3d, Ray ray, int level, double k) {
		Color color = point3d.geometry.getEmission().add(calcLocalEffects(point3d, ray, k));
		if (level == 1 || point3d == null)
			return color;
		return color.add(calcGlobalEffects(point3d, ray, level, k));
	}
	
	/**
	 * Calculation of color of a specific point from a shooted ray (with ambient light)
	 * color is calculated based on the implementation of Phong model of light.
	 * private method - main method of color calculation - used by trace ray
	 * @param geopoint - the specific point of which the color is calculated
	 * @param ray - the shooted ray to the point
	 * @return Color - final color of point with ambient light
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
		.add(scene.ambientLight.getIntensity());
	}
	
	/**
	 * calculation of local effects on the color
	 * such as - lights, diffusion and spacularation of material 
	 * @param intersection - the specific point of which the color is calculated
	 * @param ray - the shooted ray to the point
	 * @param k - the intensity of impact of secondary rays (transparency)
	 * @return Color - the local effects - all lights on scene and material characteristics
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point); //normal to point
		double nv = alignZero(n.dotProduct(v)); 
		if (nv == 0) //vectors orthogonal - no effect
			return Color.BLACK;
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.getShininess();
		double kd = material.getKD();
		double ks = material.getKS();
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) { //sum of all effects of all lights on scene
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l)); 
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				double ktr = transparency(lightSource, l, n, intersection); //intensity of shadow
				if (ktr * k > MIN_CALC_COLOR_K) {
						Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);;
						color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}

	/**
	 * Calculation of specular light component 
	 * @param ks - Attenuation coefficient for specular light component
	 * @param l - direction vector from light to point
	 * @param n - normal to point
	 * @param v - direction of ray shooted to point
	 * @param nShininess -  Light is exponentially reduced (at the order of nShininess - the object’s shininess )
	 * @param lightIntensity - the color and intensity of light source
	 * @return Color - the calculated color of specular light component
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = calcVectorR(l, n);
		return lightIntensity.scale(ks* Math.pow((v.scale(-1)).dotProduct(r), nShininess));
	}

	/**
	 * Calculation of diffusion light component 
	 * @param kd - Attenuation coefficient for diffusion light component
	 * @param l - direction vector from light to point
	 * @param n - normal to point
	 * @param lightIntensity - the color and intensity of light source
	 * @return Color - the calculated color of diffusion light component
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		return lightIntensity.scale(kd*Math.abs(l.dotProduct(n)));
	}
//sara
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
		Color color = Color.BLACK;
		Material material = geopoint.geometry.getMaterial();
		double kr = material.kR;
		double kkr = k * kr;
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, geopoint.point, ray);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if(reflectedPoint == null) return color.add(scene.background);
			color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
		}
		double kt = material.kT;
		double kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, geopoint.point, ray);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if(refractedPoint == null) return color.add(scene.background);
			color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
		}
		return color;
	} 

	private GeoPoint findClosestIntersection(Ray refractedRay) {
		return refractedRay.findClosestGeoPoint(scene.geometries.findGeoIntersections(refractedRay));
	
	}

	private Ray constructRefractedRay(Vector n, Point3D point, Ray ray) {
		Vector v = new Ray(point, ray.getDir(), n).getDir();
		double cosi = v.scale(-1).dotProduct(n);
		double cosr = n.scale(-1).dotProduct(v);
		Vector direction;
		if (!isZero(cosr-cosr))
			direction = (n.scale(cosi-cosr)).subtract(v);
		else
			direction = ray.getDir();
		Ray refractedRay = new Ray(point, direction, n);
		return refractedRay;
	}

	private Ray constructReflectedRay(Vector n, Point3D point, Ray ray) {
		Vector v = ray.getDir();
		if(isZero(v.dotProduct(n)))
			return new Ray(point, v);
		Vector vector = calcVectorR(v, n);
		Ray reflectedRay = new Ray(point, vector, n);
		return reflectedRay;
	}
	
	/**
	 * 
	 * @param v - the direction of the ray which intersect the surface
	 * @param n - normal to the intersection point on the surface
	 * @return the r vector - the vector obtained from returning the vector from the ray to the surface at the angle at which it arrived
	 */
	private Vector calcVectorR(Vector v, Vector n) {
		return v.subtract(n.scale(2*v.dotProduct(n))).normalized();
	}
}
