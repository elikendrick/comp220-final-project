import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Board {
	
	private final int width, height; //number of blocks in each dimension
	private Random rand;
	ArrayList<Integer> tiles; //IDs of block on board, empty space designated by Integer.minValue()
	private Game game;
	private Point boardOrigin;
	
	/**
	 * default constructor
	 */
	public Board(Game game) {
		
		this(8, 8, game);
	}
	
	/**
	 * fill board
	 * @param width
	 * @param height
	 */
	public Board(int width, int height, Game game) {
		this.game = game;
		this.width = width;
		this.height = height;
		tiles = new ArrayList<>();
		rand = new Random();
	}
	
	/**
	 * refills total board with random tiles
	 */
	public void refreshBoard() {
		tiles.clear();
		for(int h =0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				int c = rand.nextInt(6); //6 different color block
				tiles.add(c);
			}
			//new row
		}
	}
	
	public Point getPositionCoords(int index) {
		
		return new Point();
	}
	
	/**
	 * fill all empty spaces after a move
	 */
	public void fillSpaces() {
		for(int i = tiles.size(); i > 0; i--) {// down to up
			
			if(tiles.get(i) == Integer.MIN_VALUE) { //Check if empty
				boolean emptyC = false;	//empty column
				
				for(int j = 1; (i - j*width)> height; j++) { //(i-j*width) the # of block on top
					if(tiles.get(i - j*width) == Integer.MIN_VALUE) {
						emptyC = true; 
					}
					else {	//Switch top block 
						int switchValue = tiles.get(i- j*(width-1));
						tiles.set(i-j*width, Integer.MIN_VALUE);
						tiles.set(i, switchValue);
						emptyC = false;
						break;
					}
				}
				if(emptyC) {
					//tiles.set(i, rand.nextInt(6)); // when column empty block replace by a new
					tiles.set(i, game.addBlock(0, 0));
				}
			}
		} 
			
	}
	
	/**
	 * Check whether or not any valid moves exist
	 * @return
	 */
	public boolean movesExist() {
		boolean move = false;
		for(int i = 0; i < tiles.size(); i++) {
			if(i == 0) {//corner 1
				if (tiles.get(i) == tiles.get(i +1) || tiles.get(i)== tiles.get(i + width)) {
					move = true;
					break;
				}
			}
			else if(i == (width - 1)) {//corner 2
				if(tiles.get(i) == tiles.get(i-1) || tiles.get(i)== tiles.get(i + width))
				move = true;
				break;
			}
			else if(i == (width*(height - 1)) ) {//corner 3
				if(tiles.get(i) == tiles.get(i- width) || tiles.get(i)== tiles.get(i + 1))
				move = true;
				break;
			}
			else if(i == ((width*height) - 1) ) {//corner 4
				if(tiles.get(i) == tiles.get(i- width) || tiles.get(i)== tiles.get(i - 1))
				move = true;
				break;
			}
			else if(i < width) {//top row
				if(tiles.get(i) == tiles.get(i + width) || tiles.get(i) == tiles.get(i+1) || tiles.get(i) == tiles.get(i - 1)) {
					move = true;
					break;
				}
			}
			else if(i < width) {//bottom row
				if(tiles.get(i) == tiles.get(i - width) || tiles.get(i) == tiles.get(i+1) || tiles.get(i) == tiles.get(i - 1)) {
					move = true;
					break;
				}
			}
			else if((i%width) == 0 ) {//rigth col
				if(tiles.get(i) == tiles.get(i - 1) || tiles.get(i) == tiles.get(i+width) || tiles.get(i) == tiles.get(i - width)) {
					move = true;
					break;
				}
			}
			else if((i%width) == (width - 1) ) {//left col
				if(tiles.get(i) == tiles.get(i + 1) || tiles.get(i) == tiles.get(i+width) || tiles.get(i) == tiles.get(i - width)) {
					move = true;
					break;
				}
			}
			else {//middle
				if(tiles.get(i) == tiles.get(i+width) || tiles.get(i) == tiles.get(i - width)||
						tiles.get(i) == tiles.get(i + 1) || tiles.get(i) == tiles.get(i - 1)) {
					move = true;
					break;
				}
			}
		}
		
		return move;
	}
//	
//	/**
//	 * move blocks down and fill new spaces
//	 */
//	public void dropBlocks() {
//		
//	}
	
	/**
	 * return all neighboring matching blocks
	 * @param block
	 * @return
	 */
	public ArrayList<Integer> getNeighbors(Integer blockID) {
		
		return new ArrayList<Integer>();
	}
	
	/**
	 * Swaps position of two blocks
	 * @param block1
	 * @param block2
	 */
	public void swapBlocks(Integer blockID1, Integer blockID2) {
		
		int swap = tiles.get(blockID1);
		tiles.set(blockID1, tiles.get(blockID2));
		tiles.set(blockID2, swap);
		
	}
	
	/**
	 * Removes block from board by setting ArrayList index to Integer.minValue()
	 * @param block
	 */
	public void removeBlock(Integer blockID) {
		tiles.set(blockID, Integer.MIN_VALUE);
	}
	
	/**
	 * Remove all blocks in list from board
	 * @param blocks
	 */
	public void removeBlocks(ArrayList<Integer> blockIDs) {
		for(int i = 0; i < blockIDs.size(); i++) {
			tiles.set(blockIDs.get(i), Integer.MIN_VALUE);
		}
	}
	
	public Integer getBlock(int x, int y) {
		return tiles.get(y*width + x);
	}
	
	public void putBlock(Integer blockID, int x, int y) {
		tiles.set(y*width + x, blockID);
	}
	
	public Game getGame() {
		return game;
	}

}
