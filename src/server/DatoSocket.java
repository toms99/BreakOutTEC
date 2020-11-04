package server;

/*
 * Javier Abellán. 9 Dic 2003
 *
 * DatoSocket.java
 * Dato que se va a enviar entre servidores y clientes java y c.
 * 
 * Compilar:                sudo /usr/java/jdk-11.0.9+11/bin/javac SocketCliente.java DatoSocket.java
 */

import java.io.*;

/**
 * Dato para enviar por el socket. Sus atributos son simples y una Clase Atributo
 */
public class DatoSocket implements Serializable
 {
   /**
   * Constructor. Guarda la cadena en el atributo d y calcula su longitud para guardarla
   * en el atricuto c.
   */
   public DatoSocket (String cadena)
   {
      // Si la cadena no es null, se guarda la cadena y su longitud 
      if (cadena != null)
      {
         c = cadena.length();
         d = cadena;
      }
   }

   /** Primer atributo, un int */
   public int c = 0;
     
   /** Segundo atributo, un String */
   public String d = "";
     
   /** Método para devolver un String en el que se represente el valor de
    * todos los atributos. */
   public String toString ()
   {
       String resultado;
       resultado = d;
       return resultado;
   }

   /**
   * Método para escribir los atributos de esta clase en un DataOutputStream de forma
   * que luego pueda entenderlos un programa en C.
   */
   public void writeObject(java.io.DataOutputStream out)
         throws IOException
     {
         // Se envía la longitud de la cadena + 1 por el \0 necesario en C
         out.writeInt (c+1);

         // Se envía la cadena como bytes.
         out.writeBytes (d);

         // Se envía el \0 del final
         out.writeByte ('\0');
     }
    
      /**
      * Método que lee los atributos de esta clase de un DataInputStream tal cual nos los
      * envía un programa en C.
      * Este método no contempla el caso de que se envíe una cadena "", es decir, un
      * único \0.
      */
     public void readObject(java.io.DataInputStream in)
     throws IOException
     {
         // Se lee la longitud de la cadena y se le resta 1 para eliminar el \0 que
         // nos envía C.
         c = in.readInt() - 1;
         
         // Array de bytes auxiliar para la lectura de la cadena.
         byte [] aux = null;
         
         aux = new byte[c];    // Se le da el tamaño 
         in.read(aux, 0, c);   // Se leen los bytes
         d = new String (aux); // Se convierten a String
         in.read(aux,0,1);     // Se lee el \0
     }
}
