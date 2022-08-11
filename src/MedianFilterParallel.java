import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MedianFilterParallel extends RecursiveAction{

	protected int Threshold = 50;
	protected static BufferedImage img = null, newImg = null;
	protected int dimension, start, right, end, left, radius;

	public MedianFilterParallel(BufferedImage img, BufferedImage newImg, int dimension, int topBorder, int rightBorder, int bottomBorder, int leftBorder, int radius){
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
		if (end - start <= Threshold){
			newImg = filter();
			return;
		}
		int newBottom = (start + end) / 2;
		MedianFilterParallel leftFork = new MedianFilterParallel(img, newImg, dimension, start, right, newBottom, left, radius);
		MedianFilterParallel rightFork = new MedianFilterParallel(img, newImg, dimension, newBottom, right, end, left, radius);
		leftFork.fork();
		rightFork.compute();
		leftFork.join();
	}

	public static void main(String[] args){

		String imageName = args[0];
		String outputName = args[1];
		int dimension = Integer.parseInt(args[2]);

		if (dimension % 2 == 0){
			System.out.println("Invalid window width.");
			System.exit(0);
		}

		img = readImage(img, imageName);
		newImg = readImage(newImg, imageName);


		int[] borders = getBorders(img, dimension);

		int radius = dimension/2;

		int topBorder = borders[0];
		int rightBorder = borders[1];
		int bottomBorder = borders[2];
		int leftBorder = borders[3];

		MedianFilterParallel medianFilter = new MedianFilterParallel(img, newImg, dimension, topBorder, rightBorder, bottomBorder, leftBorder, radius);
		ForkJoinPool pool = new ForkJoinPool();

		final long startTime = System.nanoTime();

		pool.invoke(medianFilter);

		final long endTime = System.nanoTime();

		long time = (endTime - startTime) / 1000000000;
		System.out.println("Filter time = " + time + "s.");

		System.out.println("Done Processing.");
		writeImage(newImg, outputName);
	}

	public BufferedImage filter(){
		int[][] pixels = new int[dimension*dimension][];
		int index = 0;

		for (int y = start; y < end; y++){
			for(int x = left; x < right; x++){

				int stopJ = y + radius, stopI = x + radius;

				for (int j = y - radius; j <= stopJ; j++){
					for (int i = x - radius; i <= stopI; i++){
						int p = img.getRGB(i,j);
						int[] pixelValues = pixelValues(p);
						pixels[index++] = pixelValues;
					}
				}
				
				int[] avgPixelValues = medPixelValues(pixels);
				int p = setPixelValues(avgPixelValues);
				newImg.setRGB(x, y, p);
				index = 0;
			}
		}

		return newImg;
	}

	public static BufferedImage readImage(BufferedImage img, String imageName){

		try{

			// img = ImageIO.read(new File("/home/sibusiso/Desktop/CSC2002S/Assignment 1/CSC2002S_Assignment_1/src/" + imageName));
			img = ImageIO.read(new File(imageName));
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

	public static int[] getBorders(BufferedImage img, int dimension){
		int[] borders = new int[4];
		borders[0] = dimension/2; //top
		borders[1] = img.getWidth() - dimension/2; //right
		borders[2] = img.getHeight() - dimension/2; //bottom
		borders[3] = dimension/2; //left
		return borders;
	}

	public static int[] pixelValues(int p){
		int[] values = new int[4];

		values[0] = (p>>24) & 0xff;
		values[1] = (p>>16) & 0xff;
		values[2] = (p>>8) & 0xff;
		values[3] = p & 0xff;

		return values;
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

	public static int setPixelValues(int[] values){
		int p = (values[0]<<24) | (values[1]<<16) | (values[2]<<8) | values[3];
		return p;
	}
	
}