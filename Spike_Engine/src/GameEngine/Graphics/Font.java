package GameEngine.Graphics;

public class Font extends ImageBase
{
	//REGION: CLASSES AREA
	//REGION: FIELDS
	public static final Font DEFAULT=new Font("/DefaultFont.png",16,16); // Default font.
	//END REGION
	//END REGION
	
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private int widthOfOneLetter; // Width of one letter in the image.
	private int heightOfOneLetter; // Height of one letter in the image.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public Font(String path, int widthOfOneLetter, int heightOfOneLetter)
	{
		//TODO: Get an image of the font and information about one letter.
		super(path);
		this.widthOfOneLetter=widthOfOneLetter;
		this.heightOfOneLetter=heightOfOneLetter;
	}
	//END REGION
	
	//REGION: PROPORTIES
	public int getHeightOfOneLetter()
	{
		return heightOfOneLetter;
	}
	
	public int getWidthOfOneLetter() 
	{
		return widthOfOneLetter;
	}
	//END REGION
	//END REGION
}
