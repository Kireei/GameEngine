package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import shaders.TerrainShader;
import terrain.Sphere;
import terrain.Terrain;
import terrain.TerrainFace;
import textures.ModelTexture;
import toolbox.Maths;

public class TerrainRenderer {
	private TerrainShader shader;
	public static int renderingGeometry = GL11.GL_LINE_STRIP;
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Terrain> terrains){
		for(Terrain terrain:terrains){
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			//GL11.glDrawElements(GL11.GL_LINES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	public void renderSpheres(List<Sphere> spheres){
		for(Sphere sphere:spheres){
			prepareSphere(sphere);
			loadModelMatrix(sphere);
			//GL11.glDrawElements(GL11.GL_TRIANGLES, sphere.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL11.glDrawElements(GL11.GL_LINES, sphere.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	public void renderTerrainFaces(List<TerrainFace> terrainFaces, Matrix4f toShadowSpaceMatrix){
		//shader.loadShadowMapSpaceMatrix(toShadowSpaceMatrix);
		for(TerrainFace terrainFace:terrainFaces){
			prepareTerrainFace(terrainFace);
			loadModelMatrix(terrainFace);
			//GL11.glDrawElements(GL11.GL_TRIANGLES, terrainFace.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL11.glDrawElements(renderingGeometry, terrainFace.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain){
		RawModel rawModel = terrain.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		ModelTexture texture = terrain.getTexture();
		shader.loadShineVariable(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void prepareSphere(Sphere sphere){
		RawModel rawModel = sphere.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		ModelTexture texture = sphere.getTexture();
		shader.loadShineVariable(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void prepareTerrainFace(TerrainFace terrainface){
		RawModel rawModel = terrainface.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(4);
		
		ModelTexture texture = terrainface.getTexture();
		shader.loadShineVariable(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void unbindTexturedModel(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(4);
		GL30.glBindVertexArray(0);
	}
	private void loadModelMatrix(Terrain terrain){
		Matrix4f transformationMatrix = Maths.createTransformationsMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, new Vector3f(1,1,1));
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	private void loadModelMatrix(Sphere sphere){
		Matrix4f transformationMatrix = Maths.createTransformationsMatrix(new Vector3f(1f, 0, 1f), 0, 0, 0, new Vector3f(4, 4, 4));
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	private void loadModelMatrix(TerrainFace terrainFace){
		//Matrix4f transformationMatrix = Maths.createTransformationsMatrix(new Vector3f(1f, 0, 1f), 0, 0, 0, new Vector3f(4,4,4));
		shader.loadTransformationMatrix(terrainFace.gettMatrix());
	}
}
