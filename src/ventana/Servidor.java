package ventana;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private List<PrintWriter> clientes;

    public Servidor() {
        clientes = new ArrayList<>();
        executor = Executors.newCachedThreadPool();

        try {
            serverSocket = new ServerSocket(12345);

            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                clientes.add(salida);

                // Hilo para manejar la comunicación con el cliente
                executor.execute(new ManejadorCliente(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private void enviarMensaje(String mensaje) {
        for (PrintWriter cliente : clientes) {
            cliente.println(mensaje);
        }
    }

    private class ManejadorCliente implements Runnable {
        private Socket socket;
        private BufferedReader entrada;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;

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
                    enviarMensaje(mensaje);
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

    public static void main(String[] args) {
        new Servidor();
    }
}
