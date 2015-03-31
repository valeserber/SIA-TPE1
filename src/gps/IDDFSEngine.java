package gps;

import java.util.LinkedList;

public class IDDFSEngine extends GPSEngine {
	
	private int currentLimit;

	protected  boolean explode(GPSNode node) throws InterruptedException {
		if (node.getDepth() >= currentLimit) {
			open.clear();
			closed.clear();
			open.add(new GPSNode(problem.getInitState(), 0, 0));
			currentLimit += 10;
			return true;
		}
		return super.explode(node);
	}

	@Override
	public void addNode(GPSNode node) {
		((LinkedList<GPSNode>) open).addFirst(node);
	}

}
