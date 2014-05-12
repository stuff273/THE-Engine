package theGameEngine.items.items2D.spritesAndButtons;


import theGameEngine.controllers.Controller2D;
import theGameEngine.items.items2D.Item2D;
import theGameEngine.main.Main;
import theGameEngine.models.Model2D;

public abstract class Sprite extends Item2D{
	
	public Sprite(float screenCenterX, float screenCenterY, Model2D model, Controller2D controller2D)
	{
		super(model, controller2D);
			
		setTranslationValueXStart(Main.convertNormalToScreen(screenCenterX));
		setTranslationValueYStart(screenCenterY);	
	}

	public void imageOnTouch(float touchPixelX, float touchPixelY, float startTime)
	{
		// TODO Auto-generated method stub
		itemOnTouch(startTime);
	}

	public void imageWhileTouched(float touchPixelX, float touchPixelY, float deltaTime)
	{
		// TODO Auto-generated method stub
		itemWhileTouched(deltaTime);
	}

	public void imageOnRelease(float touchPixelX, float touchPixelY, float endTime) 
	{
		// TODO Auto-generated method stub
		itemOnRelease(endTime);
	}
	

}
