package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {

    //private///////////////////////////////////////////////////////////////
    private Vector3f positon;
    private Vector3f color;


    //public///////////////////////////////////////////////////////////////
    public Light(Vector3f positon, Vector3f color) {
        this.positon = positon;
        this.color = color;
    }


    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getPositon() {
        return positon;
    }

    public void setPositon(Vector3f positon) {
        this.positon = positon;
    }
}
