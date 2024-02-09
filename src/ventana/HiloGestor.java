package ventana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloGestor extends Thread{
    private Socket cliente;
    public HiloGestor(Socket cliente){
        this.cliente = cliente;
    }

    @Override
    public void run(){
        try {
            BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salidaCliente = new PrintWriter(cliente.getOutputStream());

            salidaCliente.println("Cómo te llamas?");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
