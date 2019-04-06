package ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import renderEngine.MasterRenderer;

public class UIMaster {
	public static List<UIElement> uies = MasterRenderer.uies;
	public static List<UIElement> window;
	public static UIState uiState = UIState.NORMAL;
	private static String testText = new String();
	private static List<String> textInput = new ArrayList<String>();
	
	private static GUIText test = new GUIText(Long.toString(System.currentTimeMillis()), 1, UIHandler.font, new Vector2f(1,0), 50, false, false);
	
	public static UIElement activeText;
	
	public static void init() {
		window = UIHandler.createWindow(new Vector2f(5,15));
		TextMaster.loadText(test);
		UIHandler.openWindow(window);
		UIHandler.closeWindow(window);
		
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
						if(!window.get(1).isActive()) {UIHandler.openWindow(window); return;}
						if(window.get(1).isActive()) UIHandler.closeWindow(window);
					}
				}
			}
		} else if(uiState == UIState.TEXT_INPUT) {
			getTextInput();
		}
		updateText();
		for(UIElement uie: UIMaster.uies) {
			if(uie.isActive()) {
				uie.checkMouse();
			}
		}		
		
	}
	
	private static void getTextInput() {
		String text = testText;
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {
				if(Keyboard.getEventKey() == Keyboard.KEY_END) uiState = UIState.NORMAL;
				if(Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
					textInput.add(testText);
					testText = "";
				}else {
					testText = testText.concat(Keyboard.getKeyName(Keyboard.getEventKey()));
				}
				System.out.println(textInput);
				System.out.println(testText);
				if(testText.equals("STOP")) System.exit(0);
				
				
			}
		}
		
		//System.out.println(text);
	}
	private static void updateText() {
		for(GUIText text : window.get(1).getSliders().get(0)[2].getTexts()) {
			TextMaster.removeText(text);
		}
		//TextMaster.removeText((window.get(1).getSliders().get(0)[2].getTexts().get(0)));
		
		//test = new GUIText(Integer.toString(SliderFunctions.planetResolution), 1, UIHandler.font, new Vector2f(1,0), 50, false, false);
		window.get(1).getSliders().get(0)[2].getTexts().clear();
		window.get(1).getSliders().get(0)[2].createTitle(Integer.toString(SliderFunctions.planetResolution), 1f, new Vector2f(0.01f,0.5f*0.028f));
		
		for(GUIText text : window.get(1).getSliders().get(0)[2].getTexts()) {
			TextMaster.loadText(text);
		}
		//TextMaster.loadText(window.get(1).getSliders().get(0)[2].getTexts().get(0));
	}
}
