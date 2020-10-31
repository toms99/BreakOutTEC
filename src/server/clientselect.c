/*
 * Javier Abellán. 14 de Abril de 2003
 *
 * Ejemplo de como un servidor puede manejar varios clientes con select().
 * Este programa hace de cliente de dicho servidor.
 */
#include <../libraries/Socket_Cliente.h>
#include <../libraries/Socket.h>

/* Programa principal. Abre la conexión, recibe su número de cliente y
 * luego envía dicho número cada segundo */
main()
{
	int sock;		/* descriptor de conexión con el servidor */
	int buffer1;		/* buffer de lectura de datos procedentes del servidor */
	int buffer2;
	int error;		/* error de lectura por el socket */
	int error2;

	/* Se abre una conexión con el servidor */
	sock = Abre_Conexion_Inet ("localhost", "cpp_java");

	/* Se lee el número de cliente, dato que nos da el servidor. Se escribe
	 * dicho número en pantalla.*/
	error = Lee_Socket (sock, (char *)&buffer1, sizeof(int));
	error2 = Lee_Socket (sock, (char *)&buffer2, sizeof(int));

	/* Si ha habido error de lectura lo indicamos y salimos */
	if (error < 1)
	{
		printf ("Me han cerrado la conexión\n");
		exit(-1);
	}

	/* Se escribe el número de cliente que nos ha enviado el servidor */
	printf ("Soy cliente %d\n", buffer1);
	printf ("Soy cliente %d\n", buffer2);

	/* Bucle infinito. Envia al servidor el número de cliente y espera un
	 * segundo */
		Escribe_Socket (sock, (char *)&buffer1, sizeof(int));
		Escribe_Socket (sock, (char *)&buffer2, sizeof(int));

}
