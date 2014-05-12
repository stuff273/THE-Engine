package theGameEngine.items.items2D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import theGameEngine.actions.GUIItemAction;
import theGameEngine.animations.Animation2D;
import theGameEngine.collisionDetection.CollisionDetect2D;
import theGameEngine.controllers.Controller2D;
import theGameEngine.items.Item;
import theGameEngine.main.Main;
import theGameEngine.models.Model;
import theGameEngine.models.Model2D;
import android.opengl.GLES20;

public abstract class Item2D extends Item{
	
	protected float[] attributes;
	
	protected FloatBuffer data;
	
	protected Animation2D animation = null;
	
	protected float rotationSin = 0;
	protected float rotationCos = 0;
	
	protected Model2D model;
	
	//Pickup differences for dragging
	//*********************************
	protected float pickupDifferenceX = 0;
	protected float pickupDifferenceY = 0;
	//*********************************
	
	//What action the GUIItem will take when successfully released (successfully meaning the release touch is also in the bounds of the GUIItem)
	protected GUIItemAction action;	
	
	//Accompanying Controller
	protected Controller2D controller2D;
	
	public Item2D (Model2D model, Controller2D controller2D)
	{
		this.controller2D = controller2D;
		setModel(model);
	}	
	
	
	public boolean checkTouch(float touchPixelX, float touchPixelY)
	{
		if (!touchable)
			return false;
		
		return CollisionDetect2D.collisionDetectAABBOnScreenPoint(this, Main.getTouchScreenX(), Main.getTouchScreenY());
	}
	
	
	public void calculateFinalPoints()
	{
		if (getRotationValue()!=0){
			rotationSin = (float) Math.sin(Math.toRadians(Math.abs(getRotationValue())));
			rotationCos = (float) Math.cos(Math.toRadians(Math.abs(getRotationValue())));
		}
		
		int incrementor = 0;
		switch(model.getType()){
		case(Model.TYPE_T):
			incrementor = 5;
			break;
		case(Model.TYPE_C):
			incrementor = 7;
			break;
		}
		
		int indexX = 0;
		int indexY = 1;
		int indexZ = 2;
		attributes[indexX] = model.getLowX();
		attributes[indexY] = model.getHighY();
		attributes[indexZ] = getTranslationValueZ();
		performTransformations(indexX, indexY);
		
		indexX += incrementor;
		indexY += incrementor;
		indexZ += incrementor;
		attributes[indexX] = model.getLowX();
		attributes[indexY] = model.getLowY();
		attributes[indexZ] = getTranslationValueZ();
		performTransformations(indexX, indexY);
		
		indexX += incrementor;
		indexY += incrementor;
		indexZ += incrementor;
		attributes[indexX] = model.getHighX();
		attributes[indexY] = model.getLowY();
		attributes[indexZ] = getTranslationValueZ();
		performTransformations(indexX, indexY);
		
		indexX += incrementor;
		indexY += incrementor;
		indexZ += incrementor;
		attributes[indexX] = model.getHighX();
		attributes[indexY] = model.getHighY();
		attributes[indexZ] = getTranslationValueZ();
		performTransformations(indexX, indexY);
		
	}
	
	protected void performTransformations(int indexX, int indexY)
	{
		//Scaling
		attributes[indexX] *= getScaleValueX();
		attributes[indexY] *= getScaleValueY();
		
		//Rotating
		if (getRotationValue()>0){
			float beforeRotationX = attributes[indexX];
			float beforeRotationY = attributes[indexY];
			attributes[indexX] = rotationCos*beforeRotationX - rotationSin*beforeRotationY;
			attributes[indexY] = rotationSin*beforeRotationX + rotationCos*beforeRotationY;
		}
		else if (getRotationValue()<0){
			float beforeRotationX = attributes[indexX];
			float beforeRotationY = attributes[indexY];
			attributes[indexX] = rotationCos*beforeRotationX + rotationSin*beforeRotationY;
			attributes[indexY] = -rotationSin*beforeRotationX + rotationCos*beforeRotationY;
		}
		
		//Translating
		attributes[indexX] += getTranslationValueX();
		attributes[indexY] += getTranslationValueY();
	}
	
	
	public void updateAbstractData()
	{
		calculateFinalPoints();
		
		updateAbstractData = false;
		updateOpenGLData = true;
	}
	
	
	// Must already have buffer bound!
	public void updateOpenGLData()
	{
		data.put(attributes).position(0);
		
		int bufferIndexInBytes = 0;
		switch(model.getType()){
		case(Model.TYPE_T):
			bufferIndexInBytes = modelTypeListIndex*20*4;
			break;
		case(Model.TYPE_C):
			bufferIndexInBytes = modelTypeListIndex*28*4;
			break;
		}
		GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER,bufferIndexInBytes,data.capacity()*4,data);
		
