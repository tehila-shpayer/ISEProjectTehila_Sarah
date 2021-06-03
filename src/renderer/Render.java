package renderer;
import java.util.LinkedList;
/**
 * Render class - Creates from the scene the color matrix of the image.
 * The class contain fields of ImageWriter, Scene, Camera and Ray Tracer.
 * The class partially implements the Builder template
 * 
 * @authors Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class Render {
	
	//N_RENDER - The square root of the number of rays sent through each pixel
	private static final int N_RENDER = 4;
	
	Camera camera;
	RayTracerBase rayTracerBase;
	ImageWriter imageWriter;
	
	
	// ****** Setters ********* //
	// * all setters implements the Builder Design Pattern *//
	
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
	
/**
 * The function is responsible of the whole process of rendering the image.
 * It call other function to produce rays, to calculate the color of each pixel, and to write it to the image.
 */
	public void renderImage() {
		if (camera == null)
			throw new MissingResourceException("Render class must have a non-empty camera parameter", "Camera", "" );
		if (rayTracerBase == null)
			throw new MissingResourceException("Render class must have a non-empty rayTracerBase parameter", "RayTracerBase", "" );
		if (imageWriter == null)
			throw new MissingResourceException("Render class must have a non-empty imageWriter parameter", "ImageWriter", "" );
		
		int Nx = imageWriter.getNx();
		int Ny = imageWriter.getNy();
		Color color = new Color(0,0,0);
		for(int i = 0; i < Nx; i++) {
			for(int j = 0; j < Ny; j++) {
				for (Ray ray: camera.constructRayThroughPixelSuperSamplingGrid(Nx, Ny, j, i,N_RENDER))
					color = color.add(rayTracerBase.TraceRay(ray));
				color = color.reduce(N_RENDER*N_RENDER);
				imageWriter.writePixel(j, i, color);
				}
			}
		}
	
	public void renderImageFocus() {
		if (camera == null)
			throw new MissingResourceException("Render class must have a non-empty camera parameter", "Camera", "" );
		if (rayTracerBase == null)
			throw new MissingResourceException("Render class must have a non-empty rayTracerBase parameter", "RayTracerBase", "" );
		if (imageWriter == null)
			throw new MissingResourceException("Render class must have a non-empty imageWriter parameter", "ImageWriter", "" );
		
		int Nx = imageWriter.getNx();
		int Ny = imageWriter.getNy(); 
		Color color = new Color(0,0,0);
		for(int i = 0; i < Nx; i++) {
			for(int j = 0; j < Ny; j++) {
				var lst = camera.getApertureRays(Nx, Ny, j, i);
				for (Ray ray: lst)
					color = color.add(rayTracerBase.TraceRay(ray));
				color = color.reduce(600);
				imageWriter.writePixel(j, i, color);
				}
			}
		}

	
	/**
	 * Creates a grid of lines without overriding the image
	 * gives color only to the grid defects but not to the rest of the pixels.
	 * @param interval - the size of each square in the grid (height and width)
	 * @param color - the color for the grid
	 */
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

	/**
	 * Delegation to the "writeToImage" function in "ImageWriter" class
	 */
	public void writeToImage()
	{
		if (imageWriter == null)
			throw new MissingResourceException("Render class must have a non-empty imageWriter parameter", "ImageWriter", "" );
		imageWriter.writeToImage();
	}
}