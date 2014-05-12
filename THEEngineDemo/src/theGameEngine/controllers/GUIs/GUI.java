package theGameEngine.controllers.GUIs;

import java.util.ArrayList;
import java.util.List;

import theGameEngine.controllers.Controller2D;
import theGameEngine.controllers.Scenes.Scenes2D.Scene2D;
import theGameEngine.controllers.Scenes.Scenes3D.Scene3D;
import theGameEngine.items.items2D.inventories.Inventory;
import theGameEngine.items.items2D.spritesAndButtons.Sprite;
import theGameEngine.items.items2D.textboxes.Textbox;
import theGameEngine.main.Main;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.opengl.GLES20;

public abstract class GUI extends Controller2D{
	
	//Lists to store all different GUIItems
		//Note: InventoryItems do not have a list because they are stored in their respective inventories
	//***************************************************************************************************
	protected List<Textbox> textboxes = new ArrayList<Textbox>();
	protected List<Sprite> sprites = new ArrayList<Sprite>();
	protected List<Inventory> inventories = new ArrayList<Inventory>();
	//***************************************************************************************************
	
	//The partnering scene
	protected Scene3D scene3D;
	protected Scene2D scene2D;
	
	//The all powerful constructor for a GUI. That makes up like 32.37% of the game. At least
	public GUI(AssetManager assets)
	{
		super(assets);
	}

	public void addTextbox(Textbox textbox){
		textboxes.add(textbox);
	}
	public void addSprite(Sprite sprite){
		sprites.add(sprite);
		addToBatchObjects(sprite);
	}
	public void addInventory(Inventory inventory){
		inventories.add(inventory);
		addToBatchObjects(inventory);
	}
	
	//Assign worldInfo for manipulating scenes
	public void setScene3D(Scene3D scene3D)
	{
		this.scene3D = scene3D;
	}
	public void setScene2D(Scene2D scene2D)
	{
		this.scene2D = scene2D;
	}
	public List<Textbox> getTextboxes()
	{
		return textboxes;
	}
	public List<Sprite> getSprites()
	{
		return sprites;
	}
	public List<Inventory> getInventories()
	{
		return inventories;
	}
	public Textbox getTextbox(int index)
	{
		return textboxes.get(index);
	}
	public Sprite getSprite(int index)
	{
		return sprites.get(index);
	}
	public Inventory getInventory(int index)
	{
		return inventories.get(index);
	}
	
	//Updates all GUIItem models for when they are drawn
	public void updateModels(float deltaTime)
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
		
		for (int i=0; i<textboxes.size(); i++){
			textboxes.get(i).update(deltaTime);
			textboxes.get(i).updateCharacters(deltaTime);
		}
		for (int i=0; i<batchObjectsListTexture.size(); i++)
			batchObjectsListTexture.get(i).update(deltaTime);
		for (int i=0; i<batchObjectsListColor.size(); i++)
			batchObjectsListColor.get(i).update(deltaTime);
		
		
		for (int i=0; i<textboxes.size(); i++){
			if (textboxes.get(i).getUpdateAbstractData())
				textboxes.get(i).updateAbstractData();
			if (textboxes.get(i).getUpdateCharactersAbstractData())
				textboxes.get(i).updateCharactersAbstractData();
		}
		for (int i=0; i<batchObjectsListTexture.size(); i++){
			if (batchObjectsListTexture.get(i).getUpdateAbstractData())
				batchObjectsListTexture.get(i).updateAbstractData();
		}
		for (int i=0; i<batchObjectsListColor.size(); i++){
			if (batchObjectsListColor.get(i).getUpdateAbstractData())
				batchObjectsListColor.get(i).updateAbstractData();
		}
		
