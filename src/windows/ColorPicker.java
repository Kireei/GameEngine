package windows;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import ui.UIElement;
import ui.UIHandler;

public class ColorPicker {
	public static List<UIElement> createWindow(){
		List<UIElement> window = UIHandler.createWindow(new Vector2f(3,3), new Vector2f(0.35f,0.5f));
		UIElement topEdge = window.get(1);
		
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(-0.044f, 0.07f), "sliderRed"), window);
		topEdge.getSliders().get(0)[1].createTitle("R", 0.5f, new Vector2f(-0.095f,0.5f * 0.029f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(-0.044f, 0.14f), "sliderGreen"), window);
		topEdge.getSliders().get(1)[1].createTitle("G", 0.5f, new Vector2f(-0.095f,0.5f * 0.029f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(-0.044f, 0.21f), "sliderBlue"), window);
		topEdge.getSliders().get(2)[1].createTitle("B", 0.5f, new Vector2f(-0.095f,0.5f * 0.029f));
		UIHandler.openWindow(window);
		UIHandler.closeWindow(window);
		return window;
		
	}
}
