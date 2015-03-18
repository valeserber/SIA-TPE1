package gps.model;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class GameRule implements GPSRule{
	
	private int color;
	private int row;
	private int col;
	
	public GameRule(int color, int row, int col) {
		super();
		//TODO: validar los parametros
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
		return String.valueOf(color) + " (" +
				String.valueOf(row) + ", " +
				String.valueOf(col) + ")";
	}

	@Override
	public GPSState evalRule(GPSState state) throws NotAppliableException {
		if (!(state instanceof GameState)){
			throw new IllegalArgumentException();
		}
		GameState gameState = (GameState) state;
		
		
		throw new NotAppliableException();
	}
	
	private boolean isOcuppied(GameState state, int row, int col) {
		return state.getBoard()[row][col] != 0;
	}
	
	private boolean isColorFull(GameState state) {
		int maxColorCount = state.getSize()*state.getSize()/2;
		if (this.color == GameState.BLUE) {
			return (state.getBlueCount() + 1) > maxColorCount;
		}
		if (this.color == GameState.RED) {
			return (state.getRedCount() + 1) > maxColorCount;
		}
		throw new IllegalArgumentException();
	}

}
