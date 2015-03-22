package gps.model;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameProblem implements GPSProblem {

	@Override
	public GPSState getInitState() {
//		int[][] board = { { 1, 0, 0, 0, 0, 0, 0, 0 },
//				{ 0, 0, 2, 2, 0, 2, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 0, 1, 2, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 1, 0, 0, 2 },
//				{ 1, 1, 0, 0, 1, 0, 2, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
//				{ 0, 0, 0, 0, 0, 2, 2, 0 } };
	
		// int[][] board = {{1,0,2,1,1,2,1,0},
		// {2,1,0,1,2,1,0,1},
		// {1,0,2,2,1,2,1,2},
		// {2,1,2,1,1,0,2,2},
		// {0,2,1,2,2,1,2,1},
		// {1,2,1,2,0,2,1,1},
		// {1,0,2,1,2,1,2,2},
		// {2,1,1,2,1,1,2,2}};
		
		int[][] board = 
		{{1,2,1,2,1,1,2,2},
		{2,0,1,1,2,2,1,1},
		{1,1,2,1,2,1,2,2},
		{2,0,2,2,1,0,0,1},
		{1,2,0,2,1,2,1,2},
		{1,2,0,1,2,0,0,2},
		{2,1,2,1,2,1,2,1},
		{2,1,2,2,1,2,1,1}};

		GPSState initialState = new GameState(board);
		// ((GameState)initialState).printBoard();
		return initialState;
	}

	@Override
	public List<GPSRule> getRules() {
		LinkedList<GPSRule> rules = new LinkedList<GPSRule>();
		int[][] initialBoard = ((GameState) getInitState()).getBoard();
		for (int c = 1; c < 3; c++) {
			for (int i = 0; i < GameState.SIZE; i++) {
				for (int j = 0; j < GameState.SIZE; j++) {
					if(initialBoard[i][j]==0){
						GameRule rule = new GameRule(c, i, j);
						rules.addFirst(rule);
					}
				}
			}
		}
		Collections.shuffle(rules);
		return rules;
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
	public Integer getHValue(GPSState state) {
		// Cantidad que falta en la fila con más faltantes
		if (!(state instanceof GameState)) {
			throw new IllegalArgumentException();
		}
		GameState gameState = (GameState) state;
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

	@Override
	public boolean isGoalState(GPSState state) {
		GameState gameState = (GameState) state;
		return (gameState.getBlueCount() + gameState.getRedCount()) == (gameState
				.getSize() * gameState.getSize());
	}
}
