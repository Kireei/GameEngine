package ui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.MasterRenderer;
import renderEngine.TerrainRenderer;

public class RadioButtonFunctions {
	private static Vector3f backgroundColorBlack = new Vector3f(0, 0, 0);
	private static Vector3f backgroundColorWhite = new Vector3f(1, 1, 1);
	public static boolean rotateLight = false;
	private static int renderingGeometryLines = GL11.GL_LINE_STRIP;
	private static int renderingGeometryTriangles = GL11.GL_TRIANGLES;
	
	
	public static void function(String id) {
		switch(id) {
		case "backgroundColor":
			System.out.println("Background color changed to BLACK");
			MasterRenderer.backgroundColor = backgroundColorBlack;
			break;
		case "renderingGeometry":
			System.out.println("Renderering geometry changed to TRIANGLES");
			TerrainRenderer.renderingGeometry = renderingGeometryTriangles;
			break;
		case "rotateLight":
			rotateLight = true;
			break;
		default:
			System.out.println("Function for given RadioButton-ID not found");
			UIHandler.openWindow(UIMaster.colorPicker);
		}
	}
	public static void unFunction(String id) {
		switch(id) {
		case "backgroundColor":
			System.out.println("Background color changed to WHITE");
			MasterRenderer.backgroundColor = backgroundColorWhite;
			break;
		case "renderingGeometry":
			System.out.println("Renderering geometry changed to LINES");
			TerrainRenderer.renderingGeometry = renderingGeometryLines;
			break;
		case "rotateLight":
			rotateLight = false;
			break;
		default:
			System.out.println("Function for given RadioButton-ID not found");
			UIHandler.closeWindow(UIMaster.colorPicker);
		}
	}
}
