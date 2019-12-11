import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game implements Runnable {
	
	private Map<Integer, Block> blocks = new HashMap<Integer, Block>();
	
	private boolean clicked;
	private int currentClicked;
	private boolean playing;
	
	private long lastRowAddition;
	private int rowAdditionInterval;
	
	private static int fps, ups;
	private Window window;
	private Thread thread;
	private static Random random;
	private MouseHandler mouseHandler;
	private Board board;
	
	public static final int BLOCK_SIZE = 40;
	
	private static int IDTracker;
	
	/**
	 * Creates new game in the specified window.
	 * @param window
	 */
	public Game(Window window) {
		this.window = window;
		
		this.IDTracker = 0;
		this.clicked = false;
		
		mouseHandler = new MouseHandler();
		random = new Random();
		thread = new Thread(this);
		board = new Board(this);
		
		currentClicked = board.EMPTY;
		
		window.getCanvas().addMouseListener(mouseHandler);
		
		lastRowAddition = System.currentTimeMillis();
		rowAdditionInterval = 3000;
		playing = true;
		
		thread.start();
	}
	
	/**
	 * Returns Map<Integer, Block> of all registered blocks paired with their ID number.
	 * @return
	 */
	public Map<Integer, Block> getBlocks() {
		return blocks;
	}
	
	/**
	 * Returns block with specified ID.
	 * @param ID
	 * @return
	 */
	public Block getBlock(int ID) {
		return blocks.get(ID);
	}
	
	/**
	 * Returns default block size in pixels.
	 * @return
	 */
	public static int getBlockSize() {
		return BLOCK_SIZE;
	}
	
	/**
	 * Returns a random block color.
	 * @return
	 */
	public static Color randomColor() {
		
		Color color;
		
		int c = random.nextInt(4);
		
		switch(c) {
			case 0:
				color = Color.RED;
				break;
			case 1:
				color = Color.BLUE;
				break;
			case 2:
				color = Color.ORANGE;
				break;
			case 3:
				color = Color.CYAN;
				break;
			default:
				color = Color.BLACK;
				break;
		}
		
		return color;
	}
	
	/**
	 * Creates a block with a random color on the current board at the specified pixel coordinates.
	 * Returns ID of new block.
	 * @param x
	 * @param y
	 * @return
	 */
	public int addBlock(int x, int y) {
		
		Block block = new Block(x, y, getBoard());
		blocks.put(block.getID(), block);
		return block.getID();
	}
	
	public void removeBlock(int blockID) {
		
		blocks.remove(blockID);
	}
	
	/**
	 * Method called when mouse is clicked on a block.
	 * @param blockID
	 */
	public void blockClicked(int blockID) {
		
		clicked = true;
		currentClicked = blockID;
	}
	
	/**
	 * Method called when mouse is released on a block.
	 * @param blockID
	 */
	public void blockReleased(int blockID) {
		clicked = false;
		
		System.out.println(blockID);
	}
	
	/**
	 * Returns next available ID number.
	 * @return
	 */
	public static int newID() {
		return IDTracker++;
	}
	
	/**
	 * Main game loop.
	 * Called every game tick.
	 */
	private void update() { //main game loop
		
		if (System.currentTimeMillis() - lastRowAddition > rowAdditionInterval) {
			if (!getBoard().fillTopRow()) {
				playing = false;
			}
			lastRowAddition = System.currentTimeMillis();
		}
		
		if (clicked) {
			int mouseX = MouseInfo.getPointerInfo().getLocation().x - Main.getWindow().getCanvas().getLocationOnScreen().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y - Main.getWindow().getCanvas().getLocationOnScreen().y;
			
			Block block;
			
			for (Integer blockID : getBoard().getBlocks()) {
				if (blockID != getBoard().EMPTY) {
					block = getBlock(blockID);
					if (mouseX >= block.getX() && mouseX <= block.getX() + block.getWidth() && mouseY >= block.getY() && mouseY <= block.getY() + block.getHeight()) {
						if (board.getNeighbors(currentClicked).contains(blockID)) {
							board.attemptSwap(currentClicked, blockID);
							board.dropBlocks();
							clicked = false;
							currentClicked = Board.EMPTY;
						}
						break;
					}
				}
			}
		}
		
		for (Integer blockID : getBoard().getBlocks()) {
			if (blockID != getBoard().EMPTY) {
				blocks.get(blockID).updateAnimation();
			}
		}
	}
	
	public Board getBoard() {
		
		return board;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void setPlaying(boolean state) {
		this.playing = state;
	}

	@Override
	public void run() {
		int updatesPerSecond = 20;
		int framesPerSecond = 20;
		long gameSkipTicks = 1000 / updatesPerSecond;
		long frameSkipTicks = 1000 / framesPerSecond;
		long maxFrameSkips = 5;
		long nextGameTick = System.currentTimeMillis();
		long nextFrameTick = System.currentTimeMillis();
		long time = System.currentTimeMillis();
		int loops;
		int frames = 0;
		int updates = 0;
		while (true) {
			loops = 0;
			while (System.currentTimeMillis() > nextGameTick && loops < maxFrameSkips) {
				if (playing) {
					update();
				}
				nextGameTick += gameSkipTicks;
				loops ++;
				updates ++;
			}
			if (System.currentTimeMillis() > nextFrameTick) {
				nextFrameTick += frameSkipTicks;
				window.getCanvas().repaint();
				frames ++;
			}
			if (time + 1000 <= System.currentTimeMillis()) {
				time += 1000;
				fps = frames;
				ups = updates;
				frames = 0;
				updates = 0;
			}
		}
	}

}
