package mazegen;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

public class Maze {
	private boolean[][] maze;
	private final Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
	
	public Maze(int size)
	{
		maze = new boolean[size][size];
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				maze[i][j] = false;
	}
	
	public boolean[][] getMaze()
	{
		return maze;
	}
	
	public void depthFirst()
	{
		int size = maze.length;
		Random random = new Random();
		Field currentField = new Field(random.nextInt((size-1)/2)*2 +1, random.nextInt((size-1)/2)*2 +1);
		maze[currentField.X()][currentField.Y()] = true;
		Stack<Field> fieldStack = new Stack<Field>();
		fieldStack.push(currentField);
		ArrayList<Direction> dirVisitOrder = new ArrayList<Direction>();
		dirVisitOrder.add(Direction.UP);
		dirVisitOrder.add(Direction.DOWN);
		dirVisitOrder.add(Direction.LEFT);
		dirVisitOrder.add(Direction.RIGHT);
		Field targetField = currentField;
		
		while (! fieldStack.isEmpty())
		{
			currentField = fieldStack.pop();
			Collections.shuffle(dirVisitOrder);
			for (Direction dir: dirVisitOrder)
			{
				targetField = currentField.neighbour(dir).neighbour(dir);
				if (targetField.isInBounds(size) && ! maze[targetField.X()][targetField.Y()] )
				{
					maze[currentField.neighbour(dir).X()][currentField.neighbour(dir).Y()] = true;
					maze[targetField.X()][targetField.Y()] = true;
					fieldStack.push(currentField);
					fieldStack.push(targetField);
					break;
				}
			}
		}
		fillLastLineOfMaze();
		fillLastRowOfMaze();
		makeEntrence();
		makeExit();
	}
	
	public void addCrossings(double percentage)
	{
		Random random = new Random();
		for (int i=1; i<maze.length-1; i++)
			for (int j=1; j<maze.length-1; j++)
				if (! maze[i][j])
					if (! maze[i-1][j] && ! maze[i+1][j] && maze[i][j-1] && maze[i][j+1]  || ! maze[i][j-1] && ! maze[i][j+1] && maze[i+1][j] && maze[i-1][j])
							if (random.nextDouble() < percentage)
								maze[i][j]= true; 			
	}
	
	public void removeDeadEnds()
	{
		ArrayList<Direction> dirVisitOrder = new ArrayList<Direction>();
		dirVisitOrder.add(Direction.UP);
		dirVisitOrder.add(Direction.DOWN);
		dirVisitOrder.add(Direction.LEFT);
		dirVisitOrder.add(Direction.RIGHT);
		int size = maze.length;
		
		for (int y=0; y<maze.length; y++)
		{
			for (int x=0; x<maze.length; x++)
			{
				Field currentField = new Field(x,y);
				int neighbours = 0;
				Direction origin = Direction.UP;
				for (Direction dir: directions)
				{
					Field targetField = currentField.neighbour(dir);
					if (targetField.isInBounds(size))
						if (maze[targetField.X()][targetField.Y()])
						{
							origin = dir;
							neighbours++;
						}
				}
				if (neighbours == 1)
				{
					Collections.shuffle(dirVisitOrder);
					for (Direction dir: dirVisitOrder)
					{
						if (! dir.equals(origin) && currentField.neighbour(dir).neighbour(dir).isInBounds(size))
						{
							maze[currentField.neighbour(dir).X()][currentField.neighbour(dir).Y()] = true;
							break;
						}
					}
				}
				
			}
		}
	}
	
	private void fillLastLineOfMaze()
	{
		for (int i=1; i<maze.length-1; i++)
		{
			if (maze[i][maze.length-3] && !maze[i-1][maze.length-2])
				maze[i][maze.length-2] = true;
		}
	}
	
	private void fillLastRowOfMaze()
	{
		for (int i=1; i<maze.length-1; i++)
		{
			if (maze[maze.length-3][i] && !maze[maze.length-2][i-1])
				maze[maze.length-2][i] = true;
		}
	}
	
	private void makeEntrence()
	{
		boolean entrenceFound = false;
		Random random = new Random();
		int x;
		while (!entrenceFound)
		{
			x = random.nextInt(maze.length);
			if (maze[x][1])
			{
				maze[x][0] = true;
				return;
			}
		}
	}
	
	private void makeExit()
	{
		boolean entrenceFound = false;
		Random random = new Random();
		int x;
		while (!entrenceFound)
		{
			x = random.nextInt(maze.length);
			if (maze[x][maze.length-2])
			{
				maze[x][maze.length-1] = true;
				return;
			}
		}
	}
	
	public void print()
	{
		for (int i=0; i<maze.length; i++)
		{
			String line = "";
			for (int j=0; j<maze.length; j++)
				if (maze[j][i])
					line += "O";
				else
					line += "X";
			System.out.println(line);
		}		
	}
	
	public void makeRim()
	{
		for (int i=0; i<maze.length; i++)
		{
			maze[0][i] = false;
			maze[i][0] = false;
			maze[maze.length-1][i] = false;
			maze[i][maze.length-1] = false;
		}
	}
}
