import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RegisterFrame extends JFrame {

    private JTextField nomeField, cognomeField, emailField, codiceFiscaleField, useridField, centroMonitoraggioField;
    private JPasswordField passwordField;

    ClientCM client;

    public RegisterFrame() {
        setTitle("Registrazione Operatore");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        JPanel panel1=new JPanel();

        panel1.setLayout(new GridLayout(8, 2));

        // Creazione delle etichette e dei campi di testo
        panel1.add(new JLabel("Nome:*"));
        nomeField = new JTextField();
        panel1.add(nomeField);

        panel1.add(new JLabel("Cognome:*"));
        cognomeField = new JTextField();
        panel1.add(cognomeField);

        panel1.add(new JLabel("Codice Fiscale:*"));
        codiceFiscaleField = new JTextField();
        panel1.add(codiceFiscaleField);

        panel1.add(new JLabel("Email:*"));
        emailField = new JTextField();
        panel1.add(emailField);

        panel1.add(new JLabel("UserID:*"));
        useridField = new JTextField();
        panel1.add(useridField);

        panel1.add(new JLabel("Password:*"));
        passwordField = new JPasswordField();
        panel1.add(passwordField);

        panel1.add(new JLabel("Centro di Monitoraggio:"));
        centroMonitoraggioField = new JTextField();
        panel1.add(centroMonitoraggioField);

        // Bottone per confermare la registrazione
        JButton registerButton = new JButton("Registrati");
        panel1.add(registerButton);

        // Listener per il bottone di registrazione
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //registrazioneOperatore();
                try {

                    client=new ClientCM();
                    client.writeObject("Registrazione");
                    client.writeObject(nomeField.getText().trim());
                    client.writeObject( cognomeField.getText().trim());
                    client.writeObject(codiceFiscaleField.getText().trim());
                    client.writeObject(emailField.getText().trim());
                    client.writeObject(useridField.getText().trim());
                    client.writeObject(passwordField.getPassword());
                    client.writeObject(centroMonitoraggioField.getText());
                    int risposta=(int)client.readObject();
                    if(risposta > 0){

                        System.out.println("registrazione effettuata");
                    }
                    client.close();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


                new AccessoFrame();
            }
        });

        // Bottone per tornare indietro
        JButton backButton = new JButton("Torna Indietro");
        panel1.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Chiude la finestra corrente
            }
        });

        JPanel panel2=new JPanel();
        panel2.setLayout(new FlowLayout());
        JLabel obbligatoriLabel = new JLabel("I campi contrassegnati da \"*\" sono da compilare obbligatoriamente.");
        obbligatoriLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        panel2.add(obbligatoriLabel);
        add(panel1,BorderLayout.NORTH);
        add(panel2,BorderLayout.PAGE_END);
        setVisible(true);
    }

    private void registrazioneOperatore() {
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        String codiceFiscale = codiceFiscaleField.getText().trim();
        String email = emailField.getText().trim();
        String userid = useridField.getText().trim();
        String password = new String(passwordField.getPassword());
        String centroMonitoraggio = centroMonitoraggioField.getText().trim();
        JOptionPane.showMessageDialog(this, "registrazione avvennuta on successo", "Errore", JOptionPane.ERROR_MESSAGE);
        // Verifica dei campi obbligatori
        if (nome.isEmpty() || cognome.isEmpty() || codiceFiscale.isEmpty() || email.isEmpty() || userid.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi tranne il centro di monitoraggio sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Controllo che il codice fiscale sia di 16 caratteri
        if (codiceFiscale.length() != 16) {
            JOptionPane.showMessageDialog(this, "Il codice fiscale deve essere di 16 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Controllo che l'email contenga la @
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "L'email deve contenere una @.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creazione dell'oggetto Operatore
     /*   Operatore operatore = new Operatore(nome, cognome, codiceFiscale, email, userid, password, centroMonitoraggio);
        DatabaseConnection dc=new DatabaseConnection();
        dc.insertOperatore(nome, cognome, codiceFiscale, email, userid, password, centroMonitoraggio);
        /*

        // Salvataggio dell'operatore nel file
        String pathreg = "data\\OperatoriRegistrati.dati.txt";
        try (FileWriter writer = new FileWriter(pathreg, true)) {
            writer.write(operatore.toString() + "\n");
            JOptionPane.showMessageDialog(this, "Registrazione completata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
          //   dispose();  // Chiude la finestra di registrazione
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore durante la registrazione. Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        */

    }

    public static void main(String[] args) {
        new RegisterFrame();
    }
}
