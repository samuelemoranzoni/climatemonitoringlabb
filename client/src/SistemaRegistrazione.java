/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

import java.io.FileWriter;
import java.util.Scanner;
/**
 * classe per la gestione delle registrazioni degli operatori
 */

public class SistemaRegistrazione {

/**
 * Questo metodo permette la registrazione di un nuovo operatore.
 * L'operatore inserisce i propri dati tramite input da console.
 * I dati vengono quindi salvati in un file di testo.
 */
public static void Registrazione() {
    // Percorso del file in cui vengono salvati gli operatori registrati
    String pathreg = "data\\OperatoriRegistrati.dati.txt";
    
    // Creazione di un oggetto Scanner per la lettura dell'input
    Scanner sc = new Scanner(System.in);
    
    // Variabile per controllare se la registrazione è stata completata
    boolean risposta = false;

    // Ciclo finché la registrazione non è completata
    while (risposta != true) {
        // Inserimento del nome
        System.out.println("inserisci il nome: ");
        String nome = sc.nextLine();
        // Controllo dell'input del nome
        if (nome == null || nome == "") {
            boolean nometrovato = false;
            while (nometrovato == false) {
                System.out.println("ATTENZIONE scrivere il tuo nome");
                System.out.println("inserisci il nome: ");
                nome = sc.nextLine();
                if (nome == null || nome == "") {
                } else {
                    nometrovato = true;
                }
            }
        }

        // Inserimento del cognome
        System.out.println("inserisci il cognome: ");
        String cognome = sc.nextLine();
        // Controllo dell'input del cognome
        if (cognome == null || cognome == "") {
            boolean cognometrovato = false;
            while (cognometrovato == false) {
                System.out.println("ATTENZIONE scrivere il tuo cognome");
                System.out.println("inserisci il cognome: ");
                cognome = sc.nextLine();
                if (cognome == null || cognome == "") {
                } else {
                    cognometrovato = true;
                }
            }
        }

        // Inserimento dell'email
        System.out.println("inserisci l'email: ");  
        String email = sc.nextLine();
        email = email.trim();
        // Controllo dell'input dell'email
        int posemail = email.indexOf("@");
        if (posemail == -1) {
            while (posemail == -1) {
                System.out.println("ATTENZIONE nel campo 'email' ci deve essere una @");
                System.out.println("inserisci l'email:");
                email = sc.nextLine();
                posemail = email.indexOf("@");
            }
        }

        // Inserimento del codice fiscale
        System.out.println("inserisci il codice fiscale: " + " (16 cifre)");
        String cod_fisc = sc.nextLine();
        // Controllo dell'input del codice fiscale
        if (cod_fisc.length() != 16) {
            boolean codicefoscaletrovato = false;
            while (codicefoscaletrovato == false) {
                System.out.println("ATTENZIONE il codice fiscale deve essere di 16 cifre");
                System.out.println("inserisci il codice fiscale: " + " (16 cifre)");
                cod_fisc = sc.nextLine();
                if (cod_fisc.length() != 16) {
                } else {
                    codicefoscaletrovato = true;
                }
            }
        }

        // Inserimento dell'userid
        System.out.println("inserisci il userid: ");
        String userid = sc.nextLine();
        // Controllo dell'input dell'userid
        if (userid == null || userid == "") {
            boolean useridtrovato = false;
            while (useridtrovato == false) {
                System.out.println("ATTENZIONE scrivere il tuo userid");
                System.out.println("inserisci il userid: ");
                userid = sc.nextLine();
                if (userid == null || userid == "") {
                } else {
                    useridtrovato = true;
                }
            }
        }

        // Inserimento della password
        System.out.println("inserisci la password: ");
        String psw = sc.nextLine();

        // Inserimento del centro di monitoraggio
        System.out.println("inserisci il centro di monitoraggio se disponibile: ");
        String centroMonitor = sc.nextLine();
        
        // Creazione di un nuovo operatore con i dati inseriti
        Operatore Operatore = new Operatore(nome, cognome, email, cod_fisc, userid, psw, centroMonitor);
        
        // Conferma dei dati inseriti
        System.out.println("i dati inseriti sono giusti? (rispondere con si o no) ");
        String answer = sc.nextLine();
        System.out.println("hai risposto : " + answer);

        // Se i dati non sono corretti, l'operatore viene cancellato e la registrazione riparte
        if (answer.equals("no")) {
            Operatore = null;
            risposta = false;
        } else {
            // Se i dati sono corretti, l'operatore viene registrato
            System.out.println("l'utente è stato registrato con successo : " + Operatore.toString());
            String user = Operatore.toString();

            // I dati dell'operatore vengono salvati in un file di testo
            try {
                FileWriter writer = new FileWriter(pathreg, true);
                writer.append(user + "\n");
                writer.close();
            } catch (Exception e) {
                System.out.println("errore di registrazione");
            }

            // La registrazione è completata
            risposta = true;
        }
    }
}

}
