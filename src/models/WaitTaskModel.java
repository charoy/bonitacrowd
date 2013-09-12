package models;

import org.bonitasoft.engine.exception.UpdateException;

import graphic.WaitTask;
import bonita.Task;
import bonita.process;


public class WaitTaskModel {
	Task next;
	String nextTask;

	ThreadWaitTask twt;
	public WaitTaskModel(){
		twt = new ThreadWaitTask();

	}
	public void run(){
		
		twt.start(); 
		
		
	}
	public void setObservable(WaitTask wt){
		twt.setObservable(wt);
	}
	public Task getTask() {
		// TODO Auto-generated method stub
		return twt.getTask();
	}
	public Task takeNextTask() throws UpdateException {
		// TODO Auto-generated method stub
		Task tsk = process.getInstance().getNextTask();
		tsk.takeTask();
		return tsk;
	}
	public void quit(){
		twt.stop();
	}
}
