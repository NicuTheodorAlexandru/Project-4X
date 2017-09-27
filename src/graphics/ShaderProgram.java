package graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram
{
	private final Map<String, Integer> uniforms;
	private final int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public void setUniform(String uniformName, Vector4f value)
	{
		GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String uniformName, Vector3f value) 
	{
        GL20.glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }
	
	public void setUniform(String uniformName, int value)
	{
		GL20.glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Matrix4f value)
	{
		try (MemoryStack stack = MemoryStack.stackPush()) 
		{
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
	}
	
	public void createUniform(String uniformName)
	{
		int uniformLocation = GL20.glGetUniformLocation(programID, uniformName);
		
		if(uniformLocation < 0)
		{
			System.err.println("Could not find uniform " + uniformName);
		}
		
		uniforms.put(uniformName, uniformLocation);
	}
	
	public void cleanup()
	{
		unbind();
		if(programID != 0)
		{
			GL20.glDeleteProgram(programID);
		}
	}
	
	public void unbind()
	{
		GL20.glUseProgram(0);
	}
	
	public void bind()
	{
		GL20.glUseProgram(programID);
	}
	
	public void link()
	{
		GL20.glLinkProgram(programID);
		
		if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0)
		{
			System.err.println("Error while linking ShaderProgram " 
					+ GL20.glGetProgramInfoLog(programID, 1024));
		}
		
		if(vertexShaderID != 0)
		{
			GL20.glDetachShader(programID, vertexShaderID);
		}
		
		if(fragmentShaderID != 0)
		{
			GL20.glDetachShader(programID, fragmentShaderID);
		}
		
		GL20.glValidateProgram(programID);
		
		if(GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == 0)
		{
			System.err.println("Error while validating ShaderProgram " 
					+ GL20.glGetProgramInfoLog(programID, 1024));
		}
	}
	
	public void createFragmentShader(String code)
	{
		fragmentShaderID = createShader(code, GL20.GL_FRAGMENT_SHADER);
	}
	
	public void createVertexShader(String code)
	{
		vertexShaderID = createShader(code, GL20.GL_VERTEX_SHADER);
	}
	
	private int createShader(String code, int type)
	{
		int shaderID = GL20.glCreateShader(type);
		
		if(shaderID == 0)
		{
			System.err.println("Could not create shader " + type + "!");
		}
		
		GL20.glShaderSource(shaderID, code);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0)
		{
			System.err.println("Error while compiling shader code: " 
					+ GL20.glGetShaderInfoLog(shaderID, 1024));
		}
		
		GL20.glAttachShader(programID, shaderID);
		
		return shaderID;
	}
	
	public ShaderProgram()
	{
		programID = GL20.glCreateProgram();
		uniforms = new HashMap<>();
		
		if(programID == 0)
		{
			System.err.println("Could not create ShaderProgram!");
		}
	}
}
