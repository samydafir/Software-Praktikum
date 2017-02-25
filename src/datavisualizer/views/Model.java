package datavisualizer.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * The model unites all parts of the application. It manages xml and binary parsing as well as the info
 * required to plot the traces in the nebula xyGraph.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class Model {

	private XmlParser xmlParser;
	private BinaryParser binaryParser;
	TreeSet<TraceInfo> traceInfo;

	/**
	 * Constructor. Initializes xml-, binary-parser and traceInfo Set.
	 */
	public Model() throws ParserConfigurationException, SAXException, IOException{
		xmlParser = new XmlParser();
		binaryParser = new BinaryParser();
		traceInfo = new TreeSet<>();
	}
	
	
	/**
	 * Calls the xml parser passing the xml path and a regex identifying the processor core nodes
	 * @param inputPath path to xml file
	 */
	public void parseXml(String inputPath){
		try {
			xmlParser.parse(inputPath, ".*(Core\\_c[0-9]*).*");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Parses the selected binary files and extracts states of processes with ids specified in the passes Set. 
	 * @param paths path to binary files
	 * @param selectedIds Ids of processes selected to be extracted from the binary files
	 */
	public void parseBinaries(String[] paths, Set<Double> selectedIds){
		try {
			binaryParser.parseBinary(paths, selectedIds);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a sorted list of tasks sorted by their intrinsic ordering (core, priority)
	 * @return sorted list of tasks
	 */
	public ArrayList<TaskInfo> getTaskInfo(){
		ArrayList<TaskInfo> tasks = new ArrayList<>();
		for(TaskInfo currTask: xmlParser.getTaskMap().values()){
			tasks.add(currTask);
		}
		Collections.sort(tasks);
		return tasks;
	}
	
	/**
	 * @return taskMap from the xmlParser
	 */
	public HashMap<String, TaskInfo> getTaskMap(){
		return xmlParser.getTaskMap();
	}
	
	/**
	 * @return stateMap from the binary parser
	 */
	public HashMap<Double, ArrayList<StateInfo>> getStateMap() {
		return binaryParser.getStateMap();
	}
}