package ventana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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
            while(true) {
                String nombre = entradaCliente.readLine();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
