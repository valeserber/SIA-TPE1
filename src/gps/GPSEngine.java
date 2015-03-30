package gps;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;
import gps.model.GameState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class GPSEngine {

	protected List<GPSNode> open = new LinkedList<GPSNode>();

	private List<GPSNode> closed = new ArrayList<GPSNode>();

	private GPSProblem problem;

	protected SearchStrategy strategy;
	
	protected Heuristic heuristic;

	public void engine(GPSProblem myProblem, SearchStrategy myStrategy, Heuristic myHeuristic) throws InterruptedException {

		problem = myProblem;
		strategy = myStrategy;
		heuristic = myHeuristic;

		GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
		boolean finished = false;
		boolean failed = false;
		long explosionCounter = 0;

		open.add(rootNode);
		int i = 0;
		int j = -50 + 18;
		while (!failed && !finished) {
			i++;
			if (open.size() <= 0 || i > 50000) {
				failed = true;
			} else {
				GPSNode currentNode = open.get(0);
				closed.add(currentNode);
				if (closed.size() % 20000 == 0) {
					failed = true;
				}
//				if (((GameState) currentNode.getState()).getColoredCount() < 50)
//					System.out.println("--" + i + "--" + ((GameState) currentNode.getState()).getColoredCount() + " -- " + j++);
//				System.out.println("row: " + (1 + ((GameState) currentNode.getState()).row));
//				System.out.println("col: " + (1 + ((GameState) currentNode.getState()).col));
//				System.out.println(((GameState) currentNode.getState()));
				open.remove(0);
				if (isGoal(currentNode)) {
					finished = true;
					System.out.println(currentNode.getSolution());
					System.out.println("Expanded nodes: " + explosionCounter);
				} else {
					explosionCounter++;
					explode(currentNode);
				}
			}
		}

		if (finished) {
			System.out.println("OK! solution found!");
		} else if (failed) {
			System.err.println("FAILED! solution not found!");
		}
	}

	private  boolean isGoal(GPSNode currentNode) {
		return currentNode.getState() != null
				&& problem.isGoalState(currentNode.getState());
	}

	private  boolean explode(GPSNode node) throws InterruptedException {
//		((GameState)node.getState()).printBoard();
		if(problem.getRules() == null){
			System.err.println("No rules!");
			return false;
		}
		List<GPSRule> rules = problem.getRules();
		for (GPSRule rule : rules) {
			GPSState newState = null;
			try {
				newState = rule.evalRule(node.getState());
			} catch (NotAppliableException e) {
				// Do nothing
			}
			if (newState != null
					&& !checkBranch(node, newState)
					&& !checkOpenAndClosed(node.getCost() + rule.getCost(),
							newState)) {
				GPSNode newNode = new GPSNode(newState, node.getCost()
						+ rule.getCost());
				newNode.setParent(node);
				addNode(newNode);
			}
		}
		return true;
	}

	private  boolean checkOpenAndClosed(Integer cost, GPSState state) {
		for (GPSNode openNode : open) {
			if (openNode.getState().compare(state) && openNode.getCost() <= cost) {
				return true;
			}
		}
		for (GPSNode closedNode : closed) {
			if (closedNode.getState().compare(state)
					&& closedNode.getCost() <= cost) {
				return true;
			}
		}
		return false;
	}

	private  boolean checkBranch(GPSNode parent, GPSState state) {
		if (parent == null) {
			return false;
		}
		return checkBranch(parent.getParent(), state)
				|| state.compare(parent.getState());
	}

	public abstract  void addNode(GPSNode node);
	
}
