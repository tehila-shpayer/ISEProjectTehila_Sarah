package geometries;

import primitives.*;
import java.util.List;
/**
 * Interface Intersectable is to represent intersectable shapes 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public interface Intersectable {
	/**
	 * public static class within  Intersectable to represent an intersection point
	 * including the geometry it is intersected with.
	 * The purpose is to access material characteristics of geometry.
	 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
	 */
	public static class GeoPoint {
		
		/**
	     * @param geometry - the geometry that the point is on.
		 */
	    public Geometry geometry;
	    
	    /**
	     * @param point - the represented point
	     */
	    public Point3D point;
	    
	    /**
	     * ctor for GeoPoint
	     * @param geometry - the geometry that the point is on.
	     * @param point - the represented point
	     */
	    public GeoPoint(Geometry geometry, Point3D point) {
	    	this.geometry = geometry;
	    	this.point = point;
	    }
	    
	    /**
	     * override of built in equals method
	     */
	    @Override
		public boolean equals(Object obj)
		{
			if (this == obj) return true;
	        if (obj == null) return false;
	        if (!(obj instanceof GeoPoint)) return false;
	        GeoPoint other = (GeoPoint)obj;
	        return geometry.equals(other.geometry) && point.equals(other.point);
		}
	}

	/**
	 * Default implementations of Geometry findGeoIntersections that includes maxDistance param.
	 * @param ray - ray of intersection
	 * @return result of findGeoIntersections with infinite maxDistance
	 */
	default List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}
		
	/**
	 * The method receives a ray and returns intersection points with the geometry that are in the range determinated by the maxDistance
	 * @param ray - the ray of intersection
	 * @param maxDistance - the maximum distance in which we search intersection points
	 * @return list of intersection points that are close enough
	 */
      List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    }
