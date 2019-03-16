package terrain;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;
import toolbox.OpenSimplexNoise;

public class Sphere {
	

	private int vertex_count;
	private RawModel model;
	private ModelTexture texture;
	private OpenSimplexNoise noise;
	
	
	public Sphere(int vertices, Loader loader, ModelTexture texture) {
		this.texture = texture;
		this.vertex_count = vertices;
		this.noise = new OpenSimplexNoise();
		this.model = generateSphere(loader);
	}

	
	private RawModel generateSphere(Loader loader) {
		int count = vertex_count * vertex_count;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (vertex_count) * (vertex_count)];
		//double alpha = (2 * Math.PI) / vertex_count;
		int vertexPointer = 0;

		for (int i = 0; i < vertex_count; i++) {
			double alpha = (((i / vertex_count) - 0.5) * (Math.PI / 2));
			for(int j = 0; j < vertex_count; j++) {
					
					double theta = (((j / vertex_count) - 0.5 * vertex_count) * Math.PI);
					//Vector3f vec = new Vector3f((float) (Math.cos(alpha) * Math.sin(theta)), (float) Math.cos(theta),(float) (Math.sin(theta) * Math.sin(alpha)) );
					//System.out.println(vec.x + " " + vec.y + " " + vec.z + " " + i + " " + j);

					//vec.scale((float) (noise.eval(i * 0.02, j * 0.02) / 2 + 1) +1);


					vertices[vertexPointer * 3] =(float) (Math.cos(alpha) * Math.sin(theta));// vec.x;
					vertices[vertexPointer * 3 + 1] = (float) Math.cos(theta); //vec.y;
					vertices[vertexPointer * 3 + 2] = (float) (Math.sin(theta) * Math.sin(alpha)); //vec.z;
					
					normals[vertexPointer * 3] = (float) (Math.cos(alpha) * Math.sin(theta));//vec.x; //(float) (Math.cos(alpha * j) * Math.sin(alpha * i));
					normals[vertexPointer * 3 + 1] =(float) Math.cos(theta);// vec.y;//(float) Math.cos(alpha * i);
					normals[vertexPointer * 3 + 2] = (float) (Math.sin(theta) * Math.sin(alpha));//vec.z;//(float) (Math.sin(alpha * j) * Math.sin(alpha * i));
					textureCoords[vertexPointer * 2] = 1;
					textureCoords[vertexPointer * 2 + 1] = 1;
					
					vertexPointer++;

				
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < vertex_count-1; gz++) {
			for (int gx = 0; gx < vertex_count; gx++) {
				if(gz == 0) {
					int center = 0;
					int first = gx;
					int second = first + 1;
					indices[pointer++] = center;
					indices[pointer++] = first;
					indices[pointer++] = second;
					continue;
				}
				if(gz == vertex_count - 1) {
					int center = vertex_count - 1;
					int first = center - gx;
					int second = first + 1;
					indices[pointer++] = center;
					indices[pointer++] = first;
					indices[pointer++] = second;
					continue;
				}
				int first = vertex_count * (gz - 1) + gx - 1;
				int second = first + vertex_count;
				int third = second + 1;
				int fourth = first + 1;
				indices[pointer++] =  first;
				indices[pointer++] =  third;
				indices[pointer++] =  second;
				
				indices[pointer++] =  first;
				indices[pointer++] =  fourth;
				indices[pointer++] =  third;
				
				
				
						
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}


	public RawModel getModel() {
		return model;
	}


	public void setModel(RawModel model) {
		this.model = model;
	}


	public ModelTexture getTexture() {
		return texture;
	}


	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}
}
