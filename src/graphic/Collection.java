package graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Main;

import bonita.Collect;



public class Collection extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton save;
	JLabel picture;
	Main main;
	public Collection(String area, Collect tsk, Main main){
		super();
		this.main = main;
		final JFileChooser fc = new JFileChooser();
		JPanel south = new JPanel();
		picture = new JLabel("none");
		
		JButton openButton = new JButton("Open a File...");
		save = new JButton("Send");
		save.setEnabled(false);
		openButton.addActionListener(new FileChooserActionListener(fc, this,tsk));
		save.addActionListener(new SendPictureActionListener(tsk,main));
		this.setLayout(new BorderLayout());
		this.add(picture, BorderLayout.NORTH);
		south.add(openButton);
		south.add(save);
		this.add(south,BorderLayout.SOUTH);
	}
	public void setPicture(File pic){
		ImageIcon im = new ImageIcon(pic.getAbsolutePath());
		picture.setText("");
		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(pic);
			BufferedImage resizedImage = new BufferedImage(this.getWidth(), originalImage.getHeight()/originalImage.getWidth()*this.getWidth(), originalImage.getType());
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, this.getWidth(), originalImage.getHeight()/originalImage.getWidth()*this.getWidth(), null);
			
			g.dispose();
			picture.setIcon(new ImageIcon(resizedImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public JButton getSaveButton(){
		return save;
	}
}
 