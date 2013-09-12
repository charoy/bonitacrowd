import java.io.File;
import java.io.IOException;

import bonita.*;
import org.bonitasoft.engine.bpm.document.DocumentAttachmentException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.UpdateException;


public class perfThread extends Thread {
	Task tsk;
	public perfThread(bonita.Process pr){
		this.tsk =  pr.getNextTask();;
	}
	public void run(){
		switch(tsk.getType()){
		case "Collect":
			tsk = (Collect) tsk;
			try {
				Collect();
			} catch (DocumentAttachmentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Select":
			tsk = (Select) tsk;
			Select();
			break;
		case "Assessment":
			tsk = (Assessment) tsk;
			Assessment();
			break;
		}
	}
	private  void Assessment() {
		try {
			tsk.takeTask();
			((Assessment) tsk).updatePictureToAssess();
			((Assessment) tsk).assess((int)(Math.random() * (9)));
			
		} catch (UpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FlowNodeExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessInstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void Collect() throws DocumentAttachmentException {
		// TODO Auto-generated method stub
		try {
			tsk.takeTask();
		} catch (UpdateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		//String [] list = listerRepertoire(new File("/home/phoenamandre/partage/stage"));
		//int i = (int)(Math.random() * (list.length));
		
			((Collect) tsk).SetPicture(new File("/home/phoenamandre/partage/stage/small.JPG"));
			try {
				((Collect) tsk).sendPicture();
			} catch (FlowNodeExecutionException
					| ProcessInstanceNotFoundException | UpdateException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}
	private void Select(){
		
		try {
			tsk.takeTask();
			((Select) tsk).updateRoadsToSelect();
			System.out.println(((Select) tsk).getRoadsToSelect());
			int i = (int)(Math.random() * (3));
			((Select) tsk).select(i);
		} catch (FlowNodeExecutionException | ProcessInstanceNotFoundException
				| UpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
