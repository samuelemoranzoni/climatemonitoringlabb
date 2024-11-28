package climatemonitoring;


import climatemonitoring.extensions.DatabaseConnectionException;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Classe che permette la visualizzazione dei parametri climatici in funzione dell'area ricevuta dall'utente nel frame precedente .
 * Si avvale di RMI per ottenere l'oggetto serializzato ParametriClimatici qui mostrati a schermo.
 * @author Moranzoni Samuele
 * @author Di Tullio Edoardo
 */

public class VisualizzaParametriFrame extends JFrame {

    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 14);
    private final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 14);
    private final Color ACCENT_COLOR = new Color(0, 120, 215);
    private ParametriClimatici parametri;
    private RemoteService stub;
    private String areadiricerca;

    /**
     * Costruttore frame , gestisci anche oggetti necessari per il bak-end , come Rmi e sovrascrive i parametri climatici di cui mostrerà i risultati
     * @param area
     * @throws NotBoundException
     * @throws RemoteException
     */

    public VisualizzaParametriFrame(String area)  {
        //accesso rmi
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            stub = (RemoteService) registry.lookup("climatemonitoring.RemoteService");
            this.areadiricerca=area;
            this.parametri = ottieniparametri(area);
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        } catch (DatabaseConnectionException e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);

        }

        initializeUI();


    }

    /**
     * inizializza la GUI
     */
    private void initializeUI() {
        setTitle("Visualizza Parametri Climatici");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null); // Centra la finestra

        // Imposta il layout principale
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Crea un pannello per i parametri climatici
        JPanel parametriPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Sezione Vento
        row = addSectionTitle(parametriPanel, gbc, row, "Vento");
        addLabelAndValue(parametriPanel, gbc, row++, "Velocità Media (m/s):", parametri.getMediaVelocitaVento());
        addLabelAndValue(parametriPanel, gbc, row++, "Score Medio:", parametri.getScoreMedioVento());
        addLabelAndValue(parametriPanel, gbc, row++, "Numero Rilevazioni:", parametri.getNumVento());

        // Sezione Umidità
        row = addSectionTitle(parametriPanel, gbc, row, "Umidità");
        addLabelAndValue(parametriPanel, gbc, row++, "Umidità Media (%):", parametri.getMediaUmidita());
        addLabelAndValue(parametriPanel, gbc, row++, "Score Medio:", parametri.getScoreMedioUmidita());
        addLabelAndValue(parametriPanel, gbc, row++, "Numero Rilevazioni:", parametri.getNumUmidita());

        // Sezione Pressione
        row = addSectionTitle(parametriPanel, gbc, row, "Pressione");
        addLabelAndValue(parametriPanel, gbc, row++, "Pressione Media (hPa):", parametri.getMediaPressione());
        addLabelAndValue(parametriPanel, gbc, row++, "Score Medio:", parametri.getScoreMedioPressione());
        addLabelAndValue(parametriPanel, gbc, row++, "Numero Rilevazioni:", parametri.getNumPressione());

        // Sezione Temperatura
        row = addSectionTitle(parametriPanel, gbc, row, "Temperatura");
        addLabelAndValue(parametriPanel, gbc, row++, "Temperatura Media (°C):", parametri.getMediaTemperatura());
        addLabelAndValue(parametriPanel, gbc, row++, "Score Medio:", parametri.getScoreMedioTemperatura());
        addLabelAndValue(parametriPanel, gbc, row++, "Numero Rilevazioni:", parametri.getNumTemperatura());

        // Sezione Precipitazioni
        row = addSectionTitle(parametriPanel, gbc, row, "Precipitazioni");
        addLabelAndValue(parametriPanel, gbc, row++, "Precipitazioni Medie (mm):", parametri.getMediaPrecipitazioni());
        addLabelAndValue(parametriPanel, gbc, row++, "Score Medio:", parametri.getScoreMedioPrecipitazioni());
        addLabelAndValue(parametriPanel, gbc, row++, "Numero Rilevazioni:", parametri.getNumPrecipitazioni());

        // Sezione Altitudine Ghiacciai
        row = addSectionTitle(parametriPanel, gbc, row, "Altitudine Ghiacciai");
        addLabelAndValue(parametriPanel, gbc, row++, "Altitudine Media (m):", parametri.getMediaAltitudineGhiacciai());
        addLabelAndValue(parametriPanel, gbc, row++, "Score Medio:", parametri.getScoreMedioAltitudineGhiacciai());
        addLabelAndValue(parametriPanel, gbc, row++, "Numero Rilevazioni:", parametri.getNumAltitudineGhiacciai());

        // Sezione Massa Ghiacciai
        row = addSectionTitle(parametriPanel, gbc, row, "Massa Ghiacciai");
        addLabelAndValue(parametriPanel, gbc, row++, "Massa Media (kg):", parametri.getMediaMassaGhiacciai());
        addLabelAndValue(parametriPanel, gbc, row++, "Score Medio:", parametri.getScoreMedioMassaGhiacciai());
        addLabelAndValue(parametriPanel, gbc, row++, "Numero Rilevazioni:", parametri.getNumMassaGhiacciai());

        // Aggiungi il pannello dei parametri all'area centrale con scroll
        JScrollPane scrollPane = new JScrollPane(parametriPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Crea un pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));

        JButton visualizzaCommentiButton = createStyledButton(" Commenti ");
        JButton tornaIndietroButton = createStyledButton("Torna Indietro");

        // Personalizza i pulsanti (opzionale)
        visualizzaCommentiButton.setPreferredSize(new Dimension(150, 40));
        tornaIndietroButton.setPreferredSize(new Dimension(150, 40));
        this.setVisible(true);

        // Aggiungi ActionListener ai pulsanti
        visualizzaCommentiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    visualizzacommentiin();
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                } catch (DatabaseConnectionException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RicercaAreaGeograficaFrame().setVisible(true);
                dispose();
            }
        });

        buttonPanel.add(visualizzaCommentiButton);
        buttonPanel.add(tornaIndietroButton);

        // Aggiungi i pulsanti al pannello principale
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Imposta il pannello principale nel frame
        setContentPane(mainPanel);
        setVisible(true);
    }

    /**
     * crea bottoni con un determinato stile e colore
     * @param text
     * @return
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
     * definisce il bottone Visualizza Commenti chiudendo il frame corrente e aprendo il frame dei commenti (sempre relativi all'area ricevuta dal costruttore in input)
     * @throws NotBoundException
     * @throws RemoteException
     */
    private void visualizzacommentiin() throws NotBoundException, RemoteException, DatabaseConnectionException {
        new VisualizzaCommentiFrame(this.areadiricerca);
        this.dispose();
    }

    /**
     * Aggiunge un titolo di sezione al pannello.
     *
     * @param panel     Il pannello a cui aggiungere la sezione.
     * @param gbc       I vincoli di GridBagLayout.
     * @param row       La riga corrente.
     * @param titleText Il testo del titolo della sezione.
     * @return La riga successiva.
     */
    private int addSectionTitle(JPanel panel, GridBagConstraints gbc, int row, String titleText) {
        JLabel sectionLabel = new JLabel(titleText);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setForeground(new Color(0, 102, 204)); // Blu scuro

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(sectionLabel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;

        return row + 1;
    }

    /**
     * Aggiunge una coppia etichetta-valore al pannello.
     *
     * @param panel     Il pannello a cui aggiungere la coppia.
     * @param gbc       I vincoli di GridBagLayout.
     * @param row       La riga corrente.
     * @param labelText Il testo dell'etichetta.
     * @param value     Il valore da mostrare.
     */
    private void addLabelAndValue(JPanel panel, GridBagConstraints gbc, int row, String labelText, float value) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setForeground(new Color(51, 51, 51)); // Grigio scuro

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(valueLabel, gbc);
    }

    /**
     * Aggiunge una coppia etichetta-valore al pannello.
     *
     * @param panel     Il pannello a cui aggiungere la coppia.
     * @param gbc       I vincoli di GridBagLayout.
     * @param row       La riga corrente.
     * @param labelText Il testo dell'etichetta.
     * @param value     Il valore da mostrare.
     */
    private void addLabelAndValue(JPanel panel, GridBagConstraints gbc, int row, String labelText, int value) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setForeground(new Color(51, 51, 51)); // Grigio scuro

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(valueLabel, gbc);
    }

    /**
     * Metodo per creare un oggetto climatemonitoring.climatemonitoring.model.climatemonitoring.ParametriClimatici di esempio.
     * Utilizzato solo per testare l'interfaccia.
     *
     * @return Un oggetto climatemonitoring.climatemonitoring.model.climatemonitoring.ParametriClimatici con dati di esempio.
     */
    private ParametriClimatici ottieniparametri(String area) throws DatabaseConnectionException, RemoteException {

            return  stub.visualizzaDatiClimatici(area);

    }

    // Metodo main per testare il frame
    public static void main(String[] args) {
        // Avvia il frame in modo thread-safe
    }
}
