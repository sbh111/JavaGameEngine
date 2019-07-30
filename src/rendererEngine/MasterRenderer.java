package rendererEngine;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import shaders.StaticShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    //private///////////////////////////////////////////////////////
    private StaticShader shader = new StaticShader();
    private Renderer renderer = new Renderer(shader);

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();



    //public///////////////////////////////////////////////////////
    public void render(Light light, Camera camera){
        renderer.prepare();
        shader.start();
        shader.loadLight(light);
        shader.loadViewMatrix(camera);

        renderer.render(entities);

        shader.stop();
        entities.clear();

    }

    public void cleanUp(){
        shader.cleanUp();
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null){
            batch.add(entity);
        }
        else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

}


