;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.text.SimpleDateFormat;

public class VisualizzaCommentiFrame extends JFrame {
    private RemoteService stub;
    private List<Note> noteList;
    private String areaDaCercare;

    public VisualizzaCommentiFrame(String area) throws NotBoundException, RemoteException {
        this.areaDaCercare = area;
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        stub = (RemoteService) registry.lookup("RemoteService");
        this.noteList = stub.getNote(areaDaCercare);
        initializeUI();
    }

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
        backButton.addActionListener(e ->
        {
            try {
                new VisualizzaParametriFrame(areaDaCercare);
            } catch (NotBoundException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        })
                ;
        bottomPanel.add(backButton);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

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

    private void addField(JPanel panel, GridBagConstraints gbc, String label, String value) {
        JLabel fieldLabel = new JLabel(label + ":", JLabel.LEFT);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(fieldLabel, gbc);

        JLabel fieldValue = new JLabel(value != null && !value.isEmpty() ? value : "N/A", JLabel.LEFT);
        fieldValue.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(fieldValue, gbc);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new VisualizzaCommentiFrame("Roma");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

