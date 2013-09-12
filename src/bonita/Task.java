package bonita;
import java.io.Serializable;
import java.util.HashMap;

import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.exception.UpdateException;


public abstract class Task {
	String type;
	HashMap<String, Serializable> arguments;
	long ID;
	long idUser;
	//Type can be Collect, Select or Assess
	public Task(long l, String type, long idUser){
		
		this.arguments = new HashMap<String,Serializable>();
		this.ID = l;
		this.type = type;
		this.idUser = idUser;
	}
	public String getType(){
		return type;
	}
	public long getID(){
		return ID;
	}
	public void addArguments(String key, Serializable value){
		arguments.put(key, value);
	}
	public void takeTask() throws UpdateException{
		Process.getInstance().getProcessAPI().assignUserTask(this.ID, idUser);
		
	}
	public boolean executeTask(){
		Object[] keys =  arguments.keySet().toArray();
	
		
		
		for (int i =0; i<keys.length; i++){

			try {
				Process.getInstance().getProcessAPI().updateActivityDataInstance(keys[i].toString(),ID, arguments.get(keys[i].toString()));
				
			} catch (UpdateException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}
			
		}
		try {
			Process.getInstance().getProcessAPI().executeFlowNode(ID);
			
		} catch (FlowNodeExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
	}
	
}
