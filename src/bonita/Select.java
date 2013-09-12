package bonita;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import org.bonitasoft.engine.bpm.data.DataInstance;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.UpdateException;


public class Select extends Task{
	HashMap<String, ArrayList<String>> roads;
	
	public Select( long l, long idUser) {
		super( l, "Select",  idUser);
		// TODO Auto-generated constructor stub
		roads = new HashMap<String, ArrayList<String>>();
		
		
	}
	public void updateRoadsToSelect(){
		//Will return a table of string containing file's id and urls
		java.util.List<DataInstance> bla = process.getInstance().getProcessAPI().getActivityDataInstances(this.ID, 0, 20);
		roads = new HashMap<String, ArrayList<String>>();
		for (int i =0; i< bla.size(); i++){
			if(bla.get(i).getValue()!=null)
			System.out.println(i + bla.get(i).getValue().toString());
		}
		@SuppressWarnings("unchecked")
		ArrayList<ArrayList<String>> lala = (ArrayList<ArrayList<String>>) bla.get(4).getValue();
		
		for (int i =0; i<lala.size(); i++){
			ArrayList<String> pics = new ArrayList<String>();
			pics.add(lala.get(i).get(1));
			pics.add(lala.get(i).get(2));
			roads.put(lala.get(i).get(0),pics);
			System.out.println(lala.get(i).get(1));
			
			
			
		}
		
		
	}
	public HashMap<String, ArrayList<String>> getRoadsToSelect(){
		
		return roads;
	}
	public void getFile(String host, String path)
	{
		InputStream input = null;
		FileOutputStream writeFile = null;

		try
		{
			URL url = new URL(host);
			URLConnection connection = url.openConnection();
			int fileLength = connection.getContentLength();

			if (fileLength == -1)
			{
				System.out.println("Invalide URL or file.");
				return;
			}

			input = connection.getInputStream();
			
			writeFile = new FileOutputStream(path);
			byte[] buffer = new byte[1024];
			int read;

			while ((read = input.read(buffer)) > 0)
				writeFile.write(buffer, 0, read);
			writeFile.flush();
		}
		catch (IOException e)
		{
			System.out.println("Error while trying to download the file.");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				writeFile.close();
				input.close();
			}
			catch (IOException e)
			{
				System.out.println(host);
				e.printStackTrace();
			}
		}
	}
	public void select(int i) throws FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException{
		Object[] values = roads.keySet().toArray();
		if (i<values.length && i>=0){
			this.addArguments("selectedPhoto", values[i].toString());
			this.executeTask();
			
		}
	}
	
}
