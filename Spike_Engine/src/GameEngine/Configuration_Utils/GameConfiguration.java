package GameEngine.Configuration_Utils;

public class GameConfiguration
{
	//REGION: CLASSES AREA
	//REGION: FIELDS
	public static GameConfiguration DEFAULT=new GameConfiguration("SpikeEngine",60,500,500); // Default configuration for the game.
	//END REGION
	//END REGION
	
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private String title; // Title of the game
	private int maxFPS; // Number of maximum frames per second. 
	private int widthOfTheScreen; // Width of the screen with game.
	private int heightOfTheScreen; // Height of the screen with game.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public GameConfiguration(String title,int maxFPS, int widthOfTheScreen,int heightOfTheScreen)
	{
		//TODO: Setting the fields.
		this.title=title;
		this.maxFPS=maxFPS;
		this.widthOfTheScreen=widthOfTheScreen;
		this.heightOfTheScreen=heightOfTheScreen;
	}
	//END REGION
	
	//REGION: PROPORTIES
	public String getTitle() 
	{
		return title;
	}
	
	
	
	public int getMaxFPS() 
	{
		return maxFPS;
	}
	
	public int getWidthOfTheScreen()
	{
		return widthOfTheScreen;
	}
	
	public int getHeightOfTheScreen()
	{
		return heightOfTheScreen;
	}
	//END REGION
}
