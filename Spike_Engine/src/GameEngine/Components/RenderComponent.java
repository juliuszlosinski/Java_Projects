package GameEngine.Components;

import java.awt.image.DataBufferInt;

import GameEngine.Graphics.Font;
import GameEngine.Graphics.ImageBase;
import GameEngine.Graphics.SpriteMap;
import GameEngine.Graphics.TileMap;
import GameEngine.Heart.GameContainer;

public class RenderComponent 
{
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private int widthOfScreen; // The width of the screen (width of map of pixels).
	private int heightOfScreen; // The height of the screen (height of the map of pixels).
	private int[]pixelsOfScreen; // The screen (map of pixels).
	//END REGION
	
	//REGION: CONSTRUCTORS
	public RenderComponent(GameContainer gc)
	{
		pixelsOfScreen=((DataBufferInt)gc.getWindowComponent().getImage().getRaster().getDataBuffer()).getData(); //Getting the map of pixels from the window component.
		widthOfScreen=gc.getWidthOfTheScreen(); // Getting the width of the screen from the window component.
		heightOfScreen=gc.getHeightOfTheScreen(); // Getting the height of the screen from the window component.
	}
	//END REGION
	
	//REGION: METHODS
	public void SetPixel(int x,int y, int color)
	{
		//TODO: Setting the pixel on the screen.
		if(x>=widthOfScreen || x<0 || y>=heightOfScreen || y<0) 
		{
			return;
		}
		pixelsOfScreen[x+y*widthOfScreen]=color;
	}
	
	public void ClearThePreviousFrame()
	{
		//TODO: Refresh the screen by deleting status of pixels from the previous frame.
		for(int y=0;y<heightOfScreen;y++) 
		{
			for(int x=0;x<widthOfScreen;x++) 
			{
				SetPixel(x,y,0x00_00_00);
			}
		}
	}
	
	public void DrawLetter(Font font, String let, int dx, int dy) 
	{
		//TODO: Draw the letter on the screen.
		int width=font.getWidthOfImage(); //670, width of the image with font
		int height=font.getHeightOfImage(); //16, height of the image with font
		int widthOfOneLetter=font.getWidthOfOneLetter(); //16, width of one letter on the image with font.
		int selectedX=(let.codePointAt(0)-48)*font.getHeightOfOneLetter(); //|48-50|=2, index of position in a Row
		int selectedY=0; // 0, index of position in a column
		int scaleX=width/widthOfOneLetter; //670:16 ~= 47.  // Scale of minimizing the rendering, to render only one letter.
		
		for(int y=0;y<height;y++) 
		{
			for(int x=0;x<width/scaleX;x++)
			{
				int index=(selectedX+x)+(selectedY+y)*width; // Getting the index of the color of pixel from image.
				int color=font.getPixelsOfImage()[index]; // Getting the color of the pixel. 
				if(((color>>24)& 0xFF)!=0)
				{
					SetPixel(dx+x,dy+y,color); // Setting the pixel
				}
			}
		}
	}
	
	public void DrawLetter(String let, int dx, int dy) 
	{
		//TODO: Draw the letter on the screen.
		Font font=Font.DEFAULT; // Set font.
		int width=font.getWidthOfImage(); //670, width of the image with font
		int height=font.getHeightOfImage(); //16, height of the image with font
		int widthOfOneLetter=font.getWidthOfOneLetter(); //16, width of one letter on the image with font.
		int selectedX=(let.codePointAt(0)-48)*font.getHeightOfOneLetter(); //|48-50|=2, index of position in a Row
		int selectedY=0; // 0, index of position in a column
		int scaleX=width/widthOfOneLetter; //670:16 ~= 47.  // Scale of minimizing the rendering, to render only one letter.
		
		for(int y=0;y<height;y++) 
		{
			for(int x=0;x<width/scaleX;x++)
			{
				int index=(selectedX+x)+(selectedY+y)*width; // Getting the index of the color of pixel from image.
				int color=font.getPixelsOfImage()[index]; // Getting the color of the pixel. 
				if(((color>>24)& 0xFF)!=0)
				{
					SetPixel(dx+x,dy+y,color); // Setting the pixel
				}
			}
		}
	}
	
