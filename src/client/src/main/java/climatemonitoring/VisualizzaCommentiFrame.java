package climatemonitoring;



import climatemonitoring.extensions.DatabaseConnectionException;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Classe che rappresenta un frame per visualizzare i commenti relativi a un'area specifica.
 * @author Moranzoni Samuele
 *  @author Di Tullio Edoardo
 */
public class VisualizzaCommentiFrame extends JFrame {
    private RemoteService stub;
    private List<Note> noteList;
    private String areaDaCercare;

    /**
     * Costruttore della classe climatemonitoring.VisualizzaCommentiFrame.
     *
     * @param area L'area per la quale si desidera visualizzare i commenti.
     * @throws NotBoundException Se il servizio remoto non è registrato nel registry.
     * @throws RemoteException In caso di errore nella comunicazione remota.
     */
    public VisualizzaCommentiFrame(String area) throws DatabaseConnectionException {
        this.areaDaCercare = area;
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            stub = (RemoteService) registry.lookup("climatemonitoring.RemoteService");
            noteList=stub.getNote(areaDaCercare);
            initializeUI();
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);


        }
    }

    /**
     * Inizializza l'interfaccia utente.
     */
    private void initializeUI() {
        setTitle("Visualizza Commenti - Area: " + areaDaCercare);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 30)); // Aumentato lo spazio verticale
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Commenti per l'area: " + areaDaCercare, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 120, 215));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        if (noteList != null && !noteList.isEmpty()) {
            for (Note note : noteList) {
                JPanel notePanel = createNotePanel(note);
                contentPanel.add(notePanel);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        } else {
            JLabel noCommentsLabel = new JLabel("Nessun commento trovato per quest'area.");
            noCommentsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noCommentsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JPanel noCommentsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            noCommentsPanel.setBackground(Color.WHITE);
            noCommentsPanel.add(noCommentsLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Spazio aggiuntivo sopra il messaggio
            contentPanel.add(noCommentsPanel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        JButton backButton = createStyledButton("Torna Indietro");
        backButton.addActionListener(e -> {
            new VisualizzaParametriFrame(areaDaCercare);
            dispose();
        });
        bottomPanel.add(backButton);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Crea un pannello per visualizzare i dettagli di una nota.
     *
     * @param note La nota da visualizzare.
     * @return Un pannello contenente i dettagli della nota.
     */
    private JPanel createNotePanel(Note note) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 215)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addField(panel, gbc, "Centro Monitoraggio", String.valueOf(note.getCentroMonitoraggioId()));
        addField(panel, gbc, "Operatore ID", String.valueOf(note.getOperatoreId()));
        addField(panel, gbc, "Data Rilevazione", new SimpleDateFormat("dd/MM/yyyy").format(note.getDatarilevazione()));
        addField(panel, gbc, "Nota Vento", note.getNotaVento());
        addField(panel, gbc, "Nota Umidità", note.getNotaUmidita());
        addField(panel, gbc, "Nota Pressione", note.getNotaPressione());
        addField(panel, gbc, "Nota Temperatura", note.getNotaTemperatura());
        addField(panel, gbc, "Nota Precipitazioni", note.getNotaPrecipitazioni());
        addField(panel, gbc, "Nota Altitudine Ghiacciai", note.getNotaAltitudineGhiacciai());
        addField(panel, gbc, "Nota Massa Ghiacciai", note.getNotaMassaGhiacciai());

        return panel;
    }

    /**
     * Aggiunge un campo al pannello della nota.
     *
     * @param panel Il pannello al quale aggiungere il campo.
     * @param gbc   Le costanti di layout per la posizione del campo.
     * @param label L'etichetta del campo.
     * @param value Il valore del campo.
     */
    private void addField(JPanel panel, GridBagConstraints gbc, String label, String value) {
        JLabel fieldLabel = new JLabel(label + ":", JLabel.LEFT);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(fieldLabel, gbc);

        JLabel fieldValue = new JLabel(value != null && !value.isEmpty() ? value : "N/A", JLabel.LEFT);
        fieldValue.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(fieldValue, gbc);
    }

    /**
     * Crea un pulsante stilizzato.
     *
     * @param text Il testo del pulsante.
     * @return Un pulsante stilizzato.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    /**
     * Metodo principale per avviare l'applicazione.
     *
     * @param args Argomenti della riga di comando (non utilizzati).
     */
    public static void main(String[] args) {

    }
}
