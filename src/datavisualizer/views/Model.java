package datavisualizer.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Model {

	private XmlParser xmlParser;
	private BinaryParser binaryParser;
	TreeSet<TraceInfo> traceInfo;

	
	public Model() throws ParserConfigurationException, SAXException, IOException{
		xmlParser = new XmlParser();
		binaryParser = new BinaryParser();
		traceInfo = new TreeSet<>();
	}
	
	public ArrayList<StateInfo> getStates(ArrayList<Double> selectedTasks){
		return null;
	}
	

	public void parseXml(String inputPath){
		try {
			xmlParser.parse(inputPath, ".*(Core\\_c[0-9]*).*");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO HANDLE EXCEPTIONS
			e.printStackTrace();
		}
		
	}
	public void parseBinaries(String[] paths, Set<Double> selectedIds){
		try {
			binaryParser.parseBinary(paths, selectedIds);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public ArrayList<TaskInfo> getTaskInfo(){
		ArrayList<TaskInfo> tasks = new ArrayList<>();
		for(TaskInfo currTask: xmlParser.getTaskMap().values()){
			tasks.add(currTask);
		}
		Collections.sort(tasks);

		
		return tasks;
	}
	
	public HashMap<String, TaskInfo> getTaskMap(){
		return xmlParser.getTaskMap();
	}
	
	public HashMap<Double, ArrayList<StateInfo>> getStateMap() {
		return binaryParser.getStateMap();
	}
}