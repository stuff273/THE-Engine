package theGameEngine.models;


public class Model {
	public static final int TYPE_N = 0;
	public static final int TYPE_T = 1;
	public static final int TYPE_C = 2;
	public static final int TYPE_NT = 3;
	public static final int TYPE_NC = 4;
	
	protected int type = -1;
	

	protected float[] attributes;
	protected short[] indices;
	
	
	protected float highX = 0;
	protected float lowX = 0;
	protected float highY = 0;
	protected float lowY = 0;
	protected float highZ = 0;
	protected float lowZ = 0;
	
	
	public int getType(){
		return type;
	}
	public float[] getAttributes(){
		return attributes;
	}
	public short[] getIndices(){
		return indices;
	}
	public float getAttribute(int index){
		return attributes[index];
	}	
	
	public float getHighX(){
		return highX;
	}
	public float getLowX(){
		return lowX;
	}
	public float getHighY(){
		return highY;
	}
	public float getLowY(){
		return lowY;
	}
	public float getHighZ(){
		return highZ;
	}
	public float getLowZ(){
		return lowZ;
	}
	
	public float getWidth(){
		return highX - lowX;
	}
	public float getHeight(){
		return highY - lowY;
	}
	public float getDepth(){
		return highZ - lowZ;
	}
	
	

}
