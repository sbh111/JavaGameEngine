package rendererEngine;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager
{
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static  final int FPS_CAP = 120;



    public static void createDisplay()
    {
        ContextAttribs attribs = new ContextAttribs(3,3)
        .withForwardCompatible(true)
        .withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("Test");
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        GL11.glViewport(0, 0, WIDTH, HEIGHT);   //tells opengl to use the entire display to render

    }//end createDisplay

    public static void updateDisplay()
    {
        Display.sync(FPS_CAP);
        Display.update();
    }//end updateDisplay

    public static void closeDisplay()
    {
        Display.destroy();
    }//end closeDisplay
}
