package theGameEngine.controls;

import java.util.List;

import theGameEngine.main.Main;
import theGameEngine.math.VectorAndMatrixMath;
import android.opengl.Matrix;
import android.view.MotionEvent;

public class Camera {
	
	// Position represented as {x,y,z}
	private float[] cameraPosition = new float[]{0,0,0};
	
	// Direction represented as {upX, upY, upZ}
	private float[] cameraUpDirection = new float[]{0,0,0};
	
	
	float rotateAngle;
	public float scaleFactor = 1.0f;
	public float totalZoom = 1.0f;
	
	public boolean zooming;
	public boolean unlockZoom;
	
	float[] axisCamera = {0,0,0,0};
	float[] axisObject = {0,0,0,0};
	
	float[] invertMV = new float[16];
	float[] invertV = new float[16];
	float[] objToCamera = new float[3];
	
	TouchEvent event1;
	TouchEvent event2;
	
	public Camera(float cameraPositionX, float cameraPositionY, float cameraPositionZ,
				  float cameraUpX, float cameraUpY, float cameraUpZ)
	{
		cameraPosition[0] = cameraPositionX;
		cameraPosition[1] = cameraPositionY;
		cameraPosition[2] = cameraPositionZ;
		
		cameraUpDirection[0] = cameraUpX;
		cameraUpDirection[1] = cameraUpY;
		cameraUpDirection[2] = cameraUpZ;
	}
	
	public void arcballRotation(List<TouchEvent> touchControlGeneralTouches){
		
		synchronized(touchControlGeneralTouches){
			if (touchControlGeneralTouches.size() < 2)
				return;
	
			for (int i=0; i<touchControlGeneralTouches.size()-1; i++){
				event1 = touchControlGeneralTouches.get(i);
				event2 = touchControlGeneralTouches.get(i+1);
				
				if (event1.getTouchAction() == MotionEvent.ACTION_UP || event2.getTouchAction() == MotionEvent.ACTION_UP){
					touchControlGeneralTouches.clear();
					zooming = false;
					return;
				}
				if (event1.getTouchAction() == MotionEvent.ACTION_POINTER_UP || event2.getTouchAction() == MotionEvent.ACTION_POINTER_UP){
					zooming = false;
				}
				if ((event1.getTouchPixelX() != event2.getTouchPixelX() || event1.getTouchPixelY() != event2.getTouchPixelY()) && !zooming){
					float[] v1 = {((event1.getTouchPixelX()/(Main.getScreenWidth()/2))-1),((event1.getTouchPixelY()/(Main.getScreenHeight()/2))-1),0};
					float[] v2 = {((event2.getTouchPixelX()/(Main.getScreenWidth()/2))-1),((event2.getTouchPixelY()/(Main.getScreenHeight()/2))-1),0};
					v1[1] = -v1[1];
					v2[1] = -v2[1];
					float length1 = v1[0]*v1[0] + v1[1]*v1[1];
					float length2 = v2[0]*v2[0] + v2[1]*v2[1];
					if (length1 <= 1)
						v1[2] = (float) Math.sqrt(1-length1);
					else{
						v1[0] = v1[0]/length1;
						v1[1] = v1[1]/length1;
					}
					if (length2 <= 1)
						v2[2] = (float) Math.sqrt(1-length2);
					else{
						v2[0] = v2[0]/length2;
						v2[1] = v2[1]/length2;
					}
					
			        VectorAndMatrixMath.crossProduct(v1, v2, axisCamera);
	
			        rotateAngle = (float) Math.acos(Math.min(1.0f,VectorAndMatrixMath.dotProduct(v1, v2)));
			        

			        Main.updateMVMatrix();
			        
			        Matrix.invertM(invertMV,0,Main.getMVMatrix(),0);
			        Matrix.multiplyMV(axisObject,0,invertMV,0,axisCamera,0);
			        
			        if (!Float.isNaN(rotateAngle)){
			        	if (rotateAngle*180.0f/3.14f < 20)
			        		Matrix.rotateM(Main.getViewMatrix(),0,(rotateAngle*180.0f/3.14f),axisObject[0],axisObject[1],axisObject[2]);
			        }
			        Matrix.invertM(invertV, 0, Main.getViewMatrix(), 0);
			        cameraPosition[0] = invertV[12];
			        cameraPosition[1] = invertV[13];
			        cameraPosition[2] = invertV[14];
			        
			        Main.updateViewMatrix();
				}
			}
			TouchEvent leftOver = new TouchEvent(touchControlGeneralTouches.get(touchControlGeneralTouches.size()-1));
			touchControlGeneralTouches.clear();
			touchControlGeneralTouches.add(leftOver);
		}
		
	}
	
	public void zoom(){
		synchronized(this){
			if (unlockZoom && zooming){
				cameraPosition[0] = cameraPosition[0] / scaleFactor;
				cameraPosition[1] = cameraPosition[1] / scaleFactor;
				cameraPosition[2] = cameraPosition[2] / scaleFactor;
				Main.updateViewMatrix();
			}	
			scaleFactor = 1.0f;
		}
	}
	
	public float getPositionX()
	{
		return cameraPosition[0];
	}
	public float getPositionY()
	{
		return cameraPosition[1];
	}
	public float getPositionZ()
	{
		return cameraPosition[2];
	}
	public float[] getPosition()
	{
		return cameraPosition;
	}
	
	public float getUpDirectionX()
	{
		return cameraUpDirection[0];
	}
	public float getUpDirectionY()
	{
		return cameraUpDirection[1];
	}
	public float getUpDirectionZ()
	{
		return cameraUpDirection[2];
	}
	public float[] getUpDirection()
	{
		return cameraUpDirection;
	}
}
