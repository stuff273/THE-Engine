package theGameEngine.controllers.Scenes.Scenes3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import theGameEngine.controllers.Controller3D;
import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.items.items3D.Item3D;
import theGameEngine.main.Main;
import theGameEngine.math.VectorAndMatrixMath;
import theGameEngine.models.Model;
import android.content.res.AssetManager;
import android.opengl.Matrix;

public abstract class Scene3D extends Controller3D{
	float length = 0;
	float closestDist = 0;

	// *********************************************
	// All variables used for billboarding an object
	private float[] billboard = new float[3];
	private float[] billboardingCenter = { 0.0f, 0.0f, 0.0f };
	private float[] billboardingLookAt = { 0.0f, 0.0f, 1.0f };
	private float[] objToCamera = new float[3];
	private float billboardingAngle = 0;
	// *********************************************

	// **********************************************************
	// Lists to contain all items and separates them by attributes
	List<Item3D> worldItemsN = new ArrayList<Item3D>();
	List<Item3D> worldItemsC = new ArrayList<Item3D>();
	List<Item3D> worldItemsT = new ArrayList<Item3D>();
	List<Item3D> worldItemsNT = new ArrayList<Item3D>();
	List<Item3D> worldItemsNC = new ArrayList<Item3D>();
	
	public List<Item3D> worldItemsToBeAdded = Collections.synchronizedList (new ArrayList<Item3D>());
	public List<Item3D> totalItems = new ArrayList<Item3D>();
	// **********************************************************
		
	//The partnering GUI
	GUI gui;

	public Scene3D(AssetManager assets) 
	{
		super(assets);
	}
	
	
	public void setGUI(GUI gui)
	{
		this.gui = gui;
	}

	public boolean checkTouch()
	{
		touchPixelX = getTouchPixelX();
		touchPixelY = getTouchPixelY();
		
		//Set the checkTouch boolean to false in order to check for touches only once
		checkTouch = false;
		
		//Firstly, create a screen-to-world ray in order to check against hitboxes
		Main.createScreenToWorldRay(touchPixelX, touchPixelY);
		
		//Loop through all the Items
		for (int i=0; i<totalItems.size(); i++)
		{
			checkTouchObject = totalItems.get(i);
			
			//Skip the Item if it does not have a hitbox
			if (checkTouchObject.hitbox == null)
				continue;
			
			//Does the screen-to-world ray collide with the hitbox?
			if (checkTouchObject.hitbox.rayCollisionDetect(Main.getScreenToWorldRayNear(), Main.getScreenToWorldRayNear()))
			{
				//Set the touchedItem pointer
				touchObject = checkTouchObject;
				
				checkTouchObject = null;
				
				//Set the itemTouched boolean
				onTouch = true;
				
				//An item has been touched
				return true;
			}
		}
		//An item has not been touched
		return false;
	}

	public void checkRelease()
	{
		touchPixelX = getTouchPixelX();
		touchPixelY = getTouchPixelY();
		
		whileTouched = false;
		onRelease = true;
		
		return;
	}

	
	public void update(float deltaTime)
	{
		if (checkTouch)
		{
			checkTouch();
			setCheckTouch(false);
		}
		else if (checkRelease)
		{
			checkRelease();
			setCheckRelease(false);
		}
		
		//Perform updates for the touched object(s) first
		if (onTouch)
		{
			touchObject.onTouch(getTouchPixelX(), getTouchPixelY(), deltaTime);
			onTouch = false;
			whileTouched = true;
		}
		else if (whileTouched)
		{
			touchObject.whileTouched(getTouchPixelX(), getTouchPixelY(), deltaTime);
		}
		else if (onRelease)
		{
			touchObject.onRelease(getTouchPixelX(), getTouchPixelY(), deltaTime);
			onRelease = false;
			touchObject = null;
		}
		
		/*for (int i=0; i<totalItems.size(); i++){
			totalItems.get(i).updateAbstractData(deltaTime);
		}*/
	}
	
	public void renderModels()
	{
		addItemsToBeAdded();

		/*if (worldItemsN.size() >= 1) {
			shaderHelpers.getShaderHelperN().useProgram();
			for (int i = 0; i < worldItemsN.size(); i++){
				renderObject = worldItemsN.get(i);
				renderObject.draw(matrixHolder, shaderHelpers.getShaderHelperN());
			}
		}
		
		if (worldItemsNC.size() >= 1) {
			shaderHelpers.getShaderHelperNC().useProgram();
			for (int i = 0; i < worldItemsNC.size(); i++){
				renderObject = worldItemsNC.get(i);
				renderObject.draw(matrixHolder, shaderHelpers.getShaderHelperNC());
			}
		}
		
		if (worldItemsNT.size() >= 1) {
			shaderHelpers.getShaderHelperNT().useProgram();
			for (int i = 0; i < worldItemsNT.size(); i++){
				renderObject = worldItemsNT.get(i);
				renderObject.draw(matrixHolder, shaderHelpers.getShaderHelperNT());
			}
		}

		if (worldItemsT.size() >= 1){
			shaderHelpers.getShaderHelperT().useProgram();
			for (int i = 0; i < worldItemsT.size(); i++){
				renderObject = worldItemsT.get(i);
				renderObject.draw(matrixHolder, shaderHelpers.getShaderHelperT());
			}
		}
		
		if (worldItemsC.size() >= 1) {
			shaderHelpers.getShaderHelperC().useProgram();
			for (int i = 0; i < worldItemsC.size(); i++){
				renderObject = worldItemsC.get(i);
				renderObject.draw(matrixHolder, shaderHelpers.getShaderHelperC());;
			}
		}
		renderObject = null;*/
	
	}

