package datavisualizer.views;

import java.util.ArrayList;

public class TraceInfo implements Comparable<TraceInfo>{

	private String name;
	private String core;
	private double priority;
	private double id;
	private ArrayList<StateInfo> states;


	@Override
	public int compareTo(TraceInfo other) {
		int diff = core.compareTo(other.getCore()); 
		if(diff < 0){
			return -1;
		}else if(diff > 0){
			return 1;
		}else
			if(diff < 0)
				return (int) Math.floor(priority - other.getPriority());
			else
				return (int) Math.ceil(priority - other.getPriority());
	}


	public double getId() {
		return id;
	}


	public void setId(double id) {
		this.id = id;
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


	public ArrayList<StateInfo> getStates() {
		return states;
	}


	public void setStates(ArrayList<StateInfo> states) {
		this.states = states;
	}
	
	public String toString(){
		String info = "name: " + name + " id: " + id + " core: " + core + " priority: " + priority + "\n";
		for(StateInfo currState: states){
			info += " |" + currState.getTimestamp() + ":" + currState.getState() + "|";
		}
		info += "\n";
		return info;
	}

}

