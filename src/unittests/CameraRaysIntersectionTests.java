package unittests;

//import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import elements.Camera;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import java.util.List;


/**
 * Testing camera rays intersection tests
 * 
 * @authors Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */

/**
 * Test method for
 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
 * construct ray through pixels through different geometries.
 */
public class CameraRaysIntersectionTests {
    // **** Group: vp 3X3 resolution 3X3 distance 1

	/**
	 * test sphere
	 */
	@Test
	public void testCameraRaysIntersectionWithSphere() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3, 3);

		// TC01: Sphere after view plane and visible completely from the view plane (2 points)
		Sphere sphere = new Sphere(new Point3D(0, 0, -3), 1);
		assertEquals("wrong number of intersection points with sphere", findAllIntersectionsOfViewPlane(camera, 3, 3, sphere), 2);
		
		// TC02: view plane embedded in the sphere and located after camera 
		// - Upper and lower edges outside the boundaries of the view plane (18 points)
		camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3, 3);
		sphere = new Sphere(new Point3D(0, 0, -2.5), 2.5);
		assertEquals("wrong number of intersection points with sphere", findAllIntersectionsOfViewPlane(camera, 3, 3, sphere), 18);	
		
		// TC03: view plane embedded in the sphere and located after camera
		// - Upper and lower edges within the boundaries of the view plane (10 points)
		sphere = new Sphere(new Point3D(0, 0, -2), 2);
		assertEquals("wrong number of intersection points with sphere", findAllIntersectionsOfViewPlane(camera, 3, 3, sphere), 10);	

		// TC04: view plane and camera embedded in the sphere 
		// - Upper and lower edges outside the boundaries of the view plane (9 points)
		sphere = new Sphere(new Point3D(0, 0, -0.5), 4);
		assertEquals("wrong number of intersection points with sphere", findAllIntersectionsOfViewPlane(camera, 3, 3, sphere), 9);
		
		// TC05: view plane and camera after sphere and outside it completely (0 points)  
		camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3, 3);
		sphere = new Sphere(new Point3D(0, 0, 1), 0.5);
		assertEquals("wrong number of intersection points with sphere", findAllIntersectionsOfViewPlane(camera, 3, 3, sphere), 0);
		
	}
	
	/**
	 * test plane
	 */
	@Test
	public void testCameraRaysIntersectionWithPlane() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3, 3);

		// TC01: Plane after view plane and parallel to it (9 points)
		Plane plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 0, 1));
		assertEquals("wrong number of intersection points with plane", findAllIntersectionsOfViewPlane(camera, 3, 3, plane), 9);

		// TC02: Plane after view plane and is inclined (9 points)
		plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 1, -1));
		assertEquals("wrong number of intersection points with plane", findAllIntersectionsOfViewPlane(camera, 3, 3, plane), 9);

		// TC03: Plane after view plane and is inclined 
		// so that the lower rays cast through the view plane are parallel to the plane (6 points)
		plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 1, 1));
		assertEquals("wrong number of intersection points with plane", findAllIntersectionsOfViewPlane(camera, 3, 3, plane), 6);

	}
	/**
	 * test triangle
	 */
	@Test
	public void testCameraRaysIntersectionWithTriangle() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3, 3);
		
		// TC01: Triangle after view plane and parallel to it (1 points)
		// small triangle, only one intersection
		Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
		assertEquals("wrong number of intersection points with triangle", findAllIntersectionsOfViewPlane(camera, 3, 3, triangle), 1);

		// TC02: Triangle after view plane and parallel to it, tall triangle (2 points)
		triangle = new Triangle(new Point3D(0, 20, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
		assertEquals("wrong number of intersection points with triangle", findAllIntersectionsOfViewPlane(camera, 3, 3, triangle), 2);

	}
	
	/**
	 * 
	 * @param camera
	 * @param nX
	 * @param nY
	 * @param geometry
	 * @return the number of intersection points between all the ray from the camera and the geometry given
	 */
	public int findAllIntersectionsOfViewPlane(Camera camera, int nX, int nY, Geometry geometry) {
		int count = 0;
		for(int i = 0; i < nX; i++) {
			for(int j = 0; j < nY; j++) {
				Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
				List<GeoPoint> intersectionsList = geometry.findGeoIntersections(ray);
				if(intersectionsList != null)
					count += intersectionsList.size();
			}
		}
		return count;
	}

}


