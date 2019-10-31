import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		System.out.println(point);
		Block block;
		for (Integer blockID : Main.getGame().getBlocks().keySet()) {
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

}