		if (createNewVerticesByteBufferTexture)
			createNewVerticesByteBufferTexture();
		if (createNewIndicesByteBufferTexture)
			createNewIndicesByteBufferTexture();
		if (createNewVerticesByteBufferColor)
			createNewVerticesByteBufferColor();
		if (createNewIndicesByteBufferColor)
			createNewIndicesByteBufferColor();
	}
	
	
	@SuppressLint("NewApi")
	public void renderModels(){
		for (int i=0; i<textboxes.size(); i++){
			if (textboxes.get(i).getCreateNewVerticesBufferCharacters())
				textboxes.get(i).createNewVerticesBufferCharacters();
			if (textboxes.get(i).getCreateNewIndicesBufferCharacters())
				textboxes.get(i).createNewIndicesBufferCharacters();
		}

		if (bindVerticesBufferDataTexture)
			bindVerticesBufferDataTexture();
		if (bindIndicesBufferDataTexture)
			bindIndicesBufferDataTexture();
		if (bindVerticesBufferDataColor)
			bindVerticesBufferDataColor();
		if (bindIndicesBufferDataColor)
			bindIndicesBufferDataColor();
		
				
		for (int i=0; i<textboxes.size(); i++){
			if (textboxes.get(i).getUpdateOpenGLData()){
				GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,textboxes.get(i).getTextboxVerticesBuffer());
				textboxes.get(i).updateOpenGLData();
			}
			if (textboxes.get(i).getUpdateCharactersOpenGLData()){
				GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,textboxes.get(i).getTextVerticesBuffer());
				textboxes.get(i).updateCharactersOpenGLData();
			}
		}
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,batchVerticesBuffers[0]);
		for (int i=0; i<batchObjectsListTexture.size(); i++){
			if (batchObjectsListTexture.get(i).getUpdateOpenGLData())
				batchObjectsListTexture.get(i).updateOpenGLData();
		}
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,batchVerticesBuffers[1]);
		for (int i=0; i<batchObjectsListColor.size(); i++){
			if (batchObjectsListColor.get(i).getUpdateOpenGLData())
				batchObjectsListColor.get(i).updateOpenGLData();
		}
		
		//=================================================================================================================================
		// Color rendering
		
		Main.useProgramC();
		
		if (batchObjectsListColor.size() > 0){
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, batchIndicesBuffers[1]);
	        
			Main.assignHandlesToCurrentlyBoundBufferC();
			
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, indicesCountColor, GLES20.GL_UNSIGNED_SHORT, 0);
		}
		
		////===============================================================================
		//// Render textboxes
		
		if (textboxes.size()>0){
			//Turn on drawing to the stencil
			GLES20.glEnable(GLES20.GL_STENCIL_TEST);
			GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_REPLACE);
			GLES20.glStencilMask(0xFF);
			GLES20.glClear(GLES20.GL_STENCIL_BUFFER_BIT);
			
			for (int i=0; i<textboxes.size(); i++){
				GLES20.glStencilFunc(GLES20.GL_ALWAYS, i+1, 0xFF);
				textboxes.get(i).drawTextbox();
			}
		}
		////===============================================================================
		
		//=================================================================================================================================
		
		
		//=================================================================================================================================
		// Texture rendering
		
		Main.useProgramT();
		
		////=========================================================
		//// Render text

		if (textboxes.size()>0){
		    GLES20.glStencilMask(0);
			
			for (int i=0; i<textboxes.size(); i++){	
				GLES20.glStencilFunc(GLES20.GL_EQUAL, i+1, 0xFF);
				textboxes.get(i).drawCharacters();
			}
			
			GLES20.glDisable(GLES20.GL_STENCIL_TEST);
		}
		////=========================================================
		
		if (batchObjectsListTexture.size() > 0){
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,	batchVerticesBuffers[0]);
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, batchIndicesBuffers[0]);
	        
			Main.assignHandlesToCurrentlyBoundBufferT();
			
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, indicesCountTexture, GLES20.GL_UNSIGNED_SHORT, 0);
		}
		//=================================================================================================================================
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);		
	}

	
}
