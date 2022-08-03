import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class MeanFilterSerial{

	public static void main(String[] args){

		int dimension = 9;
		BufferedImage img = null;//, newImg = null;
		img = Utilities.readImage(img);
		//newImg = Utilities.readImage(img);

		// int[] borders = Utilities.getBorders(img, dimension);

		// int radius = dimension/2;

		// int topBorder = borders[0];
		// int rightBorder = borders[1];
		// int bottomBorder = borders[2];
		// int leftBorder = borders[3];

		// int[][] pixels = new int[dimension*dimension][];
		// int index = 0;
		// for (int y = topBorder; y <= bottomBorder; y++){
		// 	for(int x = rightBorder; x <= leftBorder; x++){
		// 		for (int j = y - radius; j <= y + radius; j++){
		// 			for (int i = x - radius; i <= x + radius; i++){
		// 				int p = img.getRGB(j,i);
		// 				int[] pixelValues = Utilities.pixelValues(p);
		// 				pixels[index++] = pixelValues;
		// 			}
		// 		}
		// 		int[] avgPixelValues = Utilities.avgPixelValues(pixels);
		// 		int p = Utilities.setPixelValues(avgPixelValues);
		// 		img.setRGB(y, x, p);//newImg.setRGB(y, x, p);
		// 		index = 0;
		// 	}
		// }

		// System.out.println("Done");

		// // int[] test1 = {5,2,21,50};
		// // int[] test2 = {6,4,43,100};
		// // int[] test3 = {7,6,65,150};
		// // int[] test4 = {8,8,87,200};
		// // int[] test5 = {9,10,109,250};

		// // int[] test = Utilities.medPixelValues(test1, test2, test3, test4);
		// // for (int x : test)
		// // 	System.out.print(" " + x);

		// //Utilities.writeImage(newImg);
		Utilities.writeImage(img);

	}

	
}