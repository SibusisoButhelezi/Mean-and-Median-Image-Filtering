import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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
		img = Utilities.readImage(img, imageName);
		newImg = Utilities.readImage(newImg, imageName);

		int[] borders = Utilities.getBorders(img, dimension);

		int radius = dimension/2;

		int topBorder = borders[0];
		int rightBorder = borders[1];
		int bottomBorder = borders[2];
		int leftBorder = borders[3];

		int[][] pixels = new int[dimension*dimension][];
		int index = 0;

		for (int y = topBorder; y < bottomBorder; y++){
			for(int x = leftBorder; x < rightBorder; x++){

				for (int j = y - radius; j <= y + radius; j++){
					for (int i = x - radius; i <= x + radius; i++){
						int p = img.getRGB(i,j);
						int[] pixelValues = Utilities.pixelValues(p);
						pixels[index++] = pixelValues;
					}
				}
				
				int[] avgPixelValues = Utilities.medPixelValues(pixels);
				int p = Utilities.setPixelValues(avgPixelValues);
				newImg.setRGB(x, y, p);
				index = 0;
			}
		}

		System.out.println("Done Processing.");
		Utilities.writeImage(newImg, outputName);
	}
}