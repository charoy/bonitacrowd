import java.io.File;
import java.io.IOException;

import bonita.*;
import bonita.Process;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.bpm.document.DocumentAttachmentException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;

public class main {
	static Process pr;
	static Task task;
	public static String [] listerRepertoire(File repertoire){
		String [] listefichiers=repertoire.list();	
		return listefichiers;
	}
	
	public static void main(String[] args) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, LoginException, DocumentAttachmentException {
		// TODO Auto-generated method stub
		
		pr = bonita.Process.getInstance();
		System.out.println("Setting properties");
		pr.set(args[0]);
		System.out.println("Connecting");
		pr.connect(args[1], args[2]);

		while(true)
		try {
			waitForTask();
		} catch (ProcessInstanceNotFoundException | UpdateException
				| IOException | FlowNodeExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void waitForTask() throws FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException, IOException, DocumentAttachmentException{
		while(true){
		task = pr.getNextTask();
			if(task!=null){
				
				
				System.out.println(task.getType());
				switch(task.getType()){
				case "Collect":
					task = (Collect) task;
					Collect();
					break;
				case "Select":
					task = (Select) task;
					Select();
					break;
				case "Assessment":
					task = (Assessment) task;
					Assessment();
					break;
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private static void Assessment() {
		try {
			task.takeTask();
			((Assessment) task).updatePictureToAssess();
			((Assessment) task).assess((int)(Math.random() * (9)));
			
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
	private static void Collect() throws DocumentAttachmentException {
		// TODO Auto-generated method stub
		try {
			task.takeTask();
		} catch (UpdateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		String [] list = listerRepertoire(new File(main.class.getResource( "/photos" ).getPath()));
		int i = (int)(Math.random() * (list.length));
		
			((Collect) task).SetPicture(new File(main.class.getResource( "/photos" ).getPath()+"/"+list[i]));
			try {
				((Collect) task).sendPicture();
			} catch (FlowNodeExecutionException
					| ProcessInstanceNotFoundException | UpdateException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}
	private static void Select(){
		
		try {
			task.takeTask();
			((Select) task).updateRoadsToSelect();
			System.out.println(((Select) task).getRoadsToSelect());
			int i = (int)(Math.random() * (3));
			((Select) task).select(i);
		} catch (FlowNodeExecutionException | ProcessInstanceNotFoundException
				| UpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
