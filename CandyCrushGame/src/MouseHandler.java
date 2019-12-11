import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		
		Point point = e.getPoint();
		if (point.x > Main.getWindow().getWidth()/2 - 28 && point.x < Main.getWindow().getWidth()/2 - 28 + 65) {
			if (point.y > Main.getWindow().getHeight() - 50 && point.y < Main.getWindow().getHeight() - 50 + 20) {
				Main.getGame().setPlaying(true);
				Main.getGame().getBoard().refreshBoard();
				Main.getGame().getBoard().resetScore();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		click(e.getPoint(), true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		click(e.getPoint(), false);
	}
	
	private void click(Point point, boolean state) {
		
		Block block;
		
		try {
			for (Integer blockID : Main.getGame().getBoard().getBlocks()) {
				if (blockID != Main.getGame().getBoard().EMPTY) {
					block = Main.getGame().getBlock(blockID);
					if (point.x >= block.getX() && point.x <= block.getX() + block.getWidth() && point.y >= block.getY() && point.y <= block.getY() + block.getHeight()) {
						if (state) {
							Main.getGame().blockClicked(blockID);
						} else {
							Main.getGame().blockReleased(blockID);
						}
						break;
					}
				}
			}
		} catch (Exception e) { }
		
		if (!state) {
			Main.getGame().blockReleased(Board.EMPTY);
		}
		
	}

}
