package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class RenderTests {
	private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setDistance(100) //
			.setViewPlaneSize(500, 500);

	/**
	 * Produce a scene with basic 3D model and render it into a jpeg image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {

		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
																													// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
																													// right
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
																														// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
																													// right

		ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setScene(scene) //
				.setCamera(camera) //
				.setRayTracerBase(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}

	/**
	 * Test for XML based scene - for bonus
	 * the given test was slightly altered
	 */
	@Test
	public void basicRenderXml() {
		Scene scene = new Scene("XML Test scene");
		// enter XML file name and parse from XML file into scene object
		// ...

		try {
			// creating a constructor of file class and parsing an XML file
			File file = new File("XMLTestFile.xml");
			// an instance of factory that gives a document builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// an instance of builder to parse the specified xml file
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			//extract scene
			NodeList nodeList = doc.getElementsByTagName("scene");
			Node sceneNode = nodeList.item(0);
			Element scenElement = (Element) sceneNode;
			
			//set backGround
			String backGroundString = scenElement.getAttribute("background-color");
			String[] rgbBackGround = backGroundString.split(" ");
			scene = scene.setBackground(new Color(Integer.parseInt(rgbBackGround[0]), Integer.parseInt(rgbBackGround[1]), Integer.parseInt(rgbBackGround[2])));
			
			//set ambient light
			NodeList sceneNodeList = scenElement.getElementsByTagName("ambient-light");
			Element ambientColor = (Element) sceneNodeList.item(0);
			String ambientColorString = ambientColor.getAttribute("color");
			String[] rgbAmbient = ambientColorString.split(" ");
			scene = scene.setAmbientLight(new AmbientLight
					(new Color(Integer.parseInt(rgbAmbient[0]), Integer.parseInt(rgbAmbient[1]), Integer.parseInt(rgbAmbient[2])), 1));

			//set geometries
			NodeList geometriesNode = scenElement.getElementsByTagName("geometries");
			Element geometriesElement = (Element) geometriesNode.item(0);
			NodeList triangleList = geometriesElement.getElementsByTagName("triangle");
			for (int i = 0; i < triangleList.getLength();i++) {
				Node node = triangleList.item(i);
				Element geometryElement = (Element) node;
				Point3D p1 = getPoint3dForGeometry(geometryElement, "p0");
				Point3D p2 = getPoint3dForGeometry(geometryElement, "p1");
				Point3D p3 = getPoint3dForGeometry(geometryElement, "p2");
				scene.addGeometry(new Triangle(p1, p2, p3));
				}	
			NodeList sphereList = geometriesElement.getElementsByTagName("sphere");
			for (int i = 0; i < sphereList.getLength();i++) {
				Node node = sphereList.item(i);
				Element geometryElement = (Element) node;
				Point3D center = getPoint3dForGeometry(geometryElement, "center");
				double radius = Integer.parseInt(geometryElement.getAttribute("radius"));
				scene.addGeometry(new Sphere(center, radius));
				}	

		} catch (Exception e) {
			e.printStackTrace();
		}
		ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setScene(scene) //
				.setCamera(camera) //
				.setRayTracerBase(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}
	
	public Point3D getPoint3dForGeometry(Element geometryElement, String attribute) {
		String[] p1str = geometryElement.getAttribute(attribute).split(" ");
		Point3D p1 = new Point3D(Integer.parseInt(p1str[0]), Integer.parseInt(p1str[1]), Integer.parseInt(p1str[2]));
		return p1;
	}

}
