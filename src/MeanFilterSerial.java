import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class MeanFilterSerial{

	public static void main(String[] args){

		BufferedImage img = null;
		img = Utilities.readInImage(img);
		Utilities.writeImage(img);

		int[] test1 = {5,2,21,50};
		int[] test2 = {6,4,43,100};
		int[] test3 = {7,6,65,150};
		int[] test4 = {8,8,87,200};
		int[] test5 = {9,10,109,250};

		int[] test = Utilities.avgPixelValues(test1, test2, test3, test4, test5);
		for (int x : test)
			System.out.print(" " + x);


	}

	
}