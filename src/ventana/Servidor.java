package ventana;

import java.io.IOException;
import java.net.*;

public class Servidor {

    public static void main(String[] args) {
        try {
            int puerto = 33333;
            String ip = "225.0.0.1";
            MulticastSocket multicastSocket = new MulticastSocket(puerto);
            InetAddress host = InetAddress.getByName(ip);
            multicastSocket.joinGroup(host);
            while(true) {
                byte[] buffer=new byte[256];
                DatagramPacket entrada=new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(entrada);
                String texto = new String(entrada.getData()).trim();
                if(texto.equalsIgnoreCase("fin")) {
                    System.out.println("Conexion cerrada");
                    multicastSocket.leaveGroup(host);
                    multicastSocket.close();
                    break;
                }else{
                    System.out.println("recibido:" + texto);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
