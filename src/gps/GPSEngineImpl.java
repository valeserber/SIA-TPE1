package gps;

import gps.model.GameProblem;
import gps.model.GameState;

import java.util.LinkedList;

public class GPSEngineImpl extends GPSEngine {

	public void addNode(GPSNode node) {
		if (!(node.getState() instanceof GameState)) {
			throw new IllegalArgumentException();
		}
		switch (strategy) {
		case DFS:
			addNodeDFS(node);
			break;
		case BFS:
			addNodeBFS(node);
			break;
		case IDDFS:
			addNodeIDDFS(node);
		case GREEDY:
			addNodeGreedy(node);
		case AStar:
			addNodeAstar(node);
		default:
			break;
		}
	}

	private void addNodeDFS(GPSNode node) {
		((LinkedList<GPSNode>) open).addFirst(node);
	}

	private void addNodeBFS(GPSNode node) {
		open.add(node);
	}

	private void addNodeAstar(GPSNode node) {
		if (open.size() == 0) {
			((LinkedList<GPSNode>) open).addFirst(node);
			return;
		}
		GameProblem g = new GameProblem();
		int index = 0;
		int openSize = open.size();
		GPSNode actualNode = node;
		int fvalue = g.getHValue(actualNode.getState(), heuristic) + actualNode.getCost();
		while (index < openSize) {
			GPSNode otherNode = open.get(index);
			int otherFvalue = g.getHValue(otherNode.getState(), heuristic) + otherNode.getCost();
			if (otherFvalue >= fvalue) {
				open.add(index, actualNode);
				return;
			} else {
				index++;
			}
		}
		open.add(actualNode);
	}

	private void addNodeGreedy(GPSNode node) {
		if (open.size() == 0) {
			((LinkedList<GPSNode>) open).addFirst(node);
			return;
		}
		GameProblem g = new GameProblem();
		int index = 0;
		int openSize = open.size();
		GPSNode actualNode = node;
		int hvalue = g.getHValue(actualNode.getState(), heuristic);
		int myColors = ((GameState) actualNode.getState()).getColoredCount();
		while (index < openSize) {
			GPSNode otherNode = open.get(index);
			int otherHvalue = g.getHValue(otherNode.getState(), heuristic);
			int otherColors = ((GameState) otherNode.getState())
					.getColoredCount();
			if (otherHvalue >= hvalue || otherColors < myColors) {
				open.add(index, actualNode);
				return;
			} else {
				index++;
			}
		}
		open.add(actualNode);
	}

	private void addNodeIDDFS(GPSNode node) {
		// TODO Auto-generated method stub

	}
}