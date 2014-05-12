package theGameEngine.shaders;

public class ShadersT {

	public static final String lightShaderV = 
			"uniform mat4 u_MVPMatrix;										\n"
			
		+   "attribute vec3 a_position; 									\n"
		+   "attribute vec2 a_tex; 											\n"	
		
		+   "varying vec2 v_tex; 											\n"
		
		+   "void main(){ 													\n"		
		+	"	v_tex = a_tex;												\n"
		+	"	gl_Position = u_MVPMatrix * vec4(a_position,1.0);			\n"
		+	"}																\n";
	
	public static final String lightShaderF =
			"precision mediump float;																										\n"
			
		+   "uniform sampler2D u_tex;	 								    																\n"
		+	"uniform vec3 u_deltaPerturb;																									\n"
		+	"uniform vec2 u_deltaDisplacement;																								\n"
		
		+	"varying vec2 v_tex;																											\n"
			
		+	"void main(){																													\n"
		+	"	vec2 perturb;																												\n"
		+	"	float rad = 1.0;																											\n"
		+	"	rad = (v_tex[0] + v_tex[1] - 1.0 + u_deltaPerturb[0]) * u_deltaPerturb[1];													\n"
		+	"	perturb.x = sin(rad) * u_deltaPerturb[2];																					\n"
		+	"	rad = (v_tex[0] - v_tex[1] + u_deltaPerturb[0]) * u_deltaPerturb[1];														\n"
		+	"	perturb.y = sin(rad) * u_deltaPerturb[2];																					\n"
		
		+	"	gl_FragColor = texture2D(u_tex, v_tex+perturb+u_deltaDisplacement);															\n"
		+	"}  																															\n";
	
}