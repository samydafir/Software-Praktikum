package datavisualizer.views;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowData;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;


public class ControlView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "datavisualizer.views.SampleView";

	private TableViewer viewer;
	ProcessStateGraph test;

	 

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

/**
	 * The constructor.
	 */
	public ControlView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialise it.
	 */
	public void createPartControl(Composite parent) {
		
		//parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL));
		
		
		viewer = new TableViewer(parent, SWT.CHECK | SWT.H_SCROLL | SWT.V_SCROLL);
		
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(new String[] { "First Process to display","T1","T2","T3","T4","T5","T6","T7","T8","T9",
				"T10","T11","T12","T13","T14","T15" });
		viewer.setLabelProvider(new ViewLabelProvider());
		
		
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new RowData(100,33));
		button.setText("Display");
		button.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		        try {
					button.setText("Refresh");
					showGraph();
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


	public void showGraph() throws PartInitException{
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("datavisualizer.views.Test");
		if(test == null && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.Test") instanceof ProcessStateGraph){
			test = (ProcessStateGraph) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("datavisualizer.views.Test");
			test.showGraph();
		}else{
			//test.updateGraph();
		}
	}
	
}



