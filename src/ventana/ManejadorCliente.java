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
            boolean mensajeEsNombre = true;
            String mensaje;
            System.out.println("Usuario conectado");
            while ((mensaje = pbrEntrada.readLine()) != null) {
                    boolean encontrado = false;
                    psServer.getPlsNombres().add(mensaje);//añado al cliente a la lista
                    for (int i = 0; i<psServer.getPlsNombres().size()-1; i++){//recorro la lista sin pasar por el cliente recién añadido
                        if(psServer.getPlsNombres().get(i).equalsIgnoreCase(mensaje)){//si hay otro usuario con el mismo nombre, me lo anoto en la variable boolean "encontrado"
                            encontrado = true;
                        }
                    }
                    if(encontrado){//si he encontrado a alguien con ese nombre
                        int borrar = psServer.getPlsNombres().size()-1;//marco la posicion de la lista que tengo que borrar(el nuevo cliente que no puede conectarse porque ya hay alguien con ese nombre)
                        psServer.enviarMensajePosicion(borrar, psMensajeError);//le envio el mensaje de error
                        psServer.getPlsNombres().remove(borrar);//borro al cliente de la lista
                    }else{
                        //Si no hay nadie con ese nombre, lo añado a la lista de nomrbes
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
