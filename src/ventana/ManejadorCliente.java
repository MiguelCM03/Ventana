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
            //Contador de mensajes recibidos, el primero será el nombre, por lo que tengo que controlar que no haya nadie con ese nombre ya registrado en el chat
//            int posicionUsuario = psServer.getPlsNombres().size()-1;
//            String nombreUsuarioNuevo;
            boolean mensajeEsNombre = true;
            String mensaje;
            System.out.println("Usuario conectado");
            while ((mensaje = pbrEntrada.readLine()) != null) {
//                if(mensajeEsNombre){
//                    mensajeEsNombre = false;
                    boolean encontrado = false;
                    int posicionNombre = 0;
                    for (int i = 0; i<psServer.getPlsNombres().size(); i++){
                        if(psServer.getPlsNombres().get(i).contains(mensaje)){
                            encontrado = true;
                            posicionNombre = i;
                        }
                    }
                    if(encontrado){
                        psServer.enviarMensajePosicion(posicionNombre, psMensajeError);
                    }else {
                        if(!mensajeEsNombre) {
                            psServer.conversacion += mensaje + "\n";
                            if(psServer.conversacion.startsWith("null")){//si el string conversacion empieza con "null", se lo quito.
                                String nuevaConversacion = "";
                                for (int i = 4; i < psServer.conversacion.length(); i++){
                                    //quito el null del principio
                                    nuevaConversacion += psServer.conversacion.charAt(i);
                                }
                                psServer.conversacion = nuevaConversacion;
                            }
                            psServer.enviarMensaje(mensaje);
                        }
                    }
                    mensajeEsNombre = false;
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
