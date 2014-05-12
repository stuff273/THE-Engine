package theGameEngine.main;


import theGameEngine.controls.TouchControl;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchListener implements OnTouchListener{

	ScaleGestureDetector scaleDetector;

	TouchControl touchControl;
	
	public TouchListener(Context context, GLSurfaceView glView,TouchControl touchControl){
		glView.setOnTouchListener(this);
		this.scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		this.touchControl = touchControl;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event){
		scaleDetector.onTouchEvent(event);
		
		touchControl.processTouch(event.getX(),event.getY(),event.getAction());
		
		return true;
	}
	
	public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
		@Override
		public boolean onScale(ScaleGestureDetector detector){
			touchControl.processZoom(detector.getScaleFactor());
			return true;
		}
	}
	
}
