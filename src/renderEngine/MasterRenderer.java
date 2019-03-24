package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import functions.Function;
import models.TexturedModel;
import shaders.FunctionShader;
import shaders.StaticShader;
import shaders.TerrainShader;
import shaders.UIShader;
import terrain.Sphere;
import terrain.Terrain;
import terrain.TerrainFace;
import ui.UIElement;
import ui.UIRenderer;

public class MasterRenderer {
	
	private static final float FOV = 90;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private FunctionRenderer functionRenderer;
	private FunctionShader functionShader = new FunctionShader();
	
	private UIRenderer uiRenderer;
	private UIShader uiShader = new UIShader();
	
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private List<Function> functions = new ArrayList<Function>();
	private List<Sphere> spheres = new ArrayList<Sphere>();
	private List<TerrainFace> tfs = new ArrayList<TerrainFace>();
	public List<UIElement> uies = new ArrayList<UIElement>();

	
	
	public MasterRenderer(){
		GL11.glFrontFace(GL11.GL_CCW);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		functionRenderer = new FunctionRenderer(functionShader, projectionMatrix);
		uiRenderer = new UIRenderer(uiShader, projectionMatrix);

	}
	
	
	public void render(Light sun, Camera camera){
		prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		
		terrainShader.start();
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainRenderer.renderSpheres(spheres);
		terrainRenderer.renderTerrainFaces(tfs);
		terrainShader.stop();
		
		functionShader.start();
		functionShader.loadLight(sun);
		functionShader.loadViewMatrix(camera);
		functionRenderer.render(functions);
		functionShader.stop();
		
		uiShader.start();
		uiRenderer.render(uies);
		uiShader.stop();

		functions.clear();
		terrains.clear();
		entities.clear();
		
		
	}
	
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	public void processSphere(Sphere sphere) {
		spheres.add(sphere);
	}
	
	public void processFunction(Function function) {
		functions.add(function);
	}
	
	public void removeFunction(Function function) {
		functions.remove(function);
	}
	
	public void processTerrainFace(TerrainFace tf) {
		tfs.add(tf);
	}
	
	public void removeTerrainFace(TerrainFace tf) {
		tfs.remove(tf);
	}
	
	public void processUIE(UIElement uie) {
		uies.add(uie);
	}
	public void removeUIE(UIElement uie) {
		uies.remove(uie);
	}
	
	
	
	public void processEntity(Entity entity){
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null){
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);
	}
	
	private void createProjectionMatrix(){
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale  / aspectRatio;
		float frustrum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustrum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustrum_length);
		projectionMatrix.m33 = 0;
		
	}
	
	
	
	
	public void cleanUp(){
		shader.cleanUp();
		terrainShader.cleanUp();
		functionShader.cleanUp();
	}

}
