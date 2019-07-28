package shaders;

public class StaticShader extends ShaderProgram {

    //private//////////////////////////////////////////////////////////////
    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";


    //protected//////////////////////////////////////////////////////////////
    @Override
    protected void bindAttributes(){
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    //public/////////////////////////////////////////////////////////////////
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }



}
