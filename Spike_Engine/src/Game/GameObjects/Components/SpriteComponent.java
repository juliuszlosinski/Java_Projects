package Game.GameObjects.Components;

import Game.GameObjects.NotComponents.GameObject;
import GameEngine.Components.InputComponent;
import GameEngine.Components.RenderComponent;
import GameEngine.Graphics.SpriteMap;

public class SpriteComponent extends GameObjectComponent
{
	/*PURPOSE OF THE COMPONENT:
	
	Add the sprite to the game object, and give possibility to change it to another sprite from given sprite map.
	In the other words:
		- give sprite to game object,
		- render it,
		- give possibility to change it,
		- use this component by animation component to operate on the sprites from the sprites map.
	
	*/
	
	//REGION: CLASSES AREA
	//REGION: FIELDS
	public static final String TAG="SpriteComponent"; // Special tag of the component.
	//END REGION
	
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private SpriteMap figures; 	// Sprite map with sprites.
	private int currentFigureX; // Current row location in sprite map.
	private int currentFigureY; // Current column location in sprite map.
	private int maxFigureX; // Max number of positions in the row in sprite map.
	private int maxFigureY; // Max number of positions in the column  in sprite map.
	
	private GameObject gameObject; // Reference to the game object, which is having this component.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public SpriteComponent(GameObject gameObject, SpriteMap figures, int currentFigureX, int currentFigureY, int maxFigureX, int maxFigureY)
	{ 
		//TODO: Get the game object reference, sprite map and info about it.
		this.gameObject=gameObject;
		this.figures=figures;
		this.currentFigureX=currentFigureX;
		this.currentFigureY=currentFigureY;
		this.maxFigureX=maxFigureX;
		this.maxFigureY=maxFigureY;
		super.tag=TAG;
	}
	//END REGION
	
	//REGION: PROPORTIES
	public String getTag()
	{
		return tag;
	}
	
	public int getCurrentFigureX() 
	{
		return currentFigureX;
	}
	
	public int getCurrentFigureY() 
	{
		return currentFigureY;
	}
	//END REGION
	
	//REGION: METHODS
	public void NextFigureX() 
	{
		//TODO: Change the current position in the row with sprite to another position in the row. EX: |__|_HERE_|__|
		if(currentFigureX>maxFigureX) 
		{
			currentFigureX=0;
		}
		else 
		{
			currentFigureX++;
		}
	}
	
	public void NextFigureY() 
	{
		//TODO: Change the current position in the column to another position in the column.
		if(currentFigureY>maxFigureY) 
		{
			currentFigureY=0;
		}
		else 
		{
			currentFigureY++;
		}
	}
	
	public void SetFigureX(int x)
	{
		//TODO: Set the new position in the row.
		if(x<=maxFigureX) 
		{
			currentFigureX=x;
		}
	}
	
	public void SetFigureY(int y) 
	{
		//TODO: Set the new position in the column
		if(y<=maxFigureY) 
		{
			currentFigureY=y;
		}
	}
	
	@Override
	public void Update(InputComponent input, double dt) 
	{
		//TODO: Nothing
	}
	
	@Override
	public void Render(RenderComponent render) 
	{
		//TODO: Render sprite on the screen.
		render.DrawSprite(figures, currentFigureX, currentFigureY, gameObject.getPosX(), gameObject.getPosY());
	}
	//END REGION
	//END REGION
}