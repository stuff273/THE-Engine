package theGameEngine.text;

import theGameEngine.controllers.GUIs.GUI;
import theGameEngine.models.Model2D;

public class Text {
	
	private float largestLetterWidth = 0;
	private float largestLetterHeight = 0;
	private float largestLetterWidthStart = 0;
	private float largestLetterHeightStart = 0;
	
	public Model2D[] letterModels = new Model2D[94];
	
	public Text(GUI gui){
	
		float[] spriteDimensions = new float[4];
		
		//a
		spriteDimensions[0] = 1;
		spriteDimensions[1] = 15;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 33;
		letterModels[0] = new Model2D(spriteDimensions, gui);
		
		//b
		spriteDimensions[0] = 17;
		spriteDimensions[1] = 31;
		spriteDimensions[2] = 15;
		spriteDimensions[3] = 33;
		letterModels[1] = new Model2D(spriteDimensions, gui);
		
		//c
		spriteDimensions[0] = 33;
		spriteDimensions[1] = 47;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 33;
		letterModels[2] = new Model2D(spriteDimensions, gui);
		
		//d
		spriteDimensions[0] = 49;
		spriteDimensions[1] = 63;
		spriteDimensions[2] = 15;
		spriteDimensions[3] = 33;
		letterModels[3] = new Model2D(spriteDimensions, gui);
		
		//e
		spriteDimensions[0] = 65;
		spriteDimensions[1] = 79;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 33;
		letterModels[4] = new Model2D(spriteDimensions, gui);
		
		//f
		spriteDimensions[0] = 81;
		spriteDimensions[1] = 93;
		spriteDimensions[2] = 15;
		spriteDimensions[3] = 33;
		letterModels[5] = new Model2D(spriteDimensions, gui);
		
		//g
		spriteDimensions[0] = 95;
		spriteDimensions[1] = 109;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 37;
		letterModels[6] = new Model2D(spriteDimensions, gui);
		
		//h
		spriteDimensions[0] = 111;
		spriteDimensions[1] = 125;
		spriteDimensions[2] = 15;
		spriteDimensions[3] = 33;
		letterModels[7] = new Model2D(spriteDimensions, gui);
		
		//i
		spriteDimensions[0] = 127;
		spriteDimensions[1] = 134;
		spriteDimensions[2] = 15;
		spriteDimensions[3] = 33;
		letterModels[8] = new Model2D(spriteDimensions, gui);
		
		//j
		spriteDimensions[0] = 136;
		spriteDimensions[1] = 148;
		spriteDimensions[2] = 15;
		spriteDimensions[3] = 37;
		letterModels[9] = new Model2D(spriteDimensions, gui);
		
		//k
		spriteDimensions[0] = 150;
		spriteDimensions[1] = 164;
		spriteDimensions[2] = 15;
		spriteDimensions[3] = 33;
		letterModels[10] = new Model2D(spriteDimensions, gui);
		
		//l
		spriteDimensions[0] = 166;
		spriteDimensions[1] = 171;
		spriteDimensions[2] = 15;
		spriteDimensions[3] = 33;
		letterModels[11] = new Model2D(spriteDimensions, gui);
		
		//m
		spriteDimensions[0] = 173;
		spriteDimensions[1] = 191;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 33;
		letterModels[12] = new Model2D(spriteDimensions, gui);
		
		//n
		spriteDimensions[0] = 193;
		spriteDimensions[1] = 207;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 33;
		letterModels[13] = new Model2D(spriteDimensions, gui);
		
		//o
		spriteDimensions[0] = 209;
		spriteDimensions[1] = 221;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 33;
		letterModels[14] = new Model2D(spriteDimensions, gui);
		
		//p
		spriteDimensions[0] = 223;
		spriteDimensions[1] = 237;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 37;
		letterModels[15] = new Model2D(spriteDimensions, gui);
		
		//q
		spriteDimensions[0] = 239;
		spriteDimensions[1] = 253;
		spriteDimensions[2] = 22;
		spriteDimensions[3] = 37;
		letterModels[16] = new Model2D(spriteDimensions, gui);
		
		//r
		spriteDimensions[0] = 1;
		spriteDimensions[1] = 13;
		spriteDimensions[2] = 63;
		spriteDimensions[3] = 74;
		letterModels[17] = new Model2D(spriteDimensions, gui);
		
		//s
		spriteDimensions[0] = 15;
		spriteDimensions[1] = 29;
		spriteDimensions[2] = 63;
		spriteDimensions[3] = 74;
		letterModels[18] = new Model2D(spriteDimensions, gui);
		
		//t
		spriteDimensions[0] = 31;
		spriteDimensions[1] = 40;
		spriteDimensions[2] = 56;
		spriteDimensions[3] = 74;
		letterModels[19] = new Model2D(spriteDimensions, gui);
		
		//u
		spriteDimensions[0] = 42;
		spriteDimensions[1] = 56;
		spriteDimensions[2] = 63;
		spriteDimensions[3] = 74;
		letterModels[20] = new Model2D(spriteDimensions, gui);
		
		//v
		spriteDimensions[0] = 58;
		spriteDimensions[1] = 72;
		spriteDimensions[2] = 63;
		spriteDimensions[3] = 74;
		letterModels[21] = new Model2D(spriteDimensions, gui);
		
		//w
		spriteDimensions[0] = 74;
		spriteDimensions[1] = 97;
		spriteDimensions[2] = 63;
		spriteDimensions[3] = 74;
		letterModels[22] = new Model2D(spriteDimensions, gui);
		
		//x
		spriteDimensions[0] = 99;
		spriteDimensions[1] = 111;
		spriteDimensions[2] = 63;
		spriteDimensions[3] = 74;
		letterModels[23] = new Model2D(spriteDimensions, gui);
		
		//y
		spriteDimensions[0] = 113;
		spriteDimensions[1] = 127;
		spriteDimensions[2] = 63;
		spriteDimensions[3] = 78;
		letterModels[24] = new Model2D(spriteDimensions, gui);
		
		//z
		spriteDimensions[0] = 129;
		spriteDimensions[1] = 143;
		spriteDimensions[2] = 63;
		spriteDimensions[3] = 74;
		letterModels[25] = new Model2D(spriteDimensions, gui);
		
		//A
		spriteDimensions[0] = 145;
		spriteDimensions[1] = 163;
		spriteDimensions[2] = 54;
		spriteDimensions[3] = 74;
		letterModels[26] = new Model2D(spriteDimensions, gui);
		
		//B
		spriteDimensions[0] = 165;
		spriteDimensions[1] = 181;
		spriteDimensions[2] = 54;
		spriteDimensions[3] = 74;
		letterModels[27] = new Model2D(spriteDimensions, gui);
		
		//C
		spriteDimensions[0] = 183;
		spriteDimensions[1] = 197;
		spriteDimensions[2] = 54;
		spriteDimensions[3] = 74;
		letterModels[28] = new Model2D(spriteDimensions, gui);
		
		//D
		spriteDimensions[0] = 199;
		spriteDimensions[1] = 215;
		spriteDimensions[2] = 54;
		spriteDimensions[3] = 74;
		letterModels[29] = new Model2D(spriteDimensions, gui);
		
		//E
		spriteDimensions[0] = 217;
		spriteDimensions[1] = 233;
		spriteDimensions[2] = 54;
		spriteDimensions[3] = 74;
		letterModels[30] = new Model2D(spriteDimensions, gui);
		
		//F
		spriteDimensions[0] = 235;
		spriteDimensions[1] = 249;
		spriteDimensions[2] = 54;
		spriteDimensions[3] = 74;
		letterModels[31] = new Model2D(spriteDimensions, gui);
		
		//G
		spriteDimensions[0] = 1;
		spriteDimensions[1] = 19;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[32] = new Model2D(spriteDimensions, gui);
		
		//H
		spriteDimensions[0] = 21;
		spriteDimensions[1] = 37;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[33] = new Model2D(spriteDimensions, gui);
		
		//I
		spriteDimensions[0] = 39;
		spriteDimensions[1] = 48;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[34] = new Model2D(spriteDimensions, gui);
		
		//J
		spriteDimensions[0] = 50;
		spriteDimensions[1] = 64;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[35] = new Model2D(spriteDimensions, gui);
		
		//K
		spriteDimensions[0] = 66;
		spriteDimensions[1] = 82;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[36] = new Model2D(spriteDimensions, gui);
		
		//L
		spriteDimensions[0] = 84;
		spriteDimensions[1] = 98;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[37] = new Model2D(spriteDimensions, gui);
		
		//M
		spriteDimensions[0] = 100;
		spriteDimensions[1] = 121;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[38] = new Model2D(spriteDimensions, gui);
		
		//N
		spriteDimensions[0] = 123;
		spriteDimensions[1] = 141;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[39] = new Model2D(spriteDimensions, gui);
		
		//O
		spriteDimensions[0] = 143;
		spriteDimensions[1] = 161;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[40] = new Model2D(spriteDimensions, gui);
		
		//P
		spriteDimensions[0] = 163;
		spriteDimensions[1] = 179;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[41] = new Model2D(spriteDimensions, gui);
		
		//Q
		spriteDimensions[0] = 181;
		spriteDimensions[1] = 199;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[42] = new Model2D(spriteDimensions, gui);
		
		//R
		spriteDimensions[0] = 201;
		spriteDimensions[1] = 217;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[43] = new Model2D(spriteDimensions, gui);
		
		//S
		spriteDimensions[0] = 219;
		spriteDimensions[1] = 233;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[44] = new Model2D(spriteDimensions, gui);
		
		//T
		spriteDimensions[0] = 235;
		spriteDimensions[1] = 249;
		spriteDimensions[2] = 95;
		spriteDimensions[3] = 115;
		letterModels[45] = new Model2D(spriteDimensions, gui);
		
		//U
		spriteDimensions[0] = 1;
		spriteDimensions[1] = 17;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[46] = new Model2D(spriteDimensions, gui);
		
		//V
		spriteDimensions[0] = 19;
		spriteDimensions[1] = 37;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[47] = new Model2D(spriteDimensions, gui);
		
		//W
		spriteDimensions[0] = 39;
		spriteDimensions[1] = 69;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[48] = new Model2D(spriteDimensions, gui);
		
		//X
		spriteDimensions[0] = 71;
		spriteDimensions[1] = 87;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[49] = new Model2D(spriteDimensions, gui);
		
		//Y
		spriteDimensions[0] = 89;
		spriteDimensions[1] = 107;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[50] = new Model2D(spriteDimensions, gui);
		
		//Z
		spriteDimensions[0] = 109;
		spriteDimensions[1] = 130;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[51] = new Model2D(spriteDimensions, gui);
		
		//1
		spriteDimensions[0] = 132;
		spriteDimensions[1] = 141;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[52] = new Model2D(spriteDimensions, gui);
		
		//2
		spriteDimensions[0] = 143;
		spriteDimensions[1] = 157;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[53] = new Model2D(spriteDimensions, gui);
		
		//3
		spriteDimensions[0] = 159;
		spriteDimensions[1] = 173;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[54] = new Model2D(spriteDimensions, gui);
		
		//4
		spriteDimensions[0] = 175;
		spriteDimensions[1] = 189;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[55] = new Model2D(spriteDimensions, gui);
		
		//5
		spriteDimensions[0] = 191;
		spriteDimensions[1] = 205;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[56] = new Model2D(spriteDimensions, gui);
		
		//6
		spriteDimensions[0] = 207;
		spriteDimensions[1] = 221;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[57] = new Model2D(spriteDimensions, gui);
		
		//7
		spriteDimensions[0] = 223;
		spriteDimensions[1] = 237;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[58] = new Model2D(spriteDimensions, gui);
		
		//8
		spriteDimensions[0] = 239;
		spriteDimensions[1] = 253;
		spriteDimensions[2] = 136;
		spriteDimensions[3] = 156;
		letterModels[59] = new Model2D(spriteDimensions, gui);
		
		//9
		spriteDimensions[0] = 1;
		spriteDimensions[1] = 15;
		spriteDimensions[2] = 177;
		spriteDimensions[3] = 197;
		letterModels[60] = new Model2D(spriteDimensions, gui);
		
		//0
		spriteDimensions[0] = 17;
		spriteDimensions[1] = 31;
		spriteDimensions[2] = 177;
		spriteDimensions[3] = 197;
		letterModels[61] = new Model2D(spriteDimensions, gui);
		
		//$
		spriteDimensions[0] = 33;
		spriteDimensions[1] = 47;
		spriteDimensions[2] = 174;
		spriteDimensions[3] = 199;
		letterModels[62] = new Model2D(spriteDimensions, gui);
		
		//+
		spriteDimensions[0] = 49;
		spriteDimensions[1] = 63;
		spriteDimensions[2] = 183;
		spriteDimensions[3] = 195;
		letterModels[63] = new Model2D(spriteDimensions, gui);
		
		//-
		spriteDimensions[0] = 65;
		spriteDimensions[1] = 79;
		spriteDimensions[2] = 188;
		spriteDimensions[3] = 190;
		letterModels[64] = new Model2D(spriteDimensions, gui);
		
		//*
		spriteDimensions[0] = 81;
		spriteDimensions[1] = 88;
		spriteDimensions[2] = 170;
		spriteDimensions[3] = 177;
		letterModels[65] = new Model2D(spriteDimensions, gui);
		
		///
		spriteDimensions[0] = 90;
		spriteDimensions[1] = 99;
		spriteDimensions[2] = 177;
		spriteDimensions[3] = 197;
		letterModels[66] = new Model2D(spriteDimensions, gui);
		
		//÷
		spriteDimensions[0] = 101;
		spriteDimensions[1] = 113;
		spriteDimensions[2] = 183;
		spriteDimensions[3] = 195;
		letterModels[67] = new Model2D(spriteDimensions, gui);
		
		//=
		spriteDimensions[0] = 115;
		spriteDimensions[1] = 129;
		spriteDimensions[2] = 186;
		spriteDimensions[3] = 192;
		letterModels[68] = new Model2D(spriteDimensions, gui);
		
		//%
		spriteDimensions[0] = 134;
		spriteDimensions[1] = 149;
		spriteDimensions[2] = 179;
		spriteDimensions[3] = 197;
		letterModels[69] = new Model2D(spriteDimensions, gui);
		
		//"
		spriteDimensions[0] = 151;
		spriteDimensions[1] = 158;
		spriteDimensions[2] = 172;
		spriteDimensions[3] = 195;
		letterModels[70] = new Model2D(spriteDimensions, gui);
		
		//'
		spriteDimensions[0] = 160;
		spriteDimensions[1] = 163;
		spriteDimensions[2] = 172;
		spriteDimensions[3] = 195;
		letterModels[71] = new Model2D(spriteDimensions, gui);
		
		//#
		spriteDimensions[0] = 165;
		spriteDimensions[1] = 181;
		spriteDimensions[2] = 177;
		spriteDimensions[3] = 197;
		letterModels[72] = new Model2D(spriteDimensions, gui);
		
		//&
		spriteDimensions[0] = 183;
		spriteDimensions[1] = 199;
		spriteDimensions[2] = 177;
		spriteDimensions[3] = 199;
		letterModels[73] = new Model2D(spriteDimensions, gui);
		
		//_
		spriteDimensions[0] = 201;
		spriteDimensions[1] = 215;
		spriteDimensions[2] = 197;
		spriteDimensions[3] = 199;
		letterModels[74] = new Model2D(spriteDimensions, gui);
		
		//(
		spriteDimensions[0] = 1;
		spriteDimensions[1] = 8;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[75] = new Model2D(spriteDimensions, gui);
		
		//)
		spriteDimensions[0] = 10;
		spriteDimensions[1] = 17;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[76] = new Model2D(spriteDimensions, gui);
		
		//,
		spriteDimensions[0] = 19;
		spriteDimensions[1] = 22;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 240;
		letterModels[77] = new Model2D(spriteDimensions, gui);
		
		//.
		spriteDimensions[0] = 24;
		spriteDimensions[1] = 27;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[78] = new Model2D(spriteDimensions, gui);
		
		//;
		spriteDimensions[0] = 29;
		spriteDimensions[1] = 32;
		spriteDimensions[2] = 227;
		spriteDimensions[3] = 240;
		letterModels[79] = new Model2D(spriteDimensions, gui);
		
		//:
		spriteDimensions[0] = 34;
		spriteDimensions[1] = 37;
		spriteDimensions[2] = 227;
		spriteDimensions[3] = 238;
		letterModels[80] = new Model2D(spriteDimensions, gui);
		
		//?
		spriteDimensions[0] = 39;
		spriteDimensions[1] = 53;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[81] = new Model2D(spriteDimensions, gui);
		
		//!
		spriteDimensions[0] = 55;
		spriteDimensions[1] = 60;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[82] = new Model2D(spriteDimensions, gui);
		
		//\
		spriteDimensions[0] = 62;
		spriteDimensions[1] = 71;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[83] = new Model2D(spriteDimensions, gui);
		
		//|
		spriteDimensions[0] = 73;
		spriteDimensions[1] = 78;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[84] = new Model2D(spriteDimensions, gui);
		
		//{
		spriteDimensions[0] = 80;
		spriteDimensions[1] = 89;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[85] = new Model2D(spriteDimensions, gui);
		
		//}
		spriteDimensions[0] = 91;
		spriteDimensions[1] = 100;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[86] = new Model2D(spriteDimensions, gui);
		
		//<
		spriteDimensions[0] = 102;
		spriteDimensions[1] = 114;
		spriteDimensions[2] = 224;
		spriteDimensions[3] = 236;
		letterModels[87] = new Model2D(spriteDimensions, gui);
		
		//>
		spriteDimensions[0] = 116;
		spriteDimensions[1] = 128;
		spriteDimensions[2] = 227;
		spriteDimensions[3] = 237;
		letterModels[88] = new Model2D(spriteDimensions, gui);
		
		//[
		spriteDimensions[0] = 130;
		spriteDimensions[1] = 137;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[89] = new Model2D(spriteDimensions, gui);
		
		//]
		spriteDimensions[0] = 139;
		spriteDimensions[1] = 146;
		spriteDimensions[2] = 218;
		spriteDimensions[3] = 238;
		letterModels[90] = new Model2D(spriteDimensions, gui);
		
		//`
		spriteDimensions[0] = 148;
		spriteDimensions[1] = 151;
		spriteDimensions[2] = 215;
		spriteDimensions[3] = 238;
		letterModels[91] = new Model2D(spriteDimensions, gui);
		
		//^
		spriteDimensions[0] = 153;
		spriteDimensions[1] = 165;
		spriteDimensions[2] = 211;
		spriteDimensions[3] = 238;
		letterModels[92] = new Model2D(spriteDimensions, gui);
		
		//~
		spriteDimensions[0] = 167;
		spriteDimensions[1] = 183;
		spriteDimensions[2] = 227;
		spriteDimensions[3] = 233;
		letterModels[93] = new Model2D(spriteDimensions, gui);
		
		for (int i=0; i<=51; i++){
			if (letterModels[i].getWidth() > largestLetterWidth)
				largestLetterWidth = largestLetterWidthStart = letterModels[i].getWidth();
			if (letterModels[i].getHeight() > largestLetterHeight)
				largestLetterHeight = largestLetterHeightStart = letterModels[i].getHeight();
		}
	}
	
