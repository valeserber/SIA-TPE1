package gps.model;

import gps.api.GPSState;

public class GameState implements GPSState {

	public static int SIZE = 8;
	public static int EMPTY = 0;
	public static int RED = 1;
	public static int BLUE = 2;

	private int red;
	private int blue;
	private int size;
	private int[][] board;

	public GameState(int size) {
		this.size = size;
		this.board = new int[size][size];
	}

	// para inicializar los juegos
	public GameState(int[][] board) {
		this.size = board.length;
		this.board = board;
		countColors();
	}

	public GameState(GameState gameState) {
		this.size = gameState.size;
		this.board = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.board[i][j] = gameState.getBoard()[i][j];
			}
		}
	}

	public int getRedCount() {
		return this.red;
	}

	public int getBlueCount() {
		return this.blue;
	}

	public int getSize() {
		return this.size;
	}

	// Constructor que recibe GameState y devuelve un "clon" pero a mano

	public int[][] getBoard() {
		return this.board;
	}

	@Override
	public boolean compare(GPSState state) {
		if (!(state instanceof GameState)) {
			return false;
		}
		GameState gameState = (GameState) state;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.board[i][j] != gameState.board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	private void countColors() {
		this.red = 0;
		this.blue = 0;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.board[i][j] == RED) {
					red++;
				} else if (this.board[i][j] == BLUE) {
					blue++;
				}
			}
		}
	}
	
	public void addColor(int color, int row, int col) {
		this.board[row][col] = color;
	}

}
