package datavisualizer.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InputSelection {
	
	public InputSelection(Composite parent){

		Shell fileDialog = new Shell();
		RowLayout rl = new RowLayout();
		rl.center = true;
		
		//parent.setLayout(rl);
				
		Text xmlFile = new Text(parent, SWT.SINGLE | SWT.BORDER);
		xmlFile.setLayoutData(new RowData(200, 30));
		xmlFile.setLocation(0, 50);
		Button  chooseXml = new Button(parent, SWT.PUSH);
		chooseXml.setText("Choose XML");
		chooseXml.setLayoutData(new RowData(100, 33));
		chooseXml.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e){
		    	  FileDialog dialog = new FileDialog(fileDialog, SWT.OPEN);
		    	   dialog.setFilterExtensions(new String [] {"*.*"});
		    	   xmlFile.setText(dialog.open());
		      }
		});
		Text binaryFile = new Text(parent, SWT.SINGLE | SWT.BORDER);
		binaryFile.setLayoutData(new RowData(200, 30));
		Button  chooseBinary = new Button(parent, SWT.PUSH);
		chooseBinary.setText("Choose Binary");
		chooseBinary.setLayoutData(new RowData(100, 33));
		chooseBinary.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e){
		    	  FileDialog dialog = new FileDialog(fileDialog, SWT.OPEN);
		    	   dialog.setFilterExtensions(new String [] {"*.*"});
		    	   binaryFile.setText(dialog.open());
		      }
		});
		
	}

}
