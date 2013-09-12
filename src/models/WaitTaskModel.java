package models;

import bonita.*;
import org.bonitasoft.engine.exception.UpdateException;

import graphic.WaitTask;


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
		Task tsk = bonita.Process.getInstance().getNextTask();
		tsk.takeTask();
		return tsk;
	}
	public void quit(){
		twt.stop();
	}
}
