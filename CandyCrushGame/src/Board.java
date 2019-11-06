import java.util.ArrayList;

public class Board {
	
	private final int width, height; //number of blocks in each dimension
	
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
		
	}
	
	/**
	 * refills total board with random tiles
	 */
	public void refreshBoard() {
		
	}
	
	/**
	 * fill all empty spaces after a move
	 */
	public void fillSpaces() {
		
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
