package ventana;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        try {
            int puerto = 33333;
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado esperando a que se ejecuten los usuarios del chat...");

            while (true){
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Usuario conectado.");
                Thread hilo = new Thread(new HiloGestor(clienteSocket));
                hilo.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
