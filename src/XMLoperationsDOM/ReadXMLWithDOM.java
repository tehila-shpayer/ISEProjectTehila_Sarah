package XMLoperationsDOM;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import elements.AmbientLight;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

public class ReadXMLWithDOM {
	public static Scene BuildSceneObjectFromXML(String sceneName, String xmlPath) {
		Scene scene = new Scene(sceneName);
		try {
			// creating a constructor of file class and parsing an XML file
			File file = new File(xmlPath);
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
			scene = scene.setBackground(getColorToScene(scenElement, "background-color"));
		
			//set ambient light
			NodeList sceneNodeList = scenElement.getElementsByTagName("ambient-light");
			Element ambientColor = (Element) sceneNodeList.item(0);
			scene = scene.setAmbientLight(new AmbientLight(getColorToScene(ambientColor, "color"), 1));

			//set geometries
			NodeList geometriesNode = scenElement.getElementsByTagName("geometries");
			Element geometriesElement = (Element) geometriesNode.item(0);
			
			//set triangles
			NodeList triangleList = geometriesElement.getElementsByTagName("triangle");
			for (int i = 0; i < triangleList.getLength();i++) {
				Element geometryElement = (Element) triangleList.item(i);
				Point3D p1 = getPoint3dForGeometry(geometryElement, "p0");
				Point3D p2 = getPoint3dForGeometry(geometryElement, "p1");
				Point3D p3 = getPoint3dForGeometry(geometryElement, "p2");
				scene.addGeometry(new Triangle(p1, p2, p3));
				}	
			
			//set spheres
			NodeList sphereList = geometriesElement.getElementsByTagName("sphere");
			for (int i = 0; i < sphereList.getLength();i++) {
				Element geometryElement = (Element) sphereList.item(i);
				Point3D center = getPoint3dForGeometry(geometryElement, "center");
				double radius = Integer.parseInt(geometryElement.getAttribute("radius"));
				scene.addGeometry(new Sphere(center, radius));
				}	
			
			//set planes
			NodeList planeList = geometriesElement.getElementsByTagName("plane");
			for (int i = 0; i < planeList.getLength();i++) {
				Element geometryElement = (Element) planeList.item(i);
				Point3D q0 = getPoint3dForGeometry(geometryElement, "q0");
				Vector normal = new Vector(getPoint3dForGeometry(geometryElement, "normal"));
				scene.addGeometry(new Plane(q0, normal));
				}	
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scene;
	}
	
	public static Point3D getPoint3dForGeometry(Element geometryElement, String attribute) {
		String[] pStr = geometryElement.getAttribute(attribute).split(" ");
		Point3D p = new Point3D(Integer.parseInt(pStr[0]), Integer.parseInt(pStr[1]), Integer.parseInt(pStr[2]));
		return p;
	}
	
	public static Color getColorToScene(Element colorElement, String attribute) {
		String colorString = colorElement.getAttribute(attribute);
		String[] rgb = colorString.split(" ");
		return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));

	}
	
	

}
