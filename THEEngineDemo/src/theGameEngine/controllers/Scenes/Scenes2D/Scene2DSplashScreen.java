package theGameEngine.controllers.Scenes.Scenes2D;

import java.util.List;

import theGameEngine.animations.Animation2D;
import theGameEngine.controllers.Controller2D;
import theGameEngine.items.items2D.Item2D;
import theGameEngine.items.items2D.spritesAndButtons.SpriteNoTouch;
import theGameEngine.main.Main;
import theGameEngine.modelSets.modelSets2D.ModelSet2DSinkyFishSplashScreen;
import android.content.res.AssetManager;

public class Scene2DSplashScreen extends Controller2D
{
	
	SpriteNoTouch splashlogo;
	SpriteNoTouch colorbox;
	
	public Scene2DSplashScreen(AssetManager assets) 
	{
		super(assets);
	}
	
	@Override
	protected boolean checkTouch()
	{
		//splashlogo.setAnimating(!splashlogo.getAnimating());
		Main.startLoad(new Scene2DSinkyFish(assets));
		return false;
	}

	
	
	
	
	@Override
	public void loadInitialNonOpenGLData() 
	{
		loadBitmapAndSetTextureValue("splashscreen.png", 0);
	}
	
	@Override
	protected void loadModels() 
	{
		modelSet2D = new ModelSet2DSinkyFishSplashScreen(this);
	}
	
	@Override
	protected void addInitialObjects(List<Item2D> initialObjectsToBeAdded) 
	{
		splashlogo = new SpriteNoTouch(0,0,ModelSet2DSinkyFishSplashScreen.splashlogo,this);
		splashlogo.setAnimation(Animation2D.GrowAndShrink(2));
		initialObjectsToBeAdded.add(splashlogo);
		
		colorbox = new SpriteNoTouch(0f,-.5f, ModelSet2DSinkyFishSplashScreen.colorbox, this);
		//initialObjectsToBeAdded.add(colorbox);
	}

}
