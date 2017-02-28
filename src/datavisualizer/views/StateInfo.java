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
	
	/**
	 * @return the timestamp for this object
	 */
	public double getTimestamp() {
		return timestamp;
	}
	
	/** 
	 * @param timestamp timestamp of this object
	 */
	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * @return the state of this object
	 */
	public double getState() {
		return state;
	}
	
	/**
	 * @param state state of this object
	 */
	public void setState(double state) {
		this.state = state;
	}
}
