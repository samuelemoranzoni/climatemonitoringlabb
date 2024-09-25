import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AreaRiservataOperatorFrame extends JFrame {

    public AreaRiservataOperatorFrame() {

        super("Area Riservata Operatore");

        JPanel panel1=new JPanel();
        JLabel titolo=new JLabel("\n area riservata dell' operatore: " + "\n "  );
        titolo.setFont(new Font("Arial", Font.BOLD, 20));
        titolo.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel1.setLayout(new FlowLayout());
        panel1.add(titolo);
        JPanel panel2=new JPanel();
        panel2.setLayout(new GridLayout(4, 1));

        // Creazione dei pulsanti per le funzionalità richieste
        JButton addParametersButton = new JButton("Aggiungi parametri di monitoraggio");
        JButton createCenterButton = new JButton("Crea centri di monitoraggio");
        JButton addAreaButton = new JButton("Inserisci un'area d'interesse");
        JButton backButton = new JButton("Torna al menu principale");

        panel2.add(addParametersButton);
        panel2.add(createCenterButton);
        panel2.add(addAreaButton);
        panel2.add(backButton);

        // Listener per il pulsante "Aggiungi parametri di monitoraggio"
        addParametersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InsParametriClimaticiFrame();

              /*  SistemaInserimento sistemaInserimento = new SistemaInserimento();
                sistemaInserimento.inserisciParametriClimatici();
                JOptionPane.showMessageDialog(null, "Parametri climatici aggiunti con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE); */
            }
        });

        // Listener per il pulsante "Crea centri di monitoraggio"
        createCenterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*SistemaInserimento sistemaInserimento = new SistemaInserimento();
                String nomeCentro = sistemaInserimento.insCentroAree();
                JOptionPane.showMessageDialog(null, "Centro di monitoraggio '" + nomeCentro + "' creato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE); */

                new CenterCreationFrame();
            }
        });

        // Listener per il pulsante "Inserisci un'area d'interesse"
        addAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              /*  SistemaInserimento sistemaInserimento = new SistemaInserimento();
                sistemaInserimento.insArea();
                JOptionPane.showMessageDialog(null, "Area d'interesse inserita con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE); */
                new InsAreeInteresseFrame();
            }
        });

        // Listener per il pulsante "Torna al menu principale"
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ClimateMonitoringGUI.main(null);
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLayout(new BorderLayout());
        this.add(panel1,BorderLayout.NORTH);
        this.add(panel2);
        this.setVisible(true);
    }

    public static void main(String[] args) {

        new AreaRiservataOperatorFrame();
    }
}

