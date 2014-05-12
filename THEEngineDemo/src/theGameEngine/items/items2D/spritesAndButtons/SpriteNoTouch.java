package theGameEngine.items.items2D.spritesAndButtons;

import theGameEngine.controllers.Controller2D;
import theGameEngine.models.Model2D;

public class SpriteNoTouch extends Sprite
{

	public SpriteNoTouch(float screenCenterX, float screenCenterY, Model2D model, Controller2D controller2D)
	{
		super(screenCenterX, screenCenterY, model, controller2D);
		
		touchable = false;
	}

	@Override
	public void onTouch(float touchPixelX, float touchPixelY, float startTime)
	{
		
	}

	@Override
	public void whileTouched(float touchPixelX, float touchPixelY, float deltaTime)
	{
		
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime)
	{
		// TODO Auto-generated method stub
		
	}

}
