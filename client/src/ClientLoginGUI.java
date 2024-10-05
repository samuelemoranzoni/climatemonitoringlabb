import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class ClientLoginGUI extends JFrame {
    private JTextField useridField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    JFrame previousframe;

    public ClientLoginGUI(JFrame frame) {
        this.previousframe = frame;
        setTitle("Login Operatore");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);

        // Title
        JLabel titleLabel = new JLabel("Login Operatore");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 90, 180));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.add(titleLabel, gbc);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        // Userid field
        JLabel useridLabel = new JLabel("Userid:");
        useridLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        useridField = createStyledTextField();

        formGbc.gridx = 0; formGbc.gridy = 0;
        formPanel.add(useridLabel, formGbc);
        formGbc.gridx = 1;
        formPanel.add(useridField, formGbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = createStyledPasswordField();

        formGbc.gridx = 0; formGbc.gridy = 1;
        formPanel.add(passwordLabel, formGbc);
        formGbc.gridx = 1;
        formPanel.add(passwordField, formGbc);

        gbc.gridy = 1;
        mainPanel.add(formPanel, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setOpaque(false);

        JButton loginButton = createStyledButton("Login");
        JButton backButton = createStyledButton("Torna Indietro");
        backButton.setBackground(new Color(128, 128, 128));

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);

        // Registration link
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

        // Status label
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 4;
        mainPanel.add(statusLabel, gbc);

        add(mainPanel);

        // Action listeners
        loginButton.addActionListener(e -> performLogin());
        backButton.addActionListener(e -> {
            previousframe.setVisible(true);
            dispose();
        });
        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openRegistrationFrame();
            }
        });
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        return field;
    }

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

    private void openRegistrationFrame() {
        SwingUtilities.invokeLater(() -> {
            try {
                new RegistrationFrame(this).setVisible(true);
                dispose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Main method remains unchanged
}