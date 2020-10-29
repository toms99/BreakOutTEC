#
# Javier Abellán. 9 Dic 2003
#
# Makefile para la construcción del cliente y servidor que conectan con un servidor
# y cliente de java.
#
all : Servidor Cliente

# Coloca aqui el path donde te hayas descargado y compilado la mini-librería de sockets
PATH_CHSOCKET=/home/chago/Documents/GitHub/BreakOutTEC/libraries

CFLAGS = -g -I. -I$(PATH_CHSOCKET)

Servidor : Servidor.o
	cc Servidor.o -L$(PATH_CHSOCKET) -lChSocket -o Servidor

Cliente : Cliente.o
	cc Cliente.o -L$(PATH_CHSOCKET) -lChSocket -o Cliente

clean :
	rm *.o Cliente Servidor
