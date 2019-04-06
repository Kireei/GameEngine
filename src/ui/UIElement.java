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
import textures.ModelTexture;

public class UIElement {
	private Vector3f position;
	private Vector3f rotation;
	private Vector2f scale;
	private RawModel rawModel = UIHandler.rawModel;

	private TexturedModel texModel;
	private Entity en;
	
	private GUIText title;
	private String id = "";
	private float sliderAmount;
	private List<UIElement> radioButtons;
	private List<UIElement[]> sliders;
	private List<GUIText> texts;
	private List<UIElement[]> textBoxes;
	
	private UIElement savedElement;
	private int savedElementIndex = 0;
	

	private boolean active = false;
	private boolean toggled = false;
	
	public UIElement(Vector3f position, Vector3f rotation, Vector2f scale, ModelTexture texture){
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.texModel = new TexturedModel(this.rawModel, texture);
		this.radioButtons = new ArrayList<UIElement>();
		this.sliders = new ArrayList<UIElement[]>();
		this.texts = new ArrayList<GUIText>();
		this.textBoxes = new ArrayList<UIElement[]>();
		this.en = new Entity(texModel, new Vector3f(position.x - 1 + (scale.x*(9f/16f)), position.y + 0.9831f - scale.y, position.z), rotation.x, rotation.y, rotation.z, new Vector3f(scale.x, scale.y, 1));
		//this.en = new Entity(texModel, new Vector3f(position.x, position.y, position.z), rotation.x, rotation.y, rotation.z, new Vector3f(scale.x, scale.y, 1));
	}
	
	public void checkMouse() {
		if(active) {
			double mX = (Mouse.getX() / (double)(Display.getWidth()) * 2 - 1);
			double mY = (Mouse.getY() / (double)(Display.getHeight()) * 2 - 1);

			UIElement clickedElement = null;
				
			while (Mouse.next()){
			    if (Mouse.getEventButtonState()) {
			        if (Mouse.getEventButton() == 0) {
			            //System.out.println("Left button pressed");
			        	toggled = true;
			        	for(int index = 0; index < sliders.size(); index++) {
			        		Vector3f position = sliders.get(index)[3].getEn().getPosition();
			        		Vector3f scale = sliders.get(index)[3].getEn().getScale();
			        		if(mX >= position.x - (scale.x * 0.5626) && mX <= position.x + (scale.x * 0.5626) && mY >= position.y - scale.y + 0.015f && mY <= position.y + scale.y + 0.015f){
			    				clickedElement = sliders.get(index)[3]; 
			    				savedElement = clickedElement;
			    				savedElementIndex = index;
			    				break;		
			    			}
			        		savedElement = null;
			        	}
			        	
			        	
			        }
			    }else {
			        if (Mouse.getEventButton() == 0) {
			            //System.out.println("Left button released");
			        	toggled = false;
			            for(UIElement radioB: radioButtons) {
			            	if(mX >= radioB.en.getPosition().x - (radioB.scale.x * 0.5626) && mX <= radioB.en.getPosition().x + (radioB.scale.x * 0.5626) && mY >= radioB.en.getPosition().y - radioB.scale.y + 0.015f && mY <= radioB.en.getPosition().y + radioB.scale.y + 0.015f){
			    				clickedElement = radioB;
			    				break;		
			    			}
			            }
			            for(UIElement[] textBox: textBoxes) {
			            	for(UIElement textElement: textBox) {
			            		if(mX >= textElement.en.getPosition().x - (textElement.scale.x * 0.5626) && mX <= textElement.en.getPosition().x + (textElement.scale.x * 0.5626) && mY >= textElement.en.getPosition().y - textElement.scale.y + 0.015f && mY <= textElement.en.getPosition().y + textElement.scale.y + 0.015f){
				    				clickedElement = textElement;
				    				break;		
				    			}
			            	}
			            }
			        }
				}
				
			}
			if(clickedElement != null) {
				if(clickedElement.getTexModel().getTexture() == UIHandler.radioButtonUnchecked.getTexture()) {
					clickedElement.setTexModel(UIHandler.radioButtonChecked);
					RadioButtonFunctions.function(clickedElement.getId());
					
					
				}else if(clickedElement.getTexModel().getTexture() == UIHandler.radioButtonChecked.getTexture()){
					clickedElement.setTexModel(UIHandler.radioButtonUnchecked);
					RadioButtonFunctions.unFunction(clickedElement.getId());
				}
				if(clickedElement.getId() == "testBox") {
					UIMaster.activeText = clickedElement;
					UIMaster.uiState = UIState.TEXT_INPUT;
				}
				
			}else if(savedElement != null) {
				if(toggled && savedElement.getTexModel().getTexture() == UIHandler.slider.getTexture()) {
					savedElement.getEn().setPosition(new Vector3f((float) mX, savedElement.getEn().getPosition().y, 0));
					
					if(mX > sliders.get(savedElementIndex)[2].getEn().getPosition().x + UIHandler.sizeSlider / 2) {
						savedElement.getEn().setPosition(new Vector3f(sliders.get(savedElementIndex)[2].getEn().getPosition().x + UIHandler.sizeSlider / 2, savedElement.getEn().getPosition().y, 0));
					}
					if(mX < sliders.get(savedElementIndex)[0].getEn().getPosition().x - UIHandler.sizeSlider / 2) {
						savedElement.getEn().setPosition(new Vector3f(sliders.get(savedElementIndex)[0].getEn().getPosition().x - UIHandler.sizeSlider / 2, savedElement.getEn().getPosition().y, 0));
					}
					
					float leftBound = (sliders.get(savedElementIndex)[0].getEn().getPosition().x - UIHandler.sizeSlider / 2);
					float rightBound = (sliders.get(savedElementIndex)[2].getEn().getPosition().x + UIHandler.sizeSlider / 2);
					float size = rightBound - leftBound;
					savedElement.sliderAmount = (savedElement.getEn().getPosition().x - leftBound) / size;
					SliderFunctions.function(savedElement.getId(), savedElement.sliderAmount);
				}
			}
			
			
			
		}
	}
	
