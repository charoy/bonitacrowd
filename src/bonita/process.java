package bonita;
import java.util.ArrayList;

import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.bpm.data.DataInstance;
import org.bonitasoft.engine.bpm.flownode.ActivityInstanceCriterion;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.session.APISession;


public class process {
	ProcessAPI processAPI;
	APISession session;
	LoginAPI loginAPI;
	private static process INSTANCE = null;
	private process() {
	
	}

	public void connect(String username, String password) throws LoginException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		session = loginAPI.login(username, password);
		processAPI = TenantAPIAccessor.getProcessAPI(session);
	}
	public String getNextTaskType(){
		return getNextTask().getType();
	}
	public ProcessAPI getProcessAPI(){
		
		return processAPI;
	}
	public void set(String bonitaHome) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		System.setProperty("bonita.home", bonitaHome);
		// get the LoginAPI using the TenantAPIAccessor
		loginAPI = TenantAPIAccessor.getLoginAPI();
	
		// log in to the tenant to create a session
	}

	public static process getInstance() 
	{	
		if (INSTANCE == null)
		{
			synchronized(process.class)
			{
				if (INSTANCE == null)
				{	INSTANCE = new process();
				}
			}
		}
			return INSTANCE;
	}
	
	@SuppressWarnings("unused")
	public Task getNextTask(){

		final ArrayList<HumanTaskInstance> pendingTasks = (ArrayList<HumanTaskInstance>) processAPI.getPendingHumanTaskInstances(session.getUserId(),0, 20, ActivityInstanceCriterion.PRIORITY_ASC);
		System.out.println("Getting waiting human tasks");
		for (int j=0; j<pendingTasks.size(); j++){
			java.util.List<DataInstance> bla = processAPI.getActivityDataInstances(pendingTasks.get(j).getId(), 0, 20);
			Task task=null;
			System.out.println(pendingTasks.get(j).getName());
			switch (pendingTasks.get(j).getName()){
				case "Collect":
					task = new Collect(pendingTasks.get(j).getId(),session.getUserId());
					
					break;
				case "Road selection":
					task = new Select(pendingTasks.get(j).getId(),session.getUserId());
					
					break;
				case "Assessment":
					task = new Assessment(pendingTasks.get(j).getId(),session.getUserId());
					
					break;
					
			}
			return task;		
			
			
		}
		return null;
	}
}
