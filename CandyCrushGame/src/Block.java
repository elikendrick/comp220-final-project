import java.awt.Color;
import java.awt.Point;

public class Block {
	
	private Point location;
	private Point drawLocation;
	private final int width, height;
	private final int ID;
	private Color color;
	private Board board;
	
	/**
	 * Animation speed (pixels per tick)
	 */
	private static final int moveSpeed = 10;
	
	/**
	 * Constructor creates new block with unique ID, default size, and random color.
	 * @param x
	 * @param y
	 * @param board
	 */
	public Block(int x, int y, Board board) {
		
		this(x, y, Game.getBlockSize(), Game.randomColor(), board);
	}
	
	/**
	 * Constructor creates new block with unique ID and default size.
	 * @param x
	 * @param y
	 * @param color
	 * @param board
	 */
	public Block(int x, int y, Color color, Board board) {
		
		this(x, y, Game.getBlockSize(), color, board);
	}
	
	/**
	 * Constructor creates new block with unique ID.
	 * @param x
	 * @param y
	 * @param size
	 * @param color
	 * @param board
	 */
	public Block(int x, int y, int size, Color color, Board board) {
		
		this.ID = Main.getGame().newID();
		
		this.board = board;
		
		width = size;
		height = size;
		this.location = new Point(x, y);
		this.drawLocation = new Point(x, y);
		this.color = color;
	}
	
	/**
	 * Animates the motion of the block.
	 * Should be called every game tick.
	 */
	public void updateAnimation() {
		if (!this.drawLocation.equals(this.location)) {
			int xOffset = location.x - drawLocation.x;
			int yOffset = location.y - drawLocation.y;
			if (Math.abs(xOffset) <= moveSpeed) {
				drawLocation.x = location.x;
			} else if (xOffset > 0) {
				drawLocation.x = drawLocation.x + moveSpeed;
			} else {
				drawLocation.x = drawLocation.x - moveSpeed;
			}
			if (Math.abs(yOffset) <= moveSpeed) {
				drawLocation.y = location.y;
			} else if (yOffset > 0) {
				drawLocation.y = drawLocation.y + moveSpeed;
			} else {
				drawLocation.y = drawLocation.y - moveSpeed;
			}
		}
	}
	
	/**
	 * Updates block location.
	 * @param x
	 * @param y
	 */
	public void move(int x, int y) {
		this.drawLocation.setLocation(this.location.x, this.location.y);
		this.location.setLocation(x, y);
	}
	
	/**
	 * Updates block location.
	 * @param location
	 */
	public void move(Point location) {
		this.drawLocation.setLocation(this.location.x, this.location.y);
		this.location = location;
	}
	
	/**
	 * Returns block color.
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns block X coordinate.
	 * @return
	 */
	public int getX() {
		return location.x;
	}
	
	/**
	 * Returns block Y coordinate.
	 * @return
	 */
	public int getY() {
		return location.y;
	}
	
	/**
	 * Returns block visual X location (for animation).
	 * @return
	 */
	public int getDrawX() {
		return drawLocation.x;
	}
	
	/**
	 * Returns block visual Y location (for animation).
	 * @return
	 */
	public int getDrawY() {
		return drawLocation.y;
	}
	
	/**
	 * Returns block width (in pixels).
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns block height (in pixels).
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns block ID.
	 * @return
	 */
	public int getID() {
		return ID;
	}

}
