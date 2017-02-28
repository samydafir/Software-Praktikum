package datavisualizer.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 * The Process table creates the process table used in the user interface and defines its behaviour.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class ProcessTable{

	private TableViewer viewer;
	
	/**
	 * Creates a TableViewer and configures its layout and columns. Rows are set to be selectable
	 * using a checkbox.
	 * Each column is given its own LabelProvider and a certain layout (width, height,...) configured
	 * in a way that lets each column display the required attribute from the TaskInfo object representing
	 * each task.
	 * At the bottom a label provider is set for the table view (the custom TaskViewLabelProvider) in order
	 * to make the TableViewer display the info from the TaskInfo objects in the defined way.
	 * @param parent parent Container (second row Composite) passed from ControlView at creation
	 * @param style style
	 */
	public ProcessTable(Composite parent, int style) {
		

		RowLayout layout = new RowLayout();
		layout.fill = true;
		parent.setLayout(layout);
		Composite comp = new Composite(parent, SWT.BORDER);
		comp.setLayout(layout);
        viewer = new TableViewer(comp, SWT.V_SCROLL | SWT.CHECK | SWT.MULTI | SWT.H_SCROLL | SWT.FILL);
		final Table table = viewer.getTable();
		table.setLayout(new RowLayout());
		table.setLayoutData(new RowData(500,200));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
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
		
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new TaskViewLabelProvider());
	}
	
	/**
	 * @return the created TableViewer
	 */
	public TableViewer getViewer() {
		return viewer;
	}

}