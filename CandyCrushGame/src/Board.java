import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Board {
	
	private final int width, height; //number of blocks in each dimension
	public static final int EMPTY = Integer.MIN_VALUE;
	private Random rand;
	private ArrayList<Integer> tiles; //IDs of block on board, empty space designated by Integer.minValue()
	private Game game;
	private Point boardOrigin;
	
	private int score;
	
	/**
	 * default constructor
	 */
	public Board(Game game) {
		
		this(4, 4, game);
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
		//this.EMPTY = Integer.MIN_VALUE;
		int originX = (Main.getWindow().getFrame().getWidth() - (width * game.getBlockSize())) / 2;
		int originY = (Main.getWindow().getFrame().getHeight() - (height * game.getBlockSize())) / 2;
		this.boardOrigin = new Point((int) originX, (int) originY);
		this.score = 0;
		
		tiles = new ArrayList<>();
		rand = new Random();
		
		//System.out.println(getIndex(1, 1));
		createEmptySpaces();
		fillSpaces();
	}
	
	/**
	 * refills total board with random tiles
	 */
	public void refreshBoard() {
		/*tiles.clear();
		for(int h =0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				int c = rand.nextInt(6); //6 different color block
				tiles.add(c);
			}
			//new row
		}*/
		createEmptySpaces();
		//fillSpaces();
		tidyBoard(); //Fills all spaces, checks if any blocks can be scored, then refills spaces again in a loop
	}
	
	/**
	 * returns canvas coordinates of grid index location
	 * @param index
	 * @return
	 */
	public Point getPositionCoords(int index) {
		
		int x = (int) (boardOrigin.getX() + (index % this.width) * game.getBlockSize());
		int y = (int) (boardOrigin.getY() + ((int) index / this.height) * game.getBlockSize());
		return new Point(x, y);
	}
	
	/**
	 * returns grid location of grid index location
	 * @param index
	 * @return
	 */
	public Point getPosition(int index) {
		
		int x = index % this.width;
		int y = index / this.height;
		return new Point(x, y);
	}
	
	/**
	 * This method generates an empty board array
	 * This will override current board!
	 */
	public void createEmptySpaces() {
		tiles.clear();
		for (int i = 0; i < width*height; i++) {
			tiles.add(EMPTY);
		}
	}
	
	public void tidyBoard() {
		do {
			fillSpaces();
		} while (scoreMatches() > 0);
	}
	
	/**
	 * fill all empty spaces after a move
	 */
	public void fillSpaces() {
		boolean flag = true;
		while (flag) {
			flag = false;
			dropBlocks();
			for (int i = 0; i < width; i++) {
				if (getBlock(i, 0) == EMPTY) {
					putBlock(generateBlock(i, 0), i, 0);
					flag = true;
				}
			}
		}
		
		/*for(int i = tiles.size(); i > 0; i--) {// down to up
			
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
					//System.out.println("Adding block");
					Point newCoords = getPositionCoords(i);
					tiles.set(i, game.addBlock(newCoords.x, newCoords.y));
				}
			}
		} */
			
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
	
	/**
	 * move blocks down and fill new spaces
	 */
	public void dropBlocks() {
		
		for (int i = 0; i < width; i++) { //scan across board (left to right)
			for (int j = height - 1; j >= 0; j--) { //scan each column (bottom to top)
				if (getBlock(i, j) == EMPTY) { //if an empty space is found:
					for (int k = j; k >= 0; k--) { //scan the part of column above space
						if (getBlock(i, k) != EMPTY) { //if block is found:
							putBlock(getBlock(i, k), i, j); //move block to previously found empty space
							putBlock(EMPTY, i, k); //set found block to empty since it has been moved
						}
					}
				}
			}
		}
	}
	
	/**
	 * return all neighboring matching blocks
	 * @param block
	 * @return
	 */
	public ArrayList<Integer> getNeighbors(Integer blockID) {
		
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		Point loc = getPosition(findBlock(blockID));
		
		if (loc.x - 1 >= 0) {
			int neighbor = getBlock(loc.x - 1, loc.y);
			//neighbors.add(getBlock(loc.x - 1, loc.y));
			if (neighbor != EMPTY) {
				neighbors.add(neighbor);
			}
		}
		if (loc.y - 1 >= 0) {
			int neighbor = getBlock(loc.x, loc.y - 1);
			if (neighbor != EMPTY) {
				neighbors.add(neighbor);
			}
		}
		if (loc.x + 1 < width) {
			int neighbor = getBlock(loc.x + 1, loc.y);
			if (neighbor != EMPTY) {
				neighbors.add(neighbor);
			}
		}
		if (loc.y + 1 < height) {
			int neighbor = getBlock(loc.x, loc.y + 1);
			if (neighbor != EMPTY) {
				neighbors.add(neighbor);
			}
		}
		
		return neighbors;
	}
	
	/**
	 * Swaps position of two blocks
	 * @param block1 (board index)
	 * @param block2 (board index)
	 */
	public void swapBlocks(Integer block1, Integer block2) {
		
		int swap = getBlock(block1);
		putBlock(getBlock(block2), block1);
		putBlock(swap, block2);
		/*int swap = tiles.get(blockID1);
		tiles.set(blockID1, tiles.get(blockID2));
		tiles.set(blockID2, swap);*/
		
	}
	
	/**
	 * Attempt to swap given blocks.
	 * If not match is made, swap blocks back.
	 * @param block1 (block ID)
	 * @param block2 (block ID)
	 */
	public void attemptSwap(Integer block1, Integer block2) {
		
		swapBlocks(findBlock(block1), findBlock(block2));
		if (scoreMatches() <= 0) {
			swapBlocks(findBlock(block1), findBlock(block2));
		}
	}
	
	/**
	 * Determine if three or more neighboring blocks match.
	 * Destroy all sets of matching blocks and track score of destroyed blocks.
	 * Return score of destroyed blocks (should return 0 if no blocks were destroyed)
	 * @return
	 */
	public int scoreMatches() {
		int numScores = 0;
		for(int i = 0; i < tiles.size(); i++) {	//check all the block
			if (getBlock(i) == EMPTY) {
				continue;
			}
			
			Set<Integer> matchingBlocks = new HashSet<Integer>();
			matchingBlocks.add(getBlock(i));
			
			Set<Integer> blocksToCheck = new HashSet<Integer>();
			Set<Integer> removeFromBlocksToCheck = new HashSet<Integer>();
			Set<Integer> addToBlocksToCheck = new HashSet<Integer>();
			blocksToCheck.addAll(getNeighbors(tiles.get(i)));
			
			while (blocksToCheck.size() > 0) {
				//Iterator<Integer> toCheck = blocksToCheck.iterator();
				for (int check : blocksToCheck) {
					System.out.println(game.getBlocks().containsKey(check) + " : " + check);
					System.out.println(game.getBlocks().containsKey(getBlock(i)) + " :: " + getBlock(i));
					if (game.getBlock(check).getColor() == game.getBlock(getBlock(i)).getColor()) {
						matchingBlocks.add(check);
						for (int neighbor : getNeighbors(check)) {
							if (!matchingBlocks.contains(neighbor)) {
								//blocksToCheck.add(neighbor);
								addToBlocksToCheck.add(neighbor);
							}
						}
					}
					//blocksToCheck.remove(check);
					removeFromBlocksToCheck.add(check);
				}
				for (int block : addToBlocksToCheck) {
					blocksToCheck.add(block);
				}
				for (int block : removeFromBlocksToCheck) {
					blocksToCheck.remove(block);
				}
				addToBlocksToCheck.clear();
				removeFromBlocksToCheck.clear();
			}
			
			if (matchingBlocks.size() >= 3) {
				numScores += matchingBlocks.size();
				for (int blockID : matchingBlocks) {
					tiles.set(findBlock(blockID), EMPTY);
				}
			}
			//ArrayList<Integer> neighbors; // neighbors for each block
			//neighbors = getNeighbors(tiles.get(i));
			
			
			
			/*if(neighbors.size() > 3 || neighbors.size() == 3) {//three or more neighbors
				
				for(int j = 0; j < neighbors.size(); j++) {//remove each one of them
					
					if(neighbors.get(j) != Integer.MIN_VALUE) {//only count new neighbors
						removeBlock(neighbors.get(j));
						numScores++; //update scores for new empty block
					}
				}
			}*/
			
		}
		
		score += numScores;
		
		return numScores;
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
	
	/**
	 * returns index of given location in board grid
	 * @param x
	 * @param y
	 * @return
	 */
	public Integer getIndex(int x, int y) {
		return y*width + x;
	}
	
	/**
	 * returns ID of block at given location in board grid.
	 * @param x
	 * @param y
	 * @return
	 */
	public Integer getBlock(int x, int y) {
		/*try {
			return tiles.get(getIndex(x, y));
		} catch (IndexOutOfBoundsException e) {
			return EMPTY;
		}*/
		return getBlock(getIndex(x, y));
	}
	
	/**
	 * Returns ID of block at given index in the board grid.
	 * @param index
	 * @return
	 */
	public Integer getBlock(int index) {
		try {
			return tiles.get(index);
		} catch (IndexOutOfBoundsException e) {
			return EMPTY;
		}
	}
	
	/**
	 * Creates new block at specified grid location.
	 * Returns ID of created block.
	 * @param x
	 * @param y
	 * @return
	 */
	public Integer generateBlock(int x, int y) {
		Point newCoords = getPositionCoords(getIndex(x, y));
		return game.addBlock(newCoords.x, newCoords.y);
	}
	
	/**
	 * adds a block to the tiles array at the given grid location
	 * @param blockID
	 * @param x
	 * @param y
	 */
	public void putBlock(Integer blockID, int x, int y) {
		
		tiles.set(getIndex(x, y), blockID);
		if (blockID != EMPTY) {
			game.getBlock(blockID).move(getPositionCoords(getIndex(x, y)));
		}
	}
	
	/**
	 * adds a block to the tiles array at the given board index
	 * @param blockID
	 * @param index
	 */
	public void putBlock(Integer blockID, int index) {
		
		tiles.set(index, blockID);
		if (blockID != EMPTY) {
			game.getBlock(blockID).move(getPositionCoords(index));
		}
	}
	
	/**
	 * Returns board index of position containing block with blockID.
	 * Returns Block.EMPTY if board does not contain specified block.
	 * @param blockID
	 * @return
	 */
	public Integer findBlock(Integer blockID) {
		if (!tiles.contains(blockID)) {
			return EMPTY;
		}
		for (int i = 0; i < tiles.size(); i++) {
			if (getBlock(i) == blockID) {
				return i;
			}
		}
		return EMPTY;
	}
	
	/**
	 * Returns list of block IDs currently on the board.
	 * @return
	 */
	public ArrayList<Integer> getBlocks() {
		return tiles;
	}
	
	/**
	 * returns game instance associated with this board
	 * @return
	 */
	public Game getGame() {
		return game;
	}

}
