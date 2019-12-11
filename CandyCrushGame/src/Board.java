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
		
		this(8, 12, game);
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
		int originX = (Main.getWindow().getFrame().getWidth() - (width * game.getBlockSize())) / 2;
		int originY = (Main.getWindow().getFrame().getHeight() - (height * game.getBlockSize())) / 2;
		this.boardOrigin = new Point((int) originX, (int) originY);
		this.score = 0;
		
		tiles = new ArrayList<>();
		rand = new Random();
		
		refreshBoard();
	}
	
	/**
	 * refills total board with random tiles
	 */
	public void refreshBoard() {
		
		createEmptySpaces();
		
		for (int i=0; i<height/3; i++) {
			fillTopRow();
		}
	}
	
	public void printBoardState() {
		System.out.println("Board State:");
		for (int i = 0; i < tiles.size(); i++) {
			if (i > 0 && i % width == 0) {
				System.out.println("");
			}
			System.out.print(tiles.get(i) + " ");
		}
		System.out.println("");
	}
	
	/**
	 * returns canvas coordinates of grid index location
	 * @param index
	 * @return
	 */
	public Point getPositionCoords(int index) {
		
		int x = (int) (boardOrigin.getX() + (index % this.width) * game.getBlockSize());
		int y = (int) (boardOrigin.getY() + ((int) index / this.width) * game.getBlockSize());
		return new Point(x, y);
	}
	
	/**
	 * returns grid location of grid index location
	 * @param index
	 * @return
	 */
	public Point getPosition(int index) {
		
		int x = index % this.width;
		int y = index / this.width;
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
		
		fillSpaces();
	}
	
	public boolean containsEmpty() {
		for (int i=0; i<tiles.size(); i++) {
			if (tiles.get(i) == EMPTY) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * fill all empty spaces after a move
	 */
	public void fillSpaces() {
		
		while (containsEmpty()) {
			dropBlocks();
			for (int i=0; i<width; i++) {
				if (tiles.get(i) == EMPTY) {
					putBlock(generateBlock(i, 0), i, 0);
				}
			}
		}	
	}
	
	/**
	 * Returns false if top row already contains at least 1 block
	 * @return
	 */
	public boolean fillTopRow() {
		
		boolean flag = true;
		for (int i=0; i<width; i++) {
			if (tiles.get(i) == EMPTY) {
				putBlock(generateBlock(i, 0), i, 0);
			} else {
				flag = false;
			}
		}
		dropBlocks();
		return flag;
	}
	
	
	/**
	 * move blocks down and fill new spaces
	 */
	public void dropBlocks() {
		
		for (int i=0; i<width; i++) {
			for (int j=height-1; j>=0; j--) {
				if (getBlock(i, j) == EMPTY) {
					for (int k=j-1; k>=0; k--) {
						if (getBlock(i, k) != EMPTY) {
							swapBlocks(getIndex(i, j), getIndex(i, k));
							break;
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
	public Set<Integer> getNeighbors(Integer blockID) {
		
		Set<Integer> neighbors = new HashSet<Integer>();
		Point loc = getPosition(findBlock(blockID));
		
		if (loc.x - 1 >= 0) {
			int neighbor = getBlock(loc.x - 1, loc.y);
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
	}
	
	/**
	 * Attempt to swap given blocks.
	 * If not match is made, swap blocks back.
	 * @param block1 (block ID)
	 * @param block2 (block ID)
	 */
	public void attemptSwap(Integer block1, Integer block2) {
		
		Set<Integer> swappedBlocks = new HashSet<Integer>();
		swappedBlocks.add(block1);
		swappedBlocks.add(block2);
		swapBlocks(findBlock(block1), findBlock(block2));
		if (scoreMatches(swappedBlocks) <= 0) {
			swapBlocks(findBlock(block1), findBlock(block2));
		}
	}
	
	/**
	 * Determine if three or more neighboring blocks match.
	 * Destroy all sets of matching blocks and track score of destroyed blocks.
	 * Return score of destroyed blocks (should return 0 if no blocks were destroyed)
	 * @return
	 */
	public int scoreMatches(Set<Integer> blockIDs) {
		
		int numScores = 0;
		Set<Integer> blockIndices = new HashSet<Integer>();
		for (int ID : blockIDs) {
			blockIndices.add(findBlock(ID));
		}
		
		for (int i : blockIndices) {
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
				for (int check : blocksToCheck) {
					if (game.getBlock(check).getColor() == game.getBlock(getBlock(i)).getColor()) {
						matchingBlocks.add(check);
						for (int neighbor : getNeighbors(check)) {
							if (!matchingBlocks.contains(neighbor)) {
								addToBlocksToCheck.add(neighbor);
							}
						}
					}
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
					if (findBlock(blockID) != EMPTY) {
						tiles.set(findBlock(blockID), EMPTY);
						game.removeBlock(blockID);
					}
					
				}
			}
			matchingBlocks.clear();
		}
		
		if (numScores <= 3) {
			score += numScores;
		} else if (numScores >= 10) {
			score += numScores*2;
		} else {
			switch (numScores) {
				case 4:
					score += 5;
					break;
				case 5:
					score += 7;
					break;
				case 6:
					score += 9;
					break;
				case 7:
					score += 11;
					break;
				case 8:
					score += 13;
					break;
				case 9:
					score += 16;
					break;
				default:
					score += numScores;
					break;
			}
		}
		
		return numScores;
	}
	
	/**
	 * Removes block from board by setting ArrayList index to Integer.minValue()
	 * @param block
	 */
	public void removeBlock(Integer blockID) {
		tiles.set(blockID, Integer.MIN_VALUE);
		game.removeBlock(blockID);
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
	public int findBlock(int blockID) {
		
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
	
	public int getScore() {
		return score;
	}
	
	public void resetScore() {
		this.score = 0;
	}
	
	/**
	 * returns game instance associated with this board
	 * @return
	 */
	public Game getGame() {
		return game;
	}

}
