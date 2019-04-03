package ui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import renderEngine.MasterRenderer;

public class UIMaster {
	public static List<UIElement> uies = MasterRenderer.uies;
	public static List<UIElement> window;
	public static UIState uiState = UIState.NORMAL;
	private static String testText = new String();
	
	public static void init() {
		window = UIHandler.createWindow(new Vector2f(5,15));
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
					testText = testText.concat(" ");
				}else {
					testText = testText.concat(Keyboard.getKeyName(Keyboard.getEventKey()));
				}
				System.out.println(testText);
				if(testText.equals("STOP")) System.exit(0);
				
				
			}
		}
		
		//System.out.println(text);
	}
}
