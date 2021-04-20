package GameEngine.Components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import GameEngine.Heart.GameContainer;

public class InputComponent implements MouseListener,MouseMotionListener, KeyListener
{
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private boolean[]currentKeys=new boolean[256]; // Pressed keys in the current frame.
	private boolean[]lastKeys=new boolean[256]; // Pressed keys in the last frame.
	
	private boolean[]mouseButtons=new boolean[3]; // Pressed buttons in the current frame.
	private boolean[]lastMouseButtons=new boolean[3]; // Pressed buttons in last frame.
	private int mouseX; // Coordinate x of the mouse position.
	private int mouseY; // Coordinate y of the mouse position.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public InputComponent(GameContainer gc) 
	{
		//TODO: Add listeners to the canvas from window component.
		gc.getWindowComponent().getCanvas().addMouseListener(this); // Adding mouse listener (buttons).
		gc.getWindowComponent().getCanvas().addKeyListener(this); // Adding key listener.
		gc.getWindowComponent().getCanvas().addMouseMotionListener(this); // Adding mouse move listener.
	}
	//END REGION
	
	//REGION: PROPORTIES
	public boolean isKey(int key) 
	{
		return currentKeys[key];
	}
	
	public boolean isKeyUp(int key)
	{
		return !currentKeys[key]&&lastKeys[key];
	}
	
	public boolean isKeyDown(int key)
	{
		return currentKeys[key]&&!lastKeys[key];
	}
	
	public boolean isMouseButton(int key) 
	{
		return mouseButtons[key];
	}
	
	public boolean isMouseButtonUp(int key) 
	{
		return !mouseButtons[key]&&lastMouseButtons[key];
	}
	
	public boolean isMouseButtonDown(int key) 
	{
		return mouseButtons[key]&&!lastMouseButtons[key];
	}
	
	public int getMouseX() 
	{
		return mouseX;
	}
	
	public int getMouseY() 
	{
		return mouseY;
	}
	//END REGION
	
	//REGION: METHODS
	public void ExchangeKeysBetweenFrames() 
	{
		//TODO: Set the current keys and buttons in the frame, to be last.
		for(int i=0;i<currentKeys.length;i++) 
		{
			lastKeys[i]=currentKeys[i];
		}
		for(int i=0;i<mouseButtons.length;i++) 
		{
			lastMouseButtons[i]=mouseButtons[i];
		}
	}
	
	@Override
	public void keyPressed(KeyEvent evt)
	{
		currentKeys[evt.getKeyCode()]=true;
	}
	
	@Override
	public void keyReleased(KeyEvent evt)
	{
		currentKeys[evt.getKeyCode()]=false;
	}

	@Override
	public void mousePressed(MouseEvent evt) 
	{
		mouseButtons[evt.getButton()]=true;
	}
	
	@Override
	public void mouseReleased(MouseEvent evt) 
	{
		mouseButtons[evt.getButton()]=false;
	}
	
	@Override
	public void mouseMoved(MouseEvent evt)
	{
		mouseX=evt.getX();
		mouseY=evt.getY();
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) 
	{
		//TODO: Nothing.
	}
	@Override
	public void mouseEntered(MouseEvent evt) 
	{
		mouseX=evt.getX();
		mouseY=evt.getY();
	}
	@Override
	public void mouseExited(MouseEvent evt)
	{
		//TODO: Nothing.
	}
	
	@Override
	public void keyTyped(KeyEvent evt) 
	{
		//TODO: Nothing.
	}
	
	@Override
	public void mouseDragged(MouseEvent evt) 
	{
		mouseX=evt.getX();
		mouseY=evt.getY();
	}
	//END REGION
	//END REGION
}
