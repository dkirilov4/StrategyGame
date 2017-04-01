package client;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

public class Grid 
{
	Graphics g;
	private final int tileSize = 32;
	private int gridWidth, gridHeight;
	
	private Tile[][] map;
	private BufferedImage tileMap;
	
	public Grid(String filePath, String tilesetPath)
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			tileMap = ImageIO.read(new File(tilesetPath));
			
		    gridWidth = Integer.parseInt(br.readLine());
			gridHeight = Integer.parseInt(br.readLine());
			
			map = new Tile[gridHeight][gridWidth];
			
			String delimiter = " ";
			for (int row = 0; row < gridHeight; row++)
			{
				String line = br.readLine();
				String[] tokens = line.split(delimiter);
				for (int col = 0; col < gridWidth; col++) {
					map[row][col] = new Tile(col*tileSize, row*tileSize, Integer.parseInt(tokens[col]), tileMap);
				}
			}
			
			//printGrid();
		}
		catch (Exception e) 
		{ 
			System.out.println("File not found");
			System.exit(1);
		}
	}
	
	public void printGrid()
	{
		for (int row = 0; row < gridHeight; row++)
		{
			for (int col = 0; col < gridWidth; col++) 
			{
				System.out.print("" + row + ", " + col + "   ");
			}
			System.out.println("");
		}
	}
	
	public void setGraphics(Graphics g)
	{
		this.g = g;
	}
	
	public Tile getTile(int row, int col)
	{
		return map[row][col];
	}
	
	public void resetTiles()
	{
		for (int row = 0; row < gridHeight; row++)
		{
			for (int col = 0; col < gridWidth; col++)
			{
				Tile rc = map[row][col];
				rc.setType(0);
			}
		}
	}
	
	public void setTileColor(int x, int y, Color c)
	{
		System.out.println("Setting color of tile at x: " + x + " y: " + y);
		Game.g.setColor(c);
		Game.g.fillRect(x, y, tileSize, tileSize);
	}
	
	public int getGridHeight() {
		return gridHeight;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void render(Graphics g)
	{
		for (int row = 0; row < gridHeight; row++)
		{
			for (int col = 0; col < gridWidth; col++)
			{
				map[row][col].render(row, col, g);;
			}
		}
	}

	public void tick()
	{
		
	}
}
