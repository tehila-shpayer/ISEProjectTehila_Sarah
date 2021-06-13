package elements;

import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import geometries.Geometry;
import geometries.Plane;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Class Camera is the class representing a camera in a space of 3 dimensions, 
 * in order to create a scene for the picture in a space of 3 dimensions.
 * The camera is represented by a location and 3 direction vectors.
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

public class Camera {
		
	/**
	 * aperture: aperture part of camera - to generate focus in picture
	 */
	Aperture aperture;
		
	/**
	 * location: center point of camera
	 */
	Point3D location;
	
	/**
	 * vUp: Y axis of camera's 3D axis system - direction vector
	 */
	Vector vUp;
	
	/**
	 * vTo: Z axis of camera's 3D axis system - direction vector
	 */
	Vector vTo;
	
	/**
	 * vRight: X axis of camera's 3D axis system - direction vector
	 */
	Vector vRight;
	
	/**
	 * view plane parameter: width of the view plane in specific length unit
	 */
	double width;
	
	/**
	 * view plane parameter: height of the view plane in specific length unit
	 */
	double height;
	
	/**
	 * view plane parameter: distance of view plane from the camera
	 */
	double distance;
	
	/**
	 * constructor for camera
	 * @param _location: center point of camera's location
	 * @param _vTo: Z axis
	 * @param _vUp: Y axis
	 */
	public Camera(Point3D _location, Vector _vTo, Vector _vUp) {
		if(!isZero(_vTo.dotProduct(_vUp)))
			throw new IllegalArgumentException("vUp and vTo direction vectors of camera are not orthogonal");
		
		location = _location;
		vUp = _vUp.normalized();
		vTo = _vTo.normalized();
		vRight = _vTo.crossProduct(_vUp).normalized();
	}
	
	/**
	 * sets view plane size - width and height in a builder pattern form
	 * @param _width: width of view plane
	 * @param _height: height of view plane
	 * @return this, the camera with new data
	 */
	public Camera setViewPlaneSize(double _width, double _height) {
		if(_width <= 0 || _height <= 0)
			throw new IllegalArgumentException("width and height of the view plane must be positive");
		width = _width;
		height = _height;
		return this;
	}
	
	/**
	 * sets aperture information - length, focal distance
	 * @param d: distance of focal plane from view plane  
	 * @return this, the camera with new data
	 */
	public Camera setAperture(double d, double l) {
		if(d <= 0 || l <= 0)
			throw new IllegalArgumentException("width and height of the view plane must be positive");
		aperture = new Aperture(d, l);
		return this;
	}
	
	/**
	 * sets view plane information - distance
	 * @param _distance: distance of view plane from camera
	 * @return this, the camera with new data
	 */
	public Camera setDistance(double _distance) {
		distance = _distance;
		return this;
	}
	
	public double getRx(int nX) {
		return width / nX;
	}
	
	public double getRy(int nY) {
		return height / nY;
	}
	/**
	 * Construction of ray through pixel in the view plane from the camera
	 * @param nX - number of pixel per row 
	 * @param nY - number of pixel per column
	 * @param j - location of pixel on axis X
	 * @param i - location of pixel on axis Y
	 * @return the construct ray from camera's location to pixel i,j 
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D pIJ = calcPIJ(nX, nY, j, i);
		return new Ray(location, pIJ.subtract(location));  
	}
	
	/**
	 * The function get the dimension of a view plane and a central point and i,j indexes of one pixel in the view plane.
	 * It calculates the central point of the given pixel.
	 * @param pCenter - the central point of the current view plane
	 * @param width - the width of the current view plane
	 * @param height - the height of the current view plane
	 * @param nX - number of pixel per row 
	 * @param nY - number of pixel per column
	 * @param j - location of pixel on axis X
	 * @param i - location of pixel on axis Y
	 * @return the center point of the pixel
	 */
	public Point3D calcPIJ(Point3D pCenter, double width, double height, int nX, int nY, int j, int i) {
		double Ry = height / nY;
		double Rx = width / nX;
		double yi = -(double)(i - ((nY - 1) /(double)2)) * Ry;
		double xj = (double)(j - (nX - 1) /(double)2) * Rx;
		Point3D pIJ = pCenter;
		if (xj != 0) pIJ = pIJ.add(vRight.scale(xj));
		if (yi != 0) pIJ = pIJ.add(vUp.scale(yi));
		return pIJ;  
	}
	
	public Point3D calcPIJ(int nX, int nY, int j, int i) {
		return calcPIJ(getPCenter(), width, height, nX, nY, j, i);
	}
	/**
	 * The function construct a beam of rays from the camera to one pixel
	 * Using the Grid System
	 * @param nX - number of pixel per row 
	 * @param nY - number of pixel per column
	 * @param j - location of pixel on axis X
	 * @param i - location of pixel on axis Y
	 * @param N - The square root of the number of rays sent through each pixel
	 * @return a beam of rays from the camera trough the pixel
	 */
	public List<Ray> constructRayThroughPixelSuperSamplingGrid(int nX, int nY, int j, int i, int N) {		
		Point3D pCenter = getPCenter();
		Point3D pIJ = calcPIJ(pCenter, width, height, nX, nY, j, i);
		double Ry = height / nY;
		double Rx = width / nX;
		Ray ray;
		var lst = new LinkedList<Ray>();
		//Divides the pixel into a grid
		for(int Pi = 0; Pi < N; Pi++) {
			for(int Pj = 0; Pj < N; Pj++) {
				//for each "mini pixel" in the grid, we send a ray from the camera trough it
				ray = constructRaysThroughPixel(Ry, Rx, N, Pj, Pi, pIJ);
				if(ray != null)
					lst.add(ray);
			}
		}
	    return lst;		
	}
	
