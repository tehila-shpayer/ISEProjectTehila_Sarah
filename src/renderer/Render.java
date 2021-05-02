package renderer;

import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
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
			throw new MissingResourceException("Render class must have a non-empty scene parameter", "Scene", "" );
		if (camera == null)
			throw new MissingResourceException("Render class must have a non-empty camera parameter", "Camera", "" );
		if (rayTracerBase == null)
			throw new MissingResourceException("Render class must have a non-empty rayTracerBase parameter", "RayTracerBase", "" );
		if (imageWriter == null)
			throw new MissingResourceException("Render class must have a non-empty imageWriter parameter", "ImageWriter", "" );
		
		throw new UnsupportedOperationException("This operation is yet to be implemented");
	}
	
	public void printGrid(int interval, Color color)  {
		if(imageWriter == null)
			throw new MissingResourceException("Render can't print grid with empty image writer parameter", "ImageWriter", null);
		for(int i = 0; i< imageWriter.getNx(); i++) {
			for(int j = 0; j < imageWriter.getNy(); j++) {
				if(i % interval==0 || j % interval == 0) 
					imageWriter.writePixel(i, j, color);
			}
		}
	}

	//a function
	public void writeToImage()
	{
		//comments
		if (imageWriter == null)
			throw new MissingResourceException("Render class must have a non-empty imageWriter parameter", "ImageWriter", "" );
	}
}
