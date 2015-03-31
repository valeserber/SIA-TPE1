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
		case MINCOLOR:
			return getHValueMinColor(gameState);
		case FULLCOLOR:
			return getHValueFullColor(gameState);
		default:
			throw new IllegalArgumentException();
		}

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
	
	private Integer getHValueMinColor(GameState gameState) {
		int blue = gameState.getBlueCount();
		int red = gameState.getRedCount();
		int maxTilesColor = (GameState.SIZE * GameState.SIZE) / 2;
		return (blue < red ? (maxTilesColor - blue) : (maxTilesColor - red));
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

	@Override
	public boolean isGoalState(GPSState state) {
		GameState gameState = (GameState) state;
		return (gameState.getBlueCount() + gameState.getRedCount()) == (gameState
				.getSize() * gameState.getSize());
	}
}
