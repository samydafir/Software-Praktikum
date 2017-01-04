package datavisualizer.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TaskViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

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
