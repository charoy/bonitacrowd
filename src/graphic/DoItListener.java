package graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.UpdateException;

import bonita.Task;

import models.Main;
import models.WaitTaskModel;

public class DoItListener implements ActionListener {

	Main main;
	Task task;
	WaitTaskModel wtm;
	public DoItListener(Main main,WaitTaskModel wtm){
		this.main = main;
		this.wtm = wtm;
	}
	public void setTask(Task task){
		this.task = task;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			task = wtm.takeNextTask();
		if(task!=null){
			
			
			
			switch(task.getType()){
			case "Collect":
				try {
					wtm.quit();
					main.Collect(task);
				} catch (FlowNodeExecutionException
						| ProcessInstanceNotFoundException | UpdateException
						| IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			case "Select":
				try {
					main.Select(task);
				} catch (FlowNodeExecutionException
						| ProcessInstanceNotFoundException | UpdateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			
			}
			
		}
		} catch (UpdateException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
	
}
