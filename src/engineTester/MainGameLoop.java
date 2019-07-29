package engineTester;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import rendererEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop
{

    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float []vertices = {
                -.5f,   .5f, 0f, //v0
                -.5f, -.5f, 0f,  //v1
                .5f, -.5f, 0f,   //v2
                .5f, .5f, 0f,    //v3
                };
        int []indices = {
                0, 1, 3,        //triangle: v0 -> v1 -> v3
                3, 1, 2         //triangle: v3 -> v1 -> v2
                };
        float[] texCoords = {
                0, 0,           //v0
                0, 1,           //v1
                1, 1,           //v2
                1, 0            //v3
        };

        RawModel model = loader.loadToVao(vertices, texCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("linux-icon"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(
                texturedModel,
                new Vector3f(-1, 0, 0),
                0,
                0,
                0,
                1
        );

        //game loop
        while(!Display.isCloseRequested())
        {
            renderer.prepare();

            shader.start();
            renderer.render(entity, shader);
            shader.stop();

            DisplayManager.updateDisplay();


        }//end gameLoop


        //cleanUp
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }//end main
}
