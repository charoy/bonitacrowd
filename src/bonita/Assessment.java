package bonita;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.bonitasoft.engine.bpm.data.DataInstance;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.UpdateException;

public class Assessment extends Task {
	private String pictureToAssess;
	public Assessment(long l,  long idUser) {
		super(l, "Assessment", idUser);
		
	}
	@SuppressWarnings("unchecked")
	public void updatePictureToAssess(){
		java.util.List<DataInstance> bla = Process.getInstance().getProcessAPI().getActivityDataInstances(this.ID, 0, 20);
		pictureToAssess = ((ArrayList<String>) bla.get(0).getValue()).get(1);
		((ArrayList<String>) bla.get(0).getValue()).get(0);
		System.out.println(pictureToAssess);
		
		
	
	
	}
	public String getPictureToAssess(){
		return pictureToAssess;
	}
	public void assess(int damages) throws FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException{
		this.addArguments("damage", damages);
		this.executeTask();
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

}
