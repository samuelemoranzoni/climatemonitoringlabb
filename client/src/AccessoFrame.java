import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AccessoFrame extends JFrame {
    /*
    public String id_utente;
    public ClientCM cm;
    public AccessoFrame() throws IOException {
        super("login");
        cm=new ClientCM();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);

        // Pannello per l'inserimento dell'ID e della password
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 2));
        JLabel id = new JLabel("inserisci id");
        JTextField idfield = new JTextField();
        idfield.setPreferredSize(new Dimension(30, 30));
        JLabel password = new JLabel("password :");
        JPasswordField jpas = new JPasswordField();
        jpas.setPreferredSize(new Dimension(30, 30));
        panel1.add(id);
        panel1.add(idf);
        panel1.add(password);
        panel1.add(jpas);

        // Pannello per il pulsante di login
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centro con margine tra i bottoni

        JButton buttlogin = new JButton("Login");
        buttlogin.setPreferredSize(new Dimension(100, 30)); // Dimensioni sufficienti per essere visibili

        JButton buttback = new JButton("Indietro");
        buttback.setPreferredSize(new Dimension(100, 30)); // Dimensioni sufficienti per essere visibili

        panel2.add(buttlogin);
        panel2.add(buttback);
        // Aggiungi un bordo vuoto attorno al pannello 2
        panel2.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Imposta il bottone di login con un listener
        buttlogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        try {
            // Recupera le credenziali inserite dall'utente
            String id =
            String password = new String(.getPassword());

            // Controlla se i campi sono vuoti
            if (email.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Inserisci email e password!");
                return;
            }

            // Connessione al server RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Assicurati che l'indirizzo e la porta siano corretti
            RemoteService stub = (RemoteService) registry.lookup("RemoteService");

            // Chiamata al metodo remoto loginOperatore
            OperatoreRegistrato operatore = stub.loginOperatore(email, password);

            // Verifica se il login è riuscito
            if (operatore != null && operatore.getId() > 0) {
                statusLabel.setText("Login riuscito! Benvenuto " + operatore.getId());
                JOptionPane.showMessageDialog(this, "Login avvenuto con successo! Benvenuto, " + operatore.getId());
                // Esegui altre operazioni dopo il login...
            } else {
                statusLabel.setText("Credenziali non valide.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Errore di connessione.");
        }
    }

       buttback.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               dispose();
           }
       });
        // Configura il layout del frame
        this.setLayout(new BorderLayout());
        add(panel1, BorderLayout.PAGE_START);
        add(panel2, BorderLayout.CENTER);

        setVisible(true);
    }



    public static void main(String[] args) throws IOException {
        AccessoFrame ac = new AccessoFrame();
    }
}
*/
}