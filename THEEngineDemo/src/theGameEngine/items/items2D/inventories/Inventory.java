package theGameEngine.items.items2D.inventories;

import java.util.Arrays;

import theGameEngine.controllers.Controller2D;
import theGameEngine.items.items2D.Item2D;
import theGameEngine.items.items2D.inventoryItems.InventoryItem;
import theGameEngine.main.Main;
import theGameEngine.models.Model2D;

public abstract class Inventory extends Item2D{
	
	public static final int TYPE_ALL = 0;
	
	//Used the describe the dimensions of the Inventory
	//*************************************************
	protected int numRows;
	protected int numColumns;
	protected int numCells;
	//*************************************************
	
	protected float normalCellPointerX = 0;
	protected float normalCellPointerY = 0;
	protected float renderNormalCellPointerX = 0;
	protected float renderNormalCellPointerY = 0;
	
	//Type of the Inventory indicating which corresponding types of InventoryItems may be stored inside
	protected int type;
	
	//Array of items for an Inventory the size of rows*columns used to store InventoryItems
	public InventoryItem[] items;
	
	//Pointer to the touchedInventoryItem in cell
	protected InventoryItem touchInventoryItem;
	protected int touchInventoryItemIndex = -1;
	protected boolean inventoryItemTouch = false;
	
	
	public Inventory(float screenCenterX, float screenCenterY,
			boolean reverseX, boolean reverseY, int numRows, int numColumns, int type,
			Model2D model, Controller2D controller2D){
		
		super(model, controller2D);
		
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.numCells = numRows*numColumns;
		
		//Array of InventoryItems is created and explicitly filled with null values for storing and rendering InventoryItems
		this.items = new InventoryItem[numCells];
		Arrays.fill(items, null);

		
		float x,y;
		
		if (screenCenterX<0)
			x = -Main.getAspectRatio()+(1+screenCenterX);
		else if (screenCenterX>0)
			x = Main.getAspectRatio()-(1-screenCenterX);
		else x = 0;
		if (reverseX){
			for (int i=0; i<numColumns-1; i++)
				x-=model.getWidth();
		}
		
		y = screenCenterY;
		if (reverseY){
			for (int i=0; i<numRows-1; i++)
				y+=model.getHeight();
		}
		
		setTranslationValueXStart(x);
		setTranslationValueYStart(y);
		setTranslationValueZStart(0);
		
		setModel(model);
		
	}
	
	public void inventoryOnTouch(float touchPixelX, float touchPixelY, float startTime) {
		//Generic onTouch method for all GUIItems
		itemOnTouch(startTime);
		
		//touchedInventoryItem is set to the InventoryItem found at the cell index determined in the checkSlot method
		touchInventoryItemIndex = getCellIndexAtTouchPixel(touchPixelX, touchPixelY);
		touchInventoryItem = items[touchInventoryItemIndex];
		
		//Call the onTouch method for the touchedInventoryItem (if there is one)
		if (touchInventoryItem != null){
			inventoryItemTouch = true;
						
			if (touchInventoryItem.getDraggable())
				touchInventoryItem.setDragging(true);
			
			touchInventoryItem.onTouch(touchPixelX, touchPixelY, startTime);
		}
	}
	
	public void inventoryWhileTouched(float touchPixelX, float touchPixelY, float deltaTime){
		//Generic whileTouched method for all GUIItems
		itemWhileTouched(deltaTime);
		
		//Call the whileTouched method for the touchedInventoryItem (if there is one)
		if (inventoryItemTouch)
			touchInventoryItem.whileTouched(touchPixelX, touchPixelY, deltaTime);
	}

	public void inventoryOnRelease(float touchPixelX, float touchPixelY, float endTime) {
		//Generic onRelease method for all GUIItems
		itemOnRelease(endTime);
		
		//Call the onRelease method for the touchedInventoryItem (if there is one)
		if (inventoryItemTouch)
			touchInventoryItem.onRelease(touchPixelX, touchPixelY, endTime);
		
		//Clear the touchedInventoryItem pointer
		touchInventoryItem = null;
		touchInventoryItemIndex = -1;
		inventoryItemTouch = false;
	}
	
	public void update(float deltaTime){
		
	}

	
	public void updateInventoryItemsAbstractData(){
		for (int i=0; i<numCells; i++){
			if (items[i] == null)
				continue;
			items[i].updateAbstractData();
		}
	}
	
