package datavisualizer.views;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.*;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.layout.RowData;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;


public class ControlView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "datavisualizer.views.SampleView";

	private Model model;
	private TableViewer viewer;
	ProcessStateGraph stateGraph;


/**
	 * The constructor.
	 */
	public ControlView() throws ParserConfigurationException, SAXException, IOException {
		model = new Model("");
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialise it.
	 */
	public void createPartControl(Composite parent) {
		
		//parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL));
		
		createTable(parent);
		
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new RowData(100,33));
		button.setText("Display");
		button.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		        try {
					button.setText("Refresh");
					handleGraphCreation();
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
		      }
		    });
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "DataVisualizer.viewer");
		getSite().setSelectionProvider(viewer);
	}
	
	
	public void setFocus() {
		viewer.getControl().setFocus();
	}


	public void handleGraphCreation() throws PartInitException{
		
		//TODO:getinputData + layout input data
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("datavisualizer.views.stateGraph");
		if(stateGraph == null && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.stateGraph") instanceof ProcessStateGraph){
			stateGraph = (ProcessStateGraph) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.stateGraph");
			stateGraph.showGraph();
		}else{
			//stateGraph.updateGraph();
		}
	}
	
	private void createTable(Composite parent){
		
		viewer = new TableViewer(parent, SWT.CHECK | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		
		
		TableViewerColumn c1 = new TableViewerColumn(viewer, SWT.NONE, 0);
		c1.getColumn().setWidth(200);
		c1.getColumn().setText("Task Name");
		c1.setLabelProvider(new ColumnLabelProvider() {
		        @Override
		        public String getText(Object element) {
		                return ((TaskInfo)element).getName();
		        }
		});
		
		c1 = new TableViewerColumn(viewer, SWT.NONE, 1);
		c1.getColumn().setWidth(70);
		c1.getColumn().setText("Core");
		c1.setLabelProvider(new ColumnLabelProvider() {
		        @Override
		        public String getText(Object element) {
	        		TaskInfo taskInfo = (TaskInfo)element;
	                return taskInfo.getCore();
		        }
		});
		
		c1 = new TableViewerColumn(viewer, SWT.NONE, 2);
		c1.getColumn().setWidth(50);
		c1.getColumn().setText("ID");
		c1.setLabelProvider(new ColumnLabelProvider() {
		        @Override
		        public String getText(Object element) {
	        		TaskInfo taskInfo = (TaskInfo)element;
	                return taskInfo.getId() + "";
		        }
		});
		
		c1 = new TableViewerColumn(viewer, SWT.NONE, 3);
		c1.getColumn().setWidth(50);
		c1.getColumn().setText("Priority");
		c1.setLabelProvider(new ColumnLabelProvider() {
		        @Override
		        public String getText(Object element) {
	        		TaskInfo taskInfo = (TaskInfo)element;
	                return taskInfo.getPriority() + "";
		        }
		});
		
		viewer.setInput(model.getTaskInfo());
		viewer.setLabelProvider(new TaskViewLabelProvider());
	
	}

}



