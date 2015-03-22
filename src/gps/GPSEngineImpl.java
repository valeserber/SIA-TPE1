package gps;

import gps.model.GameProblem;
import gps.model.GameState;

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
		((LinkedList<GPSNode>) open).addFirst(node);
	}

	private void addNodeBFS(GPSNode node) {
		open.add(node);
	}

	private void addNodeAstar() {
		// TODO Auto-generated method stub

	}

	private void addNodeGreedy(GPSNode node) {
		if (!(node.getState() instanceof GameState)) {
			throw new IllegalArgumentException();
		}
		if(open.size()==0){
			((LinkedList<GPSNode>) open).addFirst(node);
			return;
		}
		GameProblem g = new GameProblem();
		GPSNode otherNode = open.get(0);
		open.remove(0);
		int hvalue = g.getHValue(node.getState());
		int otherHvalue = g.getHValue(otherNode.getState());
		int myColors = ((GameState)node.getState()).getColoredCount();
		int otherColors= ((GameState)otherNode.getState()).getColoredCount();
		
		if(hvalue>=otherHvalue || myColors>otherColors){
			((LinkedList<GPSNode>)open).addFirst(otherNode);
			((LinkedList<GPSNode>)open).addFirst(node);
		}else{
			((LinkedList<GPSNode>)open).addFirst(node);
			((LinkedList<GPSNode>)open).addFirst(otherNode);
		}
	}

	private void addNodeIDDFS(GPSNode node) {
		// TODO Auto-generated method stub

	}
}