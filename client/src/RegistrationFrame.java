import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Classe RegistrationFrame rappresenta una finestra per la registrazione di un operatore.
 * Esta finestra consente all'utente di inserire le proprie informazioni per la registrazione
 * e comunica con il servizio remoto tramite RMI.
 * @author Moranzoni Samuele
 *   @author Di Tullio Edoardo

 */
public class RegistrationFrame extends JFrame {
    private JTextField nomeField, cognomeField, codiceFiscaleField, emailField, usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> idMonitoraggioComboBox;
    private RemoteService stub;

    /**
     * Costruttore della classe RegistrationFrame.
     *
     * @param frame Il JFrame principale da cui viene chiamato.
     * @throws IOException Se si verifica un errore di input/output.
     * @throws NotBoundException Se non riesce a trovare il servizio remoto nel registro.
     */
    public RegistrationFrame(JFrame frame) throws IOException, NotBoundException {
        setTitle("Registrazione Operatore");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Accesso tramite RMI
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        stub = (RemoteService) registry.lookup("RemoteService");

        // Pannello principale con sfondo a gradiente
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = Color.WHITE;
                Color color2 = new Color(240, 240, 240);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);

        // Titolo
        JLabel titleLabel = new JLabel("Registrazione Operatore");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 90, 180));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // Pannello del modulo
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        String[] labels = {"Nome:*", "Cognome:*", "Codice Fiscale:*", "Email:*", "Username:*", "Password:*", "ID Monitoraggio:"};
        JComponent[] fields = {
                nomeField = createStyledTextField(),
                cognomeField = createStyledTextField(),
                codiceFiscaleField = createStyledTextField(),
                emailField = createStyledTextField(),
                usernameField = createStyledTextField(),
                passwordField = createStyledPasswordField(),
                idMonitoraggioComboBox = createStyledComboBox()
        };

        // Aggiungi i campi del modulo
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            formGbc.gridx = 0;
            formGbc.gridy = i;
            formPanel.add(label, formGbc);

            formGbc.gridx = 1;
            formGbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(fields[i], formGbc);
        }

        gbc.gridy = 1;
        mainPanel.add(formPanel, gbc);

        // Pannello dei pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setOpaque(false);

        JButton registratiButton = createStyledButton("Registrati");
        JButton backButton = createStyledButton("Torna Indietro");
        backButton.setBackground(new Color(128, 128, 128));

        buttonPanel.add(registratiButton);
        buttonPanel.add(backButton);

        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);

        // Listener per le azioni
        registratiButton.addActionListener(e -> {
            try {
                performRegistration();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante la registrazione: " + ex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new ClientLoginGUI().setVisible(true);
            dispose();
        });
    }

    /**
     * Crea un campo di testo styled.
     *
     * @return Un JTextField con stile personalizzato.
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 30));
        return field;
    }

    /**
     * Crea un campo di password styled.
     *
     * @return Un JPasswordField con stile personalizzato.
     */
    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 30));
        return field;
    }

    /**
     * Crea un JComboBox styled per la selezione degli ID di monitoraggio.
     *
     * @return Un JComboBox con stile personalizzato.
     */
    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(250, 30));
        try {
            comboBox.addItem("Nessun centro"); // Prima opzione
            List<String> centri = stub.getCentriRegistrati(0);
            for (String centro : centri) {
                comboBox.addItem(centro);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Errore nel caricamento delle aree: " + ex.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
        return comboBox;
    }

    /**
     * Crea un pulsante styled.
     *
     * @param text Il testo del pulsante.
     * @return Un JButton con stile personalizzato.
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

        // Aggiungi effetto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 100, 195));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
        });

        return button;
    }

    /**
     * Esegue la registrazione dell'operatore.
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     * @throws NotBoundException Se non riesce a trovare il servizio remoto nel registro.
     */
    public void performRegistration() throws RemoteException, NotBoundException {
        // Validare i campi richiesti
        if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() ||
                codiceFiscaleField.getText().isEmpty() || emailField.getText().isEmpty() ||
                usernameField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "I campi contrassegnati da * sono obbligatori",
                    "Campi mancanti", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String selectedCentroMonitoraggio = (String) idMonitoraggioComboBox.getSelectedItem();

            if (selectedCentroMonitoraggio.equals("Nessun centro")) {
                selectedCentroMonitoraggio = null;
            }

            OperatoreRegistrato or = stub.createOperatoreRegistrato(
                    nomeField.getText(), cognomeField.getText(),
                    codiceFiscaleField.getText(), emailField.getText(),
                    usernameField.getText(), new String(passwordField.getPassword()),
                    stub.get_id_centro(selectedCentroMonitoraggio));

            handleRegistrationResponse(or.getId());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "L'ID Monitoraggio deve essere un numero",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gestisce la risposta della registrazione.
     *
     * @param risposta Il codice di risposta dalla registrazione.
     */
    private void handleRegistrationResponse(int risposta) {
        switch (risposta) {
            case -2:
                JOptionPane.showMessageDialog(this, "Codice fiscale o username già in uso",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                break;
            case -3:
                JOptionPane.showMessageDialog(this, "ID centro di monitoraggio non esistente",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                break;
            case -4:
                JOptionPane.showMessageDialog(this, "Email non valida",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                break;
            case -5:
                JOptionPane.showMessageDialog(this, "Codice fiscale non valido (deve essere di 16 caratteri)",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                break;
            case -1:
                JOptionPane.showMessageDialog(this, "Errore di connessione",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                if (risposta > 0) {
                    JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo. ID utente: " + risposta);
                    new ClientLoginGUI().setVisible(true);
                    dispose();
                }
        }
    }

    /**
     * Metodo principale per avviare l'applicazione.
     *
     * @param args Argomenti della linea di comando.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new RegistrationFrame(new Menuoperatorareaframe(new ClimateMonitoringGUI())).setVisible(true);
            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        });
    }
}
