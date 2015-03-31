package gps;

import gps.model.GameState;

import java.util.LinkedList;

public class GreedyEngine extends GPSEngine {

	@Override
	public void addNode(GPSNode node) {
		if (!(node.getState() instanceof GameState)) {
			throw new IllegalArgumentException();
		}
		if (open.size() == 0) {
			((LinkedList<GPSNode>) open).addFirst(node);
			return;
		}
		int index = 0;
		int openSize = open.size();
		GPSNode actualNode = node;
		int hvalue = problem.getHValue(actualNode.getState(), heuristic);
		int myColors = ((GameState) actualNode.getState()).getColoredCount();
		while (index < openSize) {
			GPSNode otherNode = open.get(index);
			int otherHvalue = problem.getHValue(otherNode.getState(), heuristic);
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

}
