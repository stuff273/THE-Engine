package theGameEngine.modelSets.modelSets2D;

import theGameEngine.controllers.Controller2D;
import theGameEngine.models.Model2D;

public class ModelSet2DSinkyFishSplashScreen extends ModelSet2D{
	
	public static Model2D splashlogo = null;
	public static Model2D colorbox = null;

	public ModelSet2DSinkyFishSplashScreen(Controller2D controller2D) {
		super(controller2D);
	}

	@Override
	protected void loadModels(Controller2D controller2D) {
		splashlogo = new Model2D(1.0f,1.0f, new float[]{0,256,0,256}, controller2D);
		colorbox = new Model2D(1.8f,1f, 255,0,255,1);
	}

}
