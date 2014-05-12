package theGameEngine.items.items2D.textboxes;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.models.Model2D;

public abstract class TextboxDisplayOnly extends Textbox{
	
	public TextboxDisplayOnly(float centerX, float centerY, Model2D model, GUI gui) 
	{
		super(centerX, centerY, model, gui);
	}
	
	public void textboxDisplayOnlyOnTouch(float touchPixelX, float touchPixelY, float startTime)
	{
		
	}

	public void textboxDisplayOnlyWhileTouched(float touchPixelX, float touchPixelY, float deltaTime) 
	{
		
	}
	
	public void textboxDisplayOnlyOnRelease(float touchPixelX, float touchPixelY, float endTime){
		
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
			
		if (addingTextOverTime){
			for (int i=0; i<characters.size(); i++)
				setCharacterVisible(i, false);
		}
		
		updateAbstractDataCharacters = true;

	}
	
	public void addTextOverTime(float deltaTime)
	{
		textOverTimePassedTime += deltaTime;
		
		while (textOverTimePassedTime >= textOverTimeCurrentTime && addingTextOverTimeIndex < characters.size())
		{
			textOverTimeCurrentTime += textOverTimeIncrement;
			setCharacterVisible(addingTextOverTimeIndex, true);
		}
	}

}
