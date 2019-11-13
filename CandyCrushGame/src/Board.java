import java.util.ArrayList;
import java.util.Random;

public class Board {
	
	private final int width, height; //number of blocks in each dimension
	private Random rand;
	ArrayList<Integer> tiles; //IDs of block on board, empty space designated by Integer.minValue()
	
	/**
	 * default constructor
	 */
	public Board() {
		
		this(8, 8);
	}
	
	/**
	 * fill board
	 * @param width
	 * @param height
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new ArrayList<>();
		rand = new Random();
	}
	
	/**
	 * refills total board with random tiles
	 */
	public void refreshBoard() {
		for(int h =0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				int c = rand.nextInt(6); //6 different color block
				tiles.add(c);
			}
			//new row
		}
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
						int switchValue = tiles.get(i- j*width);
						tiles.set(i-j*width, Integer.MIN_VALUE);
						tiles.set(i, switchValue);
						emptyC = false;
						break;
					}
				}
				if(emptyC) {
					tiles.set(i, rand.nextInt(6)); // when column empty block replace by a new
				}
			}
		} 
			
	}
	
	/**
	 * Check whether or not any valid moves exist
	 * @return
	 */
	public boolean movesExist() {
		return false;
	}
	
	/**
	 * move blocks down and fill new spaces
	 */
	public void dropBlocks() {
		
	}
	
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
		
	}
	
	/**
	 * Removes block from board by setting ArrayList index to Integer.minValue()
	 * @param block
	 */
	public void removeBlock(Integer blockID) {
		
	}
	
	/**
	 * Remove all blocks in list from board
	 * @param blocks
	 */
	public void removeBlocks(ArrayList<Integer> blockIDs) {
		
	}
	
	public Integer getBlock(int x, int y) {
		return tiles.get(y*width + x);
	}
	
	public void putBlock(Integer blockID, int x, int y) {
		tiles.set(y*width + x, blockID);
	}

}
