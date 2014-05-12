package theGameEngine.controls;

//import android.view.MotionEvent;

public class TouchEvent {
	private float touchPixelX;
	private float touchPixelY;
	private int touchAction;
	
	public TouchEvent(){
		touchPixelX = -1;
		touchPixelY = -1;
		touchAction = -1;
	}
	
	public TouchEvent(float touchPixelX, float touchPixelY, int touchAction){
		this.touchPixelX = touchPixelX;
		this.touchPixelY = touchPixelY;
		this.touchAction = touchAction;
	}
	
	public TouchEvent(TouchEvent touch){
		this.touchPixelX = touch.getTouchPixelX();
		this.touchPixelY = touch.getTouchPixelY();
		this.touchAction = touch.getTouchAction();
	}
	
	public void update(float touchPixelX, float touchPixelY, int touchAction){
		this.touchPixelX = touchPixelX;
		this.touchPixelY = touchPixelY;
		this.touchAction = touchAction;
	}
	
	public void update(TouchEvent touch){
		this.touchPixelX = touch.getTouchPixelX();
		this.touchPixelY = touch.getTouchPixelY();
		this.touchAction = touch.getTouchAction();
	}
	
	public float getTouchPixelX(){
		return touchPixelX;
	}
	public float getTouchPixelY(){
		return touchPixelY;
	}
	public int getTouchAction(){
		return touchAction;
	}


}
