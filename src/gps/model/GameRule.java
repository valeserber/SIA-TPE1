package gps.model;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class GameRule implements GPSRule {

	private int color;
	private int row;
	private int col;

	public GameRule(int color, int row, int col) {
		super();
		// TODO: validar los parametros
		this.color = color;
		this.row = row;
		this.col = col;
	}

	@Override
	public Integer getCost() {
		return 1;
	}

	@Override
	public String getName() {
		return String.valueOf(color) + " (" + String.valueOf(row) + ", "
				+ String.valueOf(col) + ")";
	}

	@Override
	public GPSState evalRule(GPSState state) throws NotAppliableException {
		if (!(state instanceof GameState)) {
			throw new IllegalArgumentException();
		}
		GameState gameState = (GameState) state;
		if (isOcuppied(gameState) || isColorFull(gameState) ||
				hasThreeAdjacentTiles(gameState) || filledRowOrCol(gameState) ||
				containsSimilarities(gameState)){
			throw new NotAppliableException();
		}
		GameState updatedState = new GameState(gameState);
		updatedState.addColor(color, row, col);
		return updatedState;
	}

	private boolean isOcuppied(GameState state) {
		return state.getBoard()[row][col] != 0;
	}

	private boolean isColorFull(GameState state) {
		int maxColorCount = state.getSize() * state.getSize() / 2;
		if (this.color == GameState.BLUE) {
			return (state.getBlueCount() + 1) > maxColorCount;
		}
		if (this.color == GameState.RED) {
			return (state.getRedCount() + 1) > maxColorCount;
		}
		throw new IllegalArgumentException();
	}

	private boolean hasThreeAdjacentTiles(GameState state) {
		int[][] board = state.getBoard();
		int top = 0;
		for (int i = 1; i < 3; i++) {
			if (row - i >= 0) {
				if (board[row - i][col] == color) {
					top++;
				}
			}
		}
		if (top == 2)
			return true;
		int bottom = 0;
		for (int i = 1; i < 3; i++) {
			if (row + i < state.getSize()) {
				if (board[row + i][col] == color) {
					bottom++;
				}
			}
		}
		if (bottom == 2)
			return true;
		int right = 0;
		for (int i = 1; i < 3; i++) {
			if (col + i < state.getSize()) {
				if (board[row][col + i] == color) {
					right++;
				}
			}
		}
		if (right == 2)
			return true;
		int left = 0;
		for (int i = 1; i < 3; i++) {
			if (col - 1 >= 0) {
				if (board[row][col - 1] == color) {
					left++;
				}
			}
		}
		if (left == 2)
			return true;
		// row
		if (row - 1 >= 0 && row + 1 < state.getSize()) {
			if (board[row - 1][col] == color && board[row + 1][col] == color) {
				return true;
			}
		}
		// col
		if (col - 1 >= 0 && col + 1 < state.getSize()) {
			if (board[row][col - 1] == color && board[row][col + 1] == color) {
				return true;
			}
		}
		return false;
	}

	private boolean filledRowOrCol(GameState state) {
		int count = 0;
		// check that there is not to many of color already in row
		for (int i = 0; i < state.getSize(); i++) {
			if (state.getBoard()[row][i] == color)
				count++;
		}
		if (count > state.getSize() / 2 - 1)
			return true;

		// check that there is not to many of color already in column
		count = 0;
		for (int i = 0; i < state.getSize(); i++) {
			if (state.getBoard()[i][col] == color)
				count++;
		}
		if (count > state.getSize() / 2 - 1)
			return true;
		// If everything i ok
		return false;
	}
	
	/// new code
	
	private boolean containsSimilarities(GameState state) {
		if (fillingRow(state, row)) {
			int[] newRow = copyRow(state, row);
			newRow[col] = color;
			for (int i = 0; i < state.getSize(); i++) {
				if (i != row && rowsEqual(newRow, state, i))
					return true;
			} 
		}


		if (fillingCol(state, col)) {
			int[] newCol = copyCol(state, col);
			newCol[row] = color;
			for (int i = 0; i < state.getSize(); i++) {
				if (i != col && colsEqual(newCol, state, i))
					return true;
			} 
		}

		return false;
	}

	// determines if applying the rule will create a filled row.
	private boolean fillingRow(GameState state, int index) {
		int count = 0;
		for (int i = 0; i < state.getSize(); i++) {
			if (state.getBoard()[index][i] != 0)
				count++;
		}
		return count > state.getSize() - 2;
	}

	private boolean fillingCol(GameState state, int index) {
		int count = 0;
		for (int i = 0; i < state.getSize(); i++) {
			if (state.getBoard()[i][index] != 0)
				count++;
		}
		return count > state.getSize() - 2;
	}

	//copy the row that will get filled.
	private int[] copyRow(GameState state, int index) {
		int[] vector = new int[state.getSize()];
		for (int i = 0; i < state.getSize(); i++) {
			vector[i] = state.getBoard()[index][i];
		}
		return vector;
	}
	
	private int[] copyCol(GameState state, int index) {
		int[] vector = new int[state.getSize()];
		for (int i = 0; i < state.getSize(); i++) {
			vector[i] = state.getBoard()[i][index];
		}
		return vector;
	}

	//check if the new row is equal to row index.
	private boolean rowsEqual(int[] row, GameState state, int index) {
		for (int i = 0; i < state.getSize(); i++) {
			if (row[i] != state.getBoard()[index][i])
				return false;
		}

		return true;
	}
	
	private boolean colsEqual(int[] col, GameState state, int index) {
		for (int i = 0; i < state.getSize(); i++) {
			if (col[i] != state.getBoard()[i][index])
				return false;
		}

		return true;
	}

}
