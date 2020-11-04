/*
 * Javier Abellán. 14 de Abril de 2003
 *
 * Ejemplo de como un servidor puede manejar varios clientes con select().
 * Este programa hace de servidor.3
 * Puerto 35557
 */
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <../libraries/Socket_Servidor.h>
#include <../libraries/Socket.h>

#define MAX_CLIENTES 3

struct datosTableroInicial
{
	int filas; 
	int columnas;
	int ladrilloRojo;
	int ladrilloVerde;
	int ladrilloAmarillo;
	int ladrilloNaranja;
};

struct datosObjetos
{
	bool play;
	int posxbol;
	int posybol; 
	int dirxbol; 
	int dirybol; 
	int anchoraquet; 
	int posxraq;
	int posyraq;
};


/* Prototipos de las funciones definidas en este fichero */
void nuevoCliente (int servidor, int *clientes, int *nClientes, struct datosTableroInicial tab);
int dameMaximo (int *tabla, int n);
void compactaClaves (int *tabla, int *n);
struct datosTableroInicial setTablero(void);
void sendTableroInicial (struct datosTableroInicial tab, int servidor, int *clientes, int *nClientes);



/*
 * Programa principal.
 * Crea un socket servidor y se mete en un select() a la espera de clientes.
 * Cuando un cliente se conecta, le atiende y lo añade al select() y vuelta
 * a empezar.
 */
int main()	
{
	int socketServidor;				/* Descriptor del socket servidor */
	int socketCliente[MAX_CLIENTES];/* Descriptores de sockets con clientes */
	int numeroClientes = 0;			/* Número clientes conectados */
	fd_set descriptoresLectura;	    /* Descriptores de interes para select() */
	int buffer;						/* Buffer para leer de los socket */
	int maximo;						/* Número de descriptor más grande */
	int i;							/* Para bubles */
	char Cadena[100];
	int Aux;
    int Longitud_Cadena;
	struct datosTableroInicial datosIn;
	struct datosObjetos datoObj;


	/* Se abre el socket servidor, avisando por pantalla y saliendo si hay 
	 * algún problema */
	socketServidor = Abre_Socket_Inet ("cpp_java");
	if (socketServidor != -1)
	{
		datosIn = setTablero();
		perror ("\nApertura de servidor...\nEsperando conexiones...\n");
	}else
	{	
		perror ("Error al abrir servidor");
		exit (-1);
	}
	
	/* Bucle infinito.
	 * Se atiende si hay más clientes para conectar y a los mensajes enviados
	 * por los clientes ya conectados */
	while (1)
	{
		/* Cuando un cliente cierre la conexión, se pondrá un -1 en su descriptor
		 * de socket dentro del array socketCliente. La función compactaClaves()
		 * eliminará dichos -1 de la tabla, haciéndola más pequeña.
		 * 
		 * Se eliminan todos los clientes que hayan cerrado la conexión */
		compactaClaves (socketCliente, &numeroClientes);
		
		/* Se inicializa descriptoresLectura */
		FD_ZERO (&descriptoresLectura);

		/* Se añade para select() el socket servidor */
		FD_SET (socketServidor, &descriptoresLectura);

		/* Se añaden para select() los sockets con los clientes ya conectados */
		for (i=0; i<numeroClientes; i++)
			FD_SET (socketCliente[i], &descriptoresLectura);

		/* Se el valor del descriptor más grande. Si no hay ningún cliente,
		 * devolverá 0 */
		maximo = dameMaximo (socketCliente, numeroClientes);
		
		if (maximo < socketServidor)
			maximo = socketServidor;

		/* Espera indefinida hasta que alguno de los descriptores tenga algo
		 * que decir: un nuevo cliente o un cliente ya conectado que envía un
		 * mensaje */
		select (maximo + 1, &descriptoresLectura, NULL, NULL, NULL);


		/* Se comprueba si algún cliente ya conectado ha enviado algo */
		for (i=0; i<numeroClientes; i++)
		{
			
			if (FD_ISSET (socketCliente[i], &descriptoresLectura))
			{	
				
				/* Se lee lo enviado por el cliente y se escribe en pantalla */
				if ((Lee_Socket (socketCliente[i], (char *)&buffer, sizeof(int)) > 0))
					
					printf ("Cliente %d envía %d\n", i+1, buffer);
					
				else
				{
					/* Se indica que el cliente ha cerrado la conexión y se
					 * marca con -1 el descriptor para que compactaClaves() lo
					 * elimine */
					printf ("Cliente %d ha cerrado la conexión\n", i+1);
					socketCliente[i] = -1;
				}
			}
		}

		/* Se comprueba si algún cliente nuevo desea conectarse y se le
		 * admite */
		if (FD_ISSET (socketServidor, &descriptoresLectura))
			nuevoCliente (socketServidor, socketCliente, &numeroClientes, datosIn);


	}
}

