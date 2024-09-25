import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CenterCreationFrame extends JFrame {

    private JTextField nomeCentroField;
    private JTextField indirizzoField;
    private DefaultListModel<String> areeDiInteresseModel;
    private JList<String> areeDiInteresseList;
    private List<String> areeDiInteresse;

    public CenterCreationFrame() {
        super("Creazione Centro di Monitoraggio");

        // Inizializzazione
        nomeCentroField = new JTextField(20);
        indirizzoField = new JTextField(20);
        areeDiInteresseModel = new DefaultListModel<>();
        areeDiInteresseList = new JList<>(areeDiInteresseModel);
        areeDiInteresse = new ArrayList<>();

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Nome Centro:"));
        panel.add(nomeCentroField);

        panel.add(new JLabel("Indirizzo:"));
        panel.add(indirizzoField);

        panel.add(new JLabel("Aree di Interesse:"));
        panel.add(new JScrollPane(areeDiInteresseList));

        JButton addAreaButton = new JButton("Aggiungi Area di Interesse");
        JButton removeAreaButton = new JButton("Rimuovi Area Selezionata");
        JButton saveButton = new JButton("Salva Centro");
        JButton backButton = new JButton("Torna Indietro");

        panel.add(addAreaButton);
        panel.add(removeAreaButton);
        panel.add(saveButton);
        panel.add(backButton);

        this.add(panel);

        // Action Listeners
        addAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String area = JOptionPane.showInputDialog("Inserisci l'area di interesse:");
                if (area != null && !area.trim().isEmpty()) {
                    areeDiInteresseModel.addElement(area);
                }
            }
        });

        removeAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = areeDiInteresseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    areeDiInteresseModel.remove(selectedIndex);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //verifica salvataggio
                String nomeCentro = nomeCentroField.getText();
                String indirizzo = indirizzoField.getText();
                if (nomeCentro.trim().isEmpty() || indirizzo.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nome e indirizzo non possono essere vuoti.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // creazione lista Aree di interesse
                ArrayList<String> aree = new ArrayList<>();
                for (int i = 0; i < areeDiInteresseModel.size(); i++) {
                    aree.add(areeDiInteresseModel.get(i));
                }

                SistemaInserimento si=new SistemaInserimento();
                si.insCentroAree(nomeCentro,indirizzo,aree);
                JOptionPane.showMessageDialog(null,"Salvataggio centro effettuato con successo");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              /*  setVisible(false);
                new AreaRiservataOperatorFrame(); // Torna al frame dell'area riservata dell'operatore */
                dispose();
            }
        });

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.setVisible(true);
    }



    public static void main(String[] args) {
        new CenterCreationFrame ();
    }
}
