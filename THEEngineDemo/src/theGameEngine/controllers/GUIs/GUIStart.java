package theGameEngine.controllers.GUIs;

import java.util.List;

import theGameEngine.items.items2D.Item2D;
import theGameEngine.items.items2D.spritesAndButtons.ButtonNoAssignedAction;
import theGameEngine.items.items2D.spritesAndButtons.Sprite;
import theGameEngine.items.items2D.textboxes.Textbox;
import android.content.res.AssetManager;

public class GUIStart extends GUI{
	
	Sprite button;
	ButtonNoAssignedAction colorbox;
	
	Textbox textboxSign;
	Textbox textboxDialog;
	Textbox textboxFPS;
	
	public GUIStart(AssetManager assets) 
	{	
		super(assets);
	}

	
	@Override
	protected void addInitialObjects(List<Item2D> initialObjectsToBeAdded) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void loadInitialNonOpenGLData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadInitialOpenGLData() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void loadModels() {
		// TODO Auto-generated method stub
		
	}
}