	public List<Ray> constructRayThroughPixelAdaptiveSuperSamplingGrid(Point3D pCenter, double w, double h) {	
		var lstr = new LinkedList<Ray>();
		var lstp = new LinkedList<Point3D>();
		Point3D p1 = pCenter.add(vRight.scale(-w/2)).add(vUp.scale(h/2));
		Point3D p2 = pCenter.add(vRight.scale(w/2)).add(vUp.scale(h/2));
		Point3D p3 = pCenter.add(vRight.scale(w/2)).add(vUp.scale(-h/2));
		Point3D p4 = pCenter.add(vRight.scale(-w/2)).add(vUp.scale(-h/2));
		lstp.addAll(List.of(p1,p2,p3,p4));
		for(Point3D p: lstp)
			lstr.add(new Ray(location, p.subtract(location)));
		return lstr;
	}
	
	/**
	 * The function construct a beam of rays from the camera to one pixel
	 * Using the Random System
	 * @param nX - number of pixel per row 
	 * @param nY - number of pixel per column
	 * @param j - location of pixel on axis X
	 * @param i - location of pixel on axis Y
	 * @param N - The square root of the number of rays sent through each pixel
	 * @return a beam of rays from the camera trough the pixel
	 */
	public List<Ray> constructRayThroughPixelSuperSamplingRandom(int nX, int nY, int j, int i, int N) {
		Point3D pCenter =getPCenter();
		double Ry = height / nY;
		double Rx = width / nX;
		double yi = -(double)(i - ((nY - 1) /(double)2)) * Ry;
		double xj = (double)(j - (nX - 1) /(double)2) * Rx;
		Point3D pIJ = pCenter;
		if (xj != 0) pIJ = pIJ.add(vRight.scale(xj));
		if (yi != 0) pIJ = pIJ.add(vUp.scale(yi));
		var lst = new LinkedList<Ray>();
		lst.addAll(List.of(new Ray(location, pIJ.subtract(location))));
		Random rand = new Random();
		for(int Pi = 0; Pi < N; Pi++) {
			for(int Pj = 0; Pj < N; Pj++) {

		        // Generate Random doubles
		        double dX = rand.nextDouble();
		        double dY = rand.nextDouble();
		        boolean bX = rand.nextBoolean();
		        boolean bY = rand.nextBoolean();
		        dX = bX? dX : -dX;
		        dY = bY? dY : -dY;
		        yi = Ry/2 * dY;
				xj = Rx/2 *dX;
		        Point3D rndP = pIJ.add(vRight.scale(yi));
				rndP = rndP.add(vUp.scale(xj));
				
				lst.add(new Ray(location, rndP.subtract(location)));
			}
		}
	    return lst;
	}
	
	/**
	 * The method constructs rays from the aperture to the focal point in the scene.
	 * First we calculate the 4 vertices of the aperture,
	 * with random numbers we find more points on the aperture's edges -
	 * From each point we construct a ray to the focal point.
     * @param nX - number of pixel per row 
	 * @param nY - number of pixel per column
	 * @param j - location of pixel on axis X
	 * @param i - location of pixel on axis Y
	 * @return all calculated aperture rays for pixel i,j
	 */
	public List<Ray> getApertureRays(int nX, int nY, int j, int i, int N) {
		Ray ray = constructRayThroughPixel(nX, nY, j, i);
		Point3D pcenter = calcPIJ(getPCenter(), width, height, nX, nY, j, i);
		Point3D pointFocalPlane = pcenter.add(vTo.scale(aperture.distanceToFocal));
		Plane focalPlane = new Plane(pointFocalPlane, vTo);
		Point3D focalPoint = focalPlane.findGeoIntersections(ray).get(0).point;
		double l = aperture.length;
		pcenter = location;
		var lstr = new LinkedList<Ray>();
		var lstp = new LinkedList<Point3D>();
		Random rand = new Random();
		Point3D p1 = pcenter.add(vRight.scale(-l/2)).add(vUp.scale(l/2));
		Point3D p2 = pcenter.add(vRight.scale(l/2)).add(vUp.scale(l/2));
		Point3D p3 = pcenter.add(vRight.scale(l/2)).add(vUp.scale(-l/2));
		Point3D p4 = pcenter.add(vRight.scale(-l/2)).add(vUp.scale(-l/2));
		lstp.addAll(List.of(p1, p2, p3, p4)); 
		for (int a= 0;a<N/4; a++) {
			double dX = rand.nextDouble()*l;
			if(dX != 0)
			{			
				lstp.add(p1.add(vRight.scale(dX)));
				lstp.add(p2.add(vUp.scale(-dX)));
				lstp.add(p3.add(vRight.scale(-dX)));
				lstp.add(p4.add(vUp.scale(dX)));
			}
			else {
				a--;
			}
		}
		for (Point3D p: lstp)
			lstr.add(new Ray(p,focalPoint.subtract(p)));
		return lstr;
	}

	/**
	 * get the dimension of one pixel and the index of the current "mini-pixel" in the grid and construct a ray through it
	 * @param width - the width of the current view plane
	 * @param height - the height of the current view plane
	 * @param N - The Resolution
	 * @param j - location of pixel on axis X
	 * @param i - location of pixel on axis Y
	 * @param point3d - the central point of the original pixel
	 * @return a ray from the camera to the pixel according to the specific indexes
	 */
	public Ray constructRaysThroughPixel(double width, double height, int N, int j, int i, Point3D pCenter) {
		Point3D pIJ = calcPIJ(pCenter, width, height, N, N, j, i);
		return new Ray(location, pIJ.subtract(location));  
	}
	
	/**
	 * 
	 * @return  f
	 */
	public Point3D getPCenter() { return location.add(vTo.scale(distance));
 }
}