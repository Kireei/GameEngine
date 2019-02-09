package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 1, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float speed = 0.2f;
	
	public Camera(){
		
	}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			
			position.z -= Math.cos(Math.toRadians(yaw)) * speed;
			position.x += Math.sin(Math.toRadians(yaw)) * speed;
			
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.z -= Math.cos(Math.toRadians(yaw + 90)) * speed;
			position.x += Math.sin(Math.toRadians(yaw + 90)) * speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.z -= Math.cos(Math.toRadians(yaw - 90)) * speed;
			position.x += Math.sin(Math.toRadians(yaw - 90)) * speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z += Math.cos(Math.toRadians(yaw)) * speed;
			position.x -= Math.sin(Math.toRadians(yaw)) * speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y += speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			position.y -= speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			speed = 0.8f;
		} else {
			speed = 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			System.exit(0);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw -= 0.75;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw += 0.75;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			pitch -= 0.5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			pitch += 0.5;
		}
		
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

}
