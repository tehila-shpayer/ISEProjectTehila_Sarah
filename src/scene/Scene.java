package scene;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;

public class Scene {
	public String name = null;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = new AmbientLight(background, 0);
	public Geometries geometries = null;
	
	public Scene(String _name) {
		name = _name;	
		geometries = new Geometries();
	}
	
	public Scene setBackground(Color _background) {
		this.background = _background;
		return this;
	}
	
	public Scene setAmbientLight(AmbientLight _ambientLight) {
		this.ambientLight = _ambientLight;
		return this;
	}
	
	public Scene setGeometries(Geometries _geometries) {
		this.geometries = _geometries;
		return this;
	}
	
	public Scene setGeometries(Intersectable _geometry) {
		if(_geometry != null)
			this.geometries.add(_geometry);
		return this;
	}
	

}
