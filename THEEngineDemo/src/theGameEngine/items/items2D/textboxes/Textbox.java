package theGameEngine.items.items2D.textboxes;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.opengl.GLES20;
import theGameEngine.controllers.Controller2D;
import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.items.items2D.Item2D;
import theGameEngine.items.items2D.spritesAndButtons.Sprite;
import theGameEngine.items.items2D.spritesAndButtons.SpriteNoTouch;
import theGameEngine.main.Main;
import theGameEngine.models.Model2D;
import theGameEngine.text.Text;

public abstract class Textbox extends Item2D{

	private int[] textboxVerticesBuffers = new int[2];
	private int[] textboxIndicesBuffers = new int[2];
	protected FloatBuffer verticesByteBufferTextbox;
	protected FloatBuffer verticesByteBufferCharacters;
	protected ShortBuffer indicesByteBufferTextbox;
	protected ShortBuffer indicesByteBufferCharacters;
	
	protected boolean updateAbstractDataCharacters = false;
	protected boolean updateOpenGLDataCharacters = false;
	protected boolean createNewVerticesBufferCharacters = false;
	protected boolean createNewIndicesBufferCharacters = false;
	protected boolean bindVerticesBufferDataCharacters = false;
	protected boolean bindIndicesBufferDataCharacters = false;
	
	int textIndicesCount = 0;
	
	//====================================================================
	// Cursor information tells where text will be printed to the textbox
	
	float screenCursorPositionX;
	float screenCursorPositionY;
	//====================================================================
	
	boolean shrinkAndGrow = false;
	int shrinkAndGrowDirection = 1;
	
	// char array of all text that will be added to the textbox 
	char[] textAll = null;
	
	// Measure of how long the current word is in normal coordinates
	float currentWordWidth = 0;
	
	//======================================
	// Resize information

	protected float resizePositionX = 0;
	protected float resizePositionY = 0;
	protected float resizeScale = 0;
	protected float resizeSpeedScale = 0;
	protected float resizeSpeedX = 0;
	protected float resizeSpeedY = 0;;
	
	protected boolean resizing = false;
	protected boolean resized = false;
	//======================================

	
	//====================================================================
	// Timing information for when text is added over time to the textbox

	float textOverTimeIncrement = 0;
	float textOverTimeIncrementOriginal = 0;
	float textOverTimeCurrentTime = 0;
	float textOverTimePassedTime = 0;
	//====================================================================
	
	int currentPage = 0;
	int maxPageGoneTo = 0;
	public List<Integer> pages = new ArrayList<Integer>();
	
	float fontSize = 1;
	
	//Determines if the textbox is ready to move on
	boolean readyToAdvance = false;
	//Determines whether textbox has finished printing job
	boolean endOfText = false;
	
	//Determines if the text being added is over time
	boolean addingTextOverTime = false;
	int addingTextOverTimeIndex = 0;
	
	//Information on where each letter, number, and punctuation mark is stored
	Text text;
	
	//List that stores only this textbox
	protected List<Item2D> textboxList = new ArrayList<Item2D>();
	//List that stores all the character Images to be rendered to the textbox
	protected List<Item2D> characters = Collections.synchronizedList(new ArrayList<Item2D>());
	
	private List<Sprite> characterPool = Collections.synchronizedList(new ArrayList<Sprite>());
	int characterPoolIndex = 0;
	boolean getFromCharacterPool = true;
	
	Sprite characterImageToBeAdded = null;
	
	
	public Textbox(float centerX, float centerY, Model2D model, GUI gui) 
	{
		
		super(model, gui);
		
		setTranslationValueXStart(Main.convertNormalToScreen(centerX));
		setTranslationValueYStart(centerY);
		setTranslationValueZStart(0);
		
		textboxList.add(this);		
		
		verticesByteBufferTextbox = Controller2D.createVerticesByteBuffer(textboxList);
		indicesByteBufferTextbox = Controller2D.createIndicesByteBuffer(textboxList);
		
		modelTypeListIndex = 0;
	}
	
	public void textboxOnTouch(float touchPixelX, float touchPixelY, float startTime)
	{
		//Generic onTouch method for all GUIItems
		itemOnTouch(startTime);
	}

