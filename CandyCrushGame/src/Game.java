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
	
	public Map<Integer, Block> getBlocks() {
		return blocks;
	}
	
	public Block getBlock(int ID) {
		return blocks.get(ID);
	}
	
	public static int getBlockSize() {
		return BLOCK_SIZE;
	}
	
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
	
	public int addBlock(int x, int y) {
		Block block = new Block(x, y, getBoard());
		blocks.put(block.getID(), block);
		return block.getID();
	}
	
	public void blockClicked(int blockID) {
		clicked = true;
		currentClicked = blockID;
		
		System.out.println(blockID);
		board.swapBlocks(1, 2);
	}
	
	public void blockReleased(int blockID) {
		clicked = false;
		
		System.out.println(blockID);
	}
	
	public static int newID() {
		return IDTracker++;
	}
	
	private void update() { //main game loop
		
		if (clicked) {
			int mouseX = MouseInfo.getPointerInfo().getLocation().x - Main.getWindow().getCanvas().getLocationOnScreen().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y - Main.getWindow().getCanvas().getLocationOnScreen().y;
			
			Block block;
			for (Integer blockID : Main.getGame().getBlocks().keySet()) {
				block = Main.getGame().getBlock(blockID);
				if (mouseX >= block.getX() && mouseX <= block.getX() + block.getWidth() && mouseY >= block.getY() && mouseY <= block.getY() + block.getHeight()) {
					int index = board.findBlock(blockID);
					//swap this block with the currentClicked block
					break;
				}
			}
			//System.out.println(MouseInfo.getPointerInfo().getLocation().y - Main.getWindow().getCanvas().getLocationOnScreen().y);
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
