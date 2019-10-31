import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	
	//private Thread thread;
	//private Random random;
	//private KeyHandler keyHandler;
	//private MouseHandler mouseHandler;
	//private JFrame frame;
	
	//private static int fps, ups;
	
	public Canvas() {
		//this.frame = frame;
		//random = new Random();
		//keyHandler = new KeyHandler();
		//mouseHandler = new MouseHandler();
		//thread = new Thread(this);
		
		//frame.addKeyListener(keyHandler);
		//frame.addMouseListener(mouseHandler);
		
		//thread.start();
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, Main.getWindow().getFrame().getWidth(), Main.getWindow().getFrame().getHeight());
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Main.getWindow().getFrame().getWidth(), Main.getWindow().getFrame().getHeight());
		
		for (Block block : Main.getGame().getBlocks().values()) {
			g.setColor(block.getColor());
			g.fillRect(block.getX(), block.getY(), block.getWidth(), block.getHeight());
		}
	}
	
	/*public void update() {
		
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
				repaint();
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
	}*/

}
