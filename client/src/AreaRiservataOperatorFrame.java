import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AreaRiservataOperatorFrame extends JFrame {

    public AreaRiservataOperatorFrame() throws RemoteException, NotBoundException {
        setTitle("Area Riservata Operatore");
        setSize(800, 500);  //800 500
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.WHITE;
                Color color2 = new Color(240, 240, 240);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);

        // Title
        JLabel titleLabel = new JLabel("Area Riservata Operatore");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 90, 180));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        //accediamo a informazioni per essere piu' precisi nel welcome message
        Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Assicurati che l'indirizzo e la porta siano corretti
        RemoteService stub = (RemoteService) registry.lookup("RemoteService");




        // Welcome message
      String operatoreName = OperatoreSession.getInstance().getOperatore().getUserId();
      String nomecentro=stub.ottieniNomeCentro(OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId());
      String centrodiriferimento = ( nomecentro!= null ) ? nomecentro : " centro non assegnato";
        JLabel welcomeLabel = new JLabel("Benvenuto, " + operatoreName + " operatore per " + centrodiriferimento);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 30, 20, 30);
        mainPanel.add(welcomeLabel, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridwidth = GridBagConstraints.REMAINDER;
        buttonGbc.insets = new Insets(10, 0, 10, 0);
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;

        String[] buttonTexts = {
                "Aggiungi parametri di monitoraggio",
                "Crea centri di monitoraggio",
                "Crea un'area d'interesse per il tuo centro",
                "Associa un' area esistente al tuo centro " ,
                "Torna al menu principale"
        };

        ActionListener[] actions = {
                e -> {
                    try {
                        parametriclimaticiin();
                    } catch (NotBoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                },
                e -> centercreationin(),
                e ->  insareein(),
                e -> {
                    try {
                        associareaincentroin();
                    } catch (NotBoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                },
                e -> {
                    this.dispose();
                    ClimateMonitoringGUI.main(null);
                }
        };

        for (int i = 0; i < buttonTexts.length; i++) {
            JButton button = createStyledButton(buttonTexts[i]);
            if (i == buttonTexts.length - 1) {
                button.setBackground(new Color(128, 128, 128)); // Grey for back button
            }
            final int index = i;
            button.addActionListener(actions[index]);
            buttonPanel.add(button, buttonGbc);
        }

        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
        setVisible(true);
    }

    private void associareaincentroin() throws NotBoundException, RemoteException {
        Integer centro_id = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
        if(centro_id==null){
            JOptionPane.showMessageDialog(this, "Funzionalità destinata agli operatore con un centro di riferimento", "Errore:non lavori per un centro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new AssociazioneAreeCentroFrame();
        this.dispose();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(400, 40));

        // Add hover effect
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
    public void parametriclimaticiin() throws NotBoundException, RemoteException {
        Integer centro_id = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
        if(centro_id==null){
            JOptionPane.showMessageDialog(this, "Funzionalità destinata agli operatore con un centro di riferimento", "Errore:non lavori per un centro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new InsParametriClimaticiFrame().setVisible(true);
        dispose();
    }

    public void centercreationin(){
        new CenterCreationFrame().setVisible(true);
        dispose();
    }

    public void insareein(){
        Integer centro_id = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
        if(centro_id==null){
            JOptionPane.showMessageDialog(this, "Funzionalità destinata agli operatore con un centro di riferimento", "Errore:non lavori per un centro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new InsAreeInteresseFrame();
        dispose();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AreaRiservataOperatorFrame();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
