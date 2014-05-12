package theGameEngine.collisionDetection;

import theGameEngine.items.items2D.Item2D;
import theGameEngine.math.VectorAndMatrixMath;


public class CollisionDetect2D 
{
	
	public static boolean collisionDetectCircleOnScreenPoint(Item2D item, float screenX, float screenY)
	{
		float squaredDist = ((screenX-item.getTranslationValueX())*(screenX-item.getTranslationValueX())) +
							((screenY-item.getTranslationValueY())*(screenY-item.getTranslationValueY()));
		squaredDist *= squaredDist;
		
		float radius = getCircleRadius(item);
		return squaredDist <= radius*radius;
	}
	public static boolean collisionDetectAABBOnScreenPoint(Item2D item, float screenX, float screenY) {
		return (screenX > getAABBScreenLeft(item) && screenX < getAABBScreenRight(item) &&
				screenY > getAABBScreenBottom(item) && screenY < getAABBScreenTop(item));
	}
	
	public static boolean collisionDetectCircleOnRay(Item2D item, float ax, float ay, float bx, float by)
	{
		float mx = ax - item.getTranslationValueX();
		float my = ay - item.getTranslationValueY();
		float length = (float) Math.sqrt((bx-ax)*(bx-ax)+(by-ay)*(by-ay));
		float dx = (bx-ax)/length;
		float dy = (by-ay)/length;
		
		float b = VectorAndMatrixMath.dotProduct2D(mx, my, dx, dy);
		float radius = getCircleRadius(item);
		float c = VectorAndMatrixMath.dotProduct2D(mx, my, mx, my) - radius*radius;
		if (c>0 && b>0) return false;
		
		if (b*b-c < 0) return false;
		
		return true;
	}
	public boolean collisionDetectAABBOnRay(Item2D item, float ax, float ay, float bx, float by)
	{
		/*if (getScreenTop() > screenY && getScreenBottom() > screenY) 
		{
			if (getScreenLeft() > screenX && getScreenRight() > screenX)
				return true;
			else if (getScreenLeft() < screenX && getScreenRight() < screenX)
				return true;
		}
		else if (getScreenTop() < screenY && getScreenBottom() < screenY) 
		{
			if (getScreenLeft() > screenX && getScreenRight() > screenX)
				return true;
			else if (getScreenLeft() < screenX && getScreenRight() < screenX)
				return true;
		}*/
		
		return false;
	}
	
	public static boolean collisionDetectCircleOnCircle(Item2D item1, Item2D item2)
	{
		float centerDifferenceX = item1.getTranslationValueX() - item2.getTranslationValueX();
		float centerDifferenceY = item1.getTranslationValueY() - item2.getTranslationValueY();
		
		float distance = VectorAndMatrixMath.dotProduct2D(centerDifferenceX, centerDifferenceY,
															centerDifferenceX, centerDifferenceY);
		float radiusSum = getCircleRadius(item1) + getCircleRadius(item2);
		
		return distance <= radiusSum*radiusSum;
	}
	
	public static boolean collisionDetectCircleOnAABB(Item2D item1, Item2D item2) 
	{
		float squaredDistance = squaredDistFromPointToAABB(item1.getTranslationValueX(),
															item1.getTranslationValueY(), item2);
		
		float radius = getCircleRadius(item1);
		return squaredDistance <= radius*radius;
	}
	


	public static float getCircleRadius(Item2D item)
	{
		if (item.getModel().getWidth()*item.getScaleValueX() >= item.getModel().getHeight()*item.getScaleValueY())
			return item.getModel().getWidth()/2*item.getScaleValueX();
		return item.getModel().getHeight()/2*item.getScaleValueY();
	}
	public static float getAABBScreenLeft(Item2D item)
	{
		return item.getTranslationValueX() - (item.getModel().getWidth()*item.getScaleValueX()/2);
	}
	public static float getAABBScreenRight(Item2D item)
	{
		return item.getTranslationValueX() + (item.getModel().getWidth()*item.getScaleValueX()/2);
	}
	public static float getAABBScreenTop(Item2D item)
	{
		return item.getTranslationValueY() + (item.getModel().getHeight()*item.getScaleValueY()/2);
	}
	public static float getAABBScreenBottom(Item2D item)
	{
		return item.getTranslationValueY() - (item.getModel().getHeight()*item.getScaleValueY()/2);
	}
	
	public static float squaredDistFromPointToAABB(float screenX, float screenY, Item2D item)
	{
		float squareDist = 0.0f;
		
		float screenLeft = getAABBScreenLeft(item);
		float screenRight = getAABBScreenRight(item);
		float screenBottom = getAABBScreenBottom(item);
		float screenTop = getAABBScreenTop(item);
		
		if (screenX <= screenLeft)
			squareDist += (screenLeft - screenX) * (screenLeft - screenX);
		else if (screenX > screenRight)
			squareDist += (screenX - screenRight) * (screenX - screenRight);
		
		if (screenY <= screenBottom)
			squareDist += (screenBottom - screenY) * (screenBottom - screenY);
		else if (screenY > screenTop)
			squareDist += (screenY - screenTop) * (screenY - screenTop);
		
		return squareDist;
	}
	

}
