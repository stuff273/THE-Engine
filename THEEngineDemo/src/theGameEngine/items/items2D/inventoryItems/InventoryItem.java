package theGameEngine.items.items2D.inventoryItems;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.items.items2D.Item2D;
import theGameEngine.items.items2D.inventories.Inventory;
import theGameEngine.main.Main;
import theGameEngine.models.Model2D;

public abstract class InventoryItem extends Item2D{
	
	//String name used to know what InventoryItem it is
	protected String name;
	//Type of InventoryItem to determine which Inventories it may be stored in (swords don't go in cooking ingredients!)
	public int type;
	//How much of the InventoryItem is in the Inventory
	private int count;
	//Where the InventoryItem is stored in its respective Inventory
	public int cellIndex;
	
	protected boolean draggable = false;
	protected boolean dragging = false;
	
	//My own personal Inventory!
	public Inventory myInventory = null;
	
	//Inventory pointer used for Inventory drops
	Inventory checkInventory;
	
	public InventoryItem(Inventory inventory, Model2D model, GUI gui){
		
		super(model, gui);
		
		myInventory = inventory;
		
	}
	
	public abstract void dropScene(float touchPixelX, float touchPixelY);
	public abstract void dropInventory(Inventory inventory, int cellIndex);
	
	public void inventoryItemOnTouch(float touchPixelX, float touchPixelY, float startTime){
		setPickupDifference(touchPixelX, touchPixelY);
	}
	
	public void inventoryItemWhileTouched(float touchPixelX, float touchPixelY, float deltaTime){
		if (dragging)
			performDrag(touchPixelX, touchPixelY);
	}
	
	public void inventoryItemOnRelease(float touchPixelX, float touchPixelY, float endTime){
		//Method to call if InventoryItem was being dragged
		if (dragging)
			draggingOnRelease();
		
		dragging = false;
	}
	
	public void update(float deltaTime){
		
	}
	
	public String getName(){
		return name;
	}
	public int getCount(){
		return count;
	}
	public boolean getDraggable(){
		return draggable;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	public void setDragging(boolean dragging){
		this.dragging = dragging;
	}
	
	public void remove(int amount){
		//Count is decremented
		count = count - amount;
		
		//Whoa! Count is 0 so that means it shouldn't exist in the Inventory any more
		if (count == 0)
			//Remove from inventory
				//This is why we need the keyIndex to know WHERE this InventoryItem is being placed
			myInventory.items[cellIndex] = null;
	}
	
	public boolean checkDropInventory(float touchPixelX, float touchPixelY){
		for (int i=0; i<((GUI) controller2D).getInventories().size(); i++){
			//checkInventory pointer used to mark Inventory we are testing against to avoid using *.get(i) command every time
			checkInventory = ((GUI) controller2D).getInventory(i);
			//Make sure checkInventory is visible
			//if (checkInventory.getVisible() == true){
				//Make sure the type of checkInventory is compatible with InventoryItem type
				if (checkInventory.getType() == type){
					//Check for touch collision with checkInventory
					if (checkInventory.checkTouch(touchPixelX,touchPixelY))
						//An Inventory has been found, return true
						return true;
				}
			//}
		}
		//No Inventory found for collision, return false;
		return false;
	}
	
	//Inserts this InventoryItem in checkInventory at designated cell (cell determined by running checkCell() on checkInventory parameter for dropInventory())
		//This method will swap InventoryItem in new Inventory at designated cell with this InventoryItem
	public void insertInInventorySwap(Inventory newInventory, int newCellIndex){
		if (newInventory.equals(myInventory) && cellIndex == newCellIndex){
			resetTranslationValues();
			return;
		}
		
		//Create backup of InventoryItem stored in newInventory at designated newCell
			//P.S. This little guy might be null
		InventoryItem tempInventoryItem = newInventory.items[newCellIndex];
		//Create backup of Inventory and cell index to place the InventoryItem stored in newInventory at designated newCell
		Inventory tempInventory = myInventory;
		int tempCellIndex = cellIndex;
		
		newInventory.addInventoryItemAtCellIndex(this, newCellIndex);
		
		//If the tempInventoryItem is null, simply add a null value to this InventoryItem's previous Inventory
		if (tempInventoryItem == null)
			tempInventory.items[tempCellIndex] = null;
		//Else the tempInventoryItem is not null (i.e. it is an actual InventoryItem), add it to the previous Inventory
		else tempInventory.addInventoryItemAtCellIndex(tempInventoryItem,tempCellIndex);
		
		//Swap is complete!
	}
	
	public void setPickupDifference(float touchPixelX, float touchPixelY){
		pickupDifferenceX = getTranslationValueX() - Main.pixelToScreenX(touchPixelX);
		pickupDifferenceY = getTranslationValueY() - Main.pixelToScreenY(touchPixelY);
	}
	
	void performDrag(float touchPixelX, float touchPixelY){
		setTranslationValueX(Main.pixelToScreenX(touchPixelX) + pickupDifferenceX);
		setTranslationValueY(Main.pixelToScreenY(touchPixelY) + pickupDifferenceY);
	}
	
	void draggingOnRelease(){
		//Check to see if the dragging InventoryItem has been dropped in an Inventory
		if (checkDropInventory(Main.screenToPixelX(getTranslationValueX()),Main.screenToPixelY(getTranslationValueY())))
			//InventoryItem has been dropped in an Inventory and sending and receiving Inventory types match
				//Drop in inventory
			dropInventory(checkInventory,checkInventory.getCellIndexAtTouchPixel(Main.screenToPixelX(getTranslationValueX()), Main.screenToPixelY(getTranslationValueY())));
		
		//If the InventoryItem was not dropped in an Inventory, then it must have been dropped in the Scene
		else dropScene(Main.screenToPixelX(getTranslationValueX()),Main.screenToPixelY(getTranslationValueY()));
	}
	
}
