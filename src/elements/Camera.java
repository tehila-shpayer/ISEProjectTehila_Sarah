package elements;

import primitives.Point3D;
import primitives.Vector;
import static primitives.Util.*;
import primitives.*;
import static java.lang.System.out;


/**
 * Class Camera is the class representing a camera in a space of 3 dimensions, 
 * in order to create a scene for the picture in a space of 3 dimensions.
 * The camera is represented by a location and 3 direction vectors.
 * 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
*/

public class Camera {
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
	
	public Camera(Point3D _location, Vector _vTo, Vector _vUp) {
		if(!isZero(_vTo.dotProduct(_vUp)))
			throw new IllegalArgumentException("vUp and vTo direction vectors of camera are not orthogonal");
		
		location = _location;
		vUp = _vUp.normalized();
		vTo = _vTo.normalized();
		vRight = _vTo.crossProduct(_vUp).normalized();
	}
	
	public Camera setViewPlaneSize(double _width, double _height) {
		if(_width <= 0 || _height <= 0)
			throw new IllegalArgumentException("width and height of the view plane must be positive");
		width = _width;
		height = _height;
		return this;
	}
	
	public Camera setDistance(double _distance) {
		distance = _distance;
		return this;
	}
	
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
		Point3D pCenter = location.add(vTo.scale(distance));
		double Ry = height / nY;
		double Rx = width / nX;
		double yi = -(double)(i - ((nY - 1) /(double)2)) * Ry;
		double xj = (double)(j - (nX - 1) /(double)2) * Rx;
		Point3D pIJ = pCenter;
		if (xj != 0) pIJ = pIJ.add(vRight.scale(xj));
		if (yi != 0) pIJ = pIJ.add(vUp.scale(yi));
		
		return new Ray(location, pIJ.subtract(location));
	}
  
}
