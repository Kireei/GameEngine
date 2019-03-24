package ui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;

public class UIElement {
	private Vector3f position;
	private Vector3f rotation;
	private Vector2f scale;
	private RawModel rawModel = UIHandler.rawModel;

	private TexturedModel texModel;
	private Entity en;
	
	private boolean toggled = false;
	
	public UIElement(Vector3f position, Vector3f rotation, Vector2f scale, ModelTexture texture){
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.texModel = new TexturedModel(this.rawModel, texture);
		this.en = new Entity(texModel, new Vector3f(position.x - 1 + (scale.x*(9f/16f)), position.y + 0.9831f - scale.y, position.z), rotation.x, rotation.y, rotation.z, new Vector3f(scale.x, scale.y, 1));
		//this.en = new Entity(texModel, new Vector3f(position.x, position.y, position.z), rotation.x, rotation.y, rotation.z, new Vector3f(scale.x, scale.y, 1));
	}
	
	public void checkMouse() {
		double mX = (Mouse.getX() / (double)(Display.getWidth()) * 2 - 1);
		double mY = (Mouse.getY() / (double)(Display.getHeight()) * 2 - 1);
		float scaleX = this.scale.x;
		float scaleY = this.scale.y;
		if(Mouse.isButtonDown(0) && mX >= this.en.getPosition().x - (scaleX * 0.5626) && mX <= this.en.getPosition().x + (scaleX * 0.5626) && mY >= this.en.getPosition().y - scaleY && mY <= this.en.getPosition().y + scaleY){
			System.out.println(System.nanoTime());

		}
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


	public Vector3f getRotation() {
		return rotation;
	}


	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
}
