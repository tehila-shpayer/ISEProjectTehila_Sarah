package renderer;

import java.util.List;

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
		List<Point3D> intersections = scene.geometries.findIntersections(ray);
		if(intersections == null)
			return scene.background;
		Point3D closestPoint = ray.findClosestPoint(intersections);
		return calcColor(closestPoint);
	}
	
	private Color calcColor(Point3D point3d) {
		return scene.ambientLight.getIntensity();
	}

}
