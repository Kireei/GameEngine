package terrain;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;
import toolbox.OpenSimplexNoise;
import ui.SliderFunctions;

public class TerrainFace {
	private ModelTexture texture;
	private RawModel model;
	private int resolution;
	private float radius;
	private float step;
	private float amplitude;
	private float minLevel;
	private Vector3f localUp;
	private Vector3f axisA;
	private Vector3f axisB;
	
	private OpenSimplexNoise noise;
	
	public TerrainFace(Loader loader, int resolution, float radius, float step, float amplitude, float minLevel, Vector3f localUp, ModelTexture texture) {
		this.resolution = resolution;
		this.radius = radius;
		this.localUp = localUp;
		this.texture = texture;
		this.step = step;
		this.amplitude = amplitude;
		this.minLevel = minLevel;
		noise = new OpenSimplexNoise();
		
		axisA = new Vector3f(localUp.y, localUp.z, localUp.x);
		axisB = Vector3f.cross(localUp, axisA, axisB);
		
		this.model = constructMesh(loader);
	}
	
	public RawModel constructMesh(Loader loader) { 
		
		int count = resolution * resolution;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		float[] colors = new float[count * 3];
		int[] indices = new int[6 * (resolution - 1) * (resolution - 1)];
		int vertexPointer = 0;
		int triIndex = 0;
		
		
		
		for (int y = 0; y < resolution; y++) {
			for (int x = 0; x < resolution; x++) {
				int i = x + y * resolution;
				float pX = (float)x / (float)(resolution - 1);
				float pY = (float)y / (float)(resolution - 1);

				float scaleA = (pX - 0.5f) * 2;
				float scaleB = (pY - 0.5f) * 2;

				Vector3f pointOnUnitCube = new Vector3f(localUp.x + axisA.x * scaleA + axisB.x * scaleB, localUp.y + axisA.y * scaleA + axisB.y * scaleB, localUp.z + axisA.z * scaleA + axisB.z * scaleB);

				pointOnUnitCube.normalise();
				float noiseFactor = (float) noise.eval(pointOnUnitCube.x * step, pointOnUnitCube.y * step, pointOnUnitCube.z * step);

				pointOnUnitCube.scale(((noiseFactor) * amplitude) + radius);
				if(pointOnUnitCube.length() < (radius + amplitude * minLevel)) {
					pointOnUnitCube.normalise();
					pointOnUnitCube.scale(radius + amplitude * minLevel);
				}
				vertices[vertexPointer * 3] = pointOnUnitCube.x;
				vertices[vertexPointer * 3 + 1] = pointOnUnitCube.y;
				vertices[vertexPointer * 3 + 2] = pointOnUnitCube.z;
				normals[vertexPointer * 3] = pointOnUnitCube.x;
				normals[vertexPointer * 3 + 1] = pointOnUnitCube.y;
				normals[vertexPointer * 3 + 2] = pointOnUnitCube.z;
				textureCoords[vertexPointer * 2] = (float) x / ((float) resolution - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) y / ((float) resolution - 1);
				colors[vertexPointer * 3] = 1;
				colors[vertexPointer * 3 + 1] = 1;//(noiseFactor + 1) * 0.5f;
				colors[vertexPointer * 3 + 2] = 1;
				vertexPointer++;
				
				if (x != resolution - 1 && y != resolution - 1) {
					indices[triIndex] = i;
					indices[triIndex + 1] = i + resolution + 1;
					indices[triIndex + 2] = i + resolution;

					indices[triIndex + 3] = i;
					indices[triIndex + 4] = i + 1;
					indices[triIndex + 5] = i + resolution + 1;
                    triIndex += 6;
	                }
	            
				
				
			}
		}
		/*int pointer = 0;
		for (int gz = 0; gz < resolution - 1; gz++) {
			for (int gx = 0; gx < resolution - 1; gx++) {
				int topLeft = (gz * resolution) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * resolution) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomRight;
			}
		}*/
		return loader.loadToVAO(vertices, textureCoords, normals, colors, indices);
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
