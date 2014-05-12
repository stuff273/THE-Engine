package theGameEngine.controllers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import theGameEngine.items.items2D.Item2D;
import theGameEngine.main.Main;
import theGameEngine.modelSets.modelSets2D.ModelSet2D;
import theGameEngine.models.Model;
import theGameEngine.textures.Textures;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.opengl.GLES20;

public abstract class Controller2D extends Controller{
	
	//====================================================================
	// Texture data of the entire Controller2D
	
	protected int textureValue = -1;
	protected float[] texturePixelDimensions;
	protected Bitmap bitmap;
	//====================================================================

	
	//Pointer to check Objects for touches
	protected Item2D checkTouchObject = null;
	//Pointer to designate the touched Object
	protected Item2D touchObject = null;
	
	
	protected int[] batchVerticesBuffers = new int[2];
	protected int[] batchIndicesBuffers = new int[2];
	
	private FloatBuffer verticesByteBufferTexture;
	private FloatBuffer verticesByteBufferColor;
	private ShortBuffer indicesByteBufferTexture;
	private ShortBuffer indicesByteBufferColor;
	
	protected boolean loadedInitialOpenGLData = false;
		
	protected boolean createNewVerticesByteBufferTexture = false;
	protected boolean bindVerticesBufferDataTexture = false;
	protected boolean createNewIndicesByteBufferTexture = false;
	protected boolean bindIndicesBufferDataTexture = false;
	
	protected boolean createNewVerticesByteBufferColor = false;
	protected boolean bindVerticesBufferDataColor = false;
	protected boolean createNewIndicesByteBufferColor = false;
	protected boolean bindIndicesBufferDataColor = false;
	
	protected int indicesCountTexture = 0;
	protected int indicesCountColor = 0;
	
	
	protected List<Item2D> batchObjectsListTexture = new ArrayList<Item2D>();
	protected List<Item2D> batchObjectsListColor = new ArrayList<Item2D>();
	
	protected List<Item2D> itemsAll = new ArrayList<Item2D>();
	
	protected ModelSet2D modelSet2D;
	
	
	public Controller2D(AssetManager assets)
	{ 		
		super(assets);
	}
	
	
	
	public void loadInitialData()
	{
		loadInitialNonOpenGLData();
		
		loadModels();
		
		List<Item2D> objectsToBeAdded = new ArrayList<Item2D>();
		addInitialObjects(objectsToBeAdded);
		buildInitialBuffers(objectsToBeAdded);
	}
	
	
	public void buildInitialBuffers(List<Item2D> objectsToBeAdded)
	{		
		for (int i=0; i<objectsToBeAdded.size(); i++)
			addToBatchObjects(objectsToBeAdded.get(i));
		
		objectsToBeAdded.clear();

		createNewVerticesByteBufferTexture();
		createNewIndicesByteBufferTexture();
		createNewVerticesByteBufferColor();
		createNewIndicesByteBufferColor();
	}	
	
	
	protected void createNewVerticesByteBufferTexture()
	{
		if (batchObjectsListTexture.size() < 1)
			return;
				
		verticesByteBufferTexture = createVerticesByteBuffer(batchObjectsListTexture);
				
		createNewVerticesByteBufferTexture = false;
		bindVerticesBufferDataTexture = true;
	}
	protected void createNewIndicesByteBufferTexture(){
		if (batchObjectsListTexture.size() < 1)
			return;
				
		indicesByteBufferTexture = createIndicesByteBuffer(batchObjectsListTexture);
		indicesCountTexture = indicesByteBufferTexture.capacity();		
		
		createNewIndicesByteBufferTexture = false;
		bindIndicesBufferDataTexture = true;
	}	
	protected void createNewVerticesByteBufferColor(){
		if (batchObjectsListColor.size() < 1)
			return;
		
		verticesByteBufferColor = createVerticesByteBuffer(batchObjectsListColor);
		
		createNewVerticesByteBufferColor = false;
		bindVerticesBufferDataColor = true;

	}
	protected void createNewIndicesByteBufferColor(){
		if (batchObjectsListColor.size() < 1)
			return;
		
		indicesByteBufferColor = createIndicesByteBuffer(batchObjectsListColor);
		indicesCountColor = indicesByteBufferColor.capacity();
		
		createNewIndicesByteBufferColor = false;
		bindIndicesBufferDataColor = true;
	}
	
	protected void bindVerticesBufferDataTexture()
	{
		if (batchObjectsListTexture.size() < 1)
			return;
				
		bindVerticesBufferData(batchVerticesBuffers[0], verticesByteBufferTexture);
				
		bindVerticesBufferDataTexture = false;
	}
	protected void bindIndicesBufferDataTexture(){
		if (batchObjectsListTexture.size() < 1 || indicesCountTexture < 1)
			return;
				
		bindIndicesBufferData(batchIndicesBuffers[0], indicesByteBufferTexture);
				
		bindIndicesBufferDataTexture = false;
	}	
	protected void bindVerticesBufferDataColor()
	{
		if (batchObjectsListColor.size() < 1)
			return;
				
		bindVerticesBufferData(batchVerticesBuffers[1], verticesByteBufferColor);
				
		bindVerticesBufferDataColor = false;
	}
	protected void bindIndicesBufferDataColor(){
		if (batchObjectsListColor.size() < 1 || indicesCountColor < 1)
			return;
				
		bindIndicesBufferData(batchIndicesBuffers[1], indicesByteBufferColor);
				
		bindIndicesBufferDataColor = false;
	}	
	
