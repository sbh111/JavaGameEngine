package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;;
import rendererEngine.DisplayManager;
import rendererEngine.Loader;
import rendererEngine.ObjLoader;
import rendererEngine.Renderer;
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





        RawModel model = ObjLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
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
        Light light = new Light(new Vector3f(0, 0, 100), new Vector3f(1, 1, 1));


        entity.increasePosition(0, 0, -10.f);
        //game loop
        while(!Display.isCloseRequested())
        {
            entity.increaseRotation(0.02f, 1.f, 0.02f);

            camera.move();
            renderer.prepare();

            shader.start();
            shader.loadLight(light);
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
