package ventana;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UsuarioChat {
    public static void main(String[] args) {

        try {
            InterfazBasica pantalla = new InterfazBasica();
            String ipServer = "localhost";
            int puerto = 33333;
            Socket clienteSocket = new Socket(ipServer, puerto);

            PrintWriter salidaServer = new PrintWriter(clienteSocket.getOutputStream(), true);
            BufferedReader entradaServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));

            String msgNombre = entradaServer.readLine();
            //String nombre = JOptionPane.showInputDialog(null, msgNombre);

            //salidaServer.println(nombre);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
