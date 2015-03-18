package gps.model;

import java.util.List;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

public class GameProblem implements GPSProblem{

	@Override
	public GPSState getInitState() {
		int[][] board = {{0, 0, 0, 0, 1, 0, 0, 0}, 
				{0, 0, 0, 0, 0, 0, 0, 0}, 
				{1, 0, 0, 2, 0, 0, 0, 2}, 
				{0, 0, 0, 0, 1, 0, 2, 2}, 
				{0, 0, 1, 0, 0, 1, 0, 0}, 
				{1, 0, 1, 0, 0, 0, 0, 0}, 
				{0, 0, 0, 1, 0, 0, 2, 2}, 
				{0, 0, 0, 0, 0, 0, 0, 0}}; 
		GPSState initialState = new GameState(board);
		return initialState;
	}

	@Override
	public List<GPSRule> getRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getHValue(GPSState state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGoalState(GPSState state) {
		GameState gameState = (GameState) state;
		return (gameState.getBlueCount() + gameState.getRedCount()) 
				== (gameState.getSize()*gameState.getSize());
	}
}
