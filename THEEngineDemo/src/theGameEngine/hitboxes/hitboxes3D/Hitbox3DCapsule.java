package theGameEngine.hitboxes.hitboxes3D;

import theGameEngine.items.items3D.Item3D;

public class Hitbox3DCapsule extends Hitbox3D{

	Hitbox3DSphere hitboxSphereTop, hitboxSphereBottom;
	Hitbox3DCylinder hitboxCylinder;
	
	
	public Hitbox3DCapsule(float[] pointTop, float[] pointBottom, float radius, Item3D item){
		
		this.hitboxSphereTop = new Hitbox3DSphere(pointTop, radius, item);
		this.hitboxSphereBottom = new Hitbox3DSphere(pointBottom, radius, item);
		this.hitboxCylinder = new Hitbox3DCylinder(pointTop, pointBottom, radius, item);
	}
	
	@Override
	public boolean rayCollisionDetect(float[] rayBegin, float[] rayEnd) {
		if (hitboxSphereTop.rayCollisionDetect(rayBegin, rayEnd))
			return true;
		else if (hitboxSphereBottom.rayCollisionDetect(rayBegin, rayEnd))
			return true;
		else if (hitboxCylinder.rayCollisionDetect(rayBegin, rayEnd))
			return true;
		
		return false;
	}	

}
