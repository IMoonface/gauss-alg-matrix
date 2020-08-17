package Oberflaeche;

import java.util.Random;

//Für die ganzen Matrizenfunktionen (klappt aber leider nicht ich kann die in ner anderen Klasse nicht aufrufen...)
public class FKT extends GUI 
{
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
				for(int erstesEL = 0; erstesEL < matrize[0].length-1; erstesEL++) 
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
			else 
			{
				for(int erstesEL = 0; erstesEL < matrize.length; erstesEL++) 
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
	}
	
	double runden(double wert, int nachkommastellen)
	{
		double zahl = Math.pow(10, nachkommastellen);
		return Math.round(wert*zahl)/zahl;
	}
}
