package theGameEngine.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import theGameEngine.main.Main;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.opengl.GLES20;

public class Model3D extends Model
{
	
	protected float[] highXPoint = new float[3];
	protected float[] lowXPoint = new float[3];
	protected float[] highYPoint = new float[3];
	protected float[] lowYPoint = new float[3];
	protected float[] highZPoint = new float[3];
	protected float[] lowZPoint = new float[3];
	
	
	StringBuilder attributesBuilder = new StringBuilder();
	StringBuilder indicesBuilder = new StringBuilder();
	
	
	protected int[] verticesBuffer = new int[1];
	protected int[] indicesBuffer = new int[1];
	
	protected FloatBuffer verticesByteBuffer;
	protected ShortBuffer indicesByteBuffer;
	
	int indexIncrementValue = 0;
	
	private Bitmap bitmap;
	private boolean hasTexture = false;
	private int textureValue = GLES20.GL_TEXTURE2;
	
	float totalDataCount = 0;
		
	public Model3D(String filename, AssetManager assets)
	{
		int elementVertexCount = 0;
		int elementFaceCount = 0;
		
		BufferedReader bufferedReader;
		
		try
		{
			bufferedReader = new BufferedReader(new InputStreamReader(assets.open(filename)));
				
			//Initiate a string to read from the buffered reader line-by-line
			String bufferedLine = "";
			//Initiate a String array used to parse through the line
			String[] bufferedLineParser = null;
			
			//Collect information for the header
				//Does not need to be put in builder because all necessary information will be recorded here
			boolean inHeader = true;
			while(inHeader)
			{
				bufferedLine = bufferedReader.readLine();			
				bufferedLineParser = bufferedLine.split(" ");
				
				if (bufferedLineParser[0].equals("property"))
				{
					if (bufferedLineParser[2].equals("nx"))
						type = Model.TYPE_N;
					if (bufferedLineParser[2].equals("s"))
					{
						if (type == Model.TYPE_N)
							type = Model.TYPE_NT;
						else type = Model.TYPE_T;
						hasTexture = true;
					}
					if (bufferedLineParser[2].equals("red"))
					{
						if (type == Model.TYPE_N)
							type = Model.TYPE_NC;
						else type = Model.TYPE_C;
					}
				}
				else if (bufferedLineParser[0].equals("element") && bufferedLineParser[1].equals("vertex"))
					elementVertexCount = Integer.parseInt(bufferedLineParser[2]);
				else if (bufferedLineParser[0].equals("element") && bufferedLineParser[1].equals("face"))
				{
					elementFaceCount = Integer.parseInt(bufferedLineParser[2]);
					indices = new short[elementFaceCount * 3];
				}
				else if (bufferedLineParser[0].equals("end_header"))
					inHeader = false;
			}
			//Collect information for the attributes
			for (int i=0; i<elementVertexCount; i++)
			{
				bufferedLine = bufferedReader.readLine();
				attributesBuilder.append(bufferedLine);
				attributesBuilder.append("\n");
			}
			//Collect information for the indices
			for (int i=0; i<elementFaceCount; i++)
			{
				bufferedLine = bufferedReader.readLine();
				indicesBuilder.append(bufferedLine);
				indicesBuilder.append("\n");
			}
		}
		catch (IOException e){
			throw new RuntimeException("Failed to load object");
		}
			
		switch(type)
		{
		case(Model.TYPE_N):
			attributes = new float[elementVertexCount*6];
			indexIncrementValue = 6;
			break;
		case(Model.TYPE_T):
			attributes = new float[elementVertexCount*5];
			indexIncrementValue = 5;
			break;
		case(Model.TYPE_C):
			attributes = new float[elementVertexCount*7];
			indexIncrementValue = 7;
			break;
		case(Model.TYPE_NT):
			attributes = new float[elementVertexCount*8];
			indexIncrementValue = 8;
			break;
		case(Model.TYPE_NC):
			attributes = new float[elementVertexCount*10];
			indexIncrementValue = 10;
			break;
		}		
		
		int attributeCount = indexIncrementValue;
		if (type == Model.TYPE_C || type == Model.TYPE_NC)
			attributeCount--;
		totalDataCount = (elementVertexCount*attributeCount)+(elementFaceCount*3);
		
	}
	
