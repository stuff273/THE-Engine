package theGameEngine.controls;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import theGameEngine.controllers.Controller2D;
import theGameEngine.controllers.Controller3D;
import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.main.Main;
import android.annotation.SuppressLint;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.view.MotionEvent;

public class TouchControl 
{
	private float[] screenToWorldRay = {0,0,0,0};
	private float[] screenToWorldRayNear = new float[]{0,0,0};
	private float[] screenToWorldRayFar = new float[]{0,0,0};
	private float[] screenToWorldRayDirection = new float[3];
	private float[] collisionPoint = new float[3];
	private int[] viewport;
	
	//=========================================================================
	// For creating and render the actual ray created from screenToWorldRay
	int rayProgram,shaderHandleV,shaderHandleF,positionHandle,MVPMatrixHandle;
	int[] verticesBuffer;
	boolean createRenderRay = false;
	boolean drawRay = false;
	//=========================================================================
	
	private List<TouchEvent> generalTouches = Collections.synchronizedList(new ArrayList<TouchEvent>());
	
	TouchEvent touch;
	
	private GUI gui = null;
	private Controller3D scene3D = null;
	private Controller2D scene2D = null;
	
	
	
	public TouchControl()
	{
		touch = new TouchEvent();
		this.viewport = new int[]{0,0, (int)Main.getScreenWidth(), (int)Main.getScreenHeight()};
	}
	
	public void processTouch(float touchPixelX, float touchPixelY, int touchAction){
		touch.update(touchPixelX,Main.getScreenHeight() - touchPixelY,touchAction);
		
		//What happens when a user touches (ACTiON_DOWN)?
		if (touch.getTouchAction() == MotionEvent.ACTION_DOWN){
			//Collision checks time!
			if (gui != null)
				gui.setCheckTouch(true);
			
			//A screen-to-world ray must be used to check for collisions of 3D models
			//A boolean must be set to tell the Scene to construct a screen-to-world ray INSIDE AN OPENGL THREAD (P.S. This is not an OpenGL thread)
				//Fun Fact: Did you know this is not an OpenGL thread? 
			if (scene3D != null)
				scene3D.setCheckTouch(true);
			
			if (scene2D != null)
				scene2D.setCheckTouch(true);
			
			generalTouches.add(touch);
					
		}
		
		
		//What happens when a user releases (ACTION_UP)?
		else if (touch.getTouchAction() == MotionEvent.ACTION_UP){
			//Is an Item in the GUI currently touched?
			if (gui != null)
				gui.setCheckRelease(true);
			
			if (scene3D != null)
				scene3D.setCheckRelease(true);
			
			if (scene2D != null)
				scene2D.setCheckRelease(true);
		}
		
		//If none of the above, add the touch to general touches
		
		/*else{ //if (!gui.getOnTouch() && !gui.getWhileTouched() && !scene.getOnTouch() && !scene.getWhileTouched()){
			synchronized(this){
				generalTouches.add(new TouchEvent(touchPixelX, screenHeight - touchPixelY, touchAction));
			}
		}*/
	}
	public boolean processZoom(float scaleFactor){
		if (scene3D != null)
		{
			if (scene3D.getOnTouch() || scene3D.getWhileTouched())
			{
				if (gui.getOnTouch() || gui.getWhileTouched())
					return false;
			}
		}
		if (scene2D != null)
		{
			if (scene2D.getOnTouch() || scene2D.getWhileTouched())
			{
				if (gui.getOnTouch() || gui.getWhileTouched())
					return false;
			}
		}
		
		/*camera.zooming = true;
		camera.scaleFactor *= scaleFactor;
		if (camera.totalZoom * scaleFactor > 0.075f && camera.totalZoom * scaleFactor < 1.2f){
			camera.totalZoom *= scaleFactor;
			camera.unlockZoom= true;
		}
		else camera.unlockZoom= false;*/
		return true;
	}
	
	public void createScreenToWorldRay(float touchPixelX, float touchPixelY, float[] cameraPosition){
        Main.updateMVMatrix();
		GLU.gluUnProject(touchPixelX,touchPixelY,1.0f,Main.getMVMatrix(),0,Main.getProjectionMatrix(),0,viewport,0,screenToWorldRay,0);
		
		screenToWorldRay[0] /= screenToWorldRay[3];
		screenToWorldRay[1] /= screenToWorldRay[3];
		screenToWorldRay[2] /= screenToWorldRay[3];
		screenToWorldRay[3] = 1;
		screenToWorldRayNear[0] = cameraPosition[0];
		screenToWorldRayNear[1] = cameraPosition[1];
		screenToWorldRayNear[2] = cameraPosition[2];
		
		screenToWorldRayFar[0] = screenToWorldRay[0];
		screenToWorldRayFar[1] = screenToWorldRay[1];
		screenToWorldRayFar[2] = screenToWorldRay[2];
		
		screenToWorldRayDirection[0] = screenToWorldRayFar[0]-screenToWorldRayNear[0];
		screenToWorldRayDirection[1] = screenToWorldRayFar[1]-screenToWorldRayNear[1];
		screenToWorldRayDirection[2] = screenToWorldRayFar[2]-screenToWorldRayNear[2];
	}
	
