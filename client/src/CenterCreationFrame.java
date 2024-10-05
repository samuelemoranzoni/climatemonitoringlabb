import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class CenterCreationFrame extends JFrame {
    private JTextField nomeCentroField;
    private JTextField indirizzoField;
    private JTextField capField;
    private JTextField numero_civicoField;
    private JTextField provinciaField;
    private JTextField statoField;

    public CenterCreationFrame() {
        super("Creazione Centro di Monitoraggio");
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
        nomeCentroField = createStyledTextField();
        indirizzoField = createStyledTextField();
        capField = createStyledTextField();
        numero_civicoField = createStyledTextField();
        provinciaField = createStyledTextField();
        statoField = createStyledTextField();
    }

    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add title
        addTitle(gbc);

        // Add form fields
        addLabelAndField("Nome Centro:", nomeCentroField, gbc, 1);
        addLabelAndField("Indirizzo:", indirizzoField, gbc, 2);
        addLabelAndField("CAP:", capField, gbc, 3);
        addLabelAndField("Numero Civico:", numero_civicoField, gbc, 4);
        addLabelAndField("Provincia:", provinciaField, gbc, 5);
        addLabelAndField("Stato:", statoField, gbc, 6);

        // Add buttons
        addButtons(gbc);
    }

    private void addTitle(GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("Inserisci Centro di Monitoraggio");
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
        JButton addAreaButton = createStyledButton("Aggiungi Centro di monitoraggio");
        JButton backButton = createStyledButton("Torna Indietro");

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(addAreaButton, gbc);

        gbc.gridy = 8;
        add(backButton, gbc);

        // Add action listeners to buttons
        addAreaButton.addActionListener(e -> performaddarea());
        backButton.addActionListener(e -> handleBackButton());
    }
    private void addActionListeners() {
        // Any additional action listeners can be added here if needed
    }

    private void finalizeFrame() {
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
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

    private void handleBackButton() {
        setVisible(false);
        try {
            new AreaRiservataOperatorFrame();
        } catch (RemoteException | NotBoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore durante il ritorno all'area riservata",
                    "Errore di Sistema",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        dispose();
    }

    protected void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }


    public void performaddarea(){
        try {

            String nomecentro = nomeCentroField.getText();
            String indirizzo = indirizzoField.getText();
            String capcentro = capField.getText();
            String numero_civico = numero_civicoField.getText();
            String provincia = provinciaField.getText();
            String stato = statoField.getText();

            if (nomecentro.isEmpty() || indirizzo.isEmpty() || capcentro.isEmpty() || numero_civico.isEmpty() || provincia.isEmpty() || stato.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Inserisci tutti i campi ",
                        "Campi vuoti",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RemoteService stub = (RemoteService) registry.lookup("RemoteService");
            int operatoreid = OperatoreSession.getInstance().getOperatore().getId();

            int risultato = stub.createCentroMonitoraggio(nomecentro, indirizzo, capcentro, numero_civico, provincia, stato, operatoreid);

            // -1 error : di campi null   -2 error unique -3 errore generale

            if (risultato == -1) {
                JOptionPane.showMessageDialog(this,
                        "Inserisci tutti i campi ",
                        "Campi vuoti",
                        JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (risultato == -2) {
                JOptionPane.showMessageDialog(this,
                        " indirizzo già presente , controlla che il centro che stai cercando di inserire non esista già  ",
                        "Campi unique",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (risultato == -3) {
                JOptionPane.showMessageDialog(this,
                        "  errore di connessione : riprova ",
                        "errore di connessione",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(risultato > 0){
                JOptionPane.showMessageDialog(this, "Registrazione centro avvenuta con successo. ID nuovo centro: " + risultato);
                //aggiorno il centro dell'operatore che sta lavorando ora
                OperatoreSession.getInstance().getOperatore().setCentroMonitoraggioId(risultato);
                System.out.println("aggiorno il centro id dell'operatore che sta lavorando ora");
                new AreaRiservataOperatorFrame().setVisible(true);
                this.dispose();
            }
        }

        catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "errore di connessione",
                    "connessione fallita",
                    JOptionPane.ERROR_MESSAGE);

        }

}

    public static void main(String[] args) {
        new CenterCreationFrame ();
    }




}