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
	BigDecimal[][] fuellen(int var, int gleich, int modus) 
	{
		BigDecimal[][] matrize = new BigDecimal[gleich][var+1];
	    for (int gleichungen = 0; gleichungen < matrize.length; gleichungen++) 
	    {
	        for (int variablen = 0; variablen < matrize[0].length; variablen++) 
	        {
	        	switch (modus) 
	        	{     	
	        		case 1: //Nur positive Zufallszahlen
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 9));
		        		while (matrize[gleichungen][variablen] == BigDecimal.valueOf(0))
		        		{
		        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 9));
		        		}
		        		break;
	        		case 2: //Nur negative Zufallszahlen
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 0));
		        		while (matrize[gleichungen][variablen] == BigDecimal.valueOf(0)) 
		        		{
		        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 0));
		        		}
		        		break;
	        		case 3: //Positive und negative Zufallszahlen
	        			//ThreadLocalRandom: Ein Zufallszahlengenerator, isoliert zum aktuellen Thread (Macht eig nur Sinn bei Multithreading)
	    	        	//Usages of this class should typically be of the form: ThreadLocalRandom.current().nextX(...) (where X is Int, Long, etc)
	    	        	//nextLong: Gibt einen zufälligen long-Wert zwischen dem angegebenen Ursprung (erster Para.) und der angegebenen Grenze (zweiter Para.) zurück
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 9));
		        		while (matrize[gleichungen][variablen] == BigDecimal.valueOf(0)) 
		        		{
		        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 9));
		        		}
		        		break;
	        	}
	        }
	    }  
		return matrize;
	}
	//erstmalig dient nur dafuer den "Modus" zu entscheiden. Entweder auf 3 Zahlen begrenzt oder einfach normale Ausgabe
	void ausgabe(BigDecimal[][] matrize, boolean erstmalig) 
	{	
	    for (int ausgabeG = 0; ausgabeG < matrize.length; ausgabeG++) 
		{
			for (int ausgabeV = 0; ausgabeV < matrize[0].length-1; ausgabeV++) 
			{
				if (ausgabeV < matrize[0].length-2 && erstmalig)
				{
					System.out.print(" " + matrize[ausgabeG][ausgabeV] + ",");
				}
				if (ausgabeV == matrize[0].length-2 && erstmalig) 
				{
					System.out.print(" " + matrize[ausgabeG][ausgabeV]);
					System.out.print(" | ");
					System.out.print(matrize[ausgabeG][ausgabeV+1]);
				}
				if (ausgabeV < matrize[0].length-2 && erstmalig == false) 
				{
					System.out.print(" " + matrize[ausgabeG][ausgabeV].setScale(3, RoundingMode.HALF_UP) + ",");
				}
				if (ausgabeV == matrize[0].length-2 && erstmalig == false) 
				{
					System.out.print(" " + matrize[ausgabeG][ausgabeV].setScale(3, RoundingMode.HALF_UP));
					System.out.print(" | ");
					System.out.print(matrize[ausgabeG][ausgabeV+1].setScale(3, RoundingMode.HALF_UP));
				}
			}
			System.out.print("\n");
		}
	    System.out.println(); 
	}
	
	void gaussAlg(BigDecimal[][] matrize, int var, int gleich, boolean erstmalig) 
	{
		if (var==1 && gleich==1) 
		{
			System.out.println("Das Ergebnis ist:\n");
			ausgabe(matrize, erstmalig);
		}
		else 
		{
			int max = 0;
			if (var < gleich) 
			{
				max = matrize[0].length-1;
			} 
			else 
			{
				max = matrize.length;
			}
			for (int divisorEL = 0; divisorEL < max; divisorEL++) 
			{
				BigDecimal divisor = matrize[divisorEL][divisorEL];
				//Vergleicht ein BigDecimal mit dem in der Klammer. 
				//Zwei BigDecimal-Objekte mit gleichem Wert, aber unterschiedlichem scale (z. B. 2.0 und 2.00) werden von dieser Methode als gleich angesehen
				if (divisor.setScale(3, RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(0)) == 0 && divisorEL == matrize.length-1) 
				{
					break;
				}
				else if (divisor.setScale(3, RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(0)) == 0 && divisorEL != matrize.length-1)
				{
					BigDecimal divisor2 = matrize[divisorEL+1][divisorEL];
					for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						BigDecimal dividend = matrize[divisorEL+1][SpaltenIndex];
						matrize[divisorEL+1][SpaltenIndex] = dividend.divide(divisor2, 5, RoundingMode.HALF_UP);
					}
					System.out.println("Dividiere " + (divisorEL+2) + ". Zeile durch: " + divisor2.setScale(3, RoundingMode.HALF_UP) + "\n");
					ausgabe(matrize, erstmalig);
					for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						BigDecimal summand = matrize[divisorEL+1][SpaltenIndex];
						matrize[divisorEL][SpaltenIndex] = summand.add(matrize[divisorEL][SpaltenIndex]);
					}
					System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (divisorEL+1) + ". Zeile\n");
					ausgabe(matrize, erstmalig);
					//dann wird resetet
					divisorEL--;
				}
				else 
				{
					for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
					{
						BigDecimal dividend = matrize[divisorEL][SpaltenIndex];
						matrize[divisorEL][SpaltenIndex] = dividend.divide(divisor, 5, RoundingMode.HALF_UP);
					}
					System.out.println("Dividiere " + (divisorEL+1) + ". Zeile durch: " + divisor.setScale(3, RoundingMode.HALF_UP) + "\n");
					ausgabe(matrize, erstmalig);
					
					for (int spaltenEl = divisorEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						BigDecimal multi = matrize[spaltenEl][divisorEL].negate();
						for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) 
						{
							BigDecimal summand = multi.multiply(matrize[divisorEL][SpaltenIndex]);
							matrize[spaltenEl][SpaltenIndex] = summand.add(matrize[spaltenEl][SpaltenIndex]);
						}
						System.out.println("Multipliziere mit: " + multi.setScale(3, RoundingMode.HALF_UP) + "\nund addiere mit der " + (spaltenEl+1) + ". Zeile\n");
						ausgabe(matrize, erstmalig);
					}
				}
			}
			System.out.println("Das Ergebnis ist:\n");
			ausgabe(matrize, erstmalig);
			//Ueberpruefen auf Nullzeile
			boolean loesbar = false;
			for (int zeilen = 0; zeilen < matrize.length; zeilen++) 
			{
				for (int spalten = 0; spalten < matrize[0].length-1; spalten++) 
				{
					if (matrize[zeilen][spalten] == BigDecimal.valueOf(0) && matrize[zeilen][matrize[0].length-1] != BigDecimal.valueOf(0)) 
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
			if (var >= gleich && loesbar) 
			{
				int max2 = matrize.length+1;
				for (int einserSp = matrize.length; einserSp > 1; einserSp--) 
				{
					max2--; 
					for (int einserZL = max2; einserZL >= 2; einserZL--) 
					{
						BigDecimal multi = (matrize[einserZL-2][einserSp-1]).negate();
						for (int SpaltenIndex = matrize[0].length; SpaltenIndex >= 1; SpaltenIndex--) 
						{
							BigDecimal summand = multi.multiply(matrize[einserSp-1][SpaltenIndex-1]);
							matrize[einserZL-2][SpaltenIndex-1] = summand.add(matrize[einserZL-2][SpaltenIndex-1]);
						}
						System.out.println("Multipliziere mit: " + multi.setScale(3, RoundingMode.HALF_UP) + "\nund addiere mit der " + (einserZL-1) + ". Zeile\n");
						ausgabe(matrize, erstmalig);
					}
				}		
				System.out.println("Das Ergebnis ist:\n");
				ausgabe(matrize, erstmalig); 
			}
			else 
			{
				System.out.println("Das Gleichungsystem ist unloesbar!");
			}
		}
	}
	
	void vorbereiten(BigDecimal koeff[][], JButton button, boolean erstmalig) 
	{
		System.out.println(" Ihre Koeffizienten Matrix ist:\n");  
		ausgabe(koeff, erstmalig);
		button.setEnabled(true);
		button.setFocusable(false);
	}
	
	void optionPane(String nachricht, JFrame frame) 
	{
		JOptionPane pane = new JOptionPane(nachricht, JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_OPTION, null, new String[] {"OK"});
		JDialog dialog = pane.createDialog(frame, "Warnung!");
		dialog.setFocusable(true);
		dialog.setVisible(true);
	}
	
	void textChecken(String text, int laenge, JTextField eingabe, JFrame frame) 
	{
		if (laenge > 0) 
		{
			//Wenn laenge = 1 waere, dann soll es an Index 0 gucken usw. (So wird jedes neue Zeichen ueberprueft)
			char zeichen = text.charAt(laenge-1);
			if (!((zeichen >= '1') && (zeichen <= '9'))) 
			{
				optionPane("Bitte nur Zahlen von 1 bis 9 eingeben!", frame);
				eingabe.setText("");
			}
		}
	}
}