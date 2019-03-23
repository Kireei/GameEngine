package terrain;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;
import toolbox.OpenSimplexNoise;

public class TerrainFace {
	private Loader loader;
	private ModelTexture texture;
	private RawModel model;
	private int resolution;
	private float step;
	private Vector3f localUp;
	private Vector3f axisA;
	private Vector3f axisB;
	
	private OpenSimplexNoise noise;
	
	public TerrainFace(Loader loader, int resolution, float step, Vector3f localUp, ModelTexture texture) {
		this.loader = loader;
		this.resolution = resolution;
		this.localUp = localUp;
		this.texture = texture;
		this.step = step;
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
				
				/*System.out.println("x: "+ x + " y: " + y);
				System.out.println("xper: "+ pX + " yper: " + pY);
				System.out.println("sA: " + scaleA + " sB: " + scaleB);
				System.out.println("pointOnUnitCube: " + pointOnUnitCube);
				System.out.println("AxisA: " + axisA);
				System.out.println("AxisB: " + axisB);
				System.out.println();*/
				pointOnUnitCube.normalise();
				float noiseFactor = (float) noise.eval(pointOnUnitCube.x * step, pointOnUnitCube.y * step, pointOnUnitCube.z * step);

				pointOnUnitCube.scale((noiseFactor + 1) * 2);
				
				vertices[vertexPointer * 3] = pointOnUnitCube.x;
				vertices[vertexPointer * 3 + 1] = pointOnUnitCube.y;
				vertices[vertexPointer * 3 + 2] = pointOnUnitCube.z;
				normals[vertexPointer * 3] = pointOnUnitCube.x;
				normals[vertexPointer * 3 + 1] = pointOnUnitCube.y;
				normals[vertexPointer * 3 + 2] = pointOnUnitCube.z;
				textureCoords[vertexPointer * 2] = (float) x / ((float) resolution - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) y / ((float) resolution - 1);
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
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
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
