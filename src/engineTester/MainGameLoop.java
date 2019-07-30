package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;;
import rendererEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop
{

    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        RawModel model = ObjLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("white-texture"));
        texture.setReflectivity(.5f);
        texture.setShineDamper(5);
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
        Light light = new Light(new Vector3f(-100, -100, 0), new Vector3f(1, 1, 1));

        MasterRenderer renderer = new MasterRenderer();

        entity.increasePosition(0, 0, -10.f);
        //game loop
        while(!Display.isCloseRequested())
        {
            entity.increaseRotation(0.02f, 1.f, 0.02f);

            camera.move();

            renderer.processEntity(entity);

            renderer.render(light, camera);

            DisplayManager.updateDisplay();
        }//end gameLoop


        //cleanUp
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }//end main
}
