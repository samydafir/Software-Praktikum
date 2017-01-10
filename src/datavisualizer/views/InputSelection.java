package datavisualizer.views;

import java.io.File;
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
	
	private String xmlFilePath;
	private String[] binaryFiles;

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
		    	   String tempLabel = dialog.open();
		    	   xmlFile.setText(tempLabel);
		    	   xmlFilePath = tempLabel;
		    	   
		      }
		});
		Text binaryFile = new Text(parent, SWT.WRAP | SWT.BORDER);
		binaryFile.setLayoutData(new RowData(200, 30));
		Button  chooseBinary = new Button(parent, SWT.PUSH);
		chooseBinary.setText("Choose Binary");
		chooseBinary.setLayoutData(new RowData(100, 33));
		chooseBinary.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e){
		    	  FileDialog dialog = new FileDialog(fileDialog, SWT.MULTI);
		    	   dialog.setFilterExtensions(new String [] {"*.*"});
		    	   String firstFile = dialog.open();
		    	   if(firstFile != null){
		    		   binaryFiles = dialog.getFileNames();
		    	   }
		    	   for(int i = 0; i < binaryFiles.length; i++){
		    		   binaryFiles[i] = dialog.getFilterPath() + "" + File.separator + "" + binaryFiles[i];
		    		   binaryFile.setText(binaryFile.getText() + " \n" + binaryFiles[i]);
		    	   }	    
		      }
		});
		
	}
	
	public String getXmlFilePath() {
		return xmlFilePath;
	}

	public String[] getBinaryFiles() {
		return binaryFiles;
	}

}