	// Load model (position attribute only) from COLLADA .dae file
	public static Model3D newModel3DPoisitionDAE(String filename, AssetManager assets)
	{
		InputStream in = null;
		XmlPullParser parser = null;
		try
		{
			// Open stream to .dae file
			in = assets.open(filename);
			
			// Create new xml parser and set to InputStream
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setInput(in, null);
			
			// Start parser at first tag ("COLLADA")
			parser.next();
			
			// Parse through file until "library_geometries" tag is found
			while (parser.next() != XmlPullParser.END_DOCUMENT)
			{
				String name = parser.getName();
				
				if (name == null)
					continue;
				
				if (name.equals("library_geometries"))
					break;
			}
			int attributesCount = 0;
			
			// Initialize data structures for holding attributes
			String positionsString = null;
			float[] positionsArray = null;
			
			String indicesString = null;
			short[] indicesArray = null;
			
			// Parse through file for all attributes (only recording positions)
			while (parser.next() != XmlPullParser.END_DOCUMENT)
			{
				String name = parser.getName();
				
				if (name == null)
					continue;
				
				// Go until polylist (indices)
				if (name.equals("polylist"))
					break;
				
				else if (name.equals("float_array"))
				{
					if (parser.getAttributeValue(0).contains("positions"))
					{
						// Set size of positions array as indicated by "count" attribute in tag
						positionsArray = new float[Integer.parseInt(parser.getAttributeValue(1))];
						
						// Move parser to positions text
						parser.next();
						// Collect all position attribute values into string
						positionsString = parser.getText();
						
						// Increase attribute count
						++attributesCount;
					}
					else if (parser.getAttributeValue(0).contains("normals"))
					{
						attributesCount++;
					}
				}
			}
			
			// On tag "polylist", collect indices count
			indicesArray = new short[Short.parseShort(parser.getAttributeValue(0))];
			
			// Move to "p" tag that holds indices
			while (parser.next() != XmlPullParser.END_DOCUMENT)
			{
				String name = parser.getName();
				
				if (name == null)
					continue;
				
				if (name.equals("p"))
					break;
			}
			// Move parser to indices, collect
			parser.next();
			indicesString = parser.getText();
			
			// Parse through indicesText and store in indicesArray
			String[] indicesStringSplit = indicesString.split(" ");
			for (int i=0; i<indicesStringSplit.length; ++i)
			{
				indicesArray[i] = Short.parseShort(indicesStringSplit[i]);
			}
			
			// Parse through positionsText and store in positionsArray
			String[] positionsStringSplit = positionsString.split(" ");
			for (int i=0; i<positionsStringSplit.length; ++i)
			{
				positionsArray[i] = Float.parseFloat(positionsStringSplit[i]);
			}
			
			// Sort attributes into a packed array according to order in indicesArray
			float[] attributesArraySorted = new float[indicesArray.length*attributesCount];
			for (int i=0; i<attributesArraySorted.length; ++i)
			{
				
			}
			
			
			String name = parser.getName();
		}
		catch (XmlPullParserException x)
		{
			x.printStackTrace();
		}
		catch (IOException e)
		{
			throw new RuntimeException("Failed to load file " + filename);
		}
		
		return null;
	}
		
