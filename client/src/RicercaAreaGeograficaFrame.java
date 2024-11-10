import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Classe che rappresenta il frame per la ricerca di aree geografiche .
 * Questa classe gestisce l'interfaccia utente per cercare aree geografiche
 * per denominazione o coordinate e visualizzare i risultati.
 * Si avvale di RMI per ottenere un'oggetto remoto con cui agire in lettura nei confronti del database.
* @author Moranzoni Samuele
* @author Di Tullio Edoardo
 */
public class RicercaAreaGeograficaFrame extends JFrame {
    private JTextField denominazioneField; // Campo di testo per la denominazione
    private JTextField statoField; // Campo di testo per lo stato
    private JTextField latitudineField; // Campo di testo per la latitudine
    private JTextField longitudineField; // Campo di testo per la longitudine
    private JPanel risultatoPanel; // Pannello per visualizzare i risultati
    private JButton cercaPerNomeButton; // Bottone per cercare per nome
    private JButton cercaPerCoordinateButton; // Bottone per cercare per coordinate
    private JButton visualizzaButton; // Bottone per visualizzare i parametri dell'area
    private RemoteService stub; // Riferimento al servizio remoto
    private AreaGeografica area; // Area geografica trovata
    private JButton tornaIndietroButton; // Bottone per tornare indietro

