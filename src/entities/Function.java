package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;

public class Function {
	private RawModel model;
	private Vector3f position;
	
	public Function(Vector3f position, Loader loader) {
		this.setPosition(position);
		this.setModel(generateFunction(loader));
	}

	private RawModel generateFunction(Loader loader) {
		int samples = 100;
		float[] vertices = new float[samples * 3];
		float[] colors = new float[samples * 3];
		int[] indices = new int[samples * 6];
		int vertexPointer = 0;
		
		for (int i = 0; i < samples; i++) {
			vertices[vertexPointer * 3] = i;
			vertices[vertexPointer * 3 + 1] = (float)(Math.sin(Math.toDegrees(i)));
			vertices[vertexPointer * 3 + 2] = 0;
			colors[vertexPointer * 3] = 1;
			colors[vertexPointer * 3 + 1] = 1;
			colors[vertexPointer * 3 + 2] = 1;
			vertexPointer++;
			
		}
		int pointer = 0;
		for (int x = 0; x < samples * 6; x += 6) {
			System.out.println(x);
			indices[pointer++] = pointer;
			indices[pointer++] = pointer;
			indices[pointer++] = pointer;
			indices[pointer++] = pointer-3;
			indices[pointer++] = pointer-4;
			indices[pointer++] = pointer-5;
			
		}
		return loader.loadToVAO(vertices, colors, indices);
	}

	public RawModel getModel() {
		return model;
	}

	public void setModel(RawModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
}
