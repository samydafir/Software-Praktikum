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
	
	public XmlParser(String inputPath, String nodeSelectionRegex) throws ParserConfigurationException, SAXException, IOException{
		Document xmlDoc = createDoc(inputPath);
		Element root = xmlDoc.getDocumentElement();
		this.root = root;
		pattern = Pattern.compile(nodeSelectionRegex);
		
		
	}

	public void parse(){
		parseRec(root);
	}
	
	private void parseRec(Node startNode){
		
		Node nameAttribute;
		Matcher coreAttrMatcher;
		
		if(startNode.getNodeType() == Node.ELEMENT_NODE){
			nameAttribute = startNode.getAttributes().getNamedItem("name");
			if(nameAttribute != null && (coreAttrMatcher = pattern.matcher(nameAttribute.toString())).matches()){
				System.out.println(coreAttrMatcher.group(1));
				System.out.println("FOUND " + startNode.getAttributes().getNamedItem("name"));				
				extractProcesses(startNode, coreAttrMatcher.group(1));
			}

			if(startNode.hasChildNodes())
				parseRec(startNode.getFirstChild());
		}
		
		if(startNode.getNextSibling() != null)
			parseRec(startNode.getNextSibling());
	}
	
	private void extractProcesses(Node parent, String coreName){
		
	
	}
	
	
	private Document createDoc(String inputFilePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException{
		File inputFile = new File(inputFilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document document = builder.parse(inputFile);
		return document;
	}
}
