package renderer;

import java.util.List;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import static primitives.Util.*;
import scene.Scene;

/**
 * the RayTracerBasic implements the class RayTracerBase and implements the traceRay method.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */
public class RayTracerBasic extends RayTracerBase{
	
	private static final double DELTA = 0.1;
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
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if(intersections == null)
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint, ray);
	}
	
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
		Point3D point = geopoint.point.add(delta);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(geopoint.point));
		return (intersections == null);
	}
	
	private Color calcColor(GeoPoint point3d, Ray ray) {
		return (scene.ambientLight.getIntensity().add(point3d.geometry.getEmission())).add(calcLocalEffects(point3d, ray));
	}
	
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
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
				if (unshaded(lightSource, l, n, intersection))
					{
						Color lightIntensity = lightSource.getIntensity(intersection.point);
						color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
					}
			}
		}
		return color;
	}

	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.subtract(n.scale(l.dotProduct(n)*2)).normalized();
		return lightIntensity.scale(ks* Math.pow((v.scale(-1)).dotProduct(r), nShininess));
	}

	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		return lightIntensity.scale(kd*Math.abs(l.dotProduct(n)));
	}


}
