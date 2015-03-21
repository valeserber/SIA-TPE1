package gps;

import java.util.LinkedList;

public class GPSEngineImpl extends GPSEngine {

	public void addNode(GPSNode node) {
		switch (strategy) {
		case DFS:
			addNodeDFS(node);
			break;
		case BFS:
			addNodeBFS(node);
			break;
		case IDDFS:
			addNodeIDDFS(node);
		case Greedy:
			addNodeGreedy(node);
		case AStar:
			addNodeAstar();
		default:
			break;
		}
	}

	private void addNodeDFS(GPSNode node) {
		// adds node to the beginning of the list
		((LinkedList<GPSNode>) open).addFirst(node);
	}

	private void addNodeBFS(GPSNode node) {
		// adds node to the end of the list
		open.add(node);
	}

	private void addNodeAstar() {
		// TODO Auto-generated method stub

	}

	private void addNodeGreedy(GPSNode node) {
		// TODO Auto-generated method stub

	}

	private void addNodeIDDFS(GPSNode node) {
		// TODO Auto-generated method stub

	}
}