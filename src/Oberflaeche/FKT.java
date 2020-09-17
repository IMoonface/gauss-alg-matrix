package Oberflaeche;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FKT {	
	BigDecimal[][] fuellen(int var, int gleich, int modus) {
		BigDecimal[][] matrize = new BigDecimal[gleich][var+1];
	    for (int gleichungen = 0; gleichungen < matrize.length; gleichungen++) {
	        for (int variablen = 0; variablen < matrize[0].length; variablen++) {
	        	switch (modus) {     	
	        		case 1: //Nur positive Zufallszahlen
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(1, 10));
		        		break;
	        		case 2: //Nur negative Zufallszahlen
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 0));
		        		break;
	        		case 3: //Positive und negative Zufallszahlen
	        			//ThreadLocalRandom: Ein Zufallszahlengenerator, isoliert zum aktuellen Thread (Macht eig nur Sinn bei Multithreading)
	    	        	//Usages of this class should typically be of the form: ThreadLocalRandom.current().nextX(...) (where X is Int, Long, etc)
	    	        	//nextLong: Gibt einen zufälligen long-Wert zwischen dem angegebenen Ursprung (erster Para.) und der angegebenen Grenze (zweiter Para.) zurück
	        			//Hinweis: der 2te Wert wird nie erreicht!!!!
	        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 10));
	        			//Um die 0 zu verhindern
		        		while (matrize[gleichungen][variablen] == BigDecimal.valueOf(0)) {
		        			matrize[gleichungen][variablen] = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(-9, 10));
		        		}
		        		break;
	        	}
	        }
	    }  
		return matrize;
	}
	//erstmalig dient nur dafuer den "Modus" zu entscheiden. Entweder auf 3 Zahlen begrenzt oder einfach normale Ausgabe
	void ausgabe(BigDecimal[][] matrize, boolean erstmalig) {	
	    for (int ausgabeG = 0; ausgabeG < matrize.length; ausgabeG++) {
			for (int ausgabeV = 0; ausgabeV < matrize[0].length-1; ausgabeV++) {
				if (ausgabeV < matrize[0].length-2 && erstmalig) {
					System.out.print(" " + matrize[ausgabeG][ausgabeV] + ",");
				}
				if (ausgabeV == matrize[0].length-2 && erstmalig) {
					System.out.print(" " + matrize[ausgabeG][ausgabeV]);
					System.out.print(" | ");
					System.out.print(matrize[ausgabeG][ausgabeV+1]);
				}
				if (ausgabeV < matrize[0].length-2 && erstmalig == false) {
					System.out.print(" " + matrize[ausgabeG][ausgabeV].setScale(3, RoundingMode.HALF_UP) + ",");
				}
				if (ausgabeV == matrize[0].length-2 && erstmalig == false) {
					System.out.print(" " + matrize[ausgabeG][ausgabeV].setScale(3, RoundingMode.HALF_UP));
					System.out.print(" | ");
					System.out.print(matrize[ausgabeG][ausgabeV+1].setScale(3, RoundingMode.HALF_UP));
				}
			}
			System.out.print("\n");
		}
	    System.out.println(); 
	}
	
	void gaussAlg(BigDecimal[][] matrize, int var, int gleich, boolean erstmalig) {
		int max = 0;
		if (var < gleich) {
			max = matrize[0].length-1;
		} 
		else {
			max = matrize.length;
		}
		for (int divisorEL = 0; divisorEL < max; divisorEL++) {
			BigDecimal divisor = matrize[divisorEL][divisorEL];
			//Vergleicht ein BigDecimal mit dem in der Klammer. 
			//Zwei BigDecimal-Objekte mit gleichem Wert, aber unterschiedlichem scale (z. B. 2.0 und 2.00) werden von dieser Methode als gleich angesehen
			if (divisor.setScale(3, RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(0)) == 0 && divisorEL == matrize.length-1) {
				break;
			}
			if (divisor.setScale(3, RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(0)) == 0 && divisorEL != matrize.length-1) {
				BigDecimal divisor2 = matrize[divisorEL+1][divisorEL];
				for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) {
					matrize[divisorEL+1][SpaltenIndex] = matrize[divisorEL+1][SpaltenIndex].divide(divisor2, 6, RoundingMode.HALF_UP);
				}
				System.out.println("Dividiere " + (divisorEL+2) + ". Zeile durch: " + divisor2.setScale(3, RoundingMode.HALF_UP) + "\n");
				ausgabe(matrize, erstmalig);
				for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) {
					matrize[divisorEL][SpaltenIndex] = matrize[divisorEL+1][SpaltenIndex].add(matrize[divisorEL][SpaltenIndex]);
				}
				System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (divisorEL+1) + ". Zeile\n");
				ausgabe(matrize, erstmalig);
				//dann wird resetet
				divisorEL--;
			}
			else {
				for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) {
					matrize[divisorEL][SpaltenIndex] = matrize[divisorEL][SpaltenIndex].divide(divisor, 6, RoundingMode.HALF_UP);
				}
				System.out.println("Dividiere " + (divisorEL+1) + ". Zeile durch: " + divisor.setScale(3, RoundingMode.HALF_UP) + "\n");
				ausgabe(matrize, erstmalig);
				for (int spaltenEl = divisorEL+1; spaltenEl < matrize.length; spaltenEl++) {
					BigDecimal multi = matrize[spaltenEl][divisorEL].negate();
					for (int SpaltenIndex = 0; SpaltenIndex < matrize[0].length; SpaltenIndex++) {
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
		boolean loesbar = nullzeile(matrize);
		if (var >= gleich && loesbar) {
			//Rest vom ALG
			restGaus(matrize, var, gleich, loesbar, erstmalig);
		}
		else {
			System.out.println("Das Gleichungsystem ist unloesbar!");
		}
	}
	
	boolean nullzeile(BigDecimal[][] matrize) {
		boolean loesbar = false;
		for (int zeilen = 0; zeilen < matrize.length; zeilen++) {
			for (int spalten = 0; spalten < matrize[0].length-1; spalten++) {
				if (matrize[zeilen][spalten] == BigDecimal.valueOf(0) && matrize[zeilen][matrize[0].length-1] != BigDecimal.valueOf(0)) {
					loesbar = false;
				}
				else {
					loesbar = true;
				}
			}
		}
		return loesbar;
	}
	
	void restGaus(BigDecimal[][] matrize, int var, int gleich, boolean loesbar, boolean erstmalig) {
		int max2 = matrize.length;
		for (int einserSp = matrize.length; einserSp > 1; einserSp--) {
			for (int einserZL = max2; einserZL >= 2; einserZL--) {
				BigDecimal multi = (matrize[einserZL-2][einserSp-1]).negate();
				for (int SpaltenIndex = matrize[0].length; SpaltenIndex >= 1; SpaltenIndex--) {
					BigDecimal summand = multi.multiply(matrize[einserSp-1][SpaltenIndex-1]);
					matrize[einserZL-2][SpaltenIndex-1] = summand.add(matrize[einserZL-2][SpaltenIndex-1]);
				}
				System.out.println("Multipliziere mit: " + multi.setScale(3, RoundingMode.HALF_UP) + "\nund addiere mit der " + (einserZL-1) + ". Zeile\n");
				ausgabe(matrize, erstmalig);
			}
			max2--; 
		}		
		System.out.println("Die Ergebnisse sind:\n");
		ausgabe(matrize, erstmalig); 
	}
	
	void einerMatrix(BigDecimal matrize[][], boolean erstmalig) {
		BigDecimal divisor = matrize[0][0];
		for(int spalten = 0; spalten < matrize[0].length; spalten++) {
			matrize[0][spalten] = matrize[0][spalten].divide(divisor, 6, RoundingMode.HALF_UP);
		}
		System.out.println("Das Ergebnis ist:\n");
		ausgabe(matrize, erstmalig);
	}
		
	void vorbereiten(BigDecimal matrize[][], JButton button, boolean erstmalig) {
		System.out.println(" Ihre Koeffizienten Matrix ist:\n");  
		ausgabe(matrize, erstmalig);
		button.setEnabled(true);
		button.setFocusable(false);
	}
	
	void optionPane(String nachricht, JFrame frame) {
		JOptionPane pane = new JOptionPane(nachricht, JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_OPTION, null, new String[] {"OK"});
		JDialog dialog = pane.createDialog(frame, "Warnung!");
		dialog.setFocusable(true);
		dialog.setVisible(true);
	}
	
	void textChecken(String text, int laenge, JTextField eingabe, JFrame frame) {
		if (laenge > 0) {
			//Wenn laenge = 1 waere, dann soll es an Index 0 gucken usw. (So wird jedes neue Zeichen ueberprueft)
			if (!(text.charAt(laenge-1) >= '1' && text.charAt(laenge-1) <= '9')) {
				optionPane("Bitte nur Zahlen von 1 bis 9 eingeben!", frame);
				eingabe.setText("");
			}
		}
	}
}