package Game.GameObjects.NotComponents;

import java.util.List;
import java.util.Random;

import Game.GameObjects.Components.GameObjectComponent;
import GameEngine.Components.InputComponent;
import GameEngine.Components.RenderComponent;
import GameEngine.Configuration_Utils.GameConfiguration;

public abstract class GameObject 
{
	//REGION: CLASSES AREA
	//REGION: FIELDS
	public static List<GameObject>gameObjects=new java.util.ArrayList<GameObject>(); // List of all game object in the game.
	//END REGION
	//END REGION
	
	//REGION: FIELDS
	protected int posX; // Position x on the screen.
	protected int posY; // Position y on the screen.
	protected int width; // Width of the game object.
	protected int height; // Height of the game object.
	
	protected boolean grounded; // Variable to store information about status of game object, on the ground?
	
	protected boolean goRight; // Can go right?
	protected boolean goLeft; // Can go left?
	protected boolean goUp; // Can go up?
	protected boolean goDown; // Can go down?
	
	protected int color; // Color of the debug rectangle.
	
	protected int ID; // Individual id of every game object.
	
	protected List<GameObjectComponent>gameObjectsComponents=new java.util.ArrayList<GameObjectComponent>();  // List of game objects components.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public GameObject(int posX,int posY, int width, int height,int color)
	{
		//TODO: Get and set the info about game object, add to game objects list.
		
		this.posX=posX;
		this.posY=posY;
	
		this.width=width;
		this.height=height;
	
		this.color=color;
		
		goRight=true;
		goLeft=true;
		goUp=true;
		goDown=true;
		grounded=false;
		
		gameObjects.add(this);
		ID=(new Random()).nextInt(10000);
	}
	//END REGION
	
	//REGION: PROPORTIES
	public int getWidth() 
	{
		return width;
	}
	
	public int getHeight() 
	{
		return height;
	}
	
	public boolean isGrounded() 
	{
		return grounded;
	}
	
	public int getID() 
	{
		return ID;
	}
	
	public int getPosX()
	{
		return posX;
	}
	
	public int getPosY() 
	{
		return posY;
	}
	
	public int getColor() 
	{
		return color;
	}
	
	public boolean isGoRight() 
	{
		return goRight;
	}
	
	public boolean isGoLeft()
	{
		return goLeft;
	}
	
	public boolean isGoUp() 
	{
		return goUp;
	}
	
	public boolean isGoDown()
	{
		return goDown;
	}
	
	public void setGoRight(boolean goRight) 
	{
		this.goRight=goRight;
	}
	
	public void setGoLeft(boolean goLeft)
	{
		this.goLeft=goLeft;
	}
	
	public void setGoUp(boolean goUp) 
	{
		this.goUp=goUp;
	}
	
	public void setGoDown(boolean goDown)
	{
		this.goDown=goDown;
		grounded=!goDown;
	}
	//END REGION
	
	//REGION: METHODS
	public void UpdatePosition(int posX, int posY) 
	{
		//TODO: Update the position of the game object by giving new coordinates.
		if((posX>this.posX && goRight)||(posX<this.posX && goLeft))
		{
			this.posX=posX;
		}
		if((posY>this.posY && goDown)||(posY<this.posY && goUp))
		{
			this.posY=posY;
		}
	}
	
	public void UpdateWithCollisionMouse(int posX,int posY)
	{
		//TODO: Update the position by mouse, enable collision.
		if((posX>this.posX)&&goRight)
		{
			this.posX=posX;
		}
		if((posX<this.posX)&&goLeft) 
		{
			this.posX=posX;
		}
		if((posY>this.posY)&&goUp)
		{
			this.posY=posY;
		}
		if((posY<this.posY)&&goDown)
		{
			this.posY=posY;
		}
	}
	
	public void UpdateWithoutCollisionMouse(int posX, int posY) 
	{
		//TODO: Update the position by mouse, disable collision.
		if((posX>this.posX && goRight)||(posX<this.posX && goLeft))
		{
			this.posX=posX;
		}
		if((posY>this.posY && goDown)||(posY<this.posY && goUp))
		{
			this.posY=posY;
		}
	}
	
	public void AddToPosition(int dX,int dY) 
	{
		//TODO: Add specific number to current position.
		if((dX>0 && goRight)||(dX<0 && goLeft)) 
		{
			posX+=dX;
		}
		if((dY>0 && goDown)||(dY<0&& goUp)) 
		{
			posY+=dY;
		}
	}
	
	
	public GameObjectComponent GetComponent(String tag)
	{
		//TODO: Get the specific game object component from the list with components.
		for(GameObjectComponent gameObjectComponent : gameObjectsComponents) 
		{
			if(gameObjectComponent.getTag().equals(tag))
			{
				return gameObjectComponent;
			}
		}
		return null;
	}
	
	public void AddComponent(GameObjectComponent goc)
	{
		//TODO: Add the specific game object component to the list with components.
		for(GameObjectComponent gameObjectComponent : gameObjectsComponents) 
		{
			if(gameObjectComponent.getTag().equals(goc.getTag()))
			{
				return;
			}
		}
		gameObjectsComponents.add(goc);
	}
	
	private void UpdateComponents(InputComponent input,double dt) 
	{
		//TODO: Update all the game objects component (operations implemented in method update in every component).
		for(GameObjectComponent gameObjectComponent : gameObjectsComponents) 
		{
			gameObjectComponent.Update(input,dt);
		}
	}
	
	private void RenderComponents(RenderComponent render) 
	{
		//TODO: Render all the things from the code which is written in the method render in every game object component.
		for(GameObjectComponent gameObjectComponent : gameObjectsComponents) 
		{
			gameObjectComponent.Render(render);
		}
	}
	
	public void Update(InputComponent input, double dt)
	{
		//TODO: Update components (code from method update from every game object component).
		UpdateComponents(input,dt);
	}
	
	public void Render(RenderComponent render) 
	{
		//TODO: Render things from components (code from render update from every game object component).
		RenderComponents(render);
	}
	//END REGION
	//END REGION
}
