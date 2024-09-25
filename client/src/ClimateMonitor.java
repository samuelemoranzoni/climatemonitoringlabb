/*
Membri del gruppo:
Mohamadou Fadall Diagne 754545
Simone Zaninello 753751
Edoardo Di Tullio 753918
Samuele Moranzoni  754159
Sede: VA
*/

import java.util.*;

/**
 * Classe principale dell'applicazione.
 */
public class ClimateMonitor {
    
    /*
     * @param String[]args metodo da cui parte il programma
     */
    public static void main (String[]args)
    {
        // Creazione degli oggetti per la selezione, registrazione,inserimento e accesso
       SistemaSelezione sisSelezione = new SistemaSelezione();
      SistemaRegistrazione regis = new SistemaRegistrazione();
      SistemaAccesso sisAcc = new SistemaAccesso();
        SistemaInserimento sisIns= new SistemaInserimento();
        SistemaAggiunta sisAgg= new SistemaAggiunta();
        Scanner sc = new Scanner(System.in);
        //booleani che permettono i cicli nei vari menu e sotto menu
        boolean esecuzione=true,esecuzione2=true,esecuzione3=true;

        // Ciclo principale dell'applicazione
        while(esecuzione){
            System.out.println("\nper cercare e visualizzare i dati raccolti digitare '1' \n" +
                    "per entrare nell'area operatori digitare '2'\nper uscire digitare qualsiasi altro numero");
            System.out.println("");
            int answer = sc.nextInt();
            sc.nextLine();
            // Menu principale per cercare i dati e per entrare nell'area operatori
            switch (answer) {

                case 1:  // cerca e visualizza i dati raccolti
                    SistemaRicerca sRicerca = new SistemaRicerca();
                    Scanner sc1 = new Scanner(System.in);
                    System.out.println("cerca inserendo:\no il nome del paese\no il nome dello stato \no delle coordinate(latitudine e longitudine devono essere separate da una virgola)");
                    String cercatore= sc1.nextLine();
                    sRicerca.cercaAreaGeografica(cercatore);
                    System.out.println("\npremi 1 per selezionare l'area di interesse di cui visualizzare i dati\npremi qualsiasi altro numero per uscire");
                    int answer3 = sc.nextInt();

                    // Menu  per la selezione dei parametri
                    switch (answer3) {
                        case 1:

                            System.out.println("per confermare inserisci il nome dell'area da selezionare");
                            String nomeCercato= sc1.nextLine();
                            sisSelezione.visualizzaAreaGeografica(nomeCercato);
                            break;
                        default:
                            System.out.println("\nstai uscendo dall'area di selezione");
                            esecuzione2=false; // interrompiamo il loop
                            break;
                    }

                    break;
                case 2: //  area operatore
                    esecuzione2=true;
                    while (esecuzione2) {
                        System.out.println("benvenuto nell'area operatori\n");
                        System.out.println("per registrarsi digitare '1'\nper accedere digitare '2'\nper uscire premere qualsiasi altro numero");
                        int answer2 = sc.nextInt();//scelta sottomenu

                        // Menu per la registrazione o l'accesso
                        switch (answer2) {
                            case 1:  // effettuiamo la registrazione
                                try {
                                    regis.Registrazione();//passiamo al metodo registrazione
                                } catch (Exception e) {
                                    System.out.println("errore di registrazione");
                                }

                                break;
                            case 2:  // effettuiamo l'accesso
                                Scanner sc3 = new Scanner(System.in);
                                String login = "";
                                try {
                                    System.out.println("inserisci il userid: ");

                                    String userid = sc3.nextLine();
                                    while (userid == null || userid.equals("")) { //se non si inserisce lo userid si finisce nel ciclo
                                        System.out.println("ATTENZIONE scrivere il tuo userid");
                                        System.out.println("inserisci il userid: ");
                                        userid = sc3.nextLine();
                                    }
                                    System.out.println("inserisci la password: ");
                                    String passw = sc3.nextLine();
                                    //viene restituito "" se il login è fallito , altrimenti user id se il login è andato a buon fine
                                    login = sisAcc.accesso(userid, passw);//passiamo al metodo accesso

                                } catch (Exception e) {
                                    System.out.println("errore di accesso");
                                }
                                esecuzione3 = !login.equals(""); //manteniamo il loop attivo
                              
                                    while (esecuzione3) {
                                        System.out.println("inserisci 1 per aggiungere parametri di monitoraggio\npremi 2 per creare centri di monitoraggio\npremi 3 per inserire un'area d'interesse\npremi qualsiasi altro numero se si vuole uscire ");
                                        int answer4 = sc.nextInt();
                                        // Menu per l'aggiunta di parametri di monitoraggio, centri di monitoraggio o aree d'interesse
                                        switch (answer4) {
                                            case 1:
                                                System.out.println("stai aggiungendo dati...");
                                               // sisIns.inserisciParametriClimatici();
                                                System.out.println("Parametri aggiunti correttamente");
                                                break;
                                            case 2:
                                                System.out.println("stai aggiungendo centro...");
                                                //String nomeCentro = sisIns.insCentroAree();
                                               // sisAgg.AggiungiCentro(login, nomeCentro);
                                                System.out.println("Centro aggiunto correttamente");
                                                break;
                                            case 3:
                                                System.out.println("stai aggiungendo un'area d'interesse...");
                                              //  sisIns.insArea();
                                                System.out.println("Area d'interesse aggiunta correttamente");
                                                break;
                                            default:
                                                System.out.println("stai uscendo dall'area riservata...");
                                                esecuzione3 = false;// interrompiamo il loop
                                                break;

                                        }
                                    }
                                break;
                            default:
                                System.out.println("stai uscendo dall'area operatori...");
                                esecuzione2 = false; // interrompiamo il loop
                                break;
                        }

                    }
                    break;
                default: 
                    System.out.println("stai uscendo dall'applicazione...");
                    esecuzione=false;// interrompiamo il loop
                    break;
            }
        }
    }
}
