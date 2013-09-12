package graphic;
import javax.swing.JFrame;


public class Fenetre extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Fenetre(){
		
		super();
		this.setTitle("Test");
		this.setSize(400, 800);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
