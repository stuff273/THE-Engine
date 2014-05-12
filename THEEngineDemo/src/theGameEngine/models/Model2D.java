package theGameEngine.models;

import theGameEngine.controllers.Controller2D;
import theGameEngine.main.Main;

public class Model2D extends Model{
	
	//Normal width is how much of the screen (from 0 to 2) in the x-direction the model will take up
	//Normal height is how much of the screen (from 0 to 2) in the y-direction the model will take up

	//aspectRatio is screenWidth/screenHeight
	
	//==========================================================
	// textureSpriteDimensions contains information as follows:
	
	// textureSpriteDimensions[0] = pixelXStart
	// textureSpriteDimensions[1] = pixelXEnd
	// textureSpriteDimensions[2] = pixelYStart
	// textureSpriteDimensions[3] = pixelYEnd
	//==========================================================
	
	//textureImageWidth is the width of the sprite image
	//textureImageHeight is the height of the sprite image
	
	//red is the red value in rgba (from 0 to 255)
	//green is the green value in rgba (from 0 to 255)
	//blue is the blue value in rgba (from 0 to 255)
	//alpha is the alpha value in rgba (from 0 to 1)
	
	//This method is for describing a model that uses a texture
	public Model2D(float[] textureSpriteDimensions, Controller2D controller2D){
		
		float normalWidth = (textureSpriteDimensions[1] - textureSpriteDimensions[0])/controller2D.getTextureImageWidth();
		float normalHeight = (textureSpriteDimensions[3] - textureSpriteDimensions[2])/controller2D.getTextureImageHeight();
		
		normalWidth *= Main.getAspectRatio();
		normalHeight *= Main.getAspectRatio();
		
		highX = (normalWidth/2);
		lowX = -(normalWidth/2);
		highY = (normalHeight/2);
		lowY = -(normalHeight/2);
		
		attributes = new float[20];
		
		type = TYPE_T;
		
		//Attributes are recorded in array of float size 20
			//Organization is x,y,z,textureX,textureY
			//Vertex order is top-left, bottom-left, bottom-right, top-right
		//******************************************************************
		//Width and height are divided by two in order to reach preferred side of model
		//normalWidth is multiplied with aspectRatio so the model will scale correctly across multiple devices
		attributes[0] = -normalWidth/2;
		attributes[1] = normalHeight/2;
		//Z value is always 0 because the images have no depth
		attributes[2] = 0.0f;
		//textureSpriteDimensions values are divided by textureImage values to obtain normalized values (between 0 and 1)
		attributes[3] = textureSpriteDimensions[0]/controller2D.getTextureImageWidth();
		attributes[4] = textureSpriteDimensions[2]/controller2D.getTextureImageHeight();
		
		attributes[5] = -normalWidth/2;
		attributes[6] = -normalHeight/2;
		attributes[7] = 0.0f;
		attributes[8] = textureSpriteDimensions[0]/controller2D.getTextureImageWidth();
		attributes[9] = textureSpriteDimensions[3]/controller2D.getTextureImageHeight();
		
		attributes[10] = normalWidth/2;
		attributes[11] = -normalHeight/2;
		attributes[12] = 0.0f;
		attributes[13] = textureSpriteDimensions[1]/controller2D.getTextureImageWidth();
		attributes[14] = textureSpriteDimensions[3]/controller2D.getTextureImageHeight();
		
		attributes[15] = normalWidth/2;
		attributes[16] = normalHeight/2;
		attributes[17] = 0.0f;
		attributes[18] = textureSpriteDimensions[1]/controller2D.getTextureImageWidth();
		attributes[19] = textureSpriteDimensions[2]/controller2D.getTextureImageHeight();
		//******************************************************************

	}
	
	//This method is for describing width and height independent of the texture 
	public Model2D(float normalWidth, float normalHeight, float[] textureSpriteDimensions, Controller2D controller2D){
		
		normalWidth *= Main.getAspectRatio();
		
		highX = (normalWidth/2);
		lowX = -(normalWidth/2);
		highY = (normalHeight/2);
		lowY = -(normalHeight/2);
		
		attributes = new float[20];
		
		type = TYPE_T;
		
		//Attributes are recorded in array of float size 20
			//Organization is x,y,z,textureX,textureY
			//Vertex order is top-left, bottom-left, bottom-right, top-right
		//******************************************************************
		//Width and height are divided by two in order to reach preferred side of model
		//normalWidth is multiplied with aspectRatio so the model will scale correctly across multiple devices
		attributes[0] = -normalWidth/2;
		attributes[1] = normalHeight/2;
		//Z value is always 0 because the images have no depth
		attributes[2] = 0.0f;
		//textureSpriteDimensions values are divided by textureImage values to obtain normalized values (between 0 and 1)
		attributes[3] = textureSpriteDimensions[0]/controller2D.getTextureImageWidth();
		attributes[4] = textureSpriteDimensions[2]/controller2D.getTextureImageHeight();
		
		attributes[5] = -normalWidth/2;
		attributes[6] = -normalHeight/2;
		attributes[7] = 0.0f;
		attributes[8] = textureSpriteDimensions[0]/controller2D.getTextureImageWidth();
		attributes[9] = textureSpriteDimensions[3]/controller2D.getTextureImageHeight();
		
		attributes[10] = normalWidth/2;
		attributes[11] = -normalHeight/2;
		attributes[12] = 0.0f;
		attributes[13] = textureSpriteDimensions[1]/controller2D.getTextureImageWidth();
		attributes[14] = textureSpriteDimensions[3]/controller2D.getTextureImageHeight();
		
		attributes[15] = normalWidth/2;
		attributes[16] = normalHeight/2;
		attributes[17] = 0.0f;
		attributes[18] = textureSpriteDimensions[1]/controller2D.getTextureImageWidth();
		attributes[19] = textureSpriteDimensions[2]/controller2D.getTextureImageHeight();
		//******************************************************************

	}
	
