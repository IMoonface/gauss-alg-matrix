package Oberflaeche;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame jf = new GUI();
		jf.setVisible(true);
		jf.setTitle("Gauss-Jordan-Algorithmus");
		jf.setSize(650, 530);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);	
	}
}