import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class Menuoperatorareaframe extends JFrame {
    JFrame previousframe;

    public Menuoperatorareaframe(JFrame frame) {
        this.previousframe = frame;
        setTitle("Area Operatori");
        setSize(800, 400);  // Increased width for more horizontal space
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);  // Increased horizontal insets

        // Title
        JLabel titolo = new JLabel("Benvenuto nell'area operatore");
        titolo.setFont(new Font("Arial", Font.BOLD, 24));
        titolo.setForeground(new Color(0, 90, 180));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.add(titolo, gbc);

        // Subtitle - using HTML for wrapping with more width
        JLabel subtitle = new JLabel("<html><div style='width: 600px; text-align: center;'>Se gestisci un centro di monitoraggio hai la possibilità di inserire dati climatici relativi a zone di interesse, accessibili dai cittadini.</div></html>");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.insets = new Insets(20, 30, 30, 30);  // More vertical space after subtitle
        mainPanel.add(subtitle, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));  // More horizontal space between buttons
        buttonPanel.setOpaque(false);

        JButton accessButton = createStyledButton("Accedi");
        JButton registerButton = createStyledButton("Registrati");

        buttonPanel.add(accessButton);
        buttonPanel.add(registerButton);

        gbc.insets = new Insets(0, 30, 20, 30);
        mainPanel.add(buttonPanel, gbc);

        // Back button at bottom
        JButton backButton = createStyledButton("Torna Indietro");
        backButton.setBackground(new Color(128, 128, 128));
        gbc.insets = new Insets(10, 30, 20, 30);
        mainPanel.add(backButton, gbc);

        // Add action listeners
        accessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               loginframein();

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousframe.setVisible(true);
                dispose();
            }
        });

        add(mainPanel);
        setVisible(true);
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



    public void loginframein() {
        new ClientLoginGUI(this).setVisible(true);
        dispose();
    }
}