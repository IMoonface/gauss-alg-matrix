package Oberflaeche;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;


//OutputStream: Diese abstrakte Klasse ist die Oberklasse aller Klassen, die einen Ausgabestrom von Bytes darstellen
public class TextAreaOutputStream extends OutputStream 
{
    JTextArea end;
    
    //Erstellt eine neue Instanz von TextAreaOutputStream, welche in die angegebene JTextArea schreibt.
    //control: Name der JTextArea an das die Ausgabe umgeleitet wird.
    public TextAreaOutputStream (JTextArea zwischen) 
    {
    	//Wird an die lokale JTextArea übergeben, damit die in write genutzt werden kann
    	end = zwischen;
    }
    //Applications that need to define a subclass of OutputStream must always provide at least a method that writes one byte of output.
    //Schreibt das angegebene Byte in einen Ausgabestream (TextAreaOutputStream)
    public void write (int b) throws IOException 
    {
        //Haengt das in der Klammer ans Ende der TextArea dran 
    	//String.valueOf(char c) erwartet einen char, deshalb der cast, sonst würde eine andere Methode genommen werden
    	//https://de.wikibooks.org/wiki/Java_Standard:_Primitive_Datentypen
    	end.append(String.valueOf((char)b));
    }  
}