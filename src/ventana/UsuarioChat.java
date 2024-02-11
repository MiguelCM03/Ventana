package ventana;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class UsuarioChat {
    public static void main(String[] args) {

        try {
            MulticastSocket socket=new MulticastSocket();
            InterfazBasica pantalla = new InterfazBasica();
            PrintWriter pw = new PrintWriter(System.out);
            Scanner tcd = new Scanner(System.in);
            while(true){
                String wsMensaje = pantalla.getMensaje();
                pw.println(wsMensaje);
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
