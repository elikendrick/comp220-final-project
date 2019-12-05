import javax.swing.JFrame;

public class Window {
	
	private JFrame frame;
	private Canvas canvas;
	private int width, height;
	
	public Window(int width, int height) {
		frame = new JFrame();
		
		frame.setTitle("Candy Crush");
		frame.setUndecorated(false);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		canvas = new Canvas();
		frame.add(canvas);
		
		this.width = frame.getContentPane().getWidth();
		this.height = frame.getContentPane().getHeight();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}

}
