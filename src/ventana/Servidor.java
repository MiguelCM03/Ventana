package ventana;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private int piContador;
    private ServerSocket pssServerSocket;
    private ExecutorService pesExecutor;
    private List<PrintWriter> ploClientes;
    private ArrayList<String> plsNombres;
    public String conversacion;

    public Servidor() {
        piContador = 0;
        ploClientes = new ArrayList<>();
        plsNombres = new ArrayList<>();
        pesExecutor = Executors.newCachedThreadPool();

        try {
            pssServerSocket = new ServerSocket(33333);

            while (true) {
                Socket socket = pssServerSocket.accept();
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                ploClientes.add(salida);

                // Hilo para manejar la comunicación con el cliente
                pesExecutor.execute(new ManejadorCliente(socket, this));
                //Añado el mensaje a la conversacion y me guardo la conversacion entera, para que cuanodo un usuario nuevo se conecte, la vea entera, incluso los mensajes anteriores a su conexión.
                if(conversacion!=null) { enviarMensajePosicion(piContador, conversacion); }
                piContador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pesExecutor.shutdown();
        }
    }

    public void enviarMensaje(String mensaje) {
        for (PrintWriter cliente : ploClientes) {
            cliente.println(mensaje);
        }
    }
    public ArrayList<String> getPlsNombres(){
        return this.plsNombres;
    }

    public void enviarMensajePosicion(int posicion, String mensaje) {
        ploClientes.get(posicion).println(mensaje);
    }

    public static void main(String[] args) {
        new Servidor();
    }
}
