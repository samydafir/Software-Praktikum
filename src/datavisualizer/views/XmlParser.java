package datavisualizer.views;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {
	
	private Node root;
	Pattern pattern;
	
	public XmlParser(String inputPath, String nodeSelectionSpecifier) throws ParserConfigurationException, SAXException, IOException{
		Document xmlDoc = createDoc(inputPath);
		Element root = xmlDoc.getDocumentElement();
		this.root = root;
		pattern = Pattern.compile("^Core\\_.*$");
		
		
	}

	public void parse(Node startNode){
		
		if(startNode == null){
			return;
		}
		
		Element element = (Element) startNode;
		Matcher coreNodeMatcher = pattern.matcher(element.getAttribute("name"));
		if(coreNodeMatcher.matches()){
			extractProcesses(startNode);
		}else if(startNode.getChildNodes().getLength() > 0 ){
			Node firstChild = startNode.getFirstChild();
			parse(firstChild);
			parse(firstChild.getNextSibling());
		}
	}
	
	private void extractProcesses(Node parent){
		//TODO: extract process nodes and info
	}
	
	
	private Document createDoc(String inputFilePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException{
		File inputFile = new File(inputFilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document document = builder.parse(inputFile);
		return document;
	}
}
