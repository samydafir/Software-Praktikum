package datavisualizer.views;

public class TaskInfo {

	private double id;
	private double priority;
	private String core;
	
	
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	public double getPriority() {
		return priority;
	}
	public void setPriority(double priority) {
		this.priority = priority;
	}
	
	public String toString(){
		return "Core: " + core + " ID: " + id + ", Priority: " + priority;
	}

	
}
