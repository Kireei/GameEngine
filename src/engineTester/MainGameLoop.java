package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import functions.Function;
import functions.FunctionTypes;
import image.Image;
import map.Map;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrain.Planet;
import terrain.Terrain;
import textures.ModelTexture;
import ui.UIElement;
import ui.UIHandler;
import ui.UIMaster;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		UIHandler.init(loader);
		TextMaster.init(loader);
		RawModel model = OBJLoader.loadObjModel("cuby", loader);
	
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("chimp")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		
		Map mapReader = new Map();
		Image mapImage = new Image("map");
		
		int[] map = mapReader.readMap(mapImage);
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 5, -25),0,0,0, new Vector3f(1,1,1));
		Light light  = new Light(new Vector3f(0,0, -20), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("chimp")));
		
		Camera camera = new Camera();
	
		
		List<Entity> boxes = new ArrayList<Entity>();
		
		MasterRenderer renderer = new MasterRenderer();
		Planet planet = new Planet(renderer, loader, 16, new ModelTexture(loader.loadTexture("chimp")));
		
		
		
		
		
		camera.setPosition(new Vector3f(0,0,20));
		
		float var = 0;
		
		
		List<UIElement> window = UIHandler.createWindow(new Vector2f(5f, 15f));
		Function function = new Function(0,0,loader, 0, FunctionTypes.SINE);

		Function function1 = new Function(0, 0, loader, 0, FunctionTypes.SINE);
		//Function function2 = new Function(0, 0, loader, 0, FunctionTypes.HLINE);
		Function function3 = new Function(0,0, loader, 0, FunctionTypes.TRUESINE);
		//renderer.processFunction(function2);
		
		FontType ft = new FontType(loader.loadFont("arial"), new File("res/Fonts/arial.fnt"));
		GUIText text = new GUIText("String", 1, ft, new Vector2f(0,0), 50, false, false);
		text.setColour(1, 1, 1);
		
		
		TextMaster.loadText(text);
		for(UIElement uie: window) {
			MasterRenderer.processUIE(uie);
		}
		
		while(!Display.isCloseRequested()){
			window.get(1).checkMouse();
			if(var < 100) {
				//renderer.removeFunction(function1);
				//function1 = new Function(0, 0, loader, var, FunctionTypes.E);
				//renderer.processFunction(function1);
			}
			
			
			camera.move();
			
			//renderer.processTerrain(terrain);
			renderer.processFunction(function3);
			
			//light.setPosition(camera.getPosition());

			planet.checkPlanetResolution();
			
			renderer.render(light, camera);
			TextMaster.render();
			var += 0.1;
			DisplayManager.updateDisplay();
			
		}
		UIMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
