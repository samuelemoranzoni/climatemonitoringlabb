/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

import java.io.*;
import java.util.Scanner;

/**
 * Classe SistemaAccesso per gestire l'accesso degli utenti.
 */
public class SistemaAccesso {

    /**
     * Metodo per l'accesso degli utenti.
     *
     * @param userid L'ID dell'utente.
     * @param password La password dell'utente.
     * @return L'ID dell'utente se l'accesso è riuscito, altrimenti una stringa vuota.
     */
    public String accesso(String userid, String password) {
        // File contenente le credenziali degli operatori registrati
        File file = new File("data\\OperatoriRegistrati.dati.txt");
        
        try (Scanner scanner = new Scanner(file)) {
            // Scansione del file per trovare le credenziali corrispondenti
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(" ");
                
                // Controllo se le credenziali corrispondono
                if (credentials.length >= 6) {
                    if (credentials[4].equals(userid) && credentials[5].equals(password)) {
                        System.out.println("Accesso riuscito!");
                        return userid;
                    }
                } else {
                    System.out.println("Formato della riga non valido: " + line);
                }
            }
            // Messaggio di errore se le credenziali non sono valide
            System.out.println("Accesso fallito. Userid o password non validi.");
        } catch (FileNotFoundException e) {
            // Messaggio di errore se il file non viene trovato
            System.out.println("Il file non è stato trovato.");
            return "";
        }
        return "";
    }
}

