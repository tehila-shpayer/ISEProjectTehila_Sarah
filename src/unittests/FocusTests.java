package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.*;
import geometries.Geometry;
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
//	@Test
//	public void Focus() {	
//		Scene scene = new Scene("Test scene");
//
//		Camera camera = new Camera(new Point3D(0, 0, 500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//				.setViewPlaneSize(30, 30).setDistance(350).setAperture(100, 7);
//
//		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
//
//		scene.geometries.add( //
//				new Sphere(new Point3D(10, 10, -100), 3) //
//						.setEmission(new Color(java.awt.Color.BLUE)), //
//				new Sphere(new Point3D(5, 5, -50), 3) //
//						.setEmission(new Color(java.awt.Color.GREEN)), //
//				new Sphere(new Point3D(0, 0, 0), 3) //
//						.setEmission(new Color(java.awt.Color.YELLOW)), //
//				new Sphere(new Point3D(-5, -5, 50), 3) //
//						.setEmission(new Color(java.awt.Color.RED)), //
//				new Sphere(new Point3D(-10, -10, 100), 3) //
//						.setEmission(new Color(java.awt.Color.CYAN))); //
//
//		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point3D(0, 5, 0))); //
//		scene.lights.add(new DirectionalLight(new Color(700, 400, 400), new Vector(0, -1, 0))); //
//
//		ImageWriter imageWriter = new ImageWriter("DepthOfField", 600, 600);
//		Render render = new Render() //
//				.setImageWriter(imageWriter) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//
//		render.renderImageFocus();
//		render.writeToImage();
//	}//end

	@Test
	public void Focus2() {
		
	Scene scene1 = new Scene("Test scene");
	Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(150, 150) //
			.setDistance(1000) //
			.setAperture(400, 10);

	Geometry sphere1 = new Sphere(new Point3D(0, 0, -50), 15) //
			.setEmission(new Color(java.awt.Color.BLUE)) //
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
	Geometry sphere2 = new Sphere(new Point3D(15, 15, -60), 15) //
			.setEmission(new Color(java.awt.Color.GREEN)) //
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
	Geometry sphere3 = new Sphere(new Point3D(30, 30, -70), 15) //
			.setEmission(new Color(java.awt.Color.YELLOW)) //
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
	Geometry sphere4 = new Sphere(new Point3D(-15, -15, -40), 15) //
			.setEmission(new Color(java.awt.Color.RED)) //
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
	Geometry sphere5 = new Sphere(new Point3D(-30, -30, -30), 15) //
			.setEmission(new Color(java.awt.Color.CYAN)) //
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
	scene1.geometries.add(sphere1, sphere2, sphere3, sphere4, sphere5);
	
	//scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
	scene1.lights.add(new DirectionalLight(new Color(300, 300, 0), new Vector(-1, 0, -1)));

	ImageWriter imageWriter = new ImageWriter("focus2", 500, 500);
	Render render = new Render()//
			.setImageWriter(imageWriter) //
			.setCamera(camera1) //
			.setRayTracerBase(new RayTracerBasic(scene1));
	//render.renderImage();
	//render.writeToImage();
	
	ImageWriter imageWriter2 = new ImageWriter("focus3", 500, 500);
	Render render2 = new Render()//
			.setImageWriter(imageWriter2) //
			.setCamera(camera1) //
			.setRayTracerBase(new RayTracerBasic(scene1));
	render2.renderImageFocus();
	render2.writeToImage();
	}
}
