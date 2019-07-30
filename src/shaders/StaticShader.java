package shaders;

import entities.Camera;
import entities.Light;
import org.lwjgl.util.vector.Matrix4f;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {

    //private//////////////////////////////////////////////////////////////
    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;


    //protected//////////////////////////////////////////////////////////////
    @Override
    protected void bindAttributes(){
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");


    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");

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

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPositon());
        super.loadVector(location_lightColor, light.getColor());
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_reflectivity, reflectivity);
        super.loadFloat(location_shineDamper, damper);
    }


}
