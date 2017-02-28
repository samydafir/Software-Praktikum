package datavisualizer.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 *  Label provider required for the table viewer used in the user interface (ControlView) in
 *  order for information to be displayed correctly.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class TaskViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/**
	 * Controls which column of our tableViewer contains which information of each task
	 * Each column is assigned a certain attribute of our element (which is a TaskInfo object).
	 * @param element TaskInfo object containing the information of the current task to be
	 * inserted into the table.
	 * @param columnIndex column to be inserted into
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch(columnIndex){
		case 0:
			return ((TaskInfo)element).getName();
		case 1:
			return ((TaskInfo)element).getCore();
		case 2:
			return ((TaskInfo)element).getId() + "";
		case 3:
			return ((TaskInfo)element).getPriority() + "";
		}
		return null;
	}
}