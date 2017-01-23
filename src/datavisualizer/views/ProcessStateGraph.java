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
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;

import org.eclipse.draw2d.LightweightSystem;



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
		xyGraph.getPrimaryXAxis().setAutoScale(true);
		xyGraph.getPrimaryYAxis().setAutoScale(true);
		xyGraph.getPrimaryXAxis().setTitle("time");
		xyGraph.getPrimaryYAxis().setTitle("State");
		xyGraph.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryXAxis().setShowMinorGrid(true);
		xyGraph.getPrimaryXAxis().setShowMinorGrid(true);
		
		
		toolbarArmedXYGraph = new ToolbarArmedXYGraph(xyGraph);

		// set it as the content of LightwightSystem
		lws.setContents(toolbarArmedXYGraph);
		
		CircularBufferDataProvider traceDataProvider;
		Trace trace;
		int count = 0;
		String currCore = model.traceInfo.first().getCore();
		
		for(TraceInfo currTraceData: model.traceInfo){
			
			if(!currCore.equals(currTraceData.getCore())){
				traceDataProvider = new CircularBufferDataProvider(false);
				traceDataProvider.setBufferSize(4);
				traceDataProvider.setCurrentXDataArray(new double[] {0,2});
				traceDataProvider.setCurrentYDataArray(new double[] {(count) * 5 - 0.5, (count) * 5 - 0.5});
				trace = new Trace("", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);
				trace.setTraceColor(new Color(null, 0, 0, 0));
				trace.setAntiAliasing(true);
				trace.setPointStyle(PointStyle.NONE);
				trace.setLineWidth(2);
				xyGraph.addTrace(trace);
			}
			
			currCore = currTraceData.getCore();
			double[][] dataPoints = currTraceData.getTrace();
			for(int i = 0; i < dataPoints[1].length; i++){
				dataPoints[1][i] = dataPoints[1][i] + 5 * count;
			}

			// create a trace data provider, which will provide the data to the trace.
			traceDataProvider = new CircularBufferDataProvider(false);
			traceDataProvider.setBufferSize(dataPoints[0].length + dataPoints[1].length);
			traceDataProvider.setCurrentXDataArray(dataPoints[0]);
			traceDataProvider.setCurrentYDataArray(dataPoints[1]);
			
			// create the trace
			trace = new Trace(currTraceData.getName(), xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);

			trace.setAntiAliasing(true);
			
			// set trace property
			trace.setPointStyle(PointStyle.NONE);
			
			// add the trace to xyGraph
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
	
	public void setModel(Model model) {
		this.model = model;
	}
}