package ui;

import java.util.ArrayList;
import java.util.List;

public class UIMaster {
	public static List<UIElement> ui = new ArrayList<UIElement>();
	
	public static void loadUI(UIElement uie) {
		ui.add(uie);
		
	}
	
	public static void removeUI(UIElement uie) {
		ui.remove(uie);
	}
	
	public static void render() {
		
	}
	
	public static void cleanUp() {
	}
}
