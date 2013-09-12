package models;

import org.bonitasoft.engine.exception.UpdateException;

import graphic.WaitTask;
import bonita.Task;
import bonita.process;

public class ThreadWaitTask extends Thread {
	
	Task next;
	String nextTask;

	WaitTask wt;
	public ThreadWaitTask(){
		
		
		nextTask = "";
	}
	public void run() {
		while(true){
			next = process.getInstance().getNextTask();
		
			if(wt!=null){
				if(next !=null){
					if(!nextTask.equals(next.getType())){
						
						nextTask = next.getType();
						wt.update();
					}
				}
				else if (!nextTask.equals("")){
					wt.update();
					nextTask ="";
				}
			}
			try {
		        // pause
		        Thread.sleep(500);
		      }
		      catch (InterruptedException ex) {}
		}
	}
	public void setObservable(WaitTask wt2) {
		// TODO Auto-generated method stub
		wt = wt2;
		
	}
	Task getTask(){
		return next;
	}
	
		

}