	public static FloatBuffer createVerticesByteBuffer(List<Item2D> itemsToBeAdded)
	{
		if (itemsToBeAdded.size() == 0)
			return null;
		
		int sizeOfTotalAttributesFloatArray = 0;
		for (int i=0; i<itemsToBeAdded.size(); i++)
			sizeOfTotalAttributesFloatArray += itemsToBeAdded.get(i).getAttributes().length;
		float[] totalAttributesFloatArray = new float[sizeOfTotalAttributesFloatArray];
		
		float[] currentItemAttributes;
		int currentAttributesIndex = 0;
		for (int i=0; i<itemsToBeAdded.size(); i++)
		{
			currentItemAttributes = itemsToBeAdded.get(i).getAttributes();
			for (int j=0; j<currentItemAttributes.length; j++)
				totalAttributesFloatArray[currentAttributesIndex++] = currentItemAttributes[j];
		}
		
		FloatBuffer verticesByteBuffer = ByteBuffer.allocateDirect(totalAttributesFloatArray.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		verticesByteBuffer.put(totalAttributesFloatArray).position(0);
		
		return verticesByteBuffer;
	}
	
	public static void bindVerticesBufferData(int verticesBuffer, FloatBuffer verticesByteBuffer)
	{
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,verticesBuffer);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,verticesByteBuffer.capacity()*4,verticesByteBuffer,GLES20.GL_DYNAMIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
	}
	
	public static ShortBuffer createIndicesByteBuffer(List<Item2D> items)
	{
		if (items.size() == 0)
			return null;
		
		int visibleCount = 0;
		for (int i=0; i<items.size(); i++){
			if (items.get(i).getVisible())
				visibleCount++;
		}

		short[] indicesQuadArray = new short[visibleCount*6];
		
		Item2D tempItem;		
		int indicesArrayIndex = 0;
		for (int i=0; i<items.size(); i++){
			tempItem = items.get(i);
			if (!tempItem.getVisible())
				continue;
			
			indicesQuadArray[indicesArrayIndex++] = (short) (i*4);
			indicesQuadArray[indicesArrayIndex++] = (short) (i*4 + 1);
			indicesQuadArray[indicesArrayIndex++] = (short) (i*4 + 2);
			indicesQuadArray[indicesArrayIndex++] = (short) (i*4);
			indicesQuadArray[indicesArrayIndex++] = (short) (i*4 + 2);
			indicesQuadArray[indicesArrayIndex++] = (short) (i*4 + 3);	
		}
		
		ShortBuffer indicesByteBuffer = ByteBuffer.allocateDirect(indicesQuadArray.length * Short.SIZE / 8).order(ByteOrder.nativeOrder()).asShortBuffer();
		indicesByteBuffer.put(indicesQuadArray).position(0);
		
		return indicesByteBuffer;
	}
	
