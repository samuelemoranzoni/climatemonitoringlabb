import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RicercaVisualizzazioneFrame extends JFrame {
    private JTextField queryField;
    private JComboBox<String> areaComboBox;
    private JTextArea resultArea;
    private JLabel statusLabel;
    private SistemaRicerca sistemaRicerca = new SistemaRicerca();
    private SistemaSelezione sistemaSelezione = new SistemaSelezione();

    public RicercaVisualizzazioneFrame() {
        setTitle("Ricerca e Visualizzazione Aree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Campo di testo per la query
        queryField = new JTextField();
        add(queryField, BorderLayout.NORTH);

        // Pannello per il bottone di ricerca e il label di stato
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton searchButton = new JButton("Cerca");
        statusLabel = new JLabel(" ");
        topPanel.add(queryField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        topPanel.add(statusLabel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Elenco a discesa per le aree
        areaComboBox = new JComboBox<>();
        areaComboBox.setEnabled(false); // Inizialmente disabilitato
        add(areaComboBox, BorderLayout.WEST);

        // Pannello per i risultati
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Azione del bottone di ricerca
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = queryField.getText();
                if (query.trim().isEmpty()) {
                    statusLabel.setText("Inserisci una query di ricerca.");
                    return;
                }

                List<String> results = sistemaRicerca.cercaAreaGeografica(query);
                if (results.isEmpty()) {
                    statusLabel.setText("Nessuna area trovata.");
                    areaComboBox.removeAllItems();
                    areaComboBox.setEnabled(false);
                } else {
                    statusLabel.setText("");
                    areaComboBox.removeAllItems();
                    for (String area : results) {
                        String[] fields = area.split(";");
                        areaComboBox.addItem(fields[0]);
                    }
                    areaComboBox.setEnabled(true);
                }
            }
        });

        // Azione per la selezione dell'area
        areaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedArea = (String) areaComboBox.getSelectedItem();
                if (selectedArea != null && !selectedArea.trim().isEmpty()) {
                    List<String> areaDetails = sistemaSelezione.visualizzaAreaGeografica(selectedArea);
                    if (areaDetails.isEmpty()) {
                        resultArea.setText("Dati non disponibili.");
                    } else {
                        StringBuilder resultText = new StringBuilder();
                        for (String detail : areaDetails) {
                            resultText.append(detail).append("\n");
                        }
                        resultArea.setText(resultText.toString());
                    }
                }
            }
        });

        // Pannello inferiore con il pulsante di chiusura
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton buttonBack = new JButton("Indietro");
        bottomPanel.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RicercaVisualizzazioneFrame();
            }
        });
    }
}
