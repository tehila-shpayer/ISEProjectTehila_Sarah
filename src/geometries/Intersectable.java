package geometries;

import primitives.*;
import java.util.List;

import geometries.Intersectable.GeoPoint;
/**
 * Interface Intersectable is to represent intersectable shapes 
 * @author Tehila Shpayer 325236594 and Sarah Malka Hamou 325266401
 */
public interface Intersectable {
	/**
	 * javadoc com.
	 * @author tehil
	 *
	 */
	public static class GeoPoint {
	    public Geometry geometry;
	    public Point3D point;
	    
	    /**
	     * javadoc com.
	     * @param geometry
	     * @param point3d
	     */
	    public GeoPoint(Geometry geometry, Point3D point3d) {
	    	this.geometry = geometry;
	    	this.point = point3d;
	    }
	    
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
	 * The method receives a ray and returns all intersection points with the geometry
	 * @param ray
	 * @return list of intersection points
	 */
    List<GeoPoint> findGeoIntersections(Ray ray);
    
    }
