package theGameEngine.shaders;

public class ShadersC {

	public static final String lightShaderV = 
			"uniform mat4 u_MVPMatrix;										\n"
			
		+   "attribute vec3 a_position; 									\n"
		+   "attribute vec4 a_color; 										\n"
		
		+   "varying vec4 v_color;											\n"
		
		+   "void main(){ 													\n"		
		+	"	v_color = a_color;											\n"	
		+	"	gl_Position = u_MVPMatrix * vec4(a_position,1.0);			\n"
		+	"}																\n";
	
	public static final String lightShaderF =
			"precision mediump float;																										\n"
		
		+   "varying vec4 v_color;																											\n"
			
		+	"void main(){																													\n"
		+	"	gl_FragColor = v_color;																										\n"
		+	"}  																															\n";
	
}
