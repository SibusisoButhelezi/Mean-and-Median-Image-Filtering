import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Utilities{
	public static BufferedImage readImage(BufferedImage img, String imageName){
		try{

			img = ImageIO.read(new File("/home/sibusiso/Desktop/CSC2002S/Assignment 1/CSC2002S_Assignment_1/src/" + imageName));
			int width = img.getWidth();
			int height = img.getHeight();
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			img = ImageIO.read(new File("/home/sibusiso/Desktop/CSC2002S/Assignment 1/CSC2002S_Assignment_1/src/" + imageName));
			System.out.println("Image read.");

			return img;
		}
		catch(IOException e){
			System.out.println("Error reading: " + e);
		}
		return null;
	}

	public static void writeImage(BufferedImage img, String imageName){
		try{ 
			ImageIO.write(img, "jpg", new File("/home/sibusiso/Desktop/CSC2002S/Assignment 1/CSC2002S_Assignment_1/src/" + imageName));
			System.out.println("Image written");
		}
		catch(IOException e){
			System.out.println("Error writing: " + e);
		}
	}

	public static int[] pixelValues(int p){
		int[] values = new int[4];

		values[0] = (p>>24) & 0xff;
		values[1] = (p>>16) & 0xff;
		values[2] = (p>>8) & 0xff;
		values[3] = p & 0xff;

		return values;
	}

	/*  setPixelValues takes in an array containing ARGB values (indices 0 through 3, respectively)
	*	and sets the pixel values into an int p.
	*/

	public static int setPixelValues(int[] values){
		int p = (values[0]<<24) | (values[1]<<16) | (values[2]<<8) | values[3];
		return p;
	}


	/*  avgPixelValues takes in a variable number of arrays as arguements
	*	From each array the ARGB (indices 0 through 3, respectively) values will be summed up
	*	and stored in an valuesAvg array of the same size as the others.
	*	Thereafter, each value in the valuesAvg array will be divided by the number of arrays
	* 	passed into the method, resulting in the array containing averages of the values.
	*/

	public static int[] avgPixelValues(int[]... Values){
		int[] valuesAvg = new int[4]; // this will store all the average ARGB values.

		// sum up each ARGB value from the arrays passed into the function.
		for (int[] values: Values){
			for (int i = 0; i < values.length; i++)
				valuesAvg[i] += values[i];
		}

		// get averages of each ARGB values.
		for (int i = 0; i < valuesAvg.length; i++)
			valuesAvg[i] = valuesAvg[i]/Values.length;

		return valuesAvg;

	}

	public static int[] medPixelValues(int[]... Values){
		int[] valuesMed = new int[4];

		int[] Avalues = new int[Values.length];
		int[] Rvalues = new int[Values.length];
		int[] Gvalues = new int[Values.length];
		int[] Bvalues = new int[Values.length];
		int ind = 0;

		for (int[] pixel : Values){
			Avalues[ind] = pixel[0];
			Rvalues[ind] = pixel[1];
			Gvalues[ind] = pixel[2];
			Bvalues[ind++] = pixel[3];
		}
		
		valuesMed[0] = getMedian(Avalues);
		valuesMed[1] = getMedian(Rvalues);
		valuesMed[2] = getMedian(Gvalues);
		valuesMed[3] = getMedian(Bvalues);	

		return valuesMed;		
	}

	public static int getMedian(int[] array){
		Arrays.sort(array);
		if (array.length % 2 == 1)
			return array[array.length/2];
		return (array[array.length/2] + array[(array.length/2) - 1])/2;
	}

	public static int[] getBorders(BufferedImage img, int dimension){
		int[] borders = new int[4];
		borders[0] = dimension/2; //top
		borders[1] = img.getWidth() - dimension/2; //right
		borders[2] = img.getHeight() - dimension/2; //bottom
		borders[3] = dimension/2; //left
		return borders;
	}
}