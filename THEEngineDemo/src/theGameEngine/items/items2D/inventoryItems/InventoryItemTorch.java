package theGameEngine.items.items2D.inventoryItems;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.items.items2D.inventories.Inventory;
import theGameEngine.modelSets.modelSets2D.ModelSet2D;

public class InventoryItemTorch extends InventoryItem{
	
	public InventoryItemTorch(Inventory inventory, GUI gui) {
		
		super(inventory, ModelSet2D.inventoryItemTorch, gui);
		name = "TORCH";
		type = Inventory.TYPE_ALL;
		draggable = true;
	}
	
	@Override
	public void onTouch(float touchPixelX, float touchPixelY, float startTime) {
		inventoryItemOnTouch(touchPixelX, touchPixelY, startTime);
	}

	@Override
	public void whileTouched(float touchPixelX, float touchPixelY, float deltaTime) {
		inventoryItemWhileTouched(touchPixelX, touchPixelY, deltaTime);
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime) {
		inventoryItemOnRelease(touchPixelX, touchPixelY, endTime);
	}
	
	
	public void dropScene(float touchPixelX, float touchPixelY){
		resetComplete();
	}

	@Override
	public void dropInventory(Inventory inventory, int cellIndex) {
		insertInInventorySwap(inventory,cellIndex);
	}


}
