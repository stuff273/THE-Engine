package theGameEngine.hitboxes.hitboxes3D;

import theGameEngine.items.items3D.Item3D;
import theGameEngine.math.VectorAndMatrixMath;

public class Hitbox3DCylinder extends Hitbox3D{

	public float radius;
	float[] d = new float[3];
	float[] n = new float[3];
	float[] m = new float[3];
	
	float[] dCrossN = new float[3];
	float[] dCrossM = new float[3];
	
	public float[] capsuleTop = new float[3],capsuleBottom = new float[3];
	public float[] capsuleTopOrigin, capsuleBottomOrigin;
	
	float dDotD,dDotN,dDotM;
	float t;
	
	Item3D item;
	
	
	public Hitbox3DCylinder(float[] pointTop, float[] pointBottom, float radius, Item3D item){
		this.capsuleTopOrigin = new float[]{pointTop[0],pointTop[1],pointTop[2]};
		this.capsuleBottomOrigin = new float[]{pointBottom[0],pointBottom[1],pointBottom[2]};
		this.radius = radius;
		
		this.item = item;
	}

	@Override
	public boolean rayCollisionDetect(float[] rayBegin, float[] rayEnd) {
		capsuleTop[0] = capsuleTopOrigin[0] + item.getTranslationValueX();
		capsuleTop[1] = capsuleTopOrigin[1] + item.getTranslationValueY();
		capsuleTop[2] = capsuleTopOrigin[0] + item.getTranslationValueZ();
		
		capsuleBottom[0] = capsuleBottomOrigin[0] - item.getTranslationValueX();
		capsuleBottom[1] = capsuleBottomOrigin[1] - item.getTranslationValueY();
		capsuleBottom[2] = capsuleBottomOrigin[2] - item.getTranslationValueZ();
		
		VectorAndMatrixMath.subtractPoints(capsuleTop, capsuleBottom, d);
		VectorAndMatrixMath.subtractPoints(rayEnd, rayBegin, n);
		VectorAndMatrixMath.subtractPoints(rayBegin, capsuleBottom, m);
		
		VectorAndMatrixMath.crossProduct(d, n, dCrossN);
		VectorAndMatrixMath.crossProduct(d, m, dCrossM);
		
		dDotD = VectorAndMatrixMath.dotProduct(d, d);
		dDotN = VectorAndMatrixMath.dotProduct(d, n);
		dDotM = VectorAndMatrixMath.dotProduct(d, m);
		
		float a = VectorAndMatrixMath.dotProduct(dCrossN, dCrossN);
		float b = VectorAndMatrixMath.dotProduct(dCrossM, dCrossN);
		float c = VectorAndMatrixMath.dotProduct(dCrossM, dCrossM) - (radius*radius)*dDotD;
		
		float discr = b*b-a*c;
		t = (float) (-b-Math.sqrt(discr))/a;
		
		if (discr <= 0)
			return false;
		if (dDotM+t*dDotN<0)
			return false;
		if (dDotM+t*dDotN>dDotD)
			return false;
	
		return true;
	}
	
}
