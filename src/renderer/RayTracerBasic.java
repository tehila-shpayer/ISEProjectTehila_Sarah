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
	//tehila
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geopoint.point, lightDirection, n);
		double lightDistance = light.getDistance(geopoint.point);
		var intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) return 1.0;
		double ktr = 1.0;
		for (GeoPoint gp : intersections) {
			if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
				ktr *= gp.geometry.getMaterial().kT;
				if (ktr < MIN_CALC_COLOR_K) return 0.0;
			}
		}
		return ktr;
	}
	
	private Color calcColor(GeoPoint point3d, Ray ray, int level, double k) {
		Color color = point3d.geometry.getEmission().add(calcLocalEffects(point3d, ray, k));
		if (level == 1 || point3d == null)
			return color;
		return color.add(calcGlobalEffects(point3d, ray, level, k));
	}
		
	private Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
		.add(scene.ambientLight.getIntensity());
	}
	
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v)); 
		if (nv == 0) 
			return Color.BLACK;
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.getShininess();
		double kd = material.getKD();
		double ks = material.getKS();
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
						Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);;
						color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}

	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = calcVectorR(l, n);
		return lightIntensity.scale(ks* Math.pow((v.scale(-1)).dotProduct(r), nShininess));
	}

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
	
	private Vector calcVectorR(Vector v, Vector n) {
		return v.subtract(n.scale(2*v.dotProduct(n))).normalized();
	}
}
