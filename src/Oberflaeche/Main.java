package Oberflaeche;

import javax.swing.JFrame;

public class Main
{
	public static void main(String[] args) 
	{
		JFrame jf = new GUI();
		jf.setVisible(true);
		jf.setTitle("Gauss Jordan Algorithmus");
		jf.setSize(480, 370);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);	
	}
}
