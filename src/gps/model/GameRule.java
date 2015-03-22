package gps.model;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

import java.util.LinkedList;
import java.util.List;

public class GameRule implements GPSRule {

	// TODO 1: Falta la regla de que no haya dos columnas o filas IGUALES
	// TODO 2: ¿Puedo encadenar blockRow con blockCol? Usar tests para probar

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
		isAppliable(gameState);
		blockOtherTailInCol(gameState);
		blockOtherTailInRow(gameState);
		GameState updatedState = new GameState(gameState);
		updatedState.addColor(color, row, col);
		return updatedState;
	}

	private void isAppliable(GameState gameState) throws NotAppliableException {
		if (isOcuppied(gameState) || filledRowOrCol(gameState)
				|| hasThreeAdjacentTiles(gameState) || isColorFull(gameState)) {
			throw new NotAppliableException();
		}
	}

	private boolean isOcuppied(GameState state) {
		return state.getBoard()[row][col] != 0;
	}

	private void blockOtherTailInRow(GameState state)
			throws NotAppliableException {
		for (int i = 0; i < GameState.SIZE; i++) { // recorro la fila
			GameRule r = getRedRulesForTile(row, i); // genero las
														// reglas de la
														// fila
			int[][] newBoard = copyBoard(state.getBoard());
			newBoard[row][col] = color; // nuevo tablero con mi regla
										// agregada
			try {
				r.isAppliable(new GameState(newBoard));
			} catch (NotAppliableException e) { // si no puedo poner una
												// regla
				if (newBoard[r.row][r.col] == 0) {
					r = getPairRule(r);
					try {
						r.isAppliable(new GameState(newBoard));
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
		return new GameRule((r.color == 1) ? 2 : 1, r.row, r.col);
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
			GameRule r = getRedRulesForTile(i, col);
			int[][] newBoard = copyBoard(state.getBoard());
			newBoard[row][col] = color; // nuevo tablero con mi regla agregada
			try {
				r.isAppliable(new GameState(newBoard));
			} catch (NotAppliableException e) { // si no puedo poner una regla
				if (newBoard[r.row][r.col] == 0) {
					r = getPairRule(r);
					try {
						r.isAppliable(new GameState(newBoard));
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
		for (int i = 0; i < GameState.SIZE; i++) {
			if (state.getBoard()[row][i] == color)
				count++;
		}
		if (count > GameState.SIZE / 2 - 1)
			return true;

		// check that there is not to many of color already in column
		count = 0;
		for (int i = 0; i < GameState.SIZE; i++) {
			if (state.getBoard()[i][col] == color)
				count++;
		}
		if (count > GameState.SIZE / 2 - 1)
			return true;
		// If everything i ok
		return false;
	}
}
