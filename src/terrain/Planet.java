package terrain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;

public class Planet {
	private Vector3f[] directions = new Vector3f[6];
	private TerrainFace[] tfs = new TerrainFace[6];
	private Loader loader;
	private int resolution;
	private ModelTexture texture;
	private MasterRenderer renderer;
	public Planet(MasterRenderer renderer, Loader loader, int resolution, ModelTexture texture) {
		this.loader = loader;
		this.resolution = resolution;
		this.texture = texture;
		this.renderer = renderer;
		texture.setReflectivity(1);
		texture.setShineDamper(100);
		directions[0] = new Vector3f(0,1,0);
		directions[1] = new Vector3f(0,-1,0);
		directions[2] = new Vector3f(-1,0,0);
		directions[3] = new Vector3f(1,0,0);
		directions[4] = new Vector3f(0,0,1);
		directions[5] = new Vector3f(0,0,-1);
		
		for(int i = 0; i < directions.length; i++) {
			tfs[i] = generate(resolution, directions[i]);
			renderer.processTerrainFace(tfs[i]);
		}

	}
	
	public TerrainFace generate(int resolution, Vector3f localUp) {
		return new TerrainFace(loader, resolution, localUp, texture);
	}
	
	public void checkPlanetResolution() {
		int res = resolution;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1) && res >= 3) {
			res -= 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
			res = 16;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) {
			res += 1;
		}
		if(res == resolution) {
			return;
		} else {
			resolution = res;
			
			for(int i = 0; i < directions.length; i++) {
				renderer.removeTerrainFace(tfs[i]);
				tfs[i] = generate(resolution, directions[i]);
				renderer.processTerrainFace(tfs[i]);
			}
		}
		
	}
	
}
