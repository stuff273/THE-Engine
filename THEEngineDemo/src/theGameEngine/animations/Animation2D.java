package theGameEngine.animations;

import theGameEngine.items.items2D.Item2D;

public abstract class Animation2D {
	
	public abstract boolean perform(Item2D item2D, float deltaTime);
	
/*	public static class Grow extends Animation{
		public Grow(float scaleValue){
			this.scaleValueX = scaleValue;
			this.scaleValueY = scaleValue;
			this.scaleValueZ = scaleValue;
		}
		public Grow(float scaleValueX, float scaleValueY, float scaleValueZ){
			this.scaleValueX = scaleValueX;
			this.scaleValueY = scaleValueY;
			this.scaleValueZ = scaleValueZ;
		}
		
		@Override
		public boolean perform(Item item, float deltaTime) {
			item.setScaleValueX(scaleValueX);
			item.setScaleValueY(scaleValueY);
			item.setScaleValueZ(scaleValueZ);
			item.setAnimating(false);
			return true;
		}
	}
	public static Animation Grow(float scaleValue){
		return new Grow(scaleValue);
	}
	public static Animation Grow(float scaleValueX, float scaleValueY, float scalueValueZ){
		return new Grow(scaleValueX,scaleValueY,scalueValueZ);
	}*/
	
	public static Animation2D GrowAndShrink()
	{
		return new GrowAndShrinkClass();
	}
	public static Animation2D GrowAndShrink(float speed)
	{
		return new GrowAndShrinkClass(speed);
	}
	public static Animation2D GrowAndShrink(float speed, float maxSize)
	{
		return new GrowAndShrinkClass(speed,maxSize);
	}
	
	private static class GrowAndShrinkClass extends Animation2D
	{
		int direction;
		float speed;
		float maxSize;
		
		public GrowAndShrinkClass()
		{
			direction = 1;
			speed = 1;
		}
		public GrowAndShrinkClass(float speed)
		{
			direction = 1;
			this.speed = speed;
			maxSize = 2;
		}
		public GrowAndShrinkClass(float speed, float maxSize)
		{
			direction = 1;
			this.speed = speed;
			this.maxSize = maxSize;
		}
		
		@Override
		public boolean perform(Item2D item, float deltaTime) 
		{
			deltaTime *= speed;
			if (direction == 1 && item.getScaleValueX() + deltaTime > maxSize)
				direction *= -1;
			else if (direction == -1 && item.getScaleValueX() - deltaTime < 1)
				direction *= -1;
			
			item.setScaleValueX(item.getScaleValueX() + deltaTime*direction);
			item.setScaleValueY(item.getScaleValueY() + deltaTime*direction);
			
			return false;
		}
		
	}
	
	
	public static Animation2D GravityFall()
	{
		return new GravityFallClass();
	}
	public static Animation2D GravityFall(float gravityModifier)
	{
		return new GravityFallClass(gravityModifier);
	}
	
	private static class GravityFallClass extends Animation2D
	{
		float gravityConstant;
		float vi;
		
		public GravityFallClass()
		{
			gravityConstant = -9.81f;
			vi = 0;
		}
		public GravityFallClass(float gravityModifier)
		{
			gravityConstant = -9.81f * gravityModifier;
			vi = 0;
		}
		
		@Override
		public boolean perform(Item2D item, float deltaTime) 
		{
			float d = (vi*deltaTime) + (0.5f*gravityConstant*deltaTime*deltaTime);
			vi = vi +gravityConstant*deltaTime;
			item.setTranslationValueY(item.getTranslationValueY()+d);
			
			return false;
		}
		
	}

	
	/*public static class BounceConstant extends Animation{
		public BounceConstant(float initialY, float gravityMod){
			this.initialY = initialY;
			this.gravity  = 9.8f * gravityMod;
		}
		
		@Override
		public boolean perform(Item item, float deltaTime) {
			return true;
			if (posY<item.translationValueYStart){
				posY = item.translationValueYStart;
				animationTime = 0;
			}
			animationTime += deltaTime/6;
			posY = (initialY*animationTime)-(gravity*(animationTime*animationTime));
			
			item.translationValueY = posY;

	        return true;
		}
	}
	public static Animation BounceConstant(float initialY, float gravityMod){
		return new BounceConstant(initialY, gravityMod);
	}
	
	
	public static class BounceSeries extends Animation{
		public BounceSeries(float initialY, float gravityMod, float delay){
			this.initialY = initialY;
			this.gravity  = 9.8f * gravityMod;
			this.delay = delay;
		}
		
		@Override
		public boolean perform(Item item, float deltaTime) {
			if (posY<-0.001f){
				initialY = animationTime * gravity/1.5f;
				animationTime = 0;
			}
			
			animationTime += deltaTime/6;
			
			if (initialY < .9f && posY != 0)
				animationTime = 0;
			
			if (initialY < .9f)
				posY = 0;
			
			else
				posY = (initialY*animationTime)-(gravity*(animationTime*animationTime));
			
			if (posY == 0 && animationTime > delay)
				this.reset();

			item.setTranslationValueY(posY);
			
	        return true;
		}
	}
	public static Animation BounceSeries(float initialY, float gravityMod, float delay){
		return new BounceSeries(initialY, gravityMod, delay);
	}
	
	
	public static class RotateSideToSide extends Animation{
		public RotateSideToSide(float speed, float range){
			this.speed = speed;
			this.range = range;
		}
		
		@Override
		public boolean perform(Item item, float deltaTime) {
			posX += speed*(deltaTime*10);
			if (posX > range){
				posX = range;
				speed *= -1;
			}
			if (posX < -range){
				posX = -range;
				speed *= -1;
			}

			item.setRotationValue(posX);
			return true;
		}
	}
	public static Animation RotateSideToSide(float speed, float range){
		return new RotateSideToSide(speed, range);
	}*/
	
}
