package mazegen;

import java.util.ArrayList;

import javax.security.auth.x500.X500Principal;

public class Field
{
	private int x;
    private int y;
    private boolean marked;

    public Field(int x, int y)
    {
        this.x = x;
        this.y = y;
        marked = false;
    }
    
    public int X()
    {
    	return x;
    }
    
    public int Y()
    {
    	return y;
    }
    
    public boolean isMarked()
    {
    	return marked;
    }
    
    public void mark()
    {
    	marked = true;
    }
    
    public void unmark()
    {
    	marked = false;
    }
    
    public Field neighbour(Direction dir)
    {
    	return new Field(x + dir.x, y + dir.y);
    }
    
    public ArrayList<Field> allNeighbours()
    {
    	ArrayList<Field> neighbours = new ArrayList<Field>();
    	neighbours.add(neighbour(Direction.UP));
    	neighbours.add(neighbour(Direction.DOWN));
    	neighbours.add(neighbour(Direction.LEFT));
    	neighbours.add(neighbour(Direction.RIGHT));
    	return neighbours;
    }
    
    public boolean isInBounds(int size)
    {
    	return (x > 0 && x < size-1 && y > 0 && y < size-1);
    }
    
    public String toString()
    {
    	return "x: " + x + " y: " + y + " marked: " + marked;
    }
}
