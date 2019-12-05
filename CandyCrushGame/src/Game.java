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
	
	private static int fps, ups;
	private Window window;
	private Thread thread;
	private static Random random;
	private KeyHandler keyHandler;
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
		
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		random = new Random();
		thread = new Thread(this);
		board = new Board(this);
		
		currentClicked = board.EMPTY;
		
		//window.getFrame().addKeyListener(keyHandler);
		window.getCanvas().addKeyListener(keyHandler);
		window.getCanvas().addMouseListener(mouseHandler);
		
		thread.start();
		
		//addBlock(50, 100);
		//addBlock(200, 300);
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
		
		int c = random.nextInt(6);
		
		switch(c) {
			case 0:
				color = Color.RED;
				break;
			case 1:
				color = Color.BLUE;
				break;
			case 2:
				color = Color.YELLOW;
				break;
			case 3:
				color = Color.GREEN;
				break;
			case 4:
				color = Color.ORANGE;
				break;
			case 5:
				color = Color.MAGENTA;
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
		System.out.println("Before removal: " + blocks.size());
		blocks.remove(blockID);
		System.out.println("After removal: " + blocks.size());
	}
	
	/**
	 * Method called when mouse is clicked on a block.
	 * @param blockID
	 */
	public void blockClicked(int blockID) {
		clicked = true;
		currentClicked = blockID;
		
		System.out.println(blockID);
		//board.swapBlocks(1, 2);
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
		
		if (clicked) {
			int mouseX = MouseInfo.getPointerInfo().getLocation().x - Main.getWindow().getCanvas().getLocationOnScreen().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y - Main.getWindow().getCanvas().getLocationOnScreen().y;
			System.out.println("Clicked");
			Block block;
			//for (Integer blockID : Main.getGame().getBlocks().keySet()) {
			for (Integer blockID : Main.getGame().getBoard().getBlocks()) {
				block = Main.getGame().getBlock(blockID);
				if (mouseX >= block.getX() && mouseX <= block.getX() + block.getWidth() && mouseY >= block.getY() && mouseY <= block.getY() + block.getHeight()) {
					System.out.println("Over block: " + blockID);
					for (int blockNeighbs : board.getNeighbors(currentClicked)) {
						System.out.print(blockNeighbs + " ");
					}
					System.out.println("for block: " + currentClicked);
					board.printBoardState();
					System.out.println("Total rendered: " + blocks.size());
					if (board.getNeighbors(currentClicked).contains(blockID)) {
						System.out.println("Neighboring block clicked");
						board.attemptSwap(currentClicked, blockID);
						board.tidyBoard();
						clicked = false;
						currentClicked = Board.EMPTY;
					}
					//swap this block with the currentClicked block
					break;
				}
			}
			//System.out.println(MouseInfo.getPointerInfo().getLocation().y - Main.getWindow().getCanvas().getLocationOnScreen().y);
		}
		
		for (Integer blockID : getBoard().getBlocks()) {
			blocks.get(blockID).updateAnimation();
		}
	}
	
	public Board getBoard() {
		
		return board;
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
				update();
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