/*
 * Crea un nuevo socket cliente.
 * Se le pasa el socket servidor y el array de clientes, con el número de
 * clientes ya conectados.
 */
void nuevoCliente (int servidor, int *clientes, int *nClientes, struct datosTableroInicial tab)
{
	/* Acepta la conexión con el cliente, guardándola en el array */
	clientes[*nClientes] = Acepta_Conexion_Cliente (servidor);
	(*nClientes)++;

	/* Si se ha superado el maximo de clientes, se cierra la conexión,
	 * se deja todo como estaba y se vuelve. */
	if ((*nClientes) >= MAX_CLIENTES)
	{
		close (clientes[(*nClientes) -1]);
		(*nClientes)--;
		return;
	}

	printf ("Aceptado cliente %d\n", *nClientes);

	// Se envía una cadena de char a los clientes que se conectan con los valores iniciales del tablero:
	// número de filas y columnas, y los valores de los ladrillos según el color

	char Cadena[256];
	char num_filas[10];
	char num_columnas[10];
	char valor_rojos[10];
	char valor_verdes[10];
	char valor_amarillos[10];
	char valor_naranjas[10];

	sprintf(num_filas, "%d", tab.filas);
	sprintf(num_columnas, "%d", tab.columnas);
	sprintf(valor_rojos, "%d", tab.ladrilloRojo);
	sprintf(valor_verdes, "%d", tab.ladrilloVerde);
	sprintf(valor_amarillos, "%d", tab.ladrilloAmarillo);
	sprintf(valor_naranjas, "%d", tab.ladrilloNaranja);

	snprintf(Cadena, sizeof Cadena, "%s%s%s%s%s%s%s%s%s%s%s%s", num_filas, "f", num_columnas, "c", valor_rojos, "r", valor_verdes, "v", valor_amarillos, "a", valor_naranjas, "n");


	int contador = 0;
    // Obtiene el largo de la cadena de caracteres
    while (Cadena[++contador] != 0);
	printf ("%d", contador);
	
	int Longitud_Cadena = contador+1;


    /* El entero que se envía por el socket hay que transformalo a formato red */
    int Aux = htonl (Longitud_Cadena);

    /* Se envía el entero transformado */
    Escribe_Socket (clientes[(*nClientes)-1], (char *)&Aux, sizeof(Longitud_Cadena));
    printf ("Servidor C: Enviado %d\n", Longitud_Cadena);
  
    // Se envía la cadena 
    Escribe_Socket (clientes[(*nClientes)-1], Cadena, Longitud_Cadena);
    printf ("Servidor C: Enviado %s\n", Cadena);
	
	return;
}

/*
 * Función que devuelve el valor máximo en la tabla.
 * Supone que los valores válidos de la tabla son positivos y mayores que 0.
 * Devuelve 0 si n es 0 o la tabla es NULL */
int dameMaximo (int *tabla, int n)
{
	int i;
	int max;

	if ((tabla == NULL) || (n<1))
		return 0;
		
	max = tabla[0];
	for (i=0; i<n; i++)
		if (tabla[i] > max)
			max = tabla[i];

	return max;
}

/*
 * Busca en array todas las posiciones con -1 y las elimina, copiando encima
 * las posiciones siguientes.
 * Ejemplo, si la entrada es (3, -1, 2, -1, 4) con *n=5
 * a la salida tendremos (3, 2, 4) con *n=3
 */
void compactaClaves (int *tabla, int *n)
{
	int i,j;

	if ((tabla == NULL) || ((*n) == 0))
		return;

	j=0;
	for (i=0; i<(*n); i++)
	{
		if (tabla[i] != -1)
		{
			tabla[j] = tabla[i];
			j++;
		}
	}
	
	*n = j;
    
}


struct datosTableroInicial setTablero(void)
{
	struct datosTableroInicial datosIni;

	printf("Ingrese el número de filas de ladrillos:\n");
	scanf("%d",&datosIni.filas);
	printf("Ingrese el número de columnas de ladrillos:\n");
	scanf("%d",&datosIni.columnas);
	printf("Ingrese el valor para los ladrillos rojos:\n");
	scanf("%d",&datosIni.ladrilloRojo);
	printf("Ingrese el valor para los ladrillos verdes:\n");
	scanf("%d",&datosIni.ladrilloVerde);
	printf("Ingrese el valor para los ladrillos amarillos:\n");
	scanf("%d",&datosIni.ladrilloAmarillo);
	printf("Ingrese el valor para los ladrillos naranja\n");
	scanf("%d",&datosIni.ladrilloNaranja);
	return datosIni;
}
