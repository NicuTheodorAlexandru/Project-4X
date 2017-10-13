package graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh
{
	private final int vaoID;
	private final int vertexCount;
	private final List<Integer> vboIDList;
	private Vector3f minPos;
	private Vector3f maxPos;
	private Vector4f color;
	private Texture sprite;
	
	public void setMaxPos(Vector3f maxPos)
	{
		this.maxPos = maxPos;
	}
	
	public void setMinPos(Vector3f minPos)
	{
		this.minPos = minPos;
	}
	
	public Vector3f getMaxPos()
	{
		return maxPos;
	}
	
	public Vector3f getMinPos()
	{
		return minPos;
	}
	
 	public Vector3f getCenter()
	{
		Vector3f center = this.getLength();
		center.x /= 2;
		center.y /= 2;
		center.z /= 2;
		return center;
	}
	
	public Vector3f getLength()
	{
		Vector3f length = new Vector3f();
		length.x = maxPos.x - minPos.x;
		length.y = maxPos.y - minPos.y;
		length.z = maxPos.z - minPos.z;
		return length;
	}
	
	public void cleanup()
	{
		GL20.glDisableVertexAttribArray(0);

        // Delete the VBOs
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIDList) {
            GL15.glDeleteBuffers(vboId);
        }

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoID);
	}
	
	public void setColor(Vector4f color)
	{
		this.color = color;
	}
	
	public Vector4f getColor()
	{
		return color;
	}
	
	public void render()
	{
		if(sprite != null)
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
		}
		
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public int getVaoID()
	{
		return vaoID;
	}
	
	public Texture getTexture()
	{
		return sprite;
	}
	
	public void setTexture(Texture sprite)
	{
		this.sprite = sprite;
	}
	
	public int getVertexCount()
	{
		return vertexCount;
	}
	
	private void size(float[] positions)
	{
		minPos.x = minPos.y = minPos.z = Float.POSITIVE_INFINITY;
		maxPos.x = maxPos.y = maxPos.z = Float.NEGATIVE_INFINITY;
		
		for(int i = 0; i < positions.length; i += 3)
		{
			float x = positions[i];
			float y = positions[i + 1];
			float z = positions[i + 2];
			
			if(x > maxPos.x)
				maxPos.x = x;
			if(x < minPos.x)
				minPos.x = x;
			if(y > maxPos.y)
				maxPos.y = y;
			if(y < minPos.y)
				minPos.y = y;
			if(z > maxPos.z)
				maxPos.z = z;
			if(z < minPos.z)
				minPos.z = z;
		}
	}
	
	public Mesh(float[] positions, float[] texCoords, float[] normals, int[] indices)
	{
		minPos = new Vector3f();
		maxPos = new Vector3f();
		size(positions);
		sprite = null;
		color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		FloatBuffer positionBuffer = null;
		FloatBuffer texCoordsBuffer = null;
		FloatBuffer normalBuffer = null;
		IntBuffer indexBuffer = null;
		
		try
		{
			vertexCount = indices.length;
			vboIDList = new ArrayList<>();
			vaoID = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vaoID);
			
			//position vbo
			int vboID = GL15.glGenBuffers();
			vboIDList.add(vboID);
			positionBuffer = MemoryUtil.memAllocFloat(positions.length);
			positionBuffer.put(positions).flip();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			
			//texture vbo
			vboID = GL15.glGenBuffers();
			vboIDList.add(vboID);
			texCoordsBuffer = MemoryUtil.memAllocFloat(texCoords.length);
			texCoordsBuffer.put(texCoords).flip();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordsBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
			
			//normal vbo
			vboID = GL15.glGenBuffers();
			vboIDList.add(vboID);
			normalBuffer = MemoryUtil.memAllocFloat(normals.length);
			normalBuffer.put(normals).flip();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
			
			//index vbo
			vboID = GL15.glGenBuffers();
			vboIDList.add(vboID);
			indexBuffer = MemoryUtil.memAllocInt(indices.length);
			indexBuffer.put(indices).flip();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			GL30.glBindVertexArray(0);
		}
		finally
		{
			if(positionBuffer != null)
			{
				MemoryUtil.memFree(positionBuffer);
			}
			if(texCoordsBuffer != null)
			{
				MemoryUtil.memFree(texCoordsBuffer);
			}
			if(normalBuffer != null)
			{
				MemoryUtil.memFree(normalBuffer);
			}
			if(indexBuffer != null)
			{
				MemoryUtil.memFree(indexBuffer);
			}
		}
	}
}
