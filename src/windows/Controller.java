package windows;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import ui.UIElement;
import ui.UIHandler;

public class Controller {
	
	public static List<UIElement> createWindow() {
		List<UIElement> window = UIHandler.createWindow(new Vector2f(5,15), new Vector2f(0,0));
		UIElement topEdge = window.get(1);
		topEdge.createTitle("Controller", 1, new Vector2f(0,0));
		//topEdge.getTitle().setColour(0, 0, 0);
		topEdge.createRadioButtons(5, new Vector2f(-0.05f, 0.4f));
		topEdge.getRadioButtons().get(0).createTitle("White / Black", 1, new Vector2f(0, 0));
		topEdge.getRadioButtons().get(0).setId("backgroundColor");
		topEdge.getRadioButtons().get(1).createTitle("Lines / Triangles", 1, new Vector2f(0, 0));
		topEdge.getRadioButtons().get(1).setId("renderingGeometry");
		topEdge.getRadioButtons().get(2).createTitle("Rotate light", 1, new Vector2f(0, 0));
		topEdge.getRadioButtons().get(2).setId("rotateLight");

		for(UIElement uie: topEdge.getRadioButtons()) {
			window.add(uie);
		}
		
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1), "slider1"), window);
		topEdge.getSliders().get(0)[1].createTitle("Vertex Densitiy", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(0)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.06f),"slider2"), window);
		topEdge.getSliders().get(1)[1].createTitle("Frequency", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(1)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.12f),"slider3"), window);
		topEdge.getSliders().get(2)[1].createTitle("Radius", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(2)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.18f),"slider4"), window);
		topEdge.getSliders().get(3)[1].createTitle("Amplitude", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(3)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.24f),"slider5"), window);
		topEdge.getSliders().get(4)[1].createTitle("Min Level", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(4)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		topEdge.getSliders().get(4)[3].setSliderAmount(0);
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.30f),"slider6"), window);
		topEdge.getSliders().get(5)[1].createTitle("Sea Level", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(5)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.36f),"slider7"), window);
		topEdge.getSliders().get(6)[1].createTitle("Ambient", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(6)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.42f),"sliderLevels"), window);
		topEdge.getSliders().get(7)[1].createTitle("Levels", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(7)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.48f),"sliderPersistance"), window);
		topEdge.getSliders().get(8)[1].createTitle("Persistance", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(8)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		UIElement.addSlider(topEdge.createSlider(5, new Vector2f(0.05f, 1.54f),"sliderLacunarity"), window);
		topEdge.getSliders().get(9)[1].createTitle("Lacunarity", 0.5f, new Vector2f(-0.18f,0.5f*0.028f));
		topEdge.getSliders().get(9)[2].createTitle("value", 0.5f, new Vector2f(0.01f,0.5f*0.028f));
		
		
		UIElement.addTextBox(topEdge.createTextBox(new Vector2f(-0.035f,0.2f), new Vector2f(3,3), new Vector2f(2f,0.5f), "testBox"), window);
		
		UIHandler.openWindow(window);
		UIHandler.closeWindow(window);
		return window;

	}
}
