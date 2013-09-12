package graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.exception.UpdateException;

import models.Main;

import bonita.Collect;
import bonita.Select;



public class Selection extends JPanel{
	JButton save;
	Select tsk;
	Main main;
	public Selection(Select tsk, Main main){
		super();
		this.main = main;
		this.tsk = tsk;

		final JFileChooser fc = new JFileChooser();
		JPanel south = new JPanel();
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
		
			
		
		
		
	}
	public void update(){
		tsk.updateRoadsToSelect();
		final HashMap<String, ArrayList<String>> roads = tsk.getRoadsToSelect();
		ArrayList<String> paths = new ArrayList<String>();
		Object [] values = roads.values().toArray();
		final Object [] keys = roads.keySet().toArray();
		for (int i = 0; i< roads.size(); i++){
			UUID idOne = UUID.randomUUID();
			paths.add(idOne.toString()+".jpg");
			tsk.getFile(roads.get(keys[i].toString()).get(1),idOne.toString()+".jpg");
		}
		for (int i = 0; i<paths.size();i++){
			BufferedImage originalImage;
			try {
				final int key = i;
				originalImage = ImageIO.read(new File(paths.get(i)));
				BufferedImage resizedImage = new BufferedImage(this.getWidth(), originalImage.getHeight()/originalImage.getWidth()*this.getWidth(), originalImage.getType());
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(originalImage, 0, 0, this.getWidth(), originalImage.getHeight()/originalImage.getWidth()*this.getWidth(), null);
				g.dispose();
				JLabel picture = new JLabel(new ImageIcon(resizedImage));
				picture.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						try {
							tsk.select(key);
							main.waitForTasks();
						} catch (FlowNodeExecutionException
								| ProcessInstanceNotFoundException
								| UpdateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}});
				this.add(picture);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}
	
}
 