package graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import models.Main;

import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.UpdateException;

import bonita.Collect;

public class SendPictureActionListener implements ActionListener{
	Collect tsk;
	Main main;
	public SendPictureActionListener(Collect tsk, Main main){
		this.tsk = tsk;
		this.main = main;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			tsk.sendPicture();
			main.waitForTasks();
		} catch (FlowNodeExecutionException | ProcessInstanceNotFoundException
				| UpdateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
