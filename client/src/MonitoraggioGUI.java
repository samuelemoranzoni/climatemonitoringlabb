import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonitoraggioGUI { /*
    private JFrame frame;
    private JPanel panel;
    private JTextField nomeCentroTextField;
    private JTextField areaInteresseTextField;
    private JButton aggiungiCentroButton;

    public MonitoraggioGUI() {
        frame = new JFrame("Sistema di Monitoraggio");
        panel = new JPanel();

        // Impostazione della finestra
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aggiunta dei componenti
        panel.setLayout(null);

        JLabel nomeCentroLabel = new JLabel("Nome Centro:");
        nomeCentroLabel.setBounds(10, 20, 80, 25);
        panel.add(nomeCentroLabel);

        nomeCentroTextField = new JTextField(20);
        nomeCentroTextField.setBounds(150, 20, 165, 25);
        panel.add(nomeCentroTextField);

        JLabel areaInteresseLabel = new JLabel("Area Interesse:");
        areaInteresseLabel.setBounds(10, 50, 100, 25);
        panel.add(areaInteresseLabel);

        areaInteresseTextField = new JTextField(20);
        areaInteresseTextField.setBounds(150, 50, 165, 25);
        panel.add(areaInteresseTextField);

        aggiungiCentroButton = new JButton("Aggiungi Centro");
        aggiungiCentroButton.setBounds(10, 80, 150, 25);
        panel.add(aggiungiCentroButton);

        frame.add(panel);

        // Impostazione dell'azione del pulsante
        aggiungiCentroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCentro = nomeCentroTextField.getText();
                String areaInteresse = areaInteresseTextField.getText();

                // Interagisci con il backend per aggiungere il centro
                CentroMonitoraggio centro = new CentroMonitoraggio(nomeCentro, areaInteresse);
                //SistemaAccesso.aggiungiCentro(centro);
                SistemaAggiunta sa=new SistemaAggiunta();
                sa.AggiungiCentro(nomeCentro,areaInteresse);
                // Messaggio di conferma
                JOptionPane.showMessageDialog(frame, "Centro aggiunto con successo!");
            }
        });
    }

    public void mostra() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MonitoraggioGUI gui = new MonitoraggioGUI();
        gui.mostra();
    }
    */
}