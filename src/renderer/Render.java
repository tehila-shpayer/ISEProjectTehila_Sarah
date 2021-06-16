package renderer;
import java.util.LinkedList;
import java.util.List;
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
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public class Render {
	
	//N_RENDER - The square root of the number of rays sent through each pixel
	private static final int N_SUPER_SAMPLING = 4;
	private static final int N_DEPTH_OF_FIELD = 150;
	private static final int MAX_LEVEL_ADAPTIVE_SS = 4;
	
	private int threadsCount = 0;
	private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean print = false; // printing progress percentage
	
	private static final String RESOURCE_ERROR = "Renderer resource not set";
	private static final String RENDER_CLASS = "Render";
	private static final String IMAGE_WRITER_COMPONENT = "Image writer";
	private static final String CAMERA_COMPONENT = "Camera";
	private static final String RAY_TRACER_COMPONENT = "Ray tracer";
	
	Camera camera;
	RayTracerBase rayTracerBase;
	ImageWriter imageWriter;


	/**
	 * Set multi-threading <br>
	 * - if the parameter is 0 - number of cores less 2 is taken
	 * 
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Render setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
		if (threads != 0)
			this.threadsCount = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			this.threadsCount = cores <= 2 ? 1 : cores;
		}
		return this;
	}

	/**
	 * Set debug printing on
	 * 
	 * @return the Render object itself
	 */
	public Render setDebugPrint() {
		print = true;
		return this;
	}

	/**
	 * Pixel is an internal helper class whose objects are associated with a Render
	 * object that they are generated in scope of. It is used for multithreading in
	 * the Renderer and for follow up its progress.<br/>
	 * There is a main follow up object and several secondary objects - one in each
	 * thread.
	 * 
	 * @author Dan
	 *
	 */
	private class Pixel {
		private long maxRows = 0;
		private long maxCols = 0;
		private long pixels = 0;
		public volatile int row = 0;
		public volatile int col = -1;
		private long counter = 0;
		private int percents = 0;
		private long nextCounter = 0;

		/**
		 * The constructor for initializing the main follow up Pixel object
		 * 
		 * @param maxRows the amount of pixel rows
		 * @param maxCols the amount of pixel columns
		 */
		public Pixel(int maxRows, int maxCols) {
			this.maxRows = maxRows;
			this.maxCols = maxCols;
			this.pixels = (long) maxRows * maxCols;
			this.nextCounter = this.pixels / 100;
			if (Render.this.print)
				System.out.printf("\r %02d%%", this.percents);
		}

		/**
		 * Default constructor for secondary Pixel objects
		 */
		public Pixel() {
		}

		/**
		 * Internal function for thread-safe manipulating of main follow up Pixel object
		 * - this function is critical section for all the threads, and main Pixel
		 * object data is the shared data of this critical section.<br/>
		 * The function provides next pixel number each call.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return the progress percentage for follow up: if it is 0 - nothing to print,
		 *         if it is -1 - the task is finished, any other value - the progress
		 *         percentage (only when it changes)
		 */
		private synchronized int nextP(Pixel target) {
			++col;
			++this.counter;
			if (col < this.maxCols) {
				target.row = this.row;
				target.col = this.col;
				if (Render.this.print && this.counter == this.nextCounter) {
					++this.percents;
					this.nextCounter = this.pixels * (this.percents + 1) / 100;
					return this.percents;
				}
				return 0;
			}
			++row;
			if (row < this.maxRows) {
				col = 0;
				target.row = this.row;
				target.col = this.col;
				if (Render.this.print && this.counter == this.nextCounter) {
					++this.percents;
					this.nextCounter = this.pixels * (this.percents + 1) / 100;
					return this.percents;
				}
				return 0;
			}
			return -1;
		}

		/**
		 * Public function for getting next pixel number into secondary Pixel object.
		 * The function prints also progress percentage in the console window.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return true if the work still in progress, -1 if it's done
		 */
		public boolean nextPixel(Pixel target) {
			int percent = nextP(target);
			if (Render.this.print && percent > 0)
				synchronized (this) {
					notifyAll();
				}
			if (percent >= 0)
				return true;
			if (Render.this.print)
				synchronized (this) {
					notifyAll();
				}
			return false;
		}

		/**
		 * Debug print of progress percentage - must be run from the main thread
		 */
		public void print() {
			if (Render.this.print)
				while (this.percents < 100)
					try {
						synchronized (this) {
							wait();
						}
						System.out.printf("\r %02d%%", this.percents);
						System.out.flush();
					} catch (Exception e) {
					}
		}
	}

	private void exceptions() {
		if (imageWriter == null)
			throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
		if (camera == null)
			throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
		if (rayTracerBase == null)
			throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

	}

	// ****** Setters ********* //
	// * all setters implements the Builder Design Pattern *//
	
	public Render setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}
	
	public Render setRayTracerBase(RayTracerBase tracer) {
		this.rayTracerBase = tracer;
		return this;
	}
	
	public Render setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}
	
	/**
	 * Cast ray from camera in order to color a pixel
	 * @param nX resolution on X axis (number of pixels in row)
	 * @param nY resolution on Y axis (number of pixels in column)
	 * @param col pixel's column number (pixel index in row)
	 * @param row pixel's row number (pixel index in column)
	 */
	private void castRay(int nX, int nY, int col, int row) {
		Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
		Color color = rayTracerBase.TraceRay(ray);
		imageWriter.writePixel(col, row, color);
	}

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object - with multi-threading
	 */
	private void renderImageThreaded() {
		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();
		final Pixel thePixel = new Pixel(nY, nX);
		// Generate threads
		Thread[] threads = new Thread[threadsCount];
		for (int i = threadsCount - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel))
					castRay(nX, nY, pixel.col, pixel.row);
			});
		}
		// Start threads
		for (Thread thread : threads)
			thread.start();

		// Print percents on the console
		thePixel.print();

		// Ensure all threads have finished
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (Exception e) {
			}

		if (print)
			System.out.print("\r100%");
	}

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object
	 */
	public void renderImage() {
		exceptions();
		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();
		if (threadsCount == 0)
			for (int i = 0; i < nY; ++i)
				for (int j = 0; j < nX; ++j)
					castRay(nX, nY, j, i);
		else
			renderImageThreaded();
	}

   /**
    * The function is responsible of the whole process of rendering the image.
    * It call other function to produce rays, to calculate the color of each pixel, and to write it to the image.
    */
	public void renderImageSuperSumpling() {
		exceptions();		
		int Nx = imageWriter.getNx();
		int Ny = imageWriter.getNy();
		Color color = new Color(0,0,0);
		for(int i = 0; i < Nx; i++) {
			for(int j = 0; j < Ny; j++) {
				for (Ray ray: camera.constructRayThroughPixelSuperSamplingGrid(Nx, Ny, j, i,N_SUPER_SAMPLING))
					color = color.add(rayTracerBase.TraceRay(ray));
				color = color.reduce(N_SUPER_SAMPLING*N_SUPER_SAMPLING);
			    imageWriter.writePixel(j, i, color);
			    }
			}
		}
	
	/**
	 * The function is responsible of the whole process of rendering the image.
     * It call other function to produce rays, to calculate the color of each pixel, and to write it to the image.
     * Implement the improvement of an adaptive Super-Sampling, continue to produce more rays only if the colors in the vertices are not equals.
	 */
	public void renderImageAdaptiveSuperSumpling() {
		exceptions();
		int Nx = imageWriter.getNx();
		int Ny = imageWriter.getNy();
		Color color = new Color(0,0,0);
		for(int i = 0; i < Nx; i++) {
			for(int j = 0; j < Ny; j++) {
				color = CalcColorAdaptive2(camera.calcPIJ(Nx, Ny, j, i), camera.getRx(Nx), camera.getRy(Ny), MAX_LEVEL_ADAPTIVE_SS);
				imageWriter.writePixel(j, i, color);
			}
		}
	}
	
