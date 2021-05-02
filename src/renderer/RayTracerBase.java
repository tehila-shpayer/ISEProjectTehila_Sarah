package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
	protected Scene scene;
	
	public RayTracerBase(Scene _scene) {
		scene = _scene;
	}
	
	public abstract Color TraceRay(Ray ray);
}
