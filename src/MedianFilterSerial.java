import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MedianFilterSerial{

	public static void main(String[] args){

		String imageName = args[0];
		String outputName = args[1];
		int dimension = Integer.parseInt(args[2]);

		if (dimension % 2 == 0){
			System.out.println("Invalid window width.");
			System.exit(0);
		}

		BufferedImage img = null, newImg = null;
		img = readImage(img, imageName);
		newImg = readImage(newImg, imageName);

		int[] borders = getBorders(img, dimension);

		int radius = dimension/2;

		int topBorder = borders[0];
		int rightBorder = borders[1];
		int bottomBorder = borders[2];
		int leftBorder = borders[3];


		final long startTime = System.nanoTime();

		newImg = filter(img, newImg, dimension, topBorder, rightBorder, bottomBorder, leftBorder, radius);

		final long endTime = System.nanoTime();

		long time = (endTime - startTime) / 1000000000;
		System.out.println("Filter time = " + time + "s.");

		System.out.println("Done Processing.");
		writeImage(newImg, outputName);
	}

	public static BufferedImage filter(BufferedImage img, BufferedImage newImg, int dimension, int topBorder, int rightBorder, int bottomBorder, int leftBorder, int radius){
		int[][] pixels = new int[dimension*dimension][];
		int index = 0;

		for (int y = topBorder; y < bottomBorder; y++){
			for(int x = leftBorder; x < rightBorder; x++){

				for (int j = y - radius; j <= y + radius; j++){
					for (int i = x - radius; i <= x + radius; i++){
						int p = img.getRGB(i,j);
						int[] pixelValues = pixelValues(p);
						pixels[index++] = pixelValues;
					}
				}
				
				int[] medPixelValues = medPixelValues(pixels);
				int p = setPixelValues(medPixelValues);
				newImg.setRGB(x, y, p);
				index = 0;
			}
		}

		return newImg;
	}

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