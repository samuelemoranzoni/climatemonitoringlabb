
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe ClientLoginGUI rappresenta l'interfaccia grafica del login per l'operatore.
 * È estesa da JFrame e gestisce il login dell'operatore. Sempre tramite RMI controlla la validità dei valori inseriti dall'utente
 *@author Moranzoni Samuele
 *@author Di Tullio Edoardo
 */
public class ClientLoginGUI extends JFrame {
    private JTextField useridField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    /**
     * Costruttore di ClientLoginGUI.
     * Inizializza l'interfaccia grafica e i componenti necessari per la login.
     */
    public ClientLoginGUI() {
        setTitle("Login Operatore");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Pannello principale
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);

        // Titolo
        JLabel titleLabel = new JLabel("Login Operatore");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 90, 180));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.add(titleLabel, gbc);

        // Pannello del modulo
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        // Campo userid
        JLabel useridLabel = new JLabel("Userid:");
        useridLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        useridField = createStyledTextField();

        formGbc.gridx = 0; formGbc.gridy = 0;
        formPanel.add(useridLabel, formGbc);
        formGbc.gridx = 1;
        formPanel.add(useridField, formGbc);

        // Campo password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = createStyledPasswordField();

        formGbc.gridx = 0; formGbc.gridy = 1;
        formPanel.add(passwordLabel, formGbc);
        formGbc.gridx = 1;
        formPanel.add(passwordField, formGbc);

        gbc.gridy = 1;
        mainPanel.add(formPanel, gbc);

        // Pannello dei pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setOpaque(false);

        JButton loginButton = createStyledButton("Login");
        JButton backButton = createStyledButton("Torna Indietro");
        backButton.setBackground(new Color(128, 128, 128));

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);

        // Link di registrazione
        JLabel registerLink = new JLabel("Non hai un account? Registrati qui");
        registerLink.setFont(new Font("Arial", Font.PLAIN, 14));
        registerLink.setForeground(new Color(0, 90, 180));
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Font font = registerLink.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        registerLink.setFont(font.deriveFont(attributes));

        gbc.gridy = 3;
        mainPanel.add(registerLink, gbc);

        // Etichetta di stato
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 4;
        mainPanel.add(statusLabel, gbc);

        add(mainPanel);

        // Listener degli eventi
        loginButton.addActionListener(e -> performLogin());
        backButton.addActionListener(e -> {
            new ClimateMonitoringGUI().setVisible(true);
            dispose();
        });
        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openRegistrationFrame();
            }
        });
    }

    /**
     * Crea un campo di testo stilizzato per l'userid.
     *
     * @return il campo di testo stilizzato
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        return field;
    }

    /**
     * Crea un campo di password stilizzato.
     *
     * @return il campo di password stilizzato
     */
    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        return field;
    }

    /**
     * Crea un pulsante stilizzato con il testo specificato.
     *
     * @param text il testo del pulsante
     * @return il pulsante stilizzato
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    /**
     * Esegue l'operazione di login.
     * Verifica le credenziali dell'utente e gestisce l'accesso.
     */
    private void performLogin() {
        try {
            String userid = useridField.getText();
            String password = new String(passwordField.getPassword());

            if (userid.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Inserisci tutti i campi",
                        "Campi vuoti",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Connessione al servizio remoto
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RemoteService stub = (RemoteService) registry.lookup("RemoteService");
            OperatoreRegistrato operatore = stub.loginOperatore(userid, password);

            if (operatore != null && operatore.getId() > 0) {
                OperatoreSession.getInstance().setOperatore(operatore);
                JOptionPane.showMessageDialog(this,
                        "Login avvenuto con successo! Benvenuto, " + userid);
                new AreaRiservataOperatorFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenziali errate: reinseriscile",
                        "Login fallito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Errore di connessione.");
        }
    }

    /**
     * Apre il pannello di registrazione.
     * Invoca la creazione di un nuovo frame di registrazione.
     */
    private void openRegistrationFrame() {
        SwingUtilities.invokeLater(() -> {
            try {
                new RegistrationFrame(this).setVisible(true);
                dispose();
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Il metodo main per avviare l'applicazione GUI.
     *
     * @param args gli argomenti da riga di comando
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientLoginGUI().setVisible(true));
    }
}
