package datavisualizer.views;

import java.io.IOException;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Demo {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		XmlParser x = new XmlParser("data/platformModel.xml", ".*(Core\\_c[0-9]*).*");
		x.parse();
		for(Entry<String, TaskInfo> a: x.getTaskMap().entrySet()){
			System.out.println(a.getKey() + " -> " + a.getValue().toString());
		}
		
	}

}