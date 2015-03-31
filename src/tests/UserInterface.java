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
		System.out.println("Select Algorithm\n");
		System.out.println("A-BFS");
		System.out.println("B-DFS");
		System.out.println("C-IDDFS");
		System.out.println("D-GREEDY");
		System.out.println("E-A*");
		
		boolean notEntered = true;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String input, inputAlgorithm, level;
			Integer n = null;
			Heuristic heuristic = Heuristic.COLUMNS;
			SearchStrategy strategy = SearchStrategy.AStar;

			while (notEntered && (inputAlgorithm = br.readLine()) != null) {
				System.out.println("You select Algorithm: " + inputAlgorithm);
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
			
			if (strategy == SearchStrategy.AStar || strategy == SearchStrategy.GREEDY) {
				System.out.println("Select Heuristic\n");
				System.out.println("1-Columns");
				System.out.println("2-Minimum Color");
				System.out.println("3-Full Color");
				while (notEntered && (input = br.readLine()) != null) {
					System.out.println("You select:" + input + "\n");
					switch (Integer.parseInt(input)) {
					case 1:
						heuristic = Heuristic.COLUMNS;
						notEntered = false;
						break;
					case 2:
						heuristic = Heuristic.MINCOLOR;
						notEntered = false;
						break;
					case 3:
						heuristic = Heuristic.FULLCOLOR;
						notEntered = false;
						break;
					default:
						System.out.println("Enter a number between 1 and 3");
					}
				}
			}
			notEntered = true;
			System.out.println("Select a board from 1 to 4\n");
			while (notEntered && (level = br.readLine()) != null) {
				String s = "";
				try {
					s = level.substring(0, 1);
					n = Integer.parseInt(s);
					if (n < 1 || n > 4) {
						System.out.println("Invalid board");
					} else {
						notEntered = false;
					}
				} catch (Exception e) {
					System.out.println("Enter a number between 1 and 4");
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
