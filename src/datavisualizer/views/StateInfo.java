package datavisualizer.views;

/**
 * Represents a state (point in the graph) identified by timestamp and state.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class StateInfo {
	private double timestamp;
	private double  state;
	
	
	public double getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}
	public double getState() {
		return state;
	}
	public void setState(double state) {
		this.state = state;
	}
	
	
	

}