	public static void bindIndicesBufferData(int indicesBuffer, ShortBuffer indicesShortBuffer)
	{
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,indicesShortBuffer.capacity() * Short.SIZE / 8, indicesShortBuffer,GLES20.GL_DYNAMIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	
	
	
	public void addToBatchObjects(Item2D item)
	{
		item.calculateFinalPoints();
		
		switch(item.getModel().getType()){
		case(Model.TYPE_T):
			item.setModelTypeListIndex(batchObjectsListTexture.size());
			batchObjectsListTexture.add(item);		
			
			createNewVerticesByteBufferTexture = true;
			createNewIndicesByteBufferTexture = true;
			break;
		case(Model.TYPE_C):
			item.setModelTypeListIndex(batchObjectsListColor.size());
			batchObjectsListColor.add(item);
			
			createNewVerticesByteBufferColor = true;
			createNewIndicesByteBufferColor = true;
			break;
		}
		
		addToItemsAllList(item);
	}
	public void removeFromBatchObjects(Item2D item)
	{
		switch(item.getModel().getType()){
		case(Model.TYPE_T):
			batchObjectsListTexture.remove(item.getModelTypeListIndex());
		
			for (int i=0; i<batchObjectsListTexture.size(); i++)
				batchObjectsListTexture.get(i).setModelTypeListIndex(i);
			
			createNewVerticesByteBufferTexture = true;
			createNewIndicesByteBufferTexture = true;
			break;
			
		case(Model.TYPE_C):
			batchObjectsListColor.remove(item.getModelTypeListIndex());
			
			for (int i=0; i<batchObjectsListColor.size(); i++)
				batchObjectsListColor.get(i).setModelTypeListIndex(i);
			
			createNewVerticesByteBufferColor = true;
			createNewIndicesByteBufferColor = true;
			break;
		}
		
		item.setModelTypeListIndex(-1);
		
		removeFromItemsAllList(item);
	}
	
	private void addToItemsAllList(Item2D item)
	{
		item.setItemsAllListIndex(itemsAll.size());
		itemsAll.add(item);
	}
	private void removeFromItemsAllList(Item2D item)
	{
		itemsAll.remove(item.getItemsAllListIndex());
		for (int i=0; i<itemsAll.size(); i++)
			itemsAll.get(i).setItemsAllListIndex(i);
	}
	
	protected void checkToCreateNewBuffers()
	{
		if (createNewVerticesByteBufferTexture)
			createNewVerticesByteBufferTexture();
		if (createNewIndicesByteBufferTexture)
			createNewIndicesByteBufferTexture();
		if (createNewVerticesByteBufferColor)
			createNewVerticesByteBufferColor();
		if (createNewIndicesByteBufferColor)
			createNewIndicesByteBufferColor();
	}
	
	protected void checkToBindNewBuffers()
	{
		if (bindVerticesBufferDataTexture)
			bindVerticesBufferDataTexture();
		if (bindIndicesBufferDataTexture)
			bindIndicesBufferDataTexture();
		if (bindVerticesBufferDataColor)
			bindVerticesBufferDataColor();
		if (bindIndicesBufferDataColor)
			bindIndicesBufferDataColor();
	}

	protected void loadBitmapAndSetTextureValue(String imageFileName, int textureValue)
	{
		bitmap = Textures.generateBitmap(imageFileName, assets);
		texturePixelDimensions = Textures.getBitmapPixelDimensions(bitmap);
		this.textureValue = textureValue;
	}
	
	protected void generateTextureAndBuffers()
	{
		if (bitmap != null)
			Textures.generateAndBindTexture(bitmap, textureValue, GLES20.GL_NEAREST, GLES20.GL_NEAREST);
		
		GLES20.glGenBuffers(2, batchVerticesBuffers, 0);
		GLES20.glGenBuffers(2, batchIndicesBuffers, 0);
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
	
		for (int i=0; i<itemsAll.size(); i++){
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
		}
		
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
		
		checkToBindNewBuffers();
		
		if (batchObjectsListTexture.size() > 0)
		{
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,batchVerticesBuffers[0]);
			for (int i=0; i<batchObjectsListTexture.size(); i++)
			{
				if (batchObjectsListTexture.get(i).getUpdateOpenGLData())
					batchObjectsListTexture.get(i).updateOpenGLData();
			}
		}
		if (batchObjectsListColor.size() > 0)
		{
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,batchVerticesBuffers[1]);
			for (int i=0; i<batchObjectsListColor.size(); i++)
			{
				if (batchObjectsListColor.get(i).getUpdateOpenGLData())
					batchObjectsListColor.get(i).updateOpenGLData();
			}
		}
		
		//===================================================================================================
		// Texture rendering
		
		if (batchObjectsListTexture.size() > 0 && indicesCountTexture > 0)
		{
			Main.useProgramT();
			Main.switchToTexture(textureValue);
			
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,	batchVerticesBuffers[0]);
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, batchIndicesBuffers[0]);
	        
			Main.assignHandlesToCurrentlyBoundBufferT();
			
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, indicesCountTexture, GLES20.GL_UNSIGNED_SHORT, 0);
		}
		//===================================================================================================
		
		
		//===================================================================================================
		// Color rendering
				
		if (batchObjectsListColor.size() > 0 && indicesCountColor > 0)
		{
			Main.useProgramC();

			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,batchVerticesBuffers[1]);
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, batchIndicesBuffers[1]);
	        
			Main.assignHandlesToCurrentlyBoundBufferC();
			
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, indicesCountColor, GLES20.GL_UNSIGNED_SHORT, 0);
		}
		//===================================================================================================
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);		
	}
	
	public void updateAbstractData()
	{
		// Abstract data update (translation, scale, rotation)
		for (int i=0; i<itemsAll.size(); i++)
		{
			if (itemsAll.get(i).getUpdateAbstractData())
				itemsAll.get(i).updateAbstractData();
		}
		
		checkToCreateNewBuffers();
	}
	
	
	public void setCreateNewIndicesBufferTexture(boolean setValue)
	{
		createNewIndicesByteBufferTexture = setValue;
	}
	public void setCreateNewIndicesBufferColor(boolean setValue)
	{
		createNewIndicesByteBufferColor = setValue;
	}

	public int getTextureValue()
	{
		return textureValue;
	}
	public float getTextureImageWidth()
	{
		return texturePixelDimensions[0];
	}
	public float getTextureImageHeight()
	{
		return texturePixelDimensions[1];
	}
	
	protected abstract void loadModels();
	protected abstract void addInitialObjects(List<Item2D> initialObjectsToBeAdded);
}
