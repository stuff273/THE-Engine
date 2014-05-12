package theGameEngine.items.items2D.inventories;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.modelSets.modelSets2D.ModelSet2D;

public class InventoryGeneralPurpose extends Inventory{

	public InventoryGeneralPurpose(float centerX, float centerY,
			boolean reverseX, boolean reverseY,	int rows, int columns,
			GUI gui){
		
		super(centerX, centerY,
				reverseX, reverseY,	rows, columns, TYPE_ALL,
				ModelSet2D.inventoryCell, gui);
	}

	@Override
	public void onTouch(float touchPixelX, float touchPixelY, float startTime) {
		//onTouch method that all Inventories must call
		inventoryOnTouch(touchPixelX, touchPixelY, startTime);
	}

	@Override
	public void whileTouched(float touchPixelX, float touchPixelY, float deltaTime) {
		//whileTouched method that all Inventories must call
		inventoryWhileTouched(touchPixelX, touchPixelY, deltaTime);
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime) {
		//onRelease method that all Inventories must call
		inventoryOnRelease(touchPixelX, touchPixelY, endTime);
	}

}
