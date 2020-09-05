package Oberflaeche;

import javax.swing.JDialog;
import javax.swing.JTextArea;

public class Impressum extends JDialog
{
	JTextArea copyright;
	
	public Impressum(GUI easter) 
	{
		super(easter);
		setTitle("Impressum");
		setResizable(false);
		setModal(true);
		copyright = new JTextArea("programmed by Marc Uxa, 05.09.2020 \nhttps://github.com/IMoonface");
		copyright.setBackground(getContentPane().getBackground());
		add(copyright);	
		pack();
	}
}
