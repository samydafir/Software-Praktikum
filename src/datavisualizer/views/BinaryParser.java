package datavisualizer.views;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class BinaryParser {

	public HashMap<Double, ArrayList<StateInfo>> parseBinary(String path) throws IOException{
		
		DataInputStream input = new DataInputStream(new FileInputStream(path));
		HashMap<Double, ArrayList<StateInfo>> stateMap = new HashMap<>();
		int count = 0;
		double currDouble;
		StateInfo currStateInfo = null;
		
		while(input.available() > 0){
			currDouble = Double.longBitsToDouble(Long.reverseBytes(input.readLong()));
				
			if(count % 3 == 0){
				currStateInfo = new StateInfo();
				currStateInfo.setTimestamp(currDouble);
			}else if(count % 3 == 1){
				currStateInfo.setState(currDouble);
			}else{
				if (stateMap.containsKey(currDouble)){
					stateMap.get(currDouble).add(currStateInfo);
				}else{
					ArrayList<StateInfo> states = new ArrayList<>();
					states.add(currStateInfo);
					stateMap.put(currDouble, states);
				}	
			}
			count++;
		}
		input.close();
		return stateMap;
	}
	
	
	public void printMap(HashMap<Double, ArrayList<StateInfo>> stateMap){
		for(Entry<Double, ArrayList<StateInfo>> a: stateMap.entrySet()){
			System.out.print(a.getKey() + "::: ");
			for(StateInfo b: a.getValue()){
				System.out.println(b.getTimestamp() + ":" + b.getState());
			}
			System.out.println("---NEW PROCESS---");
		}
	}
}