		updateOpenGLData = false;
	}
	
	public boolean removeFromVerticesBuffer()
	{		
		controller2D.removeFromBatchObjects(this);
	
		return true;
	}
	
	public void update(float deltaTime)
	{
		if (animation != null && getAnimating())
			animation.perform(this, deltaTime);
	}
	
	
	//*********************************************************************************************
	// Getters and Setters
	
	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
		switch(model.getType())
		{
		case(Model.TYPE_T):
			controller2D.setCreateNewIndicesBufferTexture(true);
			break;
		case(Model.TYPE_C):
			controller2D.setCreateNewIndicesBufferColor(true);
			break;
		}
	}
	
	public void setModel(Model2D model)
	{
		switch(model.getType())
		{
		case(Model.TYPE_T):
			if (this.model != null)
			{
				if (getModel().getType() == Model.TYPE_T)
				{
					if (attributes == null)
					{
						attributes = new float[20];
						data = ByteBuffer.allocateDirect(attributes.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
					}
					for (int i=0; i<20; i++)
						attributes[i] = model.getAttribute(i);
					break;
				}
			}
			attributes = new float[20];
			for (int i=0; i<20; i++)
				attributes[i] = model.getAttribute(i);
			data = ByteBuffer.allocateDirect(attributes.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			break;
		case(Model.TYPE_C):
			if (this.model != null)
			{
				if (getModel().getType() == Model.TYPE_C)
				{
					if (attributes == null)
					{
						attributes = new float[28];
						data = ByteBuffer.allocateDirect(attributes.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
					}
					for (int i=0; i<28; i++)
						attributes[i] = model.getAttribute(i);
					break;
				}
			}
			attributes = new float[28];
			for (int i=0; i<28; i++)
				attributes[i] = model.getAttribute(i);
			data = ByteBuffer.allocateDirect(attributes.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			break;
		}
		
		this.model = model;
		
		updateAbstractData = true;
	}
	
	public float[] getAttributes()
	{
		return attributes;
	}
	public Model2D getModel()
	{
		return model;
	}
	
	public void setPickupDifferenceX(float value)
	{
		pickupDifferenceX = value;
	}
	public float getPickupDifferenceX()
	{
		return pickupDifferenceX;
	}
	public void setPickupDifferenceY(float value)
	{
		pickupDifferenceY = value;
	}	
	public float getPickupDifferenceY()
	{
		return pickupDifferenceY;
	}
	
	
	public void setAction(GUIItemAction action)
	{
		this.action = action;
	}
	public void setAnimation(Animation2D animation)
	{
		this.animation = animation;
		this.animating = true;
	}
	public void turnOffAnimation()
	{
		animation = null;
		animating = false;
	}
	
	
	public float getTextureValueTopLeftX()
	{
		if (model.getType() != Model.TYPE_T)
			return -1f;

		return attributes[3];
	}
	public float getTextureValueTopLeftY()
	{
		if (model.getType() != Model.TYPE_T)
			return -1f;

		return attributes[4];
	}
	public float getTextureValueBottomLeftX()
	{
		if (model.getType() != Model.TYPE_T)
			return -1f;

		return attributes[8];
	}
	public float getTextureValueBottomLeftY()
	{
		if (model.getType() != Model.TYPE_T)
			return -1f;

		return attributes[9];
	}
	public float getTextureValueBottomRightX()
	{
		if (model.getType() != Model.TYPE_T)
			return -1f;

		return attributes[13];
	}
	public float getTextureValueBottomRightY()
	{
		if (model.getType() != Model.TYPE_T)
			return -1f;

		return attributes[14];
	}
	public float getTextureValueTopRightX()
	{
		if (model.getType() != Model.TYPE_T)
			return -1f;

		return attributes[18];
	}
	public float getTextureValueTopRightY()
	{
		if (model.getType() != Model.TYPE_T)
			return -1f;

		return attributes[19];
	}
	
	public void setTextureValueTopLeftX(float newTopLeftTextureValueX)
	{
		if (model.getType() != Model.TYPE_T)
			return;
		
		attributes[3] = newTopLeftTextureValueX;
		updateAbstractData = true;
	}
	public void setTextureValueTopLeftY(float newTopLeftTextureValueY)
	{
		if (model.getType() != Model.TYPE_T)
			return;
		
		attributes[4] = newTopLeftTextureValueY;
		updateAbstractData = true;
	}
	public void setTextureValueBottomLeftX(float newBottomLeftTextureValueX)
	{
		if (model.getType() != Model.TYPE_T)
			return;
		
		attributes[8] = newBottomLeftTextureValueX;
		updateAbstractData = true;
	}
	public void setTextureValueBottomLeftY(float newBottomLeftTextureValueY)
	{
		if (model.getType() != Model.TYPE_T)
			return;
		
		attributes[9] = newBottomLeftTextureValueY;
		updateAbstractData = true;
	}
	public void setTextureValueBottomRightX(float newBottomRightTextureValueX)
	{
		if (model.getType() != Model.TYPE_T)
			return;
		
		attributes[13] = newBottomRightTextureValueX;
		updateAbstractData = true;
	}
	public void setTextureValueBottomRightY(float newBottomRightTextureValueY)
	{
		if (model.getType() != Model.TYPE_T)
			return;
		
		attributes[14] = newBottomRightTextureValueY;
		updateAbstractData = true;
	}
	public void setTextureValueTopRightX(float newTopRightTextureValueX)
	{
		if (model.getType() != Model.TYPE_T)
			return;
		
		attributes[18] = newTopRightTextureValueX;
		updateAbstractData = true;
	}
	public void setTextureValueTopRightY(float newTopRightTextureValueY)
	{
		if (model.getType() != Model.TYPE_T)
			return;
		
		attributes[19] = newTopRightTextureValueY;
		updateAbstractData = true;
	}
	//*********************************************************************************************
	
	
}
