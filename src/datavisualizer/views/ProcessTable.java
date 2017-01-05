package datavisualizer.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class ProcessTable{

	private TableViewer viewer;
	
	public ProcessTable(Composite parent, int style, Model model) {
		

		parent.setLayout(new RowLayout());
		Composite comp = new Composite(parent, SWT.BORDER);
		comp.setLayout(new FillLayout());
        viewer = new TableViewer(comp, SWT.V_SCROLL | SWT.CHECK | SWT.MULTI | SWT.H_SCROLL | SWT.FILL);
		final Table table = viewer.getTable();
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
		viewer.setInput(model.getTaskInfo());
		viewer.setLabelProvider(new TaskViewLabelProvider());
	
	}
	
	public TableViewer getViewer() {
		return viewer;
	}

}




