import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class PictureMatch{
	public static void main(String[] args){
		String imageName1 = args[0];
		String imageName2 = args[1];

		BufferedImage FirstImg = null, SecondImg = null;
		FirstImg = readImage(FirstImg, imageName1);
		SecondImg = readImage(SecondImg, imageName2);

		int width = FirstImg.getWidth();
		int height = FirstImg.getHeight();

		for (int y = 0; y < height; y++){
			for (int x = 45; x < width; x++){

				int i = FirstImg.getRGB(x, y);
				int[] pixelValues1 = pixelValues(i);

				int j = SecondImg.getRGB(x, y);
				int[] pixelValues2 = pixelValues(j);
				for (int index = 0; index < 4; index++){
					if (pixelValues1[index] != pixelValues2[index]){
						System.out.println("These images are different :( ");
						System.exit(0);
					}
				}
			}
		}

		System.out.println("Eureka! These pictures match!");
	}

	public static BufferedImage readImage(BufferedImage img, String imageName){

		try{

			img = ImageIO.read(new File("Pictures/" + imageName));
			int width = img.getWidth();
			int height = img.getHeight();
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			img = ImageIO.read(new File("Pictures/" + imageName));
			System.out.println("Image read.");

			return img;
		}
		catch(IOException e){
			System.out.println("Error reading: " + e);
		}
		return null;
	}

	public static int[] pixelValues(int p){
		int[] values = new int[4];

		values[0] = (p>>24) & 0xff;
		values[1] = (p>>16) & 0xff;
		values[2] = (p>>8) & 0xff;
		values[3] = p & 0xff;

		return values;
	}
}
