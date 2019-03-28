package ui;

public class SliderFunctions {
	public static int planetResolution = 16;
	public static float planetStep = 1;
	
	public static void function(String id, float fraction) {
		switch(id) {
		case "slider1":
			planetResolution = (int) (4 + Math.floor(128 * (fraction)));
			break;
		case "slider2":
			planetStep = (float) (5 * (fraction * 2 - 1));
			break;
		default:
			System.out.println("Function for given Slider-ID not found");
		}
	}
}