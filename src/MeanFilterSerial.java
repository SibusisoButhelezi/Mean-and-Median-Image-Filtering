import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class MeanFilterSerial{

	public static void main(String[] args){
		
		int width = 474;
		int height = 316;

		BufferedImage img = null;
		img = Utilities.readInImage(img, width, height);
		// try{			
		// 	file = new File("/home/sibusiso/Desktop/CSC2002S/Assignment 1/CSC2002S_Assignment_1/src/Puppy.jpeg");
		// 	img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// 	img = ImageIO.read(file);
		// 	System.out.println("File read.");
		// }
		// catch(IOException e){
		// 	System.out.println("Error reading: " + e);
		// }
		Utilities.writeImage(img);
	}

	
}