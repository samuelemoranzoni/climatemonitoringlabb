import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessoFrame extends JFrame {
    public String id_utente;
    public AccessoFrame() {
        super("login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);

        // Pannello per l'inserimento dell'ID e della password
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 2));
        JLabel id = new JLabel("inserisci id");
        JTextField idf = new JTextField();
        idf.setPreferredSize(new Dimension(30, 30));
        JLabel pass = new JLabel("password :");
        JPasswordField jpas = new JPasswordField();
        jpas.setPreferredSize(new Dimension(30, 30));
        panel1.add(id);
        panel1.add(idf);
        panel1.add(pass);
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

        // Listener per il pulsante di login
        buttlogin.addActionListener(new ActionListener() {
            public String id; //serve per salvare un riferimento dell' id utente dell'utente che accede

            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupera l'ID e la password dai campi di testo
                String idc = idf.getText();
                String passwordc = new String(jpas.getPassword());

                this.id=idc;

                // Crea un'istanza di SistemaAccesso
                SistemaAccesso sistemaAccesso = new SistemaAccesso();

                // Chiama il metodo accesso per verificare le credenziali
                String result = sistemaAccesso.accesso(idc, passwordc);

                // Mostra un messaggio in base al risultato
                if (!result.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Login effettuato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    new AreaRiservataOperatorFrame();
                } else {
                    JOptionPane.showMessageDialog(null, "Userid o password errati. Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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



    public static void main(String[] args) {
        AccessoFrame ac = new AccessoFrame();
    }
}