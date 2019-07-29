package engineTester;

import entities.Camera;
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
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        /*
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
        */

        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] textureCoords = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0


        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };

        RawModel model = loader.loadToVao(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("linux-icon"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(
                texturedModel,
                new Vector3f(0, 0, -1),
                0,
                0,
                0,
                1
        );
        Camera camera = new Camera();

        //game loop
        while(!Display.isCloseRequested())
        {
            entity.increaseRotation(0.02f, 1.f, 0.02f);

            camera.move();
            renderer.prepare();

            shader.start();
            shader.loadViewMatrix(camera);
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
