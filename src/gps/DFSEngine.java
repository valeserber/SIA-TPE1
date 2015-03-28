package gps;

import java.util.LinkedList;

public class DFSEngine extends GPSEngine{

	@Override
	public void addNode(GPSNode node) {
		((LinkedList<GPSNode>) open).addFirst(node);
	}

}
