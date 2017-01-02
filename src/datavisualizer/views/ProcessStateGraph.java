package datavisualizer.views;


import org.eclipse.swt.SWT;
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
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import java.util.ArrayList;

import org.eclipse.draw2d.LightweightSystem;



public class ProcessStateGraph extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "datavisualizer.views.Test";

	private ArrayList<Double[]> dataPoints;
	private Canvas mainCanvas;
	ToolbarArmedXYGraph toolbarArmedXYGraph;
	LightweightSystem lws;
	 

/**
	 * The constructor.
	 */
	public ProcessStateGraph() {
		dataPoints = new ArrayList<>();
		
	}


	public void createPartControl(Composite parent) {
		
		mainCanvas = new Canvas(parent, 1);
		mainCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL));
	}
	

	public void setFocus() {

	}

	
	public void showGraph() {
		
		// use LightweightSystem to create the bridge between SWT and draw2D
		lws = new LightweightSystem(mainCanvas);

		
		// create a new XY Graph.
		IXYGraph xyGraph = new XYGraph();
		xyGraph.setTitle("Task States");
		xyGraph.getPrimaryXAxis().setRange(0, 15);
		xyGraph.getPrimaryYAxis().setRange(0, 10);
		xyGraph.getPrimaryXAxis().setTitle("time");
		xyGraph.getPrimaryYAxis().setTitle("State");
		
		
		toolbarArmedXYGraph = new ToolbarArmedXYGraph(xyGraph);

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
		while (!mainCanvas.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

	}
	
	public void addTrace(double[] xCoords, double[] yCoords){
		//TODO
		
	}

}



