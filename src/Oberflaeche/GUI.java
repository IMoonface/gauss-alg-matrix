package Oberflaeche;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class GUI extends JFrame
{
	JPanel panel;
	JTextArea console;
	JTextPane hinweis;
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
	int var = 0, gleich = 0, bereit = 0;
	
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
		
		hinweis = new JTextPane();
		hinweis.setText("Hinweis:\nDie Ergebnisse werden auf 3 Nachkommastellen gerundet, somit können kleine Ungenauigkeiten auftreten.");
		hinweis.setBackground(getContentPane().getBackground());
		hinweis.setBounds(300, 190, 150, 100);
		hinweis.setEditable(false);
		panel.add(hinweis);
		
		addWindowListener(new WindowHandler());
	}
	//extends, damit ActionHandler die Funktionen aus FKT nutzen kann 
	private class ActionHandler extends FKT implements ActionListener 
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
				gaussAlg(koeff, var, gleich);
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
			int antwort = JOptionPane.showConfirmDialog(GUI.this, "Wollen Sie wirklich beenden?", "Beenden?", JOptionPane.YES_NO_OPTION);
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
}