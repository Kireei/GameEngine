package ui;

import org.lwjgl.util.vector.Vector3f;

public class SliderFunctions {
	public static int planetResolution = 16;
	public static float planetStep = 1;
	public static float planetRadius = 1;
	public static float planetAmplitude = 1;
	public static float planetMinLevel = 0;
	public static Vector3f planetColor = new Vector3f(1, 1, 1);
	public static float planetSeaLevel = 0;
	
	public static void function(String id, float fraction) {
		switch(id) {
		case "slider1":
			planetResolution = (int) (4 + Math.floor(128 * (fraction)));
			break;
		case "slider2":
			planetStep = (float) (10 * (fraction));
			break;
		case "slider3":
			planetRadius = (fraction) * 5 + 1;
			break;
		case "slider4":
			planetAmplitude = fraction * 5;
			break;
		case "slider5":
			planetMinLevel = fraction * 2 - 1;
			break;
		case "slider6":
			planetSeaLevel = fraction;
		case "sliderRed":
			planetColor.x = fraction;
			break;
		case "sliderGreen":
			planetColor.y = fraction;
			break;
		case "sliderBlue":
			planetColor.z = fraction;
			break;
		default:
			System.out.println("Function for given Slider-ID not found");
		}
	}
}
