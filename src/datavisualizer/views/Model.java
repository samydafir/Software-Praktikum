package datavisualizer.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Model {

	private XmlParser xmlParser;
	private BinaryParser binaryParser;

	
	public Model(String xmlPath) throws ParserConfigurationException, SAXException, IOException{
		xmlParser = new XmlParser(xmlPath, ".*(Core\\_c[0-9]*).*");
		binaryParser = new BinaryParser();
		xmlParser.parse();
	}
	
	public ArrayList<StateInfo> getStates(ArrayList<Double> selectedTasks){
		return null;
		
	}

	public ArrayList<TaskInfo> getTaskInfo(){
		ArrayList<TaskInfo> tasks = new ArrayList<>();
		for(TaskInfo currTask: xmlParser.getTaskMap().values()){
			tasks.add(currTask);
		}
		Collections.sort(tasks);

		
		return tasks;
	}



}








