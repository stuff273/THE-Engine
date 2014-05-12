package theGameEngine.items.items2D.textboxes;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.main.Main;
import theGameEngine.models.Model2D;

public abstract class TextboxPageScroll extends Textbox{
	
	//Determines scrolling to next page
	boolean scrollToNextPage = false;
	//Determines scrolling to previous page
	boolean scrollToPreviousPage = false;
	
	//Determines if the text is currently scrolling
	boolean scrolling = false;
	//Determines if the text is being manipulated up or down to indicate going forward or back a page
	boolean miniScrolling = false;
	
	float bottomOfPage = 0;
	float resetPositionY = 0;
	float miniScrollingDistance = .05f;
	
	public TextboxPageScroll(float centerX, float centerY, Model2D model, GUI gui) {
		
		super(centerX, centerY, model, gui);
		
		bottomOfPage = getTranslationValueY() - (model.getHeight()*getScaleValueY()/2f);

	}
	
	public void textboxPageScrollOnTouch(float touchPixelX, float touchPixelY, float startTime){
		//Generic onTouch method for all GUIItems
		textboxOnTouch(touchPixelX, touchPixelY, startTime);
		
		if (!addingTextOverTime)
		{
			/*for (int i=0; i<characters.size(); i++){
				characters.get(i).setTranslationValueXStart(characters.get(i).getTranslationValueX());
				characters.get(i).setTranslationValueYStart(characters.get(i).getTranslationValueY());
				characters.get(i).resetTranslationValues();
			}*/
			resetPositionY = characters.get(pages.get(currentPage)).getTranslationValueY();
			setAllCharactersPickupDifference(touchPixelX, touchPixelY/5);
			miniScrolling = true;
		}
		
	}

	public void textboxPageScrollWhileTouched(float touchPixelX, float touchPixelY, float deltaTime) {
		//Generic whileTouched method for all GUIItems
		textboxWhileTouched(touchPixelX, touchPixelY, deltaTime);
		
		//If user is touching the textbox to perform mini scrolling, perform miniScrolling dammit
		if (miniScrolling)
			performMiniScrolling(touchPixelY);
	}
	
	public void textboxPageScrollOnRelease(float touchPixelX, float touchPixelY, float endTime)
	{
		//Generic onRelease method for all GUIItems
		textboxOnRelease(touchPixelX, touchPixelY, endTime);
		
		//If performing mini scrolling and user releases, reset letters to appropriate locations
		if (miniScrolling)
		{
			float distance = characters.get(pages.get(currentPage)).getTranslationValueY() - resetPositionY;
			
			for (int i=0; i<characters.size(); i++)
				characters.get(i).setTranslationValueY(characters.get(i).getTranslationValueY() - distance);
			
			miniScrolling = false;
			
			updateAbstractDataCharacters = true;
		}
		
		if (readyToAdvance && !endOfText)
		{
			readyToAdvance = false;
			miniScrolling = false;
			
			goToPage(currentPage+1);
			maxPageGoneTo++;
			
			addingTextOverTime = true;
		}
		
		if (!addingTextOverTime)
			readyToAdvance = true;
		
	}
	
