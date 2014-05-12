package theGameEngine.hitboxes.hitboxes3D;

import theGameEngine.items.items3D.Item3D;
import theGameEngine.math.VectorAndMatrixMath;

public class Hitbox3DPlane extends Hitbox3D{

	float lowX,highX,lowY,highY;
	float[] normal = new float[]{0,0,1};
	float t;
	
	Item3D item;
	
	
	public Hitbox3DPlane(float lowX, float highX, float lowY, float highY, Item3D item){
		this.lowX = lowX;
		this.highX = highX;
		this.lowY = lowY;
		this.highY = highY;
		
		this.item = item;
	}

	@Override
	public boolean rayCollisionDetect(float[] rayBegin, float[] rayEnd) {
		VectorAndMatrixMath.calculateDirectionVector(rayBegin, rayEnd, direction);
		
		return false;
	}
	
}