	public void createRenderRay(){
		rayProgram = GLES20.glCreateProgram();
		int[] compileStatus = new int[1];
		int[] linkStatus = new int[1];
		
		shaderHandleV = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		//GLES20.glShaderSource(shaderHandleV,Shaders.rayShaderV);
		GLES20.glCompileShader(shaderHandleV);
		GLES20.glGetShaderiv(shaderHandleV, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		GLES20.glAttachShader(rayProgram, shaderHandleV);
		
		shaderHandleF = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		//GLES20.glShaderSource(shaderHandleF,Shaders.rayShaderF);
		GLES20.glCompileShader(shaderHandleF);
		GLES20.glGetShaderiv(shaderHandleF, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		GLES20.glAttachShader(rayProgram, shaderHandleF);
		
		GLES20.glBindAttribLocation(rayProgram,0,"a_position");
		
		GLES20.glLinkProgram(rayProgram);
		GLES20.glGetProgramiv(rayProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
		GLES20.glDetachShader(rayProgram, shaderHandleV);
		GLES20.glDetachShader(rayProgram, shaderHandleF);
		GLES20.glDeleteShader(shaderHandleV);
		GLES20.glDeleteShader(shaderHandleF);
		
		positionHandle = GLES20.glGetAttribLocation(rayProgram, "a_position");
		MVPMatrixHandle = GLES20.glGetUniformLocation(rayProgram, "u_MVPMatrix");	
		
		float[] twoPoints = new float[]{screenToWorldRayNear[0],screenToWorldRayNear[1],screenToWorldRayNear[2],
										screenToWorldRayFar[0],screenToWorldRayFar[1],screenToWorldRayFar[2]};
		FloatBuffer attributes = ByteBuffer.allocateDirect(twoPoints.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		attributes.put(twoPoints).position(0);
		GLES20.glGenBuffers(1,verticesBuffer,0);	
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,verticesBuffer[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,attributes.capacity()*4,attributes,GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
		
		createRenderRay = false;
		drawRay = true;
	}
	
	@SuppressLint("NewApi")
	void renderRay(float[] MVPMatrix){
		GLES20.glUseProgram(rayProgram);
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, verticesBuffer[0]);	
		
		Main.updateMVP3DMatrix();
	    
		GLES20.glVertexAttribPointer(positionHandle,3,GLES20.GL_FLOAT,false,3*4,0);
		GLES20.glEnableVertexAttribArray(positionHandle);
		
		GLES20.glLineWidth(5.0f);
	    GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, 2);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
	}
	
	public boolean rayPlaneCollisionDetect(){
		float dot1 = 0;
		float dot2 = 0;
		float t = 0;
		int[] centerPoint = {0,3,0};
		int[] normal = {0,1,0};
		for (int i=0; i<screenToWorldRayDirection.length; i++)
			dot1 += screenToWorldRayDirection[i]*normal[i];
		for (int i=0; i<normal.length; i++)
			dot2 += normal[i]*(centerPoint[i]-screenToWorldRayNear[i]);
		t = dot2/dot1;
		collisionPoint[0] = screenToWorldRayNear[0]+(t*screenToWorldRayDirection[0]);
		collisionPoint[1] = screenToWorldRayNear[1]+(t*screenToWorldRayDirection[1]);
		collisionPoint[2] = screenToWorldRayNear[2]+(t*screenToWorldRayDirection[2]);
		return true;
	}
	
	
	public void setScene3D(Controller3D scene3D)
	{
		this.scene3D = scene3D;
	}
	public void setScene2D(Controller2D scene2D)
	{
		this.scene2D = scene2D;
	}
	public void setGUI(GUI gui)
	{
		this.gui = gui;
	}
	
	public float getTouchPixelX()
	{
		return touch.getTouchPixelX();
	}
	public float getTouchPixelY()
	{
		return touch.getTouchPixelY();
	}
	
	public List<TouchEvent> getGeneralTouches()
	{
		return generalTouches;
	}
	
	public float[] getScreenToWorldRayNear()
	{
		return screenToWorldRayNear;
	}
	public float[] getScreenToWorldRayFar()
	{
		return screenToWorldRayFar;
	}
}
