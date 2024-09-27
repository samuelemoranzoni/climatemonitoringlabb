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

}
