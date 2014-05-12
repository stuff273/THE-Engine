package theGameEngine.controllers.Scenes.Scenes2D;

import java.util.List;

import theGameEngine.collisionDetection.CollisionDetect2D;
import theGameEngine.controllers.Controller2D;
import theGameEngine.items.items2D.Item2D;
import theGameEngine.items.items2D.spritesAndButtons.SpriteColorPillar;
import theGameEngine.items.items2D.spritesAndButtons.SpriteSinkyFish;
import theGameEngine.items.items2D.spritesAndButtons.SpriteNoTouch;
import theGameEngine.main.Main;
import theGameEngine.modelSets.modelSets2D.ModelSet2DStart;
import android.content.res.AssetManager;

public class Scene2DSinkyFish extends Controller2D
{
	SpriteNoTouch background;
	SpriteSinkyFish theSinkyFish;
	SpriteColorPillar[] pillars;
	
	int colorPillarClosestIndex;
	int colorPillarFurthestIndex;
	
	boolean falling = false;
	float gravityConstant = 9.81f*.4f;
	float vi = 0;
	
	
	public Scene2DSinkyFish(AssetManager assets) 
	{
		super(assets);
	}
	
	@Override
	protected boolean checkTouch()
	{
		if (!falling)
			falling = true;
		if (theSinkyFish.getTranslationValueY() >= -1)
			vi = -1.15f;
		return false;
	}
	
	@Override
	public void update(float deltaTime)
	{
		if (falling)
		{
			float d = (vi*deltaTime) + (0.5f*gravityConstant*deltaTime*deltaTime);
			vi = vi +gravityConstant*deltaTime;
			
			theSinkyFish.setTranslationValueY(theSinkyFish.getTranslationValueY()+d);
			
			for (int i=0; i<pillars.length; i++)
				pillars[i].setTranslationValueX(pillars[i].getTranslationValueX() - deltaTime/2);
			
			if (pillars[colorPillarClosestIndex].getTranslationValueX() <= -Main.getAspectRatio()-pillars[colorPillarClosestIndex].getModel().getWidth()/2.0f*pillars[colorPillarClosestIndex].getScaleValueX())
			{
				pillars[colorPillarClosestIndex].setTranslationValueX(pillars[colorPillarFurthestIndex].getTranslationValueX() + Main.getAspectRatio());
				pillars[colorPillarClosestIndex+1].setTranslationValueX(pillars[colorPillarFurthestIndex].getTranslationValueX() + Main.getAspectRatio());
				colorPillarFurthestIndex = colorPillarClosestIndex;
				colorPillarClosestIndex += 2;
				if (colorPillarClosestIndex > 5)
					colorPillarClosestIndex = 0;
			}
			
			//========================================================================================================================
			// Collision detection
			
			if (CollisionDetect2D.collisionDetectCircleOnRay(theSinkyFish, -Main.getAspectRatio(), 1, Main.getAspectRatio(), 1))
			{
				//theSinkyFish.setTranslationValueY(1-theSinkyFish.getModel().getHeight()/2f*theSinkyFish.getScaleValueY());
				//falling = false;
				reset();
			}
			for (int i=0; i<pillars.length; i++)
			{
				if (CollisionDetect2D.collisionDetectCircleOnAABB(theSinkyFish,pillars[i]))
					reset();
			}
			//========================================================================================================================


		}
	}

	
	
	
	
	@Override
	public void loadInitialNonOpenGLData() 
	{
		loadBitmapAndSetTextureValue("2D.png", 1);
	}
	
	@Override
	protected void loadModels() 
	{
		modelSet2D = new ModelSet2DStart(this);
	}
	
	@Override
	protected void addInitialObjects(List<Item2D> initialObjectsToBeAdded) 
	{
		background = new SpriteNoTouch(0,0, ModelSet2DStart.background, this);
		background.setTranslationValueZ(-.01f);
		initialObjectsToBeAdded.add(background);
		
		theSinkyFish = new SpriteSinkyFish(-.5f,0, this);
		theSinkyFish.setScaleValues(1.5f, 1.5f, 1.0f);
		initialObjectsToBeAdded.add(theSinkyFish);
		
		pillars = new SpriteColorPillar[6];
		int direction = 1;
		for (int i=0; i<pillars.length; i++)
		{
			pillars[i] = new SpriteColorPillar(0f, Main.getAspectRatio()*2*direction, this);
			direction *= -1;
		}
		pillars[0].setTranslationValueXStart(Main.getAspectRatio()+pillars[0].getModel().getWidth()/2);
		pillars[1].setTranslationValueXStart(Main.getAspectRatio()+pillars[1].getModel().getWidth()/2);
		pillars[2].setTranslationValueXStart(pillars[0].getTranslationValueX()+Main.getAspectRatio());
		pillars[3].setTranslationValueXStart(pillars[0].getTranslationValueX()+Main.getAspectRatio());
		pillars[4].setTranslationValueXStart(pillars[0].getTranslationValueX()+Main.getAspectRatio()*2);
		pillars[5].setTranslationValueXStart(pillars[0].getTranslationValueX()+Main.getAspectRatio()*2);
		for (int i=0; i<pillars.length; i++)
			initialObjectsToBeAdded.add(pillars[i]);
		
		colorPillarClosestIndex = 0;
		colorPillarFurthestIndex = 4;
	}
	
	private void reset()
	{
		theSinkyFish.resetTranslationValues();
		
		for (int i=0; i<pillars.length; i++)
			pillars[i].resetTranslationValues();
		
		colorPillarClosestIndex = 0;
		colorPillarFurthestIndex = 4;
		falling = false;
	}

}
