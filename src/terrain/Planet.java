package terrain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;
import ui.SliderFunctions;

public class Planet {
	private Vector3f[] directions = new Vector3f[6];
	private TerrainFace[] tfs = new TerrainFace[6];
	private Loader loader;
	private int resolution;
	private float radius;
	private float step;
	private float amplitude;
	private ModelTexture texture;
	private MasterRenderer renderer;
	
	public Planet(MasterRenderer renderer, Loader loader, int resolution, ModelTexture texture) {
		this.loader = loader;
		this.resolution = resolution;
		this.texture = texture;
		this.renderer = renderer;
		this.radius = SliderFunctions.planetRadius;
		this.step = 1;
		this.amplitude = SliderFunctions.planetAmplitude;
		texture.setReflectivity(1);
		texture.setShineDamper(100);
		directions[0] = new Vector3f(0,1,0);
		directions[1] = new Vector3f(0,-1,0);
		directions[2] = new Vector3f(-1,0,0);
		directions[3] = new Vector3f(1,0,0);
		directions[4] = new Vector3f(0,0,1);
		directions[5] = new Vector3f(0,0,-1);
		
		for(int i = 0; i < directions.length; i++) {
			tfs[i] = generate(directions[i]);
			renderer.processTerrainFace(tfs[i]);
		}

	}
	
	public TerrainFace generate(Vector3f localUp) {
		return new TerrainFace(loader, resolution, radius, step, amplitude, localUp, texture);
	}
 
	
	
	public void checkPlanetResolution() {
		int res = SliderFunctions.planetResolution;
		float s = SliderFunctions.planetStep;
		float r = SliderFunctions.planetRadius;
		float a = SliderFunctions.planetAmplitude;
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1) || Keyboard.isKeyDown(Keyboard.KEY_1) ) {
			res -= 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2) || Keyboard.isKeyDown(Keyboard.KEY_2) ) {
			res = 16;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3) || Keyboard.isKeyDown(Keyboard.KEY_3) ) {
			res += 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4) || Keyboard.isKeyDown(Keyboard.KEY_4) ) {
			s -= 0.01f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5) || Keyboard.isKeyDown(Keyboard.KEY_5) ) {
			s = 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6) || Keyboard.isKeyDown(Keyboard.KEY_6) ) {
			s += 0.01f;
		}
		if(res < 3) res = 3;
		if(res == resolution && s == step && r == radius && a == amplitude) {
			return;
		} else {
			resolution = res;
			step = s;
			radius = r;
			amplitude = a;
			for(int i = 0; i < directions.length; i++) {
				renderer.removeTerrainFace(tfs[i]);
				tfs[i] = generate(directions[i]);
				renderer.processTerrainFace(tfs[i]);
			}
		}
		
	}
	
}