	public void collectAttributes()
	{
		String dataString = attributesBuilder.toString();
		String[] linesArray = dataString.split("\n");
		String[] lineParser;
		
		int index = 0;
		int indexBeforeParse = 0;
		
		float currentX = 0;
		float currentY = 0;
		float currentZ = 0;
		for (int i=0; i<linesArray.length; i++)
		{
			// Split line by any whitespace
			lineParser = linesArray[i].split("\\s");
			
			index = indexBeforeParse;

			
			//=======================================================
			// Parse through positions and collect information
			
			currentX = Float.parseFloat(lineParser[0]);
			Main.incrementDataCount();
			currentY = Float.parseFloat(lineParser[1]);
			Main.incrementDataCount();
			currentZ = Float.parseFloat(lineParser[2]);
			Main.incrementDataCount();
			
			if (currentX > highX)
			{
				highX = currentX;
				highXPoint[0] = currentX;
				highXPoint[1] = currentY;
				highXPoint[2] = currentZ;
			}
			if (currentX < lowX)
			{
				lowX = currentX;
				lowXPoint[0] = currentX;
				lowXPoint[1] = currentY;
				lowXPoint[2] = currentZ;
			}
			if (currentY > highY)
			{
				highY = currentY;
				highYPoint[0] = currentX;
				highYPoint[1] = currentY;
				highYPoint[2] = currentZ;
			}
			if (currentY < lowY)
			{
				lowY = currentY;
				lowYPoint[0] = currentX;
				lowYPoint[1] = currentY;
				lowYPoint[2] = currentZ;
			}
			if (currentZ > highZ)
			{
				highZ = currentZ;
				highZPoint[0] = currentX;
				highZPoint[1] = currentY;
				highZPoint[2] = currentZ;
			}
			if (currentZ < lowZ)
			{
				lowZ = currentZ;
				lowZPoint[0] = currentX;
				lowZPoint[1] = currentY;
				lowZPoint[2] = currentZ;
			}
	
			attributes[index++] = currentX;
			attributes[index++] = currentY;
			attributes[index++] = currentZ;
			//=======================================================
			
			
			//============================================================
			// Parse through other attributes
			
			for (int j=3; j<lineParser.length; j++)
			{
				attributes[index++] = Float.parseFloat(lineParser[j]);
				Main.incrementDataCount();
			}
			//============================================================
			
			
			indexBeforeParse += indexIncrementValue;
		}
		
		//=================================================
		// Normalize color values
		
		if (type == Model.TYPE_C)
		{
			for (int i=3; i<=attributes.length-1; i+=7)
			{
				attributes[i] /= 255;
				attributes[i+1] /= 255;
				attributes[i+2] /= 255;
				attributes[i+3] = 1;
			}
		}
		else if (type == Model.TYPE_NC)
		{
			for (int i=6; i<=attributes.length-1; i+=10)
			{
				attributes[i] /= 255;
				attributes[i+1] /= 255;
				attributes[i+2] /= 255;
				attributes[i+3] = 1;
			}
		}
		//=================================================
	}
	public void buildAttributesBuffer()
	{
		verticesByteBuffer = ByteBuffer.allocateDirect(attributes.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		verticesByteBuffer.put(attributes).position(0);
	}
	
	
	public void collectIndices()
	{
		String dataString = indicesBuilder.toString();
		String[] linesArray = dataString.split("\n");
		String[] lineParser;
		
		int index = 0;
		for (String line : linesArray)
		{
			lineParser = line.split("\\s");
			
			indices[index++] = Short.parseShort(lineParser[1]);
			Main.incrementDataCount();
			indices[index++] = Short.parseShort(lineParser[2]);
			Main.incrementDataCount();
			indices[index++] = Short.parseShort(lineParser[3]);
			Main.incrementDataCount();
		}
	}
	public void buildIndicesBuffer()
	{
		indicesByteBuffer = ByteBuffer.allocateDirect(indices.length * Short.SIZE / 8).order(ByteOrder.nativeOrder()).asShortBuffer();
		indicesByteBuffer.put(indices).position(0);
	}
	
	public void collectAndBuild()
	{
		collectAttributes();
		collectIndices();
		buildAttributesBuffer();
		buildIndicesBuffer();
	}
	
	public float[] getHighXPoint()
	{
		return highXPoint;
	}
	public float[] getLowXPoint()
	{
		return lowXPoint;
	}
	public float[] getHighYPoint()
	{
		return highYPoint;
	}
	public float[] getLowYPoint()
	{
		return lowYPoint;
	}
	public float[] getHighZPoint()
	{
		return highZPoint;
	}
	public float[] getLowZPoint()
	{
		return lowZPoint;
	}
	
	public Bitmap getBitmap()
	{
		return bitmap;
	}
	public boolean hasTexture()
	{
		return hasTexture;
	}
	public int getTextureValue()
	{
		return textureValue;
	}
	public int[] getVerticesBufferArray()
	{
		return verticesBuffer;
	}
	public int[] getIndicesBufferArray()
	{
		return indicesBuffer;
	}
	public int getVerticesBuffer()
	{
		return verticesBuffer[0];
	}
	public int getIndicesBuffer()
	{
		return indicesBuffer[0];
	}
	public FloatBuffer getVerticesFloatBuffer()
	{
		return verticesByteBuffer;
	}
	public ShortBuffer getIndicesShortBuffer()
	{
		return indicesByteBuffer;
	}
	public int getIndicesCount()
	{
		return indices.length;
	}
	public float getTotalDataCount()
	{
		return totalDataCount;
	}
	

}
