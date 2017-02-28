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

	/**
	 * This function assumes that the binary file is contains tuples of the form (timeStamp, value, id).
	 * To extract all the info for the selected processes the whole binary file is scanned. Each tuple is
	 * then represented by a StateInfo object which is created after every third double is read. Info
	 * for each tuple is read and since the id is the last entry in every tuple, a check is performed at the
	 * end as to whether the id was even selected or not (fast since we look for Double in a Set<Double>).
	 * If it is part of the selected id the stateInfo object is inserted into the state´Map containing a 
	 * mapping of every process id to all its states. If the found id was not selected the object is discarded
	 * (not inserted into the map).
	 * Concerning reading double values: The values in the binary files have most likely been created with
	 * a C based program. C and java do not share the same byte layout regarding 64 bit types. To take care
	 * of this difference double values are read as long values, bytes are then reversed and finally the new
	 * long value is converted to double to obtain the real double value.
	 * @param paths paths to all binary files to parse
	 * @param selectedIds Ids od processes selected from the xml File
	 */
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
	
	
	/**
	 * prints a String representation of the stateMap
	 */
	public void printMap(){
		for(Entry<Double, ArrayList<StateInfo>> a: stateMap.entrySet()){
			System.out.print(a.getKey() + "::: ");
			for(StateInfo b: a.getValue()){
				System.out.println(b.getTimestamp() + ":" + b.getState());
			}
			System.out.println("---NEW PROCESS---");
		}
	}
	
	/**
	 * @return the stateMap (mapping of process ids to all that process's states)
	 */
	public HashMap<Double, ArrayList<StateInfo>> getStateMap() {
		return stateMap;
	}
}