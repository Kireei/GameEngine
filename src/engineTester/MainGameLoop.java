package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
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
import ui.RadioButtonFunctions;
import ui.UIHandler;
import ui.UIMaster;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		UIHandler.init(loader);
		TextMaster.init(loader);
		UIMaster.init();
		RawModel model = OBJLoader.loadObjModel("cuby", loader);
	
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("chimp")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10000);
		texture.setReflectivity(100);
		
		
		Map mapReader = new Map();
		Image mapImage = new Image("map");
		
		int[] map = mapReader.readMap(mapImage);
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 5, -25),0,0,0, new Vector3f(1,1,1));
		Light light  = new Light(new Vector3f(0,0, -200), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("chimp")));
		
		Camera camera = new Camera();
	
		
		MasterRenderer renderer = new MasterRenderer();
		Planet planet = new Planet(renderer, loader, 16, texture);

		camera.setPosition(new Vector3f(0,0,20));
		
		float var = 0;
		
		
		Function function = new Function(0,0,loader, 0, FunctionTypes.SINE);

		Function function1 = new Function(0, 0, loader, 0, FunctionTypes.SINE);

		Function function3 = new Function(0,0, loader, 0, FunctionTypes.TRUESINE);
		
		double firstFrameTime = 0;
		while(!Display.isCloseRequested()){
			double start = System.nanoTime();
			
			UIMaster.updateUI();
			if(var < 100) {
				//renderer.removeFunction(function1);
				//function1 = new Function(0, 0, loader, var, FunctionTypes.E);
				//renderer.processFunction(function1);
			}
			camera.move();

			renderer.processFunction(function3);

			planet.checkPlanetResolution();
			light.setPosition(new Vector3f(2000f * (float)Math.cos(var), 0, 2000f*(float)Math.sin(var)));
			if(RadioButtonFunctions.rotateLight)var += 0.009;
			
			renderer.render(light, camera);
			TextMaster.render();
			DisplayManager.updateDisplay();
			
			double done = System.nanoTime();
			if(firstFrameTime / Math.pow(10, 9) <= 1) {
				firstFrameTime += done - start;	
			}else{
				firstFrameTime = 0;
				System.out.println(Math.round(1 / ((done - start) / Math.pow(10, 9))));
			}
			
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
