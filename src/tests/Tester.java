package tests;

import gps.model.GameProblem;
import gps.GPSEngineImpl;
import gps.Heuristic;
import gps.SearchStrategy;

public class Tester {
	public static void main(String[] args) {
		GameProblem problem = new GameProblem();
		GPSEngineImpl engine = new GPSEngineImpl();
		try {
			engine.engine(problem, SearchStrategy.GREEDY, Heuristic.MINCOLOR);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
