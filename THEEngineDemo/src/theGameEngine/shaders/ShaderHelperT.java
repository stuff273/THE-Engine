package theGameEngine.shaders;

import android.annotation.SuppressLint;
import android.opengl.GLES20;

public class ShaderHelperT extends ShaderHelper{

	public int program = -1;
	private int handleTextureData;
	
	public ShaderHelperT(){
		int shaderHandleV;
		int shaderHandleF;
		
		int[] compileStatus = new int[1];
		int[] linkStatus = new int[1];
		
		program = GLES20.glCreateProgram();
		
		shaderHandleV = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		GLES20.glShaderSource(shaderHandleV,ShadersT.lightShaderV);
		GLES20.glCompileShader(shaderHandleV);
		GLES20.glGetShaderiv(shaderHandleV, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		GLES20.glAttachShader(program, shaderHandleV);
		
		shaderHandleF = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		GLES20.glShaderSource(shaderHandleF,ShadersT.lightShaderF);
		GLES20.glCompileShader(shaderHandleF);
		GLES20.glGetShaderiv(shaderHandleF, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		GLES20.glAttachShader(program, shaderHandleF);
		
		GLES20.glBindAttribLocation(program,0,"a_position");
		GLES20.glBindAttribLocation(program,1,"a_texture");
		
		GLES20.glLinkProgram(program);
		GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
		GLES20.glDetachShader(program, shaderHandleV);
		GLES20.glDetachShader(program, shaderHandleF);
		GLES20.glDeleteShader(shaderHandleV);
		GLES20.glDeleteShader(shaderHandleF);
		
		assignHandles();
	}
	
	public void assignHandles() {;
		handlePositionAttribute = GLES20.glGetAttribLocation(program, "a_position");
		handleTextureAttribute = GLES20.glGetAttribLocation(program, "a_tex");
		
		MVPMatrixHandle = GLES20.glGetUniformLocation(program, "u_MVPMatrix");
		handleTextureData = GLES20.glGetUniformLocation(program, "u_tex");
	}
	
	@SuppressLint("NewApi")
	@Override
	public void bindAttributeHandles() {
		GLES20.glVertexAttribPointer(handlePositionAttribute,3,GLES20.GL_FLOAT,false,5*4,0);
		GLES20.glEnableVertexAttribArray(handlePositionAttribute);
		GLES20.glVertexAttribPointer(handleTextureAttribute,2,GLES20.GL_FLOAT,false,5*4,12);
		GLES20.glEnableVertexAttribArray(handleTextureAttribute);
	}

	public void bindTextureDataHandle(int textureValue){
		GLES20.glUniform1i(handleTextureData,textureValue);
	}
	
	public void bindMVPMatrixHandle(float[] MVPMatrix)
	{
	    GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
	}

	public void useProgram() {
		GLES20.glUseProgram(program);
	}
}
