package Oberflaeche;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class GUI extends JFrame
{
	JPanel panel;
	JTextArea console;
	JScrollPane scroll;
	JButton variable;
	JButton gleichung;
	JButton loesen;
	JTextField eingabe1;
	JTextField eingabe2;
	JLabel erklaerung;
	
	double koeff [][];
	
	int var = 0, gleich = 0, fertig = 0, maximum = 0;
	
	public GUI() 
	{
		setLayout(null);
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 500, 350);
		add(panel);
		
		console = new JTextArea();
		//Ein PrintStream erweitert einen anderen Ausgabestream um die Moeglichkeit Darstellungen verschiedener Datenwerte (System.out.println(x) z.B.) zu drucken
		//Parameter: Erstellt eine neue Instanz von TextAreaOutputStream, die in die angegebene Instanz des Steuerelements (console) schreibt.
		PrintStream out = new PrintStream(new TextAreaOutputStream(console));
		//Weisst den "Standard"-Ausgabestream neu zu (in dem Fall wird es dem PrintStream zugewiesen)
		//Heisst alles was im System.out geschrieben wird, wird nun an die console drangehangen und PrintStream macht das Ganze "druckbar"
		System.setOut(out);
		
		scroll = new JScrollPane(console);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVisible(true);
		scroll.setBounds(10, 10, 280, 280);
		panel.add(scroll);
		
		variable = new JButton("Variablen");
		variable.setBounds(340, 40, 100, 20);
		variable.setEnabled(false);
		variable.addActionListener(new ActionHandler());
		variable.setToolTipText("Druecken Sie!");
		panel.add(variable);
		
		gleichung = new JButton("Gleichungen");
		gleichung.setBounds(340, 100, 110, 20);
		gleichung.setEnabled(false);
		gleichung.addActionListener(new ActionHandler());
		gleichung.setToolTipText("Druecken Sie!");
		panel.add(gleichung);
		
		loesen = new JButton("Loese");
		loesen.setBounds(320, 160, 110, 20);
		loesen.setEnabled(false);
		loesen.addActionListener(new ActionHandler());
		loesen.setToolTipText("Druecken Sie!");
		panel.add(loesen);
		
		eingabe1 = new JTextField();
		eingabe1.setBounds(300, 40, 30, 20);
		eingabe1.addCaretListener(new CaretHandler());
		eingabe1.addKeyListener(new KeyHandler());
		eingabe1.setToolTipText("Geben Sie eine Zahl zwischen 0 und 10 ein!");
		panel.add(eingabe1);
		
		eingabe2 = new JTextField();
		eingabe2.setBounds(300, 100, 30, 20);
		eingabe2.addCaretListener(new CaretHandler());
		eingabe2.addKeyListener(new KeyHandler());
		eingabe2.setToolTipText("Geben Sie eine Zahl zwischen 0 und 10 ein!");
		panel.add(eingabe2);
		
		erklaerung = new JLabel("Die ist Programm zur Ausfuehrung des Gauss Jordan Algorithmus");
		erklaerung.setBounds(10, 260, 450, 100);
		panel.add(erklaerung);
	}

	private class ActionHandler implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent a) 
		{
			if(a.getSource()==variable) 
			{
				var = Integer.valueOf(String.valueOf(eingabe1.getText()));	
				fertig += 1;
				if(fertig % 2 == 0 && var != 0 && gleich != 0) 
				{
					koeff = Fuellen(var, gleich);
					System.out.println(" Ihre Koeffizienten Matrix ist:\n");  
					Ausgabe(koeff);
					loesen.setEnabled(true);
				}
			} 
			
			if (a.getSource()==gleichung) 
			{
				gleich = Integer.valueOf(String.valueOf(eingabe2.getText()));
				fertig += 1;
				if(fertig % 2 == 0 && var != 0 && gleich != 0) 
				{
					
					koeff = Fuellen(var, gleich);
					System.out.println(" Ihre Koeffizienten Matrix ist:\n");  
					Ausgabe(koeff);
					loesen.setEnabled(true);
				}
			}
			
			if (a.getSource()==loesen) 
			{
				gaussAlg(koeff);
				loesen.setEnabled(false);
			}
		}
	}
	
	private class CaretHandler implements CaretListener 
	{
		@Override
		public void caretUpdate(CaretEvent a) 
		{
			String e1 = String.valueOf(eingabe1.getText());
			e1 = e1.trim();
			String e2 = String.valueOf(eingabe2.getText());
			e2 = e2.trim();
			if(e1.isEmpty() || e1.length() > 1) 
			{
				variable.setEnabled(false);
			}
			else 
			{
				variable.setEnabled(true);
			}
			
			if(e2.isEmpty() || e2.length() > 1) 
			{
				gleichung.setEnabled(false);
			}
			else 
			{
				gleichung.setEnabled(true);
			}
		}	
	}
	
	private class KeyHandler implements KeyListener 
	{	
		@Override
		public void keyReleased(KeyEvent e) 
		{
			if(e.getSource() == eingabe1) 
			{
				String text = eingabe1.getText();
				int laenge = text.length();
				if (laenge > 0) 
				{
					//Wenn laenge = 1 waere, dann soll es an Index 0 gucken usw. (So wird jedes neue Zeichen ueberprueft)
					char zeichen = text.charAt(laenge-1);
					//Falls zeichen nicht groesser gleich 0 und kleiner gleich 9 ist 
					if (!((zeichen >= '0') && (zeichen <= '9'))) 
					{
						//Standart Pop Up mit Message drin
						JOptionPane.showMessageDialog(GUI.this, "Bitte nur Zahlen eingeben!");
						eingabe1.setText("");
					}
				}	
			}
			else if(e.getSource() == eingabe2) 
			{
				String text = eingabe2.getText();
				int laenge = text.length();
				if (laenge > 0) 
				{
					char zeichen = text.charAt(laenge-1);
					if (!((zeichen >= '0') && (zeichen <= '9'))) 
					{
						JOptionPane.showMessageDialog(GUI.this, "Bitte nur Zahlen eingeben!") ;
						eingabe2.setText("");
					}
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{
			
		}

		@Override
		public void keyPressed(KeyEvent e) 
		{
			
		}
	}
	
	double[][] Fuellen (int x, int y) 
	{
		double[][] matrize = new double[y][x+1];
		Random random = new Random();
	    for (int gleichungen = 0; gleichungen < matrize.length; gleichungen++) 
	    {
	        for (int variablen = 0; variablen < matrize[0].length; variablen++) 
	        {
	        	matrize[gleichungen][variablen] = random.nextInt(9)+1;
	        }
	    }  
		return matrize;
	}
	
	void Ausgabe (double[][] matrize) 
	{	
	    for(int ausgabeG = 0; ausgabeG < matrize.length; ausgabeG++) 
		{
			for(int ausgabeV = 0; ausgabeV <= matrize[0].length-1; ausgabeV++) 
			{
				if (ausgabeV < matrize[0].length-2)
				{
					System.out.print(" " + matrize[ausgabeG][ausgabeV] + ",");
				}
				if (ausgabeV == matrize[0].length-2) 
				{
					System.out.print(" " + matrize[ausgabeG][ausgabeV]);
					System.out.print(" | ");
					System.out.print(matrize[ausgabeG][ausgabeV+1]);
				}
			}
			System.out.print("\n");
		}
	    System.out.println(); 
	}
	
	void gaussAlg (double[][] matrize) 
	{
		if (var==1 && gleich==1) 
		{
			System.out.println("Die Loesung ist:\n");
			Ausgabe(matrize);
		}
		else 
		{
			if(var < gleich) 
			{
				maximum = matrize[0].length-1;
			} 
			else 
			{
				maximum = matrize.length;
			}
			for(int erstesEL = 0; erstesEL < maximum; erstesEL++) 
			{
				double teiler = matrize[erstesEL][erstesEL];
				for (int index = 0; index < matrize[0].length; index++) 
				{
					matrize[erstesEL][index] = runden(matrize[erstesEL][index]/teiler, 3);
				}
				System.out.println("Dividiere " + (erstesEL+1) + " Zeile durch: " + teiler + "\n");
				Ausgabe(matrize);
					
				for(int spaltenEl = erstesEL + 1; spaltenEl < matrize.length; spaltenEl++) 
				{
					double summand = matrize[spaltenEl][erstesEL];
					for (int index2 = 0; index2 < matrize[0].length; index2++) 
					{
						double minus = matrize[erstesEL][index2]*summand;
						matrize[spaltenEl][index2] = runden(matrize[spaltenEl][index2]-minus, 3);
					}
					System.out.println("Multipliziere mit: " + summand + "\nund subtrahiere mit der " + (spaltenEl+1) + " Zeile\n");
					Ausgabe(matrize);
				}
			}
			//Noch den Rest hinmachen vom GaussJordan: https://matrixcalc.org/de/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B3,3,8,0,8%7D,%7B8,7,9,0,5%7D,%7B3,2,9,0,4%7D%7D%29
			System.out.println("Die Loesung ist:\n");
			Ausgabe(matrize);
		}
	}
	
	double runden(double wert, int nachkommastellen)
	{
		double zahl = Math.pow(10, nachkommastellen);
		return Math.round(wert*zahl)/zahl;
	}
}