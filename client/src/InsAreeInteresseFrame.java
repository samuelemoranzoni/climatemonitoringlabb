import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class InsAreeInteresseFrame extends JFrame {
    private JTextField nomeAreaField;
    private JTextField nazioneField;
    private JTextField latitudineField;
    private JTextField longitudineField;
    private JButton saveButton;
    private JButton backButton;

    public InsAreeInteresseFrame() {
        super("Inserimento Area di Interesse");
        initializeFrame();
        initializeComponents();
        setupLayout();
        addActionListeners();
        finalizeFrame();
    }

    private void initializeFrame() {
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeComponents() {
        nomeAreaField = createStyledTextField();
        nazioneField = createStyledTextField();
        latitudineField = createStyledTextField();
        longitudineField = createStyledTextField();
        saveButton = createStyledButton("Aggiungi Area");
        backButton = createStyledButton("Torna Indietro");
    }

    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add title
        addTitle(gbc);

        // Add form fields
        addLabelAndField("Nome Area:", nomeAreaField, gbc, 1);
        addLabelAndField("Nazione:", nazioneField, gbc, 2);
        addLabelAndField("Latitudine:", latitudineField, gbc, 3);
        addLabelAndField("Longitudine:", longitudineField, gbc, 4);

        // Add buttons
        addButtons(gbc);
    }

    private void addTitle(GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("Inserisci Area di Interesse");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 120, 215));

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 20, 10);
        add(titleLabel, gbc);

        // Reset constraints for other components
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
    }

    private void addButtons(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(saveButton, gbc);

        gbc.gridy = 6;
        add(backButton, gbc);
    }

    private void addActionListeners() {
        saveButton.addActionListener(e -> {
            try {
                performAreaInteresseInserimento();
            } catch (RemoteException | NotBoundException ex) {
                showErrorMessage("Errore di connessione", "Errore di Sistema");
                ex.printStackTrace();
            }
        });
         backButton.addActionListener(e -> {


             try {
                 backin();
             } catch (NotBoundException ex) {
                 throw new RuntimeException(ex);
             } catch (RemoteException ex) {
                 throw new RuntimeException(ex);
             }
         });
    }




    private void addLabelAndField(String labelText, JTextField field, GridBagConstraints gbc, int row) {
        JLabel label = createStyledLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        add(field, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMargin(new Insets(5, 5, 5, 5));
        return field;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

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

    private void finalizeFrame() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }

    public void backin() throws NotBoundException, RemoteException {

        new AreaRiservataOperatorFrame().setVisible(true);
        this.dispose();
    }


    private void performAreaInteresseInserimento() throws RemoteException, NotBoundException {
        String nomeArea = nomeAreaField.getText().trim();
        String nazione = nazioneField.getText().trim();
        String latitudine = latitudineField.getText().trim();
        String longitudine = longitudineField.getText().trim();
        float latitudinef = 0.0f;
        float longitudinef = 0.0f;

        // Validazione dei campi vuoti
        if (nomeArea.isEmpty() || nazione.isEmpty() || latitudine.isEmpty() || longitudine.isEmpty()) {
            showErrorMessage("Tutti i campi devono essere compilati.", "Campi Mancanti");
            return;
        }

        // Parsing e validazione coordinate
        try {
            latitudinef = Float.parseFloat(latitudine);
            longitudinef = Float.parseFloat(longitudine);
        } catch (NumberFormatException e) {
            showErrorMessage("Inserisci un numero valido per latitudine e longitudine", "Valori Inconsistenti");
            return;
        }

        // Verifica centro monitoraggio
        Integer centroMonitoraggioId = OperatoreSession.getInstance().getOperatore().getCentroMonitoraggioId();
        if (centroMonitoraggioId == null) {
            showErrorMessage("Non puoi registrare una nuova area di interesse se non lavori per un centro di monitoraggio",
                    "Centro Monitoraggio Mancante");
            return;
        }

        // Comunicazione con il server
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RemoteService stub = (RemoteService) registry.lookup("RemoteService");

            int risultato = stub.insertAreeInteresse(latitudinef, longitudinef, nomeArea, nazione, centroMonitoraggioId);

            handleServerResponse(risultato);
        } catch (Exception e) {
            showErrorMessage("Errore di connessione al server", "Errore di Rete");
            e.printStackTrace();
        }
    }

    private void handleServerResponse(int risultato) throws RemoteException, NotBoundException {
        switch (risultato) {
            case -1:
                showErrorMessage("Errore di connessione al server: riprova", "Errore di Connessione");
                break;
            case -2:
                showErrorMessage("Alcuni campi di inserimento risultano essere nulli", "Campi Nulli");
                break;
            case -3:
                showErrorMessage("È già presente nel database un'area di interesse con questa denominazione ufficiale e stato",
                        "Duplicato");
                break;
            default:
                if (risultato > 0) {
                    showInfoMessage("Inserimento area di interesse con id: " + risultato + " eseguito con successo");
                    new AreaRiservataOperatorFrame();
                    dispose();
                }
        }
    }
}