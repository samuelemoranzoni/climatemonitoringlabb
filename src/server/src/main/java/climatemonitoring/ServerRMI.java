package climatemonitoring;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class ServerRMI {
    static DatabaseConnection dc = null;

    /**
     * La classe climatemonitoring.ServerRMI avvia un server RMI (Remote Method Invocation)
     * per consentire a client remoti di invocare metodi su un oggetto remoto.
     *
     * @author Moranzoni Samuele
     * @author Di Tullio Edoardo
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        try {
            dc=new DatabaseConnection();
            String dbPassword=null;
            String dbHost=null;
            String dbUser=null;
            String nome_database=null;
            int tentativi = 3;
            boolean running=true;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Benvenuto al server di Climate Monitoring , prima di poter accedere alle funzionalità è necessario fornire le credenziali del database...");
            while (tentativi > 0) {

                System.out.println("Inserisci l'host del database (es. localhost:5432):");
                dbHost = scanner.nextLine().trim();
                System.out.println("Inserisci lo user admin del database");
                dbUser= scanner.nextLine().trim();
                System.out.println("Inserisci la password del database:");
                dbPassword = scanner.nextLine().trim();
                System.out.println("Inserisci il nome del database");
                nome_database= scanner.nextLine();

                if (dc.checkcredentials(dbHost,dbUser,dbPassword,nome_database)) {
                    System.out.println("Credenziali corrette , accesso in corso ...");
                    break;

                } else {
                        tentativi = tentativi-1;
                        System.out.println("Credenziali errate: ti rimangono " + tentativi + " tentativi.");
                    }

                if(tentativi==0) {
                    System.out.println("hai esaurito il numero di tentativi consentiti , la comunicazione sarà interrota...");
                    System.exit(1);
                }
                }
            // L'oggetto usato dal server per connettersi al database si connette

            dc.connetti(dbHost,dbUser,dbPassword,nome_database);
            // Crea l'oggetto remoto
            RemoteServiceImpl remoteService = new RemoteServiceImpl(dc);

            // Crea il registro RMI sulla porta 1099
            LocateRegistry.createRegistry(1099);

            // Registra l'oggetto remoto nel registro
            Naming.rebind("rmi://localhost/climatemonitoring.RemoteService", remoteService);

            System.out.println("Server RMI avviato e in ascolto..." +
                            "se vuoi interrompere la connessione scrivi 'stop' nella prossima riga ");

            // Leggi i messaggi dell'utente e ferma il server se viene ricevuto "stop"
            while (running) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("stop")) {
                    running = false;
                    System.out.println("Arresto del server in corso...");
                    System.exit(1);
                }
            }

        } catch (Exception e) {
            System.err.println("Errore nell'avvio del server: " + e.getMessage());
            e.printStackTrace();
        }
        finally{
            dc.disconnetti();
        }
    }
}
