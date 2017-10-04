package misc;

import java.util.List;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3f;
import org.joml.Vector4f;

import graphics.Camera;
import graphics.Model;
import graphics.Renderer;
import input.Mouse;
import main.Main;
import main.Window;

public class MouseBoxSelection extends CameraBoxSelection
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6068318501333708770L;
	private final Matrix4f invProjectionMatrix;
	private final Matrix4f invViewMatrix;
	private final Vector3f mouseDir;
	private final Vector4f tmpVec;
	
	public static Vector3f getPos()
	{
		Vector4f vec1 = new Vector4f();
		Matrix4f mat1 = new Matrix4f();
		
		float x = 1.0f - (2.0f * (float)Mouse.getMousePosition().x) / Main.window.getWindowWidth();
		float y = 1.0f - (2.0f * (float)Mouse.getMousePosition().y ) / Main.window.getWindowHeight();
		float z = 1.0f;
		Vector4f clipCoords = new Vector4f(x, y, z, 1.0f);
		mat1.set(Renderer.projectionMatrix);
		
		mat1.set(mat1.invert());
		vec1.set(mat1.transform(clipCoords));
		Vector4f eyeCoords = new Vector4f(vec1.x, vec1.y, -1.0f, 0.0f);
		mat1.set(Renderer.viewMatrix);
		mat1.invert();
		vec1.set(mat1.transform(eyeCoords));
		Vector3f worldRay = new Vector3f(vec1.x, vec1.y, vec1.z);
		worldRay.normalize();
		Vector3f pos = new Vector3f();
		float distance = -Main.camera.getPosition().z;
		pos.set(Main.camera.getPosition());
		pos.x += worldRay.x * distance;
		pos.y += worldRay.y * distance;
		pos.z += worldRay.z * distance;
		
		return pos;
	}
	
	public void selectModel(List<Model> models, Window window, Vector2d mousePos, Camera camera)
	{
		int wdwWidth = window.getWindowWidth();
		int wdwHeight = window.getWindowHeight();
		
		float x = (float)(2 * mousePos.x) / (float)wdwWidth - 1.0f;
        float y = 1.0f - (float)(2 * mousePos.y) / (float)wdwHeight;
        float z = -1.0f;
        
        //
        invProjectionMatrix.set(Renderer.projectionMatrix);
        invProjectionMatrix.invert();
        
        tmpVec.set(x, y, z, 1.0f);
        tmpVec.mul(invProjectionMatrix);
        tmpVec.z = -1.0f;
        tmpVec.w = 0.0f;
        
        Matrix4f viewMatrix = Renderer.viewMatrix;
        invViewMatrix.set(viewMatrix);
        invViewMatrix.invert();
        tmpVec.mul(invViewMatrix);
        
        mouseDir.set(tmpVec.x, tmpVec.y, tmpVec.z);
        
        selectModel(models, camera.getPosition(), mouseDir);
	}
	
	public MouseBoxSelection()
	{
		super();
		invProjectionMatrix = new Matrix4f();
		invViewMatrix = new Matrix4f();
		mouseDir = new Vector3f();
		tmpVec = new Vector4f();
	}
}
