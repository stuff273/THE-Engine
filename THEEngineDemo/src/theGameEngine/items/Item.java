package theGameEngine.items;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {
	
	protected int modelTypeListIndex = -1;
	protected int itemsAllListIndex = -1;

	//Abstract data that will be used for calulations and updating actual rendering information
	//*****************************************************************************************
	private float abstractTranslationValueXStart = 0;
	private float abstractTranslationValueYStart = 0;
	private float abstractTranslationValueZStart = 0;
	private float abstractTranslationValueX = 0;
	private float abstractTranslationValueY = 0;
	private float abstractTranslationValueZ = 0;
	
	private float abstractScaleValueXStart = 1;
	private float abstractScaleValueYStart = 1;
	private float abstractScaleValueZStart = 1;
	private float abstractScaleValueX = 1;
	private float abstractScaleValueY = 1;
	private float abstractScaleValueZ = 1;
		
	private float abstractRotationValue = 0;
	private float abstractRotationValueX = 1;
	private float abstractRotationValueY = 1;
	private float abstractRotationValueZ = 1;
	//*****************************************************************************************
	 
	//Time values to determine specific animations
	//********************************************
	protected float passedTime = 0;
	//********************************************
	
	//Updater that records what necessary updates need to take place
	//private Updater updater;
	
	//Pointers to other GUIItems in case one has to call another
	protected List<Item> connectedItems = new ArrayList<Item>();
		
	protected boolean updateAbstractData = false;
	protected boolean updateOpenGLData = false;
	protected boolean visible = true;
	protected boolean animating = false;
	protected boolean touchable = true;

	//Custom method that will run when the Item is first touched
	public abstract void onTouch(float touchPixelX, float touchPixelY, float startTime);
	//Custom method that will run while the Item is touched
	public abstract void whileTouched(float touchPixelX, float touchPixelY, float deltaTime);
	//Custom method that will run when the Item is first released
	public abstract void onRelease(float touchPixelX, float touchPixelY, float endTime);	
	public abstract void update(float deltaTime);
	
	public void setTranslationValueXStart(float translationValueX)
	{
		abstractTranslationValueX = abstractTranslationValueXStart = translationValueX;
		updateAbstractData = true;
	}
	public void setTranslationValueYStart(float translationValueY)
	{
		abstractTranslationValueY = abstractTranslationValueYStart = translationValueY;
		updateAbstractData = true;
	}
	public void setTranslationValueZStart(float translationValueZ)
	{
		abstractTranslationValueZ = abstractTranslationValueZStart = translationValueZ;
		updateAbstractData = true;
	}
	public void setTranslationValuesStart(float translationValueX, float translationValueY, float translationValueZ)
	{
		abstractTranslationValueX = abstractTranslationValueXStart = translationValueX;
		abstractTranslationValueY = abstractTranslationValueYStart = translationValueY;
		abstractTranslationValueZ = abstractTranslationValueZStart = translationValueZ;
		updateAbstractData = true;
	}
	public void setTranslationValueX(float translationValueX)
	{
		abstractTranslationValueX = translationValueX;
		updateAbstractData = true;
	}
	public void setTranslationValueY(float translationValueY)
	{
		abstractTranslationValueY = translationValueY;
		updateAbstractData = true;
	}
	public void setTranslationValueZ(float translationValueZ)
	{
		abstractTranslationValueZ = translationValueZ;
		updateAbstractData = true;
	}
	public void setTranslationValues(float translationValueX, float translationValueY, float translationValueZ)
	{
		setTranslationValueX(translationValueX);
		setTranslationValueY(translationValueY);
		setTranslationValueZ(translationValueZ);
		updateAbstractData = true;
	}
	public void resetTranslationValues()
	{
		setTranslationValueX(abstractTranslationValueXStart);
		setTranslationValueY(abstractTranslationValueYStart);
		setTranslationValueZ(abstractTranslationValueZStart);
		updateAbstractData = true;
	}
	
	public void setScaleValueXStart(float scaleValueX)
	{
		abstractScaleValueX = abstractScaleValueXStart = scaleValueX;
		updateAbstractData = true;
	}
	public void setScaleValueYStart(float scaleValueY)
	{
		abstractScaleValueY = abstractScaleValueYStart = scaleValueY;
		updateAbstractData = true;
	}
	public void setScaleValueZStart(float scaleValueZ)
	{
		abstractScaleValueZ = abstractScaleValueZStart = scaleValueZ;
		updateAbstractData = true;
	}
	public void setScaleValuesStart(float scaleValueX, float scaleValueY, float scaleValueZ)
	{
		abstractScaleValueX = abstractScaleValueXStart = scaleValueX;
		abstractScaleValueY = abstractScaleValueYStart = scaleValueY;
		abstractScaleValueZ = abstractScaleValueZStart = scaleValueZ;
		updateAbstractData = true;
	}
	public void setScaleValueX(float scaleValueX)
	{
		abstractScaleValueX = scaleValueX;
		updateAbstractData = true;
	}
	public void setScaleValueY(float scaleValueY)
	{
		abstractScaleValueY = scaleValueY;
		updateAbstractData = true;
	}
	public void setScaleValueZ(float scaleValueZ)
	{
		abstractScaleValueZ = scaleValueZ;
		updateAbstractData = true;
	}
	public void setScaleValues(float scaleValueX, float scaleValueY, float scaleValueZ)
	{
		setScaleValueX(scaleValueX);
		setScaleValueY(scaleValueY);
		setScaleValueZ(scaleValueZ);
		updateAbstractData = true;
	}
	public void resetScaleValues()
	{
		setScaleValueX(abstractScaleValueXStart);
		setScaleValueY(abstractScaleValueYStart);
		setScaleValueZ(abstractScaleValueZStart);
		updateAbstractData = true;
	}
	
	public void setRotationValue(float rotationValue)
	{
		abstractRotationValue = rotationValue;
		updateAbstractData = true;
	}
	public void setRotationValueX(float rotationValueX)
	{
		abstractRotationValueX = rotationValueX;
		updateAbstractData = true;
	}
	public void setRotationValueY(float rotationValueY)
	{
		abstractRotationValueY = rotationValueY;
		updateAbstractData = true;
	}
	public void setRotationValueZ(float rotationValueZ)
	{
		abstractRotationValueZ = rotationValueZ;
		updateAbstractData = true;
	}
	public void resetRotationValues()
	{
		setRotationValue(0);
		setRotationValueX(1);
		setRotationValueY(1);
		setRotationValueZ(1);
		updateAbstractData = true;
	}

	public void setUpdateAbstractData(boolean updateAbstractData)
	{
		this.updateAbstractData = updateAbstractData;
	}
	public void setUpdateOpenGLData(boolean updateOpenGLData)
	{
		this.updateOpenGLData = updateOpenGLData;
	}
	public void setAnimating(boolean animating)
	{
		this.animating = animating;
	}
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	
	//TranslationValueXStart accessor
	public float getTranslationValueXStart(){
		return abstractTranslationValueXStart;
	}
	//TranslationValueYStart accessor
	public float getTranslationValueYStart(){
		return abstractTranslationValueYStart;
	}
	//TranslationValueZStart accessor
	public float getTranslationValueZStart(){
		return abstractTranslationValueZStart;
	}
	//TranslationValueX accessor
	public float getTranslationValueX(){
		return abstractTranslationValueX;
	}
	//TranslationValueY accessor
	public float getTranslationValueY(){
		return abstractTranslationValueY;
	}
	//TranslationValueZ accessor
	public float getTranslationValueZ(){
		return abstractTranslationValueZ;
	}
	
	//ScaleValueXStart accessor
	public float getScaleValueXStart(){
		return abstractScaleValueXStart;
	}
	//ScaleValueYStart accessor
	public float getScaleValueYStart(){
		return abstractScaleValueYStart;
	}
	//ScaleValueZStart accessor
	public float getScaleValueZStart(){
		return abstractScaleValueZStart;
	}
	//ScaleValueX accessor
	public float getScaleValueX(){
		return abstractScaleValueX;
	}
	//ScaleValueY accessor
	public float getScaleValueY(){
		return abstractScaleValueY;
	}
	//ScaleValueZ accessor
	public float getScaleValueZ(){
		return abstractScaleValueZ;
	}
	
	//RotationValue accessor
	public float getRotationValue(){
		return abstractRotationValue;
	}
	//RotationValueX accessor
	public float getRotationValueX(){
		return abstractRotationValueX;
	}
	//RotationValueY accessor
	public float getRotationValueY(){
		return abstractRotationValueY;
	}
	//RotationValueZ accessor
	public float getRotationValueZ(){
		return abstractRotationValueZ;
	}

	
	public List<Item> getConnectedItems()
	{
		return connectedItems;
	}
	public void addConnectedItem(Item item)
	{
		connectedItems.add(item);
	}
	public Item getConnectedItem(int index)
	{
		return connectedItems.get(index);
	}
	public boolean getUpdateAbstractData()
	{
		return updateAbstractData;
	}
	public boolean getUpdateOpenGLData()
	{
		return updateOpenGLData;
	}
	public boolean getVisible()
	{
		return visible;
	}
	public boolean getAnimating()
	{
		return animating;
	}
	public boolean getTouchable()
	{
		return touchable;
	}
	public void setTouchable(boolean touchable)
	{
		this.touchable = touchable;
	}
	
	public void setModelTypeListIndex(int mainObjectsListIndex)
	{
		this.modelTypeListIndex = mainObjectsListIndex;
	}
	public int getModelTypeListIndex()
	{
		return modelTypeListIndex;
	}
	public void setItemsAllListIndex(int itemsAllListIndex)
	{
		this.itemsAllListIndex = itemsAllListIndex;
	}
	public int getItemsAllListIndex()
	{
		return itemsAllListIndex;
	}

	
	//Generic method that should be called by all Items on touch
	protected void itemOnTouch(float startTime){
		//Passed time reset
		this.passedTime = 0;
	}
	
	//Generic method that should be called by all Items while touched
	protected void itemWhileTouched(float deltaTime){
		//Passed time is calculated and appended
		passedTime += deltaTime;
	}
	
	//Generic method that should be called by all Items on release
	protected void itemOnRelease(float endTime){

	}
	
	//Resets the Item's animations
	public void resetAnimation(){
		//animating = false;
		//animation = null;
	}
	
	//Completely resets the Item's animations and abstract data
	public void resetComplete(){
		resetAnimation();
		resetTranslationValues();
		resetScaleValues();
		resetRotationValues();
	}
	
	public void synchronize(){
		//updater.performUpdates(this);
	}
	

	public void updateTranslationValueX(){
		//renderTranslationValueX = abstractTranslationValueX;
	}
	public void updateTranslationValueY(){
		//renderTranslationValueY = abstractTranslationValueY;
	}
	public void updateTranslationValueZ(){
		//renderTranslationValueZ = abstractTranslationValueZ;
	}
	
	public void updateScaleValueX(){
		//renderScaleValueX = abstractScaleValueX;
	}
	public void updateScaleValueY(){
		//renderScaleValueY = abstractScaleValueY;
	}
	public void updateScaleValueZ(){
		//renderScaleValueZ = abstractScaleValueZ;
	}
	
	public void updateRotationValue(){
		//renderRotationValue = abstractRotationValue;
	}
	public void updateRotationValueX(){
		//renderRotationValueX = abstractRotationValueX;
	}
	public void updateRotationValueY(){
		//renderRotationValueY = abstractRotationValueY;
	}
	public void updateRotationValueZ(){
		//renderRotationValueZ = abstractRotationValueZ;
	}
	
}