	public void createTitle(String text, float size) {
		GUIText titleText = new GUIText(text, size, UIHandler.font, new Vector2f(position.x + 0.05f, position.y + scale.y / 2), 50, false, false);
		texts.add(titleText);
		this.title = titleText;
		
		
	}
	public void createTitle(String text, float size, Vector2f adjustment) {
		GUIText titleText = new GUIText(text, size, UIHandler.font, new Vector2f(adjustment.x + position.x + 0.05f, adjustment.y - position.y + scale.y / 2), 50, false, false);
		texts.add(titleText);
		this.title = titleText;
	}
	
	public void updateTitle(GUIText title, String text) {
		texts.remove(title);
		GUIText newTitle = new GUIText(text ,title.getFontSize(), title.getFont(), title.getPosition(), 50, false, false);
		texts.add(newTitle);
		//this.title = newTitle;
	}
	
	public void createRadioButtons(int number, Vector2f adjustment) {
		float size = UIHandler.sizeRadioButton;
		for(int n = 0; n < number; n++) {
			UIElement radioButton = new UIElement(new Vector3f(adjustment.x + position.x, -adjustment.y - n * size * 2 + position.y ,0), this.rotation, new Vector2f(size, size), UIHandler.radioButtonUnchecked.getTexture());
			radioButtons.add(radioButton);
		}
	}
	
