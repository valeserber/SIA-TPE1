package gps.model;

import gps.Heuristic;
import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;
import gps.utils.Board;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameProblem implements GPSProblem {
	
	private int[][] initialBoard;
	private LinkedList<GPSRule> rules;
	private Integer currentLevel;
	
	public GameProblem(Integer boardLevel) {
		currentLevel = boardLevel;
		initialBoard = Board.getInitialBard(boardLevel);
		rules = new LinkedList<GPSRule>();
		int[][] initialBoard = ((GameState) getInitState()).getBoard();
		for (int c = 1; c < 3; c++) {
			for (int i = 0; i < GameState.SIZE; i++) {
				for (int j = 0; j < GameState.SIZE; j++) {
					if (initialBoard[i][j] == 0) {
						GameRule rule = new GameRule(c, i, j);
						rules.addFirst(rule);
					}
				}
			}
		}
		Collections.shuffle(rules);
	}
	
	@Override
	public Integer getCurrentLevel() {
		return currentLevel;
	}
	
	@Override
	public GPSState getInitState() {
		GPSState initialState = new GameState(initialBoard);
		countSpacesLeft();
		return initialState;
	}

	@Override
	public List<GPSRule> getRules() {
		Collections.shuffle(rules);
		return rules;
	}
	
	private void countSpacesLeft() {
		int count=0;
		for (int i=0; i< initialBoard.length; i++) {
			for (int j=0; j< initialBoard.length; j++) {
				if (initialBoard[i][j] == 0){
					count++;
				}
			}
		}
//		System.out.println("Lugares vacios: "+count);
	}

	@Override
	public Integer getHValue(GPSState state, Heuristic heuristic) {
		if (!(state instanceof GameState)) {
			throw new IllegalArgumentException();
		}
		GameState gameState = (GameState) state;
		switch (heuristic) {
		case COLUMNS:
			return getHValueCols(gameState);
		case POSSIBILITIES:
			return getHValuePossibilities(gameState);
		case MINCOLOR:
			return getHValueMinColor(gameState);
		case USEFULSTATE:
			return getHValueUsefulState(gameState);
		case FULLCOLOR:
			return getHValueFullColor(gameState);
		case ADJACENTS:
			return getHValueAdjacents(gameState);
		default:
			throw new IllegalArgumentException();
		}

	}
	
	private Integer getHValueAdjacents(GameState gameState) {
		int empty = gameState.getEmptyCount();
		int[][] board = gameState.getBoard();
		int adjacents = 0;
		for (int i=0; i< gameState.SIZE; i++) {
			for (int j=0; j<gameState.SIZE; j++) {
				if ((i > 0) && sameColor(board, i-1, j ,i, j)) { //top
					adjacents++;
				}
				if ((i < gameState.SIZE - 1) && sameColor(board, i+1, j, i, j)) { //bottom
					adjacents++;
				}
				if ((j > 0) && sameColor(board, i, j-1, i, j)) { //left
					adjacents++;
				}
				if ((j < gameState.SIZE - 1) && sameColor(board, i, j+1, i, j)) { //right
					adjacents++;
				}
			}
		}
		int ans = empty - (adjacents/2);
		return (ans < 0)? 0 : ans;
	}
	
	private boolean sameColor(int[][]board, int row1, int col1, int row2, int col2) {
		return board[row1][col1] == board[row2][col2];
	}

	private Integer getHValueFullColor(GameState gameState) {
		int rows = 0;
		int blue = 0;
		int red = 0;
		int cant = GameState.SIZE /2 ;
		for (int i=0; i< gameState.getSize(); i++) {
			blue = 0;
			red = 0;
			for (int j=0; j<gameState.getSize(); j++) {
				int color = gameState.getBoard()[i][j];
				if (color == GameState.BLUE) {
					blue++;
				} else if (color == GameState.RED) {
					red++;
				}
			}
			if (blue == cant || red == cant) {
				rows++;
			}
		}
		return (GameState.SIZE - rows);
	}
	
	private Integer getHValueUsefulState(GameState gameState) {
		int blue = 0;
		int red = 0;
		int colorFull = 0;
		int doubleCount = 0;
		int semiFullRow = 0;
		int sameRow = 0;
		int[][] board = gameState.getBoard();
		int maxTiles = (GameState.SIZE * GameState.SIZE);
		for (int r = 0; r < GameState.SIZE - 1; r++) {
			int cantRow = 0;
			for (int c = 0; c < GameState.SIZE - 1; c++) {
				int actualTile = board[r][c];
				// Count number of colored tiles in row
				if (actualTile != GameState.EMPTY) {
					cantRow++;

					// Count red and blue tiles in row
					if (actualTile == GameState.RED) {
						red++;
					} else if (actualTile == GameState.BLUE) {
						blue++;
					}

					// Count empty spaces that should be colored because of the
					// "NoThreeAdjacent" rule
					doubleCount = checkDoubleColor(gameState, r, c, doubleCount);
				}
			}
			if (cantRow == GameState.SIZE - 2) {
				// sameRow = checkNoTwoSameColumns(gameState, r);
			}
			if (cantRow == GameState.SIZE - 1) {
				semiFullRow++;
			}
			if (blue == (GameState.SIZE / 2) || red == (GameState.SIZE / 2)
					&& cantRow != GameState.SIZE) {
				colorFull++;
			}
		}

		int ret = maxTiles
				- (gameState.getColoredCount() - doubleCount - semiFullRow
						- colorFull - sameRow);
		return ret >= 0 ? ret : 0;
	}

	private int checkNoTwoSameColumns(GameState gameState, int row) {
		int[][] board = gameState.getBoard();
		boolean flag = true;
		int coincidence = 0;
		for (int r = 0; r < GameState.SIZE; r++) {
			for (int c = 0; c < GameState.SIZE && flag; c++) {
				if (r != row && board[r][c] != GameState.EMPTY
						&& board[r][c] != board[row][c]) {
					flag = false;
				}
				coincidence++;
			}
			if (coincidence == GameState.SIZE - 2) {
				return 1;
			}
		}
		return 0;
	}

	private int checkDoubleColor(GameState gameState, int row, int col,
			int doubleCount) {
		int[][] board = gameState.getBoard();
		int actualTile = board[row][col];
		if (actualTile == board[row + 1][col]) {
			if (row + 2 < GameState.SIZE
					&& board[row + 2][col] == GameState.EMPTY || row - 1 >= 0
					&& board[row - 1][col] == GameState.EMPTY) {
				doubleCount++;
			}
		}
		if (actualTile == board[row][col + 1]) {
			if (col + 2 < GameState.SIZE
					&& board[row][col + 2] == GameState.EMPTY || col - 1 >= 0
					&& board[row][col - 1] == GameState.EMPTY) {
				doubleCount++;
			}
		}
		if (row + 2 < GameState.SIZE && actualTile == board[row + 2][col]) {
			doubleCount++;
		}
		if (col + 2 < GameState.SIZE && actualTile == board[row][col + 2]) {
			doubleCount++;
		}

		return doubleCount;

	}

	private Integer getHValueMinColor(GameState gameState) {
		int blue = gameState.getBlueCount();
		int red = gameState.getRedCount();
		int maxTilesColor = (GameState.SIZE * GameState.SIZE) / 2;
		return (blue < red ? (maxTilesColor - blue) : (maxTilesColor - red));
	}

	private Integer getHValuePossibilities(GameState gameState) {
		int possibilities = 0;
		for (int r = 0; r < GameState.SIZE; r++) {
			for (int c = 0; c < GameState.SIZE; c++) {
				List<GameRule> rules = getRulesForTile(r, c);
				for (GameRule rule : rules) {
					try {
						rule.evalRule(gameState);
						possibilities++;
					} catch (NotAppliableException e) {
						// noSumo
					}
				}
			}
		}
		return possibilities;
	}

	private Integer getHValueCols(GameState gameState) {
		int[][] board = gameState.getBoard();
		int maxCant = 0;
		for (int col = 0; col < GameState.SIZE; col++) {
			int cant = 0;
			for (int row = 0; row < GameState.SIZE; row++) {
				if (board[row][col] == GameState.EMPTY) {
					cant++;
				}
			}
			if (cant > maxCant) {
				maxCant = cant;
			}
		}
		return maxCant;
	}

	private List<GameRule> getRulesForTile(int r, int col) {
		List<GameRule> rules = new LinkedList<GameRule>();
		rules.add(new GameRule(GameState.RED, r, col));
		rules.add(new GameRule(GameState.BLUE, r, col));
		return rules;
	}

	@Override
	public boolean isGoalState(GPSState state) {
		GameState gameState = (GameState) state;
		return (gameState.getBlueCount() + gameState.getRedCount()) == (gameState
				.getSize() * gameState.getSize());
	}
}
