package renderEngine;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class FunctionShader extends ShaderProgram{
	
	private static final String VERTEX_SHADER = "src/shaders/functionVertexShader.txt";
	private static final String FRAGMENT_SHADER = "src/shaders/functionFragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	public FunctionShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		
	}

	
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "color");
		
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

}
