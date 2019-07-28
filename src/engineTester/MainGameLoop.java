package engineTester;

import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
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

        float []vertices =
                {
                   -.5f,   .5f, 0f, //v0
                   -.5f, -.5f, 0f,  //v1
                   .5f, -.5f, 0f,   //v2
                   .5f, .5f, 0f,    //v3
                };
        int []indices =
                {
                        0, 1, 3,
                        3, 1, 2
                };

        RawModel model = loader.loadToVao(vertices, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("linux-icon"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        //game loop
        while(!Display.isCloseRequested())
        {
            renderer.prepare();

            shader.start();
            renderer.render(texturedModel);
            shader.stop();

            DisplayManager.updateDisplay();


        }//end gameLoop


        //cleanUp
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }//end main
}