	public void DrawLetter(String let, int dx, int dy, int color) 
	{
		//TODO: Draw the letter on the screen.
		Font font=Font.DEFAULT; // Set font.
		int width=font.getWidthOfImage(); //670, width of the image with font
		int height=font.getHeightOfImage(); //16, height of the image with font
		int widthOfOneLetter=font.getWidthOfOneLetter(); //16, width of one letter on the image with font.
		int selectedX=(let.codePointAt(0)-48)*font.getHeightOfOneLetter(); //|48-50|=2, index of position in a Row
		int selectedY=0; // 0, index of position in a column
		int scaleX=width/widthOfOneLetter; //670:16 ~= 47.  // Scale of minimizing the rendering, to render only one letter.
		
		for(int y=0;y<height;y++) 
		{
			for(int x=0;x<width/scaleX;x++)
			{
				int index=(selectedX+x)+(selectedY+y)*width; // Getting the index of the color of pixel from image.
				int colorR=font.getPixelsOfImage()[index]; // Getting the color of the pixel. 
				if(((colorR>>24)& 0xFF)!=0)
				{
					SetPixel(dx+x,dy+y,color); // Setting the pixel
				}
			}
		}
	}
	
	public void DrawLetter(Font font, String let, int dx, int dy, int color) 
	{
		//TODO: Draw the letter on the screen.
		int width=font.getWidthOfImage(); //670, width of the image with font
		int height=font.getHeightOfImage(); //16, height of the image with font
		int widthOfOneLetter=font.getWidthOfOneLetter(); //16, width of one letter on the image with font.
		int selectedX=(let.codePointAt(0)-48)*font.getHeightOfOneLetter(); //|48-50|=2, index of position in a Row
		int selectedY=0; // 0, index of position in a column
		int scaleX=width/widthOfOneLetter; //670:16 ~= 47.  // Scale of minimizing the rendering, to render only one letter.
		
		for(int y=0;y<height;y++) 
		{
			for(int x=0;x<width/scaleX;x++)
			{
				int index=(selectedX+x)+(selectedY+y)*width; // Getting the index of the color of pixel from image.
				int colorR=font.getPixelsOfImage()[index]; // Getting the color of the pixel. 
				if(((colorR>>24)& 0xFF)!=0)
				{
					SetPixel(dx+x,dy+y,color); // Setting the pixel
				}
			}
		}
	}
	
	
	public void DrawString(Font font, String word, int dx, int dy)
	{
		//TODO: Draw a word using DrawLetter method.
		int space=font.getWidthOfOneLetter()+1; // Spacing between two words.
		for(int i=0;i<word.length();i++)
		{
			DrawLetter(font,word.charAt(i)+"",dx,dy);
			dx+=space;
		}
	}
	
	public void DrawString(Font font, String word, int dx, int dy, int color)
	{
		//TODO: Draw a word using DrawLetter method.
		int space=font.getWidthOfOneLetter()+1; // Spacing between two words.
		for(int i=0;i<word.length();i++)
		{
			DrawLetter(font,word.charAt(i)+"",dx,dy,color);
			dx+=space;
		}
	}
	
	public void DrawString(String word, int dx, int dy, int color)
	{
		//TODO: Draw a word using DrawLetter method.
		Font font=Font.DEFAULT; // Set the font.
		int space=font.getWidthOfOneLetter()+1; // Spacing between two words.
		for(int i=0;i<word.length();i++)
		{
			DrawLetter(font,word.charAt(i)+"",dx,dy,color);
			dx+=space;
		}
	}
	
	public void DrawString(String word, int dx, int dy)
	{
		//TODO: Draw a word using DrawLetter method.
		Font font=Font.DEFAULT; // Setting the font of the string.
		int space=font.getWidthOfOneLetter()+1; // Spacing between two words.
		for(int i=0;i<word.length();i++)
		{
			DrawLetter(font,word.charAt(i)+"",dx,dy);
			dx+=space;
		}
	}
	
	public void DrawSprite(SpriteMap spriteMap, int selectedX, int selectedY, int dx, int dy)
	{
		//TODO: Draw the specific sprite from the sprite map.
		int widthOfOneSprite=spriteMap.getWidthOfOneSprite(); //16, width of the image with sprites.
		int heightOfOneSprite=spriteMap.getHeightOfOneSprite(); //16, height of the image with sprites.
		selectedX=selectedX*widthOfOneSprite; // 0*16, getting the index of position in the row and adding the offset to move between sprites in the row.
		selectedY=selectedY*heightOfOneSprite; // 0*16, getting the index of position in the column and adding the offset to move between sprites in the column.
		int scaleX=spriteMap.getWidthOfImage()/widthOfOneSprite; // 64/16 = 4, getting the scale of minimizing the x to get the sprite.
		int scaleY=spriteMap.getHeightOfImage()/heightOfOneSprite; //64/16 = 4, getting the scale of minimizing the y to get the sprite.
		
		for(int y=0;y<spriteMap.getHeightOfImage();y++) 
		{
			for(int x=0;x<spriteMap.getWidthOfImage();x++) 
			{
				int index=(selectedX+x/scaleX)+(selectedY+y/scaleY)*spriteMap.getWidthOfImage(); // Getting the index of pixel in the sprite map.
				int color=spriteMap.getPixelsOfImage()[index]; // Getting the color from sprite map.
				if(((color>>24)& 0xff)!=0)
				{
					try 
					{
						SetPixel(dx+x,y+dy,color); // Setting the pixel on the screen.
					}
					catch(Exception e) 
					{
						continue;
					}
				}
			}
		}
	}
	
