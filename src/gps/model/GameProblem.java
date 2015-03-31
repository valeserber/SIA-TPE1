package gps.model;

import gps.Heuristic;
import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameProblem implements GPSProblem {
	
	List<GPSRule> ruleList;
	
	public GameProblem() {
		
		ruleList = new ArrayList<GPSRule>();
		for (int c = 1; c < 3; c++) {
			for (int i = 0; i < GameState.SIZE; i++) {
				for (int j = 0; j < GameState.SIZE; j++) {
					GameRule rule = new GameRule(c, i, j);
					ruleList.add(rule);
				}
			}
		}
		Collections.shuffle(ruleList);
		
	}

	@Override
	public GPSState getInitState() {
		 int[][] board = { { 1, 0, 0, 0, 0, 0, 0, 0 },
		 { 0, 0, 2, 2, 0, 2, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1 },
		 { 0, 1, 2, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 1, 0, 0, 2 },
		 { 1, 1, 0, 0, 1, 0, 2, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
		 { 0, 0, 0, 0, 0, 2, 2, 0 } };

//		 int[][] board = { { 1, 2, 1, 2, 1, 1, 2, 2 },
//		 { 2, 0, 0, 1, 0, 2, 1, 1 }, { 0, 0, 0, 1, 2, 0, 2, 2 },
//		 { 2, 0, 2, 2, 1, 0, 0, 1 }, { 1, 2, 0, 2, 0, 2, 1, 2 },
//		 { 1, 2, 0, 1, 2, 0, 0, 2 }, { 2, 1, 2, 1, 2, 1, 2, 1 },
//		 { 2, 1, 2, 2, 1, 2, 1, 1 } };

//		int[][] board = { { 0, 1, 2, 2, 1, 1, 2, 0 },
//				{ 2, 1, 2, 1, 2, 1, 1, 2 }, 
//				{ 0, 2, 1, 1, 2, 2, 1, 0 },
//				{ 2, 1, 1, 2, 1, 2, 2, 1 }, 
//				{ 1, 2, 2, 1, 2, 1, 1, 2 },
//				{ 2, 1, 2, 1, 2, 1, 2, 1 }, 
//				{ 0, 0, 1, 2, 1, 2, 1, 2 },
//				{ 1, 2, 1, 2, 1, 2, 2, 1 } };

		GPSState initialState = new GameState(board);
		return initialState;
	}

	@Override
	public List<GPSRule> getRules() {
		return ruleList;
	}

	// private boolean hasNearColoredTiles(int i, int j) {
	// int[][] board = ((GameState) getInitState()).getBoard();
	// if (i - 1 >= 0 && board[i - 1][j] != 0) {
	// return true;
	// } else if (i + 1 < GameState.SIZE && board[i + 1][j] != 0) {
	// return true;
	// } else if (j - 1 > 0 && board[i][j - 1] != 0) {
	// return true;
	// } else if (j + 1 < GameState.SIZE && board[i][j + 1] != 0) {
	// return true;
	// }
	// return false;
	// }

	@Override
	public Integer getHValue(GPSState state, Heuristic heuristic) {
		if (!(state instanceof GameState)) {
			throw new IllegalArgumentException();
		}
		GameState gameState = (GameState) state;
		if (gameState.getHValue() != -1) {
			return gameState.getHValue();
		}
		int hValue = 0;
		switch (heuristic) {
		case ROWS:
			hValue = getHValueRows(gameState);
			break;
		case POSSIBILITIES:
			hValue = getHValuePossibilities(gameState);
			break;
		case MINCOLOR:
			hValue = getHValueMinColor(gameState);
			break;
		case USEFULSTATE:
			hValue = getHValueUsefulState(gameState);
			break;
		case SURFACE:
			hValue = getHValueSurface(gameState);
			break;
		default:
			throw new IllegalArgumentException();
		}
		gameState.setHValue(hValue);
		return hValue;

	}
	
	private Integer getHValueSurface(GameState gameState) {
		
		//check if the state is impossible to use
		if (!stateOk(gameState)) {
			return 1000;
		}
		
		return 0;
		
	}
	
	private boolean stateOk(GameState gameState) {
		//check if the state is impossible to use
		GameState tmpState = new GameState(gameState);
		GameRule nextMove;
		nextMove = retDoubleFree(tmpState);
		while (nextMove != null) {
			try {
				tmpState = (GameState) nextMove.evalRule(tmpState);
			} catch (NotAppliableException e) {
				return false;
			}
			nextMove = retDoubleFree(tmpState);
		}
		return true;
	}

	private Integer getHValueUsefulState(GameState gameState) {
		int blue = 0;
		int red = 0;
		int colorFullRow = 0;
		int colorFullCol = 0;
		int doubleCount = 0;
		int doubleCountDone = 0;
		int fullRow = 0;
		int fullCol = 0;
//		int sameRow = 0;
		int[][] board = gameState.getBoard();
		int maxTiles = (GameState.SIZE * GameState.SIZE);
		
		if (!stateOk(gameState)) {
			return 100000;
		}
		
		for (int r = 0; r < GameState.SIZE; r++) {
			int freeTiles = 0;
			red = 0;
			blue = 0;
			for (int c = 0; c < GameState.SIZE; c++) {
				int actualTile = board[r][c];
				// Count number of colored tiles in row
				if (actualTile != GameState.EMPTY) {
					// Count red and blue tiles in row
					if (actualTile == GameState.RED) {
						red++;
					} else if (actualTile == GameState.BLUE) {
						blue++;
					}
					doubleCount = checkDoubleColor(gameState, r, c, doubleCount);
					doubleCountDone = checkDoubleColorDone(gameState, r, c, doubleCountDone);
				} else {
					// Count empty spaces that should be colored because of the
					// "NoThreeAdjacent" rule
					freeTiles++;
				}
			}
			if (freeTiles == 2) {
//				sameRow = checkNoTwoSameColumns(gameState, r);
			}
			if (freeTiles == 0) {
				fullRow += 5;
			}
			if (blue == (GameState.SIZE / 2) || red == (GameState.SIZE / 2)
					&& freeTiles != 0) {
				colorFullRow += 5 - freeTiles;
			}
		}
		
		for (int c = 0; c < GameState.SIZE; c++) {
			int freeTiles = 0;
			red = 0;
			blue = 0;
			for (int r = 0; r < GameState.SIZE; r++) {
				int actualTile = board[r][c];
				// Count number of colored tiles in row
				if (actualTile != GameState.EMPTY) {

					// Count red and blue tiles in row
					if (actualTile == GameState.RED) {
						red++;
					} else if (actualTile == GameState.BLUE) {
						blue++;
					}

				} else {
					freeTiles++;
				}
			}
			if (freeTiles == 2) {
//				sameRow = checkNoTwoSameColumns(gameState, r);
			}
			if (freeTiles == 0) {
				fullCol++;
			}
			if (blue == (GameState.SIZE / 2) || red == (GameState.SIZE / 2)
					&& freeTiles != 0) {
				colorFullCol += 5 - freeTiles;
			}
		}

		int ret = 10*maxTiles
				+ 5*doubleCount - 1*doubleCountDone 
				- 15*fullRow - 15*fullCol;
//		System.out.println("---- " + ret);
//		System.out.println(100*maxTiles);
//		System.out.println(10*gameState.getColoredCount());
//		System.out.println(-doubleCountDone);
//		System.out.println(-fullRow);
//		System.out.println(-fullCol);
//		System.out.println(-colorFullCol);
//		System.out.println(-colorFullRow);
//		System.out.println(gameState);
		if (ret < 1 || ret > 100000)
			System.out.println(ret);
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

	public int checkDoubleColor(GameState gameState, int row, int col,
			int doubleCount) {
		int[][] board = gameState.getBoard();
		int actualTile = board[row][col];
		if (row + 1 < GameState.SIZE && actualTile == board[row + 1][col]) {
			if ((row + 2 < GameState.SIZE
					&& board[row + 2][col] == GameState.EMPTY) || (row - 1 >= 0
					&& board[row - 1][col] == GameState.EMPTY)) {
				doubleCount++;
			}
		}
		if (col + 1 < GameState.SIZE && actualTile == board[row][col + 1]) {
			if ((col + 2 < GameState.SIZE
					&& board[row][col + 2] == GameState.EMPTY) || (col - 1 >= 0
					&& board[row][col - 1] == GameState.EMPTY)) {
				doubleCount++;
			}
		}
		if (row + 2 < GameState.SIZE && actualTile == board[row + 2][col] && 
				GameState.EMPTY == board[row + 1][col]) {
			doubleCount++;
		}
		if (col + 2 < GameState.SIZE && actualTile == board[row][col + 2] && 
				GameState.EMPTY == board[row][col + 1]) {
			doubleCount++;
		}

		return doubleCount;

	}
	
	private GameRule retDoubleFree(GameState gameState) {
		GameRule ret = null;
		
		for (int r = 0; r < gameState.getSize(); r++) {
			for (int c = 0; c < gameState.getSize(); c++) {
				if (gameState.getBoard()[r][c] != gameState.EMPTY) {
					ret = doubleFree(gameState, r, c);
				}
				if (ret != null) {
					return ret;
				}
			}
		}
		return ret;
	}
	
	private GameRule doubleFree(GameState gameState, int row, int col) {
		int[][] board = gameState.getBoard();
		int actualTile = board[row][col];
		GameRule rule = null;
		if (row + 1 < GameState.SIZE && actualTile == board[row + 1][col]) {
			if (row + 2 < GameState.SIZE && board[row + 2][col] == GameState.EMPTY) {
				rule = new GameRule((actualTile == 1 ? 2 : 1), row + 2, col);
			} else if ((row - 1 >= 0 && board[row - 1][col] == GameState.EMPTY)) {
				rule = new GameRule((actualTile == 1 ? 2 : 1), row - 1, col);
			}
		} else if (col + 1 < GameState.SIZE && actualTile == board[row][col + 1]) {
			if ((col + 2 < GameState.SIZE && board[row][col + 2] == GameState.EMPTY)) {
				rule = new GameRule((actualTile == 1 ? 2 : 1), row, col + 2);
			} else if ((col - 1 >= 0 && board[row][col - 1] == GameState.EMPTY)) {
				rule = new GameRule((actualTile == 1 ? 2 : 1), row, col - 1);
			}
		} else if(row + 2 < GameState.SIZE && actualTile == board[row + 2][col] && 
				GameState.EMPTY == board[row + 1][col]) {
			rule = new GameRule((actualTile == 1 ? 2 : 1), row + 1, col);
		} else if (col + 2 < GameState.SIZE && actualTile == board[row][col + 2] && 
				GameState.EMPTY == board[row][col + 1]) {
			rule = new GameRule((actualTile == 1 ? 2 : 1), row, col + 1);
		}
		return rule;
	}
	
	public int checkDoubleColorDone(GameState gameState, int row, int col,
			int doubleCount) {
		int[][] board = gameState.getBoard();
		int actualTile = board[row][col];
		if (row + 1 < GameState.SIZE && actualTile == board[row + 1][col]) {
			if ((row + 2 < GameState.SIZE
					&& board[row + 2][col] != GameState.EMPTY) || (row - 1 >= 0
					&& board[row - 1][col] != GameState.EMPTY)) {
				doubleCount++;
			}
		}
		if (col + 1 < GameState.SIZE && actualTile == board[row][col + 1]) {
			if ((col + 2 < GameState.SIZE
					&& board[row][col + 2] != GameState.EMPTY) || (col - 1 >= 0
					&& board[row][col - 1] != GameState.EMPTY)) {
				doubleCount++;
			}
		}
		if (row + 2 < GameState.SIZE && actualTile == board[row + 2][col] && 
				GameState.EMPTY != board[row + 1][col]) {
			doubleCount++;
		}
		if (col + 2 < GameState.SIZE && actualTile == board[row][col + 2] && 
				GameState.EMPTY != board[row][col + 1]) {
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

	private Integer getHValueRows(GameState gameState) {
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
