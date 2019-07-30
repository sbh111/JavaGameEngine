package rendererEngine;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

import java.util.List;
import java.util.Map;

public class Renderer {


    //private////////////////////////////////////////////////////////////////////
    private static final float FOV = 70.f;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    private StaticShader shader;

    private Matrix4f projectionMatrix;


    private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float)Display.getHeight();
        float yScale = (1.f / (float)Math.tan(Math.toRadians(FOV / 2.f)));
        float xScale = yScale / aspectRatio;
        float frustrumLength = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 =  yScale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustrumLength);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustrumLength);
        projectionMatrix.m33 = 0;
    }

    private void prepareTexturedModel(TexturedModel texturedModel){
        RawModel rawModel = texturedModel.getRawModel();

        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        ModelTexture texture = texturedModel.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());


    }

    private void unbindTexturedModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale()
        );
        shader.loadTransformationMatrix(transformationMatrix);

    }

    //public////////////////////////////////////////////////////////////////////
    public Renderer(StaticShader shader){
        this.shader = shader;
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare()
    {
        //called once every frame to prepare opengl
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(.6f, .2f, .1f, 1);
    }


    public void render(Map<TexturedModel, List<Entity>> entities){
            for(TexturedModel model : entities.keySet()){
                prepareTexturedModel(model);
                List<Entity> batch = entities.get(model);
                for(Entity entity : batch){
                    prepareInstance(entity);
                    GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                }
                unbindTexturedModel();
            }
    }
}
