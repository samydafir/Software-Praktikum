package datavisualizer.views;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to parse XML Files and extract the information for each process of each CPU core.
 * All information is then stored in a HashMap mapping the taskname to all other information about the task.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class XmlParser {
	
	private Pattern pattern;
	private String currCoreName;
	private HashMap<String, TaskInfo> taskMap;
	
	/**
	 * Constructor. Initiates the HashMap.
	 */
	public XmlParser(){
		taskMap = new HashMap<>();
		
	}

	/**
	 * XML parsing entry point. Compiles a pattern to identify nodes identifying a core and calls
	 * createDoc to create the document object required for to traverse the XML file.
	 * @param inputPath path to the xml file
	 * @param nodeSelectionRegex regex to identify core nodes. 
	 */
	public void parse(String inputPath, String nodeSelectionRegex) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException{
		pattern = Pattern.compile(nodeSelectionRegex);
		Document xmlDoc = createDoc(inputPath);
		Element root = xmlDoc.getDocumentElement();
		findCores(root);
	}
	
	/**
	 * Recursive function traverses the xml "tree" to find all processor core nodes. For this purpose
	 * all children and siblings of the current node are scanned recursively. For each found core
	 * findProcesses is called to extract all process information.
	 * @param startNode node from which the traversal starts (root node in the beginning)
	 */
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
	
	/**
	 * Recursively scans the xml "tree" until it finds the itemlist containing all processes. For this
	 * purpose all nodes starting at the startNode are scanned by scanning children and siblings. Once
	 * a node is found extractProcess is called to get all the process info.
	 * @param startNode node where scanning starts.
	 */
	private void findProcesses(Node startNode){
		if(startNode.getNodeType() == Node.ELEMENT_NODE && startNode.getNodeName().equals("itemlist") && ((Element)startNode).getAttribute("type").equals("Task")){
			extractProcesses(startNode.getFirstChild());
		}
		
		if(startNode.hasChildNodes())
			findProcesses(startNode.getFirstChild());
		if(startNode.getNextSibling() != null)
			findProcesses(startNode.getNextSibling());
		
		
	}
	
	
	/**
	 * Is called when the itemlist containing the processes is found. Starts at the first node in that
	 * itemlist and traverses all siblings, extracts all info (Core, Name, ID, priority) and inserts an
	 * entry mapping the taskname to all that info into the taskMAp (HashMap).
	 * @param taskNode node found to contain info related to a process
	 */
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
			currTask.setName(taskName);
			taskMap.put(taskName, currTask);
			if(taskNode.getNextSibling() != null)
				extractProcesses(taskNode.getNextSibling());
		}
	}
	
	/**
	 * Creates the initial setup for parsing the xml with DOM which requires a Document-object of the
	 * XML file.
	 * @param inputFilePath path to the xml file to parse
	 * @return document object created from the xml file
	 */
	private Document createDoc(String inputFilePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException{
		File inputFile = new File(inputFilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document document = builder.parse(inputFile);
		return document;
	}
	
	/**
	 * @return HashMap with tasknames mapped to the info of each task.
	 */
	public HashMap<String, TaskInfo> getTaskMap(){
		return taskMap;	
	}
}