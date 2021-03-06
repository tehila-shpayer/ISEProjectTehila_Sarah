package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class OurPictureTest {

	private Scene scene = new Scene("Test scene");

	
	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void ourPicture() {
		Camera camera = new Camera(new Point3D(689, 777, -100), new Vector(-597, -754, -46), new Vector(0, -46, 754)) //
				.setViewPlaneSize(337, 225).setDistance(1200).setAperture(100, 10);

		scene.setAmbientLight(new AmbientLight(new Color(209, 240, 251), 0.15));

		scene.geometries.add( //
				new Plane(new Point3D(1, -200, -200), new Point3D(150, -200, -135), new Point3D(75, -200, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)).setEmission(new Color(1, 1, 1)), //
						
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
				        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0.5)), //
		        new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
				        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0.5)), //
		        new Sphere(new Point3D(70, -20, -100), 20) //
		                .setEmission(new Color(82, 228, 228)) //
		                .setMaterial(new Material().setkD(0.5).setkS(0.2).setnShininess(30).setkT(0.1)),
		                
				new Triangle(new Point3D(-150, -150, -215), new Point3D(150, -150, -235), new Point3D(75, 75, -250)) //
				        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkR(0.4)), //
		        new Triangle(new Point3D(-150, -150, -215), new Point3D(-70, 70, -240), new Point3D(75, 75, -250)) //
				        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkR(0.5)), //
				new Sphere(new Point3D(30, -40, -200), 20) //
	                    .setEmission(new Color(java.awt.Color.ORANGE)) //
	                    .setMaterial(new Material().setkD(0.5).setkS(0.2).setnShininess(30).setkT(0.4)),
	    		new Sphere(new Point3D(90, 90, -200), 11) //
	                    .setEmission(new Color(0, 0, 200)) //
	                    .setMaterial(new Material().setkD(0.5).setkS(0.2).setnShininess(30).setkT(0.4)),
	                    
	            new Sphere(new Point3D(-50, -90, -190), 12) //
	                    .setEmission(new Color(210, 47, 20)) //
	                    .setMaterial(new Material().setkD(0.5).setkS(0.2).setnShininess(100).setkT(0.3)),
	            
	    	    new Triangle(new Point3D(-40, -20, -100), new Point3D(-40, -30, -100), new Point3D(-40, -20, -90)) //
	                    .setEmission(new Color(java.awt.Color.BLUE)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkR(0)),
	            new Triangle(new Point3D(-40, -20, -100), new Point3D(-40, -30, -100), new Point3D(-40, -20, -110)) //
	                    .setEmission(new Color(java.awt.Color.BLUE)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkR(0)),
	            new Triangle(new Point3D(-40, -20, -100), new Point3D(-40, -10, -100), new Point3D(-40, -20, -90)) //
	                    .setEmission(new Color(java.awt.Color.BLUE)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkR(0)),
	    	    new Triangle(new Point3D(-40, -20, -100), new Point3D(-40, -10, -100), new Point3D(-40, -20, -110)) //
	                    .setEmission(new Color(java.awt.Color.BLUE)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkR(0)),
	                    
	            new Triangle(new Point3D(-40, -20, -100), new Point3D(-30, -40, -100), new Point3D(-40, -20, -90)) //
	                    .setEmission(new Color(java.awt.Color.black)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkR(1)),
	            new Triangle(new Point3D(-40, -20, -100), new Point3D(-30, -40, -100), new Point3D(-40, -20, -110)) //
	                    .setEmission(new Color(java.awt.Color.black)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkR(1)),
	            new Triangle(new Point3D(-40, -20, -100), new Point3D(-50, 0, -100), new Point3D(-40, -20, -90)) //
	                    .setEmission(new Color(java.awt.Color.black)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkR(1)),
	    	    new Triangle(new Point3D(-40, -20, -100), new Point3D(-50, 0, -100), new Point3D(-40, -20, -110)) //
	                    .setEmission(new Color(java.awt.Color.black)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkR(1)),	       
	                    
	    	    new Triangle(new Point3D(125, 50, -100), new Point3D(125, 43, -100), new Point3D(125, 50, -93)) //
	                    .setEmission(new Color(java.awt.Color.BLUE)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkT(0.5)),
	            new Triangle(new Point3D(125, 50, -100), new Point3D(125, 43, -100), new Point3D(125, 50, -107)) //
	                    .setEmission(new Color(java.awt.Color.BLUE)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkT(0.5)),
	            new Triangle(new Point3D(125, 50, -100), new Point3D(125, 57, -100), new Point3D(125, 50, -93)) //
	                    .setEmission(new Color(java.awt.Color.BLUE)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkT(0.5)),
	    	    new Triangle(new Point3D(125, 50, -100), new Point3D(125, 57, -100), new Point3D(125, 50, -107)) //
	                    .setEmission(new Color(java.awt.Color.BLUE)) //
	                    .setMaterial(new Material().setkD(0.6).setkS(0.2).setnShininess(100).setkT(0.5)),
	            
	            new Sphere(new Point3D(-50, -90, -190), 6) //
	                    .setEmission(new Color(58, 138, 160)) //
	                    .setMaterial(new Material().setkD(0.5).setkS(0.2).setnShininess(100)),
	                
				new Sphere(new Point3D(60, 50, -50), 40) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.8)),
						
				new Sphere(new Point3D(70, -20, -100), 30) //
						.setEmission(new Color(40 ,40, 100)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.8)),
						
				new Sphere(new Point3D(-45, 0, -200), 14) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.4)),		                
				new Sphere(new Point3D(-45, 0, -200), 7) //
						.setEmission(new Color(java.awt.Color.GREEN)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
						
				new Sphere(new Point3D(-55, -20, -175), 10) //
						.setEmission(new Color(198, 91, 219)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.4).setnShininess(50).setkR(0.4)),
				new Sphere(new Point3D(-90, 10, -200), 7) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.3).setkS(0.1).setnShininess(20).setkR(0.4)),
		                
		        new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(150, -150, -235)) //
						.setMaterial(new Material().setkD(0).setkS(1).setnShininess(60).setkR(1)), //
			    new Triangle(new Point3D(150, -150, -135), new Point3D(150, -150, -235), new Point3D(-150, -150, -215)) //
						.setMaterial(new Material().setkD(0).setkS(1).setnShininess(60).setkR(1)), //
		                
		        new Triangle(new Point3D(-98, 63, -86), new Point3D(-70, 70, -140), new Point3D(-150, -150, -115)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0.5)), //
			    new Triangle(new Point3D(-98, 63, -86), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0.5)), //
				new Triangle(new Point3D(-98, 63, -86), new Point3D(75, 75, -150), new Point3D(-150, -150, -115)) //
			         	.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0.5)), //
						
				new Triangle(new Point3D(-98, 63, -200), new Point3D(-70, 70, -240), new Point3D(-150, -150, -215)) //
				        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkR(0.4)), //
			    new Triangle(new Point3D(-98, 63, -200), new Point3D(-70, 70, -240), new Point3D(75, 75, -250)) //
				        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkR(0.4)), //
				new Triangle(new Point3D(-98, 63, -200), new Point3D(75, 75, -250), new Point3D(-150, -150, -215)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkR(0.4))); //


		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));
		scene.lights.add(new SpotLight(new Color(200, 180, 180), new Point3D(914, 826, -130), new Vector(-959.0, -826.0, -65.0)) //
				.setkL(4E-5).setkQ(2E-7));
		scene.lights.add(new DirectionalLight(new Color(30, 30, 150), new Vector(200, -500, 40)));
		scene.lights.add(new PointLight(new Color(60,60,60), new Point3D(215, 368, 0))//
				.setkL(0.00001).setkQ(0.000001));