    // Costanti di stile
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16); // Font per i titoli
    private final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 14); // Font per le etichette
    private final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 14); // Font per gli input
    private final Color ACCENT_COLOR = new Color(0, 120, 215); // Colore accentato
    private final int FIELD_WIDTH = 20; // Larghezza consistente per i campi di testo

    /**
     * Costruttore della classe RicercaAreaGeograficaFrame.
     * Inizializza l'interfaccia utente e stabilisce la connessione RMI.
     *
     * @throws RemoteException se si verifica un errore durante la connessione al servizio remoto.
     * @throws NotBoundException se il servizio remoto non è vincolato.
     */
    public RicercaAreaGeograficaFrame() throws RemoteException, NotBoundException {
        setTitle("Ricerca Area Geografica");
        setSize(500, 675); // Dimensione della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Accesso RMI
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
        gbc.insets = new Insets(10, 5, 15, 5); // Maggiore spazio dopo il bottone
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
        scrollPane.setPreferredSize(new Dimension(450, 300)); // Dimensione della scrollable area
        gbc.gridy = 9;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        visualizzaButton = createStyledButton("Visualizza");
        visualizzaButton.setEnabled(false); // Disabilita il bottone all'inizio
        gbc.gridy = 10;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 5, 5);
        mainPanel.add(visualizzaButton, gbc);

        add(mainPanel);
        setLocationRelativeTo(null); // Centra la finestra

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

    /**
     * Metodo per tornare indietro all'interfaccia principale del monitoraggio climatico.
     */
    private void tornaIndietro() {
        new ClimateMonitoringGUI().setVisible(true);
        this.dispose();
    }

    /**
     * Metodo per visualizzare i parametri dell'area selezionata.
     *
     * @throws NotBoundException se il servizio remoto non è vincolato.
     * @throws RemoteException se si verifica un errore durante la comunicazione con il servizio remoto.
     */
    private void visualizza() throws NotBoundException, RemoteException {
        new VisualizzaParametriFrame(area.getDenominazione());
        this.dispose();
    }

    /**
     * Crea un campo di testo stilizzato.
     *
     * @return il campo di testo stilizzato.
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(FIELD_WIDTH);
        field.setFont(INPUT_FONT);
        return field;
    }

    /**
     * Crea un bottone stilizzato con un testo specifico.
     *
     * @param text il testo da visualizzare sul bottone.
     * @return il bottone stilizzato.
     */
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

    /**
     * Aggiunge una sezione con titolo al pannello.
     *
     * @param panel il pannello a cui aggiungere la sezione.
     * @param gbc il layout di GridBagConstraints per gestire la posizione.
     * @param title il titolo della sezione.
     * @param gridy la posizione verticale nella grid.
     */
    private void addSection(JPanel panel, GridBagConstraints gbc, String title, int gridy) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(ACCENT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 10, 5); // Maggiore spazio prima del titolo
        panel.add(titleLabel, gbc);
    }

    /**
     * Aggiunge un'etichetta e un campo di testo al pannello.
     *
     * @param panel il pannello a cui aggiungere l'etichetta e il campo.
     * @param labelText il testo dell'etichetta.
     * @param field il campo di testo da aggiungere.
     * @param gbc il layout di GridBagConstraints per gestire la posizione.
     * @param gridy la posizione verticale nella grid.
     */
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

    /**
     * Mostra i risultati della ricerca nell'area dei risultati.
     */
    private void mostraRisultati() {
        risultatoPanel.removeAll(); // Pulisce i risultati precedenti

        if (area == null) {
            JLabel noResults = new JLabel("Nessun'area trovata");
            noResults.setFont(LABEL_FONT);
            noResults.setAlignmentX(Component.CENTER_ALIGNMENT);
            risultatoPanel.add(noResults);
            visualizzaButton.setEnabled(false); // Disabilita il bottone Visualizza
        } else {
            JPanel areaPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Layout per mostrare i dettagli
            areaPanel.setBackground(Color.WHITE);
            areaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Aggiunge i dettagli dell'area al pannello
            aggiungiCampo(areaPanel, "Denominazione:", area.getDenominazione());
            aggiungiCampo(areaPanel, "Stato:", area.getStato());
            aggiungiCampo(areaPanel, "Latitudine:", String.format("%.6f", area.getLatitudine()));
            aggiungiCampo(areaPanel, "Longitudine:", String.format("%.6f", area.getLongitudine()));

            areaPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, areaPanel.getPreferredSize().height));
            risultatoPanel.add(areaPanel);
            risultatoPanel.add(Box.createVerticalGlue()); // Aggiunge spazio flessibile sotto il risultato

            visualizzaButton.setEnabled(true); // Abilita il bottone Visualizza
        }

        risultatoPanel.revalidate(); // Rende i risultati aggiornati
        risultatoPanel.repaint(); // Rinfresca il pannello per visualizzare i cambiamenti
    }

    /**
     * Aggiunge un campo al pannello per visualizzare un'etichetta e un valore.
     *
     * @param panel il pannello a cui aggiungere il campo.
     * @param label il testo dell'etichetta.
     * @param value il valore da visualizzare.
     */
    private void aggiungiCampo(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(LABEL_FONT);
        labelComponent.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(labelComponent);

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(INPUT_FONT);
        valueComponent.setHorizontalAlignment(JLabel.LEFT);
        panel.add(valueComponent);
    }

    /**
     * Mostra un messaggio di errore in un dialogo.
     *
     * @param messaggio il messaggio da visualizzare.
     */
    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Metodo principale per avviare l'applicazione.
     *
     * @param args argomenti da linea di comando.
     */
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

    /**
     * Ricerca un'area geografica per denominazione e stato.
     *
     * @throws RemoteException se si verifica un errore durante la comunicazione con il servizio remoto.
     */
    private void cercaPerNomeèStato() throws RemoteException {
        String denominazione = denominazioneField.getText().trim();
        String stato = statoField.getText().trim();

        if (denominazione.isEmpty() || stato.isEmpty()) {
            mostraErrore("Inserire sia denominazione che stato");
            return;
        }

        area = stub.cercaAreaGeograficaPerDenominazioneeStato(denominazione, stato);
        mostraRisultati(); // Mostra i risultati dopo la ricerca
    }

    /**
     * Ricerca un'area geografica per coordinate.
     *
     * @throws RemoteException se si verifica un errore durante la comunicazione con il servizio remoto.
     */
    private void cercaPerCoordinate() {
        try {
            double lat = Double.parseDouble(latitudineField.getText().trim().replace(",", "."));
            double lon = Double.parseDouble(longitudineField.getText().trim().replace(",", "."));

            area = stub.cercaPerCoordinate(lat, lon);
            mostraRisultati(); // Mostra i risultati dopo la ricerca
        } catch (NumberFormatException ex) {
            mostraErrore("Inserire coordinate valide");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
