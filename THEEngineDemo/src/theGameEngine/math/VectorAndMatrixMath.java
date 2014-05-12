package theGameEngine.math;

public class VectorAndMatrixMath {
	
	public static float[] normalBackup = new float[9];
	public static float[] transpose = new float[9];
	private static float length;
	
	public static void normalize(float[] vector){
		length = dotProduct(vector,vector);
		length = (float) Math.sqrt(length);
		if (length == 0)
			return;
		for (int i=0; i<vector.length; i++){
			vector[i] /= length;
		}
	}
	
	public static void calculateDirectionVector(float[] start, float[] end, float[] result){
		for (int i=0; i<start.length; i++)
			result[i] = end[i] - start[i];
	}
	
	public static void calculateNormalizedDirectionVector(float[] start, float[] end, float[] result){
		for (int i=0; i<start.length; i++)
			result[i] = end[i] - start[i];
		normalize(result);
	}
	
	public static float dotProduct(float[] point1, float[] point2){
        float dot = 0;
        for (int i=0;i<point1.length;i++)
        	dot += point1[i]*point2[i];
        return dot;
	}
	
	public static float dotProduct2D(float screenX1, float screenY1, float screenX2, float screenY2){
        float dot = 0;
        dot += screenX1*screenX2;
        dot += screenY1*screenY2;
        return dot;
	}
	
	public static void crossProduct(float[] point1, float[] point2, float[] result){
        result[0] = point1[1]*point2[2]-point1[2]*point2[1];
        result[1] = point1[2]*point2[0]-point1[0]*point2[2];
        result[2] = point1[0]*point2[1]-point1[1]*point2[0];
	}
	
	public static void subtractPoints(float[] point1, float[] point2, float[] result){
		result[0] = point1[0] - point2[0];
		result[1] = point1[1] - point2[1];
		result[2] = point1[2] - point2[2];
	}
	
	
	public static boolean inverseAndTranspose3x3(float[] input, float[] output){
		float det = (input[0]*((input[5]*input[10])-(input[6]*input[9])))-(input[4]*((input[1]*input[10])-(input[2]*input[9])))+(input[8]*((input[1]*input[6])-(input[2]*input[5])));
		if (det == 0)
			return false;
		
		transpose[0] = input[0];
		transpose[1] = input[4];
		transpose[2] = input[8];
		transpose[3] = input[1];
		transpose[4] = input[5];
		transpose[5] = input[9];
		transpose[6] = input[2];
		transpose[7] = input[6];
		transpose[8] = input[10];
		
		normalBackup[0] = ((transpose[4]*transpose[8])-(transpose[5]*transpose[7]))/det;
		normalBackup[1] = -((transpose[3]*transpose[8])-(transpose[5]*transpose[6]))/det;
		normalBackup[2] = ((transpose[3]*transpose[7])-(transpose[4]*transpose[6]))/det;
		normalBackup[3] = -((transpose[1]*transpose[8])-(transpose[2]*transpose[7]))/det;
		normalBackup[4] = ((transpose[0]*transpose[8])-(transpose[2]*transpose[6]))/det;
		normalBackup[5] = -((transpose[0]*transpose[7])-(transpose[1]*transpose[6]))/det;
		normalBackup[6] = ((transpose[1]*transpose[5])-(transpose[2]*transpose[4]))/det;
		normalBackup[7] = -((transpose[0]*transpose[5])-(transpose[2]*transpose[3]))/det;
		normalBackup[8] = ((transpose[0]*transpose[4])-(transpose[1]*transpose[3]))/det;
		
		output[0] = normalBackup[0];
		output[1] = normalBackup[3];
		output[2] = normalBackup[6];
		output[3] = normalBackup[1];
		output[4] = normalBackup[4];
		output[5] = normalBackup[7];
		output[6] = normalBackup[2];
		output[7] = normalBackup[5];
		output[8] = normalBackup[8];
		return true;
	}
}
