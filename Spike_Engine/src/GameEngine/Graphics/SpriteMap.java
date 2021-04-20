package GameEngine.Graphics;

public class SpriteMap extends ImageBase
{
	//Definition of sprite: Sprite is an image which is in the foreground, example: player, NPC.
	
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private int widthOfOneSprite; // Width of one sprite from the image with sprites (map of sprites).
	private int heightOfOneSprite; // Height of one sprite.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public SpriteMap(String path, int widthOfOneSprite, int heightOfOneSprite) 
	{
		//Get the image with sprites and information about one sprite.
		super(path);
		this.widthOfOneSprite=widthOfOneSprite;
		this.heightOfOneSprite=heightOfOneSprite;
	}
	//END REGION
	
	//REGION: PROPORTIES
	public int getWidthOfOneSprite()
	{
		return widthOfOneSprite;
	}
	
	public int getHeightOfOneSprite()
	{
		return heightOfOneSprite;
	}
	//END REGION
	//END REGION
}
