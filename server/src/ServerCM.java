import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCM {
    static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ServerSocket server = new ServerSocket(PORT);
        DatabaseConnection database = new DatabaseConnection();
        try {
            while(true) {
                Socket clientSocket = server.accept();
                new Skeleton(clientSocket, database);
            }
        }finally {
            server.close();
        }

    }

}

