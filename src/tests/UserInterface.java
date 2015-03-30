package tests;

import gps.AStarEngine;
import gps.BFSEngine;
import gps.DFSEngine;
import gps.GPSEngine;
import gps.GreedyEngine;
import gps.Heuristic;
import gps.IDDFSEngine;
import gps.SearchStrategy;
import gps.model.GameProblem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface {
	public static void main(String[] args) {
		System.out.println("Select Heuristic\n");
		System.out.println("1-Rows");
		System.out.println("2-Possibilities");
		System.out.println("3-Minimun Color");
		System.out.println("4-Useful State");
		boolean notEntered = true;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String input, inputAlgorithm, level;
			Integer n = null;
			Heuristic heuristic=Heuristic.USEFULSTATE;
			SearchStrategy strategy=SearchStrategy.AStar;

			while (notEntered && (input = br.readLine()) != null) {
				System.out.println("You select:" + input + "\n");
				switch (Integer.parseInt(input)) {
				case 1:
					heuristic = Heuristic.ROWS;
					notEntered = false;
					break;
				case 2:
					heuristic = Heuristic.POSSIBILITIES;
					notEntered = false;
					break;
				case 3:
					heuristic = Heuristic.MINCOLOR;
					notEntered = false;
					break;
				case 4:
					heuristic = Heuristic.USEFULSTATE;
					notEntered = false;
					break;
				default:
					System.out.println("Enter a number between 1 and 4");
				}
			}
			notEntered = true;
			System.out.println("Select Algotithm\n");
			System.out.println("A-BFS");
			System.out.println("B-DFS");
			System.out.println("C-IDDFS");
			System.out.println("D-GREEDY");
			System.out.println("E-A*");
			while (notEntered && (inputAlgorithm = br.readLine()) != null) {
				System.out.println("You select Algotithm: " + inputAlgorithm);
				switch (inputAlgorithm) {
				case "A":
					strategy = SearchStrategy.BFS;
					notEntered = false;
					break;
				case "B":
					strategy = SearchStrategy.DFS;
					notEntered = false;
					break;
				case "C":
					strategy = SearchStrategy.IDDFS;
					notEntered = false;
					break;
				case "D":
					strategy = SearchStrategy.GREEDY;
					notEntered = false;
					break;
				case "E":
					strategy = SearchStrategy.AStar;
					notEntered = false;
					break;
				default:
					System.out.println("Enter a letter between A and E");
				}
			}
			notEntered = true;
			System.out.println("Select a Level from 1 to 5\n");
			while (notEntered && (level = br.readLine()) != null) {
				String s = level.substring(0, 1);
				try {
					n = Integer.parseInt(s);
					if (n < 1 || n > 5) {
						System.out.println("Invalid level");
					} else {
						notEntered = false;
					}
				} catch (NumberFormatException e) {
					System.out.println("Enter a number between 1 and 5");
				}
			}
			Run(strategy, heuristic, n);

		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	private static void Run(SearchStrategy strategy, Heuristic heuristic, int level) {
		GameProblem problem = new GameProblem(level);
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
		try {
			gps.engine(problem, strategy, heuristic);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}