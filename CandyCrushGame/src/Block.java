import java.awt.Color;

public class Block {
	
	private int x, y;
	private final int width, height;
	private final int ID;
	private Color color;
	
	public Block(int x, int y, int size) {
		this.ID = Main.getGame().newID();
		
		width = size;
		height = size;
		this.x = x;
		this.y = y;
		color = Color.RED;
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