	public UIElement[] createSlider(float width, Vector2f adjustment, String id) {
		float size = UIHandler.sizeSlider;
		UIElement leftSlider = new UIElement(new Vector3f(adjustment.x + position.x, position.y - adjustment.y, 0), this.rotation, new Vector2f(size, size), UIHandler.leftSlider.getTexture());
		UIElement middleSlider = new UIElement(new Vector3f(adjustment.x + position.x + size, position.y - adjustment.y, 0), this.rotation, new Vector2f(size * (width - 2), size), UIHandler.middleSlider.getTexture());
		UIElement rightSlider = new UIElement(new Vector3f(adjustment.x + position.x + size * (width - 1), position.y - adjustment.y, 0), this.rotation, new Vector2f(size, size), UIHandler.rightSlider.getTexture());
		UIElement slider = new UIElement(new Vector3f(adjustment.x + position.x + size * 0.5f * width - size / 2, position.y - adjustment.y, 0), this.rotation, new Vector2f(size, size), UIHandler.slider.getTexture());
		slider.id = id;
		UIElement[] sliderArray = {leftSlider, middleSlider, rightSlider, slider};
		sliders.add(sliderArray);
		return sliderArray;
	}
	public static void addSlider(UIElement[] slider, List<UIElement> dest) {
		for(UIElement uie: slider) {
			dest.add(uie);
		}
	}
	public UIElement[] createTextBox(Vector2f adjustment, Vector2f scale, Vector2f size, String id) {
		float tileSize = 0.04f;
		float tileSpaceX = tileSize * 18f / 16f * size.x;
		float tileSpaceY = tileSize * 2  * size.y;
		float adjustX = adjustment.x + position.x;
		float adjustY = -(adjustment.y + position.y);
		
		UIElement TLCorner = new UIElement(new Vector3f(adjustX, adjustY,0), new Vector3f(0,0,0), new Vector2f(tileSize*size.x, tileSize * size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextCorner 1")));
		UIElement topEdge = new UIElement(new Vector3f(tileSpaceX + adjustX, adjustY, 0), new Vector3f(0,0,0), new Vector2f(tileSize*(scale.x-2) * size.x, tileSize* size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextEdge 1")));
		UIElement TRCorner = new UIElement(new Vector3f(tileSpaceX * (scale.x - 1) + adjustX, adjustY, 0), new Vector3f(0,0,0), new Vector2f(tileSize * size.x, tileSize* size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextCorner 2")));
		UIElement LEdge = new UIElement(new Vector3f(adjustX, -tileSpaceY + adjustY, 0), new Vector3f(0,0,0), new Vector2f(tileSize * size.x, tileSize * (scale.y - 2)* size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextEdge 2")));
		UIElement middle = new UIElement(new Vector3f(tileSpaceX + adjustX, -tileSpaceY + adjustY, 0), new Vector3f(0,0,0), new Vector2f(tileSize * (scale.x - 2) * size.x, tileSize * (scale.y - 2)* size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextBackground")));
		UIElement REdge = new UIElement(new Vector3f(tileSpaceX * (scale.x - 1) + adjustX, -tileSpaceY+ adjustY,0), new Vector3f(0,0,0), new Vector2f(tileSize * size.x, tileSize * (scale.y - 2)* size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextEdge 3")));
		UIElement LLCorner = new UIElement(new Vector3f( adjustX, -tileSpaceY * (scale.y - 1)+ adjustY,0), new Vector3f(0,0,0), new Vector2f(tileSize * size.x, tileSize* size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextCorner 3")));
		UIElement bottomEdge = new UIElement(new Vector3f(tileSpaceX+ adjustX, -tileSpaceY * (scale.y - 1)+ adjustY, 0), new Vector3f(0,0,0), new Vector2f(tileSize * (scale.x - 2) * size.x, tileSize* size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextEdge 4")));
		UIElement LRCorner = new UIElement(new Vector3f(tileSpaceX * (scale.x - 1)+ adjustX, -tileSpaceY * (scale.y - 1)+ adjustY,0), new Vector3f(0,0,0), new Vector2f(tileSize * size.x, tileSize* size.y), new ModelTexture(UIHandler.loader.loadTexture("GUI/TextCorner 4")));
		
		UIElement[] textBox = {TLCorner, topEdge, TRCorner, LEdge, middle, REdge, LLCorner, bottomEdge, LRCorner};
		for(UIElement uie: textBox) {
			uie.id = id;
		}
		textBoxes.add(textBox);
		return textBox;
	}
	
	public static void addTextBox(UIElement[] textBox, List<UIElement> dest) {
		for(UIElement uie: textBox) {
			dest.add(uie);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<UIElement[]> getSliders() {
		return sliders;
	}

	public void setSliders(List<UIElement[]> sliders) {
		this.sliders = sliders;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<GUIText> getTexts() {
		return texts;
	}

	public void setTexts(List<GUIText> texts) {
		this.texts = texts;
	}

	public float getSliderAmount() {
		return sliderAmount;
	}

	public void setSliderAmount(float sliderAmount) {
		this.sliderAmount = sliderAmount;
	}
}
