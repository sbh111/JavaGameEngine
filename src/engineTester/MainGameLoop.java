package engineTester;

import org.lwjgl.opengl.Display;
import rendererEngine.DisplayManager;

public class MainGameLoop
{

    public static void main(String[] args)
    {
        DisplayManager.createDisplay();

        //game loop
        while(!Display.isCloseRequested())
        {
            DisplayManager.updateDisplay();
        }//end gameLoop
        DisplayManager.closeDisplay();
    }//end main
}
