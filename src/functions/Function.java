package functions;

import models.RawModel;
import renderEngine.Loader;
import toolbox.Maths;

public class Function {

	private static final float SIZE = 100;
	private static final int VERTEX_COUNT = 512;
	private static final float SPACE = SIZE / VERTEX_COUNT; 

	private float x;
	private float z;
	private RawModel model;


	public Function(int gridX, int gridZ, Loader loader, float var, FunctionTypes type) {
		this.x = gridX;
		this.z = gridZ;
		this.model = generateFunction(loader, var, type);
	}

	private RawModel generateFunction(Loader loader, float var, FunctionTypes type) {
		int count = VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] colors = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;

		for (int j = 0; j < VERTEX_COUNT; j++) {
			
			vertices[vertexPointer * 3] = SPACE * j - (SIZE / 2);
			float x = vertices[vertexPointer * 3];
			vertices[vertexPointer * 3 + 1] = (float) getFunction(type, x, var);
			vertices[vertexPointer * 3 + 2] = 0;
			colors[vertexPointer * 3] = 1 - var * var / 255;
			colors[vertexPointer * 3 + 1] = 0.5f;
			colors[vertexPointer * 3 + 2] = 0;
			vertexPointer++;
		}

		int pointer = 0;
		for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
			int first = gx;
			int second = first + 1;
			indices[pointer++] = first;
			indices[pointer++] = second;
			
		}
	
		return loader.loadToVAO(vertices, textureCoords, colors, indices);
	}
	
	private double getFunction(FunctionTypes type, float x, float var) {
		switch(type) {
		case SINE:
			return Maths.macLaurinSine(x, var);
		case ARCTAN:
			return Maths.macLaurinArcTan(x, var);
		case E:
			return Maths.macLaurinE(x, var);
		case COS:
			return Maths.macLaurinCos(x, var);
		case TRUESINE:
			return Math.sin(x);
		case TRUECOS:
			return Math.cos(x);
		default:
			return Maths.macLaurinSine(x, var);
		}
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

}
