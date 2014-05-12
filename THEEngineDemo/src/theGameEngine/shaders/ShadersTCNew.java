package theGameEngine.shaders;

public class ShadersTCNew {

	public static final String lightShaderV = 
			"uniform mat4 u_MVPMatrix;										\n"
			
		+   "attribute vec3 a_position; 									\n"
		+   "attribute vec4 a_color; 										\n"
		+   "attribute vec2 a_tex; 											\n"	
		
		+   "varying vec2 v_tex; 											\n"
		+   "varying vec4 v_color;											\n"
		
		+   "void main(){ 													\n"		
		+	"	v_tex = a_tex;												\n"
		+	"	v_color = a_color;											\n"	
		+	"	gl_Position = u_MVPMatrix * vec4(a_position,1.0);			\n"
		+	"}																\n";
	
	public static final String lightShaderF =
			"precision mediump float;										\n"
			
		+   "uniform sampler2D u_tex;	 									\n"
		+	"uniform vec4 u_colorModifier;									\n"
		
		+	"varying vec2 v_tex;											\n"
		+   "varying vec4 v_color;											\n"
			
		+	"void main(){													\n"	
		
		+	"	vec4 texture = vec4(1,1,1,1);								\n"
		+	"	texture = texture * texture2D(u_tex, v_tex);				\n"	
		+	"	vec4 color = vec4(0,0,0,0);									\n"
		+	"	color = color + v_color;									\n"	
		+	"	vec4 colorMod = vec4(1.0,1.0,1.0,1.0);						\n"
		+	"	colorMod = colorMod * u_colorModifier;						\n"	

		//+	"	vec4 finalColor = texture * color * colorMod;				\n"
		+	"	vec4 finalColor = texture * color;							\n"
		
		//+	"	gl_FragColor = finalColor;									\n"
		+	"	gl_FragColor = finalColor;									\n"
		+	"}  															\n";
	
}
