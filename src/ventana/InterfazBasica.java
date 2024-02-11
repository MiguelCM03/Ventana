package ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazBasica extends JFrame {

    private JTextArea txtUsuariosConectados;
    private JTextArea txtMensajesEnviados;
    private JTextField txtMensaje;
    private String nombreUsuario;

    public InterfazBasica() {
        // Ventana inicial para obtener el nombre del usuario
        nombreUsuario = JOptionPane.showInputDialog(null, "Por favor, ingresa tu nombre:");

        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            // Configuración de la ventana principal
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Crear componentes
            txtUsuariosConectados = new JTextArea();
            txtUsuariosConectados.setEditable(false);
            String usuarios = txtUsuariosConectados.getText();
            txtUsuariosConectados.setText(usuarios+nombreUsuario);//pongo el nombre del usuario conectado en la lista de usuarios en el chat

            JScrollPane scrollPaneSuperior = new JScrollPane(txtUsuariosConectados);

            txtMensajesEnviados = new JTextArea();
            txtMensajesEnviados.setBackground(Color.lightGray);
            txtMensajesEnviados.setCaretColor(Color.white);
            txtMensajesEnviados.setEditable(false);

            JScrollPane scrollPaneCentral = new JScrollPane(txtMensajesEnviados);

            txtMensaje = new JTextField();
            JButton btnEnviar = new JButton("Enviar");
            btnEnviar.setBackground(Color.blue);
            btnEnviar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    enviarMensaje();
                }
            });

            // Organizar la interfaz
            JPanel panelSuperior = new JPanel(new BorderLayout());
            panelSuperior.add(scrollPaneSuperior, BorderLayout.CENTER);

            JPanel panelCentral = new JPanel(new BorderLayout());
            panelCentral.add(scrollPaneCentral, BorderLayout.CENTER);
            panelCentral.add(txtMensaje, BorderLayout.SOUTH);

            JPanel panelDerecho = new JPanel(new BorderLayout());
            panelDerecho.add(btnEnviar, BorderLayout.SOUTH);

            // Contenedor principal
            Container container = getContentPane();
            container.setLayout(new BorderLayout());
            container.add(panelSuperior, BorderLayout.NORTH);
            container.add(panelCentral, BorderLayout.CENTER);
            container.add(panelDerecho, BorderLayout.EAST);

            setVisible(true);
        } else {
            // Si el usuario no ingresa un nombre, cierra la aplicación
            JOptionPane.showMessageDialog(null, "No ingresaste un nombre. La aplicación se cerrará.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public String getNombreUsuario(){
        return this.nombreUsuario;
    }

    public String getMensaje(){
        return this.txtMensaje.getText();
    }

    private void enviarMensaje() {
        String mensaje = txtMensaje.getText();
        if (!mensaje.isEmpty()) {
            txtMensajesEnviados.append(mensaje + "\n");
            txtMensaje.setText("");
        }
    }
}
