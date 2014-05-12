package theGameEngine.controllers;

import java.util.ArrayList;
import java.util.List;

import theGameEngine.items.items3D.Item3D;
import theGameEngine.main.Main;
import theGameEngine.modelSets.modelSets3D.ModelSet3D;
import theGameEngine.models.Model;
import theGameEngine.textures.Textures;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.opengl.GLES20;

public abstract class Controller3D extends Controller{
	
	protected boolean loadedInitialOpenGLData = false;
	
	protected List<Item3D> objectsListTexture = new ArrayList<Item3D>();
	protected List<Item3D> objectsListColor = new ArrayList<Item3D>();
		
	
	//Pointer to check Objects for touches
	protected Item3D checkTouchObject = null;
	//Pointer to designate the touched Object
	protected Item3D touchObject = null;
		
	protected List<Item3D> itemsAll = new ArrayList<Item3D>();
	
	protected ModelSet3D modelSet3D;
		
	public Controller3D(AssetManager assets)
	{ 		
		super(assets);
	}
	
	public void loadInitialData()
	{
		loadInitialNonOpenGLData();
		
		loadModels();
		
		List<Item3D> objectsToBeAdded = new ArrayList<Item3D>();
		addInitialObjects(objectsToBeAdded);
		
		for (int i=0; i<objectsToBeAdded.size(); i++)
			addItem(objectsToBeAdded.get(i));
	}

	
	public void addItem(Item3D item)
	{
		switch(item.getModel().getType())
		{
		case(Model.TYPE_T):
			item.setModelTypeListIndex(objectsListTexture.size());
			objectsListTexture.add(item);
			break;
		case(Model.TYPE_C):
			item.setModelTypeListIndex(objectsListColor.size());
			objectsListColor.add(item);
			break;
		}
		
		item.setItemsAllListIndex(itemsAll.size());
		itemsAll.add(item);
	}
	public void removeItem(Item3D item)
	{
		switch(item.getModel().getType())
		{
		case(Model.TYPE_T):
			objectsListTexture.remove(item.getModelTypeListIndex());
			for (int i=0; i<objectsListTexture.size(); i++)
				objectsListTexture.get(i).setModelTypeListIndex(i);
			break;
		case(Model.TYPE_C):
			objectsListColor.remove(item.getModelTypeListIndex());
			for (int i=0; i<objectsListColor.size(); i++)
				objectsListColor.get(i).setModelTypeListIndex(i);
			break;
		}
		
		itemsAll.remove(item.getItemsAllListIndex());
		for (int i=0; i<itemsAll.size(); i++)
			itemsAll.get(i).setItemsAllListIndex(i);
	}
	
