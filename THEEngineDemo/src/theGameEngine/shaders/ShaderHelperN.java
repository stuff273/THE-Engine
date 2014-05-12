package theGameEngine.shaders;

import theGameEngine.items.Item;
import android.annotation.SuppressLint;
import android.opengl.GLES20;

public class ShaderHelperN extends ShaderHelper{

	public int[] program = new int[]{-1,-1};
	
	public ShaderHelperN(){
		String[] lightShaders = {ShadersN.lightShader0V,ShadersN.lightShader0F,ShadersN.lightShader1V,ShadersN.lightShader1F};
		int shaderHandleV;
		int shaderHandleF;
		
		program = new int[lightShaders.length/2];
		int[] compileStatus = new int[1];
		int[] linkStatus = new int[1];
		int counter = 0;
		for (int i=0; i<lightShaders.length/2; i++){
			program[i] = GLES20.glCreateProgram();
			
			shaderHandleV = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
			GLES20.glShaderSource(shaderHandleV,lightShaders[i*2]);
			GLES20.glCompileShader(shaderHandleV);
			GLES20.glGetShaderiv(shaderHandleV, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
			GLES20.glAttachShader(program[i], shaderHandleV);
			
			shaderHandleF = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
			GLES20.glShaderSource(shaderHandleF,lightShaders[i*2+1]);
			GLES20.glCompileShader(shaderHandleF);
			GLES20.glGetShaderiv(shaderHandleF, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
			GLES20.glAttachShader(program[i], shaderHandleF);
			
			GLES20.glBindAttribLocation(program[i],counter++,"a_position");
			if (i>0)
				GLES20.glBindAttribLocation(program[i],counter++,"a_normal");
			
			GLES20.glLinkProgram(program[i]);
			GLES20.glGetProgramiv(program[i], GLES20.GL_LINK_STATUS, linkStatus, 0);
			GLES20.glDetachShader(program[i], shaderHandleV);
			GLES20.glDetachShader(program[i], shaderHandleF);
			GLES20.glDeleteShader(shaderHandleV);
			GLES20.glDeleteShader(shaderHandleF);
			
			assignHandles();
		}
	}
	

	public void assignUniforms(Item item, float[] MVPMatrix) {
	    GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
/*		if (drawNumLights>0){
	    	GLES20.glUniformMatrix4fv(MVMatrixHandle, 1, false, controller.matrixHolder.MVMatrix, 0);
	        GLES20.glUniformMatrix4fv(MMatrixHandle, 1, false, controller.matrixHolder.modelMatrix, 0);
	        GLES20.glUniformMatrix3fv(NMatrixHandle, 1, false, controller.matrixHolder.normalMatrix, 0);
	        GLES20.glUniform3fv(lightHandle,controller.numLights,controller.lightPosition,0);
	        GLES20.glUniform3fv(cameraHandle,1,controller.getCamera().cameraPosition,0);
		}*/
	}

	public void useProgram() {
		GLES20.glUseProgram(program[0]);		
	}
	
	public void assignHandles(){
		handlePositionAttribute = GLES20.glGetAttribLocation(program[0], "a_position");
		
		MVPMatrixHandle = GLES20.glGetUniformLocation(program[0], "u_MVPMatrix");
		
/*		if (numLights>0){
			normalHandle = GLES20.glGetAttribLocation(program[numLights], "a_normal");
			MVMatrixHandle = GLES20.glGetUniformLocation(program[numLights], "u_MVMatrix");
			NMatrixHandle = GLES20.glGetUniformLocation(program[numLights], "u_NMatrix");
			MMatrixHandle = GLES20.glGetUniformLocation(program[numLights], "u_MMatrix");
			lightHandle = GLES20.glGetUniformLocation(program[numLights], "u_lightPos");
			cameraHandle = GLES20.glGetUniformLocation(program[numLights], "u_cameraPos");
		}*/
	}
	
	@SuppressLint("NewApi")
	@Override
	public void bindAttributeHandles() {		
	/*	if (drawNumLights>0){
			GLES20.glVertexAttribPointer(positionHandle,3,GLES20.GL_FLOAT,false,model.attributeCount*4,0);
			GLES20.glEnableVertexAttribArray(positionHandle);
			GLES20.glVertexAttribPointer(normalHandle,3,GLES20.GL_FLOAT,false,model.attributeCount*4,model.indexNormal);
			GLES20.glEnableVertexAttribArray(normalHandle);
		}
		else{
			GLES20.glVertexAttribPointer(positionHandle,3,GLES20.GL_FLOAT,false,model.attributeCount*4,0);
			GLES20.glEnableVertexAttribArray(positionHandle);
		}*/
	}

}
