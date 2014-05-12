package theGameEngine.modelSets.modelSets2D;

import theGameEngine.controllers.Controller2D;
import theGameEngine.models.Model2D;


public abstract class ModelSet2D{
	
	public static Model2D buttonInventoryHide = null;

	public static Model2D buttonLightBulbTurnOff = null;
	public static Model2D buttonLightBulbTurnOn = null;
	
	public static Model2D inventoryCell = null;
	
	public static Model2D inventoryItemTorch = null;
	public static Model2D inventoryItemMonkey = null;
	
	public static Model2D textbox = null;
	public static Model2D fps = null;
	
	public static Model2D colorbox = null;	
	
	public ModelSet2D(Controller2D controller2D){
		loadModels(controller2D);
	}
	
	protected abstract void loadModels(Controller2D controller2D);
}
