package rendererEngine;

import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    //private/////////////////////////////////////////////////////////////
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    private int createVao() {
        int vaoID = GL30.glGenVertexArrays();   //creates an empty VAO
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }


    private void storeDataInAttribList(int attribNumber, int coordSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer
                (
                        attribNumber,
                        coordSize,
                        GL11.GL_FLOAT,
                        false,
                        0,
                        0
                );
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void unbindVao() {
        GL30.glBindVertexArray(0);
    }

    private void bindIndexBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
       IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
       buffer.put(data);
       buffer.flip();
       return  buffer;
    }

    //public//////////////////////////////////////////////////////////////////////////

    public int loadTexture(String filename) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+filename+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int textureID = texture.getTextureID();
        textures.add(textureID);
        return  textureID;
    }

    public RawModel loadToVao(float[] positions, float[] texCoords, int[] indices) {
        int vaoID = createVao();
        bindIndexBuffer(indices);
        storeDataInAttribList(0, 3, positions);
        storeDataInAttribList(1, 2, texCoords);
        unbindVao();
        return new RawModel(vaoID, indices.length);
    }

    public void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }

        for(int texture : textures){
            GL11.glDeleteTextures(texture);
        }
    }


}//end loader class
