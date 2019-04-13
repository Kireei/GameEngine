package terrain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;
import ui.SliderFunctions;

public class Planet {
	private Vector3f[] directions = new Vector3f[6];
	private List<TerrainFace> tfs = new ArrayList<TerrainFace>();
	private List<Entity> planet = new ArrayList<Entity>();
	private Loader loader;
	private int resolution;
	private float radius;
	private float step;
	private float amplitude;
	private float minLevel;
	private float seaLevel;
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
		this.minLevel = 0;
		texture.setReflectivity(1);
		texture.setShineDamper(100);
		directions[0] = new Vector3f(0,1,0);
		directions[1] = new Vector3f(0,-1,0);
		directions[2] = new Vector3f(-1,0,0);
		directions[3] = new Vector3f(1,0,0);
		directions[4] = new Vector3f(0,0,1);
		directions[5] = new Vector3f(0,0,-1);
		
		for(int i = 0; i < directions.length; i++) {
			tfs.add(generate(directions[i]));
			planet.add(tfs.get(i).getEn());
			renderer.processTerrainFace(tfs.get(i));
		}

	}
	
	public TerrainFace generate(Vector3f localUp) {
		return new TerrainFace(loader, resolution, radius, step, amplitude, minLevel, seaLevel, localUp, texture);
	}
 
	
	
	public void checkPlanetResolution() {
		int res = SliderFunctions.planetResolution;
		float s = SliderFunctions.planetStep;
		float r = SliderFunctions.planetRadius;
		float a = SliderFunctions.planetAmplitude;
		float mL = SliderFunctions.planetMinLevel;
		float sL = SliderFunctions.planetSeaLevel;
		if(res < 3) res = 3;
		if(res == resolution && s == step && r == radius && a == amplitude && mL == minLevel && sL == seaLevel) {
			return;
		} else {
			resolution = res;
			step = s;
			radius = r;
			amplitude = a;
			minLevel = mL;
			seaLevel = sL;
			for(int i = 0; i < directions.length; i++) {
				renderer.removeTerrainFace(tfs.get(i));
				tfs.remove(i);
				tfs.add(i, generate(directions[i]));
				renderer.processTerrainFace(tfs.get(i));
			}
		}
		
	}

	public List<Entity> getPlanet() {
		return planet;
	}

	public void setPlanet(List<Entity> planet) {
		this.planet = planet;
	}

	public List<TerrainFace> getTfs() {
		return tfs;
	}

	public void setTfs(List<TerrainFace> tfs) {
		this.tfs = tfs;
	}

	
	
}
