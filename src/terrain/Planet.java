package terrain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;
import toolbox.Maths;
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
	private float levels;
	private float persistance;
	private float lacunarity;
	private ModelTexture texture;
	private MasterRenderer renderer;
	private Matrix4f tMatrix;
	
	public Planet(MasterRenderer renderer, Loader loader, Matrix4f tMatrix, ModelTexture texture) {
		this.loader = loader;
		this.resolution = 16;
		this.texture = texture;
		this.renderer = renderer;
		this.radius = SliderFunctions.planetRadius;
		this.step = 1;
		this.amplitude = SliderFunctions.planetAmplitude;
		this.minLevel = 0;
		this.tMatrix = tMatrix;
		texture.setReflectivity(1);
		texture.setShineDamper(100);
		directions[0] = new Vector3f(0,1,0);
		directions[1] = new Vector3f(0,-1,0);
		directions[2] = new Vector3f(-1,0,0);
		directions[3] = new Vector3f(1,0,0);
		directions[4] = new Vector3f(0,0,1);
		directions[5] = new Vector3f(0,0,-1);
		this.tMatrix = tMatrix;
		for(int i = 0; i < directions.length; i++) {
			tfs.add(generate(directions[i]));
			tfs.get(i).settMatrix(this.tMatrix);
			planet.add(tfs.get(i).getEn());
			renderer.processTerrainFace(tfs.get(i));
		}

	}
	
	public TerrainFace generate(Vector3f localUp) {
		return new TerrainFace(loader, resolution, radius, step, amplitude, minLevel, seaLevel, levels, persistance, lacunarity, localUp, tMatrix, texture);
	}
 
	
	
	public void checkPlanetResolution() {
		int res = SliderFunctions.planetResolution;
		float s = SliderFunctions.planetStep;
		float r = SliderFunctions.planetRadius;
		float a = SliderFunctions.planetAmplitude;
		float mL = SliderFunctions.planetMinLevel;
		float sL = SliderFunctions.planetSeaLevel;
		int l = SliderFunctions.planetLevels;
		float p = SliderFunctions.planetPersistance;
		float lac = SliderFunctions.planetLacunarity;
		if(res < 3) res = 3;
		if(res == resolution && s == step && r == radius && a == amplitude && mL == minLevel && sL == seaLevel && l == levels && p == persistance && lac == lacunarity) {
			for(TerrainFace tf: tfs) {
				tf.settMatrix(tMatrix);
			}
			return;
		} else {
			resolution = res;
			step = s;
			radius = r;
			amplitude = a;
			minLevel = mL;
			seaLevel = sL;
			levels = l;
			persistance = p;
			lacunarity = lac;
			for(int i = 0; i < directions.length; i++) {
				renderer.removeTerrainFace(tfs.get(i));
				tfs.remove(i);
				tfs.add(i, generate(directions[i]));
				renderer.processTerrainFace(tfs.get(i));
			}
		}
		for(TerrainFace tf: tfs) {
			tf.settMatrix(tMatrix);
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

	public Matrix4f gettMatrix() {
		return tMatrix;
	}

	public void settMatrix(Matrix4f tMatrix) {
		this.tMatrix = tMatrix;
	}

	
	
}
