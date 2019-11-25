import javax.swing.SwingUtilities;

public class Main {
	
	private static Window window;
	private static Game game;
	
	/**
	 * Main method creates new window and game objects
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				window = new Window(1000, 600);
				game = new Game(window);
			}
			
		});
	}
	
	/**
	 * Returns current instance of Game.
	 * @return
	 */
	public static Game getGame() {
		return game;
	}
	
	/**
	 * Returns current instance of Window.
	 * @return
	 */
	public static Window getWindow() {
		return window;
	}

}
