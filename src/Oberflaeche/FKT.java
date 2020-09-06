package Oberflaeche;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FKT
{
	int max = 0;
	boolean loesbar = false;
	Random r = new Random();
	
	double[][] Fuellen(int var, int gleich, int modus) 
	{
		double[][] matrize = new double[gleich][var+1];
	    for(int gleichungen = 0; gleichungen < matrize.length; gleichungen++) 
	    {
	        for(int variablen = 0; variablen < matrize[0].length; variablen++) 
	        {
	        	switch(modus) 
	        	{     	
	        		case 1: //Nur positive Zufallszahlen
	        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(0, 9);
		        		while(matrize[gleichungen][variablen] == 0.0) 
		        		{
		        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(0, 9);
		        		}
		        		break;
	        		case 2: //Nur negative Zufallszahlen
	        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(-9, 0);
		        		while(matrize[gleichungen][variablen] == 0.0) 
		        		{
		        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(-9, 0);
		        		}
		        		break;
	        		case 3: //Positive und negative Zufallszahlen
	        			//ThreadLocalRandom: Ein Zufallszahlengenerator, isoliert zum aktuellen Thread (Macht eig nur Sinn bei Multithreading)
	    	        	//Usages of this class should typically be of the form: ThreadLocalRandom.current().nextX(...) (where X is Int, Long, etc)
	    	        	//nextLong: Gibt einen zufälligen long-Wert zwischen dem angegebenen Ursprung (erster Para.) und der angegebenen Grenze (zweiter Para.) zurück
	        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(-9, 9);
		        		while(matrize[gleichungen][variablen] == 0.0) 
		        		{
		        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(-9, 9);
		        		}
		        		break;
	        	}
	        }
	    }  
		return matrize;
	}
	
	void Ausgabe(double[][] matrize) 
	{	
	    for(int ausgabeG = 0; ausgabeG < matrize.length; ausgabeG++) 
		{
			for(int ausgabeV = 0; ausgabeV <= matrize[0].length-1; ausgabeV++) 
			{
				if(ausgabeV < matrize[0].length-2)
				{
					System.out.print(" " + matrize[ausgabeG][ausgabeV] + ",");
				}
				if(ausgabeV == matrize[0].length-2) 
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
	
	void gaussAlg(double[][] matrize, int var, int gleich) 
	{
		if(var==1 && gleich==1) 
		{
			System.out.println("Das Ergebnis ist:\n");
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
				if(teiler == 0.0) 
				{
					teiler = matrize[erstesEL+1][erstesEL];
					for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						matrize[erstesEL+1][SpaltenIndex] = runden(matrize[erstesEL+1][SpaltenIndex]/teiler);
					}
					System.out.println("Dividiere " + (erstesEL+2) + ". Zeile durch: " + teiler + "\n");
					Ausgabe(matrize);
					for(int spaltenEl = erstesEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						for(int SpaltenIndex2 = 0; SpaltenIndex2 < matrize[0].length; SpaltenIndex2++) 
						{
							double summand = matrize[erstesEL+1][SpaltenIndex2];
							matrize[spaltenEl-1][SpaltenIndex2] = runden(matrize[spaltenEl-1][SpaltenIndex2]+summand);
						}
						System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (spaltenEl) + ". Zeile\n");
						Ausgabe(matrize);
					}
					//weil die 3te Zeile 1 als erstes Element hat muss man die 2 Zeile jetzt negiert draufrechnen
					for(int spaltenEl = erstesEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						for(int SpaltenIndex2 = 0; SpaltenIndex2 < matrize[0].length; SpaltenIndex2++) 
						{
							double summand = -matrize[erstesEL][SpaltenIndex2];
							matrize[spaltenEl][SpaltenIndex2] = runden(matrize[spaltenEl][SpaltenIndex2]+summand);
						}
						System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (spaltenEl+1) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
				else 
				{
					for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						matrize[erstesEL][SpaltenIndex] = runden(matrize[erstesEL][SpaltenIndex]/teiler);
					}
					System.out.println("Dividiere " + (erstesEL+1) + ". Zeile durch: " + teiler + "\n");
					Ausgabe(matrize);
					
					for(int spaltenEl = erstesEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						double multi = -matrize[spaltenEl][erstesEL];
						for(int SpaltenIndex2 = 0; SpaltenIndex2 < matrize[0].length; SpaltenIndex2++) 
						{
							double summand = matrize[erstesEL][SpaltenIndex2] * multi;
							matrize[spaltenEl][SpaltenIndex2] = runden(matrize[spaltenEl][SpaltenIndex2]+summand);
						}
						System.out.println("Multipliziere mit: " + multi + "\nund addiere mit der " + (spaltenEl+1) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
			}
			System.out.println("Das Ergebnis ist:\n");
			Ausgabe(matrize);
			//Ueberpruefen auf Nullzeile
			for(int zeilen = 0; zeilen < matrize.length; zeilen++) 
			{
				for(int spalten = 0; spalten < matrize[0].length-1; spalten++) 
				{
					if((matrize[zeilen][spalten]) == 0.0) 
					{
						loesbar = false;
					}
					else 
					{
						loesbar = true;
					}
				}
			}
			//Rest des Gauss Jordan
			if(var >= gleich && loesbar) 
			{
				int max2 = matrize.length+1;
				for(int einserSp = matrize.length; einserSp > 1; einserSp--) 
				{
					max2--; 
					for(int einserZL = max2; einserZL >= 2; einserZL--) 
					{
						double multi2 = -matrize[einserZL-2][einserSp-1];		
						for(int SpaltenIndex3 = matrize[0].length; SpaltenIndex3 >= 1; SpaltenIndex3--) 
						{
							double summand2 = matrize[einserSp-1][SpaltenIndex3-1]*multi2;
							matrize[einserZL-2][SpaltenIndex3-1] = runden(matrize[einserZL-2][SpaltenIndex3-1]+summand2);
						}
						System.out.println("Multipliziere mit: " + multi2 + "\nund addiere mit der " + (einserZL-1) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
				System.out.println("Das Ergebnis ist:\n");
				Ausgabe(matrize); 
			}
			else 
			{
				System.out.println("Das Gleichungsystem ist unloesbar!");
			}
		}
	}
	
	double runden(double wert) 
	{
		wert = Math.rint(wert*1000.0)/1000.0;
		if(wert == -0.0) 
		{
			wert = 0.0; 
		}
		return wert;
	}
	
	void OptionPane(String nachricht, JFrame frame) 
	{
		JOptionPane pane = new JOptionPane(nachricht, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_OPTION, null, new String[] {"OK"});
		JDialog dialog = pane.createDialog(frame, "Warnung");
		dialog.setFocusable(true);
		dialog.setVisible(true);
	}
	
	void textChecken(String text, int laenge, JTextField eingabe, JFrame frame) 
	{
		if(laenge > 0) 
		{
			//Wenn laenge = 1 waere, dann soll es an Index 0 gucken usw. (So wird jedes neue Zeichen ueberprueft)
			char zeichen = text.charAt(laenge-1);
			//Falls zeichen nicht groesser gleich 0 und kleiner gleich 9 ist 
			if(!((zeichen >= '1') && (zeichen <= '9'))) 
			{
				OptionPane("Bitte nur Zahlen zwischen 0 und 10 eingeben!", frame);
				eingabe.setText("");
			}
		}
	}
}