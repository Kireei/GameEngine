package entities;

import models.RawModel;
import renderEngine.Loader;
import toolbox.Maths;

public class Function {

	private static final float SIZE = 200;
	private static final int VERTEX_COUNT = 512;
	private static final float SPACE = SIZE / VERTEX_COUNT; 

	private float x;
	private float z;
	private RawModel model;

	/*
	 * @param gridX 	X-coordinate of function
	 * @param gridZ 	Z-coordinate of function
	 * 
	 * @param loader 	loader
	 * @param var 		a variable to use as the maximum grade of a certain function
	 */
	
	
	public Function(int gridX, int gridZ, Loader loader, float var) {
		this.x = gridX;
		this.z = gridZ;
		this.model = generateFunction(loader, var);
		
		
	}

	private RawModel generateFunction(Loader loader, float var) {
		int count = VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] colors = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;

		for (int j = 0; j < VERTEX_COUNT; j++) {
			
			vertices[vertexPointer * 3] = SPACE * j -( SIZE / 2);//(float) j / ((float) SIZE - 1) * SIZE;
			float x = vertices[vertexPointer * 3];
			vertices[vertexPointer * 3 + 1] = (float) Maths.macLaurinSine(x, (int)Math.floor(var));//Maths.macLaurinSine(x, (int)Math.floor(var)); ;
			vertices[vertexPointer * 3 + 2] = 0;//(float) i / ((float) VERTEX_COUNT - 1) * SIZE;
			colors[vertexPointer * 3] = 1 - var * var / 255;
			colors[vertexPointer * 3 + 1] = 0.5f;//(var+2) * var / 255;
			colors[vertexPointer * 3 + 2] = 0;//var * var / 255;
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
