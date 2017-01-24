package datavisualizer.views;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
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
				
		Button  chooseXml = new Button(parent, SWT.PUSH);
		Text xmlFile = new Text(parent, SWT.SINGLE | SWT.BORDER);
		xmlFile.setLayoutData(new RowData(200, 26));
		chooseXml.setText("Choose XML");
		//chooseXml.setLocation(200,0);
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
		Button  chooseBinary = new Button(parent, SWT.PUSH);
		Text binaryFile = new Text(parent, SWT.MULTI | SWT.BORDER);
		binaryFile.setLayoutData(new RowData(200, 26));
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
		    		   if(i < binaryFiles.length - 1)
		    			   binaryFile.append(binaryFiles[i] + "\n");
		    		   else
		    			   binaryFile.append(binaryFiles[i]);
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
