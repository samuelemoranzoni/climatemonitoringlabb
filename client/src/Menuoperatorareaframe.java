import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Menuoperatorareaframe extends JFrame {
JFrame previousframe;
    public Menuoperatorareaframe(JFrame frame) {
        this.previousframe=frame; //salva un riferimento al frame precedente
        setTitle("Area Operatori");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel1=new JPanel();
        panel1.setLayout(new GridLayout(1, 2 ));

        JButton accessbutton=new JButton("accedi");

        JButton registerButton = new JButton("Registrati");

        panel1.add(accessbutton);
        panel1.add(registerButton);


        accessbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new AccessoFrame();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();
            }
        });


        JPanel panel2=new JPanel();
        panel2.setLayout(new FlowLayout());
        JButton buttonback=new JButton("torna indietro");
        buttonback.addActionListener(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {
                                         previousframe.setVisible(true);
                                         dispose();
                                         }
                                     }
        );
        panel2.add(buttonback);
        this.setLayout(new BorderLayout());
        add(panel1,BorderLayout.NORTH);
        JLabel titolo = new JLabel("<html><div style='text-align: center;'>Benvenuto nell'area operatore,<br>se gestisci un centro di monitoraggio hai la possibilità di inserire dati di tipo climatici relativi a zone di interesse,<br>accessibili dai cittadini.</div></html>");
        titolo.setFont(new Font("Arial", Font.BOLD, 16)); // Imposta il testo in grassetto e la dimensione a 16
        titolo.setHorizontalAlignment(JLabel.CENTER); // Centra il testo
        add(titolo, BorderLayout.CENTER);
        add(panel2,BorderLayout.SOUTH);
        setVisible(true);
    }


}