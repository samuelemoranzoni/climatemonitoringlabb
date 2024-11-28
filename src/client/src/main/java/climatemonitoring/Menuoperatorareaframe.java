package climatemonitoring;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe climatemonitoring.Menuoperatorareaframe rappresenta la finestra principale per gli operatori.
 * Questa finestra consente agli operatori di accedere a diverse funzioni,
 * come il login e la registrazione, e fornisce un'interfaccia utente
 * per gestire i dati climatici.
 * @author Moranzoni Samuele
 *  @author Di Tullio Edoardo
 */
class Menuoperatorareaframe extends JFrame {
    private JFrame previousframe;

    /**
     * Costruttore della classe climatemonitoring.Menuoperatorareaframe.
     *
     * @param frame JFrame precedente da cui viene aperta questa finestra.
     */
    public Menuoperatorareaframe(JFrame frame) {
        this.previousframe = frame;
        setTitle("Area Operatori");
        setSize(800, 400);  // Larghezza aumentata per più spazio orizzontale
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Pannello principale con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);  // Aumento degli spazi orizzontali

        // Titolo
        JLabel titolo = new JLabel("Benvenuto nell'area operatore");
        titolo.setFont(new Font("Arial", Font.BOLD, 24));
        titolo.setForeground(new Color(0, 90, 180));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.add(titolo, gbc);

        // Sottotitolo - utilizzo di HTML per la larghezza
        JLabel subtitle = new JLabel("<html><div style='width: 600px; text-align: center;'>Se gestisci un centro di monitoraggio hai la possibilità di inserire dati climatici relativi a zone di interesse, accessibili dai cittadini.</div></html>");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.insets = new Insets(20, 30, 30, 30);  // Maggiore spazio verticale dopo il sottotitolo
        mainPanel.add(subtitle, gbc);

        // Pannello dei pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));  // Maggiore spazio orizzontale tra i pulsanti
        buttonPanel.setOpaque(false);

        JButton accessButton = createStyledButton("Accedi");
        JButton registerButton = createStyledButton("Registrati");

        buttonPanel.add(accessButton);
        buttonPanel.add(registerButton);

        gbc.insets = new Insets(0, 30, 20, 30);
        mainPanel.add(buttonPanel, gbc);

        // Pulsante per tornare indietro in fondo
        JButton backButton = createStyledButton("Torna Indietro");
        backButton.setBackground(new Color(128, 128, 128));
        gbc.insets = new Insets(10, 30, 20, 30);
        mainPanel.add(backButton, gbc);

        // Aggiunta degli action listener ai pulsanti
        accessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginframein();  // Apertura della finestra di login
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousframe.setVisible(true);  // Ritorna al frame precedente
                dispose();  // Chiude la finestra attuale
            }
        });

        add(mainPanel);
        setVisible(true);  // Rende visibile la finestra
    }

    /**
     * Crea un pulsante stilizzato con impostazioni predefinite.
     *
     * @param text Il testo da visualizzare sul pulsante.
     * @return Restituisce un pulsante di tipo JButton.
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
     * Apre la finestra di login e chiude la finestra attuale.
     */
    public void loginframein() {
        new ClientLoginGUI().setVisible(true);  // Apertura della finestra di login
        dispose();  // Chiude la finestra attuale
    }
}
