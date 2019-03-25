package ui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import models.RawModel;
import shaders.UIShader;
import textures.ModelTexture;
import toolbox.Maths;

public class UIRenderer {
	private UIShader shader;
	public UIRenderer(UIShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadPMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<UIElement> uies) {
		prepare();
		for(UIElement uie: uies) {
			prepareUI(uie);
			shader.loadColor(new Vector4f(0, 0, 0, 0));
			renderUI(uie);
		}
	}
	
	public void renderUI(UIElement uie) {

    	Matrix4f tMatrix = Maths.createTransformationsMatrix(uie.getEn().getPosition(), uie.getEn().getRotX(), uie.getEn().getRotY(), uie.getEn().getRotZ(), new Vector3f(uie.getScale().x , uie.getScale().y, 1));
    	shader.loadTMatrix(tMatrix);
    	shader.loadTranslation(new Vector2f(uie.getPosition().x, uie.getPosition().y));
    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    	
    	GL11.glDrawElements(GL11.GL_TRIANGLES, uie.getTexModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL30.glBindVertexArray(0);
	}
	
	private void prepareUI(UIElement uie) {
		RawModel rawModel = uie.getEn().getModel().getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		
		ModelTexture texture = uie.getTexModel().getTexture();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
}