	//This method is for describing width and height independent of the texture 
	public Model2D(float normalWidth, float normalHeight, float texturePixelLeft, float texturePixelTop, float texturePixelBottom, Controller2D controller2D){
		
		normalWidth *= Main.getAspectRatio();
		
		float texturePixelWidth = texturePixelBottom - texturePixelTop;
		texturePixelWidth *= Main.getAspectRatio();
		float texturePixelRight = texturePixelLeft + texturePixelWidth;
		
		highX = (normalWidth/2);
		lowX = -(normalWidth/2);
		highY = (normalHeight/2);
		lowY = -(normalHeight/2);
		
		attributes = new float[20];
		
		type = TYPE_T;
		
		//Attributes are recorded in array of float size 20
			//Organization is x,y,z,textureX,textureY
			//Vertex order is top-left, bottom-left, bottom-right, top-right
		//******************************************************************
		//Width and height are divided by two in order to reach preferred side of model
		//normalWidth is multiplied with aspectRatio so the model will scale correctly across multiple devices
		attributes[0] = -normalWidth/2;
		attributes[1] = normalHeight/2;
		//Z value is always 0 because the images have no depth
		attributes[2] = 0.0f;
		//textureSpriteDimensions values are divided by textureImage values to obtain normalized values (between 0 and 1)
		attributes[3] = texturePixelLeft/controller2D.getTextureImageWidth();
		attributes[4] = texturePixelTop/controller2D.getTextureImageHeight();
		
		attributes[5] = -normalWidth/2;
		attributes[6] = -normalHeight/2;
		attributes[7] = 0.0f;
		attributes[8] = texturePixelLeft/controller2D.getTextureImageWidth();
		attributes[9] = texturePixelBottom/controller2D.getTextureImageHeight();
		
		attributes[10] = normalWidth/2;
		attributes[11] = -normalHeight/2;
		attributes[12] = 0.0f;
		attributes[13] = texturePixelRight/controller2D.getTextureImageWidth();
		attributes[14] = texturePixelBottom/controller2D.getTextureImageHeight();
		
		attributes[15] = normalWidth/2;
		attributes[16] = normalHeight/2;
		attributes[17] = 0.0f;
		attributes[18] = texturePixelRight/controller2D.getTextureImageWidth();
		attributes[19] = texturePixelTop/controller2D.getTextureImageHeight();
		//******************************************************************

	}
	
	//This function is for describing a model that uses colors instead of texture
	public Model2D(float normalWidth, float normalHeight, float red, float green, float blue, float alpha){
		
		normalWidth *= Main.getAspectRatio();
				
		highX = (normalWidth/2);
		lowX = -(normalWidth/2);
		highY = (normalHeight/2);
		lowY = -(normalHeight/2);
		
		attributes = new float[28];
		
		
		type = TYPE_C;
		
		//Attributes are recorded in array of float size 20
			//Organization is x,y,z,textureX,textureY
			//Vertex order is top-left, bottom-left, bottom-right, top-right
		//******************************************************************
		//Width and height are divided by two in order to reach preferred side of model
		//normalWidth is multiplied with aspectRatio so the model will scale correctly across multiple devices
		attributes[0] = -normalWidth/2;
		attributes[1] = normalHeight/2;
		//Z value is always 0 because the images have no depth
		attributes[2] = 0.0f;
		//Colors are divided by 255 to obtain normalized value
		attributes[3] = red/255f;
		attributes[4] = green/255f;
		attributes[5] = blue/255f;
		attributes[6] = alpha; 
		
		attributes[7] = -normalWidth/2;
		attributes[8] = -normalHeight/2;
		attributes[9] = 0.0f;
		attributes[10] = red/255f;
		attributes[11] = green/255f;
		attributes[12] = blue/255f;
		attributes[13] = alpha; 
		
		attributes[14] = normalWidth/2;
		attributes[15] = -normalHeight/2;
		attributes[16] = 0.0f;
		attributes[17] = red/255f;
		attributes[18] = green/255f;
		attributes[19] = blue/255f;
		attributes[20] = alpha; 
		
		attributes[21] = normalWidth/2;
		attributes[22] = normalHeight/2;
		attributes[23] = 0.0f;
		attributes[24] = red/255f;
		attributes[25] = green/255f;
		attributes[26] = blue/255f;
		attributes[27] = alpha;
		
	}
	
	public float[] getAttributes(){
		return attributes;
	}
}
