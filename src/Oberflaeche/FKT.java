package Oberflaeche;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FKT
{	
	BigDecimal zero = new BigDecimal(0);
	
	BigDecimal[][] Fuellen(int var, int gleich, int modus) 
	{
		BigDecimal[][] matrize = new BigDecimal[gleich][var+1];
	    for(int gleichungen = 0; gleichungen < matrize.length; gleichungen++) 
	    {
	        for(int variablen = 0; variablen < matrize[0].length; variablen++) 
	        {
	        	switch(modus) 
	        	{     	
	        		case 1: //Nur positive Zufallszahlen
	        			//Uebersetzt einen long-Wert in ein BigDecimal-Objekt mit einer scale von Null (also 0 Stellen hinterm Komma)
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 9));
	        			//Vergleicht das erste BigDecimal-Onjekt mit dem in der Klammer
	        			//Zwei BigDecimal-Objekte mit gleichem Wert, aber unterschiedlichem scale (z. B. 2.0 und 2.00) werden bei dieser Methode als gleich angesehen.
		        		while(matrize[gleichungen][variablen].compareTo(zero) == 0)
		        		{
		        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 9));
		        		}
		        		break;
	        		case 2: //Nur negative Zufallszahlen
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 0));
		        		while(matrize[gleichungen][variablen].compareTo(zero) == 0) 
		        		{
		        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 0));
		        		}
		        		break;
	        		case 3: //Positive und negative Zufallszahlen
	        			//ThreadLocalRandom: Ein Zufallszahlengenerator, isoliert zum aktuellen Thread (Macht eig nur Sinn bei Multithreading)
	    	        	//Usages of this class should typically be of the form: ThreadLocalRandom.current().nextX(...) (where X is Int, Long, etc)
	    	        	//nextLong: Gibt einen zufälligen long-Wert zwischen dem angegebenen Ursprung (erster Para.) und der angegebenen Grenze (zweiter Para.) zurück
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 9));
		        		while(matrize[gleichungen][variablen].compareTo(zero) == 0) 
		        		{
		        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 9));
		        		}
		        		break;
	        	}
	        }
	    }  
		return matrize;
	}
	
	void Ausgabe(BigDecimal[][] matrize) 
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
	
	void gaussAlg(BigDecimal[][] matrize, int var, int gleich) 
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
				BigDecimal divisor = matrize[divisorEL][divisorEL];
				//Falls der divisor eine 0 ist
				if(divisor.compareTo(zero) == 0)
				{
					BigDecimal divisor2 = matrize[divisorEL+1][divisorEL];
					for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						BigDecimal dividend = matrize[divisorEL+1][SpaltenIndex];
						matrize[divisorEL+1][SpaltenIndex] = dividend.divide(divisor2, 3, RoundingMode.HALF_UP);
					}
					System.out.println("Dividiere " + (divisorEL+2) + ". Zeile durch: " + divisor2 + "\n");
					Ausgabe(matrize);
					for(int spaltenEl = divisorEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
						{
							BigDecimal summand = matrize[divisorEL+1][SpaltenIndex];
							//add: Addiert die Zahl in der Klammer drauf
							//Muss man in einer eigenen Zeile machen sonst passiert nix
							BigDecimal summandEnd = summand.add(matrize[spaltenEl-1][SpaltenIndex]);
							matrize[spaltenEl-1][SpaltenIndex] = summandEnd.setScale(3, RoundingMode.HALF_UP);
						}
						System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (spaltenEl) + ". Zeile\n");
						Ausgabe(matrize);
					}
					//weil die 3te Zeile 1 als erstes Element hat muss man die 2 Zeile jetzt negiert draufrechnen
					for(int spaltenEl = divisorEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
						{
							BigDecimal summand = matrize[divisorEL][SpaltenIndex];
							//negate: negiert die Zahl in der Klammer
							BigDecimal summandNeg = summand.negate();
							BigDecimal summandEnd = summandNeg.add(matrize[spaltenEl][SpaltenIndex]);
							matrize[spaltenEl][SpaltenIndex] = summandEnd.setScale(3, RoundingMode.HALF_UP);
						}
						System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (spaltenEl+1) + ". Zeile\n");
						Ausgabe(matrize);
					}
				}
				else 
				{
					for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						BigDecimal dividend = matrize[divisorEL][SpaltenIndex];
						//divide: Dividiert die Zahl in der Klammer
						matrize[divisorEL][SpaltenIndex] = dividend.divide(divisor, 3, RoundingMode.HALF_UP);
					}
					System.out.println("Dividiere " + (divisorEL+1) + ". Zeile durch: " + divisor + "\n");
					Ausgabe(matrize);
					
					for(int spaltenEl = divisorEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						BigDecimal multi = matrize[spaltenEl][divisorEL];
						BigDecimal multiNeg = multi.negate();
						for(int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
						{
							BigDecimal summand = multiNeg.multiply(matrize[divisorEL][SpaltenIndex]);
							BigDecimal summandEnd = summand.add(matrize[spaltenEl][SpaltenIndex]);
							matrize[spaltenEl][SpaltenIndex] = summandEnd.setScale(3, RoundingMode.HALF_UP);
						}
						System.out.println("Multipliziere mit: " + multiNeg + "\nund addiere mit der " + (spaltenEl+1) + ". Zeile\n");
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
					if(matrize[zeilen][spalten].compareTo(zero) == 0) 
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
						BigDecimal multi = matrize[einserZL-2][einserSp-1];
						BigDecimal multiNeg = multi.negate();
						for(int SpaltenIndex = matrize[0].length; SpaltenIndex >= 1; SpaltenIndex--) 
						{
							BigDecimal summand = multiNeg.multiply(matrize[einserSp-1][SpaltenIndex-1]);
							BigDecimal summandEnd = summand.add(matrize[einserZL-2][SpaltenIndex-1]);
							matrize[einserZL-2][SpaltenIndex-1] = summandEnd.setScale(3, RoundingMode.HALF_UP);
						}
						System.out.println("Multipliziere mit: " + multiNeg + "\nund addiere mit der " + (einserZL-1) + ". Zeile\n");
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
	
	void vorbereiten(BigDecimal koeff[][], JButton button) 
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