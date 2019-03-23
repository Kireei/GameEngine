package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

public class UIShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/shaders/uiVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/uiFragmentShader.txt";
    
    private int location_translation;
    private int location_tMatrix;
    private int location_pMatrix;
    private int location_color;

	
	public UIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_translation = super.getUniformLocation("translation");
		location_tMatrix = super.getUniformLocation("tMatrix");
		location_pMatrix = super.getUniformLocation("pMatrix");
		location_color = super.getUniformLocation("color");

		
	}

	@Override
	protected void bindAttributes() {
    	super.bindAttribute(0, "position");
    	super.bindAttribute(1, "textureCoords");
    }
	
	public void loadTranslation(Vector2f translation){
		super.loadVector2(location_translation, translation);
	}
	public void loadTMatrix(Matrix4f matrix){
		super.loadMatrix(location_tMatrix, matrix);
	}
	public void loadPMatrix(Matrix4f matrix) {
		super.loadMatrix(location_pMatrix, matrix);
	}
	public void loadColor(Vector4f vector){
		super.loadVector4(location_color, vector);
	}


}
