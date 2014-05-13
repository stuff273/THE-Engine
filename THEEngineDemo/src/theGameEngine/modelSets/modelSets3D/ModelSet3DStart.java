package theGameEngine.modelSets.modelSets3D;

import theGameEngine.main.Main;
import theGameEngine.models.Model3D;
import android.content.res.AssetManager;

@SuppressWarnings("unused")
public class ModelSet3DStart extends ModelSet3D
{
	
	public static Model3D redCube = null;
	public static Model3D monkeyBlue = null;
	public static Model3D group = null;
	public static Model3D monkey = null;
	
	float totalDataCount;
		
	AssetManager assets = null;

	public ModelSet3DStart(AssetManager assets)
	{
		monkey = Model3D.newModel3DPoisitionDAE("monkey.dae", assets);
		//redCube = new Model3D("redCube.ply", assets);
		//monkeyBlue = new Model3D("monkeyBlue.ply", assets);
		//group = new Model3D("group.ply", assets);
		
		//Main.setTotalDataCount(redCube.getTotalDataCount() + monkeyBlue.getTotalDataCount() + group.getTotalDataCount());
		//redCube.collectAndBuild();
		//monkeyBlue.collectAndBuild();
		//group.collectAndBuild();

		//monkey = new ModelScene("monkey.ply",assets);
		//modelMonkeyNoNormals = new Model("monkeyNoNormals.ply",assets);
		//modelCharacterRed = new ModelSceneMesh("characterRed.ply",assets);
		//modelCharacterEyesNormal = new ModelSceneMesh("characterEyesNormal.ply",assets);
		//modelCharacterEyesWink = new ModelSceneMesh("characterEyesWink.ply",assets);
		//modelCharacterEyesMad = new ModelSceneMesh("characterEyesMad.ply",assets);
		//modelSword = new ModelSceneMesh("sword.ply",assets);
		//modelStaff = new ModelSceneMesh("staff.ply",assets);
		//modelBow = new ModelSceneMesh("bow.ply",assets);
		
		//modelBlock = new ModelScene("block.ply",assets);
		//modelBall = new Model("ball.ply",assets);
		//modelWater = new ModelSceneMesh("water2D.ply",assets);
	}

}
