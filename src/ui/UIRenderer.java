package ui;

import java.util.List;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import models.RawModel;
import models.TexturedModel;
import shaders.UIShader;
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
		
		for(UIElement uie: uies) {
			prepare();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, uie.getEn().getModel().getTexture().getID());
			shader.loadColor(new Vector4f(0, 0, 0, 0));
			renderUI(uie);
		}
	}
	
	public void renderUI(UIElement uie) {
		TexturedModel model = uie.getEn().getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
    	GL20.glEnableVertexAttribArray(0);
    	GL20.glEnableVertexAttribArray(1);
    	Matrix4f tMatrix = Maths.createTransformationsMatrix(uie.getEn().getPosition(), 0, 0, 0, new Vector3f(uie.getScale().x , uie.getScale().y * (0.5625f), 1));
    	shader.loadTMatrix(tMatrix);
    	shader.loadTranslation(new Vector2f(uie.getPosition().x, uie.getPosition().y));
    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    	
    	GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL30.glBindVertexArray(0);
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
}
