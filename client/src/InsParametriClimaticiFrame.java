import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsParametriClimaticiFrame extends JFrame {
    // Definizione dei campi di input
    private JTextField centroMonitoraggioField;
    private JTextField areaInteresseField;
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

    public InsParametriClimaticiFrame() {
        super("Inserisci Parametri Climatici");

        // Creazione del pannello principale che conterrà tutti i componenti
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10));

        // Creazione dei campi di input e delle relative etichette
        mainPanel.add(new JLabel("Centro di Monitoraggio:"));
        centroMonitoraggioField = new JTextField();
        mainPanel.add(centroMonitoraggioField);

        mainPanel.add(new JLabel("Area di Interesse:"));
        areaInteresseField = new JTextField();
        mainPanel.add(areaInteresseField);

        mainPanel.add(new JLabel("Data di Rilevazione:"));
        dataRilevazioneField = new JTextField();
        mainPanel.add(dataRilevazioneField);

        mainPanel.add(new JLabel("Velocità del Vento (km/h):"));
        velocitaVentoField = new JTextField();
        mainPanel.add(velocitaVentoField);

        mainPanel.add(new JLabel("Score Vento (1-5):"));
      scoreVentoSpinner = createScoreSpinner();
        mainPanel.add(scoreVentoSpinner);

        mainPanel.add(new JLabel("Nota Vento:"));
       notaVentoField = new JTextField();
        mainPanel.add(notaVentoField);

        mainPanel.add(new JLabel("% di Umidità:"));
       umiditaField = new JTextField();
        mainPanel.add(umiditaField);

        mainPanel.add(new JLabel("Score Umidità (1-5):"));
        scoreUmiditaSpinner = createScoreSpinner();
        mainPanel.add(scoreUmiditaSpinner);

        mainPanel.add(new JLabel("Nota Umidità:"));
        notaUmiditaField = new JTextField();
        mainPanel.add(notaUmiditaField);

        mainPanel.add(new JLabel("Pressione (hPa):"));
         pressioneField = new JTextField();
        mainPanel.add(pressioneField);

        mainPanel.add(new JLabel("Score Pressione (1-5):"));
         scorePressioneSpinner = createScoreSpinner();
        mainPanel.add(scorePressioneSpinner);

        mainPanel.add(new JLabel("Nota Pressione:"));
         notaPressioneField = new JTextField();
        mainPanel.add(notaPressioneField);

        mainPanel.add(new JLabel("Temperatura (°C):"));
         temperaturaField = new JTextField();
        mainPanel.add(temperaturaField);

        mainPanel.add(new JLabel("Score Temperatura (1-5):"));
         scoreTemperaturaSpinner = createScoreSpinner();
        mainPanel.add(scoreTemperaturaSpinner);

        mainPanel.add(new JLabel("Nota Temperatura:"));
        notaTemperaturaField = new JTextField();
        mainPanel.add(notaTemperaturaField);

        mainPanel.add(new JLabel("Precipitazioni (mm):"));
     precipitazioniField = new JTextField();
        mainPanel.add(precipitazioniField);

        mainPanel.add(new JLabel("Score Precipitazioni (1-5):"));
        scorePrecipitazioniSpinner = createScoreSpinner();
        mainPanel.add(scorePrecipitazioniSpinner);

        mainPanel.add(new JLabel("Nota Precipitazioni:"));
         notaPrecipitazioniField = new JTextField();
        mainPanel.add(notaPrecipitazioniField);

        mainPanel.add(new JLabel("Altitudine dei Ghiacciai (m):"));
         altitudineGhiacciaiField = new JTextField();
        mainPanel.add(altitudineGhiacciaiField);

        mainPanel.add(new JLabel("Score Altitudine Ghiacciai (1-5):"));
      scoreAltitudineGhiacciaiSpinner = createScoreSpinner();
        mainPanel.add(scoreAltitudineGhiacciaiSpinner);

        mainPanel.add(new JLabel("Nota Altitudine Ghiacciai:"));
         notaAltitudineGhiacciaiField = new JTextField();
        mainPanel.add(notaAltitudineGhiacciaiField);

        mainPanel.add(new JLabel("Massa dei Ghiacciai (Kg):"));
      massaGhiacciaiField = new JTextField();
        mainPanel.add(massaGhiacciaiField);

        mainPanel.add(new JLabel("Score Massa Ghiacciai (1-5):"));
       scoreMassaGhiacciaiSpinner = createScoreSpinner();
        mainPanel.add(scoreMassaGhiacciaiSpinner);

        mainPanel.add(new JLabel("Nota Massa Ghiacciai:"));
        notaMassaGhiacciaiField = new JTextField();
        mainPanel.add(notaMassaGhiacciaiField);

        // Pulsante per salvare i parametri
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Centrare il pulsante
        JButton saveButton = new JButton("Salva Parametri");
        JButton backbutton=new JButton("Indietro");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Recupero dei valori dai campi di testo e spinner
                    String centroMonitoraggio = centroMonitoraggioField.getText();
                    String areaInteresse = areaInteresseField.getText();
                    String dataRilevazione = dataRilevazioneField.getText();

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

                    // Memorizziamo i nuovi riferimenti nel file
                    SistemaInserimento si = new SistemaInserimento();
                    si.inserisciParametriClimatici(
                            centroMonitoraggio, areaInteresse, dataRilevazione,
                            velocitaVento, scoreVento, notaVento,
                            umidita, scoreUmidita, notaUmidita,
                            pressione, scorePressione, notaPressione,
                            temperatura, scoreTemperatura, notaTemperatura,
                            precipitazioni, scorePrecipitazioni, notaPrecipitazioni,
                            altitudineGhiacciai, scoreAltitudineGhiacciai, notaAltitudineGhiacciai,
                            massaGhiacciai, scoreMassaGhiacciai, notaMassaGhiacciai
                    );

                    JOptionPane.showMessageDialog(null, "Parametri salvati con successo.");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Errore nei dati inseriti. Assicurati che tutti i numeri siano validi.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
/*Questi metodi gestiscono le conversioni e forniscono valori predefiniti se i campi sono vuoti. Se il campo è vuoto
 o contiene un valore non valido, viene restituito un valore di default (0 per gli interi e 0.0f per i float).*/
            private int parseInteger(String text, String fieldName) throws NumberFormatException {
                if (text == null || text.trim().isEmpty()) {
                    return 0; //  un valore di default appropriato
                }
                return Integer.parseInt(text);
            }

            private float parseFloat(String text, String fieldName) throws NumberFormatException {
                if (text == null || text.trim().isEmpty()) {
                    return 0.0f; // -1 un valore di default appropriato
                }
                return Float.parseFloat(text);
            }
        });

        backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(backbutton);
        // Aggiunta della barra di scorrimento
        JScrollPane scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 600);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.PAGE_END);
        this.setVisible(true);
    }

    private JSpinner createScoreSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        // Personalizzazione per ridurre le dimensioni
        spinner.setPreferredSize(new Dimension(25, 25));
        // Riduzione della dimensione del pulsante dello spinner
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setPreferredSize(new Dimension(50, 25));
        return spinner;
    }

    public static void main(String[] args) {
        new InsParametriClimaticiFrame();
    }
}
