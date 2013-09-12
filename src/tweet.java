import java.io.File;
import java.io.IOException;

import org.bonitasoft.engine.bpm.document.DocumentAttachmentException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.platform.LoginException;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import bonita.Assessment;
import bonita.Collect;
import bonita.Select;
import bonita.Task;
import bonita.process;


public class tweet {

	/**
	 * @param args
	 */
	static process pr;
	static Task task;
	public static void main(String[] args) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, LoginException, DocumentAttachmentException, FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException, IOException {
		// TODO Auto-generated method stub
		
		pr = process.getInstance();
		System.out.println("Setting properties");
		pr.set(args[0]);
		System.out.println("Connecting");
		pr.connect(args[1], args[2]);

		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(args[3]);
		QueryResult result;
		float last = 0;
		while(true){
			try {
				result = twitter.search(query);
				
				for (Status status : result.getTweets()) {
					if (status.getId() > last){
						executeNextTask();
						
						last = status.getId();
					}
			    }
					
			} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}   
		
		
	}
	public static void executeNextTask() throws FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException, IOException, DocumentAttachmentException{
		boolean w = true;
		while(w){
		task = pr.getNextTask();
			if(task!=null){
				
				w=false;
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

		
		String [] list = listerRepertoire(new File(tweet.class.getResource( "/photos" ).getPath()));
		int i = (int)(Math.random() * (list.length));
		
			((Collect) task).SetPicture(new File(tweet.class.getResource( "/photos" ).getPath()+"/"+list[i]));
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

	public static String [] listerRepertoire(File repertoire){
		String [] listefichiers=repertoire.list();	
		return listefichiers;
	}

}
