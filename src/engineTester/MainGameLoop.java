package engineTester;

import org.lwjgl.opengl.Display;
import rendererEngine.*;

public class MainGameLoop
{

    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float []vertices =
                {
                   -.5f,   .5f, 0f,
                   -.5f, -.5f, 0f,
                   .5f, -.5f, 0f,

                   .5f, -.5f, 0f,
                   .5f, .5f, 0f,
                   -.5f, .5f, 0f
                };

        RawModel model = loader.loadToVao(vertices);

        //game loop
        while(!Display.isCloseRequested())
        {
            renderer.prepare();

            renderer.render(model);

            DisplayManager.updateDisplay();


        }//end gameLoop

        loader.cleanUp();
        DisplayManager.closeDisplay();
    }//end main
}
