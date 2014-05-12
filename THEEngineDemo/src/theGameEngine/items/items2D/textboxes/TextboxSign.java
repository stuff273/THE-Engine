package theGameEngine.items.items2D.textboxes;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.modelSets.modelSets2D.ModelSet2D;
import theGameEngine.text.Text;

public class TextboxSign extends TextboxScroll
{
	
	int pass = 0;

	public TextboxSign(float centerX, float centerY, GUI gui)
	{	
		super(centerX, centerY, ModelSet2D.textbox, gui);
		
		this.text = new Text(gui);
		
		addTextToBeAddedInstant("WARNING: DANGEROUS CAVE AHEAD!!"
				+ " Be sure to bring lots of capable equipment, potions, and courage!"
				+ " Total number of lost explorers = 76,967."
				+ " Will YOU be lucky number 77,777?? This is a test to see if the"
				+ " engine has received any speed up because of the changes I made to"
				+ " sprite batching. I really hope so because this took a lot of work!",30);
		
	}

	@Override
	public void onTouch(float touchPixelX, float touchPixelY, float startTime) 
	{		
		/*for (int i=0; i<characters.size(); i++)
		{
			if ((i+1)%2 == 0)
				setCharacterVisible(i,false);
		}*/
		//animation = AnimationsGUI.Grow(1f,1.05f,1f);
		
		textboxScrollOnTouch(touchPixelX, touchPixelY, startTime);
	}

	@Override
	public void whileTouched(float touchPixelX, float touchPixelY, float deltaTime)
	{
		textboxScrollWhileTouched(touchPixelX, touchPixelY, deltaTime);
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime) 
	{
		//for (int i=0; i<characters.size(); i++)
			//setCharacterVisible(i,true);
					
		//resetComplete();
		
		textboxScrollOnRelease(touchPixelX, touchPixelY, endTime);
	}
	

}
