package datavisualizer.views;

public class TraceInfo implements Comparable<TraceInfo>{

	private String name;
	private String core;
	private double priority;
	private StateInfo states;


	@Override
	public int compareTo(TraceInfo other) {
		int diff = core.compareTo(other.getCore()); 
		if(diff < 0){
			return -1;
		}else if(diff > 0){
			return 1;
		}else
			return (int)(priority - other.getPriority());
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCore() {
		return core;
	}


	public void setCore(String core) {
		this.core = core;
	}


	public double getPriority() {
		return priority;
	}


	public void setPriority(double priority) {
		this.priority = priority;
	}


	public StateInfo getStates() {
		return states;
	}


	public void setStates(StateInfo states) {
		this.states = states;
	}

}

