all: servselect clientselect

# Directorio en el que están los ficheros Socket.h, Socket_Servidor.h,
# Socket_Cliente.h y libChSocket.a
LIBCHSOCKET = /home/chago/Documents/GitHub/BreakOutTEC/libraries

servselect: servselect.c
	cc servselect.c -I$(LIBCHSOCKET) -L$(LIBCHSOCKET) -lChSocket -o servselect

clientselect: clientselect.c
	cc clientselect.c -I$(LIBCHSOCKET) -L$(LIBCHSOCKET) -lChSocket -o clientselect
