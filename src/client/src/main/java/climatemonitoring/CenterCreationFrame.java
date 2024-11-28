package climatemonitoring;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;

/**
 * Classe che rappresenta un frame per la creazione di un centro di monitoraggio.
 * @author Moranzoni Samuele
 *  @author Di Tullio Edoardo
 */
public class CenterCreationFrame extends JFrame {
    private JTextField nomeCentroField;
    private JTextField indirizzoField;
    private JTextField capField;
    private JTextField numero_civicoField;
    private JTextField provinciaField;
    private JTextField statoField;

    /**
     * Costruttore della classe climatemonitoring.CenterCreationFrame.
     * Inizializza il frame, i componenti e imposta il layout.
     */
    public CenterCreationFrame() {
        super("Creazione Centro di Monitoraggio");
        initializeFrame();
        initializeComponents();
        setupLayout();
        finalizeFrame();

    }

    /**
     * Inizializza le impostazioni del frame.
     */
    private void initializeFrame() {
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Inizializza i vari campi di input.
     */
    private void initializeComponents() {
        nomeCentroField = createStyledTextField();
        indirizzoField = createStyledTextField();
        capField = createStyledTextField();
        numero_civicoField = createStyledTextField();
        provinciaField = createStyledTextField();
        statoField = createStyledTextField();
    }

    /**
     * Imposta il layout della finestra e aggiunge i componenti.
     */
    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Aggiungi il titolo
        addTitle(gbc);

        // Aggiungi i campi del modulo
        addLabelAndField("Nome Centro:", nomeCentroField, gbc, 1);
        addLabelAndField("Indirizzo:", indirizzoField, gbc, 2);
        addLabelAndField("CAP:", capField, gbc, 3);
        addLabelAndField("Numero Civico:", numero_civicoField, gbc, 4);
        addLabelAndField("Provincia:", provinciaField, gbc, 5);
        addLabelAndField("Stato:", statoField, gbc, 6);

        // Aggiungi i pulsanti
        addButtons(gbc);
    }

    /**
     * Aggiunge il titolo alla finestra.
     *
     * @param gbc le impostazioni di layout del GridBag
     */
    private void addTitle(GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("Inserisci Centro di Monitoraggio");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 120, 215));

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 20, 10);
        add(titleLabel, gbc);

        // Reset dei vincoli per altri componenti
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
    }

    /**
     * Aggiunge i pulsanti per l'aggiunta del centro e per tornare indietro.
     *
     * @param gbc le impostazioni di layout del GridBag
     */
    private void addButtons(GridBagConstraints gbc) {
        JButton addAreaButton = createStyledButton("Aggiungi Centro di monitoraggio");
        JButton backButton = createStyledButton("Torna Indietro");

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(addAreaButton, gbc);

        gbc.gridy = 8;
        add(backButton, gbc);

        // Aggiungi i listener agli action per i pulsanti
        addAreaButton.addActionListener(e -> performaddarea());
        backButton.addActionListener(e -> handleBackButton());
    }

    /**
     * Metodo per finalizzare le impostazioni del frame, come dimensioni e visibilità.
     */
    private void finalizeFrame() {
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Aggiunge un'etichetta e il campo di input al layout.
     *
     * @param labelText il testo da visualizzare nell'etichetta
     * @param field il campo di input da aggiungere
     * @param gbc le impostazioni di layout del GridBag
     * @param row la riga in cui aggiungere i componenti
     */
    private void addLabelAndField(String labelText, JTextField field, GridBagConstraints gbc, int row) {
        JLabel label = createStyledLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        add(field, gbc);
    }

    /**
     * Crea un campo di testo stilizzato.
     *
     * @return il campo di testo stilizzato
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMargin(new Insets(5, 5, 5, 5));
        return field;
    }

    /**
     * Crea un'etichetta stilizzata con un determinato testo.
     *
     * @param text il testo dell'etichetta
     * @return l'etichetta stilizzata
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    /**
     * Crea un pulsante stilizzato con un determinato testo.
     *
     * @param text il testo del pulsante
     * @return il pulsante stilizzato
     */
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

    /**
     * Gestisce l'azione del pulsante "Torna Indietro".
     * Chiude il frame corrente e apre l'area riservata dell'operatore.
     */
    private void handleBackButton() {
        setVisible(false);
        new AreaRiservataOperatorFrame();
        dispose();
    }

    /**
     * Mostra un messaggio di errore in una finestra di dialogo.
     *
     * @param message il messaggio di errore da visualizzare
     * @param title il titolo della finestra di dialogo
     */
    protected void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esegue l'azione di aggiunta del centro di monitoraggio.
     * Raccoglie i dati dai campi e invia la richiesta tramite RMI.
     */
    public void performaddarea() {
        try {
            String nomecentro = nomeCentroField.getText();
            String indirizzo = indirizzoField.getText();
            String capcentro = capField.getText();
            String numero_civico = numero_civicoField.getText();
            String provincia = provinciaField.getText();
            String stato = statoField.getText();

            // Controllo se i campi sono vuoti
            if (nomecentro.isEmpty() || indirizzo.isEmpty() || capcentro.isEmpty() || numero_civico.isEmpty() || provincia.isEmpty() || stato.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Inserisci tutti i campi ",
                        "Campi vuoti",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RemoteService stub = (RemoteService) registry.lookup("climatemonitoring.RemoteService");
            int operatoreid = OperatoreSession.getInstance().getOperatore().getId();

            // Invio della richiesta per creare il centro
            int risultato = stub.createCentroMonitoraggio(nomecentro, indirizzo, capcentro, numero_civico, provincia, stato, operatoreid);

            // -1 error: campi null; -2 error: unique; -3 errore generale
            if (risultato == -1) {
                JOptionPane.showMessageDialog(this,
                        "Inserisci tutti i campi ",
                        "Campi vuoti",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (risultato == -2) {
                JOptionPane.showMessageDialog(this,
                        " indirizzo già presente, controlla che il centro che stai cercando di inserire non esista già  ",
                        "Campi unique",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (risultato == -3) {
                JOptionPane.showMessageDialog(this,
                        " errore di connessione: riprova ",
                        "errore di connessione",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (risultato > 0) {
                JOptionPane.showMessageDialog(this, "Registrazione centro avvenuta con successo. ID nuovo centro: " + risultato);
                // Aggiorno il centro dell'operatore che sta lavorando ora
                OperatoreSession.getInstance().getOperatore().setCentroMonitoraggioId(risultato);
                System.out.println("Aggiorno il centro ID dell'operatore che sta lavorando ora");
                new AreaRiservataOperatorFrame().setVisible(true);
                this.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }
    }

    /**
     * Metodo principale per avviare l'applicazione.
     *
     * @param args gli argomenti della riga di comando
     */
    public static void main(String[] args) {
        new CenterCreationFrame();
    }
}

