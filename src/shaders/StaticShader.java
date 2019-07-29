package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {

    //private//////////////////////////////////////////////////////////////
    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
    private int location_transformationMatrix;
    private int location_projectionMatrix;


    //protected//////////////////////////////////////////////////////////////
    @Override
    protected void bindAttributes(){
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");

    }

    //public/////////////////////////////////////////////////////////////////
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformationMatrix(Matrix4f mat){
        super.loadMatrix(location_transformationMatrix, mat);
    }

    public void loadProjectionMatrix(Matrix4f mat){
        super.loadMatrix(location_projectionMatrix, mat);
    }



}