	@Override
	public void updateCharacters(float deltaTime){
		super.updateCharacters(deltaTime);
		
		if (scrollToPreviousPage)
			performScrollToPreviousPage(deltaTime);
		
		if (scrollToNextPage)
			performScrollToNextPage(deltaTime);
		
		if (scrollToPreviousPage || scrollToNextPage)
			updateAbstractDataCharacters = true;
		
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
				checkForNewPage(reformatIndex);
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
	public void addTextOverTime(float deltaTime){		
		textOverTimePassedTime += deltaTime;
		
		while (textOverTimePassedTime >= textOverTimeCurrentTime){
			if (currentPage == pages.size()-1){
				if (addingTextOverTimeIndex <= characters.size()-1){
					textOverTimeCurrentTime += textOverTimeIncrement;
					setCharacterVisible(addingTextOverTimeIndex++, true);
				}
				else{
					addingTextOverTime = false;
					endOfText = true;
					break;
				}
			}
			else{
				if (shrinkAndGrow) break;
				if (addingTextOverTimeIndex < pages.get(currentPage+1)){
					textOverTimeCurrentTime += textOverTimeIncrement;
					setCharacterVisible(addingTextOverTimeIndex++, true);
				}
				else{
					addingTextOverTime = false;
					
					if (controller2D.getOnTouch() || controller2D.getWhileTouched())
						readyToAdvance = false;
					else readyToAdvance = true;
					
					break;
				}
			}
		}
	}
	
	//Checks to see if the newline lies outside of the textbox and a new page needs to be created to hold additional text
	private boolean checkForNewPage(int currentCharacterIndex){
		//Calculation is basically alignCursorTop() calculation from the bottom
		if (screenCursorPositionY <= bottomOfPage + text.getLargestLetterHeight()){
			//Indicates that there is more text to be added to the textbox in another page
			startNewPage(currentCharacterIndex);
			return true;
		}
		return false;
	}	
	//Starts a new page in the textbox
	private void startNewPage(int currentCharacterIndex)
	{						
		alignCursorTop();
		screenCursorPositionY -= model.getHeight()*getScaleValueY()*1.5f*pages.size();
		
		bottomOfPage = screenCursorPositionY + text.getLargestLetterHeight()*2f - model.getHeight()*getScaleValueY();
		
		//Add to pages to indicate what character starts the new page
		pages.add(currentCharacterIndex);
	}
	
	private void goToPage(int page)
	{
		//alignCursorTop();
		//float distance = screenCursorPositionY - characters.get(pages.get(page)).getTranslationValueY();
		float distance = (page - currentPage) * (model.getHeight()*getScaleValueY()*1.5f);
		//float distance = characters.get(pages.get(page)).getTranslationValueYStart() - characters.get(pages.get(page)).getTranslationValueY();
		for (int i=0; i<characters.size(); i++){
			characters.get(i).setTranslationValueY(characters.get(i).getTranslationValueY() + distance);
			//characters.get(i).setTranslationValueY(screenCursorPositionY + (characters.get(i).getTranslationValueY() - screenCursorPositionYStart));
			//characters.get(i).resetTranslationValues();
		}
		updateAbstractDataCharacters = true;
		currentPage = page;
	}
	
	//miniScrolling is when the user grabs the page displayed in List letters and moves it up and down to indicate going to previous or next page
	private void performMiniScrolling(float recordedTouchPixelY){
		//Align cursor to the top
		//alignCursorTop();
		//Align cursor to (almost) second line
		//screenCursorPositionY -= (text.getLargestLetterHeight()*1.5f)/7;

		//Check to see if scrolling to next page
		if (Main.pixelToScreenY(recordedTouchPixelY/5) + characters.get(pages.get(currentPage)).getPickupDifferenceY() > resetPositionY + miniScrollingDistance){
			//Set current page to the next page
			currentPage++;
			//Tells the TextboxPageScroll to perform scrolling to the next page
			scrollToNextPage = true;
			//Return to stop miniScrolling
			return;
		}
		
		//Align cursor to the top
		//alignCursorTop();
		//Align cursor to (almost) imaginary line above the top
		//screenCursorPositionY += (text.getLargestLetterHeight()*1.5f)/7;
		
		//Check to see if scrolling to previous page
		if (Main.pixelToScreenY(recordedTouchPixelY/5) + characters.get(pages.get(currentPage)).getPickupDifferenceY() < resetPositionY - miniScrollingDistance){
			//Set current page to previous page
			currentPage--;
			//Tells the TextboxPageScroll to perform scrolling to the previous page
			scrollToPreviousPage = true;
			//Return to stop miniScrolling
			return;
		}
		
		//Loop through all letters and perform mini scrolling effect
		for (int i=0; i<characters.size(); i++)
			characters.get(i).setTranslationValueY(Main.pixelToScreenY(recordedTouchPixelY/5) + characters.get(i).getPickupDifferenceY());
		
		updateAbstractDataCharacters = true;
	}
	
	
	private void performScrollToPreviousPage(float deltaTime){
		//If the current page is the very first page...
		if (currentPage < 0){		
			//Turn off scrolling to previous page
			scrollToPreviousPage = false;
			//Makes it so when a user releases, the textbox will not continue to the next page
			readyToAdvance = false;
			//Reset current page to what it was before miniScrolling set it to to the previous page (which doesn't exist)
			currentPage++;
			return;
		}
		
		readyToAdvance = false;
		miniScrolling = false;
		
		float endingPoint = characters.get(pages.get(currentPage)).getTranslationValueYStart() + model.getHeight()*getScaleValueY()*1.5f*currentPage;
		//if ((characters.get(pages.get(currentPage)).getTranslationValueY() - deltaTime*2f) <= screenCursorPositionY){
		if ((characters.get(pages.get(currentPage)).getTranslationValueY() - deltaTime*2f) <= endingPoint){

			//float scrollingDifference = screenCursorPositionY - characters.get(pages.get(currentPage)).getTranslationValueY();
			float scrollingDifference = endingPoint - characters.get(pages.get(currentPage)).getTranslationValueY();
			
			for (int i=0; i<characters.size(); i++)
				characters.get(i).setTranslationValueY(characters.get(i).getTranslationValueY() + scrollingDifference);
			
			scrollToPreviousPage = false;
			
			//Needs to be set because scrollToPreviousPage being false wouldn't set it in the update method
			updateAbstractDataCharacters = true;

			if (!controller2D.getOnTouch() && !controller2D.getWhileTouched())
				readyToAdvance = true;
			else readyToAdvance = false;
		}
		
		else {
			for (int i=0; i<characters.size(); i++)
				characters.get(i).setTranslationValueY(characters.get(i).getTranslationValueY() - deltaTime*2f);
		}
	}
	
	private void performScrollToNextPage(float deltaTime){
		if (currentPage > maxPageGoneTo){			
			scrollToNextPage = false;
			
			readyToAdvance = false;
			
			currentPage--;
			
			return;
		}
		
		readyToAdvance = false;
		miniScrolling = false;
		
		float endingPoint = characters.get(pages.get(currentPage)).getTranslationValueYStart() + model.getHeight()*getScaleValueY()*1.5f*currentPage;
		//if ((characters.get(pages.get(currentPage)).getTranslationValueY() + deltaTime*2f) >= screenCursorPositionY){
		if ((characters.get(pages.get(currentPage)).getTranslationValueY() + deltaTime*2f) >= endingPoint){

			//float scrollingDifference = screenCursorPositionY - characters.get(pages.get(currentPage)).getTranslationValueY();
			float scrollingDifference = endingPoint - characters.get(pages.get(currentPage)).getTranslationValueY();
			
			for (int i=0; i<characters.size(); i++)
				characters.get(i).setTranslationValueY(characters.get(i).getTranslationValueY() + scrollingDifference);
			
			
			scrollToNextPage = false;
			updateAbstractDataCharacters = true;

			if (!controller2D.getOnTouch() && !controller2D.getWhileTouched())
				readyToAdvance = true;
			else readyToAdvance = false;
		}
		
		else {
			for (int i=0; i<characters.size(); i++)
				characters.get(i).setTranslationValueY(characters.get(i).getTranslationValueY() + deltaTime*2f);
		}
	}

}
