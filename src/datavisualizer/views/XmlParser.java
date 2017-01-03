package datavisualizer.views;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {
	
	private Node root;
	private Pattern pattern;
	private String currCoreName;
	private HashMap<String, TaskInfo> taskMap;
	
	public XmlParser(String inputPath, String nodeSelectionRegex) throws ParserConfigurationException, SAXException, IOException{
		taskMap = new HashMap<>();
		Document xmlDoc = createDoc(inputPath);
		Element root = xmlDoc.getDocumentElement();
		this.root = root;
		pattern = Pattern.compile(nodeSelectionRegex);
		
		
	}

	public void parse(){
		findCores(root);
	}
	
	private void findCores(Node startNode){
		
		Node nameAttribute;
		Matcher coreAttrMatcher;
		
		if(startNode.getNodeType() == Node.ELEMENT_NODE){
			nameAttribute = startNode.getAttributes().getNamedItem("name");
			if(nameAttribute != null && (coreAttrMatcher = pattern.matcher(nameAttribute.toString())).matches()){
				currCoreName = coreAttrMatcher.group(1);
				findProcesses(startNode);
			}

			if(startNode.hasChildNodes())
				findCores(startNode.getFirstChild());
		}
		
		if(startNode.getNextSibling() != null)
			findCores(startNode.getNextSibling());
	}
	
	private void findProcesses(Node startNode){
		if(startNode.getNodeType() == Node.ELEMENT_NODE && startNode.getNodeName().equals("itemlist") && ((Element)startNode).getAttribute("type").equals("Task")){
			extractProcesses(startNode.getFirstChild());
		}
		
		if(startNode.hasChildNodes())
			findProcesses(startNode.getFirstChild());
		if(startNode.getNextSibling() != null)
			findProcesses(startNode.getNextSibling());
		
		
	}
	
	
	private void extractProcesses(Node taskNode){
		if(taskNode.getNodeType() != Node.ELEMENT_NODE || !taskNode.getNodeName().equals("item")){
			if(taskNode.getNextSibling() != null)
				extractProcesses(taskNode.getNextSibling());
		}else{
			String taskName = ((Element)taskNode).getAttribute("name");
			Node currAttribute = taskNode.getFirstChild();
			TaskInfo currTask = new TaskInfo();
			Element currElement = null;	
			
			while(currAttribute != null){
				if(currAttribute.getNodeType() == Node.ELEMENT_NODE){
					currElement = (Element)(currAttribute);
					if(currElement.getAttribute("name").equals("ID")){
						currTask.setId(Double.parseDouble(currElement.getAttribute("value")));
					}else if(currElement.getAttribute("name").equals("Priority")){
						currTask.setPriority(Double.parseDouble(currElement.getAttribute("value")));
					}
					
				}
				currAttribute = currAttribute.getNextSibling();
			}
			
			currTask.setCore(currCoreName);
			taskMap.put(taskName, currTask);
			if(taskNode.getNextSibling() != null)
				extractProcesses(taskNode.getNextSibling());
		}
	}
	
	
	private Document createDoc(String inputFilePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException{
		File inputFile = new File(inputFilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document document = builder.parse(inputFile);
		return document;
	}
	
	public HashMap<String, TaskInfo> getTaskMap(){
		return taskMap;	
	}
}



