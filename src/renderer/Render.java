package renderer;

import java.util.MissingResourceException;

import elements.Camera;
import scene.Scene;

public class Render {
	Scene scene;
	Camera camera;
	RayTracerBase rayTracerBase;
	ImageWriter imageWriter;
	
	public Render setScene(Scene _scene) {
		scene = _scene;
		return this;
	}
	
	public Render setCamera(Camera _camera) {
		camera = _camera;
		return this;
	}
	
	public Render setRayTracerBase(RayTracerBase _rayTracerBase) {
		rayTracerBase = _rayTracerBase;
		return this;
	}
	
	public Render setImageWriter(ImageWriter _imageWriter) {
		imageWriter = _imageWriter;
		return this;
	}
	
	public void renderImage() {
		if (scene == null)
			throw new MissingResourceException("", "", "" );
		if (camera == null)
			throw new MissingResourceException("", "", "" );
		if (rayTracerBase == null)
			throw new MissingResourceException("", "", "" );
		if (imageWriter == null)
			throw new MissingResourceException("", "", "" );
	}

}
