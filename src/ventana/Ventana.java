package ventana;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {

    private JButton btnEnviar;
    private JTextArea txtMensaje;


    public Ventana(){
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Titulo");
        setResizable(false);
        this.getContentPane().setBackground(Color.gray);
        iniciarComponentes();
    }

    private void iniciarComponentes(){
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.X_AXIS));

        //texto mensaje
        txtMensaje = new JTextArea();
        txtMensaje.setVisible(true);
        txtMensaje.setBackground(Color.red);
        pnlMain.add(txtMensaje, BorderLayout.SOUTH);
        txtMensaje.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));


        //boton enviar
        btnEnviar = new JButton("Enviar");
        btnEnviar.setSize(100, 20);
        btnEnviar.setVisible(true);
        btnEnviar.setBackground(Color.green);
        pnlMain.add(btnEnviar, BorderLayout.LINE_END);

        this.getContentPane().add(pnlMain);
    }


}