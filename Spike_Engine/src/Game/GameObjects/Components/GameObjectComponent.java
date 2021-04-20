package Game.GameObjects.Components;

import GameEngine.Components.InputComponent;
import GameEngine.Components.RenderComponent;

public abstract class GameObjectComponent
{
	//REGION: FIELDS
	protected String tag; // Special tag of game object component.
	//END REGION
	
	//REGION: PROPORTIES
	public String getTag() 
	{
		return tag;
	}
	//END REGION
	
	//REGION: METHODS
	public void Update(InputComponent input, double dt) 
	{
		//TODO: Instructions to implement by derivative game object components.
	}
	public void Render(RenderComponent render) 
	{
		//TODO: Instructions to implement by derivative game object components.
	}
	//END REGION
}

