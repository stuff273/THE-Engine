package theGameEngine.hitboxes.hitboxes3D;

import theGameEngine.items.items3D.Item3D;
import theGameEngine.math.VectorAndMatrixMath;

public class Hitbox3DSphere extends Hitbox3D{

	public float[] center = new float[3];
	public float[] originCenter = new float[]{0,0,0};
	public float[] distanceFromOrigin;
	public float radius;
	float[] m = new float[3];
	float b,c,discr;
	
	Item3D item;
	
	public Hitbox3DSphere(float[] distanceFromOrigin, float radius, Item3D item){
		this.distanceFromOrigin = new float[]{distanceFromOrigin[0],distanceFromOrigin[1],distanceFromOrigin[2]};
		this.radius = radius;
		
		this.item = item;
	}
	
	@Override
	public boolean rayCollisionDetect(float[] rayBegin, float[] rayEnd) {
		center[0] = originCenter[0]+distanceFromOrigin[0]+item.getTranslationValueX();
		center[1] = originCenter[1]+distanceFromOrigin[1]+item.getTranslationValueY();
		center[2] = originCenter[2]+distanceFromOrigin[2]+item.getTranslationValueZ();
		
		VectorAndMatrixMath.calculateNormalizedDirectionVector(rayBegin, rayEnd, direction);
		VectorAndMatrixMath.subtractPoints(rayBegin, center, m);
		
		b = VectorAndMatrixMath.dotProduct(m, direction);
		c = VectorAndMatrixMath.dotProduct(m, m) - (radius*radius);
		
		if (c>0 && b>0)
			return false;
		
		discr = b*b - c;
		
		if (discr < 0)
			return false;
		
		return true;
	}
	
}
