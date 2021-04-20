package Game;


import java.awt.event.KeyEvent;

import Game.GameObjects.Components.BoxColliderComponent;
import Game.GameObjects.Components.RigidBodyComponent;
import Game.GameObjects.Components.SpriteComponent;
import Game.GameObjects.NotComponents.Player;
import GameEngine.Audio.Audio;
import GameEngine.Components.InputComponent;
import GameEngine.Components.RenderComponent;
import GameEngine.Configuration_Utils.GameConfiguration;
import GameEngine.Graphics.SpriteMap;
import GameEngine.Heart.GameAPI;
import GameEngine.Heart.GameContainer;

public class Game implements GameAPI
{
	//REGION: Main code of the game.
	
	Player player,player2,player3,player4;
	SpriteMap sm=new SpriteMap("/SimpleSprite.png",16,16);
	float dT=0.0f;
	float dT2=0.0f;
	int color=0;
	Audio clip=new Audio("Music/SimpleMusic.wav");
	boolean next=false;
	
	public Game() 
	{
		player=new Player(200,50,sm.getWidthOfImage(),sm.getHeightOfImage(),-1);
		player.AddComponent(new BoxColliderComponent(player));
		player.AddComponent(new SpriteComponent(player,sm,0,0,16,16));
		player.AddComponent(new RigidBodyComponent(player,5));
		
		player2=new Player(200,200,sm.getWidthOfImage(),sm.getHeightOfImage(),0xAADDAA);
		player2.AddComponent(new BoxColliderComponent(player2));
		player2.AddComponent(new SpriteComponent(player2,sm,1,0,16,16));
		clip.setVolume(0.1);
		clip.Play();
		clip.Loop(10);
	}
	
	
	@Override
	public void Update(GameContainer gc, InputComponent input, double dt) 
	{
		dT+=dt;
		dT2+=dt;
		if(dT>=1.0) 
		{
			((SpriteComponent)player.GetComponent(SpriteComponent.TAG)).NextFigureX();
			dT=0;
			color+=10;
		}
		if(dT2>=2.0) 
		{
			((SpriteComponent)player2.GetComponent(SpriteComponent.TAG)).NextFigureX();
			dT2=0;
		}
		
		
		if(input.isKey(KeyEvent.VK_W)) 
		{
			player.AddToPosition(0, -1);
		}
		if(input.isKey(KeyEvent.VK_S)) 
		{
			player.AddToPosition(0, 1);
		}
		if(input.isKey(KeyEvent.VK_A)) 
		{
			player.AddToPosition(-1, 0);
		}
		if(input.isKey(KeyEvent.VK_D)) 
		{
			player.AddToPosition(1, 0);
		}
		
		if(input.isKey(KeyEvent.VK_UP)) 
		{
			player2.AddToPosition(0, -1);
		}
		if(input.isKey(KeyEvent.VK_DOWN)) 
		{
			player2.AddToPosition(0, 1);
		}
		if(input.isKey(KeyEvent.VK_LEFT)) 
		{
			player2.AddToPosition(-1, 0);
		}
		if(input.isKey(KeyEvent.VK_RIGHT)) 
		{
			player2.AddToPosition(1, 0);
		}
		if(input.isKey(KeyEvent.VK_R)) 
		{
			player.UpdatePosition(200, 50);
			player2.UpdatePosition(200, 200);
		}
		
		player.Update(input, dt);
		player2.Update(input, dt);
	}
	
	@Override
	public void Render(GameContainer gc, RenderComponent renderer) 
	{
		renderer.FillRectangle(gc.getWidthOfTheScreen()-1, gc.getHeightOfTheScreen()-1, 1, 1, 0x454547+color);
		renderer.DrawRectangle(gc.getWidthOfTheScreen()-1, gc.getHeightOfTheScreen(), 0, 0, 0xAADDCC);
		
		
		player.Render(renderer);
		((BoxColliderComponent)player.GetComponent(BoxColliderComponent.TAG)).RenderConllisionBox(renderer);
		
		renderer.DrawString("SPIKE",1,5,0xAADDFF);
		renderer.DrawString("WASD:MOVE",1,55,0xAAA5FF );
		renderer.DrawString("ENGINE",100,5,0xFFFFFFF);
		renderer.DrawString("FPS:"+gc.getCurrentFPS(), 300, 5,0xFFBBAA);
		renderer.DrawString("R:RESET",1,30,0xADFBEA);
		
		player2.Render(renderer);
		((BoxColliderComponent)player2.GetComponent(BoxColliderComponent.TAG)).RenderConllisionBox(renderer);
	}
	
	//END REGION
	
	public static void main(String[]inputs)
	{
		GameContainer gc=new GameContainer(new Game(),GameConfiguration.DEFAULT);
		gc.Start();
	}
}

