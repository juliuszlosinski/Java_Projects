package GameEngine.Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBase
{
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private int widthOfImage;  // Width of the image.
	private int heightOfImage; // Height of the image.
	private int[]pixelsOfImage; // Map of pixels from the image.
	protected BufferedImage image;  // Image.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public ImageBase(String path) 
	{
		//TODO: Get an image and save it.
		try 
		{
			image=ImageIO.read(Image.class.getResourceAsStream(path));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		widthOfImage=image.getWidth();
		heightOfImage=image.getHeight();
		pixelsOfImage=image.getRGB(0, 0, widthOfImage, heightOfImage, null, 0, widthOfImage);
	}
	//END REGION
	
	//REGION: PROPORTIES
	public int getWidthOfImage() 
	{
		return widthOfImage;
	}
	
	public int getHeightOfImage()
	{
		return heightOfImage;
	}
	
	public int[] getPixelsOfImage() 
	{
		return pixelsOfImage;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	//END REGION
	//END REGION
}
