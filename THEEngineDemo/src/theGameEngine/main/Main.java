package theGameEngine.main;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import theGameEngine.controllers.Controller2D;
import theGameEngine.controllers.Controller3D;
import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.controllers.Scenes.Scenes2D.Scene2DFourSwords;
import theGameEngine.controllers.Scenes.Scenes3D.Scene3DStart;
import theGameEngine.controls.Camera;
import theGameEngine.controls.TouchControl;
import theGameEngine.shaders.ShaderHelperC;
import theGameEngine.shaders.ShaderHelperT;
import theGameEngine.updateThread.LoadThread2D;
import theGameEngine.updateThread.LoadThread3D;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;


@SuppressLint("NewApi")
public class Main extends Activity implements Renderer{
	//The glView describes how we will output to the screen using Android
	GLSurfaceView glView;

	//TouchListener thread that handles touch actions
	public TouchListener touchListener;
	
	static float totalDataCount = 0;
	static float currentDataCount = 0;
	
	
	//=============================================================================================
	// Helpers
	// Will stay in Main and be called by static functions
	
	// Camera controls camera movement such as arcball rotation
	private static Camera camera = null;
	
	// Matrices that are needed for rendering
	private static float[] modelMatrix = null;
	private static float[] viewMatrix = null;
	private static float[] projectionMatrix = null;
	private static float[] orthogonalMatrix = null;
	
	private static float[] MVMatrix = null;
	private static float[] MVP2DMatrix = null;
	private static float[] MVP3DMatrix = null;
	
	//private static float[] normalMatrix = null;
	
	// ShaderHelpers gives all renderers tools to set up and use shaders
	private static ShaderHelperT shaderHelperT = null;
	private static ShaderHelperC shaderHelperC = null;
	
	// TouchControl controls what to do with touches such as zoom and model manipulation
	private static TouchControl touchControl = null;
	
	// Vibrator allows control over device vibration
	private static Vibrator vibrator = null;
	//=============================================================================================
	
	
	// Current scene3D
	private static Controller3D currentScene3D = null;
	private static Controller3D toSetScene3D = null;
	private static boolean setNewScene3D = false;
	
	// Current scene2D
	private static Controller2D currentScene2D = null;
	private static Controller2D toSetScene2D = null;
	private static boolean setNewScene2D = false;
	
	//Current GUI
	GUI currentGUI = null; 
	
	//Updater thread
	//UpdateThread runnable = new UpdateThread();
	 
	//===================================================================================
	// Delta time represents how much time has passed between previous and current frame
	
	float deltaTime = 0;
	float previousTime = 0;
	//===================================================================================
	
	
	//======================================
	// Screen information
	
	private static float screenWidth = 0;
	private static float screenHeight = 0;
	private static float aspectRatio = 0;
	//======================================
	
	private static int currentTextureValue = -1;
	
	
	// Used for loading from assets folder
	private static AssetManager assets;
	
	// Used for retrieving initial screen information
	DisplayMetrics displaymetrics;

	
	 
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// AssetManager is used to load files from assets folder
		assets = getAssets();
		
		//=============================================================================================================
		// Set up full screen and turn off default obstructions

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//=============================================================================================================
		
		
		//=========================================================================================
		// Find the width and height of the screen for initial setup
		
		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		screenWidth = (float)displaymetrics.widthPixels;
		screenHeight = (float)displaymetrics.heightPixels;
		aspectRatio = screenWidth/screenHeight;
		//=========================================================================================
		
		
		// Set up the camera
		camera = new Camera(2.0f,4.0f,5.0f,	0.0f,1.0f,0.0f);
		
		// Set up matrices
		buildMatrices();
		
		// Vibrator is saved for future reference
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		// Set up touchControl
		touchControl = new TouchControl();
		
		
		// Set up view matrix
		updateViewMatrix();
		
		// Sets up the OpenGL view
		//========================================================================================================
		glView = new GLSurfaceView(this);
		glView.setEGLContextClientVersion(2);
		
		// Sets up the stencil buffer (bits for r, bits for g, bits for b, bits for a, depth size, stencil size)
		glView.setEGLConfigChooser(8, 8, 8, 8, 16, 8);
		
		glView.setRenderer(this);
		setContentView(glView);
		//========================================================================================================
		
		// Construct the initial Scene2D (this will not be using the OpenGL thread yet)
		//currentScene2D = new Scene2DSplashScreen(assets);
		//currentScene2D.loadInitialData();
		currentScene2D = new Scene2DFourSwords(assets);
		currentScene2D.loadInitialData();
		touchControl.setScene2D(currentScene2D);
		
