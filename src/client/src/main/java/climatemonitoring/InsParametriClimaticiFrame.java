package climatemonitoring;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.List;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * Classe per la gestione della finestra di inserimento dei parametri climatici.
 * Estende JFrame per fornire un'interfaccia grafica per l'inserimento dei dati climatici.
 * @author Moranzoni Samuele
 *  @author Di Tullio Edoardo
 */
public class InsParametriClimaticiFrame extends JFrame {
    private JComboBox<String> comboBox;
   private RemoteService stub;
   private Registry registry;

    private String areaSelezionata;

    private JTextField dataRilevazioneField;
    private JTextField velocitaVentoField;
    private JSpinner scoreVentoSpinner;
    private JTextField notaVentoField;
    private JTextField umiditaField;
    private JSpinner scoreUmiditaSpinner;
    private JTextField notaUmiditaField;
    private JTextField pressioneField;
    private JSpinner scorePressioneSpinner;
    private JTextField notaPressioneField;
    private JTextField temperaturaField;
    private JSpinner scoreTemperaturaSpinner;
    private JTextField notaTemperaturaField;
    private JTextField precipitazioniField;
    private JSpinner scorePrecipitazioniSpinner;
    private JTextField notaPrecipitazioniField;
    private JTextField altitudineGhiacciaiField;
    private JSpinner scoreAltitudineGhiacciaiSpinner;
    private JTextField notaAltitudineGhiacciaiField;
    private JTextField massaGhiacciaiField;
    private JSpinner scoreMassaGhiacciaiSpinner;
    private JTextField notaMassaGhiacciaiField;

    /**
     * Costruttore della classe climatemonitoring.InsParametriClimaticiFrame.
     * Inizializza la finestra con i componenti necessari per l'inserimento dei parametri climatici.
     *
     * @throws RemoteException se si verifica un errore di comunicazione remota
     * @throws NotBoundException se il servizio remoto non è stato trovato
     */

