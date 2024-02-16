package ventana;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class UsuarioChat extends JFrame {
    private Socket psCliente;
    private BufferedReader pbrEntrada;
    private PrintWriter ppwSalida;
    private JTextField txtMensaje;
    private JTextArea txtConversacion;
    private JTextField txtNombre;
    private String psNombre;

    public UsuarioChat() {
        // Solicitar el nombre al principio de la ejecución
        String wsNombre = JOptionPane.showInputDialog("Ingrese su nombre:");
        psNombre = wsNombre;
        setTitle("Chat - " + wsNombre);

        // Configuración de la interfaz gráfica del cliente
        txtNombre = new JTextField(wsNombre);
        txtNombre.setEditable(false);

        txtMensaje = new JTextField();
        txtMensaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarMensaje(wsNombre + ": " + e.getActionCommand());
                txtMensaje.setText("");
            }
        });

        txtConversacion = new JTextArea();
        txtConversacion.setEditable(false);

        add(txtNombre, "North");
        add(txtMensaje, "South");
        add(new JScrollPane(txtConversacion), "Center");

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Conexión al servidor
        try {
            psCliente = new Socket("localhost", 33333);
            pbrEntrada = new BufferedReader(new InputStreamReader(psCliente.getInputStream()));
            ppwSalida = new PrintWriter(psCliente.getOutputStream(), true);
            ppwSalida.println(psNombre);

            // Hilo para recibir mensajes del servidor
            new Thread(new Runnable() {
                public void run() {
                    recibirMensajes();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje(String mensaje) {
        ppwSalida.println(mensaje);
    }

    private void recibirMensajes() {
        try {
            String mensaje;
            while ((mensaje = pbrEntrada.readLine()) != null) {
                if(mensaje.contains("error")){
                    txtConversacion.setText("No se puede conectar, ya hay alguien con ese nombre. Cerrando conexión...");
                    Thread.sleep(2000);
                    System.exit(1);
                }else{
                    txtConversacion.append(mensaje + "\n");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UsuarioChat();
            }
        });
    }
}

