package theGameEngine.items.items2D.spritesAndButtons;

import theGameEngine.controllers.Controller2D;
import theGameEngine.models.Model2D;

public class Background extends Sprite
{
	float textureScreenValueLeft = 0;
	float textureScreenValueRight = 0;

	public Background(float screenCenterX, float screenCenterY, Model2D model, Controller2D controller2D)
	{
		super(screenCenterX, screenCenterY, model, controller2D);
		
		touchable = false;
		
		textureScreenValueLeft = getTextureValueTopLeftX();
		textureScreenValueRight = 505/controller2D.getTextureImageWidth();
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
	
	@Override
	public void update(float deltaTime)
	{
		deltaTime /= 15;

		setTextureValueTopLeftX(getTextureValueTopLeftX()+deltaTime);
		setTextureValueBottomLeftX(getTextureValueBottomLeftX()+deltaTime);

		setTextureValueTopRightX(getTextureValueTopRightX()+deltaTime);
		setTextureValueBottomRightX(getTextureValueBottomRightX()+deltaTime);	
	}

}
