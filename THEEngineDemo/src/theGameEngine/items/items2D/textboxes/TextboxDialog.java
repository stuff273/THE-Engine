package theGameEngine.items.items2D.textboxes;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.modelSets.modelSets2D.ModelSet2D;
import theGameEngine.text.Text;

public class TextboxDialog extends TextboxPageScroll{
	
	int pass = 0;

	public TextboxDialog(float centerX, float centerY, GUI gui) {
		
		super(centerX, centerY, ModelSet2D.textbox, gui);
		
		this.text = new Text(gui);
		
		addTextToBeAddedOverTime("\"This is the first dialog box that I'm testing with actual"
				+ " dialog! I... I don't know what to say! I'm quite speechless! Umm,"
				+ " my dream is to become a shop keeper and sell overpriced merchandise to the"
				+ " hero of this future game! Whatever it may be!\"",.1f,20);
		
		/*addTextToBeAddedInstant("\"This is the first dialog box that I'm testing with actual"
				+ " dialog! I... I don't know what to say! I'm quite speechless! Umm,"
				+ " my dream is to become a shop keeper and sell overpriced merchandise to the"
				+ " hero of this future game! Whatever it may be!\"",20);*/
		
	}

	@Override
	public void onTouch(float touchPixelX, float touchPixelY, float startTime) {
		textboxPageScrollOnTouch(touchPixelX, touchPixelY, startTime);
		//shrinkAndGrow = true;
		//animation = AnimationsGUI.Grow(1f,1.05f,1f);
	}

	@Override
	public void whileTouched(float touchPixelX, float touchPixelY, float deltaTime) {
		textboxPageScrollWhileTouched(touchPixelX, touchPixelY, deltaTime);		
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime) {
		textboxPageScrollOnRelease(touchPixelX, touchPixelY, endTime);
		
		//shrinkAndGrow = false;
		//resetComplete();
	}

}