	public void textboxWhileTouched(float touchPixelX, float touchPixelY, float deltaTime)
	{
		textOverTimeIncrement = textOverTimeIncrementOriginal/5f;
		
		//Generic whileTouched method for all GUIItems
		itemWhileTouched(deltaTime);
	}
	
	public void textboxOnRelease(float touchPixelX, float touchPixelY, float endTime)
	{
		textOverTimeIncrement = textOverTimeIncrementOriginal;
		textOverTimeCurrentTime = textOverTimePassedTime = 0;
		
		//Generic onRelease method for all GUIItems
		itemOnRelease(endTime);
	}
	
	public int getTextboxVerticesBuffer()
	{
		return textboxVerticesBuffers[0];
	}
	public int getTextVerticesBuffer()
	{
		return textboxVerticesBuffers[1];
	}
	
	public boolean getUpdateCharactersAbstractData()
	{
		return updateAbstractDataCharacters;
	}
	public boolean getUpdateCharactersOpenGLData()
	{
		return updateOpenGLDataCharacters;
	}
	
	public boolean getCreateNewVerticesBufferCharacters()
	{
		return createNewVerticesBufferCharacters;
	}
	public boolean getCreateNewIndicesBufferCharacters()
	{
		return createNewIndicesBufferCharacters;
	}
	public boolean bindVerticesBufferDataCharacters()
	{
		return bindVerticesBufferDataCharacters;
	}
	public boolean bindIndicesBufferDataCharacters()
	{
		return bindIndicesBufferDataCharacters;
	}
	public boolean getResized()
	{
		return resized;
	}
	public boolean getResizing()
	{
		return resizing;
	}
	
	public void setCharacterVisible(int index, boolean visible){
		characters.get(index).setVisible(visible);
		createNewIndicesBufferCharacters = true;
	}
	
	@Override
	public void resetComplete()
	{
		super.resetComplete();
		
		for (int i=0; i<characters.size(); i++)
			characters.get(i).resetComplete();
	}
	
	public void update(float deltaTime)
	{
		if (resizing)
			resize(deltaTime);
	}
	
	public void updateCharacters(float deltaTime){
		//Add text if there is text to be added over time
		if (addingTextOverTime)
			addTextOverTime(deltaTime);
	}
	
	public void updateCharactersAbstractData(){
		//Loop through all characters and see if they need updating, and update them accordingly
		for (int i=0; i<characters.size(); i++){
			if (characters.get(i).getUpdateAbstractData())
				characters.get(i).updateAbstractData();
		}
		
		updateAbstractDataCharacters = false;
		updateOpenGLDataCharacters = true;
	}
	
	public void updateCharactersOpenGLData(){
		for (int i=0; i<characters.size(); i++){
			if (characters.get(i).getUpdateOpenGLData())
				characters.get(i).updateOpenGLData();
		}		
		
		updateOpenGLDataCharacters = false;
	}
	
	
	public void createNewVerticesBufferTextbox(){
		verticesByteBufferCharacters = Controller2D.createVerticesByteBuffer(textboxList);
		
		createNewVerticesBufferCharacters = false;
		bindVerticesBufferDataCharacters = true;
	}
	
	public void createNewIndicesBufferTextbox(){
		if (characters.size() < 1){
			createNewIndicesBufferCharacters = false;
			return;
		}
		
		indicesByteBufferCharacters = Controller2D.createIndicesByteBuffer(characters);
		
		createNewIndicesBufferCharacters = false;
		bindIndicesBufferDataCharacters = true;
	}
	public void createNewVerticesBufferCharacters(){
		if (characters.size() < 1){
			createNewVerticesBufferCharacters = false;
			return;
		}
		
		verticesByteBufferCharacters = Controller2D.createVerticesByteBuffer(characters);
		
		createNewVerticesBufferCharacters = false;
		bindVerticesBufferDataCharacters = true;
	}
	public void createNewIndicesBufferCharacters(){
		if (characters.size() < 1){
			createNewIndicesBufferCharacters = false;
			return;
		}
		
		indicesByteBufferCharacters = Controller2D.createIndicesByteBuffer(characters);
		
		createNewIndicesBufferCharacters = false;
		bindIndicesBufferDataCharacters = true;
	}
	
