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

/**
 * La classe <code>AreaRiservataOperatorFrame</code> rappresenta una finestra dell'interfaccia grafica
 * per l'area riservata di un operatore nel sistema di monitoraggio climatico.
 * Questa finestra offre funzionalità per la gestione dei parametri di monitoraggio,
 * la creazione di centri di monitoraggio , l'associazione di aree d'interesse e la creazione di nuove aree.
 * @author Moranzoni Samuele
 * @author Edoardo Di Tullio
 */
public class AreaRiservataOperatorFrame extends JFrame {

    /**
     * Costruisce un'istanza della finestra dell'area riservata per operatore.
     *
     * @throws RemoteException se si verifica un errore durante la comunicazione remota.
     * @throws NotBoundException se il servizio remoto non è correttamente collegato.
     */
    public AreaRiservataOperatorFrame() throws RemoteException, NotBoundException {
        // Configurazione della finestra
        setTitle("Area Riservata Operatore");
        setSize(800, 500);  // Imposta la dimensione della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creazione di un pannello principale con sfondo a gradiente
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

        // Titolo
        JLabel titleLabel = new JLabel("Area Riservata Operatore");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 90, 180));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // Accesso alle informazioni per messaggio di benvenuto
        Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Assicurati che l'indirizzo e la porta siano corretti
        RemoteService stub = (RemoteService) registry.lookup("RemoteService");

        // Messaggio di benvenuto
        String operatoreName = OperatoreSession.getInstance().getOperatore().getUserId();
        String nomecentro = stub.ottieniNomeCentro(OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId());
        String centrodiriferimento = (nomecentro != null) ? nomecentro : " centro non assegnato";
        JLabel welcomeLabel = new JLabel("Benvenuto, " + operatoreName + " operatore per " + centrodiriferimento);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 30, 20, 30);
        mainPanel.add(welcomeLabel, gbc);

        // Pannello dei pulsanti
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
                "Associa un' area esistente al tuo centro",
                "Torna al menu principale"
        };

        ActionListener[] actions = {
                e -> {
                    try {
                        parametriclimaticiin();
                    } catch (NotBoundException | RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                },
                e -> centercreationin(),
                e -> insareein(),
                e -> {
                    try {
                        associareaincentroin();
                    } catch (NotBoundException | RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                },
                e -> {
                    this.dispose();
                    ClimateMonitoringGUI.main(null);
                }
        };

        // Creazione dei pulsanti
        for (int i = 0; i < buttonTexts.length; i++) {
            JButton button = createStyledButton(buttonTexts[i]);
            if (i == buttonTexts.length - 1) {
                button.setBackground(new Color(128, 128, 128)); // Grigio per il pulsante di ritorno
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

    /**
     * Apre una finestra per associare aree al centro di monitoraggio.
     *
     * @throws NotBoundException se il servizio remoto non è correttamente collegato.
     * @throws RemoteException se si verifica un errore durante la comunicazione remota.
     */
    private void associareaincentroin() throws NotBoundException, RemoteException {
        Integer centro_id = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
        if (centro_id == null) {
            JOptionPane.showMessageDialog(this, "Funzionalità destinata agli operatore con un centro di riferimento", "Errore: non lavori per un centro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new AssociazioneAreeCentroFrame();
        this.dispose();
    }

    /**
     * Crea un pulsante stilizzato.
     *
     * @param text il testo da visualizzare sul pulsante.
     * @return il pulsante stilizzato creato.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(400, 40));

        // Aggiunta effetto hover
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
     * Apre la finestra per aggiungere parametri climatici.
     *
     * @throws NotBoundException se il servizio remoto non è correttamente collegato.
     * @throws RemoteException se si verifica un errore durante la comunicazione remota.
     */
    public void parametriclimaticiin() throws NotBoundException, RemoteException {
        Integer centro_id = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
        if (centro_id == null) {
            JOptionPane.showMessageDialog(this, "Funzionalità destinata agli operatore con un centro di riferimento", "Errore: non lavori per un centro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new InsParametriClimaticiFrame().setVisible(true);
        dispose();
    }

    /**
     * Apre la finestra per la creazione di centri di monitoraggio.
     */
    public void centercreationin() {
        new CenterCreationFrame().setVisible(true);
        dispose();
    }

    /**
     * Apre la finestra per l'inserimento delle aree di interesse.
     */
    public void insareein() {
        Integer centro_id = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
        if (centro_id == null) {
            JOptionPane.showMessageDialog(this, "Funzionalità destinata agli operatore con un centro di riferimento", "Errore: non lavori per un centro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new InsAreeInteresseFrame();
        dispose();
    }

    /**
     * Punto di entrata principale dell'applicazione.
     *
     * @param args argomenti da riga di comando.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AreaRiservataOperatorFrame();
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}



