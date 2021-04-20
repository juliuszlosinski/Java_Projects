package GameEngine.Graphics;

public class TileMap extends ImageBase
{
	//Definition of tile: Tile is an image, which is in the background, example: Trees, Mountains, ground.
	
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private int widthOfOneTile; // Width of one tile from the image with tiles.
	private int heightOfOneTile; // Height of one tile from the image with tiles.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public TileMap(String path,int widthOfOneTile, int heightOfOneTile) 
	{
		//TODO: Get an image with tiles and info about one tile.
		super(path);
		this.widthOfOneTile=widthOfOneTile;
		this.heightOfOneTile=heightOfOneTile;
	}
	//END REGION
	
	//REGION: PROPORTIES
	public int getWidthOfOneTile()
	{
		return widthOfOneTile;
	}
	
	public int getHeightOfOneTile() 
	{
		return heightOfOneTile;
	}
	//END REGION
	//END REGION
}
