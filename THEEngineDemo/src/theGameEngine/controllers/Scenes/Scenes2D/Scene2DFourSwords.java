package theGameEngine.controllers.Scenes.Scenes2D;

import java.util.List;

import theGameEngine.items.items2D.Item2D;
import theGameEngine.items.items2D.spritesAndButtons.SpriteLinkIdle;
import theGameEngine.items.items2D.spritesAndButtons.SpriteNoTouch;
import theGameEngine.main.Main;
import theGameEngine.modelSets.modelSets2D.ModelSet2DFourSwords;
import theGameEngine.models.Model2D;
import android.content.res.AssetManager;

public class Scene2DFourSwords extends Scene2D{

	SpriteLinkIdle link;
	SpriteNoTouch loadingBar;
	
	int keyframeIndex;
	
	boolean beginSwinging = false;
	boolean swinging = false;
	float passedTimeSwinging = 0;
	
	public Scene2DFourSwords(AssetManager assets)
	{
		super(assets);
	}
	
	
	@Override
	public boolean checkTouch()
	{
		// This is a test of GIT branches
		// Moves link to location of touch and starts swinging animation
		link.setTranslationValueX(Main.getTouchScreenX());
		link.setTranslationValueY(Main.getTouchScreenY());
		keyframeIndex = 0;
		swinging = true;
		return false;
	}
	
	@Override
	public void update(float deltaTime)
	{
		loadingBar.setTranslationValueX(loadingBar.getTranslationValueXStart()+Main.getLoadingNormalPercentage()*Main.getAspectRatio()*2);
		if (loadingBar.getTranslationValueX() == 0)
			loadingBar.setVisible(false);
		
		if (swinging)
		{
			passedTimeSwinging += deltaTime;
			if (passedTimeSwinging > .027f)
			{
				keyframeIndex++;
				if (keyframeIndex == 8)
				{
					keyframeIndex = 0;
					swinging = false;
				}
				
				switch(keyframeIndex)
				{
				case(0):
					link.setModel(ModelSet2DFourSwords.link);
					break;
				case(1):
					link.setModel(ModelSet2DFourSwords.linkSwordSwing0);
					break;
				case(2):
					link.setModel(ModelSet2DFourSwords.linkSwordSwing1);
					break;
				case(3):
					link.setModel(ModelSet2DFourSwords.linkSwordSwing2);
					break;
				case(4):
					link.setModel(ModelSet2DFourSwords.linkSwordSwing3);
					break;
				case(5):
					link.setModel(ModelSet2DFourSwords.linkSwordSwing4);
					break;
				case(6):
					link.setModel(ModelSet2DFourSwords.linkSwordSwing5);
					break;
				case(7):
					link.setModel(ModelSet2DFourSwords.linkSwordSwing6);
					break;
				}
				passedTimeSwinging = 0;
			}
		}
	}

	@Override
	protected void loadModels() 
	{
		modelSet2D = new ModelSet2DFourSwords(this);
	}

	@Override
	protected void addInitialObjects(List<Item2D> initialObjectsToBeAdded)
	{
		link = new SpriteLinkIdle(0, 0, this);
		link.setScaleValuesStart(10, 10, 10);
		initialObjectsToBeAdded.add(link);
		
		loadingBar = new SpriteNoTouch(-1*2,-0.8f,new Model2D(2f,.3f,255,0,0,1),this);
		initialObjectsToBeAdded.add(loadingBar);
		
		keyframeIndex = 0;
	}

	@Override
	public void loadInitialNonOpenGLData() 
	{
		this.loadBitmapAndSetTextureValue("2D.png", 0);
	}

}
