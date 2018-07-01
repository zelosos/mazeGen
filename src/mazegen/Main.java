package mazegen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main
{

	public static void main(String[] args)
	{
		Maze maze = new Maze(1001);
		System.out.println("Create Maze ...");
		maze.depthFirst();
		System.out.println("Add Crossings ...");
		maze.addCrossings(0.001);
		System.out.println("Saving ...");
		saveImage(maze, "smallMaze.png");
		//maze.print();
		System.out.println("finished");
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
