package geometries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import primitives.Point3D;
import primitives.Ray;

import geometries.Intersectable.GeoPoint;

public class Geometries implements Intersectable{
	List<Intersectable> geometries;
	
	public Geometries() {
		geometries = new LinkedList<Intersectable>();
	}
	
	public Geometries(Intersectable... _geometries) {
		geometries = new LinkedList<Intersectable>();
		geometries.addAll(List.of(_geometries));
	}
	
	public int getNumberOfGeometries() {
		return geometries.size();
	}
	
	public void add(Intersectable... _geometries) {
		for (Intersectable g: List.of(_geometries)) {
			if(g != null)
				geometries.add(g);
		}
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		List<Point3D> bigList = new LinkedList<Point3D>();
		for (Intersectable g: geometries) {
			List<Point3D> lst = g.findIntersections(ray);
			if (lst != null)
				bigList.addAll(lst);
		}
		if (bigList.size() == 0)
			return null;
		return bigList;
	}
	
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		List<GeoPoint> bigList = new LinkedList<GeoPoint>();
		for (Intersectable g: geometries) {
			List<GeoPoint> lst = g.findGeoIntersections(ray);
			if (lst != null)
				bigList.addAll(lst);
		}
		if (bigList.size() == 0)
			return null;
		return bigList;
	}

}
