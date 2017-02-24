package datavisualizer.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.*;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.IXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.ToolbarArmedXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.PointStyle;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.TraceType;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.draw2d.LightweightSystem;


/**
 * This class represents a view containing the State Graph. This is not a
 * stand-alone class but used to display the states selected in the ControlView.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class ProcessStateGraph extends ViewPart {

	
	public static final String ID = "datavisualizer.views.ProcessStateGraph";
	private Model model;
	private Canvas mainCanvas;
	private ToolbarArmedXYGraph toolbarArmedXYGraph;
	private LightweightSystem lws;	 


	/**
	 * Called when the view is created.
	 * Create and layout a Canvas as a container for the nebula 
	 * xyGraph to be created later.
	 */
	public void createPartControl(Composite parent) {
		
		mainCanvas = new Canvas(parent, 1);
		mainCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL));
	}
	
	/**
	 * Method is required for viewPart Extension but does not do anything.
	 * Called when the view is opened. In our case all configuration is done
	 * in createPArtControl and showGraph.
	 */
	public void setFocus() {
	}

	
	/**
	 * This method creates or refreshes the state graph
	 * It is called whenever a graph is created or its content changed, but only if there is actually any process state info to plot.
	 * A LightweightSystem is created as a connection between the eclipse view and nebula. Then a new xyGraph is created, configured
	 * and added to the LightweightSystem.
	 * The displayed data is taken from a TreeSet in the corresponding model object. Traces are intrinsically sorted by core and priority.
	 * If a core-change is detected a separator trace is added clearly separating traces belonging to different cores.
	 * Each trace is then added to the xyGraph
	 */
	public void showGraph() {
		
		if(model.traceInfo.size() < 1)
			return;
		
		lws = new LightweightSystem(mainCanvas);
		
		IXYGraph xyGraph = new XYGraph();
		xyGraph.setTitle("Task States");
		xyGraph.getPrimaryXAxis().setAutoScale(true);
		xyGraph.getPrimaryYAxis().setAutoScale(true);
		xyGraph.getPrimaryXAxis().setTitle("Time");
		xyGraph.getPrimaryYAxis().setTitle("State");
		xyGraph.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryXAxis().setShowMinorGrid(true);
		xyGraph.getPrimaryXAxis().setShowMinorGrid(true);
		
		toolbarArmedXYGraph = new ToolbarArmedXYGraph(xyGraph);

		lws.setContents(toolbarArmedXYGraph);
		
		CircularBufferDataProvider traceDataProvider;
		Trace trace;
		int count = 0;
		String currCore = model.traceInfo.first().getCore();
		String coreSeparator;
		
		for(TraceInfo currTraceData: model.traceInfo){	
			if(!currCore.equals(currTraceData.getCore())){
				traceDataProvider = new CircularBufferDataProvider(false);
				traceDataProvider.setBufferSize(4);
				traceDataProvider.setCurrentXDataArray(new double[] {0,2});
				traceDataProvider.setCurrentYDataArray(new double[] {(count) * 5 - 0.5, (count) * 5 - 0.5});
				coreSeparator = currCore + " | " + currTraceData.getCore();
				trace = new Trace(coreSeparator, xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);
				trace.setTraceColor(new Color(null, 0, 0, 0));
				trace.setAntiAliasing(true);
				trace.setPointStyle(PointStyle.NONE);
				trace.setLineWidth(3);
				trace.setTraceType(TraceType.DASH_LINE);
				xyGraph.addTrace(trace);
			}
			
			currCore = currTraceData.getCore();
			double[][] dataPoints = currTraceData.getTrace();
			for(int i = 0; i < dataPoints[1].length; i++){
				dataPoints[1][i] = dataPoints[1][i] + 5 * count;
			}

			traceDataProvider = new CircularBufferDataProvider(false);
			traceDataProvider.setBufferSize(dataPoints[0].length + dataPoints[1].length);
			traceDataProvider.setCurrentXDataArray(dataPoints[0]);
			traceDataProvider.setCurrentYDataArray(dataPoints[1]);
			trace = new Trace(currTraceData.getName(), xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);
			trace.setAntiAliasing(true);
			trace.setPointStyle(PointStyle.NONE);
			xyGraph.addTrace(trace);
			
			count++;
		}
		
		xyGraph.getPrimaryYAxis().setRange(0, 5 * (count + 1));

		Display display = Display.getDefault();
		while (!mainCanvas.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

	}
	
	/**
	 * Sets a model. Used to assign a model to this view
	 * @param model model to be assigned
	 */
	public void setModel(Model model) {
		this.model = model;
	}
}