package Game.GameObjects.Components;

import Game.GameObjects.NotComponents.GameObject;
import GameEngine.Components.InputComponent;
import GameEngine.Components.RenderComponent;

public class BoxColliderComponent extends GameObjectComponent
{
	/* PURPOSE OF THE COMPONENT:
	 * Detect the collision with other game objects.
	 * Get the other game objects from the list (GameObject.GameObjects) with all game objects, and check the position.
	 */
	
	//REGION: CLASSES AREA
	//REGION: FIELDS
	public static final String TAG="BoxColliderComponent";  // Special tag of game object component.
	//END REGION
	//END REGION
	
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private int width; 	// Width of current game object.
	private int height; // Height of current game object. 
	private GameObject currentGameObject; // Reference to current game object.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public BoxColliderComponent(GameObject currentGameObject) 
	{
		//TODO: Set the reference to game object and get the info about it.
		this.width=currentGameObject.getWidth();
		this.height=currentGameObject.getHeight();
		this.currentGameObject=currentGameObject;
		super.tag=TAG;
	}
	//END REGION
	
	//REGION: METHODS
	public void RenderConllisionBox(RenderComponent render)
	{
		//TODO: Draw debug collision rectangle on the screen.
		render.DrawRectangle( currentGameObject.getWidth(),currentGameObject.getHeight(), currentGameObject.getPosX(), currentGameObject.getPosY(), currentGameObject.getColor());
	}
	
	@Override
	public void Update(InputComponent input, double dt)
	{
		//TODO: Check the collision with the other game objects.
		for(GameObject other: GameObject.gameObjects)
		{
			int otherID=other.getID();
			if(otherID!=currentGameObject.getID())
			{
				BoxColliderComponent bx=((BoxColliderComponent)other.GetComponent(TAG));
				if(bx!=null)
				{
					int otherWidth=other.getWidth();
					int otherHeight=other.getHeight();
					int otherPosX=other.getPosX();
					int otherPosY=other.getPosY();
					
					int posX=currentGameObject.getPosX();
					int posY=currentGameObject.getPosY();
					
					
					if((otherPosX>posX)) // Other position x is greater than our, it means that other object is on the right side.
					{
						if(posX+width+2>=otherPosX)
						{
							if(posY+height<=otherPosY) // ON
							{
								int dif=posY+height+2;
								if(dif>=otherPosY)
								{
									currentGameObject.setGoDown(false);
								}
								else 
								{
									currentGameObject.setGoDown(true);
								}
								currentGameObject.setGoRight(true);
							} 
							else if(posY>=otherPosY+otherHeight) //UNDER
							{
								int dif=posY-(otherPosY+otherHeight);
								if(dif<=1)
								{
									currentGameObject.setGoUp(false);
								}
								else
								{
									currentGameObject.setGoUp(true);
								}
								currentGameObject.setGoRight(true);
							}
							else // NEXT TO
							{
								currentGameObject.setGoRight(false);
							}
						}
						else
						{
							currentGameObject.setGoUp(true);
							currentGameObject.setGoRight(true);
							currentGameObject.setGoDown(true);
						}
					}
					else // Other game object is on the left side.
					{
						if(otherPosX+width+2>=posX)
						{
							if(posY+height<otherPosY) // ON
							{
								if(posY+height+2>=otherPosY)
								{
									currentGameObject.setGoDown(false);
								}
								else
								{
									currentGameObject.setGoDown(true);
								}
								currentGameObject.setGoLeft(true);
							}
							else if(posY>otherPosY+otherHeight) // UNDER
							{
								if(posY-2<otherPosY+otherHeight)
								{
									currentGameObject.setGoUp(false);
								}
								else 
								{
									currentGameObject.setGoUp(true);
								}
								currentGameObject.setGoLeft(true);
							}
							else //OBOK
							{
								currentGameObject.setGoLeft(false);
							}
						}
						else
						{
							currentGameObject.setGoUp(true);
							currentGameObject.setGoLeft(true);
							currentGameObject.setGoDown(true);
						}
					}
					
					
				}
			}
		}
	}
	//END REGION
}
