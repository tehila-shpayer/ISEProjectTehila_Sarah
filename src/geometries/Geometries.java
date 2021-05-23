package geometries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import primitives.Point3D;
import primitives.Ray;

import geometries.Intersectable.GeoPoint;

/**
 * Geometries class is a class to represent a collection of geometries s one geometry
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 *
 */
public class Geometries implements Intersectable{
	/**
	 * @param geometries - list of geometries that assemble one geometry
	 */
	List<Intersectable> geometries;
	
	/**
	 * Default ctor - empty list of geometries 
	 */
	public Geometries() {
		geometries = new LinkedList<Intersectable>();
	}
	
	/**
	 * ctor of Geometries
	 * @param _geometries - list of geometries 
	 * can start with as many geometries as wanted
	 */
	public Geometries(Intersectable... _geometries) {
		geometries = new LinkedList<Intersectable>();
		geometries.addAll(List.of(_geometries));
	}
	
	/**
	 * simple get number of geometries in Geometries object
	 * @return geometries's size (int)
	 */
	public int getNumberOfGeometries() {
		return geometries.size();
	}
	
	/**
	 * add a number of geometries to Geometries object,
	 * only if geometry isn't empty
	 * @param _geometries - geometries objects to add
	 */
	public void add(Intersectable... _geometries) {
		for (Intersectable g: List.of(_geometries)) {
			if(g != null)
				geometries.add(g);
		}
	}
	
	/**
	 * Implementation of findGeoIntersections method
	 * adding all intersections with each geometry to one list.
	 * The method only adds the intersections that are within a certain distance range
	 * @param ray - ray with which the intersections are made
	 * @param maxDistance - maximum distance to which-
	 * the distance between the point and the ray head can get.
	 * @return - the united list of all intersections with geometries
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		List<GeoPoint> bigList = new LinkedList<GeoPoint>();
		for (Intersectable g: geometries) {
			List<GeoPoint> lst = g.findGeoIntersections(ray, maxDistance);
			if (lst != null) 
				bigList.addAll(lst);
		}
		if (bigList.size() == 0)
			return null;
		return bigList;
	}

}