		//currentScene3D.loadInitialData();
		//touchControl.setScene3D(currentScene3D);
		
		startLoad(new Scene3DStart(assets));
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		glView.onResume();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		glView.onPause();
		currentScene3D = null;
	}

	@Override
	public void onDrawFrame(GL10 arg0)
	{
		// Clear the color and depth information that OpenGL currently has
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		//currentScene.synchronizeObjects();
		//currentGUI.synchronizeObjects();
		
		//Calculate deltaTime (it's going to be very small)
		deltaTime = (System.nanoTime()-previousTime) / 1000000000.0f;
		//Start time is set to current system time in order to recalculate deltaTime next frame
		previousTime = System.nanoTime();
		
		//runnable.setDeltaTime(deltaTime);
		
		/*if (camera.createRenderRay)
			camera.createRenderRay();
				if (setNewScene2D)
		{
			currentScene2D = toSetScene2D;
			touchControl.setScene2D(currentScene2D);
			setNewScene2D = false;
		}
		if (camera.drawRay){	
			camera.renderRay();
		}*/
		//Calculate arcball rotation with the touch actions built up and apply the rotation to the view matrix
		//camera.arcballRotation(touchControl.getGeneralTouches());
		//Calculate zoom and apply to view matrix
		//camera.zoom();
		
		if (setNewScene3D)
		{
			currentScene3D = toSetScene3D;
			touchControl.setScene3D(currentScene3D);
			setNewScene3D = false;
		}
		if (setNewScene2D)
		{
			currentScene2D = toSetScene2D;
			touchControl.setScene2D(currentScene2D);
			setNewScene2D = false;
		}
				
		//===========================================================
		// Resolve any touches
		
		if (currentScene3D != null)
			currentScene3D.resolveTouches(deltaTime);
		if (currentScene2D != null)
			currentScene2D.resolveTouches(deltaTime);
		//===========================================================

		
		//===========================================================
		// Updating
		
		if (currentScene3D != null)
		{
			currentScene3D.update(deltaTime);
		}
		if (currentScene2D != null)
		{
			currentScene2D.update(deltaTime);
		}
		if (currentGUI != null)
		{
			currentGUI.updateModels(deltaTime);
		}
		//===========================================================
		
		//===========================================================
		// Update all abstract data
		
		if (currentScene2D != null)
			currentScene2D.updateAbstractData();
		//===========================================================
		
		
		//===========================================================
		// Rendering
		
		if (currentScene3D != null)
		{			
			currentScene3D.renderModels();
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
		}
		if (currentScene2D != null)
		{
			currentScene2D.renderModels();
		}
		if (currentGUI != null)
		{
			currentGUI.renderModels();
		}
		//===========================================================
	}


	@SuppressLint("NewApi")
	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height)
	{
		//Specifies the affine transformation of x and y from normalized device coordinates to window coordinates
		GLES20.glViewport(0, 0, width, height);
		
		//Record new screen information
		//***********************************************
		screenWidth = (float)width;
		screenHeight = (float)height;
		aspectRatio = screenWidth/screenHeight;
		//***********************************************
		
		// Set up perspective matrix with newly obtained infromation
		updateProjectionMatrix();
		
		// Set up new orthogonalMatix (and, in turn, MVP2DMatrix)
		updateOrthogonalMatrix();		
	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig config)
	{
		//Set the clear color
		//GLES20.glClearColor(0.5f, 1.0f, 0.83f, 0.5f);
		GLES20.glClearColor(00.0f, 0.0f, 0.0f, 0.5f);
		
		//Set up OpenGL with many options
		//*********************************************************************************
		
		//Enable OpenGL to consider faces drawn in counterclockwise fashion to be the front
		GLES20.glFrontFace(GLES20.GL_CCW);
		//Enable OpenGL to remove all faces of objects if looking at the back of them
		GLES20.glCullFace(GLES20.GL_BACK);
		//Enable OpenGL to cull faces
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		//Set up depth testing
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		//Enable OpenGL to alpha blend colors
		GLES20.glEnable(GLES20.GL_BLEND);
		//Set up blender function
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		//GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		//Enable OpenGL to fill in polygons to reduce artifacts
		//GLES20.glEnable(GLES20.GL_POLYGON_OFFSET_FILL);
		//Set up offset function
		//GLES20.glPolygonOffset(0.0f, 1.0f);
		//*********************************************************************************
		
		//Set up TouchListener (needs OpenGL thread to set TouchListener to GLSurfaceView glView)
		touchListener = new TouchListener(getBaseContext(),glView,touchControl);
		//Set up ShaderHelpers (needs OpenGL thread to construct shaders and build programs)
		shaderHelperT = new ShaderHelperT();
		shaderHelperC = new ShaderHelperC();
		
		//Construct the initial GUI
		//currentGUI = new GUIStart(matrixHolder, shaderHelpers, camera, touchControl, vibrator, getBaseContext());	
		
		//Link Scene and GUI together
		//syncScene2DAndGUI();
		
		//Setup the initial Scene (needs OpenGL thread for building buffers)
		//Setup the initial GUI(needs OpenGL thread for building buffers)
		//currentGUI.setup(assets);
				
		//runnable.setControllers(currentScene,currentGUI);
		//new Thread(runnable).start();
		
		// Begin timer for calculating deltaTime
		previousTime = System.nanoTime();
	}
	
	public static float getScreenWidth()
	{
		return screenWidth;
	}
	public static float getScreenHeight()
	{
		return screenHeight;
	}
	public static float getAspectRatio()
	{
		return aspectRatio;
	}
	
	
	
	private static void buildMatrices()
	{
		modelMatrix = new float[16];
		viewMatrix = new float[16];
		projectionMatrix = new float[16];
		orthogonalMatrix = new float[16];
		
		MVMatrix = new float[16];
		MVP2DMatrix = new float[16];
		MVP3DMatrix = new float[16];
		
		//normalMatrix = new float[9];
		
		Matrix.setIdentityM(modelMatrix, 0);
		
		updateProjectionMatrix();
		
		//Create a new "projection" matrix to be used for othrographic viewing (Only GUI needs this)
			//VERY IMPORTANT:
			//Using the orthogonal android matrix for the GUI, the difference in screen sizes must be taken into account
			//Using the aspect ratio of the screen helps to avoid any undesirable artifacts
			//Remember: Even though the screen measures x=-aspectRatio:aspectRatio & y=-1:1, the distances will be the same for both axis
			//Ex. The screen distance 0.5f in the x direction will be the same screen distance in the y direction
		updateOrthogonalMatrix();
		
		updateMVP2DMatrix();
	}
	public static float[] getModelMatrix()
	{
		return modelMatrix;
	}
	public static float[] getViewMatrix()
	{
		return viewMatrix;
	}
	public static float[] getProjectionMatrix()
	{
		return projectionMatrix;
	}
	public static float[] getOrthogonalMatrix()
	{
		return orthogonalMatrix;
	}
	public static float[] getMVMatrix()
	{
		return MVMatrix;
	}
	public static float[] getMVP2DMatrix()
	{
		return MVP2DMatrix;
	}
	public static float[] getMVP3DMatrix()
	{
		return MVP3DMatrix;
	}

	public static void updateModelMatrix()
	{
		Matrix.setIdentityM(modelMatrix, 0);
	}
	public static void updateViewMatrix()
	{
		Matrix.setLookAtM(viewMatrix,0,
		          camera.getPositionX(),camera.getPositionY(),camera.getPositionZ(),
		          0.0f,0.0f,0.0f,
		          camera.getUpDirectionX(),camera.getUpDirectionY(),camera.getUpDirectionZ());
	}
	public static void updateProjectionMatrix()
	{
		Matrix.perspectiveM(projectionMatrix, 0, 60.0f, Main.getAspectRatio(), 2f, 300.0f);
	}
	public static void updateOrthogonalMatrix()
	{
		Matrix.orthoM(orthogonalMatrix, 0, -Main.getAspectRatio(), Main.getAspectRatio(), -1f, 1f, -.05f, .05f);
	}
	public static void updateMVMatrix()
	{
		Matrix.multiplyMM(MVMatrix, 0, viewMatrix, 0, modelMatrix, 0);
	}
	public static void updateMVP2DMatrix()
	{
	    Matrix.multiplyMM(MVP2DMatrix, 0, orthogonalMatrix, 0, modelMatrix, 0);
	}
	public static void updateMVP3DMatrix()
	{
		updateMVMatrix();
		Matrix.multiplyMM(MVP3DMatrix, 0, projectionMatrix, 0, MVMatrix, 0);
	}
	
	public static float[] getCameraPosition()
	{
		return camera.getPosition();
	}
	public static float[] getCameraUpDirection()
	{
		return camera.getUpDirection();
	}
	
	public static float getTouchPixelX()
	{
		return touchControl.getTouchPixelX();
	}
	public static float getTouchPixelY()
	{
		return touchControl.getTouchPixelY();
	}
	public static float getTouchScreenX()
	{
		return pixelToScreenX(touchControl.getTouchPixelX());
	}
	public static float getTouchScreenY()
	{
		return pixelToScreenY(touchControl.getTouchPixelY());
	}
	public static void createScreenToWorldRay(float touchPixelX, float touchPixelY)
	{
		touchControl.createScreenToWorldRay(touchPixelX, touchPixelY, camera.getPosition());
	}
	public static float[] getScreenToWorldRayNear()
	{
		return touchControl.getScreenToWorldRayNear();
	}
	public static float[] getScreenToWorldRayFar()
	{
		return touchControl.getScreenToWorldRayFar();
	}
	
	public static void useProgramC()
	{
		shaderHelperC.useProgram();
	}
	public static void useProgramT()
	{
		shaderHelperT.useProgram();
	}
	public static void switchToTexture(int textureValue)
	{
		shaderHelperT.bindTextureDataHandle(textureValue);
		//shaderHelpers.getShaderHelperNT().setTextureValue(textureValue);
		currentTextureValue = textureValue;
	}
	public static void assignHandlesToCurrentlyBoundBufferC()
	{
		shaderHelperC.bindAttributeHandles();
		shaderHelperC.bindMVPMatrixHandle(MVP2DMatrix);
	}
	public static void assignHandlesToCurrentlyBoundBufferC3D()
	{
		shaderHelperC.bindAttributeHandles();
		shaderHelperC.bindMVPMatrixHandle(MVP3DMatrix);
	}
	public static void assignHandlesToCurrentlyBoundBufferT()
	{		
		shaderHelperT.bindAttributeHandles();
		shaderHelperT.bindMVPMatrixHandle(MVP2DMatrix);
	}
	public static void assignHandlesToCurrentlyBoundBufferT3D()
	{		
		shaderHelperT.bindAttributeHandles();
		shaderHelperT.bindMVPMatrixHandle(MVP3DMatrix);
	}
	
	public static int getCurrentTextureValue()
	{
		return currentTextureValue;
	}
	
	
	public static void vibrate(long durationMilliseconds)
	{
		vibrator.vibrate(durationMilliseconds);
	}
	
	public static AssetManager getAssetManager()
	{
		return assets;
	}
	
	public static void setNewScene2D(Controller2D newScene)
	{
		//Log.i(newScene.toString(), "New Scene loaded");
		toSetScene2D = newScene;
		setNewScene2D = true;
	}
	public static void setNewScene3D(Controller3D newScene)
	{
		toSetScene3D = newScene;
		setNewScene3D = true;
	}
	
	
	//Method used to convert a number from any range to any other range
	public static float convertToNewRange(float oldNumber, float oldMin, float oldMax, float newMin, float newMax){
		return ((oldNumber - oldMin) * (newMax - newMin) / (oldMax - oldMin)) + newMin;
	}
	//Method used to convert normal coordinates (-1,1) to screen coordinates (-aspectRatio,aspectRatio)
	public static float convertNormalToScreen(float oldNumber){
		return ((oldNumber+1)*(aspectRatio*2)/2) - aspectRatio;
	}
	//Method used for converting pixel coordinates to screen coordinates on the x axis
	public static float pixelToScreenX(float pixelCoordinateX){
		float answer = ((pixelCoordinateX*aspectRatio*2)/screenWidth)-aspectRatio;
		return answer;
	}
	//Method used for converting pixel coordinates to screen coordinates on the y axis
	public static float pixelToScreenY(float pixelCoordinateY){
		float answer = (pixelCoordinateY*2/screenHeight)-1;
		return answer;
	}
	//Method used for converting screen coordinates to pixel coordinates on the x axis
	public static float screenToPixelX(float screenCoordinateX){
		float answer = ((screenCoordinateX+aspectRatio)*screenWidth/(aspectRatio*2));
		return answer;
	}
	//Method used for converting screen coordinates to pixel coordinates on the y axis
	public static float screenToPixelY(float screenCoordinateY){
		float answer = ((screenCoordinateY+1)*screenHeight/2);
		return answer;
	}	
	
	public static Controller2D getCurrentScene2D()
	{
		return currentScene2D;
	}
	
	public static void startLoad(Controller2D theScene)
	{
		new LoadThread2D().execute(theScene);
	}
	public static void startLoad(Controller3D theScene)
	{
		new LoadThread3D().execute(theScene);
	}
	public static void setTotalDataCount(float dataCount)
	{
		totalDataCount = dataCount;
		currentDataCount = 0;
	}
	public static void incrementDataCount()
	{
		currentDataCount += 1;
	}
	public static float getLoadingNormalPercentage()
	{
		return currentDataCount/totalDataCount;
	}
	
}
