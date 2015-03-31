package gps;

import java.util.LinkedList;

public class AStarEngine extends GPSEngine {

	@Override
	public void addNode(GPSNode node) {
		if (open.size() == 0) {
			((LinkedList<GPSNode>) open).addFirst(node);
			return;
		}
		int index = 0;
		int openSize = open.size();
		GPSNode actualNode = node;
		int fvalue = problem.getHValue(actualNode.getState(), heuristic) + actualNode.getCost();
		while (index < openSize) {
			GPSNode otherNode = open.get(index);
			int otherFvalue = problem.getHValue(otherNode.getState(), heuristic) + otherNode.getCost();
			if (otherFvalue >= fvalue) {
				open.add(index, actualNode);
				return;
			} else {
				index++;
			}
		}
		open.add(actualNode);
	}

}
