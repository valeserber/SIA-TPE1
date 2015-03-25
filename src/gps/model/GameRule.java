package gps.model;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

import java.util.LinkedList;
import java.util.List;

public class GameRule implements GPSRule {

	// TODO 1: ¿Puedo encadenar blockRow con blockCol? Usar tests para probar

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
		return String.valueOf(getColor()) + " (" + String.valueOf(getRow())
				+ ", " + String.valueOf(getCol()) + ")";
	}

	@Override
	public GPSState evalRule(GPSState state) throws NotAppliableException {
		if (!(state instanceof GameState)) {
			throw new IllegalArgumentException();
		}
		GameState gameState = (GameState) state;
		isAppliable(gameState);
		blockOtherTailInCol(gameState);
		blockOtherTailInRow(gameState);
		GameState updatedState = new GameState(gameState);
		updatedState.addColor(getColor(), getRow(), getCol());
		return updatedState;
	}

	private void isAppliable(GameState gameState) throws NotAppliableException {
		if (isOcuppied(gameState) || filledRowOrCol(gameState)
				|| hasThreeAdjacentTiles(gameState) || isColorFull(gameState) || containsSimilarities(gameState)) {
			throw new NotAppliableException();
		}
	}

	private boolean isOcuppied(GameState state) {
		return state.getBoard()[getRow()][getCol()] != 0;
	}

	private void blockOtherTailInRow(GameState state)
			throws NotAppliableException {
		for (int i = 0; i < GameState.SIZE; i++) { // recorro la fila
			GameRule r = getRedRulesForTile(getRow(), i); // genero las
															// reglas de la
															// fila
			GameState newState = new GameState(state);
			newState.addColor(getColor(), getRow(), getCol());// nuevo tablero
																// con mi regla
			// agregada
			try {
				r.isAppliable(newState);
			} catch (NotAppliableException e) { // si no puedo poner una
												// regla
				if (newState.getBoard()[r.getRow()][r.getCol()] == 0) {
					r = getPairRule(r);
					try {
						r.isAppliable(newState);
					} catch (NotAppliableException e2) { // ni el otro color
															// en el mismo
															// lugar
						throw new NotAppliableException();
					}
				}
			}
		}

	}

	private GameRule getPairRule(GameRule r) {
		return new GameRule((r.getColor() == 1) ? 2 : 1, r.getRow(), r.getCol());
	}

	private int[][] copyBoard(int[][] board) {
		int[][] newBoard = new int[GameState.SIZE][GameState.SIZE];
		for (int i = 0; i < GameState.SIZE; i++) {
			for (int j = 0; j < GameState.SIZE; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}

	private void blockOtherTailInCol(GameState state)
			throws NotAppliableException {
		for (int i = 0; i < GameState.SIZE; i++) { // recorro la fila
			GameRule r = getRedRulesForTile(i, getCol());
			GameState newState = new GameState(state);
			newState.addColor(getColor(), getRow(), getCol()); // nuevo tablero
																// con mi regla
																// agregada
			try {
				r.isAppliable(newState);
			} catch (NotAppliableException e) { // si no puedo poner una regla
				if (newState.getBoard()[r.getRow()][r.getCol()] == 0) {
					r = getPairRule(r);
					try {
						r.isAppliable(newState);
					} catch (NotAppliableException e2) { // ni el otro color en
															// el mismo lugar
						throw new NotAppliableException();
					}
				}
			}
		}
	}

	private GameRule getRedRulesForTile(int r, int col) {
		return new GameRule(GameState.RED, r, col);
	}

	private boolean isColorFull(GameState state) {
		int maxColorCount = state.getSize() * state.getSize() / 2;
		if (this.getColor() == GameState.BLUE) {
			return (state.getBlueCount() + 1) > maxColorCount;
		}
		if (this.getColor() == GameState.RED) {
			return (state.getRedCount() + 1) > maxColorCount;
		}
		throw new IllegalArgumentException();
	}

	private boolean hasThreeAdjacentTiles(GameState state) {
		int[][] board = state.getBoard();
		int top = 0;
		for (int i = 1; i < 3; i++) {
			if (getRow() - i >= 0) {
				if (board[getRow() - i][getCol()] == getColor()) {
					top++;
				}
			}
		}
		if (top == 2)
			return true;
		int bottom = 0;
		for (int i = 1; i < 3; i++) {
			if (getRow() + i < state.getSize()) {
				if (board[getRow() + i][getCol()] == getColor()) {
					bottom++;
				}
			}
		}
		if (bottom == 2)
			return true;
		int right = 0;
		for (int i = 1; i < 3; i++) {
			if (getCol() + i < state.getSize()) {
				if (board[getRow()][getCol() + i] == getColor()) {
					right++;
				}
			}
		}
		if (right == 2)
			return true;
		int left = 0;
		for (int i = 1; i < 3; i++) {
			if (getCol() - i >= 0) {
				if (board[getRow()][getCol() - i] == getColor()) {
					left++;
				}
			}
		}
		if (left == 2)
			return true;
		// row
		if (getRow() - 1 >= 0 && getRow() + 1 < state.getSize()) {
			if (board[getRow() - 1][getCol()] == getColor()
					&& board[getRow() + 1][getCol()] == getColor()) {
				return true;
			}
		}
		// col
		if (getCol() - 1 >= 0 && getCol() + 1 < state.getSize()) {
			if (board[getRow()][getCol() - 1] == getColor()
					&& board[getRow()][getCol() + 1] == getColor()) {
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
		// If everything is ok
		return false;
	}

	// check if new rule makes two rows or cols similar.	
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

	public int getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}
