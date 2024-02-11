package ventana;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UsuarioChat {
    public static void main(String[] args) {

        try {
            MulticastSocket socket=new MulticastSocket();
            InterfazBasica pantalla = new InterfazBasica();
            while(true) {

                String wsMensaje = pantalla.getMensaje();
                byte[] mensaje = wsMensaje.getBytes();
                int puerto = 33333;
                DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, InetAddress.getByName("225.0.0.1"), puerto);
                socket.send(dp);
                if(wsMensaje.equalsIgnoreCase("fin")){
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
