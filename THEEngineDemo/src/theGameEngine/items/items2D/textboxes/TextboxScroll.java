package theGameEngine.items.items2D.textboxes;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.main.Main;
import theGameEngine.models.Model2D;

public abstract class TextboxScroll extends Textbox
{
	
	//Determines scrolling
	boolean scrolling = false;
	float scrollingDistance = 0;
	
	public TextboxScroll(float centerX, float centerY, Model2D model, GUI gui) 
	{
		super(centerX, centerY, model, gui);
	}
	
	public void textboxScrollOnTouch(float touchPixelX, float touchPixelY, float startTime)
	{		
		if (!addingTextOverTime)
		{
			setAllCharactersPickupDifference(touchPixelX, touchPixelY);
			scrolling = true;
		}
		
		//Generic onTouch method for all GUIItems
		textboxOnTouch(touchPixelX, touchPixelY, startTime);
	}

	public void textboxScrollWhileTouched(float touchPixelX, float touchPixelY, float deltaTime)
	{
		//If user is touching the textbox to perform mini scrolling, perform miniScrolling dammit
		if (scrolling && touchable)
			performScrolling(touchPixelY);
		
		//Generic whileTouched method for all GUIItems
		textboxWhileTouched(touchPixelX, touchPixelY, deltaTime);
	}
	
	public void textboxScrollOnRelease(float touchPixelX, float touchPixelY, float endTime)
	{		
		if (scrolling)
		{
			if (Math.abs(characters.get(0).getTranslationValueY() - characters.get(0).getTranslationValueYStart()) > text.getLargestLetterWidth()/4)
				readyToAdvance = false;
		}
		
		scrolling = false;
		
		//Generic onRelease method for all GUIItems
		textboxOnRelease(touchPixelX, touchPixelY, endTime);
	}	
	
	@Override
	public void reformatText()
	{
		if (characters.isEmpty())
			return;
		
		int reformatIndex = 0;
		currentWordWidth = 0;
		
		alignCursorTopLeft();
		
		for (int i=0; i<textAll.length && textAll[i] != ' '; i++)
			currentWordWidth += text.getCharacterModel(textAll[i]).getWidth()*fontSize*getScaleValueX();
		
		for (int i=0; i<textAll.length; i++)
		{
			if (textAll[i] == ' ')
			{
				screenCursorPositionX += text.getLargestLetterWidth()*getScaleValueX()*(1.0f/2.0f);
				
				currentWordWidth = 0;
				for (int j=i+1; j<textAll.length && textAll[j] != ' '; j++)
					currentWordWidth += text.getCharacterModel(textAll[j]).getWidth()*fontSize*getScaleValueX();
				
				checkForNewLine();
			}
			else
			{
				reformatCharacter(characters.get(reformatIndex), textAll[i]);
				reformatIndex++;
			}
			
		}
		updateAbstractDataCharacters = true;
	}
	
	@Override
	public void addTextOverTime(float deltaTime)
	{
		textOverTimePassedTime += deltaTime;
		
		while (textOverTimePassedTime >= textOverTimeCurrentTime && addingTextOverTimeIndex < characters.size())
		{
			textOverTimeCurrentTime += textOverTimeIncrement;
			setCharacterVisible(addingTextOverTimeIndex, true);
		}
	}
	
	@Override
	protected void resize(float deltaTime)
	{
		super.resize(deltaTime);
		
		for (int i=0; i<characters.size(); i++)
			characters.get(i).setTranslationValueY(characters.get(i).getTranslationValueY() + scrollingDistance*getScaleValueY());
	}
		
	private void performScrolling(float recordedTouchPixelY)
	{
		float topOfTextbox = (getTranslationValueYStart() + model.getHeight()/2.0f);
		float firstCharacterPosition = characters.get(0).getTranslationValueYStart();
		float distanceBetweenTopOfTextboxAndFirstCharacterPosition = (topOfTextbox - firstCharacterPosition) * getScaleValueY();
		float pointTop = getTranslationValueY() + model.getHeight()*getScaleValueY()/2.0f - distanceBetweenTopOfTextboxAndFirstCharacterPosition;
		float pointBottom = getTranslationValueY() - model.getHeight()*getScaleValueY()/2.0f + distanceBetweenTopOfTextboxAndFirstCharacterPosition;
		
		if (Main.pixelToScreenY(recordedTouchPixelY) + characters.get(0).getPickupDifferenceY() < pointTop){
			return;
		}
		
		if (Main.pixelToScreenY(recordedTouchPixelY) + characters.get(characters.size()-1).getPickupDifferenceY() > pointBottom){
			return;
		}
		
		float beforeScroll = characters.get(0).getTranslationValueY();
		for (int i=0; i<characters.size(); i++)
			characters.get(i).setTranslationValueY(Main.pixelToScreenY(recordedTouchPixelY) + characters.get(i).getPickupDifferenceY());
		
		scrollingDistance += characters.get(0).getTranslationValueY() - beforeScroll;
		updateAbstractDataCharacters = true;
	}

}