	protected void generateTextureAndBuffers()
	{
		for (int i=0; i<objectsListTexture.size(); i++)
		{
			if (itemsAll.get(i).getBitmap() != null)
				Textures.generateAndBindTexture(itemsAll.get(i).getBitmap(), itemsAll.get(i).getTextureValue(), GLES20.GL_LINEAR, GLES20.GL_LINEAR);
		}
		
		for (int i=0; i<itemsAll.size(); i++)
		{
			GLES20.glGenBuffers(1, itemsAll.get(i).getVerticesBufferArray(), 0);
			GLES20.glGenBuffers(1, itemsAll.get(i).getIndicesBufferArray(), 0);
			
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, itemsAll.get(i).getVerticesBuffer());
			GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, itemsAll.get(i).getVerticesFloatBuffer().capacity()*4, itemsAll.get(i).getVerticesFloatBuffer(), GLES20.GL_DYNAMIC_DRAW);
			
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, itemsAll.get(i).getIndicesBuffer());
			GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, itemsAll.get(i).getIndicesShortBuffer().capacity()*2, itemsAll.get(i).getIndicesShortBuffer(), GLES20.GL_DYNAMIC_DRAW);
		}
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	@Override
	public void loadInitialOpenGLData() 
	{
		generateTextureAndBuffers();
		//Textures.generateAndBindTexture(bitmap, textureValue, GLES20.GL_LINEAR, GLES20.GL_LINEAR);
		loadedInitialOpenGLData = true;
	}
	
	
	//=============================================================================================
	// onTouch and onRelease for Items2D
	
	// This method will be called from touchControl IF the processed touch's action is ACTION_DOWN
	protected boolean checkTouch(){
		touchPixelX = getTouchPixelX();
		touchPixelY = getTouchPixelY();
	
		/*for (int i=0; i<itemsAll.size(); i++){
			//currentTouch pointer used to mark Item2D we will be testing against
			checkTouchObject = itemsAll.get(i);
			//Call GUIItem's checkTouch method to see if the GUIItem has been touched
			if (checkTouchObject.checkTouch(touchPixelX,touchPixelY)){
				//Warning. Warning. GUIItem is hit. All hands on deck
				
				//Set the touchedObject pointer for future reference
				touchObject = checkTouchObject;
				
				checkTouchObject = null;
				
				//Set boolean to indicate that a GUIItem is touched
				onTouch = true;
				
				//Return true, a GUIItem has been touched
				return true;
			}
		}*/
		
		checkTouchObject = null;
		
		//Return false, a GUIItem has not been touched
		return false;
	}
	
	
	
	//This method will set the Item2D currently touched for onRelease state
	public void checkRelease(){

		//Clear boolean to indicate that the GUIItem is no longer touched
		whileTouched = false;
		if (touchObject != null)
			onRelease = true;
	}
	//=============================================================================================
	
	public void resolveTouches(float deltaTime)
	{
		if (checkTouch)
		{
			checkTouch();
			setCheckTouch(false);
		}
		else if (checkRelease)
		{
			checkRelease();
			setCheckRelease(false);
		}
		
		//Perform updates for the touched object(s) first
		if (onTouch)
		{
			touchObject.onTouch(getTouchPixelX(), getTouchPixelY(), deltaTime);
			onTouch = false;
			whileTouched = true;
		}
		else if (whileTouched)
		{
			touchObject.whileTouched(getTouchPixelX(), getTouchPixelY(), deltaTime);
		}
		else if (onRelease)
		{
			touchObject.onRelease(getTouchPixelX(), getTouchPixelY(), deltaTime);
			onRelease = false;
			touchObject = null;
		}
	}
	
	public void update(float deltaTime)
	{
		// Generic update for all objects
		for (int i=0; i<itemsAll.size(); i++)
			itemsAll.get(i).update(deltaTime);
	}
	
	@SuppressLint("NewApi")
	public void renderModels()
	{
		if (!loadedInitialOpenGLData)
			loadInitialOpenGLData();
		
		//===================================================================================================
		// Texture rendering
		
		if (objectsListTexture.size() > 0)
		{
			Main.useProgramT();

			for (int i=0; i<objectsListTexture.size(); i++)
			{
				Item3D tempItem = objectsListTexture.get(i);
				if (!tempItem.getVisible())
					continue;
				
				if (Main.getCurrentTextureValue() != tempItem.getTextureValue())
					Main.switchToTexture(tempItem.getTextureValue());
				
				GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,	tempItem.getVerticesBuffer());
				GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, tempItem.getIndicesBuffer());
		        
				Main.updateMVP3DMatrix();
				Main.assignHandlesToCurrentlyBoundBufferT3D();
				
				GLES20.glDrawElements(GLES20.GL_TRIANGLES, tempItem.getIndicesCount(), GLES20.GL_UNSIGNED_SHORT, 0);
			}
		}
		//===================================================================================================
		
		
		//===================================================================================================
		// Color rendering
				
		if (objectsListColor.size() > 0)
		{
			Main.useProgramC();

			for (int i=0; i<objectsListColor.size(); i++)
			{
				Item3D tempItem = objectsListColor.get(i);
				if (!tempItem.getVisible())
					continue;
				
				GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,	tempItem.getVerticesBuffer());
				GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, tempItem.getIndicesBuffer());
		        
				Main.updateMVP3DMatrix();
				Main.assignHandlesToCurrentlyBoundBufferC3D();
				
				GLES20.glDrawElements(GLES20.GL_TRIANGLES, tempItem.getIndicesCount(), GLES20.GL_UNSIGNED_SHORT, 0);
			}
		}
		//===================================================================================================
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);		
	}
	
	protected abstract void loadModels();
	protected abstract void addInitialObjects(List<Item3D> initialObjectsToBeAdded);
	
	
}
