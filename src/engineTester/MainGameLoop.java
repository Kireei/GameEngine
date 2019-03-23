package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
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
import ui.UIHandler;
import ui.UIMaster;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
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
		UIHandler uih = new UIHandler(loader, camera);
		
		camera.setPosition(new Vector3f(0,0,20));
		
		float var = 0;

		Function function = new Function(0,0,loader, 0, FunctionTypes.SINE);

		Function function1 = new Function(0, 0, loader, 0, FunctionTypes.SINE);
		//Function function2 = new Function(0, 0, loader, 0, FunctionTypes.HLINE);
		Function function3 = new Function(0,0, loader, 0, FunctionTypes.TRUESINE);
		//renderer.processFunction(function2);
		
		while(!Display.isCloseRequested()){
			renderer.processUIE(uih.uie);
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
			var += 0.1;
			DisplayManager.updateDisplay();
			
		}
		UIMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
