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
import org.eclipse.draw2d.geometry.Point;



public class ProcessStateGraph extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "datavisualizer.views.ProcessStateGraph";

	private Model model;
	private Canvas mainCanvas;
	private ToolbarArmedXYGraph toolbarArmedXYGraph;
	private LightweightSystem lws;
	 


	public void createPartControl(Composite parent) {
		
		mainCanvas = new Canvas(parent, 1);
		mainCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL));
	}
	

	public void setFocus() {

	}

	
	public void showGraph() {
		
		lws = new LightweightSystem(mainCanvas);
		
		// create a new XY Graph.
		IXYGraph xyGraph = new XYGraph();
		xyGraph.setTitle("Task States");
		xyGraph.getPrimaryXAxis().setRange(0, 0.25);
		xyGraph.getPrimaryYAxis().setRange(0, 5);
		xyGraph.getPrimaryXAxis().setTitle("time");
		xyGraph.getPrimaryYAxis().setTitle("State");
		
		
		toolbarArmedXYGraph = new ToolbarArmedXYGraph(xyGraph);

		// set it as the content of LightwightSystem
		lws.setContents(toolbarArmedXYGraph);
/*	
		// create a trace data provider, which will provide the data to the
		// trace.
		CircularBufferDataProvider traceDataProvider = new CircularBufferDataProvider(false);
		traceDataProvider.setBufferSize(100);
		traceDataProvider.setCurrentXDataArray(new double[] {0,1,2,3,3,4,5,6,7,7,8,9,10});
		traceDataProvider.setCurrentYDataArray(new double[] {1,1,1,1,2,2,2,2,2,3,3,3,3 });
		
		// create the trace
		Trace trace = new Trace("Task1", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);

		trace.setLocation(new Point(0,20));
		
		// set trace property
		trace.setPointStyle(PointStyle.NONE);

		// add the trace to xyGraph
		xyGraph.addTrace(trace);
*/		
		CircularBufferDataProvider traceDataProvider;
		Trace trace;
		
		for(TraceInfo currTraceData: model.traceInfo){
			double[][] dataPoints = currTraceData.getTrace();
			
			// create a trace data provider, which will provide the data to the trace.
			traceDataProvider = new CircularBufferDataProvider(false);
			traceDataProvider.setBufferSize(100);
			traceDataProvider.setCurrentXDataArray(dataPoints[0]);
			traceDataProvider.setCurrentYDataArray(dataPoints[1]);
			
			// create the trace
			trace = new Trace(currTraceData.getName(), xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);

			trace.setAntiAliasing(true);
			
			// set trace property
			trace.setPointStyle(PointStyle.NONE);

			// add the trace to xyGraph
			xyGraph.addTrace(trace);
		}

		Display display = Display.getDefault();
		while (!mainCanvas.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

	}
	
	public void setModel(Model model) {
		this.model = model;
	}
}



