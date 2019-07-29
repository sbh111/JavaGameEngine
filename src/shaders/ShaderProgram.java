package shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {

    //private//////////////////////////////////////////////////////////////////////////////////
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    private static int loadShader(String file, int type){

        StringBuilder shaderSource = new StringBuilder();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null  ) {

                shaderSource.append(line).append("\n");
            }
            reader.close();
        }//end try
        catch (IOException e) {

            System.err.println("Couldn't read file");
            e.printStackTrace();
            System.exit(-1);

        }//end catch

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.out.println("Couldn't compile shader.");
            System.exit(-1);
        }

        return shaderID;

    }//end loadShader


    //protected///////////////////////////////////////////////////////////////////////////////////
    protected abstract void bindAttributes();
    protected void bindAttribute(int attribute, String varName){
        GL20.glBindAttribLocation(programID, attribute, varName);
    }

    protected int getUniformLocation(String uniformName){
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    protected void loadFloat(int location, float val){
        GL20.glUniform1f(location, val);
    }

    protected void loadVector(int location, Vector3f val){
        GL20.glUniform3f(location, val.x, val.y, val.z);
    }

    protected void loadBoolean(int location, boolean bool){
        if(bool){
            loadFloat(location, 1);
        }
        else{
            loadFloat(location, 0);
        }
    }

    protected void loadMatrix(int location, Matrix4f mat){
        mat.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    protected abstract void getAllUniformLocations();

    //public//////////////////////////////////////////////////////////////////////////////////////
    public ShaderProgram(String vertexSourceFile, String fragmentSourceFile){
        vertexShaderID = loadShader(vertexSourceFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentSourceFile, GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    public void start(){
        GL20.glUseProgram(programID);
    }

    public void stop(){
        GL20.glUseProgram(0);
    }

    public void cleanUp(){
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);

    }



}//end shaderProgram class
