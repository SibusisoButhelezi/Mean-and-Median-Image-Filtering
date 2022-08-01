import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Utilities{
	public static BufferedImage readInImage(BufferedImage img){
		try{
			img = ImageIO.read(new File("/home/sibusiso/Desktop/CSC2002S/Assignment 1/CSC2002S_Assignment_1/src/Puppy.jpeg"));
			System.out.println("File read.");
		}
		catch(IOException e){
			System.out.println("Error reading: " + e);
			return null;
		}

		int width = img.getWidth();
		int height = img.getHeight();

		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public static void writeImage(BufferedImage img){
		try{
			ImageIO.write(img, "jpeg", new File("/home/sibusiso/Desktop/CSC2002S/Assignment 1/CSC2002S_Assignment_1/src/PuppyOut.jpeg"));
			System.out.println("File written");
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
}