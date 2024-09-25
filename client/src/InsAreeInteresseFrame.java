import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsAreeInteresseFrame extends JFrame {

    private JTextField nomeAreaField;
    private JTextField nazioneField;
    private JTextField latitudineField;
    private JTextField longitudineField;

    public InsAreeInteresseFrame() {
        super("Inserimento Area di Interesse");

        // Inizializzazione dei campi di testo
        nomeAreaField = new JTextField(20);
        nazioneField = new JTextField(20);
        latitudineField = new JTextField(20);
        longitudineField = new JTextField(20);

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Nome Area:"));
        panel.add(nomeAreaField);

        panel.add(new JLabel("Nazione:"));
        panel.add(nazioneField);

        panel.add(new JLabel("Latitudine:"));
        panel.add(latitudineField);

        panel.add(new JLabel("Longitudine:"));
        panel.add(longitudineField);

        JButton saveButton = new JButton("Aggiungi Area");
        JButton backButton = new JButton("Torna Indietro");

        panel.add(saveButton);
        panel.add(backButton);

        this.add(panel);

        // Action Listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeArea = nomeAreaField.getText();
                String nazione = nazioneField.getText();
                String latitudine = latitudineField.getText();
                String longitudine = longitudineField.getText();

                // Validazione dei dati
                if (nomeArea.trim().isEmpty() || nazione.trim().isEmpty() ||
                        latitudine.trim().isEmpty() || longitudine.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tutti i campi devono essere compilati.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String coordinate = latitudine + ", " + longitudine;
                SistemaInserimento si=new SistemaInserimento();
                si.insArea(nomeArea,nazione,coordinate);
                JOptionPane.showMessageDialog(null, "Inserimento area eseguito con successo");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Torna al frame dell'area riservata dell'operatore
                dispose();
            }
        });

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.setVisible(true);
    }

    private void addArea() {
    /*    String nomeArea = nomeAreaField.getText();
        String nazione = nazioneField.getText();
        String latitudine = latitudineField.getText();
        String longitudine = longitudineField.getText();

        // Validazione dei dati
        if (nomeArea.trim().isEmpty() || nazione.trim().isEmpty() ||
                latitudine.trim().isEmpty() || longitudine.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi devono essere compilati.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String coordinate = latitudine + ", " + longitudine;

        // Crea l'oggetto AreeInteresse (Assicurati di avere una classe AreeInteresse con il metodo toCsvString())
        AreeInteresse areaDati = new AreeInteresse(nomeArea, nazione, coordinate);

        // Salva i dati in un file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data\\CoordinateMonitoraggio.dati.csv", true))) {
            writer.write(areaDati.toCsvString() + "\n");
            JOptionPane.showMessageDialog(this, "Area di interesse aggiunta con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
            // Pulisce i campi dopo l'inserimento
            nomeAreaField.setText("");
            nazioneField.setText("");
            latitudineField.setText("");
            longitudineField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio.", "Errore", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }*/
    }

    public static void main(String[] args) {
        new InsAreeInteresseFrame();
    }
}
