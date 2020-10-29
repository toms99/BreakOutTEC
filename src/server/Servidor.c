/*
* Javier Abellan, 9 Dic 2003
*
* Programa servidor en C para conectarse con un cliente java.
*
*
* Para compilar:    make Servidor
*					./Servidor
*/
#include </home/chago/Documents/GitHub/BreakOutTEC/libraries/Socket_Servidor.h>
#include </home/chago/Documents/GitHub/BreakOutTEC/libraries/Socket.h>
#include <string.h>
#include <stdio.h>

main ()
{
	/*
	* Descriptores de socket servidor y de socket con el cliente
	*/
	int Socket_Servidor;
	int Socket_Cliente;
   int Aux;
   int Longitud_Cadena;
	char Cadena[100];

	/*
	* Se abre el socket servidor, con el servicio "cpp_java" dado de
	* alta en /etc/services. El número de dicho servicio debe ser 35557, que es
   * el que se ha puesto en el código java del cliente.
	*/
	Socket_Servidor = Abre_Socket_Inet ("cpp_java");
	if (Socket_Servidor == -1)
	{
		printf ("No se puede abrir socket servidor\n");
		exit (-1);
	}

	/*
	* Se espera un cliente que quiera conectarse
	*/
	Socket_Cliente = Acepta_Conexion_Cliente (Socket_Servidor);
	if (Socket_Servidor == -1)
	{
		printf ("No se puede abrir socket de cliente\n");
		exit (-1);
	}

   /*
    * Se envia un entero con la longitud de una cadena (incluido el \0 del final) y
    * la cadena.
    */
   Longitud_Cadena = 5;
   strcpy (Cadena, "Hola");

   /* El entero que se envía por el socket hay que transformalo a formato red */
   Aux = htonl (Longitud_Cadena);

   /* Se envía el entero transformado */
   Escribe_Socket (Socket_Cliente, (char *)&Aux, sizeof(Longitud_Cadena));
   printf ("Servidor C: Enviado %d\n", Longitud_Cadena-1);
  
   /* Se envía la cadena */
   Escribe_Socket (Socket_Cliente, Cadena, Longitud_Cadena);
   printf ("Servidor C: Enviado %s\n", Cadena);
   
   
	/*
	* Se lee la informacion del cliente, primero el número de caracteres de la cadena
   * que vamos a recibir (incluido el \0) y luego la cadena.
	*/
   Lee_Socket (Socket_Cliente, (char *)&Aux, sizeof(Longitud_Cadena));

   /* El entero recibido hay que transformarlo de formato red a formato hardware */
   Longitud_Cadena = ntohl(Aux);
   printf ("Servidor C: Recibido %d\n", Longitud_Cadena-1);
  
   /* Se lee la cadena */
	Lee_Socket (Socket_Cliente, Cadena, Longitud_Cadena);
   printf ("Servidor C: Recibido %s\n", Cadena);

	/*
	* Se cierran los sockets
	*/
	close (Socket_Cliente);
	close (Socket_Servidor);
}