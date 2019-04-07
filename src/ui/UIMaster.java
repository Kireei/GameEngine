package ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import renderEngine.MasterRenderer;
import windows.ColorPicker;
import windows.Controller;

public class UIMaster {
	public static List<UIElement> uies = MasterRenderer.uies;
	
	public static List<UIElement> controller;
	public static List<UIElement> colorPicker;
	
	public static UIState uiState = UIState.NORMAL;
	public static String testText = new String();
	private static List<String> words = new ArrayList<String>();
	static List<List<UIElement>> activeWindows = new ArrayList<List<UIElement>>();
	
	public static UIElement activeText;
	
	public static void init() {
		controller = Controller.createWindow();
		colorPicker = ColorPicker.createWindow();
		
	}
	
	public static void updateUI() {
		if(uiState == UIState.NORMAL) {
			while(Keyboard.next()) {
				if(Keyboard.getEventKeyState()) {
					if(Keyboard.getEventKey() == Keyboard.KEY_G) {
						
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_E) {
						uiState = UIState.TEXT_INPUT;
						
					}
				} else {
					if(Keyboard.getEventKey() == Keyboard.KEY_G) {
						if(!controller.get(1).isActive()) {
							UIHandler.openWindow(controller);
							return;
							}
						if(controller.get(1).isActive()) {
							UIHandler.closeWindow(controller);
							
						}
					}
				}
			}
		} else if(uiState == UIState.TEXT_INPUT) {
			getTextInput();
		}
		
		if(activeWindows.size() > 0) {
			for(UIElement uie: activeWindows.get(activeWindows.size() - 1)) {
				if(uie.isActive()) {
					uie.checkMouse();
				}
			}		
		}
	}
	
	private static void getTextInput() {
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {
				if(Keyboard.getEventKey() == Keyboard.KEY_END) uiState = UIState.NORMAL;
				if(Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
					words.add(testText);
					testText = "";
				}else {
					testText = testText.concat(Keyboard.getKeyName(Keyboard.getEventKey()));
				}
				System.out.println(words);
				System.out.println(testText);
				if(testText.equals("STOP")) System.exit(0);
				
				
			}
		}
		
	}
	public static void updateText(UIElement uie) {
		List<GUIText> texts = uie.getTexts();
		Vector3f color = new Vector3f(1,1,1);
		for(GUIText text : texts) {
			color = text.getColour();
			TextMaster.removeText(text);
		}
		
		texts.clear();
	
		uie.createTitle(TextValues.getValue(uie.getId()), 0.5f, new Vector2f(0.01f,0.5f*0.028f), color);
		
		
		for(GUIText text : texts) {
			TextMaster.loadText(text);
		}

	}
	public static void clearUpdatedText() {
		for(GUIText text : controller.get(1).getSliders().get(0)[2].getTexts()) {
			TextMaster.removeText(text);
		}
	}
}
