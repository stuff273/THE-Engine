package theGameEngine.modelSets.modelSets2D;

import theGameEngine.controllers.Controller2D;
import theGameEngine.models.Model2D;

public class ModelSet2DStart extends ModelSet2D{
	
	public static Model2D fish = null;
	public static Model2D background = null;
	public static Model2D colorPillar = null;

	public ModelSet2DStart(Controller2D controller2D) {
		super(controller2D);
	}

	@Override
	protected void loadModels(Controller2D controller2D) {
		buttonInventoryHide = new Model2D(.15f, 0.2f, new float[] {133,184,247,410}, controller2D);
		
		inventoryCell = new Model2D(0.35f, 0.25f, new float[] {2,130,262,390}, controller2D);
		//inventoryCell = new ModelGUI(1f, new float[] {2,130,262,390}, gui);
		
		//inventoryItemTorch = new ModelGUI(0.35f, 0.25f, new float[]{2,43,422,511},  gui);
		//inventoryItemMonkey = new ModelGUI(0.35f, 0.25f, new float[]{45,177,415,510}, gui);
		//inventoryItemTorch = new Model2D(1.5f, new float[]{2,43,422,511},  controller2D);
		//inventoryItemMonkey = new Model2D(1f, new float[]{45,177,415,510}, controller2D);
		
		textbox = new Model2D(1.8f,0.7f,  248,248,255,0.2f);
		fps = new Model2D(0.3f,0.2f,  0,0,0,0f);
		colorbox = new Model2D(0.2f,0.2f,  255,0,0,1f);
		
		fish = new Model2D(new float[]{0,50,243,292}, controller2D);
		background = new Model2D(2f,2f, 0f,292f,512f, controller2D);
		colorPillar = new Model2D(.35f,2f, 255f,0f,0f,1f);
	}

}
