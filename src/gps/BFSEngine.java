package gps;

public class BFSEngine extends GPSEngine {

	@Override
	public void addNode(GPSNode node) {
		open.add(node);
	}

}
