package datavisualizer.views;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Parses a binary file, extracts states and stores a mapping of task-id to a list of (time, state)-tuples.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class BinaryParser {

	private HashMap<Double, ArrayList<StateInfo>> stateMap = new HashMap<>();

	public void parseBinary(String[] paths, Set<Double> selectedIds) throws IOException{
		
		int count;
		double currDouble;
		StateInfo currStateInfo = null;
		DataInputStream input;
		
		for(String currFile: paths){
			input = new DataInputStream(new FileInputStream(currFile));
			
			count = 0;
		
			while(input.available() > 0){
				currDouble = Double.longBitsToDouble(Long.reverseBytes(input.readLong()));
				
				if(count % 3 == 0){
					currStateInfo = new StateInfo();
					currStateInfo.setTimestamp(currDouble);
				}else if(count % 3 == 1){
					currStateInfo.setState(currDouble);
				}else{
					if(selectedIds.contains(currDouble)){
						if (stateMap.containsKey(currDouble)){
							stateMap.get(currDouble).add(currStateInfo);
						}else{
							ArrayList<StateInfo> states = new ArrayList<>();
							states.add(currStateInfo);
							stateMap.put(currDouble, states);
						}
					}
				}
				count++;
			}
		input.close();
		}
	}
	
	
	public void printMap(){
		for(Entry<Double, ArrayList<StateInfo>> a: stateMap.entrySet()){
			System.out.print(a.getKey() + "::: ");
			for(StateInfo b: a.getValue()){
				System.out.println(b.getTimestamp() + ":" + b.getState());
			}
			System.out.println("---NEW PROCESS---");
		}
	}
	
	public HashMap<Double, ArrayList<StateInfo>> getStateMap() {
		return stateMap;
	}
}