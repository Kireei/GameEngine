package ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import fontMeshCreator.GUIText;
import models.RawModel;
import models.TexturedModel;
import renderEngine.MasterRenderer;
import textures.ModelTexture;

public class UIElement {
	private Vector3f position;
	private Vector3f rotation;
	private Vector2f scale;
	private RawModel rawModel = UIHandler.rawModel;

	private TexturedModel texModel;
	private Entity en;
	
	private GUIText title = null;
	private List<UIElement> radioButtons;
	
	private boolean toggled = false;
	
	public UIElement(Vector3f position, Vector3f rotation, Vector2f scale, ModelTexture texture){
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.texModel = new TexturedModel(this.rawModel, texture);
		this.radioButtons = new ArrayList<UIElement>();
		this.en = new Entity(texModel, new Vector3f(position.x - 1 + (scale.x*(9f/16f)), position.y + 0.9831f - scale.y, position.z), rotation.x, rotation.y, rotation.z, new Vector3f(scale.x, scale.y, 1));
		//this.en = new Entity(texModel, new Vector3f(position.x, position.y, position.z), rotation.x, rotation.y, rotation.z, new Vector3f(scale.x, scale.y, 1));
	}
	
	public void checkMouse() {
		double mX = (Mouse.getX() / (double)(Display.getWidth()) * 2 - 1);
		double mY = (Mouse.getY() / (double)(Display.getHeight()) * 2 - 1);
		float scaleX = this.scale.x;
		float scaleY = this.scale.y;
		/*if(Mouse.isButtonDown(0) && mX >= this.en.getPosition().x - (scaleX * 0.5626) && mX <= this.en.getPosition().x + (scaleX * 0.5626) && mY >= this.en.getPosition().y - scaleY && mY <= this.en.getPosition().y + scaleY){
			if(title != null) {
				title.setColour(1, 1, 0);
			}
		}*/
		
		UIElement clickedRadioButton = null;
		
			
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0) {
		            System.out.println("Left button pressed");
		        }
		    }else {
		        if (Mouse.getEventButton() == 0) {
		            System.out.println("Left button released");
		            for(UIElement radioB: radioButtons) {
		            	if(mX >= radioB.en.getPosition().x - (radioB.scale.x * 0.5626) && mX <= radioB.en.getPosition().x + (radioB.scale.x * 0.5626) && mY >= radioB.en.getPosition().y - radioB.scale.y + 0.015f && mY <= radioB.en.getPosition().y + radioB.scale.y + 0.015f){
		    				clickedRadioButton = radioB;
		    				System.out.println("Clicked");
		    				break;		
		    			}
		            }
		        }
			}
			
		}
		if(clickedRadioButton != null) {
			if(clickedRadioButton.getTexModel().getTexture() == UIHandler.radioButtonUnchecked) {
				UIElement newButton = new UIElement(clickedRadioButton.position, clickedRadioButton.rotation, clickedRadioButton.scale, UIHandler.radioButtonChecked);
				newButton.radioButtons = clickedRadioButton.radioButtons;
				if(newButton.getTitle() != null && newButton.getTitle().getTextString() == "test") System.out.println("test");
				
				
				int index = radioButtons.lastIndexOf(clickedRadioButton);
				radioButtons.remove(clickedRadioButton);
				radioButtons.add(index, newButton);
				MasterRenderer.processUIE(newButton);
				MasterRenderer.removeUIE(clickedRadioButton);
				
			}else if(clickedRadioButton.getTexModel().getTexture() == UIHandler.radioButtonChecked){
				UIElement newButton = new UIElement(clickedRadioButton.position, clickedRadioButton.rotation, clickedRadioButton.scale, UIHandler.radioButtonUnchecked);	
				int index = radioButtons.lastIndexOf(clickedRadioButton);
				radioButtons.remove(clickedRadioButton);
				
				radioButtons.add(index, newButton);
				MasterRenderer.processUIE(newButton);
				MasterRenderer.removeUIE(clickedRadioButton);
			}
			
			
		}
	}
	
	public void createTitle(String text, float size) {
		this.title = new GUIText(text, size, UIHandler.font, new Vector2f(position.x + 0.05f, position.y + scale.y / 2), 50, false, false);
	}
	public void createTitle(String text, float size, Vector2f adjustment) {
		this.title = new GUIText(text, size, UIHandler.font, new Vector2f(adjustment.x + position.x + 0.05f, adjustment.y - position.y + scale.y / 2), 50, false, false);
	}
	
	public void createRadioButtons(int number, Vector2f adjustment) {
		float size = 0.03f;
		for(int n = 0; n < number; n++) {
			UIElement radioButton = new UIElement(new Vector3f(adjustment.x + position.x, -adjustment.y - n * size * 2 + position.y ,0), this.rotation, new Vector2f(size, size), UIHandler.radioButtonUnchecked);
			//Vector3f.add(position, new Vector3f(adjustment.x, -adjustment.y,0), radioButton.position);
			radioButtons.add(radioButton);
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

	public GUIText getTitle() {
		return title;
	}

	public void setTitle(GUIText title) {
		this.title = title;
	}

	public List<UIElement> getRadioButtons() {
		return radioButtons;
	}

	public void setRadioButtons(List<UIElement> radioButtons) {
		this.radioButtons = radioButtons;
	}
}
