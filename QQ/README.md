# QQ
This is an app similar to QQ.


### Directory Introduction
- **db** is the operation of the database.
 - **info** is used to process account and user information.
 - **util** is used to process data access object of database.
- **exception** is used to exception handling.
- **socket** is the network communication module.
 - **Client** is a client program used to establish a connection with the previous server-side program, and the information sent by the server is displayed on the standard output.
 - **Sever** is a server-side program, used to monitor the client application to establish a connection request, and send information to the client after the connection is established.
 - **SocketReaderThread** is the thread of reader.
 - **SocketWriterThread** is the thread of writer.
- **ui** include the frame and the component.
- **util** include some global function.


### Project Functions

The main function of the project is as follows:

- Multithreading
- Network Socket programming
- Transfer Control Protocol
- Data flow
- MySQL Database





### Requirements
- java
- jdk 1.7
- MySQL
- java.net.Socket, java.net.ServerSocket

