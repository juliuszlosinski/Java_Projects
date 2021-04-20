package Game.GameObjects.Components;

import Game.GameObjects.NotComponents.GameObject;
import GameEngine.Heart.GameContainer;
import GameEngine.Components.InputComponent;
import GameEngine.Components.RenderComponent;
import GameEngine.Configuration_Utils.GameConfiguration;
import GameEngine.Heart.GameContainer;

public class RigidBodyComponent extends GameObjectComponent
{
	/* PURPOSE OF THE COMPONENT:
	 * 
	 * Add the physics behavior for the game object.
	 * 
	 */
	
	
	//REGION: CLASSES AREA
	//REGION: FIELDS
	public static final String TAG="RigidBodyComponent"; // Special tag for the component.
	//END REGION
	
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private final double g=9.81; // Gravity.
	private boolean active; // Is this component activated?
	private double mass; // Mass of the game object.
	private GameObject gameObject; // Reference to game object, which is having this component.
	private double currentTime; // Current time.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public RigidBodyComponent(GameObject gameObject, double mass) 
	{
		//TODO: Get the reference to game object and get info about it.
		this.gameObject=gameObject;
		this.mass=mass;
		currentTime=0;
		super.tag=TAG;
		active=true;
	}
	//END REGION
	
	//REGION: METHODS
	public void CheckIsGrounded() 
	{
		//TODO: Check if is the game object on the floor.
		if(!(gameObject.isGrounded())&&active)
		{
			int height=gameObject.getPosY();
			if(height>2) 
			{
				double velocity=GetSpeedFromGravity(height);
				gameObject.UpdatePosition(gameObject.getPosX(), gameObject.getPosY()+(int)(velocity*currentTime*0.1));
			}
		}
		else 
		{
			currentTime=0;
		}
		
		if(gameObject.getPosY()>GameContainer.getGameConfiguration().getHeightOfTheScreen())
		{
			currentTime=0;
			active=false;
		}
		else
		{
			active=true;
		}
	}
	
	private double GetSpeedFromGravity(int h) 
	{
		return Math.sqrt(2*g*h);
	}
	
	public void SetActive(boolean active) 
	{
		this.active=active;
	}
	
	@Override
	public void Update(InputComponent input,double dt)
	{
		//TODO: Update the status.
		CheckIsGrounded();
		currentTime+=dt;
	}
	
	@Override
	public void Render(RenderComponent render)
	{
		//TODO: Nothing.
	}
	//END REGION
	//END REGION
}