    public InsParametriClimaticiFrame()  {

        super("Inserisci Parametri Climatici");
        setLayout(new BorderLayout());
        JPanel areaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        areaPanel.setBackground(Color.WHITE);

// Crea l'etichetta
        JLabel areaLabel = new JLabel("Selezione area osservata dal tuo centro:");
        areaLabel.setFont(new Font("Arial", Font.BOLD, 14));




// Popola il ComboBox con i dati dal database
        try {
            //invocazione registry
            registry = LocateRegistry.getRegistry("localhost", 1099); // Assicurati che l'indirizzo e la porta siano corretti
            stub = (RemoteService) registry.lookup("climatemonitoring.RemoteService");

            // Crea il ComboBox
            JComboBox<String> areaComboBox = new JComboBox<>();
            Integer centroMonitoraggioId = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
            if(centroMonitoraggioId==null){
                JOptionPane.showMessageDialog(this, "Errore: Non puoi inserire parametri se non lavori per nessun centro. Accederai alla pagina ma non potrai effettuare inserimenti", "Errore", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
            areaComboBox.addItem("Nessuna area selezionata"); //prima opzione
            List<String> aree = stub.getareeosservatedalcentro(centroMonitoraggioId);
            for (String area : aree) {
                areaComboBox.addItem(area);
            }
            //aggiunge action listener per combobox
            areaComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    areaSelezionata = (String) areaComboBox.getSelectedItem();
                    if(areaSelezionata.equals("Nessuna area selezionata")){
                        JOptionPane.showMessageDialog(null, "Nessun' area selezionata" ,"Inserisci un 'area valida", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        System.out.println("Area selezionata: " + areaSelezionata);
                    }
                }
            });

// Personalizza l'aspetto del ComboBox
            areaComboBox.setPreferredSize(new Dimension(250, 25));

// Aggiungi i componenti al pannello dell'area
            areaPanel.add(areaLabel);
            areaPanel.add(areaComboBox);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Errore nel caricamento delle aree: " + ex.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        // aggiunge un titolo
        TitledBorder titleBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 215), 1),
                "Parametri Climatici"
        );
        titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.setBorder(titleBorder);




        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Crea tutti i componenti

        addFormField(formPanel, gbc, "Data di Rilevazione:", dataRilevazioneField = createStyledTextField());

        // Vento
        addFormField(formPanel, gbc, "Velocità del Vento (km/h):", velocitaVentoField = createStyledTextField());
        addFormField(formPanel, gbc, "Score Vento (1-5):", scoreVentoSpinner = createStyledSpinner());
        addFormField(formPanel, gbc, "Nota Vento:", notaVentoField = createStyledTextField());

        // Umidità
        addFormField(formPanel, gbc, "Umidità (%):", umiditaField = createStyledTextField());
        addFormField(formPanel, gbc, "Score Umidità (1-5):", scoreUmiditaSpinner = createStyledSpinner());
        addFormField(formPanel, gbc, "Nota Umidità:", notaUmiditaField = createStyledTextField());

        // Pressione
        addFormField(formPanel, gbc, "Pressione (hPa):", pressioneField = createStyledTextField());
        addFormField(formPanel, gbc, "Score Pressione (1-5):", scorePressioneSpinner = createStyledSpinner());
        addFormField(formPanel, gbc, "Nota Pressione:", notaPressioneField = createStyledTextField());

        // Temperatura
        addFormField(formPanel, gbc, "Temperatura (°C):", temperaturaField = createStyledTextField());
        addFormField(formPanel, gbc, "Score Temperatura (1-5):", scoreTemperaturaSpinner = createStyledSpinner());
        addFormField(formPanel, gbc, "Nota Temperatura:", notaTemperaturaField = createStyledTextField());

        // Precipitazioni
        addFormField(formPanel, gbc, "Precipitazioni (mm):", precipitazioniField = createStyledTextField());
        addFormField(formPanel, gbc, "Score Precipitazioni (1-5):", scorePrecipitazioniSpinner = createStyledSpinner());
        addFormField(formPanel, gbc, "Nota Precipitazioni:", notaPrecipitazioniField = createStyledTextField());

        // Altitudine Ghiacciai
        addFormField(formPanel, gbc, "Altitudine Ghiacciai (m):", altitudineGhiacciaiField = createStyledTextField());
        addFormField(formPanel, gbc, "Score Altitudine Ghiacciai (1-5):", scoreAltitudineGhiacciaiSpinner = createStyledSpinner());
        addFormField(formPanel, gbc, "Nota Altitudine Ghiacciai:", notaAltitudineGhiacciaiField = createStyledTextField());

        // Massa Ghiacciai
        addFormField(formPanel, gbc, "Massa Ghiacciai (Kg):", massaGhiacciaiField = createStyledTextField());
        addFormField(formPanel, gbc, "Score Massa Ghiacciai (1-5):", scoreMassaGhiacciaiSpinner = createStyledSpinner());
        addFormField(formPanel, gbc, "Nota Massa Ghiacciai:", notaMassaGhiacciaiField = createStyledTextField());

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(scrollPane);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = createStyledButton("Salva Parametri", new Color(0, 120, 215));
        JButton backButton = createStyledButton("Indietro", new Color(108, 117, 125));

        saveButton.addActionListener(e -> performinserimentoparametri());
        backButton.addActionListener(e ->
                {
                    try {
                        backin();
                    } catch (NotBoundException ex) {
                        JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }

        );

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);
        add(areaPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    /**
     * Aggiunge un campo di input al pannello fornito.
     *
     * @param panel il pannello al quale aggiungere il campo
     * @param gbc le costanti di layout per la posizione del campo
     * @param labelText il testo dell'etichetta per il campo
     * @param component il componente di input da aggiungere
     */
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.3;
        panel.add(createStyledLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }
    /**
     * Crea un'etichetta stilizzata.
     *
     * @param text il testo dell'etichetta
     * @return l'etichetta creata
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }
    /**
     * Crea un campo di testo stilizzato.
     *
     * @return il campo di testo creato
     */
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setPreferredSize(new Dimension(200, 25));
        return textField;
    }
    /**
     * Crea uno spinner stilizzato per l'inserimento di numeri.
     *
     * @return lo spinner creato
     */
    private JSpinner createStyledSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        spinner.setPreferredSize(new Dimension(60, 25));
        return spinner;
    }
    /**
     * Crea un pulsante stilizzato con il testo e il colore di sfondo specificati.
     *
     * @param text il testo da visualizzare sul pulsante
     * @param bgColor il colore di sfondo del pulsante
     * @return il pulsante creato
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(120, 30));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    /**
     * Esegue l'inserimento dei parametri climatici nel sistema database.
     * Recupera i dati dai campi di input e gestisce i messaggi di errore.
     */
    private void performinserimentoparametri() {
        try {
              if (dataRilevazioneField.getText().isEmpty() ||
                    velocitaVentoField.getText().isEmpty() ||
                    umiditaField.getText().isEmpty() ||
                    pressioneField.getText().isEmpty() ||
                    temperaturaField.getText().isEmpty() ||
                    precipitazioniField.getText().isEmpty() ||
                    altitudineGhiacciaiField.getText().isEmpty() ||
                    massaGhiacciaiField.getText().isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Tutti i campi eccetto i campi che si riferiscono alle note vanno compilati obbligatoriamente prima di salvare i parametri", "Errore", JOptionPane.ERROR_MESSAGE);
                  return;
              }
            // Recupero dei valori dai campi di testo e spinner
            //areaInteresseField.getText();
            String areaInteresse = areaSelezionata;
           /* if(areaInteresse.isEmpty()){
                JOptionPane.showMessageDialog(this, "Errore: area non inserita ", "Errore : area mancante", JOptionPane.ERROR_MESSAGE);
                return;
            } */
            String dataRilevazione = dataRilevazioneField.getText();

// Controlla se la data è nel formato corretto YYYY-MM-DD
            if (!dataRilevazione.matches("\\d{4}-\\d{2}-\\d{2}")) {
                // Mostra un messaggio di errore se il formato è errato
                JOptionPane.showMessageDialog(this, "Errore: La data deve essere nel formato YYYY-MM-DD. Inserisci una data valida.", "Errore formato data", JOptionPane.ERROR_MESSAGE);
                return; // Blocca il processo di inserimento
            }
//controlla se la data non esiste del tipo : 2022-35-36
            try {
                Date sqlDate = Date.valueOf(dataRilevazione);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Errore: Data non valida.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Utilizzo di valori di default se i campi sono vuoti
            int velocitaVento = parseInteger(velocitaVentoField.getText(), "Velocità Vento");
            int scoreVento = (int) scoreVentoSpinner.getValue();
            String notaVento = notaVentoField.getText();

            float umidita = parseFloat(umiditaField.getText(), "Umidità");
            int scoreUmidita = (int) scoreUmiditaSpinner.getValue();
            String notaUmidita = notaUmiditaField.getText();

            float pressione = parseFloat(pressioneField.getText(), "Pressione");
            int scorePressione = (int) scorePressioneSpinner.getValue();
            String notaPressione = notaPressioneField.getText();

            float temperatura = parseFloat(temperaturaField.getText(), "Temperatura");
            int scoreTemperatura = (int) scoreTemperaturaSpinner.getValue();
            String notaTemperatura = notaTemperaturaField.getText();

            float precipitazioni = parseFloat(precipitazioniField.getText(), "Precipitazioni");
            int scorePrecipitazioni = (int) scorePrecipitazioniSpinner.getValue();
            String notaPrecipitazioni = notaPrecipitazioniField.getText();

            float altitudineGhiacciai = parseFloat(altitudineGhiacciaiField.getText(), "Altitudine Ghiacciai");
            int scoreAltitudineGhiacciai = (int) scoreAltitudineGhiacciaiSpinner.getValue();
            String notaAltitudineGhiacciai = notaAltitudineGhiacciaiField.getText();

            float massaGhiacciai = parseFloat(massaGhiacciaiField.getText(), "Massa Ghiacciai");
            int scoreMassaGhiacciai = (int) scoreMassaGhiacciaiSpinner.getValue();
            String notaMassaGhiacciai = notaMassaGhiacciaiField.getText();

            //operatore che ha effettuato la connessione
            OperatoreRegistrato operatore = OperatoreSession.getInstance().getOperatore();
            if(operatore.getCentroMonitoraggioId()==null){
                JOptionPane.showMessageDialog(this, "Errore: il sistema non permette di inserire paramatri agli operatori registrati che non hanno un centro di monitoraggio di afferenza ", "Errore: centro di moniotraggio mancante", JOptionPane.ERROR_MESSAGE);
                return;
            }


            //operatore che ha effettuato la connessione


    int risposta = stub.insertParametriClimatici(operatore.getCentroMonitoraggioId(), areaInteresse, operatore.getId(), dataRilevazione,
            velocitaVento, scoreVento, notaVento,
            umidita, scoreUmidita, notaUmidita,
            pressione, scorePressione, notaPressione,
            temperatura, scoreTemperatura, notaTemperatura,
            precipitazioni, scorePrecipitazioni, notaPrecipitazioni,
            altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai,
            massaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai);



// Controllo dei risultati e gestione dei messaggi di errore
            if (risposta == -1) {
                JOptionPane.showMessageDialog(this, "Errore: denominazione ufficiale area non trovata. Provvedi a registrare correttamente quest'area di interesse se vuoi inserirne dei parametri relativi.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (risposta == -2) {
                JOptionPane.showMessageDialog(this, "Errore: il centro di monitoraggio non esiste. Controlla il centro di monitoraggio e riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (risposta == -3) {
                JOptionPane.showMessageDialog(this, "Errore: l'operatore non esiste. Verifica l'ID operatore e riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (risposta == -4) {
                JOptionPane.showMessageDialog(this, "Errore: l'operatore non è associato al centro di monitoraggio. Contatta l'amministratore.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (risposta == -5) {
                JOptionPane.showMessageDialog(this, "Errore: lo score deve essere compreso tra 1 e 5 per tutti i parametri. Correggi i valori e riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (risposta == -6) {
                JOptionPane.showMessageDialog(this, "Errore: la data di rilevazione non può essere vuota. Inserisci una data valida.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (risposta == -7) {
                JOptionPane.showMessageDialog(this, "Errore: i parametri climatici devono essere valori positivi. Verifica e correggi i valori.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (risposta == -8) {
                JOptionPane.showMessageDialog(this, "Errore: inserimento fallito per errore di connessione .Riprova più tardi.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (risposta > 0) {
                JOptionPane.showMessageDialog(this, "Inserimento dei parametri climatici riuscito con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "Errore sconosciuto. Codice di risposta: " + risposta, "Errore", JOptionPane.ERROR_MESSAGE);
            }



        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Errore nei dati inseriti. Assicurati che tutti i numeri siano validi.", "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        } catch (AccessException e) {
            JOptionPane.showMessageDialog(null, "Errore nei dati inseriti. Assicurati che tutti i numeri siano validi.", "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }
    /**
     * Analizza un numero intero da una stringa e fornisce un valore di default ( 0 ) se la stringa è vuota.
     *
     * @param text la stringa da analizzare
     * @param fieldName il nome del campo (utilizzato per i messaggi di errore)
     * @return il numero intero analizzato o 0 se la stringa è vuota
     * @throws NumberFormatException se la stringa non può essere convertita in intero
     */
    public int parseInteger (String text, String fieldName) throws NumberFormatException {
        if (text == null || text.trim().isEmpty()) {
            return 0; //  un valore di default appropriato
        }
        return Integer.parseInt(text);
    }
    /**
     * Analizza un numero a virgola mobile da una stringa e fornisce un valore di default ( 0.0f ) se la stringa è vuota.
     *
     * @param text la stringa da analizzare
     * @param fieldName il nome del campo (utilizzato per i messaggi di errore)
     * @return il numero a virgola mobile analizzato o 0.0f se la stringa è vuota
     * @throws NumberFormatException se la stringa non può essere convertita in float
     */
    private float parseFloat (String text, String fieldName) throws NumberFormatException {
        if (text == null || text.trim().isEmpty()) {
            return 0.0f; // -1 un valore di default appropriato
        }
        return Float.parseFloat(text);
    }
    /**
     * Torna alla finestra precedente.
     *
     * @throws NotBoundException se il servizio remoto non è stato trovato
     * @throws RemoteException se si verifica un errore di comunicazione remota
     */
public void backin() throws NotBoundException, RemoteException {
        new AreaRiservataOperatorFrame().setVisible(true);
        dispose();


}

    /**
     * Metodo principale per avviare l'applicazione.
     *
     * @param args gli argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InsParametriClimaticiFrame().setVisible(true);
        });
    }
}
