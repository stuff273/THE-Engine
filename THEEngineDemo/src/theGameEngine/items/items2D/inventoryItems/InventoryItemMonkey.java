package theGameEngine.items.items2D.inventoryItems;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.items.items2D.inventories.Inventory;
import theGameEngine.main.Main;
import theGameEngine.modelSets.modelSets2D.ModelSet2D;


public class InventoryItemMonkey extends InventoryItem{
	
	public InventoryItemMonkey(Inventory inventory, GUI gui) {
		
		super(inventory,ModelSet2D.inventoryItemMonkey,gui);
		name = "MONKEY";
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
		
		Main.vibrate(60);
	}

	@Override
	public void onRelease(float touchPixelX, float touchPixelY, float endTime) {
		inventoryItemOnRelease(touchPixelX, touchPixelY, endTime);
	}
	
	
	@Override
	public void dropScene(float pixelX, float pixelY) {
		resetComplete();
		//gui.touchControl.createScreenToWorldRay(pixelX,pixelY,gui.camera.cameraPosition); 
		//gui.touchControl.rayPlaneCollisionDetect();
		//if (VectorAndMatrixMath.checkWorldArea2DCollision(gui.camera.collisionPoint[0],gui.camera.collisionPoint[2],worldInfo.layoutTop) == false){
			//VectorAndMatrixMath.findClosestPointToArea(gui.camera.collisionPoint[0],gui.camera.collisionPoint[2],worldInfo.layoutTop,newPoints);
		
			//worldInfo.addItem(0, new float[]{newPoints[0],gui.camera.collisionPoint[1]+2,newPoints[1]}, true, false, false);
						
			//return;
		//}
		
		//worldInfo.worldItemsToBeAdded.add(new ItemMonkeyMorphing(new float[]{gui.touchControl.collisionPoint[0],gui.touchControl.collisionPoint[1]+2,gui.touchControl.collisionPoint[2]},true,-1,worldInfo));
	}

	@Override
	public void dropInventory(Inventory inventory, int cellIndex) {
		insertInInventorySwap(inventory,cellIndex);
	}

}
