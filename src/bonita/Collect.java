package bonita;
import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;
import org.bonitasoft.engine.bpm.document.DocumentAttachmentException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.UpdateException;


public class Collect extends Task{
	File picture;
	public Collect( long l, long idUser) {
		super(l,"Collect", idUser);
		
		// TODO Auto-generated constructor stub
	}
	public void sendPicture(String picture) throws FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException{
		this.addArguments("image", picture);
		this.executeTask();
		
	}
	public void SetPicture(File picture){
		this.picture = picture;
	}
	public void sendPicture() throws FlowNodeExecutionException, ProcessInstanceNotFoundException, UpdateException, IOException{
		byte[] FileBytes = FileUtils.readFileToByteArray(picture);
		

		try {
			process.getInstance().getProcessAPI().attachNewDocumentVersion(process.getInstance().getProcessAPI().getProcessInstanceIdFromActivityInstanceId(ID), "image",picture.getName(), new MimetypesFileTypeMap().getContentType(picture),FileBytes);
		} catch (DocumentAttachmentException e) {
			// TODO Auto-generated catch block
			
			try {
				process.getInstance().getProcessAPI().attachDocument(process.getInstance().getProcessAPI().getProcessInstanceIdFromActivityInstanceId(ID), "image",picture.getName(), new MimetypesFileTypeMap().getContentType(picture),FileBytes);
			} catch (DocumentAttachmentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
	
		this.executeTask();
		
	}
}
