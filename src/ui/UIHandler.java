package ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class UIHandler {
	private static Loader loader;
	public static RawModel rawModel;
	public List<UIElement> uies;
	
	
	public UIHandler(Camera camera) {
		
		//this.uie = new UIElement(new Vector3f(0, 0, 0), new Vector2f(0.06f, 0.06f), new ModelTexture(loader.loadTexture("GUI/Corner")));
		//this.uies = createWindow(new Vector2f(5f, 15f));
	}
	
	public static void init(Loader actuallLoader) {
		loader = actuallLoader;
		rawModel = rawModel();
	}
	private static RawModel rawModel() {
		float aspectRatio = 9f / 16f;
		float[] positions = {-aspectRatio, 1.0f, 0.0f, -aspectRatio, -1.0f, 0.0f, aspectRatio, -1.0f, 0.0f, aspectRatio, 1.0f, 0.0f};
		float[] textureCoords = {0, 0, 0, 1, 1, 1, 1, 0};
		float[] normals = {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1};
		int[] indices = {0, 1, 2, 0, 2, 3};
		
		return loader.loadToVAO(positions, textureCoords, normals, indices);
	}
	
	public static List<UIElement> createWindow(Vector2f scale) {
		float tileSize = 0.06f;
		float tileSpaceX = tileSize * (18f/16f);
		float tileSpaceY = tileSize*2;
		
		
		List<UIElement> window = new ArrayList<UIElement>();
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(y == 0 && x == 0) {
					window.add(new UIElement(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize), new ModelTexture(loader.loadTexture("GUI/Corner 1"))));
					continue;
				} else if(y == 0 && x == 1) {
					UIElement topEdge = new UIElement(new Vector3f(tileSpaceX, 0, 0), new Vector3f(0,0,0), new Vector2f(tileSize*(scale.x-2), tileSize), new ModelTexture(loader.loadTexture("GUI/Edge 1")));
					window.add(topEdge);
					continue;
				} else if(y == 0 && x == 2) {
					window.add(new UIElement(new Vector3f(tileSpaceX * (scale.x - 1), 0, 0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize), new ModelTexture(loader.loadTexture("GUI/Corner 2"))));
					continue;
				} else if(y == 1 && x == 0) {
					window.add(new UIElement(new Vector3f(0, -tileSpaceY, 0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize * (scale.y - 2)), new ModelTexture(loader.loadTexture("GUI/Edge 2"))));
					continue;
				} else if(y == 1 && x == 1) {
					window.add(new UIElement(new Vector3f(tileSpaceX, -tileSpaceY, 0), new Vector3f(0,0,0), new Vector2f(tileSize * (scale.x - 2), tileSize * (scale.y - 2)), new ModelTexture(loader.loadTexture("GUI/Background"))));
					continue;
				} else if (y == 1 && x == 2) {
					window.add(new UIElement(new Vector3f(tileSpaceX * (scale.x - 1), -tileSpaceY,0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize * (scale.y - 2)), new ModelTexture(loader.loadTexture("GUI/Edge 3"))));
					continue;
				} else if(y == 2 && x == 0) {
					window.add(new UIElement(new Vector3f(0, -tileSpaceY * (scale.y - 1),0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize), new ModelTexture(loader.loadTexture("GUI/Corner 3"))));
					continue;
				} else if(y == 2 && x == 1) {
					window.add(new UIElement(new Vector3f(tileSpaceX, -tileSpaceY * (scale.y - 1), 0), new Vector3f(0,0,0), new Vector2f(tileSize * (scale.x - 2), tileSize), new ModelTexture(loader.loadTexture("GUI/Edge 4"))));
					continue;
				} else if (y == 2 && x == 2) {
					window.add(new UIElement(new Vector3f(tileSpaceX * (scale.x - 1), -tileSpaceY * (scale.y - 1),0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize), new ModelTexture(loader.loadTexture("GUI/Corner 4"))));
					continue;
				}
			
			}
		}
		return window;
	}
}
