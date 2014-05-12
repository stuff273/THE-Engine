package theGameEngine.controllers;

import theGameEngine.main.Main;
import android.content.res.AssetManager;


public abstract class Controller {
	
	protected float touchPixelX = 0;
	protected float touchPixelY = 0;
	
	//Boolean value to have the Controller check for item touch
	protected boolean checkTouch = false;
	//Boolean value to have the Controller check for item release
	protected boolean checkRelease = false;
	//Boolean value to indicate if an Object has been touched in the controller
	protected boolean onTouch = false;
	//Boolean value to indicate if an Object is currently touched in the controller
	protected boolean whileTouched = false;
	//Boolean value to indicate if an Object has been touched in the controller
	protected boolean onRelease = false;
	
	protected AssetManager assets;

	
	public Controller(AssetManager assets)
	{
		this.assets = assets;
	}
	
	
	public float getTouchPixelX()
	{
		return Main.getTouchPixelX();
	}
	public float getTouchPixelY()
	{
		return Main.getTouchPixelY();
	}
	
	public void setCheckTouch(boolean checkTouch)
	{
		this.checkTouch = checkTouch;
	}
	public void setCheckRelease(boolean checkRelease)
	{
		this.checkRelease = checkRelease;
	}
	public boolean getOnTouch()
	{
		return onTouch;
	}
	public boolean getWhileTouched()
	{
		return whileTouched;
	}
	public boolean getOnRelease()
	{
		return onRelease;
	}
	
	// Abstract method that a Controller must call to load all non-OpenGL data (can be loaded from another thread)
	// Used to:
	//	1.) Load Models
	//	2.) Load bitmaps
	//	3.) Parse data files
	public abstract void loadInitialNonOpenGLData();
	
	// Abstract method that a Controller must call to load all OpenGL data
	// Used to:
	//	1.) Generate and bind textures
	//	2.) Generate and bind buffers
	public abstract void loadInitialOpenGLData();
	
	//Abstract method that will check the Items of the controller to see if any are touched
	protected abstract boolean checkTouch();
	
	//Abstract method that will setup the controller and its touchedItem for release
	protected abstract void checkRelease();
	
	
	//Abstract method that updates all models of respective controller
	protected abstract void update(float deltaTime);
	
	//Abstract method that renders all models of respective controller
	protected abstract void renderModels();
	
	
	
}
