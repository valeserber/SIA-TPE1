package tests;

import gps.model.GameProblem;
import gps.model.GameState;
import gps.GPSEngineImpl;
import gps.Heuristic;
import gps.SearchStrategy;

public class Tester {
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		GameProblem problem = new GameProblem();
		
//		GameState state = (GameState) problem.getInitState();
//		for (int r = 0; r < 8; r++) {
//			for (int c = 0; c < 8; c++) {
//				System.out.print(state.getBoard()[r][c]);
//			}
//			System.out.println("");
//		}
//		for (int r = 0; r < 8; r++) {
//			for (int c = 0; c < 8; c++) {
//				if (problem.checkDoubleColor(state, r, c, 0) > 0) {
//					System.out.println("r: " + r + " c: " + c + " " + problem.checkDoubleColor(state, r, c, 0));
//				}
//			}
//		}
		
		GPSEngineImpl engine = new GPSEngineImpl();
		try {
			engine.engine(problem, SearchStrategy.GREEDY, Heuristic.USEFULSTATE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		time -= System.currentTimeMillis();
		System.out.println("time: " + (-time));
	}

}
