import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game implements Runnable {
	
	private Map<Integer, Block> blocks = new HashMap<Integer, Block>();
	
	private static int fps, ups;
	private Window window;
	private Thread thread;
	private Random random;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	
	public final int BLOCK_SIZE = 20;
	
	private static int IDTracker;
	
	public Game(Window window) {
		this.window = window;
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		random = new Random();
		thread = new Thread(this);
		
		//window.getFrame().addKeyListener(keyHandler);
		window.getCanvas().addKeyListener(keyHandler);
		window.getCanvas().addMouseListener(mouseHandler);
		
		this.IDTracker = 0;
		
		thread.start();
		
		addBlock(50, 100, BLOCK_SIZE);
		addBlock(200, 300, BLOCK_SIZE);
	}
	
	public Map<Integer, Block> getBlocks() {
		return blocks;
	}
	
	public Block getBlock(int ID) {
		return blocks.get(ID);
	}
	
	public int addBlock(int x, int y, int size) {
		Block block = new Block(x, y, size);
		blocks.put(block.getID(), block);
		return block.getID();
	}
	
	public void blockClicked(int blockID) {
		System.out.println(blockID);
	}
	
	public void blockReleased(int blockID) {
		System.out.println(blockID);
	}
	
	public static int newID() {
		return IDTracker++;
	}
	
	private void update() { //main game loop
		
	}

	@Override
	public void run() {
		int updatesPerSecond = 50;
		int framesPerSecond = 50;
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
