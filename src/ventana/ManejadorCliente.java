package ventana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ManejadorCliente implements Runnable {
    private Socket socket;
    private BufferedReader entrada;
    private Servidor server;

    public ManejadorCliente(Socket socket, Servidor server) {
        this.socket = socket;
        this.server = server;

        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                server.conversacion += mensaje+"\n";
                server.enviarMensaje(mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
