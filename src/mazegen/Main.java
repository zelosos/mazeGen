package mazegen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main
{

	public static void main(String[] args)
	{
		createMazeDepthFirst(11, 2, "tinyDepthFirst");
		createMazeDepthFirstNoDeadEnds(11, 2, "tinyDepthFirstNoDeadEnds");
		createMazeDepthFirstWithCrossings(11, 0.05, 2, "tinyDepthFirstWithCrossings");
		
		createMazeDepthFirst(101, 2, "smallDepthFirst");
		createMazeDepthFirstNoDeadEnds(101, 2, "smallDepthFirstNoDeadEnds");
		createMazeDepthFirstWithCrossings(101, 0.001, 2, "smallDepthFirstWithCrossings");
		
		createMazeDepthFirst(1001, 2, "middleDepthFirst");
		createMazeDepthFirstNoDeadEnds(1001, 2, "middleDepthFirstNoDeadEnds");
		createMazeDepthFirstWithCrossings(10001, 0.001, 2, "middleDepthFirstWithCrossings");
		
		createMazeDepthFirst(1001, 2, "bigDepthFirst");
		createMazeDepthFirstNoDeadEnds(10001, 2, "bigDepthFirstNoDeadEnds");
		createMazeDepthFirstWithCrossings(10001, 0.001, 2, "bigDepthFirstWithCrossings");
	}
	
	private static void createMazeDepthFirst(int size, int amount, String name)
	{
		for (int i=0; i<amount; i++)
		{
			Maze maze = new Maze(size);
			System.out.println("Create Maze ...");
			maze.depthFirst();
			System.out.println("Saving ...");
			String fileName = name + i +".png";
			saveImage(maze, fileName);
			System.out.println("finished");
		}
	}
	
	private static void createMazeDepthFirstWithCrossings(int size, double crossingsPercentage, int amount, String name)
	{
		for (int i=0; i<amount; i++)
		{
			Maze maze = new Maze(size);
			System.out.println("Create Maze ...");
			maze.depthFirst();
			System.out.println("Add Crossings ...");
			maze.addCrossings(crossingsPercentage);
			System.out.println("Saving ...");
			String fileName = name + i +".png";
			saveImage(maze, fileName);
			System.out.println("finished");
		}
	}
	
	private static void createMazeDepthFirstNoDeadEnds(int size, int amount, String name)
	{
		for (int i=0; i<amount; i++)
		{
			Maze maze = new Maze(size);
			System.out.println("Create Maze ...");
			maze.depthFirst();
			System.out.println("Removing Deadends ...");
			maze.removeDeadEnds();
			System.out.println("Saving ...");
			String fileName = name + i +".png";
			saveImage(maze, fileName);
			System.out.println("finished");
		}
	}

	private static void saveImage(Maze maze, String fileName)
	{
		int size = maze.getMaze().length;
		//create buffered image object img
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		//file object
		File file = new File(fileName);
		int p = 0;
		for(int y = 0; y < size; y++)
		{
			for(int x = 0; x < size; x++)
			{
				if (maze.getMaze()[x][y])
				{
					if (y == 0)
						p = pixel(0, 0, 255);
						//p = pixel(255, 255, 255);
					else if (y == size-1)
						p = pixel(0, 255, 0);
						//p = pixel(255, 255, 255);
					else
						//p = pixel(0, 255, 0);
						p = pixel(255, 255, 255);
				}
				else
					//p = pixel(0, 255, 0);
					p = pixel(0, 0, 0);
				img.setRGB(x, y, p);
			}
		}
		try
		{
			ImageIO.write(img, "png", file);
		}
		catch(IOException e){
			System.out.println("Error: " + e);
		}
	}
	
	private static int pixel(int red, int green, int blue)
	{
		return (red<<16) | (green<<8) | blue;
	}
}
