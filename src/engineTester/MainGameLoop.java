package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
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
import toolbox.Maths;
import ui.RadioButtonFunctions;
import ui.UIHandler;
import ui.UIMaster;

public class MainGameLoop {
	public static ModelTexture tex;
	public static void run() {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		UIHandler.init(loader);
		TextMaster.init(loader);
		UIMaster.init();
		RawModel model = OBJLoader.loadObjModel("cuby", loader);
	
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("chimp")));
		ModelTexture texture = staticModel.getTexture();
		tex = texture;
		texture.setShineDamper(0);
		texture.setReflectivity(0);
		
		
		Map mapReader = new Map();
		Image mapImage = new Image("map");
		
		int[] map = mapReader.readMap(mapImage);
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 5, -25),0,0,0, new Vector3f(1,1,1));
		Light light  = new Light(new Vector3f(1000000,100000, -200000), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("chimp")));
		
		Camera camera = new Camera();
	
		
		MasterRenderer renderer = new MasterRenderer(camera);
		Matrix4f planetMatrix = Maths.createTransformationsMatrix(new Vector3f(0,0,0), 0, 0, 0, new Vector3f(4,4,4));
		Planet planet = new Planet(renderer, loader, planetMatrix, texture);

		camera.setPosition(new Vector3f(0,0,20));
		
		float var = 0;
		
		
		Function function = new Function(0,0,loader, 0, FunctionTypes.SINE);

		Function function1 = new Function(0, 0, loader, 0, FunctionTypes.SINE);

		Function function3 = new Function(0,0, loader, 0, FunctionTypes.TRUESINE);
		

		float test = 0;
		float xd = 0;
		double firstFrameTime = 0;
		while(!Display.isCloseRequested()){
			double start = System.nanoTime();
			UIMaster.updateUI();
			/*if(xd < 100) {
				renderer.removeFunction(function1);
				function1 = new Function(0, 0, loader, xd, FunctionTypes.SINE);
				renderer.processFunction(function1);
			}*/
			camera.move();
			entity.setPosition(new Vector3f(20f * (float)Math.cos(var), 0, 20f*(float)Math.sin(var)));
			
			test += 0.5f;
			planet.settMatrix(Maths.createTransformationsMatrix(new Vector3f(0,0,0), 0, test, 0, new Vector3f(4,4,4)));
			
			renderer.processEntity(entity);
			renderer.processFunction(function3);
			//Planet.checkPlanetResolution();
			
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
			xd += 0.1f;
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
