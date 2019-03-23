package ui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class UIElement {
	private Vector3f position;
	private Vector2f scale;
	private RawModel rawModel = UIHandler.rawModel;
	private Loader loader;
	private TexturedModel texModel;
	private Entity en;
	
	private boolean toggled = false;
	
	public UIElement(Vector3f position, Vector2f scale, ModelTexture texture){
		this.position = position;
		this.scale = scale;
		this.texModel = new TexturedModel(this.rawModel, texture);
		this.en = new Entity(texModel, new Vector3f(position.x, position.y, position.z), 0,0,10, new Vector3f(scale.x, scale.y, 1));
	}

	
	public TexturedModel getTexModel() {
		return texModel;
	}

	public void setTexModel(TexturedModel texModel) {
		this.texModel = texModel;
	}

	public Entity getEn() {
		return en;
	}

	public void setEn(Entity en) {
		this.en = en;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
}
