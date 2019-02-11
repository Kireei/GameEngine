package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {
	
	public static Matrix4f createTransformationsMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}
	public static Matrix4f createViewMatrix(Camera camera){
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static float factorial(float n) {
		float final_answer = 1;
		if(n == 0) return 1;
		for(float i = n; i > 0; i--) {
			final_answer *= i;
		}
		return final_answer;
	}
	
	public static double macLaurinSine(float var, float grade) {
		double expression = 0;
		for(int i = 0; i < grade; i++) {
			expression += (Math.pow(-1, i) * Math.pow(var, 2 * i + 1)) / (factorial(2 * i + 1));
		}
		
		return expression;
	}
	
	public static double macLaurinArcTan(float var, float grade) {
		double expression = 0;
		for(int i = 0; i < grade; i++) {
			expression += (Math.pow(-1, i) * Math.pow(var, 2 * i + 1)) / (2 * i + 1);
		}
		return expression;
	}
	
	public static double macLaurinE(float var, float grade) {
		double expression = 0;
		for(int i = 0; i < grade; i++) {
			expression += Math.pow(var, i) / factorial(i);
		}
		return expression;
	}
	
	public static double macLaurinCos(float var, float grade) {
		double expression = 0;
		for(int i = 0; i < grade; i++) {
			expression += (Math.pow(-1, i) * Math.pow(var, 2 * i)) / (factorial(2 * i));
		}
		
		return expression;
	}
	
}
