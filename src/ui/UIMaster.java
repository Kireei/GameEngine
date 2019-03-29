package ui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import renderEngine.MasterRenderer;

public class UIMaster {
	public static List<UIElement> uies = MasterRenderer.uies;
	public static List<UIElement> window;
	
	public static void init() {
		window = UIHandler.createWindow(new Vector2f(5,15));
		UIHandler.openWindow(window);
		UIHandler.closeWindow(window);
		
	}
	
	public static void updateUI() {
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {
				if(Keyboard.getEventKey() == Keyboard.KEY_G) {
					System.out.println("close");
					
				}
			} else {
				if(Keyboard.getEventKey() == Keyboard.KEY_G) {
					System.out.println("close");
					if(!window.get(1).isActive()) {UIHandler.openWindow(window); return;}
					if(window.get(1).isActive())UIHandler.closeWindow(window);
				}
			}
		}
		
		
		for(UIElement uie: UIMaster.uies) {
			if(uie.isActive()) {
				uie.checkMouse();
			}
		}
		
	
		
	}
}
