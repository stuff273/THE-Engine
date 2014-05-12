package theGameEngine.hitboxes.hitboxes3D;

public abstract class Hitbox3D {
	
	float[] direction = new float[3];
	
	public abstract boolean rayCollisionDetect(float[] rayBegin, float[] rayEnd);
	

}
