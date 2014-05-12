package theGameEngine.shaders;

public abstract class ShaderHelper {	
	
	int handlePositionAttribute;
	int handleNormalAttribute;
	int handleTextureAttribute;
	int handleColorAttribute;
	
	int MVPMatrixHandle;
	
	public abstract void assignHandles();
	
	public abstract void bindAttributeHandles();
	
	public abstract void useProgram();

}
