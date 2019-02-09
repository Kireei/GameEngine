package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Function;
import entities.Light;
import image.Image;
import map.Map;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;

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
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 5, -25),0,0,0,1);
		Light light  = new Light(new Vector3f(0,0, -20), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("chimp")));
		
		Camera camera = new Camera();
		
		List<Entity> boxes = new ArrayList<Entity>();
		
		MasterRenderer renderer = new MasterRenderer();
		
		List<Function> functions = new ArrayList<Function>();
		functions.add(new Function(new Vector3f(0, 0, 0), loader));
		
		int xPos = 0;
		int yPos = 0;
		
		for(int i = 0; i < map.length; i++){
			if(xPos >= mapImage.getW()){
				xPos = 0;
				yPos++;
			}
			if(map[i] == 0){
				xPos++;
				continue;
				}
			if(map[i] == 1){
				boxes.add(new Entity(staticModel, new Vector3f(xPos, yPos, 0),0,0,0,1));
				xPos++;	
				
			}
			
			
		}
		
		while(!Display.isCloseRequested()){
			camera.move();
			entity.increaseRotation(0, 1, 0);
			renderer.processTerrain(terrain);
			renderer.processEntity(entity);
			//light.setPosition(camera.getPosition());
			light.setPosition(new Vector3f(100, 50, -100));
			for(int i = 0; i < boxes.size(); i++){
				renderer.processEntity(boxes.get(i));
			}
			
			
			renderer.render(light, camera);
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
