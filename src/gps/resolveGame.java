package gps;

import gps.model.GameProblem;

public class resolveGame {

	public static void main(String[] args) throws InterruptedException {
		GPSEngine gps = new GPSEngineImpl();
		gps.engine(new GameProblem(), SearchStrategy.Greedy, Heuristic.POSSIBILITIES);
	}
}
