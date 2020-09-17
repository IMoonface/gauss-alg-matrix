package Oberflaeche;

import java.io.OutputStream;
import javax.swing.JTextArea;

//OutputStream: Diese abstrakte Klasse ist die Oberklasse aller Klassen, die einen Ausgabestrom von Bytes darstellen
public class TextAreaOutputStream extends OutputStream {
    JTextArea end; 
    //Erstellt eine neue Instanz von TextAreaOutputStream, welche in die angegebene JTextArea schreibt.
    //control: Name der JTextArea an das die Ausgabe umgeleitet wird.
    public TextAreaOutputStream(JTextArea zwischen) {
    	//Wird an die lokale JTextArea uebergeben, damit die in write genutzt werden kann
    	end = zwischen;
    }
    //write: Schreibt das angegebene Byte in diesen Ausgabestrom (TextAreaOutputStream)
    //Klassen, die eine Unterklasse von OutputStream sind müssen, mindestens die Methode "write" bereitstellen, die einen Byte "Ausgabe" schreibt (z.B.: ein H).
    public void write(int b) {
        //append: Haengt das in der Klammer ans Ende der TextArea dran 
    	//String.valueOf(char c) erwartet einen char, deshalb der cast, sonst wuerde eine andere Methode genommen werden
    	end.append(String.valueOf((char)b));
    }  
}