	public void DrawImage(ImageBase img, int dx,int dy) 
	{
		//TODO: Draw the image on the screen.
		int widthOfImage=img.getWidthOfImage(); // Getting the width of the image.
		int heightOfImage=img.getHeightOfImage(); // Getting the height of the image.
		
		if(isSafetyToRender(widthOfImage,heightOfImage,dx,dy))
		{
			for(int y=0;y<heightOfImage;y++) 
			{
				for(int x=0;x<widthOfImage;x++) 
				{
					int color=img.getPixelsOfImage()[x+y*widthOfImage]; // Getting the color from image pixels.
					if(((color>>24)& 0xff)!=0)
					{
						SetPixel(x+dx,y+dy,img.getPixelsOfImage()[x+y*widthOfImage]); // Setting the pixel.
					}
				}
			}
		}
	}
	
	public void DrawTile(TileMap tile, int selectedX,int selectedY,int dx, int dy) 
	{
		//TODO: Draw the specific tile from the tile map.
		int heightOfOneTile=tile.getHeightOfOneTile();  // 16, getting the height of one tile.
		int widthOfOneTile=tile.getWidthOfOneTile();    // 16, getting the width of one tile.
		selectedX=selectedX*widthOfOneTile; // Getting index of selected position in the row.
		selectedY=selectedY*heightOfOneTile; // Getting index of selected position in the column.
		int scaleOfY=tile.getImage().getHeight()/heightOfOneTile; //64/16= 4, scale of minimizing the x.
		int scaleOfX=tile.getImage().getWidth()/widthOfOneTile; //64/16 = 4, scale of minimizing the y.
		
		if(isSafetyToRender(widthOfOneTile,heightOfOneTile,dx,dy)) 
		{
			for(int y=0;y<tile.getImage().getHeight();y++) 
			{
				for(int x=0;x<tile.getImage().getWidth();x++) 
				{
					int index=(selectedX+x/scaleOfX)+(selectedY+y/scaleOfY)*tile.getImage().getWidth(); // Getting the index of the color from the tile map.
					int color=tile.getPixelsOfImage()[index]; // Getting the color.
					if(((color>>24)&0xff)!=0)
					{
						try
						{
							SetPixel(x+dx,y+dy,color); // Setting the pixel.
						}catch(Exception e) 
						{
							break;
						}
					}
				}
			}
		}
	}
	
	private boolean isSafetyToRender(int width, int height, int dx, int dy) 
	{
		//TODO: Check safety of rendering the pixel.
		if(dy < -width || dy < -height || dx >= widthOfScreen || dx >= heightOfScreen) 
		{
			return false;
		}
		return true;
	}
	
	public void DrawRectangle(int width, int height, int dx, int dy, int color)
	{
		//TODO: Draw the rectangle on the screen.
		if(isSafetyToRender(width,height,dx,dy))
		{		
			for(int y=0;y<height;y++)
			{
				for(int x=0;x<width;x++)
				{
					if(y==0 || y==height-1) 
					{
						//TODO: Drawing the two rows of the pixel, one top and bottom.
						// ----------
						// 
						// ----------
						SetPixel(dx+x,dy+y,color); // Setting the pixel.
					}
					else 
					{

						//TODO: Drawing the pixel one pixel on the left side and right side of the rectangle.
						// |      |
						// |      |
						SetPixel((dx+x),(y+dy),color); // Setting the pixel on the left.
						SetPixel((dx+x+width),(y+dy),color); // Setting the pixel on the right.
						break;
					}
				}
			}
		}
	}
	
	public void FillRectangle(int width, int height, int dx, int dy, int color)
	{
		//TODO: Draw the rectangle and fill it.
		for(int y=0;y<height;y++) 
		{
			for(int x=0;x<width;x++)
			{
				SetPixel(x+dx,y+dy,color);
			}
		}
	}
	//END REGION
	//END REGION
}
