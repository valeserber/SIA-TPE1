package gps;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;
import gps.model.GameState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class GPSEngine {

	protected List<GPSNode> open = new LinkedList<GPSNode>();

	protected Set<GPSState> closed = new HashSet<GPSState>();

	protected GPSProblem problem;

	protected SearchStrategy strategy;
	
	protected Heuristic heuristic;
	
	protected int level;

	public void engine(GPSProblem myProblem, SearchStrategy myStrategy, Heuristic myHeuristic) throws InterruptedException {

		problem = myProblem;
		strategy = myStrategy;
		heuristic = myHeuristic;
		level = 0;

		GPSNode rootNode = new GPSNode(problem.getInitState(), 0, 0);
		boolean finished = false;
		boolean failed = false;
		long explosionCounter = 0;

		open.add(rootNode);
		long startTime = System.nanoTime();
		while (!failed && !finished) {
			if (open.size() <= 0) {
				failed = true;
			} else {
				GPSNode currentNode = open.get(0);
				closed.add(currentNode.getState());
				open.remove(0);
				if (isGoal(currentNode)) {
					finished = true;
					System.out.println(currentNode.getSolution());
					System.out.println("Expanded nodes: " + explosionCounter);
					System.out.println("Depth: " + currentNode.getDepth());
					System.out.println("Frontier nodes: " + open.size());
					System.out.println("Total States: " + open.size() + closed.size());
					long endTime = System.nanoTime();
					long duration = endTime - startTime;
					System.out.println("Processing Time: " + (duration/1000000000.0) + " sec");
				} else {
					explosionCounter++;
					explode(currentNode);
				}
			}
		}

		if (finished) {
			System.out.println("OK! solution found!");
			System.out.println();
		} else if (failed) {
			System.err.println("FAILED! solution not found!");
		}
	}

	private  boolean isGoal(GPSNode currentNode) {
		return currentNode.getState() != null
				&& problem.isGoalState(currentNode.getState());
	}

	protected  boolean explode(GPSNode node) throws InterruptedException {
//		((GameState)node.getState()).	Board();
		if(problem.getRules() == null){
			System.err.println("No rules!");
			return false;
		}
		List<GPSRule> rules = problem.getRules();
//		for (GPSRule r: rules) {
//			System.out.println(r.getName());
//		}
		for (GPSRule rule : rules) {
			GPSState newState = null;
			try {
//				System.out.println("rule applied: " + rule.getName());
				newState = rule.evalRule(node.getState());
			} catch (NotAppliableException e) {
				// Do nothing
			}
			if (newState != null
					&& !checkBranch(node, newState)
					&& !checkOpenAndClosed(node.getCost() + rule.getCost(),
							newState)) {
//				((GameState) newState).printBoard();
				GPSNode newNode = new GPSNode(newState, node.getCost()
						+ rule.getCost(), node.getDepth() + 1);
				newNode.setParent(node);
				addNode(newNode);
//				System.out.println("nodos en open: " + open.size());
//				int seconds = (int)(System.currentTimeMillis() /1000);
//				System.out.println("tiempo: " + seconds + "sg");
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
		if (closed.contains(state)) {
//			System.out.println("already here");
			return true;
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
