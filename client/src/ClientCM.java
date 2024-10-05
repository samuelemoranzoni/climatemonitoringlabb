import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientCM extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private  DatabaseConnection dc;

    ClientCM() throws IOException {
        socket = new Socket("localhost", 8080); // Replace with your server's IP and port
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connected to server");

    }

   public Object readObject() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    public void writeObject(Object o) throws IOException {

        out.writeObject(o);
    }

    public void close() throws IOException {
        socket.close();
    }

    public OperatoreRegistrato registrati(String nome, String cognome, String codiceFiscale, String email, String username, String password, Integer idMonitoraggio) throws IOException, ClassNotFoundException {
        // Invia i dati di registrazione al server
        this.writeObject("Registrazione");
        this.writeObject(nome);
        this.writeObject(cognome);
        this.writeObject(codiceFiscale);
        this.writeObject(email);
        this.writeObject(username);
        this.writeObject(password);
        this.writeObject(idMonitoraggio);

        // Ricezione dell'oggetto dal server
        OperatoreRegistrato op = null;
        try {
            // Assicuriamoci che il server risponda prima di provare a leggere
            op = (OperatoreRegistrato) in.readObject();
        } catch (EOFException e) {
            System.out.println("Errore: il server ha chiuso la connessione prima di inviare una risposta.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore durante la lettura dell'oggetto dal server: " + e.getMessage());
        }
        return op;
    }


    public void login(String user , String password) throws IOException {
      this.writeObject("Login");
      this.writeObject(user);
      this.writeObject(password);
    }

}
