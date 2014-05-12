package theGameEngine.items.items2D.spritesAndButtons;

import theGameEngine.controllers.Controller2D;
import theGameEngine.main.Main;
import theGameEngine.models.Model2D;

public class ButtonNoAssignedAction extends Sprite
{

	public ButtonNoAssignedAction(float centerX, float centerY, Model2D model, Controller2D controller2D) 
	{
		super(centerX, centerY, model, controller2D);
	}
	
	@Override
	public void onTouch(float touchPixelX, float touchPixelY, float startTime)
	{
		Main.vibrate(60);

		//animation = Animations2D.Grow(1.2f,1.3f,0);
		//performAnimation(startTime);		
		
		//onTouch method that all Buttons must call
		imageOnTouch(touchPixelX, touchPixelY, startTime);
	}

	@Override
	public void whileTouched(float touchPixelX, float touchPixelY, float deltaTime)
	{
		//whileTouched method that all Buttons must call
		imageWhileTouched(touchPixelX, touchPixelY, deltaTime);
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime) 
	{		
		if (checkTouch(touchPixelX,touchPixelY))
		{
			if (action != null)
				action.perform();
		}
		
		resetScaleValues();
		
		//onRelease method that all Buttons must call
		imageOnRelease(touchPixelX, touchPixelY, endTime);
	}
	
}
