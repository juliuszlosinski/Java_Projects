package GameEngine.Components;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import GameEngine.Heart.GameContainer;

public class WindowComponent
{
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private BufferedImage mainImage; // Map of pixels in the screen, which will be rendered.
	private BufferStrategy bs; // Buffer strategy for having multiple screens in the buffer (in the RAM).
	private Canvas canvas; // Canvas on which image will be painted.
	private JFrame window; // Window.
	private Graphics g; // Graphics from buffer strategy.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public WindowComponent(GameContainer gc) 
	{
		Dimension dim=new Dimension(gc.getWidthOfTheScreen(),gc.getHeightOfTheScreen()); // Setting the size of the screen.
		
		mainImage=new BufferedImage(dim.width,dim.height,BufferedImage.TYPE_INT_RGB); // Creating the map of pixels.
		
		window=new JFrame(); // Creating the window.
		window.setLayout(new BorderLayout()); // Setting the layout.
		window.setSize(dim); // Setting the size of the screen.
		window.setTitle(gc.getTitle()); // Giving the title.
		window.setResizable(false); // Setting the option to minimize screen. 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the default close operation.
		window.setVisible(true); // Setting the visible of the screen.
		window.setLocationRelativeTo(null); // Setting the location of the screen.

		canvas=new Canvas(); // Creating a canvas for the image.
		canvas.setSize(dim); // Setting the size of the canvas.
		canvas.setPreferredSize(dim); // Setting the preferred size of the canvas.
		canvas.setMaximumSize(dim); // Setting the maximum size.
		
		window.add(canvas,BorderLayout.CENTER);  // Adding the canvas to the screen.
		window.pack(); // Packing all together.
		
		canvas.createBufferStrategy(3); // Creating the buffer strategy with 3 buffers, slots for screen in the RAM.
		bs=canvas.getBufferStrategy(); // Getting reference to buffer strategy.
		g=bs.getDrawGraphics(); // Setting the paint brush to draw on the canvas.
	}
	//END REGION
	
	//REGION: PROPORTIES
	public BufferedImage getImage()
	{
		return mainImage;
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	//END REGION
	
	//REGION: METHODS
	public void Render()
	{
		bs.show(); // Paste the done map of pixels (screen) from the buffer to the real screen.
		g.drawImage(mainImage, 0, 0, canvas.getWidth(), canvas.getHeight(), null); // Draw the map of pixels.
	}
	//END REGION
	//END REGION
}
