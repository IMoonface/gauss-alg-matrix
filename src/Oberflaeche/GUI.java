package Oberflaeche;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
	JTextField eingabeVar;
	JTextField eingabeGlei;
	JLabel erklaerung;
	double koeff [][];
	double test [][] = {{5,5,3,7},{4,4,8,6},{5,6,9,1}};
	double test2 [][] = {{5,8,3,2},{3,4,1,5},{2,8,7,4}};
	double test3 [][] = {{3,2,2,2},{2,3,2,2},{2,2,3,2}};
	int var = 0, gleich = 0, bereit = 0, max = 0, max2 = 0, antwort = 0;
	
	public GUI() 
	{
		setLayout(null);
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 500, 350);
		add(panel);
		
		console = new JTextArea();
		console.setEditable(false);
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
		variable.setToolTipText("Druecken Sie, um die Anzahl der Variablen zu bestaetigen");
		panel.add(variable);
		
		gleichung = new JButton("Gleichungen");
		gleichung.setBounds(340, 100, 110, 20);
		gleichung.setEnabled(false);
		gleichung.addActionListener(new ActionHandler());
		gleichung.setToolTipText("Druecken Sie, um die Anzahl der Gleichungen zu bestaetigen");
		panel.add(gleichung);
		
		loesen = new JButton("Loesen");
		loesen.setBounds(300, 160, 110, 20);
		loesen.setEnabled(false);
		loesen.addActionListener(new ActionHandler());
		loesen.setToolTipText("Druecken Sie, um den Gauss-Jordan-Algorithmus auszufuehren");
		panel.add(loesen);
		
		eingabeVar = new JTextField();
		eingabeVar.setBounds(300, 40, 30, 20);
		eingabeVar.addCaretListener(new CaretHandler());
		eingabeVar.addKeyListener(new KeyHandler());
		eingabeVar.setToolTipText("Geben Sie eine Zahl zwischen 0 und 10 ein!");
		panel.add(eingabeVar);
		
		eingabeGlei = new JTextField();
		eingabeGlei.setBounds(300, 100, 30, 20);
		eingabeGlei.addCaretListener(new CaretHandler());
		eingabeGlei.addKeyListener(new KeyHandler());
		eingabeGlei.setToolTipText("Geben Sie eine Zahl zwischen 0 und 10 ein!");
		panel.add(eingabeGlei);
		
		erklaerung = new JLabel("Dies ist Programm zur Ausfuehrung des Gauss Jordan Algorithmus");
		erklaerung.setBounds(10, 260, 450, 100);
		panel.add(erklaerung);
		
		addWindowListener(new WindowHandler());
	}

	private class ActionHandler implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent a) 
		{
			if(a.getSource()==variable) 
			{
				var = Integer.valueOf(String.valueOf(eingabeVar.getText()));	
				bereit += 1;
				loesen.setEnabled(false);
				if(bereit % 2 == 0 && var != 0 && gleich != 0) 
				{
					koeff = Fuellen(var, gleich);
					System.out.println(" Ihre Koeffizienten Matrix ist:\n");  
					Ausgabe(koeff);
					loesen.setEnabled(true);
				}
			}		
			if (a.getSource()==gleichung) 
			{
				gleich = Integer.valueOf(String.valueOf(eingabeGlei.getText()));
				bereit += 1;
				loesen.setEnabled(false);
				if(bereit % 2 == 0 && var != 0 && gleich != 0) 
				{
					
					koeff = Fuellen(var, gleich);
					System.out.println(" Ihre Koeffizienten Matrix ist:\n");  
					Ausgabe(koeff);
					loesen.setEnabled(true);
				}
			}		
			if (a.getSource()==loesen) 
			{
				gaussAlg(test3);
				loesen.setEnabled(false);
			}
		}
	}
	
	private class CaretHandler implements CaretListener 
	{
		@Override
		public void caretUpdate(CaretEvent a) 
		{
			String e1 = String.valueOf(eingabeVar.getText());
			e1 = e1.trim();
			String e2 = String.valueOf(eingabeGlei.getText());
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
			if(e.getSource() == eingabeVar) 
			{
				String text = eingabeVar.getText();
				int laenge = text.length();
				if (laenge > 0) 
				{
					//Wenn laenge = 1 waere, dann soll es an Index 0 gucken usw. (So wird jedes neue Zeichen ueberprueft)
					char zeichen = text.charAt(laenge-1);
					//Falls zeichen nicht groesser gleich 0 und kleiner gleich 9 ist 
					if (!((zeichen >= '0') && (zeichen <= '9'))) 
					{
						JOptionPane.showMessageDialog(GUI.this, "Bitte nur Zahlen eingeben!");
						eingabeVar.setText("");
					}
				}	
			}
			else if(e.getSource() == eingabeGlei) 
			{
				String text = eingabeGlei.getText();
				int laenge = text.length();
				if (laenge > 0) 
				{
					char zeichen = text.charAt(laenge-1);
					if (!((zeichen >= '0') && (zeichen <= '9'))) 
					{
						JOptionPane.showMessageDialog(GUI.this, "Bitte nur Zahlen eingeben!") ;
						eingabeGlei.setText("");
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
	
	private class WindowHandler implements WindowListener 
	{
		@Override
		public void windowOpened(WindowEvent e) 
		{
			
		}

		@Override
		public void windowClosing(WindowEvent e) 
		{
			antwort = JOptionPane.showConfirmDialog(GUI.this, "Wollen Sie wirklich beenden?", "Beenden?", JOptionPane.YES_NO_OPTION);
			if(antwort == JOptionPane.YES_OPTION) 
			{
				System.exit(0);
			}
		}

		@Override
		public void windowClosed(WindowEvent e) 
		{
			
		}

		@Override
		public void windowIconified(WindowEvent e) 
		{
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) 
		{
			
		}

		@Override
		public void windowActivated(WindowEvent e) 
		{
			
		}
		//Gibt an, dass eine Methodendeklaration eine Methodendeklaration in einem Supertyp überschreiben soll.
		@Override
		public void windowDeactivated(WindowEvent e) 
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
				max = matrize[0].length-1;
			} 
			else 
			{
				max = matrize.length;
			}
			for(int erstesEL = 0; erstesEL < max; erstesEL++) 
			{
				double teiler = matrize[erstesEL][erstesEL];
				if (teiler == 0.0) 
				{
					teiler = matrize[erstesEL+1][erstesEL];
					for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						matrize[erstesEL+1][SpaltenIndex] = runden(matrize[erstesEL+1][SpaltenIndex]/teiler);
					}
					System.out.println("Dividiere " + (erstesEL+2) + ". Zeile durch: " + teiler + "\n");
					Ausgabe(matrize);
					for(int spaltenEl = erstesEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						for (int SpaltenIndex2 = 0; SpaltenIndex2 < matrize[0].length; SpaltenIndex2++) 
						{
							double summand = matrize[erstesEL+1][SpaltenIndex2]*1;
							matrize[spaltenEl-1][SpaltenIndex2] = runden(matrize[spaltenEl-1][SpaltenIndex2]+summand);
						}
						System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (spaltenEl) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
				else 
				{
					for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						matrize[erstesEL][SpaltenIndex] = runden(matrize[erstesEL][SpaltenIndex]/teiler);
					}
					System.out.println("Dividiere " + (erstesEL+1) + ". Zeile durch: " + teiler + "\n");
					Ausgabe(matrize);
					
					for(int spaltenEl = erstesEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						double multi = -matrize[spaltenEl][erstesEL];
						for (int SpaltenIndex2 = 0; SpaltenIndex2 < matrize[0].length; SpaltenIndex2++) 
						{
							double summand = matrize[erstesEL][SpaltenIndex2] * multi;
							matrize[spaltenEl][SpaltenIndex2] = runden(matrize[spaltenEl][SpaltenIndex2]+summand);
						}
						System.out.println("Multipliziere mit: " + multi + "\nund addiere mit der " + (spaltenEl+1) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
			}
			System.out.println("Die Loesung ist:\n");
			Ausgabe(matrize);
			if(var >= gleich) 
			{
				max2 = matrize.length+1;
				for(int einserSp = matrize.length; einserSp > 1; einserSp--) 
				{
					max2--; 
					for (int einserZL = max2; einserZL >= 2; einserZL--) 
					{
						double multi2 = -matrize[einserZL-2][einserSp-1];		
						for (int SpaltenIndex3 = matrize[0].length; SpaltenIndex3 >= 1; SpaltenIndex3--) 
						{
							double summand2 = matrize[einserSp-1][SpaltenIndex3-1]*multi2;
							matrize[einserZL-2][SpaltenIndex3-1] = runden(matrize[einserZL-2][SpaltenIndex3-1]+summand2);
						}
						System.out.println("Multipliziere mit: " + multi2 + "\nund addiere mit der " + (einserZL-1) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
				System.out.println("Die Loesung ist:\n");
				Ausgabe(matrize); 
			}
		}
	}
	/*
	//Probleme beim runden noch fixen
	double runden2(double wert, int nachkommastellen)
	{
		double zahl = Math.pow(10, nachkommastellen);
		double gerundet = Math.rint(wert*zahl)/zahl;
		if(gerundet == -0.0) 
		{
			gerundet = 0.0;
		}
		return gerundet;
	}
	*/
	//Probleme beim runden noch fixen
	double runden(double wert) 
	{
		wert = wert * 1000;
		wert = Math.round(wert);
		wert = wert / 1000;
		if(wert == -0.0) 
		{
			wert = 0.0;
		}
		return wert;
	}
}