	@SuppressLint("NewApi")
	public void drawTextbox(){
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,	textboxVerticesBuffers[0]);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, textboxIndicesBuffers[0]);
		
		Main.assignHandlesToCurrentlyBoundBufferC();
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, 0);
	}
	
	@SuppressLint("NewApi")
	public void drawCharacters(){
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,	textboxVerticesBuffers[1]);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, textboxIndicesBuffers[1]);
		
		Main.assignHandlesToCurrentlyBoundBufferT();
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, textIndicesCount, GLES20.GL_UNSIGNED_SHORT, 0);
	}
	
	public void addTextToBeAddedInstant(String textToBeAdded, int fontSize)
	{
		initiateText(textToBeAdded, fontSize);
		reformatText();
		
		setCharactersTranslationValuesStart();
		setCharactersScaleValuesStart();
		
		createNewVerticesBufferCharacters = true;
		createNewIndicesBufferCharacters = true;
	}
	
	public void addTextToBeAddedOverTime(String textToBeAdded, float delay, int fontSize)
	{
		addingTextOverTime = true;
		textOverTimeIncrement = textOverTimeIncrementOriginal = delay;
		textOverTimeCurrentTime = 0;
		textOverTimePassedTime = 0;
		addingTextOverTimeIndex = 0;
		
		initiateText(textToBeAdded, fontSize);
		reformatText();

		setCharactersTranslationValuesStart();
		setCharactersScaleValuesStart();
		
		for (int i=0; i<characters.size(); i++)
			setCharacterVisible(i, false);
		
		createNewVerticesBufferCharacters = true;
	}
	
	public void initiateText(String textToBeAdded, int fontSize){
		setFontSize(fontSize);

		characters.clear();
		
		pages.clear();
		pages.add(0);
		currentPage = 0;
		maxPageGoneTo = 0;
		
		characterPoolIndex = 0;
		getFromCharacterPool = true;

		readyToAdvance = false;
		
		//Record textAll
		textAll = textToBeAdded.toCharArray();
		
		//=========================================================================================
		//Gather all characters (excluding spaces) into characters List
		for (int i=0; i<textAll.length; i++){
			if (textAll[i] == ' ')
				continue;
			//=====================================================================================
			//Use Image from characterPool or create a new one and add it to the pool
			if ((characterImageToBeAdded = getFromCharacterPool(textAll[i])) == null){
				characterImageToBeAdded = new SpriteNoTouch(0,0,text.getCharacterModel(textAll[i]),controller2D);
				characterPool.add(characterImageToBeAdded);
			}
			//=====================================================================================
			characterImageToBeAdded.setModelTypeListIndex(characters.size());
			characters.add(characterImageToBeAdded);
		}
		//=========================================================================================
	}
	
	private Sprite getFromCharacterPool(char character){
		if (!getFromCharacterPool)
			return null;
		
		if (characterPoolIndex == characterPool.size()){
			getFromCharacterPool = false;
			return null;
		}
		else{
			Sprite characterFromPool = characterPool.get(characterPoolIndex);
			characterFromPool.setModel(text.getCharacterModel(character));
			characterPoolIndex++;
			return characterFromPool;
		}
	}
	
	public void setFontSize(int size){
		this.fontSize = ((float)size)/10f*getScaleValueX();
		text.changeFontSize(fontSize);
	}
	
	private void setCharactersTranslationValuesStart(){
		Item2D tempCharacter;
		for (int i=0; i<characters.size(); i++){
			tempCharacter = characters.get(i);
			tempCharacter.setTranslationValueXStart(tempCharacter.getTranslationValueX());
			tempCharacter.setTranslationValueYStart(tempCharacter.getTranslationValueY());
			tempCharacter.setTranslationValueZStart(-1f);
		}
		tempCharacter = null;
	}
	
	private void setCharactersScaleValuesStart(){
		Item2D tempCharacter;
		for (int i=0; i<characters.size(); i++){
			tempCharacter = characters.get(i);
			tempCharacter.setScaleValueXStart(tempCharacter.getScaleValueX());
			tempCharacter.setScaleValueYStart(tempCharacter.getScaleValueY());
		}
		tempCharacter = null;
	}
	
	public void resetCharacters(){
		for (int i=0; i<characters.size(); i++){
			characters.get(i).resetComplete();
		}
		updateAbstractDataCharacters = true;
	}
	
	public void shrinkAndGrow(float deltaTime)
	{
		deltaTime /= 4f;
		
		if (getScaleValueX()+(deltaTime*shrinkAndGrowDirection) <= 0 ||
				getScaleValueX()+(deltaTime*shrinkAndGrowDirection) >= getScaleValueXStart() ||
				getScaleValueY()+(deltaTime*shrinkAndGrowDirection) <= 0 ||
				getScaleValueY()+(deltaTime*shrinkAndGrowDirection) >= getScaleValueYStart())
			shrinkAndGrowDirection *= -1;
		
		setScaleValueX(getScaleValueX()+(deltaTime*shrinkAndGrowDirection));
		setScaleValueY(getScaleValueY()+(deltaTime*shrinkAndGrowDirection));
		
		reformatText();
	}
	
	public void setResizing(float finalPositionX, float finalPositionY, float finalScale, float speed)
	{
		resizePositionX = Main.convertNormalToScreen(finalPositionX);
		resizePositionY = finalPositionY;
		resizeScale = finalScale;
		
		resizeSpeedScale = (finalScale - getScaleValueX()) * speed/5f;
		resizeSpeedX = (resizePositionX - getTranslationValueX()) * speed/5f;
		resizeSpeedY = (resizePositionY - getTranslationValueY()) * speed/5f;
		
		resizing = true;
		touchable = false;
	}
	public void setRestore(){
		resizePositionX = getTranslationValueXStart();
		resizePositionY = getTranslationValueYStart();
		resizeScale = getScaleValueXStart();
		
		resizeSpeedScale *= -1;
		resizeSpeedX *= -1;
		resizeSpeedY *= -1;
		
		resizing = true;
		touchable = false;
	}
	
	protected void resize(float deltaTime)
	{
		if (resizeSpeedX < 0)
		{
			if (getTranslationValueX() + resizeSpeedX*deltaTime < resizePositionX)
				setTranslationValueX(resizePositionX);
			else setTranslationValueX(getTranslationValueX() + resizeSpeedX*deltaTime);
		}
		else
		{
			if (getTranslationValueX() + resizeSpeedX*deltaTime > resizePositionX)
				setTranslationValueX(resizePositionX);
			else setTranslationValueX(getTranslationValueX() + resizeSpeedX*deltaTime);
		}
		
		if (resizeSpeedY < 0)
		{
			if (getTranslationValueY() + resizeSpeedY*deltaTime < resizePositionY)
				setTranslationValueY(resizePositionY);
			else setTranslationValueY(getTranslationValueY() + resizeSpeedY*deltaTime);
		}
		else
		{
			if (getTranslationValueY() + resizeSpeedY*deltaTime > resizePositionY)
				setTranslationValueY(resizePositionY);
			else setTranslationValueY(getTranslationValueY() + resizeSpeedY*deltaTime);
		}
		
		if (resizeSpeedScale < 0)
		{
			if (getScaleValueX() + resizeSpeedScale*deltaTime < resizeScale)
			{
				setScaleValueX(resizeScale);
				setScaleValueY(resizeScale);
			}
			else
			{
				setScaleValueX(getScaleValueX() + resizeSpeedScale*deltaTime);
				setScaleValueY(getScaleValueY() + resizeSpeedScale*deltaTime);
			}
		}
		else
		{
			if (getScaleValueX() + resizeSpeedScale*deltaTime > resizeScale)
			{
				setScaleValueX(resizeScale);
				setScaleValueY(resizeScale);
			}
			else
			{
				setScaleValueX(getScaleValueX() + resizeSpeedScale*deltaTime);
				setScaleValueY(getScaleValueY() + resizeSpeedScale*deltaTime);
			}
		}

		
		if (getTranslationValueX() == resizePositionX && getTranslationValueY() == resizePositionY && getScaleValueX() == resizeScale)
		{
			resizing = false;
			resized = !resized;
			touchable = true;
		}
		
		reformatText();
	}
	
	
	protected void reformatCharacter(Item2D characterImage, char character){
		characterImage.setScaleValueX(fontSize*getScaleValueX());
		characterImage.setScaleValueY(fontSize*getScaleValueY());
		
		float width = characterImage.getModel().getWidth() * getScaleValueX() * fontSize;
		float height = characterImage.getModel().getHeight() * getScaleValueY() * fontSize;
		
		//Move the cursor to the left by half the letter's width and a little more
		screenCursorPositionX += width/2  + text.getLargestLetterWidth()*getScaleValueX()/8;
		
		//=============================================================================================================
		//Set translation values of character based on its special properties 
		
		//These characters need to hang a little lower
		if (character == 'g' || character == 'j' || character == 'p' || character == 'q' || character == 'y')
		{
			characterImage.setTranslationValueX(screenCursorPositionX);
			characterImage.setTranslationValueY(screenCursorPositionY + height/4);
		}
		
		//These characters need to move to the center of the line
		else if (character == '+' || character == '-' || character == '*' || character == '÷' ||
				character == '=' || character == ':' || character == '{' || character == '}' ||
				character == '<' || character == '>' || character == '[' || character == ']' || character == '~')
		{
			characterImage.setTranslationValueX(screenCursorPositionX);
			characterImage.setTranslationValueY(screenCursorPositionY + height);
		}
		
		//These characters are normal
		else
		{
			characterImage.setTranslationValueX(screenCursorPositionX);
			characterImage.setTranslationValueY(screenCursorPositionY + height/2);
		}		
		//=============================================================================================================
		
		//Move the cursor over by half the letter's width
		screenCursorPositionX += width/2;
	}
	
	//Align the cursor to the left side of the textbox
	public void alignCursorLeft(){
		screenCursorPositionX = getTranslationValueX() - model.getWidth()/2.0f*getScaleValueX() + text.getLargestLetterWidth()*getScaleValueX();
	}
	
	//Align the cursor to the top of the textbox
	public void alignCursorTop(){
		screenCursorPositionY = getTranslationValueY() + model.getHeight()*getScaleValueY()/2.0f - text.getLargestLetterHeight()*getScaleValueY()*2.0f;
	}
	
	//Align the cursor to the bottom 
	public void alignCursorBottom(){
		screenCursorPositionY = getTranslationValueY() - model.getHeight()*getScaleValueY()/2.0f + text.getLargestLetterHeight()*getScaleValueY()*2.0f;
	}
	
	//Align the cursor to the next line
	public void alignCursorNextLine(){
		screenCursorPositionY -= text.getLargestLetterHeight()*getScaleValueY()*1.5f;
	}
	
	//Align the cursor to the next line
	public void alignCursorPreviousLine(){
		screenCursorPositionY += text.getLargestLetterHeight()*getScaleValueY()*1.5f;
	}
	
	//Align the cursor to the top-left of the textbox
	public void alignCursorTopLeft(){
		alignCursorLeft();
		alignCursorTop();
	}
	
	//Move the cursor to a new line in the textbox
	public void alignCursorNewLine(){
		//Cursor is placed to the far left
		alignCursorLeft();
		
		//Cursor is also moved down by the largest letter height of the text plus an additional half 
		alignCursorNextLine();
	}	
	
	protected void checkForNewLine(){
		//If the word reaches over the side of the textbox it needs to start a new line
		if (screenCursorPositionX + currentWordWidth + text.getLargestLetterWidth()*getScaleValueX() > getTranslationValueX() + model.getWidth()*getScaleValueX()/2.0f - text.getLargestLetterWidth()*getScaleValueX()/2.0f)
			//Start a new line
			alignCursorNewLine();
	}
	
	protected void setAllCharactersPickupDifference(float recordedTouchPixelX, float recordedTouchPixelY){
		//Set pickup difference for all in List letters
		for (int i=0; i<characters.size(); i++){
			characters.get(i).setPickupDifferenceX(characters.get(i).getTranslationValueX() - Main.pixelToScreenX(recordedTouchPixelX));
			characters.get(i).setPickupDifferenceY(characters.get(i).getTranslationValueY() - Main.pixelToScreenY(recordedTouchPixelY));
		}
	}
	
	public abstract void reformatText();
	public abstract void addTextOverTime(float delay);
	
}
