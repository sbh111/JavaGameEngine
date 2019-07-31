package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    //private////////////////////////////////////////////////////
    private Vector3f position = new Vector3f(0, 1, 0);
    private static final float SPEED = 0.05f;
    private float pitch;    //rotation about x axis
    private float yaw;      //rotation about y axis
    private float roll;     //rotation about z axis

    //public/////////////////////////////////////////////////////
    public Camera(){

    }

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z -= SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x -= SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z += SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x += SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
            roll -= 1;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            roll += 1;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            yaw += 5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            yaw -= 5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            position.y -= SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
            position.y += SPEED;
        }


    }


    //Accessors
    public float getPitch() {
        return pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRoll() {
        return roll;
    }

    public float getYaw() {
        return yaw;
    }
}
