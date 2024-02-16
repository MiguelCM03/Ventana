package ventana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ManejadorCliente implements Runnable {
    private Socket psSocket;
    private BufferedReader pbrEntrada;
    private Servidor psServer;
    private String psMensajeError;

    public ManejadorCliente(Socket socket, Servidor server) {
        this.psSocket = socket;
        this.psServer = server;
        psMensajeError = "error";
        try {
            pbrEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            //Contador de mensajes recibidos, el primero será el nombre, por lo que tenog que controlar que no haya nadie con ese nombre ya registrado en el chat
            int contadorMensajes = 0;
            int posicionUsuario = psServer.getPlsNombres().size()-1;
            String nombreUsuarioNuevo;
            String mensaje;
            while ((mensaje = pbrEntrada.readLine()) != null) {
                if(contadorMensajes == 0){//El primer mensaje es el nombre, comprueba si existe o no, y permite que el usuario se conecte o no
                    nombreUsuarioNuevo = mensaje;
                    for (String s : psServer.getPlsNombres()){
                        if(nombreUsuarioNuevo.equalsIgnoreCase(s)){
                            psServer.enviarMensajePosicion(posicionUsuario, psMensajeError);
                        }
                    }
                }else{//En el resto de mensajes los añade a la conversacion y los envía.
                    psServer.conversacion += mensaje+"\n";
                    psServer.enviarMensaje(mensaje);
                }
                contadorMensajes++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                psSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
