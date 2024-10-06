import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;



public class RicercaAreaGeograficaFrame extends JFrame {
    private JTextField denominazioneField;
    private JTextField statoField;
    private JTextField latitudineField;
    private JTextField longitudineField;
    private JPanel risultatoPanel;
    private JButton cercaPerNomeButton;
    private JButton cercaPerCoordinateButton;
    private JButton visualizzaButton;
    private RemoteService stub;

    private AreaGeografica area;

    private JButton tornaIndietroButton;


    // Costanti di stile
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 14);
    private final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 14);
    private final Color ACCENT_COLOR = new Color(0, 120, 215);
    private final int FIELD_WIDTH = 20;  // Larghezza consistente per i campi di testo

    public RicercaAreaGeograficaFrame() throws RemoteException, NotBoundException {
        setTitle("Ricerca Area Geografica");
        setSize(500, 675);  //600
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //accesso rmi
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        stub = (RemoteService) registry.lookup("RemoteService");

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Sezione Ricerca per Nome
        addSection(mainPanel, gbc, "Ricerca per Nome", 0);

        addLabelAndField(mainPanel, "Denominazione:", denominazioneField = createStyledTextField(), gbc, 1);
        addLabelAndField(mainPanel, "Stato:", statoField = createStyledTextField(), gbc, 2);

        cercaPerNomeButton = createStyledButton("Cerca per Nome");
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 15, 5);  // Maggiore spazio dopo il bottone
        mainPanel.add(cercaPerNomeButton, gbc);

        // Sezione Ricerca per Coordinate
        addSection(mainPanel, gbc, "Ricerca per Coordinate", 4);

        addLabelAndField(mainPanel, "Latitudine:", latitudineField = createStyledTextField(), gbc, 5);
        addLabelAndField(mainPanel, "Longitudine:", longitudineField = createStyledTextField(), gbc, 6);

        cercaPerCoordinateButton = createStyledButton("Cerca per Coordinate");
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 5, 15, 5);
        mainPanel.add(cercaPerCoordinateButton, gbc);

        // Sezione Risultati
        addSection(mainPanel, gbc, "Risultati", 8);

        risultatoPanel = new JPanel();
        risultatoPanel.setLayout(new BoxLayout(risultatoPanel, BoxLayout.Y_AXIS));
        risultatoPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(risultatoPanel);
        scrollPane.setPreferredSize(new Dimension(450, 300)); //200
        gbc.gridy = 9;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        visualizzaButton = createStyledButton("Visualizza");

        visualizzaButton.setEnabled(false);
        gbc.gridy = 10;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 5, 5);
        mainPanel.add(visualizzaButton, gbc);

        add(mainPanel);
        setLocationRelativeTo(null);

        // Eventi
        cercaPerNomeButton.addActionListener(e -> {
            try {
                cercaPerNomeèStato();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        cercaPerCoordinateButton.addActionListener(e -> cercaPerCoordinate());
        visualizzaButton.addActionListener(e -> {
            try {
                visualizza();
            } catch (NotBoundException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        tornaIndietroButton = createStyledButton("Torna Indietro");
        gbc.gridy = 11;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(tornaIndietroButton, gbc);

        tornaIndietroButton.addActionListener(e -> tornaIndietro());
    }

    private void tornaIndietro() {
        new ClimateMonitoringGUI().setVisible(true);
        this.dispose();
    }

    private void visualizza() throws NotBoundException, RemoteException {
     new VisualizzaParametriFrame(area.getDenominazione());
     this.dispose();
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(FIELD_WIDTH);
        field.setFont(INPUT_FONT);
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(TITLE_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(ACCENT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    private void addSection(JPanel panel, GridBagConstraints gbc, String title, int gridy) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(ACCENT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 10, 5);  // Maggiore spazio prima del titolo
        panel.add(titleLabel, gbc);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field, GridBagConstraints gbc, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }


    private void mostraRisultati() {
        risultatoPanel.removeAll();

        if (area == null ) {
            JLabel noResults = new JLabel("Nessun'area trovata");
            noResults.setFont(LABEL_FONT);
            noResults.setAlignmentX(Component.CENTER_ALIGNMENT);
            risultatoPanel.add(noResults);
            visualizzaButton.setEnabled(false);
        } else {

                JPanel areaPanel = new JPanel(new GridLayout(4, 2, 10, 10)); //10 10
                areaPanel.setBackground(Color.WHITE);
                //areaPanel.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR));
            areaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                aggiungiCampo(areaPanel, "Denominazione:", area.getDenominazione());
                aggiungiCampo(areaPanel, "Stato:", area.getStato());
                aggiungiCampo(areaPanel, "Latitudine:", String.format("%.6f", area.getLatitudine()));
                aggiungiCampo(areaPanel, "Longitudine:", String.format("%.6f", area.getLongitudine()));

                areaPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, areaPanel.getPreferredSize().height));
                risultatoPanel.add(areaPanel);
              //  risultatoPanel.add(Box.createVerticalStrut(10));
            risultatoPanel.add(Box.createVerticalGlue()); // Aggiunge spazio flessibile sotto il risultato

            visualizzaButton.setEnabled(true);
        }

        risultatoPanel.revalidate();
        risultatoPanel.repaint();
    }

    private void aggiungiCampo(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(LABEL_FONT);
        //panel.add(labelComponent);
        labelComponent.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(labelComponent);

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(INPUT_FONT);
       // panel.add(valueComponent);
        valueComponent.setHorizontalAlignment(JLabel.LEFT);
        panel.add(valueComponent);
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new RicercaAreaGeograficaFrame().setVisible(true);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void cercaPerNomeèStato() throws RemoteException {
        String denominazione = denominazioneField.getText().trim();
        String stato = statoField.getText().trim();

        if (denominazione.isEmpty() || stato.isEmpty()) {
            mostraErrore("Inserire sia denominazione che stato");
            return;
        }

       area = stub.cercaAreaGeograficaPerDenominazioneeStato(denominazione, stato);
        mostraRisultati();
    }

    private void cercaPerCoordinate() {
        try {
            double lat = Double.parseDouble(latitudineField.getText().trim().replace(",", "."));
            double lon = Double.parseDouble(longitudineField.getText().trim().replace(",", "."));

            area = stub.cercaPerCoordinate(lat, lon);
            mostraRisultati();
        } catch (NumberFormatException ex) {
            mostraErrore("Inserire coordinate valide");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }


}