//public class CameraRaysIntersectionTests {
//    @Test
//    public void testConstructRayThroughPixelAndSphere() {
//        //TC01: sphere is after view plane and camera and is captured by one pixel
//        Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Sphere sphere1=new Sphere(new Point3D(0,0,-3),1);
//        assertEquals(2,sumOfIntersections(camera1,3,3,sphere1));
//
//        //TC02: sphere is after view plane and camera and is captured by all pixels
//        Camera camera2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0,0,-1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Sphere sphere2=new Sphere(new Point3D(0,0,-2.5),2.5);
//        assertEquals(18,sumOfIntersections(camera2,3,3,sphere2));
//
//        //TC03: sphere is after view plane and camera and is captured by part of the pixels
//        Camera camera3 = new Camera(new Point3D(0, 0, 0.5), new Vector(0,0,-1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Sphere sphere3=new Sphere(new Point3D(0,0,-2),2);
//        assertEquals(10,sumOfIntersections(camera3,3,3,sphere3));
//
//        //TC04: camera is inside of the sphere
//        Camera camera4 = new Camera(Point3D.ZERO, new Vector(0,0,-1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Sphere sphere4=new Sphere(new Point3D(0,0,-1),4);
//        assertEquals(9,sumOfIntersections(camera4,3,3,sphere4));
//
//        //TC05: sphere is before view plane
//        Camera camera5 = new Camera(Point3D.ZERO, new Vector(0,0,-1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Sphere sphere5=new Sphere(new Point3D(0,0,1),0.5);
//        assertEquals(0,sumOfIntersections(camera5,3,3,sphere5));
//    }
//    
//    @Test
//    public void testConstructRayThroughPixelAndPlane() {
//        //TC01: Plane is after view plane and camera
//        Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Plane plane1=new Plane(new Point3D(0,0,-3),new Vector(0,0,1));
//        assertEquals(9,sumOfIntersections(camera1,3,3,plane1));
//
//        //TC02: Plane is after view plane and camera and plane is sideways
//        Camera camera2 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Plane plane2=new Plane(new Point3D(0,0,-10),new Vector(0,-1,10));
//        assertEquals(9,sumOfIntersections(camera2,3,3,plane2));
//
//        //TC03: Plane is after view plane and camera and plane is sideways
//        Camera camera3 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Plane plane3=new Plane(new Point3D(0,0,-10),new Vector(0,-10,10));
//        assertEquals(6,sumOfIntersections(camera3,3,3,plane3));
//    }
//    @Test
//    public void testConstructRayThroughPixelAndTriangle() {
//        //TC01: Triangle is after view plane and camera and captured by one pixel
//        Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Triangle triangle1=new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
//        assertEquals(1,sumOfIntersections(camera1,3,3,triangle1));
//
//        //TC02: Triangle is after view plane and camera and captured by two pixels
//        Camera camera2 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
//        Triangle triangle2=new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
//        assertEquals(2,sumOfIntersections(camera2,3,3,triangle2));
//
//    }
//
//    /**
//     *
//     * @param camera
//     * @param Nx
//     * @param Ny
//     * @param geometry
//     * @return
//     */
//    public int sumOfIntersections(Camera camera, int Nx, int Ny, Geometry geometry){
//        int count =0;
//        for(int i=0;i<Nx;i++){
//            for(int j=0;j<Ny;j++){
//                Ray ray=camera.constructRayThroughPixel(Nx,Ny,j,i);
//                List<GeoPoint> findInt=geometry.findGeoIntersections(ray);
//                if(findInt!=null)
//                {
//                    count+=findInt.size();
//                }
//            }
//        }
//        return count;
//    }
//
//
//}