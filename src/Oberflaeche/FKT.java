package Oberflaeche;

import java.util.Random;

//Fuer die ganzen Matrizenfunktionen
public class FKT
{
	int max = 0, max2 = 0;
	
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
	
	void gaussAlg (double[][] matrize, int var, int gleich) 
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
							double summand = matrize[erstesEL+1][SpaltenIndex2];
							matrize[spaltenEl-1][SpaltenIndex2] = runden(matrize[spaltenEl-1][SpaltenIndex2]+summand);
						}
						System.out.println("Multipliziere mit: 1" + "\nund addiere mit der " + (spaltenEl) + ". Zeile\n");
						Ausgabe(matrize);
					}
					//weil die 3te Zeile dann 1 als erstes Element hat muss man die 2 Zeile jetzt negiert draufrechnen
					for(int spaltenEl = erstesEL+1; spaltenEl < matrize.length; spaltenEl++) 
					{
						for (int SpaltenIndex2 = 0; SpaltenIndex2 < matrize[0].length; SpaltenIndex2++) 
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
			//Rest des Gauss Jordan
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
	//Probleme beim runden noch fixen
	double runden(double wert) 
	{
		wert = Math.round(wert * 1000.0) / 1000.0;
		if (wert == -0.0) 
		{
			wert = 0.0; 
		}
		return wert;
	}
}
