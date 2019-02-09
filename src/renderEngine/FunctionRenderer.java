package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import entities.Function;
import models.RawModel;
import shaders.FunctionShader;
import toolbox.Maths;

public class FunctionRenderer {
	private FunctionShader shader;
	
	public FunctionRenderer(FunctionShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Function> functions) {
		for(Function function: functions) {
			prepareFunction(function);
			loadModelMatrix(function);
			GL11.glDrawArrays(GL11.GL_LINES, 0, function.getModel().getVertexCount());
			//GL11.glDrawElements(GL11.GL_LINE, function.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	private void prepareFunction(Function function) {
		RawModel rawModel = function.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Function function) {
		Matrix4f transformationMatrix = Maths.createTransformationsMatrix(function.getPosition(), 0, 0, 0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
