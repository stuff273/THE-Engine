package theGameEngine.items.items2D.textboxes;

import android.os.SystemClock;
import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.modelSets.modelSets2D.ModelSet2D;
import theGameEngine.text.Text;

public class TextboxFPS extends TextboxDisplayOnly{
	
	float passedTime = 0;
	float previousTime = SystemClock.elapsedRealtime();
	String framesPerSecondString;
	
	public TextboxFPS(float centerX, float centerY, GUI gui) {
		
		super(centerX, centerY, ModelSet2D.fps, gui);
		
		this.text = new Text(gui);
		
		//addTextToBeAddedInstant(Integer.toString(gui.getFramesPerSecond()),15);
	}

	@Override
	public void onTouch(float touchPixelX, float touchPixelY, float startTime) {
	}

	@Override
	public void whileTouched(float touchPixelX, float touchPixelY, float deltaTime) {
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime) {
	}
	
	@Override
	public void updateCharacters(float deltaTime){		
		//Calculate deltaTime (it's going to be very small)
		/*passedTime += SystemClock.elapsedRealtime()-previousTime;
		//Start time is set to current system time in order to recalculate deltaTime next frame
		previousTime = SystemClock.elapsedRealtime();
		
		if (passedTime >= 1000){
			addTextToBeAddedInstant(Integer.toString(gui.getFramesPerSecond()),15);
			passedTime = 0;
			gui.setFramesPerSecond(0);
		}*/
		
		super.updateCharacters(deltaTime);
		
	}

}
