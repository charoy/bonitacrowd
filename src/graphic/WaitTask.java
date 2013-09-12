package graphic;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bonita.Task;

import models.Main;
import models.WaitTaskModel;

public class WaitTask extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main main;
	WaitTaskModel model;
	JLabel task;
	JButton doIt;
	DoItListener dil;
	public WaitTask(Main main, WaitTaskModel model){
		this.model = model;
		this.main = main;
		task = new JLabel();
		doIt = new JButton("Take it");
		dil = new DoItListener(main,model);
		this.add(new JLabel("Available task : "));
		this.add(task);
		this.add(doIt);
		doIt.addActionListener(dil);
		doIt.setEnabled(false);
	}
	public void update(){
		Task next = model.getTask();
		if(next != null){
			task.setText(next.getType());
			
			doIt.setEnabled(true);
		}
		else{
			task.setText("none");
			doIt.setEnabled(false);
		}
		
	}
}
