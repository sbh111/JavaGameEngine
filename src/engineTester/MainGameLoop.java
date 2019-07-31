package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;;
import rendererEngine.*;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop
{

    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        RawModel model = ObjLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("linux-icon"));
        texture.setReflectivity(.5f);
        texture.setShineDamper(5);
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(
                texturedModel,
                new Vector3f(0, 0, -20),
                -30,
                50,
                30,
                1
        );
        Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("linux-icon")));
        Camera camera = new Camera();
        Light light = new Light(new Vector3f(0, 0, 100), new Vector3f(1, 1, 1));
        MasterRenderer renderer = new MasterRenderer();



        //game loop
        while(!Display.isCloseRequested())
        {
            entity.increaseRotation(0.02f, 1.f, 0.02f);

            camera.move();
            renderer.processTerrain(terrain);
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
