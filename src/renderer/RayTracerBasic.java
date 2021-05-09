package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

/**
 * the RayTracerBasic implements the class RayTracerBase and implements the traceRay method.
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */
public class RayTracerBasic extends RayTracerBase{
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
		return calcColor(closestPoint);
	}
	
	private Color calcColor(GeoPoint point3d) {
		return scene.ambientLight.getIntensity().add(point3d.geometry.getEmission());
	}

}
