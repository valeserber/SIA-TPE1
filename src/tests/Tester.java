package tests;

import gps.model.GameProblem;
import gps.AStarEngine;
import gps.BFSEngine;
import gps.DFSEngine;
import gps.GPSEngine;
import gps.GreedyEngine;
import gps.Heuristic;
import gps.IDDFSEngine;
import gps.SearchStrategy;

public class Tester {

	public static void main(String[] args) {
		String s = args[0];
		Integer n = Integer.parseInt(s);
		GameProblem problem;
		try{
			problem = new GameProblem(n);
			SearchStrategy strategy = SearchStrategy.IDDFS;
			Heuristic heuristic = Heuristic.USEFULSTATE;
			GPSEngine gps = null;

			switch(strategy) {
			case BFS:
				gps = new BFSEngine();
				break;
			case DFS:
				gps = new DFSEngine();
				break;
			case AStar:
				gps = new AStarEngine();
				break;
			case IDDFS:
				gps = new IDDFSEngine();
				break;
			case GREEDY:
				gps = new GreedyEngine();
				break;
			default:
				break;
			}
			gps.engine(problem, strategy, heuristic);
		} catch (IllegalArgumentException e){
			System.out.println("Invalid level.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
