package graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

import bonita.Collect;

public class FileChooserActionListener implements ActionListener {
	JFileChooser fc;
	Collection mother;
	Collect tsk;
	public FileChooserActionListener(JFileChooser fc,Collection mother, Collect tsk){
		this.fc = fc;
		this.mother = mother;
		this.tsk = tsk;
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 //Handle open button action.
	   
	      int returnVal = fc.showOpenDialog(mother);

	      if (returnVal == JFileChooser.APPROVE_OPTION) {
	        File file = fc.getSelectedFile();
	        //This is where a real application would open the file.
	        System.out.println("Opening: " + file.getAbsolutePath() );
	        mother.getSaveButton().setEnabled(true);
	        tsk.SetPicture(file);
	        mother.setPicture(file);
	    
	      } else {
	        System.out.println("Open command cancelled by user.");
	      }
	    

	  
	}
}
