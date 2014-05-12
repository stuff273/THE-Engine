package theGameEngine.controllers.Scenes.Scenes3D;

import java.util.List;

import theGameEngine.controllers.Controller3D;
import theGameEngine.items.items3D.Item3D;
import theGameEngine.items.items3D.Mesh;
import theGameEngine.modelSets.modelSets3D.ModelSet3DStart;
import android.content.res.AssetManager;

public class Scene3DStart extends Controller3D{

	Mesh cube = null;
	Mesh monkeyBlue = null;
	
	public Scene3DStart(AssetManager assets) 
	{	
		super(assets);	
	}

	@Override
	protected void loadModels()
	{
		modelSet3D = new ModelSet3DStart(assets);
	}

	@Override
	protected void addInitialObjects(List<Item3D> initialObjectsToBeAdded) {
		cube = new Mesh(new float[]{0,0,0}, ModelSet3DStart.redCube, this);
		monkeyBlue = new Mesh(new float[]{0,0,0}, ModelSet3DStart.monkeyBlue, this);
		initialObjectsToBeAdded.add(monkeyBlue);
	}

	@Override
	public void loadInitialNonOpenGLData() {
		// TODO Auto-generated method stub
		
	}



}
