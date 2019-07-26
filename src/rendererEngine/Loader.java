package rendererEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    //private/////////////////////////////////////////////////////////////
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();

    private int createVao() {
        int vaoID = GL30.glGenVertexArrays();   //creates an empty VAO
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }


    private void storeDataInAttribList(int attribNumber, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer
                (
                        attribNumber,
                        3,
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
    public RawModel loadToVao(float[] positions, int[] indices) {
        int vaoID = createVao();
        bindIndexBuffer(indices);
        int slot = 0;
        storeDataInAttribList(slot, positions);
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
    }


}//end loader class
