import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerRMI {
    /**
     * La classe ServerRMI avvia un server RMI (Remote Method Invocation)
     * per consentire a client remoti di invocare metodi su un oggetto remoto.
     *  @author Moranzoni Samuele
     *  @author Di Tullio Edoardo
     */
    public static void main(String[] args) {

        try {

                // Crea il database
                DatabaseConnection db = new DatabaseConnection();

                // Crea l'oggetto remoto
                RemoteServiceImpl remoteService = new RemoteServiceImpl(db);

                // Crea il registro RMI sulla porta 1099
                LocateRegistry.createRegistry(1099);

                // Registra l'oggetto remoto nel registro
                Naming.rebind("rmi://localhost/RemoteService", remoteService);

                System.out.println("Server RMI avviato e in ascolto...");

            } catch(Exception e){
                System.err.println("Errore nell'avvio del server: " + e.getMessage());
                e.printStackTrace();
            }

    }
}
