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

/**
 * This class contains elements which were factored out from the ControlView class to improve structuring.
 * This class is used to create the first line in the user interface (the file selection part) and hold
 * paths to all seleted and required files. The paths are returned by the corresponding getter methods.
 * @author Samy Dafir
 * @author Sophie Reischl
 * @author Dominik Baumgartner
 */
public class InputSelection {
	
	
	private String xmlFilePath;
	private String[] binaryFiles;

	/**
	 * Constructor. Does all there is to do. Creates a text fiels and a button for both xml- and
	 * binary-file selection. The respective text field displays the file path after selection.
	 * Buttons are configured to opena new window for the file selection dialog.
	 * After selection the paths are stored in the respective instance variables.
	 * @param parent first row container (Composite) passed from ControlView at initialization.
	 */
	public InputSelection(Composite parent){

		Shell fileDialog = new Shell();
				
		Button  chooseXml = new Button(parent, SWT.PUSH);
		Text xmlFile = new Text(parent, SWT.SINGLE | SWT.BORDER);
		xmlFile.setLayoutData(new RowData(200, 26));
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
	
	/**
	 * @return path to the xml file
	 */
	public String getXmlFilePath() {
		return xmlFilePath;
	}

	/**
	 * @return paths to the binary files
	 */
	public String[] getBinaryFiles() {
		return binaryFiles;
	}
}