package ui;

public class TextValues {
	public static String getValue(String id) {
		switch(id) {
		case "slider1":
			return Integer.toString(SliderFunctions.planetResolution);
		case "slider2":
			return  Float.toString(Math.round((SliderFunctions.planetStep * 100)) / 100f);
		case "slider3":
			return Float.toString(Math.round((SliderFunctions.planetRadius * 100)) / 100f);
		case "slider4":
			return Float.toString(Math.round((SliderFunctions.planetAmplitude * 100)) / 100f);
		case "slider5":
			return Float.toString(Math.round((SliderFunctions.planetMinLevel * 100)) / 100f);
		case "slider6":
			return Float.toString(Math.round((SliderFunctions.planetSeaLevel * 100)) / 100f);
		case "testBox":
			return UIMaster.testText;
		default:
			return "";
		}
	}
}
