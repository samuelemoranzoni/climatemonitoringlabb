import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AssociazioneAreeCentroFrame extends JFrame {
    private RemoteService stub;
    private int centroMonitoraggioId;
    private java.util.List<String> tutteLeAree;
    private DefaultComboBoxModel<String> comboBoxModel;
    private JComboBox<String> searchComboBox;
    private JButton aggiungiButton;
    private JButton backButton;

    public AssociazioneAreeCentroFrame() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        stub = (RemoteService) registry.lookup("RemoteService");
        centroMonitoraggioId = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
        String nomecentro = stub.ottieniNomeCentro(centroMonitoraggioId);
        String centrodiriferimento = (nomecentro != null) ? nomecentro : "centro non assegnato";
        setTitle("Associazione Aree Esistenti al tuo Centro: " + centrodiriferimento);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            tutteLeAree = stub.getTutteAreeInteresse(0);
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle aree",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Associazione Aree Esistenti");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 90, 180));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ComboBox panel
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.setOpaque(false);

        JLabel selectLabel = new JLabel("Seleziona un'area:");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBoxPanel.add(selectLabel);

        comboBoxModel = new DefaultComboBoxModel<>();
        if (tutteLeAree != null && !tutteLeAree.isEmpty()) {
            for (String area : tutteLeAree) {
                comboBoxModel.addElement(area);
            }
        } else {
            comboBoxModel.addElement("Nessuna area disponibile");
        }

        searchComboBox = new JComboBox<>(comboBoxModel);
        searchComboBox.setPreferredSize(new Dimension(200, 30));
        searchComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBoxPanel.add(searchComboBox);

        mainPanel.add(comboBoxPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setOpaque(false);

        aggiungiButton = createStyledButton("Aggiungi Area");
        aggiungiButton.addActionListener(e -> aggiungiArea());
        buttonsPanel.add(aggiungiButton);

        backButton = createStyledButton("Indietro");
        backButton.addActionListener(    e -> {
                    try {
                        buttonin();
                    } catch (NotBoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        buttonsPanel.add(backButton);

        mainPanel.add(buttonsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Link for registering new area
        JLabel linkLabel = new JLabel("<html><u>Se l'area che stai cercando non è presente, devi registrarla , sarà associata in automatico</u></html>");
        linkLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        linkLabel.setForeground(Color.BLUE);
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new InsAreeInteresseFrame().setVisible(true);
            }
        });
        linkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(linkLabel);

        setContentPane(mainPanel);

        // Set minimum size
        setMinimumSize(new Dimension(400, 300));
    }

    private void buttonin() throws NotBoundException, RemoteException {
        new AreaRiservataOperatorFrame();
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
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }



    private void updateComboBox() {
        comboBoxModel.removeAllElements();
        if (tutteLeAree != null && !tutteLeAree.isEmpty()) {
            for (String area : tutteLeAree) {
                comboBoxModel.addElement(area);
            }
        } else {
            comboBoxModel.addElement("Nessuna area disponibile");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AssociazioneAreeCentroFrame();
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        });
    }



    private void aggiungiArea() {
        String selectedArea = (String) searchComboBox.getSelectedItem();
        if (selectedArea != null && !selectedArea.trim().isEmpty()) {
            try {
                //risalgo al id del centro attivo dell'operatore
                centroMonitoraggioId = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
                int areaId=stub.get_id_denominazione_area(selectedArea) ;// Implementa questo metodo
                int risposta=stub.insertAreeControllate(centroMonitoraggioId, areaId);
                if(risposta > 0) {
                    JOptionPane.showMessageDialog(this, "Area aggiunta con successo!",
                            "Successo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if(risposta == -1){
                    JOptionPane.showMessageDialog(this, "Errore di connessione",
                            "Errore", JOptionPane.ERROR_MESSAGE);

                }
                if(risposta == -2){

                    JOptionPane.showMessageDialog(this, "Errore di connessione: area già assegnata al centro",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }


            } catch (RemoteException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore nell'aggiunta dell'area",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}

