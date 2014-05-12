package theGameEngine.controllers.Scenes.Scenes2D;

import java.util.ArrayList;
import java.util.List;

import theGameEngine.controllers.Controller2D;
import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.items.items2D.Item2D;
import theGameEngine.items.items2D.spritesAndButtons.Background;
import android.content.res.AssetManager;
import android.opengl.GLES20;

public abstract class Scene2D extends Controller2D{
		
	protected int[] backgroundVerticesBuffer = null;
	protected int[] backgroundIndicesBuffer = null;
	
	// The partnering GUI
	protected GUI gui;
	
	Background background;
	
	
	public Scene2D(AssetManager assets)
	{	
		super(assets);		
	}
	
	public void setGUI(GUI gui)
	{
		this.gui = gui;
	}
	
	protected void setNewBackground(Background background)
	{
		
		if (backgroundVerticesBuffer == null)
		{
			backgroundVerticesBuffer = new int[1];
			GLES20.glGenBuffers(1, backgroundVerticesBuffer, 0);
		}
		if (backgroundIndicesBuffer == null)
		{
			backgroundIndicesBuffer = new int[1];
			GLES20.glGenBuffers(1, backgroundIndicesBuffer, 0);
		}
		
		List<Item2D> tempBackgroundList = new ArrayList<Item2D>();
		tempBackgroundList.add(background);	
		
		//Controller2D.createVerticesBuffer(backgroundVerticesBuffer[0], tempBackgroundList);
		//Controller2D.createQuadIndicesBuffer(backgroundIndicesBuffer[0], tempBackgroundList);
		
		background.setModelTypeListIndex(0);
		
		itemsAll.add(background);
		
		this.background = background;
	}
	
}
