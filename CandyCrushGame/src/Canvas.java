import java.awt.Color;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	
	/**
	 * Default constructor.
	 */
	public Canvas() {
		
	}
	
	/**
	 * Draw method.
	 */
	public void paintComponent(Graphics g) {
		
		g.clearRect(0, 0, Main.getWindow().getFrame().getWidth(), Main.getWindow().getFrame().getHeight());
		if (Main.getGame().isPlaying()) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(255, 201, 194));
		}
		g.fillRect(0, 0, Main.getWindow().getFrame().getWidth(), Main.getWindow().getFrame().getHeight());
		
		char[] scoreData = ("Score: " + Integer.toString(Main.getGame().getBoard().getScore())).toCharArray();
		g.setColor(Color.BLACK);
		g.drawChars(scoreData, 0, scoreData.length, (Main.getWindow().getWidth()/2) - (g.getFontMetrics().charsWidth(scoreData, 0, scoreData.length) / 2), 50);
		
		char[] restartData = ("RESTART").toCharArray();
		g.setColor(Color.BLACK);
		g.drawChars(restartData, 0, restartData.length, Main.getWindow().getWidth()/2 - 23, Main.getWindow().getHeight() - 35);
		g.drawRect(Main.getWindow().getWidth()/2 - 28, Main.getWindow().getHeight() - 50, 65, 20);
		
		try {
			for (int blockID : Main.getGame().getBoard().getBlocks()) {
				if (blockID != Main.getGame().getBoard().EMPTY) {
					Block block = Main.getGame().getBlock(blockID);
					g.setColor(block.getColor());
					g.fillRect(block.getDrawX(), block.getDrawY(), block.getWidth(), block.getHeight());
				}
			}
		} catch (Exception e) { }
		
	}
}
