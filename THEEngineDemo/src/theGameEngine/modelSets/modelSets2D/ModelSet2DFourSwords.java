package theGameEngine.modelSets.modelSets2D;

import theGameEngine.controllers.Controller2D;
import theGameEngine.models.Model2D;

public class ModelSet2DFourSwords extends ModelSet2D{

	public static Model2D link = null;
	public static Model2D linkSwordSwing0 = null;
	public static Model2D linkSwordSwing1 = null;
	public static Model2D linkSwordSwing2 = null;
	public static Model2D linkSwordSwing3 = null;
	public static Model2D linkSwordSwing4 = null;
	public static Model2D linkSwordSwing5 = null;
	public static Model2D linkSwordSwing6 = null;

	
	public ModelSet2DFourSwords(Controller2D controller2d) {
		super(controller2d);
	}

	@Override
	protected void loadModels(Controller2D controller2d) {
		link = new Model2D(new float[]{56,74,245,271}, controller2d);
		linkSwordSwing0 = new Model2D(new float[]{89,115,242,283}, controller2d);
		linkSwordSwing1 = new Model2D(new float[]{131,153,242,283}, controller2d);
		linkSwordSwing2 = new Model2D(new float[]{168,187,242,283}, controller2d);
		linkSwordSwing3 = new Model2D(new float[]{207,225,242,283}, controller2d);
		linkSwordSwing4 = new Model2D(new float[]{246,269,242,283}, controller2d);
		linkSwordSwing5 = new Model2D(new float[]{285,314,242,283}, controller2d);
		linkSwordSwing6 = new Model2D(new float[]{325,357,242,283}, controller2d);
	}

}
