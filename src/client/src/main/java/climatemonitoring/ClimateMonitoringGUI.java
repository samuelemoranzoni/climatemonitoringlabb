package climatemonitoring;



import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.imageio.ImageIO;

/**
 * Classe per la GUI di monitoraggio climatico.
 * Questa classe estende JFrame e fornisce un'interfaccia utente per
 * la gestione dei dati climatici.
 * @author Moranzoni Samuele
 *  @author Di Tullio Edoardo
 */
public class ClimateMonitoringGUI extends JFrame {
    private Image backgroundImage;

    /**
     * Costruttore per l'oggetto climatemonitoring.ClimateMonitoringGUI.
     * Imposta il titolo della finestra, la dimensione e la posizione.
     * Carica un'immagine di sfondo e crea il pannello principale
     * con diversi componenti grafici.
     */
    public ClimateMonitoringGUI() {
        setTitle("Climate Monitoring");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Posiziona il frame al centro

        // Carica l'immagine di sfondo
        try {
            File imageFile = new File("C:\\Users\\samuele\\OneDrive\\Desktop\\UFV\\climate monitoring image.jpeg");
            backgroundImage = ImageIO.read(imageFile);
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'immagine: " + e.getMessage());
            // Continua senza immagine invece di crashare
            backgroundImage = null;
            e.printStackTrace();
        }

        // Crea un pannello personalizzato con l'immagine di sfondo
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new GridBagLayout());
        setContentPane(panel);

        // Crea e aggiunge i componenti
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("CLIMATE MONITORING: gestione dei tuoi dati climatici ", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, gbc);

        JButton searchButton = createStyledButton("Cerca e visualizza dati raccolti");
        JButton operatorButton = createStyledButton("Area Operatori");
        JButton exitButton = createStyledButton("Esci dall'applicazione");

        panel.add(searchButton, gbc);
        panel.add(operatorButton, gbc);
        panel.add(exitButton, gbc);

        // Aggiungi action listener
        searchButton.addActionListener(e -> {
            new RicercaAreaGeograficaFrame().setVisible(true);
            setVisible(false);
        });

        operatorButton.addActionListener(e -> {
            new Menuoperatorareaframe(this).setVisible(true);
            setVisible(false);
        });

        exitButton.addActionListener(e -> dispose());
    }

    /**
     * Crea un pulsante stilizzato con il testo specificato.
     *
     * @param text Il testo da visualizzare sul pulsante.
     * @return Il pulsante stilizzato creato.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    /**
     * Punto di ingresso principale dell'applicazione.
     * Inizializza e visualizza la GUI di monitoraggio climatico.
     *
     * @param args Argomenti della riga di comando (non utilizzati).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClimateMonitoringGUI().setVisible(true);
        });
    }
}