	public void changeFontSize(float fontSize){
		largestLetterWidth = largestLetterWidthStart * fontSize;
		largestLetterHeight = largestLetterHeightStart * fontSize;
	}
	
	public float getLargestLetterWidth(){
		return largestLetterWidth;
	}
	public float getLargestLetterHeight(){
		return largestLetterHeight;
	}
	public float getLargestLetterWidthStart(){
		return largestLetterWidthStart;
	}
	public float getLargestLetterHeightStart(){
		return largestLetterHeightStart;
	}
	
	public Model2D getCharacterModel(char character){
		switch (character){
		
		case('a'):
			return letterModels[0];
		case('b'):
			return letterModels[1];
		case('c'):
			return letterModels[2];
		case('d'):
			return letterModels[3];
		case('e'):
			return letterModels[4];
		case('f'):
			return letterModels[5];
		case('g'):
			return letterModels[6];
		case('h'):
			return letterModels[7];
		case('i'):
			return letterModels[8];
		case('j'):
			return letterModels[9];
		case('k'):
			return letterModels[10];
		case('l'):
			return letterModels[11];
		case('m'):
			return letterModels[12];
		case('n'):
			return letterModels[13];
		case('o'):
			return letterModels[14];
		case('p'):
			return letterModels[15];
		case('q'):
			return letterModels[16];
		case('r'):
			return letterModels[17];
		case('s'):
			return letterModels[18];
		case('t'):
			return letterModels[19];
		case('u'):
			return letterModels[20];
		case('v'):
			return letterModels[21];
		case('w'):
			return letterModels[22];
		case('x'):
			return letterModels[23];
		case('y'):
			return letterModels[24];
		case('z'):
			return letterModels[25];
		case('A'):
			return letterModels[26];
		case('B'):
			return letterModels[27];
		case('C'):
			return letterModels[28];
		case('D'):
			return letterModels[29];
		case('E'):
			return letterModels[30];
		case('F'):
			return letterModels[31];
		case('G'):
			return letterModels[32];
		case('H'):
			return letterModels[33];
		case('I'):
			return letterModels[34];
		case('J'):
			return letterModels[35];
		case('K'):
			return letterModels[36];
		case('L'):
			return letterModels[37];
		case('M'):
			return letterModels[38];
		case('N'):
			return letterModels[39];
		case('O'):
			return letterModels[40];
		case('P'):
			return letterModels[41];
		case('Q'):
			return letterModels[42];
		case('R'):
			return letterModels[43];
		case('S'):
			return letterModels[44];
		case('T'):
			return letterModels[45];
		case('U'):
			return letterModels[46];
		case('V'):
			return letterModels[47];
		case('W'):
			return letterModels[48];
		case('X'):
			return letterModels[49];
		case('Y'):
			return letterModels[50];
		case('Z'):
			return letterModels[51];
		case('1'):
			return letterModels[52];
		case('2'):
			return letterModels[53];
		case('3'):
			return letterModels[54];
		case('4'):
			return letterModels[55];
		case('5'):
			return letterModels[56];
		case('6'):
			return letterModels[57];
		case('7'):
			return letterModels[58];
		case('8'):
			return letterModels[59];
		case('9'):
			return letterModels[60];
		case('0'):
			return letterModels[61];
		case('$'):
			return letterModels[62];
		case('+'):
			return letterModels[63];
		case('-'):
			return letterModels[64];
		case('*'):
			return letterModels[65];
		case('/'):
			return letterModels[66];
		case('÷'):
			return letterModels[67];
		case('='):
			return letterModels[68];
		case('%'):
			return letterModels[69];
		case('"'):
			return letterModels[70];
		case('\''):
			return letterModels[71];
		case('#'):
			return letterModels[72];
		case('&'):
			return letterModels[73];
		case('_'):
			return letterModels[74];
		case('('):
			return letterModels[75];
		case(')'):
			return letterModels[76];
		case(','):
			return letterModels[77];
		case('.'):
			return letterModels[78];
		case(';'):
			return letterModels[79];
		case(':'):
			return letterModels[80];
		case('?'):
			return letterModels[81];
		case('!'):
			return letterModels[82];
		case('\\'):
			return letterModels[83];
		case('|'):
			return letterModels[84];
		case('{'):
			return letterModels[85];
		case('}'):
			return letterModels[86];
		case('<'):
			return letterModels[87];
		case('>'):
			return letterModels[88];
		case('['):
			return letterModels[89];
		case(']'):
			return letterModels[90];
		case('`'):
			return letterModels[91];
		case('^'):
			return letterModels[92];
		case('~'):
			return letterModels[93];
		
		}
		
		return null;
	}
	
}
