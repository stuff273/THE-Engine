package theGameEngine.items.items2D.spritesAndButtons;

import theGameEngine.controllers.Controller2D;
import theGameEngine.modelSets.modelSets2D.ModelSet2DStart;

public class SpriteColorPillar extends SpriteNoTouch{

	public SpriteColorPillar(float screenCenterX, float screenCenterY, Controller2D controller2d) {
		super(screenCenterX, screenCenterY, ModelSet2DStart.colorPillar, controller2d);
	}

	@Override
	public void onTouch(float touchPixelX, float touchPixelY, float startTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void whileTouched(float touchPixelX, float touchPixelY,
			float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime) {
		// TODO Auto-generated method stub
		
	}

}