//		ImageWriter imageWriter = new ImageWriter("ourPictureSS", 600, 600);
//		Render render = new Render() //
//				.setImageWriter(imageWriter) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//
//		render.renderImageAdaptiveSuperSumpling();
//		render.writeToImage();
		//639.6s, N = 4 (4X4 rays in each pixel)?
		
//		ImageWriter imageWriter2 = new ImageWriter("ourPictureAdaptiveSS", 600, 600);
//		Render render2 = new Render() //
//				.setImageWriter(imageWriter2) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//
//		render2.renderImageAdaptiveSuperSumpling();
//		render2.writeToImage();
		//602.9s, level = 3
		
//		ImageWriter imageWriter2 = new ImageWriter("ourPictureRectangle", 900, 600);
//		Render render2 = new Render() //
//				.setImageWriter(imageWriter2) //
//				.setCamera(camera) //
//				.setRayTracerBase(new RayTracerBasic(scene));
//
//		render2.renderImage();
//		render2.writeToImage();
		
		ImageWriter imageWriter2 = new ImageWriter("ourPicture4x4ASS", 900, 600);
		Render render2 = new Render() //
				.setImageWriter(imageWriter2) //
				.setCamera(camera) //
				.setRayTracerBase(new RayTracerBasic(scene));

		render2.renderImageAdaptiveSuperSumpling();
		render2.writeToImage();
	}//end

}