	public synchronized void synchronizeInventoryItems(){
		for (int i=0; i<numCells; i++){
			if (items[i] != null)
				items[i].synchronize();
		}
	}
	
	public int getType(){
		return type;
	}
	public int getRow(){
		return numRows;
	}
	public int getColumns(){
		return numColumns;
	}
	public int getNumCells(){
		return numCells;
	}
	
	public InventoryItem getTouchInventoryItem(){
		return touchInventoryItem;
	}
	
	public synchronized boolean addInventoryItemAtFirstEmptyCell(InventoryItem passedItem, int count){
		//If passed InventoryItem's type is not compatible with the Inventory's type, return false
		if (passedItem.type != type)
			return false;
		
		int firstEmptyCellIndex = -1;
		//First loop through inventory to determine if specific InventoryItem is already added
		for (int i=0; i<items.length; i++){
			if (items[i] != null){
				//Checks if the string name of the passed InventoryItem matches indexed InventoryItem
				if (items[i].getName().equals(passedItem.getName())){
					//If there is a match, add the count of the passed InventoryItem to existing InventoryItem
					items[i].setCount(items[i].getCount()+count);
					//Return true indicating the InventoryItem was added
					return true;
				}
			}
			//Record the index of the first empty cell found for possible insertion
			else if (firstEmptyCellIndex == -1)
				firstEmptyCellIndex = i;
		}
		if (firstEmptyCellIndex == -1)
			return false;
		
		addItem(passedItem, firstEmptyCellIndex);

		return true;
	}
	
	public synchronized boolean addInventoryItemAtCellIndex(InventoryItem passedItem, int cellIndex){
		if (passedItem.type != type)
			return false;
		
		if (items[cellIndex].getName().equals(passedItem.getName())){
			items[cellIndex].setCount(items[cellIndex].getCount() + passedItem.getCount());
			return true;
		}
		
		addItem(passedItem, cellIndex);
		
		return true;
	}
	
	public int getCellIndexAtTouchPixel(float touchPixelX, float touchPixelY){
		float touchNormalX = Main.pixelToScreenX(touchPixelX);
		float touchNormalY = Main.pixelToScreenY(touchPixelY);
		
		float cellWidth = model.getWidth();
		float cellHeight = model.getHeight();
		
		int touchIndex = 0;
		resetNormalCellPointers();
		while (!(touchNormalX>normalCellPointerX-cellWidth/2 &&
				touchNormalX<normalCellPointerX+cellWidth/2 &&
				touchNormalY<normalCellPointerY+cellHeight/2 &&
				touchNormalY>normalCellPointerY-cellHeight/2)){
			
			shiftToNextCell(touchIndex);
			touchIndex++;
		}
		
		return touchIndex;
		/*int selectColumn = 
		
		int selectRow = (int) Math.ceil(((returnScreenCenterY()+normalCoordinatesHeight/2)-
				pixelToScreenY(touchPixelY))/normalCellDimensionHeight)-1; //-1 for indexing starting at 0
		
		//Multiply by columns to accommodate for the number of columns per row
		return selectColumn + selectRow*numColumns;*/
	}
	
	private void addItem(InventoryItem passedItem, int cellIndex){
		alignToCell(cellIndex);
		
		passedItem.setTranslationValueXStart(normalCellPointerX);
		passedItem.setTranslationValueYStart(normalCellPointerY);
		passedItem.setTranslationValueZStart(-1);
		
		passedItem.myInventory = this;
		passedItem.cellIndex = cellIndex;
		
		items[cellIndex] = passedItem;
	}
	
	private void alignToCell(int index){
		resetNormalCellPointers();
		
		for (int i=0; i<index; i++)
			shiftToNextCell(i);
	}
	
	protected void shiftToNextCell(int currentIndex){
		//Indicates the current cell of the inventory is the last of the row (move to next row)
		if ((currentIndex+1)%numColumns == 0){
			//Put translationValueX back to the beginning
			normalCellPointerX = getTranslationValueXStart();
			//Move translationValueY down a row
			normalCellPointerY = normalCellPointerY-model.getHeight();
		}
		//Indicates the current cell of the inventory is not the last of the row (move to next column)
		else
			normalCellPointerX = (getTranslationValueX()+model.getWidth());
	}
	
	private void resetNormalCellPointers(){
		normalCellPointerX = getTranslationValueX();
		normalCellPointerY = getTranslationValueY();
	}
	
}
