package graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation
{
	private final Matrix4f projectionMatrix;
	private final Matrix4f modelViewMatrix;
	private final Matrix4f orthoMatrix;
	private final Matrix4f orthoModelMatrix;
	private final Matrix4f modelMatrix;
	
	public Matrix4f buildOrtoProjModelMatrix(Model model, Matrix4f orthoMatrix) 
	{
		Vector3f rotation = model.getRotation();
		modelMatrix.identity().translate(model.getPosition()).
                rotateX((float) Math.toRadians(-rotation.x)).
                rotateY((float) Math.toRadians(-rotation.y)).
                rotateZ((float) Math.toRadians(-rotation.z)).
                scale(model.getScale());
        orthoModelMatrix.set(orthoMatrix);
        orthoModelMatrix.mul(modelMatrix);
        return orthoModelMatrix;
    }
	
	public final Matrix4f getOrthoProjectionMatrix(float left, float right, float bottom, float top)
	{
		orthoMatrix.identity();
		orthoMatrix.setOrtho2D(left, right, bottom, top);
		
		return orthoMatrix;
	}
	
 	public Matrix4f getModelViewMatrix(Model gameItem, Matrix4f viewMatrix) 
 	{
        Vector3f rotation = gameItem.getRotation();
        modelViewMatrix.identity().translate(gameItem.getPosition()).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameItem.getScale());
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(modelViewMatrix);
    }
	
	public Matrix4f getViewMatrix(Camera camera, Matrix4f viewMatrix)
	{
		Vector3f position = camera.getPosition();
		Vector3f rotation = camera.getRotation();
		
		viewMatrix.rotationX((float)Math.toRadians(rotation.x))
        		.rotateY((float)Math.toRadians(rotation.y))
        		.translate(-position.x, -position.y, -position.z);
		
		return viewMatrix;
	}
	
	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, 
			float zFar)
	{
		float aspectRatio = (float)width / (float)height;
		projectionMatrix.setPerspective(fov, aspectRatio, zNear, zFar);
		
		return projectionMatrix;
	}
	
	public Transformation()
	{
		orthoModelMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
		modelViewMatrix = new Matrix4f();
		orthoMatrix = new Matrix4f();
		modelMatrix = new Matrix4f();
	}
}
