package GameEngine.Heart;

import GameEngine.Components.InputComponent;
import GameEngine.Components.RenderComponent;
import GameEngine.Components.WindowComponent;
import GameEngine.Configuration_Utils.GameConfiguration;

public class GameContainer implements Runnable 
{	
	//REGION: CLASSES AREA
	//REGION: FIELDS
	private static GameConfiguration gameConfiguration; // Current game configuration
	//END REGION
	
	//REGION: PROPORTIES
	public static GameConfiguration getGameConfiguration() 
	{
		return gameConfiguration;
	}
	//END REGION
	//END REGION
	
	//REGION: FIELDS
	//REGION: Configuration of the game
	private int widthOfTheScreen;  // Width of the screen with game.
	private int heightOfTheScreen; // Height of the screen with game.
	private int maxFPS; // Maximum number of frames per second.
	private String title; // Title of game.
	private GameConfiguration conf;
	//END REGION
	
	//REGION: GAME THREADS
	private Thread gameThread; //Game thread.
	//END REGION
	
	//REGION: STATUS OF ENGINE
	private int currentFPS; //Current number of frames per second.
	private double dt; //Time of one frame.
	private boolean running;  // Check if is it running.
	//END REGION
	
	//REGION: COMPONENTS
	private WindowComponent windowComponent; // Component responsible for setting window and canvas strategy with image. 
	private RenderComponent renderComponent; // Component responsible for rendering objects on the image from canvas.
	private InputComponent inputComponent;	 // Component responsible for getting input from the user.
	//END REGION
	
	//REGION: Game API for development of games
	private GameAPI gameAPI; // Custom operations (update, render) to insert in the game loop.
	//END REGION
	//END REGION
	
	//REGION: CONSTRUCTORS AND HELP METHODS
	public GameContainer(GameAPI gameAPI, GameConfiguration conf) 
	{
		//TODO: Initialize game.
		SetGameConfiguration(conf);
		SetComponents();
		SetGameAPI(gameAPI);
	}
	
	private void SetGameConfiguration(GameConfiguration conf) 
	{
		//TODO: Initialize basic configuration.
		widthOfTheScreen=conf.getWidthOfTheScreen();
		heightOfTheScreen=conf.getHeightOfTheScreen();
		maxFPS=conf.getMaxFPS();
		title=conf.getTitle();
		gameConfiguration=conf;
		dt=1.0/maxFPS;
	}
	
	
	private void SetComponents()
	{
		//TODO: Set the engine components.
		windowComponent=new WindowComponent(this);
		renderComponent=new RenderComponent(this);
		inputComponent=new InputComponent(this);
	}
	
	private void SetGameAPI(GameAPI gameAPI) 
	{
		//TODO: Set the custom configuration of game loop (render, update).
		this.gameAPI=gameAPI;
	}
	
	//END REGION
	
	//REGION: PROPORTIES
	public int getCurrentFPS() 
	{
		return currentFPS;
	}
	
	public int getWidthOfTheScreen() 
	{
		return widthOfTheScreen;
	}
	
	public int getHeightOfTheScreen() 
	{
		return heightOfTheScreen;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public WindowComponent getWindowComponent() 
	{
		return windowComponent;
	}
	//END REGION
	
	//REGION: METHODS
	public void Start() 
	{
		//TODO: Start the game/ game thread.
		if(!running) 
		{
			running=true;
			gameThread=new Thread(this);
			gameThread.start();
		}
	}

	@Override
	public void run() 
	{
		//TODO: Run the game loop (Simple game loop, simple means fast and easy to implement).
		long lastTime=System.nanoTime();
		double cap_FPS=1_000_000_000.0/maxFPS;
		double delta=0;
		
		int frames=0;
		double timer=System.currentTimeMillis();
		
		while(running)
		{
			long now=System.nanoTime();
			delta+=(now-lastTime)/cap_FPS;
			lastTime=now;
			
			//TODO: Render and update specific times per second.
			if(delta>=1.0)
			{
				Update();
				Render();
				delta=0;
				frames++;
			}
			
			//TODO: Count the frames.
			if(System.currentTimeMillis()-timer>1000) 
			{
				//System.out.println("FPS: "+frames);
				timer+=1000;
				currentFPS=frames;
				frames=0;
			}
		}
	}
	
	private void Render()
	{
		//TODO: Render the things on the screen.
		renderComponent.ClearThePreviousFrame();  // Clear the previous status of pixels on the screen.
		gameAPI.Render(this, renderComponent); // Render custom things from the Game API.
		windowComponent.Render(); // Render an image on the screen, image is our map of pixels.
	}
	
	private void Update() 
	{
		//TODO: Updates the things on the screen.
		inputComponent.ExchangeKeysBetweenFrames(); // Set the current pressed keys to be last.
		gameAPI.Update(this, inputComponent, dt); // Update custom things from the Gami API.
	}
	//END REGION
}
