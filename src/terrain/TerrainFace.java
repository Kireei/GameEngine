package terrain;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class TerrainFace {
	private Loader loader;
	private ModelTexture texture;
	private RawModel model;
	private int resolution;
	private Vector3f localUp;
	private Vector3f axisA;
	private Vector3f axisB;
	
	public TerrainFace(Loader loader, int resolution, Vector3f localUp, ModelTexture texture) {
		this.loader = loader;
		this.resolution = resolution;
		this.localUp = localUp;
		this.texture = texture;
		
		axisA = new Vector3f(localUp.y, localUp.z, localUp.x);
		axisB = Vector3f.cross(localUp, axisA, axisB);
		
		this.model = constructMesh(loader);
	}
	
	public RawModel constructMesh(Loader loader) {
		Vector3f[] vertices = new Vector3f[resolution * resolution];
		int[] indices = new int[(resolution - 1) * (resolution - 1) * 6];
		int triIndex = 0;
		
		for(int y = 0; y < resolution; y++) {
			for(int x = 0; x < resolution; x++) {
				int i = x + y * resolution;
				
				Vector2f percent = new Vector2f(x,y);
				percent.scale(1 / (resolution - 1));
				Vector3f tempVec = new Vector3f(); 
				Vector3f.add((Vector3f) axisA.scale((percent.x - 0.5f) * 2f), (Vector3f) axisB.scale((percent.y - 0.5f) * 2f), localUp);
				Vector3f pointOnUnitCube = localUp;
				vertices[i] = pointOnUnitCube;
				
				if(x != resolution - 1 && y != resolution - 1) {
					indices[triIndex] = i;
					indices[triIndex + 1] = i + resolution +1;
					indices[triIndex + 2] = i + resolution;
					
					indices[triIndex + 3] = i;
					indices[triIndex + 4] = i + 1;
					indices[triIndex + 5] = i + resolution + 1;
					
					triIndex += 6;
				}
				
			}
		}
		float[] newVertices = new float[vertices.length * 3];
		float[] textureCoordinates = new float[vertices.length * 2];
		float[] normals = new float[vertices.length * 3];
		int counter = 0;
		for(int i = 0; i < vertices.length; i++) {
			newVertices[counter] = vertices[i].x;
			textureCoordinates[counter] = 1f;
			normals[counter] = 1f;
			newVertices[counter++] = vertices[i].y;
			textureCoordinates[counter] = 1f;
			normals[counter] = 1f;
			newVertices[counter++] = vertices[i].z;
			normals[counter] = 1f;
		}
		return loader.loadToVAO(newVertices, textureCoordinates, normals, indices);
		
		
	}

	public ModelTexture getTexture() {
		return texture;
	}

	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}

	public RawModel getModel() {
		return model;
	}

	public void setModel(RawModel model) {
		this.model = model;
	}
}
