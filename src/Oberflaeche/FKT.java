package Oberflaeche;

import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FKT
{	
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
	        			//Uebersetzt einen long-Wert in ein BigDecimal-Objekt mit einer scale von Null (also 0 Stellen hinterm Komma)
	        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(0, 9);
	        			//Vergleicht das erste BigDecimal-Onjekt mit dem in der Klammer
	        			//Zwei BigDecimal-Objekte mit gleichem Wert, aber unterschiedlichem scale (z. B. 2.0 und 2.00) werden bei dieser Methode als gleich angesehen.
		        		while(matrize[gleichungen][variablen] == 0)
		        		{
		        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(0, 9);
		        		}
		        		break;
	        		case 2: //Nur negative Zufallszahlen
	        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(-9, 0);
		        		while(matrize[gleichungen][variablen] == 0) 
		        		{
		        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(-9, 0);
		        		}
		        		break;
	        		case 3: //Positive und negative Zufallszahlen
	        			//ThreadLocalRandom: Ein Zufallszahlengenerator, isoliert zum aktuellen Thread (Macht eig nur Sinn bei Multithreading)
	    	        	//Usages of this class should typically be of the form: ThreadLocalRandom.current().nextX(...) (where X is Int, Long, etc)
	    	        	//nextLong: Gibt einen zufälligen long-Wert zwischen dem angegebenen Ursprung (erster Para.) und der angegebenen Grenze (zweiter Para.) zurück
	        			matrize[gleichungen][variablen] = ThreadLocalRandom.current().nextLong(-9, 9);
		        		while(matrize[gleichungen][variablen] == 0) 
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
			for(int ausgabeV = 0; ausgabeV < matrize[0].length; ausgabeV++) 
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
			int max = 0;
			if(var < gleich) 
			{
				max = matrize[0].length-1;
			} 
			else 
			{
				max = matrize.length;
			}
			for(int divisorEL = 0; divisorEL < max; divisorEL++) 
			{
				double divisor = matrize[divisorEL][divisorEL];
				//Falls der divisor eine 0 ist und in der letzten Zeile steht
				if(divisor == 0 && divisorEL == max-1) 
				{
					break;
				}
				else if(divisor == 0 && divisorEL != max-1)
				{
					divisor = matrize[divisorEL+1][divisorEL];
					for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						matrize[divisorEL+1][SpaltenIndex] = runden(matrize[divisorEL+1][SpaltenIndex] / divisor);
					}
					System.out.println("Dividiere " + (divisorEL+2) + ". Zeile durch: " + divisor + "\n");
					Ausgabe(matrize);
					for(int spaltenEl = divisorEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
						{
							double summand = matrize[divisorEL+1][SpaltenIndex];
							matrize[spaltenEl-1][SpaltenIndex] = runden(matrize[spaltenEl-1][SpaltenIndex] + summand);
						}
						System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (spaltenEl) + ". Zeile\n");
						Ausgabe(matrize);
					}
					//weil die 3te Zeile 1 als erstes Element hat muss man die 2 Zeile jetzt negiert draufrechnen
					for(int spaltenEl = divisorEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
						{
							double summand = -matrize[divisorEL][SpaltenIndex];
							matrize[spaltenEl][SpaltenIndex] = runden(matrize[spaltenEl][SpaltenIndex] + summand);
						}
						System.out.println("Multipliziere mit: -1" + "\nund addiere mit der " + (spaltenEl+1) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
				else 
				{
					for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						matrize[divisorEL][SpaltenIndex] = runden(matrize[divisorEL][SpaltenIndex] / divisor);
					}
					System.out.println("Dividiere " + (divisorEL+1) + ". Zeile durch: " + divisor + "\n");
					Ausgabe(matrize);
					
					for(int spaltenEl = divisorEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						double multi = -matrize[spaltenEl][divisorEL];
						for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
						{
							double summand = matrize[divisorEL][SpaltenIndex] * multi;
							matrize[spaltenEl][SpaltenIndex] = runden(matrize[spaltenEl][SpaltenIndex] + summand);
						}
						System.out.println("Multipliziere mit: " + multi + "\nund addiere mit der " + (spaltenEl+1) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
			}
			System.out.println("Das Ergebnis ist:\n");
			Ausgabe(matrize);
			boolean loesbar = false;
			//Ueberpruefen auf Nullzeile
			for(int zeilen = 0; zeilen < matrize.length; zeilen++) 
			{
				for(int spalten = 0; spalten < matrize[0].length-1; spalten++) 
				{
					if(matrize[zeilen][spalten] == 0 && matrize[zeilen][matrize[0].length-1] != 0) 
					{
						loesbar = false;
					}
					else 
					{
						loesbar = true;
					}
				}
			}
			//Rest vom ALG
			if(var >= gleich && loesbar) 
			{
				int max2 = matrize.length+1;
				for(int einserSp = matrize.length; einserSp > 1; einserSp--) 
				{
					max2--; 
					for(int einserZL = max2; einserZL >= 2; einserZL--) 
					{
						double multi = -matrize[einserZL-2][einserSp-1];
						for(int SpaltenIndex = matrize[0].length; SpaltenIndex >= 1; SpaltenIndex--) 
						{
							double summand = matrize[einserSp-1][SpaltenIndex-1] * multi;
							matrize[einserZL-2][SpaltenIndex-1] = runden(matrize[einserZL-2][SpaltenIndex-1] + summand);
						}
						System.out.println("Multipliziere mit: " + multi + "\nund addiere mit der " + (einserZL-1) + ". Zeile\n");
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
		wert = Math.round(wert * 1000.0) / 1000.0;
		if(wert-(Math.round(wert)) <= 0.005 && wert-(Math.round(wert)) > 0 || wert-(Math.round(wert)) >= -0.005 && wert-(Math.round(wert)) < 0) 
		{
			wert = Math.round(wert);
		}
		return wert;
	}
	
	void vorbereiten(double koeff[][], JButton button) 
	{
		System.out.println(" Ihre Koeffizienten Matrix ist:\n");  
		Ausgabe(koeff);
		button.setEnabled(true);
		button.setFocusable(false);
	}
	
	void OptionPane(String nachricht, JFrame frame) 
	{
		JOptionPane pane = new JOptionPane(nachricht, JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_OPTION, null, new String[] {"OK"});
		JDialog dialog = pane.createDialog(frame, "Warnung!");
		dialog.setFocusable(true);
		dialog.setVisible(true);
	}
	
	void textChecken(String text, int laenge, JTextField eingabe, JFrame frame) 
	{
		if(laenge > 0) 
		{
			//Wenn laenge = 1 waere, dann soll es an Index 0 gucken usw. (So wird jedes neue Zeichen ueberprueft)
			char zeichen = text.charAt(laenge-1);
			//Falls zeichen nicht groesser gleich 1 und kleiner gleich 9 ist 
			if(!((zeichen >= '1') && (zeichen <= '9'))) 
			{
				OptionPane("Bitte nur Zahlen von 1 bis 9 eingeben!", frame);
				eingabe.setText("");
			}
		}
	}
}