package datavisualizer.views;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;


public class ControlView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "datavisualizer.views.ControlView";

	private Model model;
	private TableViewer viewer;
	ProcessStateGraph stateGraph;


/**
	 * The constructor.
	 */
	public ControlView() throws ParserConfigurationException, SAXException, IOException {
		model = new Model("E:\\OneDrive - stud.sbg.ac.at\\University\\WS16\\Software Praktikum\\Software-Praktikum\\data\\platformModel.xml");
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialise it.
	 */
	public void createPartControl(Composite parent) {
		
		RowLayout rl = new RowLayout();
		parent.setLayout(rl);
		
		InputSelection is = new InputSelection(parent);
		
		
		ProcessTable pt = new ProcessTable(parent, 1, model);
		viewer = pt.getViewer();
		
		createButtons(parent);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "DataVisualizer.viewer");
		getSite().setSelectionProvider(viewer);
	}
	
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
		
		Button back = new Button(parent, SWT.PUSH);
		back.setLayoutData(new RowData(100,33));
		back.setText("Back");
		back.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e){
				backToInputSelection();
		      }
		});
		
	}
	
	
	public void setFocus() {
		viewer.getControl().setFocus();
	}


	public void handleGraphCreation() throws PartInitException{
		ArrayList<Double> ids = new ArrayList<>();
		
		final TableItem [] items = viewer.getTable().getItems();
	    for (int i = 0; i < items.length; ++i) {
	      if (items[i].getChecked())
	    	  System.out.println(items[i].getText());
	    }
	    
	    
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("datavisualizer.views.ProcessStateGraph");
		if(stateGraph == null && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.ProcessStateGraph") instanceof ProcessStateGraph){
			stateGraph = (ProcessStateGraph) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.ProcessStateGraph");
			stateGraph.showGraph();
		}else{
			//stateGraph.updateGraph();
		}
	}
	
	private void backToInputSelection(){
		
	}

}