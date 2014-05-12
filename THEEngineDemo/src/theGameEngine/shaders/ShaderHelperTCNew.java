package theGameEngine.shaders;

import theGameEngine.items.Item;
import android.annotation.SuppressLint;
import android.opengl.GLES20;

public class ShaderHelperTCNew extends ShaderHelper{

	public int program = -1;
	private int texDataHandle;
	
	public ShaderHelperTCNew(){
		int shaderHandleV;
		int shaderHandleF;
		
		int[] compileStatus = new int[1];
		int[] linkStatus = new int[1];
		
		program = GLES20.glCreateProgram();
		
		shaderHandleV = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		GLES20.glShaderSource(shaderHandleV,ShadersTCNew.lightShaderV);
		GLES20.glCompileShader(shaderHandleV);
		GLES20.glGetShaderiv(shaderHandleV, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		GLES20.glAttachShader(program, shaderHandleV);
		
		shaderHandleF = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		GLES20.glShaderSource(shaderHandleF,ShadersTCNew.lightShaderF);
		GLES20.glCompileShader(shaderHandleF);
		GLES20.glGetShaderiv(shaderHandleF, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		GLES20.glAttachShader(program, shaderHandleF);
		
		GLES20.glBindAttribLocation(program,0,"a_position");
		GLES20.glBindAttribLocation(program,1,"a_texture");		
		GLES20.glBindAttribLocation(program,2,"a_color");
		
		GLES20.glLinkProgram(program);
		GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
		GLES20.glDetachShader(program, shaderHandleV);
		GLES20.glDetachShader(program, shaderHandleF);
		GLES20.glDeleteShader(shaderHandleV);
		GLES20.glDeleteShader(shaderHandleF);
		
		assignHandles();
	}
	
	@Override
	public void assignHandles() {
		handlePositionAttribute = GLES20.glGetAttribLocation(program, "a_position");
		handleTextureAttribute = GLES20.glGetAttribLocation(program, "a_tex");
		handleColorAttribute = GLES20.glGetAttribLocation(program, "a_color");
		
		MVPMatrixHandle = GLES20.glGetUniformLocation(program, "u_MVPMatrix");
		texDataHandle = GLES20.glGetUniformLocation(program, "u_tex");
		//colorModifierHandle = GLES20.glGetUniformLocation(program, "u_colorModifier");
	}
	

	public void assignUniforms(Item item, float[] MVPMatrix) {
	    GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
	   
	    //if (item.getModel().hasTexture())
	    	//GLES20.glUniform1i(texDataHandle,item.getTextureValue());
	    //if (item.hasColorModifier())
	    	//GLES20.glUniform4fv(colorModifierHandle,1,item.getColorModifier(),0);
	}
	
	public void setTexture(int value){
		GLES20.glUniform1i(texDataHandle,value);
	}
	public void setMVPMatrix(float[] MVPMatrix){
		GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void bindAttributeHandles() {	
/*		GLES20.glVertexAttribPointer(handlePositionAttribute,3,GLES20.GL_FLOAT,false,model.getAttributeCount()*4,0);
		GLES20.glEnableVertexAttribArray(handlePositionAttribute);
		
		if (model.hasTexture()){
			GLES20.glVertexAttribPointer(handleTextureAttribute,2,GLES20.GL_FLOAT,false,model.getAttributeCount()*4,model.getIndexTexture());
			GLES20.glEnableVertexAttribArray(handleTextureAttribute);
		}
		else GLES20.glDisableVertexAttribArray(handleTextureAttribute);
		
		if (model.hasColor()){
			GLES20.glVertexAttribPointer(handleColorAttribute,4,GLES20.GL_FLOAT,false,model.getAttributeCount()*4,model.getIndexColor());
			GLES20.glEnableVertexAttribArray(handleColorAttribute);
		}
		else GLES20.glDisableVertexAttribArray(handleColorAttribute);*/
	}
	
	@Override
	public void useProgram() {
		if (program == -1)
			return;
		GLES20.glUseProgram(program);
	}

}
