package models;
import bonita.*;
import bonita.Process;
import graphic.Collection;
import graphic.Fenetre;
import graphic.Selection;
import graphic.WaitTask;

import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.platform.LoginException;


public class Main {
	static Fenetre fen;
	Process pr;
	static Main main ;
	public Main(String [] args) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		 try {
		        UIManager.setLookAndFeel(
		            UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch (Exception e) { }
		fen = new Fenetre();
		
		try {
			pr = bonita.Process.getInstance();
			pr.set(args[0]);
			pr.connect(args[1], args[2]);
			
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to connect");
		}
		
	}
	public static void main(String[] args) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, LoginException, FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException, IOException{
		
		main = new Main(args);
		main.waitForTasks();
		

	}
	public void waitForTasks(){ 
	  
		fen.getContentPane().removeAll();
		WaitTaskModel wtm = new WaitTaskModel();
		WaitTask wt = new WaitTask(main, wtm);
		wtm.setObservable(wt);
		wtm.run();
		fen.add(wt);
	}
	public void Select(Task tsk) throws FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException {
		Select sl = (Select) tsk;
		sl.updateRoadsToSelect();
		fen.getContentPane().removeAll();
		fen.repaint();
		Selection select = new Selection(sl,main);
		JScrollPane jsp = new JScrollPane(select);
		fen.add(jsp);
		fen.revalidate();
		select.update();
		fen.revalidate();
		
		
	}
	public void Collect(Task tsk) throws FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException, IOException{
		fen.getContentPane().removeAll();
		fen.repaint();
		Collect cl = (Collect) tsk;
		Collection collect = new Collection("bla",cl,main);
		fen.add(collect);
		fen.revalidate();
		
		
	}
	
	
	

	
}
