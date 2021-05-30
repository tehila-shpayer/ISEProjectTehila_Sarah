package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.*;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;


public class FocusTests {

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void Focus() {	
		Scene scene = new Scene("Test scene");

		Camera camera = new Camera(new Point3D(0, 0, 500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(30, 30).setDistance(450);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Sphere(new Point3D(10, 10, -100), 3) //
						.setEmission(new Color(java.awt.Color.BLUE)), //
				new Sphere(new Point3D(5, 5, -50), 3) //
						.setEmission(new Color(java.awt.Color.GREEN)), //
				new Sphere(new Point3D(0, 0, 0), 3) //
						.setEmission(new Color(java.awt.Color.YELLOW)), //
				new Sphere(new Point3D(-5, -5, 50), 3) //
						.setEmission(new Color(java.awt.Color.RED)), //
				new Sphere(new Point3D(-10, -10, 100), 3) //
						.setEmission(new Color(java.awt.Color.CYAN))); //

		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point3D(0, 5, 0))); //
		scene.lights.add(new DirectionalLight(new Color(700, 400, 400), new Vector(0, -1, 0))); //

		ImageWriter imageWriter = new ImageWriter("DepthOfField", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracerBase(new RayTracerBasic(scene));

		render.renderImageFocus();
		render.writeToImage();
	}//end


}
