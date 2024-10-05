import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;

public class ClimateMonitoringGUI extends JFrame {
    private Image backgroundImage;

    public ClimateMonitoringGUI() {
        setTitle("Climate Monitoring");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); //frame centrale

        // Load the background image
        try {
            File imageFile = new File("C:\\Users\\samuele\\OneDrive\\Desktop\\UFV\\climate monitoring image.jpeg");
            backgroundImage = ImageIO.read(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a custom panel with the background image
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

        // Create and add components
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

        // Add action listeners
        searchButton.addActionListener(e -> {

            new RicercaVisualizzazioneFrame().setVisible(true);
            setVisible(false);
        });

        operatorButton.addActionListener(e -> {

            new Menuoperatorareaframe(this).setVisible(true);
            setVisible(false);

        });

        exitButton.addActionListener(e -> dispose());
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClimateMonitoringGUI().setVisible(true);
        });
    }
}