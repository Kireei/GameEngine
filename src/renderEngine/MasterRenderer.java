package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import functions.Function;
import models.TexturedModel;
import shaders.FunctionShader;
import shaders.StaticShader;
import shaders.TerrainShader;
import shaders.UIShader;
import shadows.ShadowMapMasterRenderer;
import terrain.Sphere;
import terrain.Terrain;
import terrain.TerrainFace;
import ui.SliderFunctions;
import ui.UIElement;
import ui.UIRenderer;

public class MasterRenderer {
	
	public static final float FOV = 45;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000f;
	
	public static Vector3f backgroundColor;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private ShadowMapMasterRenderer shadowRenderer;
	
	private FunctionRenderer functionRenderer;
	private FunctionShader functionShader = new FunctionShader();
	
	private UIRenderer uiRenderer;
	private UIShader uiShader = new UIShader();
	
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private List<Function> functions = new ArrayList<Function>();
	private List<Sphere> spheres = new ArrayList<Sphere>();
	private List<TerrainFace> tfs = new ArrayList<TerrainFace>();
	public static List<UIElement> uies = new ArrayList<UIElement>();

	
	
	public MasterRenderer(Camera camera){
		GL11.glFrontFace(GL11.GL_CCW);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		this.shadowRenderer = new ShadowMapMasterRenderer(camera);
		functionRenderer = new FunctionRenderer(functionShader, projectionMatrix);
		uiRenderer = new UIRenderer(uiShader, projectionMatrix);
		backgroundColor = new Vector3f(1, 1, 1);

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
		terrainShader.loadColor(SliderFunctions.planetColor);
		terrainShader.loadAmbientLight(SliderFunctions.ambientLight);
		terrainShader.loadShadowMap();
		terrainRenderer.render(terrains);
		terrainRenderer.renderSpheres(spheres);
		terrainRenderer.renderTerrainFaces(tfs, shadowRenderer.getToShadowMapSpaceMatrix());
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
	
	public static void processUIE(UIElement uie) {
		uies.add(uie);
	}
	public static void removeUIE(UIElement uie) {
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
		GL11.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
	}
	
	private void createProjectionMatrix(){
	  	projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
	
	public void renderShadowMap(List<TerrainFace> entityList, Light sun) {
		for(TerrainFace entity : entityList) {
			processTerrainFace(entity);
		}
		shadowRenderer.render(tfs, sun);
		entities.clear();
	}
	
	public int getShadowMapTexture(){
		return shadowRenderer.getShadowMap();
	}
	
	
	public void cleanUp(){
		shader.cleanUp();
		terrainShader.cleanUp();
		functionShader.cleanUp();
		terrainShader.cleanUp();
		uiShader.cleanUp();
		shadowRenderer.cleanUp();
	}

}
