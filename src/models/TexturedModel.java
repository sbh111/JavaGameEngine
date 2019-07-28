package models;

import textures.ModelTexture;

public class TexturedModel {

    //private/////////////////////////////////



    private RawModel rawModel;



    private ModelTexture texture;

    //public//////////////////////////////////
    public TexturedModel(RawModel model, ModelTexture texture){
        this.texture = texture;
        this.rawModel = model;
    }

    //accessors
    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }



}
