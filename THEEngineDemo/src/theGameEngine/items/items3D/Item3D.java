package theGameEngine.items.items3D;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.graphics.Bitmap;
import theGameEngine.controllers.Controller3D;
import theGameEngine.hitboxes.hitboxes3D.Hitbox3D;
import theGameEngine.items.Item;
import theGameEngine.models.Model3D;

public abstract class Item3D extends Item{
	
	protected Model3D model;
	protected Controller3D controller3D;
	
	//Hitbox information
	public Hitbox3D hitbox = null;
	
	public Item3D(float[] center, Model3D model, Controller3D controller3D)
	{
		this.model = model;
		this.controller3D = controller3D;
		
		setTranslationValueXStart(center[0]);
		setTranslationValueYStart(center[1]);
		setTranslationValueZStart(center[2]);	
	}
	
/*	public void assignHitbox(int type){
		
		float mostDistance;
		float length; 
		
		float[] highPoint = model.getHighXPoint();
		float[] lowPoint = model.getLowXPoint();
		float highX = model.getHighX();
		float lowX = model.getLowX();
		float highY = model.getHighY();
		float lowY = model.getLowY();
		float highZ = model.getHighZ();
		float lowZ = model.getLowZ();
		
		switch(type){
		case(HITBOX_SPHERE):
			//To encase the entire model, the highest distance between two points on the same plane is found
			mostDistance = highX-lowX;
			if (highY-lowY > mostDistance){
				mostDistance = highY-lowY;
				highPoint = model.getHighYPoint();
				lowPoint = model.getLowYPoint();
			}
			if (highZ-lowZ > mostDistance){
				mostDistance = highZ-lowZ;
				highPoint = model.getHighZPoint();
				lowPoint = model.getLowZPoint();
			}
			
			
			//The radius is half the distance between the two furthest points
			length = (float) Math.sqrt((highPoint[0]-lowPoint[0])*(highPoint[0]-lowPoint[0])+(highPoint[1]-lowPoint[1])*(highPoint[1]-lowPoint[1])+(highPoint[2]-lowPoint[2])*(highPoint[2]-lowPoint[2]));
			hitbox = new Hitbox3DSphere(new float[]{0,0,0},length/2, this);
			break;
			
		case(HITBOX_CYLINDER):
			//Finding the two points furthest away on the same plane to encase the entire model
			//NOTE: This includes finding the next furthest points for the radius of the cylinder
			mostDistance = highX-lowX;
		
			float cylinderRadius = (highY-lowY>=highZ-lowZ ? (highY-lowY)/2 : (highZ-lowZ)/2);
			
			if (highY-lowY > mostDistance){
				mostDistance = highY-lowY;
				highPoint = model.getHighYPoint();
				lowPoint = model.getLowYPoint();
				cylinderRadius = (highX-lowX>=highZ-lowZ ? (highX-lowX)/2 : (highZ-lowZ)/2);
			}
			if (highZ-lowZ > mostDistance){
				mostDistance = highZ-lowZ;
				highPoint = model.getHighZPoint();
				lowPoint = model.getLowZPoint();
				cylinderRadius = (highX-lowX>=highY-lowY ? (highX-lowX)/2 : (highY-lowY)/2);
			}
			hitbox = new Hitbox3DCylinder(highPoint,lowPoint,cylinderRadius,this);
			break;
			
		case(HITBOX_CAPSULE):
			mostDistance = highX-lowX;
			cylinderRadius = (highY-lowY>=highZ-lowZ ? (highY-lowY)/2 : (highZ-lowZ)/2);
			if (highY-lowY > mostDistance){
				mostDistance = highY-lowY;
				highPoint = model.getHighYPoint();
				lowPoint = model.getLowYPoint();
				cylinderRadius = (highX-lowX>=highZ-lowZ ? (highX-lowX)/2 : (highZ-lowZ)/2);
			}
			if (highZ-lowZ > mostDistance){
				mostDistance = highZ-lowZ;
				highPoint = model.getHighZPoint();
				lowPoint = model.getLowZPoint();
				cylinderRadius = (highX-lowX>=highY-lowY ? (highX-lowX)/2 : (highY-lowY)/2);
			}
			
			//High point and low point modified to have radius taken off P & Q endcaps for more capsule shaped fit around object
			//NOTE: It's so snug, I wanna cuddle with it
			float[] normalHighToLow = new float[3];
			float[] normalLowToHigh = new float[3];
			VectorAndMatrixMath.calculateNormalizedDirectionVector(highPoint, lowPoint, normalHighToLow);
			VectorAndMatrixMath.calculateNormalizedDirectionVector(lowPoint, highPoint, normalLowToHigh);
			float[] capsuleTop = new float[]{(highPoint[0]+cylinderRadius*normalHighToLow[0]),(highPoint[1]+cylinderRadius*normalHighToLow[1]),(highPoint[2]+cylinderRadius*normalHighToLow[2])};
			float[] capsuleBottom = new float[]{(lowPoint[0]+cylinderRadius*normalLowToHigh[0]),(lowPoint[1]+cylinderRadius*normalLowToHigh[1]),(lowPoint[2]+cylinderRadius*normalLowToHigh[2])};
			hitbox = new Hitbox3DCapsule(capsuleTop, capsuleBottom, cylinderRadius, this);
			break;
		
		case(HITBOX_PLANE):
			hitbox = new Hitbox3DPlane(lowX, highX, lowY, highY, this);
			break;
			
		}	
	}	*/
	
	public Model3D getModel()
	{
		return model;
	}
	public float[] getAttributes()
	{
		return model.getAttributes();
	}
	public Bitmap getBitmap()
	{
		return model.getBitmap();
	}
	public boolean hasTexture()
	{
		return model.hasTexture();
	}
	public int getTextureValue()
	{
		return model.getTextureValue();
	}
	public int[] getVerticesBufferArray()
	{
		return model.getVerticesBufferArray();
	}
	public int[] getIndicesBufferArray()
	{
		return model.getIndicesBufferArray();
	}
	public int getVerticesBuffer()
	{
		return model.getVerticesBuffer();
	}
	public int getIndicesBuffer()
	{
		return model.getIndicesBuffer();
	}
	public FloatBuffer getVerticesFloatBuffer()
	{
		return model.getVerticesFloatBuffer();
	}
	public ShortBuffer getIndicesShortBuffer()
	{
		return model.getIndicesShortBuffer();
	}
	public int getIndicesCount()
	{
		return model.getIndicesCount();
	}

	
}
