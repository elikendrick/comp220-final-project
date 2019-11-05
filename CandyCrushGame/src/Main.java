import javax.swing.SwingUtilities;

public class Main {
	
	private static Window window;
	private static Game game, hello;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				window = new Window(1000, 600);
				game = new Game(window);
			}
			
		});
	}
	
	public static Game getGame() {
		return game;
	}
	
	public static Window getWindow() {
		return window;
	}

}
