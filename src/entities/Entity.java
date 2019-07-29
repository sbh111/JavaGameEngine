package entities;


import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

//Instance of a textured model
public class Entity {

    //private////////////////////////////////////////////////////////////////////////////////////
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;



    //public/////////////////////////////////////////////////////////////////////////////////////
    public Entity(TexturedModel model, Vector3f pos, float rx, float ry, float rz, float s) {
        this.model = model;
        this.position = pos;
        this.rotX = rx;
        this.rotY = ry;
        this.rotZ = rz;
        this.scale = s;
    }


    public void increasePosition(float dx, float dy, float dz){
        this.position.x+=dx;
        this.position.y+=dy;
        this.position.z+=dz;
    }

    public void increaseRotation(float rx, float ry, float rz){
        this.rotX += rx;
        this.rotY += ry;
        this.rotZ += rz;
    }


    //Accessors and Mutators
    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
