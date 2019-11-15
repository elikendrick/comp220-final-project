import java.awt.Color;
import java.awt.Point;

public class Block {
	
	private Point location;
	private final int width, height;
	private final int ID;
	private Color color;
	private Board board;
	
	public Block(int x, int y, Board board) {
		
		this(x, y, Game.getBlockSize(), Game.randomColor(), board);
	}
	
	public Block(int x, int y, Color color, Board board) {
		
		this(x, y, Game.getBlockSize(), color, board);
	}
	
	public Block(int x, int y, int size, Color color, Board board) {
		
		this.ID = Main.getGame().newID();
		
		this.board = board;
		
		width = size;
		height = size;
		this.location = new Point(x, y);
		this.color = color;
	}
	
	public void move(int x, int y) {
		this.location.setLocation(x, y);
	}
	
	public void move(Point location) {
		this.location = location;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getX() {
		return location.x;
	}
	
	public int getY() {
		return location.y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getID() {
		return ID;
	}

}
