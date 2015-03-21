package gps.model;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

import java.util.LinkedList;
import java.util.List;

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
		List<GPSRule> rules = new LinkedList<GPSRule>();
		for(int c=1; c<3;c++){
			for(int i=0; i< GameState.SIZE;i++){
				for (int j=0; j<GameState.SIZE; j++){
					rules.add(new GameRule(c,i,j));
				}
			}
		}
		return rules;
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
