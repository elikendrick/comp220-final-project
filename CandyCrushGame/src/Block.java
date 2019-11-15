import java.awt.Color;

public class Block {
	
	private int x, y;
	private final int width, height;
	private final int ID;
	private Color color;
	private Board board;
	
	public Block(int x, int y, Board board) {
		
		this(x, y, Game.getBlockSize(), board.getGame().randomColor(), board);
	}
	
	public Block(int x, int y, Color color, Board board) {
		
		this(x, y, Game.getBlockSize(), color, board);
	}
	
	public Block(int x, int y, int size, Color color, Board board) {
		
		this.ID = Main.getGame().newID();
		
		this.board = board;
		
		width = size;
		height = size;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
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