///**
// * * Calculation of color of a specific point from a shooted ray (without ambient light)
//	 * color is calculated based on the implementation of Phong model of light.
//	 * private method - used by main calcColor
//	 * @param point3d - the specific point of which the color is calculated
//	 * @param ray - the shooted ray to the point
//	 * @param level - level of depth in recursion
//	 * @param k - the intensity of impact of secondary rays
//	 * @return Color - final color of point without ambient light
// */
	
	/**
	 * Calculation of color of a specific pixel with the improvement of Super-Sampling: for each pixel, if there is a need, it produce more than one ray
	 * @param pCenter - center of the little pixels in the recursion
	 * @param w - width of current pixel
	 * @param h - height of current pixel
	 * @param level - level of recursion
	 * @param up - 
	 * @param colorList
	 * @return
	 */
	public Color CalcColorAdaptive(Point3D pCenter, double w, double h, int level, boolean up,Color...colorList) {
		var lstc = new LinkedList<Color>();
		var lstcNextIteration = new LinkedList<Color>();
		if(level == MAX_LEVEL_ADAPTIVE_SS) {
			for(Ray ray: camera.constructRayThroughPixelAdaptiveSuperSamplingGridFirstTime(pCenter, w, h)) 
				lstc.add(rayTracerBase.TraceRay(ray));
		}
		lstc.addAll(List.of(colorList));
		
		Color color = new Color(0,0,0);
		if (level == 1) {
			for(Color c: lstc)
				color = color.add(c.reduce(4));
			return color;
		}

		boolean flag = true;
		for(int i=0;i<3; i++) {
	        if(!(lstc.get(i)).equals(lstc.get(i+1)))
	        {
	        	flag = false;
	        	break;
	        }
	    }
		if (flag)
			return lstc.get(0);
				
		for(Ray ray: camera.constructRayThroughPixelAdaptiveSuperSamplingGrid(pCenter, w, h)) 
			lstcNextIteration.add(rayTracerBase.TraceRay(ray));
		
		int index = 0;
		Color centerColor = rayTracerBase.TraceRay(camera.constructRayToPoint(pCenter));
		for(int i = 0;i<2;i++) {
			for(int j = 0;j<2;j++) {
				color = color.add(CalcColorAdaptive(camera.calcPIJ(pCenter, w, h, 2, 2, j, i), w/2, h/2,
						level - 1, i==j, centerColor, lstc.get(index), lstcNextIteration.get(index), lstcNextIteration.get((index+1)%4)).reduce(4));
				index++;
			}
		}
		return color;			
	}

	/**
	 * calcAdaptive2 method in each iteration of the recursion creates a view plane of 2X2,
	 * within the pixel and calculates the 4 centers of the 'mini' pixels.
	 * if the recursion ends(level = 1) it returns the average of the 4 colors,
	 * else for each center it test equality to the main center color, if it is the same,
	 * it adds this color reduced by 4, else it calculates by the recursion.
	 * @param pCenter - the current pixel center
	 * @param w - width of current pixel
	 * @param h - height of current pixel 
	 * @param level - level of recursion
	 * @return the final color of the original pixel.
	 */
	public Color CalcColorAdaptive2(Point3D pCenter, double w, double h, int level) {
		var lstc = new LinkedList<Color>();
		var lstp = new LinkedList<Point3D>();

		for(int a = 0;a<2;a++) {
			for(int b = 0;b<2;b++) {
				Point3D point3d = camera.calcPIJ(pCenter, w, h, 2, 2, b, a);
				lstp.add(point3d);
				lstc.add(rayTracerBase.TraceRay(camera.constructRayToPoint(point3d)));
			}
		}
	
		Color color = new Color(0,0,0);
		if (level == 1) {
			for(Color c: lstc)
				color = color.add(c);
			return color.reduce(4);
		}
		
		Color centerColor = rayTracerBase.TraceRay(camera.constructRayToPoint(pCenter));
		for(int k = 0; k < 4; k++) {
	        if(!(lstc.get(k)).equals(centerColor))
	        	color = color.add(CalcColorAdaptive2(lstp.get(k), w/2, h/2, level - 1).reduce(4));
	        else 
	        	color = color.add(lstc.get(k).reduce(4));
	    }			
		return color;			
	}


	/**
	 * 
	 */
	public void renderImageFocus() {
		exceptions();
		int Nx = imageWriter.getNx();
		int Ny = imageWriter.getNy(); 
		Color color = new Color(0,0,0);
		for(int i = 0; i < Nx; i++) {
			for(int j = 0; j < Ny; j++) {
				var lst = camera.getApertureRays(Nx, Ny, j, i, N_DEPTH_OF_FIELD);
				for (Ray ray: lst)
					color = color.add(rayTracerBase.TraceRay(ray));
				color = color.reduce(N_DEPTH_OF_FIELD*4);
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