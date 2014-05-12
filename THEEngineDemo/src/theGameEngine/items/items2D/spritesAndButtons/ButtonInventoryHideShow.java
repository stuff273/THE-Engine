package theGameEngine.items.items2D.spritesAndButtons;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.main.Main;
import theGameEngine.modelSets.modelSets2D.ModelSet2D;

public class ButtonInventoryHideShow extends Sprite
{

	public ButtonInventoryHideShow(float centerX, float centerY, GUI gui)
	{	
		super(centerX, centerY, ModelSet2D.buttonInventoryHide, gui);
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
		getConnectedItem(0).setRotationValue(getConnectedItem(0).getRotationValue()-deltaTime*100);
		
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
