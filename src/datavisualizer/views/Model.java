package datavisualizer.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Model {

	public HashMap<String, TaskInfo> taskInfo;
	private HashMap<Double, ArrayList<StateInfo>> stateInfo;
	private XmlParser xmlParser;
	private BinaryParser binaryParser;

	
	public Model(String xmlPath) throws ParserConfigurationException, SAXException, IOException{
		xmlParser = new XmlParser("E:\\OneDrive - stud.sbg.ac.at\\University\\WS16\\Software Praktikum\\Software-Praktikum\\data\\platformModel.xml", ".*(Core\\_c[0-9]*).*");
		binaryParser = new BinaryParser();
		xmlParser.parse();
	}
	
	public void getStates(){
		
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








