package Oberflaeche;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
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
	//Versions ID: Zum Unterscheiden der Klasse GUI
	private static final long serialVersionUID = 1L;
	
	JPanel panel;
	JTextArea console;
	JTextPane hinweis;
	JScrollPane scroll;
	JButton variable, gleichung, ergebnis, ja, nein;
	JCheckBox positiv, negativ, mixed;
	JTextField eingabeVar, eingabeGlei;
	JLabel erklaerung;
	JDialog dialog;
	Image logo;
	double koeff[][];
	/*
	//Nur zum Testen
	double test [][] = {{5.0,5.0,3.0,7.0},
						{4.0,4.0,8.0,6.0},
						{5.0,6.0,9.0,1.0}};
	
	double test2 [][] = {{5.0,8.0,3.0,2.0},
						 {3.0,4.0,1.0,5.0},
						 {2.0,8.0,7.0,4.0}};
	
	double test3 [][] = {{3.0,2.0,2.0,2.0},
						 {2.0,3.0,2.0,2.0},
						 {2.0,2.0,3.0,2.0}};
	
	double test4 [][] = {{8.0,-3.0,3.0,3.0},
						 {4.0,-1.0,7.0,-3.0},
						 {3.0,3.0,-9.0,-6.0},
						 {-6.0,-8.0,3.0,-3.0}};	
	
	double test5 [][] = {{7.0, 1.0, 3.0},
						 {7.0, 1.0, 3.0}};
	*/
	int var = 0, gleich = 0, modus = 0;
	boolean varBereit = false, gleiBereit = false, angeklickt = false;
	
	public GUI() 
	{
		setLayout(null);
		setFocusable(true);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 480, 370);
		add(panel);
		
		logo = (new ImageIcon(getClass().getResource("gauss.jpg"))).getImage();
		setIconImage(logo);
		
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
		variable.setBounds(340, 40, 110, 20);
		variable.setEnabled(false);
		variable.addActionListener(new ActionHandler());
		variable.setToolTipText("Klicken Sie hier, um diese Anzahl an Variablen zu erzeugen!");
		panel.add(variable);
		
		positiv = new JCheckBox("+");
		positiv.setBounds(336, 65, 40, 25);
		positiv.setEnabled(false);
		positiv.addItemListener(new ItemHandler());
		positiv.setToolTipText("Generiere nur positive Zahlen.");
		panel.add(positiv);
		
		negativ = new JCheckBox("-");
		negativ.setBounds(375, 65, 40, 25);
		negativ.setEnabled(false);
		negativ.addItemListener(new ItemHandler());
		negativ.setToolTipText("Generiere nur negative Zahlen.");
		panel.add(negativ);
		
		mixed = new JCheckBox("+/-");
		mixed.setBounds(414, 65, 100, 25);
		mixed.setEnabled(false);
		mixed.addItemListener(new ItemHandler());
		mixed.setToolTipText("Generiere positive und negative Zahlen.");
		panel.add(mixed);
		
		gleichung = new JButton("Gleichungen");
		gleichung.setBounds(340, 100, 110, 20);
		gleichung.setEnabled(false);
		gleichung.addActionListener(new ActionHandler());
		gleichung.setToolTipText("Klicken Sie hier, um diese Anzahl an Gleichungen zu erzeugen!");
		panel.add(gleichung);
		
		ergebnis = new JButton("Ergebnis");
		ergebnis.setBounds(300, 150, 110, 20);
		ergebnis.setEnabled(false);
		ergebnis.addActionListener(new ActionHandler());
		ergebnis.setToolTipText("Klicken Sie hier, um den Gauss-Jordan-Algorithmus anzuwenden!");
		panel.add(ergebnis);
		
		eingabeVar = new JTextField();
		eingabeVar.setBounds(300, 40, 30, 20);
		eingabeVar.addCaretListener(new CaretHandler());
		eingabeVar.addKeyListener(new KeyHandler());
		eingabeVar.setToolTipText("Geben Sie hier eine Zahl von 1 bis 9 ein!");
		panel.add(eingabeVar);
		
		eingabeGlei = new JTextField();
		eingabeGlei.setBounds(300, 100, 30, 20);
		eingabeGlei.addCaretListener(new CaretHandler());
		eingabeGlei.addKeyListener(new KeyHandler());
		eingabeGlei.setToolTipText("Geben Sie hier eine Zahl von 1 bis 9 ein!");
		panel.add(eingabeGlei);
		
		erklaerung = new JLabel("Dies ist Programm zur Anwendung des Gauss Jordan Algorithmus.");
		erklaerung.setBounds(10, 260, 450, 100);
		panel.add(erklaerung);
		
		hinweis = new JTextPane();
		hinweis.setText("Hinweis:\nDas Ergebnis wird auf 3 Nachkommastellen gerundet. Es kann zu kleinen Ungenauigkeiten kommen.");
		hinweis.setBackground(getContentPane().getBackground());
		hinweis.setBounds(300, 190, 150, 100);
		hinweis.setEditable(false);
		panel.add(hinweis);
		
		addWindowListener(new WindowHandler());
		addKeyListener(new KeyHandler());
	}
	//extends, damit ActionHandler die Funktionen aus FKT nutzen kann 
	private class ActionHandler extends FKT implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent ap) 
		{
			if(ap.getSource() == variable) 
			{
				if(modus == 0) 
				{
					OptionPane("Bitte geben Sie einen Modus an!", GUI.this);
					variable.setFocusable(false);
				}
				else 
				{
					var = Integer.valueOf(String.valueOf(eingabeVar.getText()));	
					varBereit = true;
					if(gleiBereit && varBereit) 
					{
						koeff = Fuellen(var, gleich, modus);
						vorbereiten(koeff, ergebnis);
					}
					variable.setFocusable(false);
				}
			}		
			if(ap.getSource() == gleichung) 
			{
				if(modus == 0) 
				{
					OptionPane("Bitte geben Sie einen Modus an!", GUI.this);
					gleichung.setFocusable(false);
				}
				else 
				{
					gleich = Integer.valueOf(String.valueOf(eingabeGlei.getText()));
					gleiBereit = true;
					if(gleiBereit && varBereit) 
					{
						koeff = Fuellen(var, gleich, modus);
						vorbereiten(koeff, ergebnis);
					}
					gleichung.setFocusable(false);
				}
			}		
			if(ap.getSource() == ergebnis) 
			{
				gaussAlg(koeff, var, gleich);
				ergebnis.setEnabled(false);
				ergebnis.setFocusable(false);
			}
			if(ap.getSource() == ja) 
			{
				System.exit(0);
			}
			if(ap.getSource() == nein) 
			{
				//Gibt alle nativen Bildschirmressourcen frei, die von diesem Fenster, seinen Unterkomponenten und allen eigenen untergeordneten Elementen verwendet werden
				dialog.dispose();
			}
		}
	}
	
	private class CaretHandler implements CaretListener 
	{
		@Override
		public void caretUpdate(CaretEvent cu) 
		{
			String e1 = (String.valueOf(eingabeVar.getText())).trim();
			String e2 = (String.valueOf(eingabeGlei.getText())).trim();
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
			if(angeklickt == false) 
			{
				if(e1.length() == 1 && e2.length() == 1) 
				{
					positiv.setEnabled(true);
					negativ.setEnabled(true);
					mixed.setEnabled(true);
				}
				else 
				{
					positiv.setEnabled(false);
					negativ.setEnabled(false);
					mixed.setEnabled(false);
				}
			}
		}	
	}
	
	private class ItemHandler implements ItemListener 
	{
		@Override
		public void itemStateChanged(ItemEvent isc) 
		{
			if(isc.getSource() == positiv) 
			{
				if(positiv.isSelected()) 
				{
					modus = 1;
					positiv.setFocusable(false);
					negativ.setEnabled(false);
					mixed.setEnabled(false);
					angeklickt = true;
				}
				else 
				{
					modus = 0;
					negativ.setEnabled(true);
					mixed.setEnabled(true);
					angeklickt = false;
				}
			}
			if(isc.getSource() == negativ) 
			{
				if(negativ.isSelected()) 
				{
					modus = 2;
					negativ.setFocusable(false);
					positiv.setEnabled(false);
					mixed.setEnabled(false);
					angeklickt = true;
				}
				else 
				{
					modus = 0;
					positiv.setEnabled(true);
					mixed.setEnabled(true);
					angeklickt = false;
				}
			}
			if(isc.getSource() == mixed) 
			{
				if(mixed.isSelected()) 
				{
					modus = 3;
					mixed.setFocusable(false);
					positiv.setEnabled(false);
					negativ.setEnabled(false);
					angeklickt = true;
				}
				else 
				{
					modus = 0;
					positiv.setEnabled(true);
					negativ.setEnabled(true);
					angeklickt = false;
				}
			}
		}	
	}
	
	private class KeyHandler extends FKT implements KeyListener 
	{	
		@Override
		public void keyReleased(KeyEvent kr) 
		{
			if(kr.getSource() == eingabeVar) 
			{
				textChecken(eingabeVar.getText(), eingabeVar.getText().length(), eingabeVar, GUI.this);	
			}
			if(kr.getSource() == eingabeGlei) 
			{
				textChecken(eingabeGlei.getText(), eingabeGlei.getText().length(), eingabeGlei, GUI.this);
			}
		}

		@Override
		public void keyTyped(KeyEvent kt) {
		}

		@Override
		public void keyPressed(KeyEvent kp) 
		{
			if(kp.isControlDown() && kp.getKeyCode() == KeyEvent.VK_G) 
			{
				Impressum dialogEast = new Impressum(GUI.this);
				dialogEast.setLocationRelativeTo(GUI.this);
				dialogEast.setVisible(true);
			}
		}
	}
	
	private class WindowHandler implements WindowListener 
	{
		@Override
		public void windowOpened(WindowEvent wo) {		
		}

		@Override
		public void windowClosing(WindowEvent wc) 
		{
			Object[] options = {ja = new JButton("Ja"), nein = new JButton("Nein")};
			ja.addActionListener(new ActionHandler());
			nein.addActionListener(new ActionHandler());
			JOptionPane schliessen = new JOptionPane("Wollen Sie das Programm wirklich beenden?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
			dialog = schliessen.createDialog(GUI.this, "Schließen?");
			dialog.setFocusable(true);
			dialog.setVisible(true);
		}

		@Override
		public void windowClosed(WindowEvent wcd) {
		}

		@Override
		public void windowIconified(WindowEvent wi) {	
		}

		@Override
		public void windowDeiconified(WindowEvent wdi) {
		}

		@Override
		public void windowActivated(WindowEvent wa) {
		}
		//Gibt an, dass eine Methodendeklaration eine Methodendeklaration in einem Supertyp überschreiben soll.
		@Override
		public void windowDeactivated(WindowEvent wd) {	
		}
	}
}