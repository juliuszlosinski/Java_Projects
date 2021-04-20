package GameEngine.Heart;

import GameEngine.Components.InputComponent;
import GameEngine.Components.RenderComponent;

public interface GameAPI 
{
	//REGION: Operations to implement with custom users instructions. 
	public void Update(GameContainer gc, InputComponent input, double dt);
	public void Render(GameContainer gc, RenderComponent renderer);
	//END REGION
}
