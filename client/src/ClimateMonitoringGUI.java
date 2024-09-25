import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClimateMonitoringGUI {
    public static void main(String[] args) {
        // Creazione del frame principale
        JFrame frame = new JFrame("Climate Monitoring Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permette di cocnludere l'esecuzione del proigramma quando l'utente esce volontariamente dal programma
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1)); // Griglia con 4 righe e 1 colonna

        // Creazione del titolo
        JLabel titleLabel = new JLabel("Benvenuto a Climate Monitoring", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        frame.add(titleLabel);

        // Creazione dei pulsanti del menu
        JButton searchButton = new JButton("Cerca e visualizza dati raccolti");
        JButton operatorButton = new JButton("Area Operatori");
        JButton exitButton = new JButton("Esci dall'applicazione");

        // Aggiunta dei pulsanti al frame
        frame.add(searchButton);
        frame.add(operatorButton);
        frame.add(exitButton);

        // Aggiunta degli ActionListener per gestire gli eventi dei pulsanti
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Funzione di ricerca e visualizzazione selezionata.");
                // Qui puoi chiamare il metodo per la ricerca e la visualizzazione dei dati
                new RicercaVisualizzazioneFrame();
            }
        });

        operatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                JOptionPane.showMessageDialog(frame, "identificazione operatore da verificare");
                new Menuoperatorareaframe(frame);
                //JOptionPane.showMessageDialog(frame, "Area Operatori selezionata.");
                // Qui puoi chiamare il metodo per l'accesso all'area operatori
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiude l'applicazione
            }
        });

        // Rendere visibile la finestra
        frame.setVisible(true);
    }
}