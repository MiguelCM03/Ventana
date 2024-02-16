package ventana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ManejadorCliente implements Runnable {
    private Socket socket;
    private BufferedReader entrada;
    private Servidor server;
    private String mensajeError;

    public ManejadorCliente(Socket socket, Servidor server) {
        this.socket = socket;
        this.server = server;
        mensajeError = "error";
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            //Contador de mensajes recibidos, el primero será el nombre, por lo que tenog que controlar que no haya nadie con ese nombre ya registrado en el chat
            int contadorMensajes = 0;
            int posicionUsuario = server.getNombres().size()-1;
            String nombreUsuarioNuevo;
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                if(contadorMensajes == 0){//El primer mensaje es el nombre, comprueba si existe o no, y permite que el usuario se conecte o no
                    nombreUsuarioNuevo = mensaje;
                    for (String s : server.getNombres()){
                        if(nombreUsuarioNuevo.equalsIgnoreCase(s)){
                            server.enviarMensajePosicion(posicionUsuario, mensajeError);
                        }
                    }
                }else{//En el resto de mensajes los añade a la conversacion y los envía.
                    server.conversacion += mensaje+"\n";
                    server.enviarMensaje(mensaje);
                }
                contadorMensajes++;
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