	protected void createBoxWorld(float[][][] world) 
	{
		/*int height = world.length;
		int depth;
		int width;

		int heightHalf = (int) Math.floor(height / 2);
		int depthHalf;
		int widthHalf;

		for (int i = 0; i < world.length; i++) {
			depth = world[i].length;
			depthHalf = -(int) Math.floor(depth / 2);
			for (int j = 0; j < world[i].length; j++) {
				width = world[i][j].length;
				widthHalf = -(int) Math.floor(width / 2);
				for (int k = 0; k < world[i][j].length; k++) {
					if (world[i][j][k] == 0)
						addItem(new ItemSceneBlock(new float[] { widthHalf * 2, heightHalf * 2, depthHalf * 2 }, 0, this));
					//else if (world[i][j][k] == 1)
						//addItem(new ItemSceneWaterStill(new float[] { widthHalf * 2, (heightHalf * 2)+.8f, depthHalf * 2 }, true, 1, this));
					//else if (world[i][j][k] == 2)
						//addItem(new ItemSceneWaterNegZ(new float[] { widthHalf * 2, (heightHalf * 2)+.8f, depthHalf * 2 }, true, 1, this));
					widthHalf += 1;
				}
				depthHalf += 1;
			}
			heightHalf -= 1;
		}*/

	}
	
	private void addItemsToBeAdded()
	{
		if (worldItemsToBeAdded.size()>=1){
			for (int i=0; i<worldItemsToBeAdded.size(); i++)
				addItem(worldItemsToBeAdded.get(i));
			worldItemsToBeAdded.clear();
		}
	}
	
	public void addItem(Item3D item) 
	{
		totalItems.add(item);

		switch(item.getModel().getType()){
		case(Model.TYPE_N):
			worldItemsN.add(item);
			break;
		case(Model.TYPE_T):
			worldItemsT.add(item);
			break;
		case(Model.TYPE_C):
			worldItemsC.add(item);
			break;
		case(Model.TYPE_NT):
			worldItemsNT.add(item);
			break;
		case(Model.TYPE_NC):
			worldItemsNC.add(item);
			break;
		}
		
	}

	public void billboardCylindricalX(float[] modelMatrix)
	{
		billboardingAngle = 0;

		float[] cameraPosition = Main.getCameraPosition();
		objToCamera[0] = 0;
		objToCamera[1] = cameraPosition[1] - billboardingCenter[1];
		objToCamera[2] = cameraPosition[2] - billboardingCenter[2];
		
		VectorAndMatrixMath.normalize(objToCamera);
		for (int i = 0; i < objToCamera.length; i++)
			billboardingAngle += objToCamera[i] * billboardingLookAt[i];
		
		billboardingAngle = (float) Math.acos(billboardingAngle);
		
		billboard[0] = billboardingLookAt[1] * objToCamera[2]
				- billboardingLookAt[2] * objToCamera[1];
		billboard[1] = billboardingLookAt[2] * objToCamera[0]
				- billboardingLookAt[0] * objToCamera[2];
		billboard[2] = billboardingLookAt[0] * objToCamera[1]
				- billboardingLookAt[1] * objToCamera[0];
		
		if (billboardingAngle != 0)
			Matrix.rotateM(modelMatrix, 0, (billboardingAngle * 180.0f / 3.14f), billboard[0], billboard[1], billboard[2]);
	}

	public void billboardCylindricalY(float[] modelMatrix)
	{
		billboardingAngle = 0;

		float[] cameraPosition = Main.getCameraPosition();
		objToCamera[0] = cameraPosition[0] - billboardingCenter[0];
		objToCamera[1] = 0;
		objToCamera[2] = cameraPosition[2] - billboardingCenter[2];
		
		VectorAndMatrixMath.normalize(objToCamera);
		
		for (int i = 0; i < objToCamera.length; i++)
			billboardingAngle += objToCamera[i] * billboardingLookAt[i];
		
		billboardingAngle = (float) Math.acos(billboardingAngle);
		
		billboard[0] = billboardingLookAt[1] * objToCamera[2]
				- billboardingLookAt[2] * objToCamera[1];
		billboard[1] = billboardingLookAt[2] * objToCamera[0]
				- billboardingLookAt[0] * objToCamera[2];
		billboard[2] = billboardingLookAt[0] * objToCamera[1]
				- billboardingLookAt[1] * objToCamera[0];
		
		if (billboardingAngle != 0)
			Matrix.rotateM(modelMatrix, 0, (billboardingAngle * 180.0f / 3.14f), billboard[0], billboard[1], billboard[2]);
	}

	public void billboardCylindricalZ(float[] modelMatrix) 
	{
		billboardingAngle = 0;

		float[] cameraPosition = Main.getCameraPosition();
		objToCamera[1] = cameraPosition[0] - billboardingCenter[0];
		objToCamera[1] = cameraPosition[1] - billboardingCenter[1];
		objToCamera[2] = 0;
		
		VectorAndMatrixMath.normalize(objToCamera);
		
		for (int i = 0; i < objToCamera.length; i++)
			billboardingAngle += objToCamera[i] * billboardingLookAt[i];
		
		billboardingAngle = (float) Math.acos(billboardingAngle);
		
		billboard[0] = billboardingLookAt[1] * objToCamera[2]
				- billboardingLookAt[2] * objToCamera[1];
		billboard[1] = billboardingLookAt[2] * objToCamera[0]
				- billboardingLookAt[0] * objToCamera[2];
		billboard[2] = billboardingLookAt[0] * objToCamera[1]
				- billboardingLookAt[1] * objToCamera[0];
		
		if (billboardingAngle != 0)
			Matrix.rotateM(modelMatrix, 0, (billboardingAngle * 180.0f / 3.14f), billboard[0], billboard[1], billboard[2]);
	}
}
