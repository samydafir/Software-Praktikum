package datavisualizer.views;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * Represents the main view of this plugin. Used to select traces to later be displayed in the state graph.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class ControlView extends ViewPart {

	public static final String ID = "datavisualizer.views.ControlView";
	private Model model;
	private TableViewer viewer;
	private ProcessStateGraph stateGraph;
	private InputSelection is;
	private ProcessTable pt;

	/**
	 * Constructor just creates a new Model instance
	 */
	public ControlView() throws ParserConfigurationException, SAXException, IOException {
		model = new Model();
	}

	/**
	 * Called when the view is opened. Creates all elements of the user interface such as buttons, text fields,...
	 * Also creates elements outsourced to other classes.
	 * Elements are inserted into composite elemts whicha re then vertically aligned.
	 * 
	 */
	public void createPartControl(Composite parent) {
		
		RowLayout rl = new RowLayout(SWT.VERTICAL);
		parent.setLayout(rl);
		
		Composite titleRow = new Composite(parent, SWT.NONE);
		titleRow.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label title = new Label(titleRow, SWT.SINGLE | SWT.BOLD);
		title.setText("Select your files and press Start");
		Font boldFont = new Font( title.getDisplay(), new FontData( "Arial", 14, SWT.BOLD ) );
		title.setFont( boldFont );
		
		Composite firstRow = new Composite(parent, SWT.NONE | SWT.BORDER);
		firstRow.setLayout(new RowLayout(SWT.HORIZONTAL));
		is = new InputSelection(firstRow);
		Button start = new Button(firstRow, SWT.PUSH);
		start.setLayoutData(new RowData(100,33));
		start.setText("Start");
		start.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e){
				model.parseXml(is.getXmlFilePath());
		  		viewer.setInput(model.getTaskInfo());
		      }
		});
		
		Composite secondRow = new Composite(parent, SWT.NONE | SWT.BORDER);
		secondRow.setLayout(new RowLayout(SWT.HORIZONTAL));
    	pt = new ProcessTable(secondRow, 1);
  		viewer = pt.getViewer();
		
		createButtons(secondRow);		
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "DataVisualizer.viewer");
		getSite().setSelectionProvider(viewer);
	}
	
	/**
	 * Creates the "start" button which changes into a "refresh" button when pressed once.
	 * Called by createPartControl. This button initiates the graph creation
	 * @param parent
	 */
	public void createButtons(Composite parent){
		
		Button display = new Button(parent, SWT.PUSH);
		display.setLayoutData(new RowData(100,33));
		display.setText("Display");
		display.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		        try {
					display.setText("Refresh");
					handleGraphCreation();
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
		      }
		  });
	}
	
	
	/**
	 * Called when view is opened. Sets focus to this view.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/**
	 * Called when the start/refresh button is pressed. Handles the creation of the state-graph.
	 * - Creates a list of all selected ids and create an object for each trace. Collect traces
	 *   in TreeSet.
	 * - The binary files are parsed using a BinaryParser object and states and times for processes
	 *   with the selected ids are extracted and stored in a Map in the model. If there are no files
	 *   this method returns and does nothing -> No graph will be created.
	 * - Data for the traces (state, time) is then extracted from the StateMap in the Model and for each
	 *   Process all states are added to the respective TraceInfo objects in the TreeMap.
	 * - Based on the current state of the graph (not yet created, or already there) graph creation is
	 * 	 triggered. If the graph only needs to be refreshed, the old graph-view is first removed and a 
	 *   new one is opened.
	 */
	public void handleGraphCreation() throws PartInitException{
		HashSet<Double> selectedIds = new HashSet<>();
		TraceInfo currTrace;
		HashMap<Double, ArrayList<StateInfo>> stateMap;
		TaskInfo currTask;
		
		model.traceInfo = new TreeSet<>();
		final TableItem [] items = viewer.getTable().getItems();
	    for (int i = 0; i < items.length; ++i) {
	      if (items[i].getChecked()){
	    	  currTask = model.getTaskMap().get(items[i].getText());
	    	  selectedIds.add(currTask.getId());
	    	  currTrace = new TraceInfo();
	    	  currTrace.setId(currTask.getId());
	    	  currTrace.setCore(currTask.getCore());
	    	  currTrace.setName(currTask.getName());
	    	  currTrace.setPriority(currTask.getPriority());
	    	  model.traceInfo.add(currTrace);
	      }
	    }
	    
	    if(is.getBinaryFiles() == null || is.getBinaryFiles().length < 1)
	    	return;
	    
	    model.parseBinaries(is.getBinaryFiles(), selectedIds);
	    stateMap = model.getStateMap();

	    
	    TreeSet<TraceInfo> tempSet = new TreeSet<>();
	    for(TraceInfo currInfo: model.traceInfo){
	    	if(stateMap.containsKey(currInfo.getId())){
	    		currInfo.setStates(stateMap.get(currInfo.getId()));
	    		tempSet.add(currInfo);
	    	}
	    }
	    model.traceInfo = tempSet;
	    
		if(stateGraph == null && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.ProcessStateGraph") instanceof ProcessStateGraph){
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("datavisualizer.views.ProcessStateGraph");
			stateGraph = (ProcessStateGraph) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.ProcessStateGraph");
			stateGraph.setModel(model);
			stateGraph.showGraph();
		}else{
			IWorkbenchPage wp=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IViewPart myView=wp.findView("datavisualizer.views.ProcessStateGraph");
			wp.hideView(myView);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("datavisualizer.views.ProcessStateGraph");
			stateGraph = (ProcessStateGraph) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.ProcessStateGraph");
			stateGraph.setModel(model);
			stateGraph.showGraph();
		}
	}
}