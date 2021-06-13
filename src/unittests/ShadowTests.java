package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*; 
import scene.Scene;

/**
 * Testing basic shadows
 * 
 * @author Dan
 */
public class ShadowTests {
	private Scene scene = new Scene("Test scene");
	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setDistance(1000);
//
//	/**
//	 * Produce a picture of a sphere and triangle with point light and shade
//	 */
//	@Test 
//	public void sphereTriangleInitial() {
//		scene.geometries.add( //
//				new Sphere(new Point3D(0, 0, -200), 60) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
//				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
//		);
//		scene.lights.add( //
//				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
//						.setkL(1E-5).setkQ(1.5E-7));
//
//		Render render = new Render(). //
//				setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//		render.renderImage();
//		render.writeToImage();
//	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test 
	public void sphereTriangleMoveTriangle1() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
				new Triangle(new Point3D(-62, -32, 0), new Point3D(-32, -62, 0), new Point3D(-58, -58, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
						.setkL(1E-5).setkQ(1.5E-7));

//		Render render = new Render(). //
//				setImageWriter(new ImageWriter("shadowSphereTriangleMoveTriangle1", 400, 400)) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//		render.renderImage();
//		render.writeToImage();
//		
//		Render render1 = new Render(). //
//				setImageWriter(new ImageWriter("shadowSphereTriangleMoveTriangle1SS", 400, 400)) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//		render1.renderImageSuperSumpling();
//		render1.writeToImage();
		
		Render render2 = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleMoveTriangle1SSA", 400, 400)) //
				.setCamera(camera) //
				.setRayTracerBase(new RayTracerBasic(scene));
		render2.renderImageAdaptiveSuperSumpling();
		render2.writeToImage();
	}
//	
//	/**
//	 * Produce a picture of a sphere and triangle with point light and shade
//	 */
//	@Test 
//	public void sphereTriangleMoveTriangle2() {
//		scene.geometries.add( //
//				new Sphere(new Point3D(0, 0, -200), 60) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
//				new Triangle(new Point3D(-50, -20, 0), new Point3D(-20, -50, 0), new Point3D(-48, -48, -4)) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
//		);
//		scene.lights.add( //
//				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
//						.setkL(1E-5).setkQ(1.5E-7));
//
//		Render render = new Render(). //
//				setImageWriter(new ImageWriter("shadowSphereTriangleMoveTriangle2", 400, 400)) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//		render.renderImage();
//		render.writeToImage();
//	}
//	/**
//	 * Produce a picture of a sphere and triangle with point light and shade
//	 */
//	@Test 
//	public void sphereTriangleMoveSphere1() {
//		scene.geometries.add( //
//				new Sphere(new Point3D(0, 0, -200), 60) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
//				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
//		);
//		scene.lights.add( //
//				new SpotLight(new Color(400, 240, 0), new Point3D(-85, -85, 130), new Vector(1, 1, -3)) //
//						.setkL(1E-5).setkQ(1.5E-7));
//
//		Render render = new Render(). //
//				setImageWriter(new ImageWriter("shadowSphereTriangleMoveSphere1", 400, 400)) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//		render.renderImage();
//		render.writeToImage();
//	}
//	/**
//	 * Produce a picture of a sphere and triangle with point light and shade
//	 */
//	@Test 
//	public void sphereTriangleMoveSphere2() {
//		scene.geometries.add( //
//				new Sphere(new Point3D(0, 0, -200), 60) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
//				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
//		);
//		scene.lights.add( //
//				new SpotLight(new Color(400, 240, 0), new Point3D(-70, -70, 50), new Vector(1, 1, -3)) //
//						.setkL(1E-5).setkQ(1.5E-7));
//
//		Render render = new Render(). //
//				setImageWriter(new ImageWriter("shadowSphereTriangleMoveSphere2", 400, 400)) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//		render.renderImage();
//		render.writeToImage();
//	}
//
//	/**
//	 * Produce a picture of a two triangles lighted by a spot light with a Sphere
//	 * producing a shading
//	 */
//	@Test
//	public void trianglesSphere() {
//		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
//
//		scene.geometries.add( //
//				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
//						.setMaterial(new Material().setkS(0.8).setnShininess(60)), //
//				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
//						.setMaterial(new Material().setkS(0.8).setnShininess(60)), //
//				new Sphere(new Point3D(0, 0, -115),30) //
//						.setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
//		);
//		scene.lights.add( //
//				new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
//						.setkL(4E-4).setkQ(2E-5));
//
//		Render render = new Render() //
//				.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//		render.renderImage();
//		render.writeToImage();
//	}

}
