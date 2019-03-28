package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;

public class UIHandler {
	private static Loader loader;
	public static RawModel rawModel;
	public static FontType font;
	
	public static float sizeRadioButton = 0.03f;
	public static float sizeSlider = 0.03f;
	
	public static TexturedModel radioButtonUnchecked;
	public static TexturedModel radioButtonChecked;
	public static TexturedModel leftSlider;
	public static TexturedModel middleSlider;
	public static TexturedModel rightSlider;
	public static TexturedModel slider;
	
	public List<UIElement> uies;
	
	
	public UIHandler(Camera camera) {
		
		//this.uie = new UIElement(new Vector3f(0, 0, 0), new Vector2f(0.06f, 0.06f), new ModelTexture(loader.loadTexture("GUI/Corner")));
		//this.uies = createWindow(new Vector2f(5f, 15f));
	}
	
	public static void init(Loader actuallLoader) {
		loader = actuallLoader;
		rawModel = rawModel();
		font = new FontType(loader.loadFont("arial"), new File("res/Fonts/arial.fnt"));
		radioButtonUnchecked = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("GUI/Radiobutton Unclicked")));
		radioButtonChecked = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("GUI/Radiobutton Clicked")));
		leftSlider = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("GUI/Slider edge left")));
		middleSlider = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("GUI/Slider middle")));
		rightSlider = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("GUI/Slider edge right")));
		slider = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("GUI/Slider")));
		
	}
	private static RawModel rawModel() {
		float aspectRatio = 9f / 16f;
		float[] positions = {-aspectRatio, 1.0f, 0.0f, -aspectRatio, -1.0f, 0.0f, aspectRatio, -1.0f, 0.0f, aspectRatio, 1.0f, 0.0f};
		float[] textureCoords = {0, 0, 0, 1, 1, 1, 1, 0};
		float[] normals = {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1};
		int[] indices = {0, 1, 2, 0, 2, 3};
		
		return loader.loadToVAO(positions, textureCoords, normals, indices);
	}
	
	public static List<UIElement> createWindow(Vector2f scale) {
		float tileSize = 0.06f;
		float tileSpaceX = tileSize*18f/16f;
		float tileSpaceY = tileSize*2;
		
		
		
		List<UIElement> window = new ArrayList<UIElement>();
		UIElement TLCorner = new UIElement(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize), new ModelTexture(loader.loadTexture("GUI/Corner 1")));
		UIElement topEdge = new UIElement(new Vector3f(tileSpaceX, 0, 0), new Vector3f(0,0,0), new Vector2f(tileSize*(scale.x-2), tileSize), new ModelTexture(loader.loadTexture("GUI/Edge 1")));
		UIElement TRCorner = new UIElement(new Vector3f(tileSpaceX * (scale.x - 1), 0, 0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize), new ModelTexture(loader.loadTexture("GUI/Corner 2")));
		UIElement LEdge = new UIElement(new Vector3f(0, -tileSpaceY, 0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize * (scale.y - 2)), new ModelTexture(loader.loadTexture("GUI/Edge 2")));
		UIElement middle = new UIElement(new Vector3f(tileSpaceX, -tileSpaceY, 0), new Vector3f(0,0,0), new Vector2f(tileSize * (scale.x - 2), tileSize * (scale.y - 2)), new ModelTexture(loader.loadTexture("GUI/Background")));
		UIElement REdge = new UIElement(new Vector3f(tileSpaceX * (scale.x - 1), -tileSpaceY,0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize * (scale.y - 2)), new ModelTexture(loader.loadTexture("GUI/Edge 3")));
		UIElement LLCorner = new UIElement(new Vector3f(0, -tileSpaceY * (scale.y - 1),0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize), new ModelTexture(loader.loadTexture("GUI/Corner 3")));
		UIElement bottomEdge = new UIElement(new Vector3f(tileSpaceX, -tileSpaceY * (scale.y - 1), 0), new Vector3f(0,0,0), new Vector2f(tileSize * (scale.x - 2), tileSize), new ModelTexture(loader.loadTexture("GUI/Edge 4")));
		UIElement LRCorner = new UIElement(new Vector3f(tileSpaceX * (scale.x - 1), -tileSpaceY * (scale.y - 1),0), new Vector3f(0,0,0), new Vector2f(tileSize, tileSize), new ModelTexture(loader.loadTexture("GUI/Corner 4")));
		window.add(TLCorner);
		window.add(topEdge);
		window.add(TRCorner);
		window.add(LEdge);
		window.add(middle);
		window.add(REdge);
		window.add(LLCorner);
		window.add(bottomEdge);
		window.add(LRCorner);
		
		//topEdge.setActive(true);
		topEdge.createTitle("Detta ar en testmeny", 1, new Vector2f(0,0));
		//topEdge.getTitle().setColour(0, 0, 0);
		topEdge.createRadioButtons(5, new Vector2f(0, 0.4f));
		topEdge.getRadioButtons().get(0).createTitle("White / Black", 1, new Vector2f(0, 0));
		topEdge.getRadioButtons().get(0).setId("backgroundColor");
		topEdge.getRadioButtons().get(1).createTitle("Lines / Triangles", 1, new Vector2f(0, 0));
		topEdge.getRadioButtons().get(1).setId("renderingGeometry");
		topEdge.getRadioButtons().get(4).createTitle("Close this menu", 1, new Vector2f(0, 0));
		topEdge.getRadioButtons().get(4).setId("closeMenu");

		for(UIElement uie: topEdge.getRadioButtons()) {
			window.add(uie);
		}
		
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0, 1), "slider1"), window);
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0, 1.1f),"slider2"), window);

		return window;
	}
	
	public static void openWindow(List<UIElement> window) {
		window.get(1).setActive(true);
		for(int i = 0; i < window.size(); i++) {
			MasterRenderer.uies.add(window.get(i));
			
			for(UIElement[] slider: window.get(i).getSliders()) {
				MasterRenderer.uies.add(slider[0]);
				MasterRenderer.uies.add(slider[1]);
				MasterRenderer.uies.add(slider[2]);
				MasterRenderer.uies.add(slider[3]);
				for(int j = 0; j < 4; j++) {
					for(GUIText text: slider[j].getTexts()) {
						TextMaster.loadText(text);
					}
				}
			}
			
			
			for(UIElement rb: window.get(i).getRadioButtons()) {
				MasterRenderer.uies.add(rb);
				for(GUIText text: rb.getTexts()) {
					TextMaster.loadText(text);
				}
				
			}
			for(GUIText text: window.get(i).getTexts()) {
				TextMaster.loadText(text);
			}
			
		}
	}
	
	public static void closeWindow(List<UIElement> window) {
		for(int i = 0; i < window.size(); i++) {
			for(UIElement rb: window.get(i).getRadioButtons()) {
				MasterRenderer.uies.remove(rb);
				//if(rb.getTitle() != null)TextMaster.removeText(rb.getTitle());
				for(GUIText text: rb.getTexts()) {
					TextMaster.removeText(text);
					
				}
			}
			for(UIElement[] slider: window.get(i).getSliders()) {
				MasterRenderer.uies.remove(slider[0]);
				MasterRenderer.uies.remove(slider[1]);
				MasterRenderer.uies.remove(slider[2]);
				MasterRenderer.uies.remove(slider[3]);
				for(int j = 0; j < 4; j++) {
					for(GUIText text: slider[j].getTexts()) {
						
						TextMaster.removeText(text);
					}
				}
			}
			for(GUIText text: window.get(i).getTexts()) {
				TextMaster.removeText(text);
			}
			MasterRenderer.uies.remove(window.get(i));
			window.get(1).setActive(false);
		}
	}
	
	
	
	public void destroyWindow() {
		uies.clear();
	}
}
