
    import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

    public class VisualizzaAreaGeograficaFrame extends JFrame { /*
        private JTextField searchField;
        private JComboBox<String> tipoRicercaComboBox;
        private JList<AreaGeografica> risultatiList;
        private DefaultListModel<AreaGeografica> listModel;
        private JTextArea parametriArea;
        private JButton cercaButton, visualizzaButton;
        private RemoteService stub;

        public VisualizzaAreaGeograficaFrame() {
            setTitle("Ricerca Aree e Visualizzazione Parametri");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            try {
                Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                stub = (RemoteService) registry.lookup("RemoteService");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore di connessione al server", "Errore", JOptionPane.ERROR_MESSAGE);
            }

            initComponents();
        }

        private void initComponents() {
            setLayout(new BorderLayout(10, 10));

            // Panel di ricerca
            JPanel searchPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            tipoRicercaComboBox = new JComboBox<>(new String[]{"Denominazione", "Stato", "Coordinate"});
            searchField = new JTextField(20);
            cercaButton = new JButton("Cerca");

            gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
            searchPanel.add(new JLabel("Tipo di ricerca:"), gbc);
            gbc.gridx = 1; gbc.weightx = 0.7;
            searchPanel.add(tipoRicercaComboBox, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
            searchPanel.add(new JLabel("Valore di ricerca:"), gbc);
            gbc.gridx = 1; gbc.weightx = 0.7;
            searchPanel.add(searchField, gbc);

            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            searchPanel.add(cercaButton, gbc);

            add(searchPanel, BorderLayout.NORTH);

            // Lista risultati
            listModel = new DefaultListModel<>();
            risultatiList = new JList<>(listModel);
            risultatiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane listScrollPane = new JScrollPane(risultatiList);

            // Area parametri
            parametriArea = new JTextArea(10, 40);
            parametriArea.setEditable(false);
            JScrollPane parametriScrollPane = new JScrollPane(parametriArea);

            // Split pane per risultati e parametri
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listScrollPane, parametriScrollPane);
            splitPane.setResizeWeight(0.5);
            add(splitPane, BorderLayout.CENTER);

            // Pulsante visualizza
            visualizzaButton = new JButton("Visualizza Parametri");
            visualizzaButton.setEnabled(false);
            add(visualizzaButton, BorderLayout.SOUTH);

            // Listeners
            cercaButton.addActionListener(e -> eseguiRicerca());
            visualizzaButton.addActionListener(e -> visualizzaParametri());
            risultatiList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    visualizzaButton.setEnabled(risultatiList.getSelectedIndex() != -1);
                }
            });
        }

        private void eseguiRicerca() {
            listModel.clear();
            parametriArea.setText("");
            String tipoRicerca = (String) tipoRicercaComboBox.getSelectedItem();
            List<AreaGeografica> risultati = null;

            try {
                switch (tipoRicerca) {
                    case "Denominazione":
                        risultati = stub.(searchField.getText());
                        break;
                    case "Stato":
                        risultati = stub.cercaAreaGeograficaPerStato(searchField.getText());
                        break;
                    case "Coordinate":
                        String[] coords = searchField.getText().split(",");
                        if (coords.length != 2) {
                            throw new IllegalArgumentException("Inserire le coordinate nel formato: latitudine,longitudine");
                        }
                        double lat = Double.parseDouble(coords[0].trim());
                        double lon = Double.parseDouble(coords[1].trim());
                        risultati = stub.cercaPerCoordinate(lat, lon);
                        break;
                }

                if (risultati != null && !risultati.isEmpty()) {
                    for (AreaGeografica area : risultati) {
                        listModel.addElement(area);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nessun risultato trovato.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante la ricerca: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void visualizzaParametri() {
            AreaGeografica areaSelezionata = risultatiList.getSelectedValue();
            if (areaSelezionata != null) {
                try {
                    List<ParametriClimatici> datiClimatici = stub.visualizzaDatiClimatici(areaSelezionata.getDenominazione());
                    if (datiClimatici != null && !datiClimatici.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Parametri climatici per ").append(areaSelezionata.getDenominazione()).append(":\n\n");
                        for (ParametriClimatici pc : datiClimatici) {
                            sb.append(pc.toString()).append("\n\n");
                        }
                        parametriArea.setText(sb.toString());
                    } else {
                        parametriArea.setText("Nessun dato climatico disponibile per l'area selezionata.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Errore durante il recupero dei dati climatici: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new VisualizzaAreaGeograficaFrame().setVisible(true));
        }  */
    }

