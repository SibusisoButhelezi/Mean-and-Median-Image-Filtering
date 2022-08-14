import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MeanFilterParallel extends RecursiveAction{

	protected int Threshold = 120;
	protected static BufferedImage img = null, newImg = null;
	protected int dimension, start, right, end, left, radius;

	public MeanFilterParallel(BufferedImage img, BufferedImage newImg, int dimension, int topBorder, int rightBorder, int bottomBorder, int leftBorder, int radius){
		this.img = img;
		this.newImg = newImg;
		this.dimension = dimension;
		start = topBorder;
		right = rightBorder;
		end = bottomBorder;
		left = leftBorder;
		this.radius = radius;
	}

	protected void compute(){
		// Check if the height of the image satisfies the threshold, if it does, filter the area of the image
		if (end - start <= Threshold){
			newImg = filter();
			return;
		}

		// Create a new thread

		int newBottom = (start + end) / 2; // Split the height in 2
		MeanFilterParallel leftFork = new MeanFilterParallel(img, newImg, dimension, start, right, newBottom, left, radius);
		MeanFilterParallel rightFork = new MeanFilterParallel(img, newImg, dimension, newBottom, right, end, left, radius);
		leftFork.fork();
		rightFork.compute();
		leftFork.join();
	}

	public BufferedImage filter(){
		int pixelsIW = dimension*dimension; // pixels in window

		// Access each pixel in the image
		for (int y = start; y < end; y++){
			for(int x = left; x < right; x++){

				int Atotal = 0, Rtotal = 0, Gtotal = 0, Btotal = 0;
				int stopJ = y + radius, stopI = x + radius;

				// Get the surrounding pixels of the particular (x,y) pixel, within the winodow
				for (int j = y - radius; j <= stopJ; j++){
					for (int i = x - radius; i <= stopI; i++){
						int p = img.getRGB(i,j);	// Get the pixel value for each
						// For each pixel, get the ARGB values and add them.
						Atotal += (p>>24) & 0xff;
						Rtotal += (p>>16) & 0xff;
						Gtotal += (p>>8) & 0xff;
						Btotal += p & 0xff;
					}
				}
				
				// obtain the mean of each ARGB values of the pixels
				int meanA = Atotal/pixelsIW;  
				int meanR = Rtotal/pixelsIW;  
				int meanG = Gtotal/pixelsIW;
				int meanB = Btotal/pixelsIW; 

				// Set the new ARGB values into a new pixel value
				int p = (meanA << 24) | (meanR << 16) | (meanG << 8) | meanB;

				newImg.setRGB(x, y, p); // set the new pixel value on the writing image.
			}
		}

		return newImg;
	}		

	public static void main(String[] args){

		// Store parameters from command line.
		String imageName = args[0];
		String outputName = args[1];
		int dimension = Integer.parseInt(args[2]);

		// Checks if window width is an odd number >= 3
		// exits otherwise
		if (dimension % 2 == 0 || dimension < 3){
			System.out.println("Invalid window width.");
			System.exit(0);
		}

		// Create 2 BufferedImage object and read in the input image twice
		// One will be used for reading, the other for writing.
		BufferedImage img = null, newImg = null;

		// Read in the immage
		try{

			img = ImageIO.read(new File("bin/Pictures/" + imageName));
			int width = img.getWidth();
			int height = img.getHeight();
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			img = ImageIO.read(new File("bin/Pictures/" + imageName));
			newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			newImg = ImageIO.read(new File("bin/Pictures/" + imageName));
		}
		catch(IOException e){
			System.out.println("Error reading: " + e);
			System.exit(0);
		}

		System.out.println("Image read.");


		int topBorder = dimension/2; //top
		int rightBorder = img.getWidth() - dimension/2; //right
		int bottomBorder = img.getHeight() - dimension/2; //bottom
		int leftBorder = dimension/2; //left
		
		int radius = dimension/2; // This is the distance from the middle pixel to the edge of the window

		MeanFilterParallel meanFilter = new MeanFilterParallel(img, newImg, dimension, topBorder, rightBorder, bottomBorder, leftBorder, radius);
		ForkJoinPool pool = new ForkJoinPool(); // create a new ForkJoinPool

		final long startTime = System.nanoTime(); // start time for filtering

		pool.invoke(meanFilter); // begin the filter process

		final long endTime = System.nanoTime(); // end time for filtering

		System.out.println("Done Processing.");

		double time = (endTime - startTime) / 1000000000.0; // calculate the running time for the filtering and convert to seconds
		System.out.println("Filter time =");
		System.out.println("	" + time + "s.");
		System.out.println("	" + 1000 * time + "ms."); // Print out filtering time in milliseconds.

		// Write the image
		try{ 
			ImageIO.write(newImg, "jpg", new File("bin/Pictures/" + outputName));
			System.out.println("Image written");
		}
		catch(IOException e){
			System.out.println("Error writing: " + e);
			System.exit(0);
		}
	}

}