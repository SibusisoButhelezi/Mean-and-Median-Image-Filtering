import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Utilities{
	public static BufferedImage readInImage(BufferedImage img, int width, int height){
		try{
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			img = ImageIO.read(new File("/home/sibusiso/Desktop/CSC2002S/Assignment 1/CSC2002S_Assignment_1/src/Puppy.jpeg"));
			System.out.println("File read.");
			return img;
		}
		catch(IOException e){
			System.out.println("Error reading: " + e);
			return null;
		}
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
}