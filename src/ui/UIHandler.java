package ui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class UIHandler {
	private Loader loader;
	public static RawModel rawModel;
	public UIElement uie;
	public UIHandler(Loader loader, Camera camera) {
		this.loader = loader;
		rawModel = rawModel();
		this.uie = new UIElement(new Vector3f(0, 0, 0), new Vector2f(1, 1), new ModelTexture(loader.loadTexture("chimp")));
	}
	private RawModel rawModel() {
		float aspectRatio = 9f / 16f;
		float[] positions = {-aspectRatio, 1.0f, 0.0f, -aspectRatio, -1.0f, 0.0f, aspectRatio, -1.0f, 0.0f, aspectRatio, 1.0f, 0.0f};
		float[] textureCoords = {0, 0, 0, 1, 1, 1, 1, 0};
		float[] normals = {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1};
		int[] indices = {0, 1, 2, 0, 2, 3};
		
		return loader.loadToVAO(positions, textureCoords, normals, indices);
	}
}
