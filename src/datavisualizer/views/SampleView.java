package datavisualizer.views;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.IXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.ToolbarArmedXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.PointStyle;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "datavisualizer.views.SampleView";

	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Canvas rightCanvas;
	private Canvas leftCanvas;
	ToolbarArmedXYGraph toolbarArmedXYGraph;
	LightweightSystem lws;
	 

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
	public SampleView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		leftCanvas = new Canvas(parent, 1);
		leftCanvas.setLayout(new RowLayout(1));
		leftCanvas.setLayoutData(new RowData(500, 500));
		
		
		viewer = new TableViewer(leftCanvas, SWT.CHECK | SWT.H_SCROLL | SWT.V_SCROLL);
		
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(new String[] { "T1","T2","T3","T4","T5","T6","T7","T8","T9","T10","T11","T12","T13", });
		viewer.setLabelProvider(new ViewLabelProvider());

		rightCanvas = new Canvas(parent, 1);
		rightCanvas.setLayout(new RowLayout(1));
		rightCanvas.setLayoutData(new RowData(500, 500));
		
		leftCanvas.setLayout(new RowLayout(1));
		Button button = new Button(leftCanvas, SWT.PUSH);
		button.setText("Press Me");
		button.setLayoutData(new RowData(70, 40));
		button.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		        removeGraph();
		        System.out.println("Btton pressed");
		      }
		    });
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "DataVisualizer.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
				test();
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				test();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Sample View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}


	public void removeGraph(){
		test();
	}
	
	public void test() {
		
		// use LightweightSystem to create the bridge between SWT and draw2D
		lws = new LightweightSystem(rightCanvas);

		// create a new XY Graph.
		IXYGraph xyGraph = new XYGraph();
		xyGraph.setTitle("Task States");
		xyGraph.getPrimaryXAxis().setRange(0, 15);
		xyGraph.getPrimaryYAxis().setRange(0, 10);
		xyGraph.getPrimaryXAxis().setTitle("time");
		xyGraph.getPrimaryYAxis().setTitle("State");
		
		
		toolbarArmedXYGraph = new ToolbarArmedXYGraph(xyGraph);;
		
		// set it as the content of LightwightSystem
		ToolbarArmedXYGraph toolbarArmedXYGraph = new ToolbarArmedXYGraph(xyGraph);

		// set it as the content of LightwightSystem
		lws.setContents(toolbarArmedXYGraph);
		
		// create a trace data provider, which will provide the data to the
		// trace.
		CircularBufferDataProvider traceDataProvider = new CircularBufferDataProvider(false);
		traceDataProvider.setBufferSize(100);
		traceDataProvider.setCurrentXDataArray(new double[] {0,1,2,3,3,4,5,6,7,7,8,9,10});
		traceDataProvider.setCurrentYDataArray(new double[] {1,1,1,1,2,2,2,2,2,3,3,3,3 });
		
		CircularBufferDataProvider traceDataProvider1 = new CircularBufferDataProvider(false);
		traceDataProvider1.setBufferSize(100);
		traceDataProvider1.setCurrentXDataArray(new double[] {0,1,2,3,3,4,5,6,7,7,8,9,10});
		traceDataProvider1.setCurrentYDataArray(new double[] {5,5,5,5,7,7,7,7,7,6,6,6,6 });
		
		// create the trace
		Trace trace = new Trace("Task1", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);
		Trace trace1 = new Trace("Task2", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider1);

		// set trace property
		trace.setPointStyle(PointStyle.NONE);
		trace1.setPointStyle(PointStyle.NONE);

		// add the trace to xyGraph
		xyGraph.addTrace(trace);
		xyGraph.addTrace(trace1);

		Display display = Display.getDefault();
		while (!rightCanvas.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

	}

}



