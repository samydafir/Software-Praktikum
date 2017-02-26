package datavisualizer.views;

/**
 * Represents a task as read from the xml file with all its properties (id, priority, core, name).
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class TaskInfo implements Comparable<TaskInfo>{

	private double id;
	private double priority;
	private String core;
	private String name;
	
	
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
	
	/**
	 * returns a String representation of the object including all data it holds.
	 */
	public String toString(){
		return "Name: " + name + " Core: " + core + " ID: " + id + ", Priority: " + priority;
	}
	
	/**
	 * Required to make objects of this class comparable. Primarily ordered by core, secondarily by id.
	 * @param t2 other TaskInfo object to compare to.
	 */
	@Override
	public int compareTo(TaskInfo t2) {
		if (core.compareTo(t2.getCore()) > 0){
			return 1;
		}else if (core.compareTo(t2.getCore()) < 0){
			return -1;
		}
		return (int) (id - t2.getId());
	}
	
}
