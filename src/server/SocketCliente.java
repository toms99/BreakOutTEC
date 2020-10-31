package server;
/*
 * Javier Abellán. 9 Dic 2003
 *
 * SocketCliente.java
 *
 * Ejemplo de cliente en java que se conecta con un servidor en C.
 */

import java.net.*;
import java.io.*;

/**
 * Clase que crea un socket cliente, establece la conexión y lee los datos
 * del servidor, escribiéndolos en pantalla.
 */

public class SocketCliente
 {    

    /** Programa principal, crea el socket cliente */
    public static void main (String [] args)
    {
        new SocketCliente();
    }
     /**
      * Crea el socket cliente y lee los datos
      */
     public SocketCliente()
     {
         try
         {
             /* Se crea el socket cliente */
             Socket socket = new Socket ("localhost", 15557);
             System.out.println ("Conexión exitosa...");
             System.out.println ("Conectado al puerto 15557...");

             socket.setSoLinger (true, 10);

            DataInputStream bufferEntrada = new DataInputStream (socket.getInputStream());            // Se contruye el stream de entrada
            DatoSocket aux = new DatoSocket(""); // Para guardar el dato leido del socket
            aux.readObject (bufferEntrada); // Se lee del socket.
            System.out.println ("Cliente:" + aux.toString());


             /* Se obtiene un flujo de envio de datos para enviar un dato al servidor 
             DataOutputStream bufferSalida =
               new DataOutputStream (socket.getOutputStream());

             Se crea el dato y se escribe en el flujo de salida 
             DatoSocket aux1 = new DatoSocket ("Adios");
             aux.writeObject (bufferSalida);

             System.out.println ("Cliente Java: Enviado " + aux.toString());*/
            socket.close();
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
     }
}