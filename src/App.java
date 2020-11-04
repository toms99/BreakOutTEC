import GameObjects.GameLoop;
import server.DatoSocket;

import javax.swing.*;
import java.net.*;
import java.io.*;


/**
 * Hello world!
 */
public final class App {

    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        try
        {
            /* Se crea el socket cliente */
            Socket socket = new Socket ("localhost", 35557);
            System.out.println ("Conexión exitosa al puerto 35557");

            /* Se hace que el cierre espere a la recogida de todos los datos desde
            * el otro lado */
            socket.setSoLinger (true, 10);
            
            /* Se obtiene un stream de lectura para leer objetos */
            DataInputStream bufferEntrada =
               new DataInputStream (socket.getInputStream());
            
            /* Se lee un Datosocket que nos envía el servidor y se muestra 
             * en pantalla */
            DatoSocket dato = new DatoSocket("");
            dato.readObject(bufferEntrada);
            System.out.println ("Cliente Java: Recibido " + dato.toString());

            //readString("12f62c6r54v69a52n");
            String message = dato.toString();

            JFrame frame = new JFrame();
            //GameLoop gameLoop = new GameLoop(8, 14, 1000, 700, 1, 2, 3, 4);
            GameLoop gameLoop = GameLoop.getInstance(message);
            frame.setBounds(10, 10, 1000, 750);
            frame.setTitle("Break Out TEC");
            frame.setResizable(true);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(gameLoop);


            /* Se obtiene un flujo de envio de datos para enviar un dato al servidor */
            DataOutputStream bufferSalida =
              new DataOutputStream (socket.getOutputStream());

            /* Se crea el dato y se escribe en el flujo de salida */
            DatoSocket aux = new DatoSocket ("400");
            aux.writeObject (bufferSalida);

            System.out.println ("Cliente Java: Enviado " + aux.toString());
          
            /* La llamada a setSoLinger() hará que el cierre espere a que el otro
            lado retire los datos que hemos enviado */
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        

    }


}
