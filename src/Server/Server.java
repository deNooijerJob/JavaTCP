package Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * Created by Job de Nooijer on 26/04/2018.
 * Simple TCP Server example
 */
public class Server {

    private ServerSocket socket;
    private Socket connection;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;



    /**
     * Method that starts te server
     * @param portNumber the port number the server will be running on
     * @param backLog the number of clients that can connect to this server
     */
    public void startServer(int portNumber, int backLog) {
        try {
            socket = new ServerSocket(portNumber, backLog);
            while (true) {
                try {
                    waitForClient();
                    setupStream();
                    whileActiveStream();
                } catch (EOFException eofException) {
                    showMessage("connection disconnected");
                } finally {
                    closeBackTrack();
                }
            }
        } catch (IOException ioException) {
            showMessage("Exception occured");
            ioException.printStackTrace();

        }
    }

    /**
     * waits for a client to connect to the server
     * @throws IOException when there is an issue connecting to the socket
     */
    private void waitForClient () throws IOException {
        showMessage("Waiting for connection");
        connection = socket.accept();
        showMessage("Connection established with: " + connection.getInetAddress().getHostName());
    }

    /**
     * Sets up the stream on the socket
     * @throws IOException when there is an issue setting up the stream
     */
    private void setupStream () throws IOException {
        outputStream = new ObjectOutputStream(connection.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(connection.getInputStream());
        showMessage("Streams are setup");
    }

    /**
     * executes while there is an active stream
     * @throws IOException when there is an issue during the connection
     */
    private void whileActiveStream () throws IOException {
       String message = "";
        do {
            try {
                message = (String) inputStream.readObject();
                showMessage(message);
            } catch (ClassNotFoundException classNotFoundException) {
                System.out.println("format not accepted");
            }

        } while (!message.equals("CLIENT - END"));
    }

    /**
     * safely shuts down the connection
     */
    private void closeBackTrack () {
        showMessage("closing connection");
        try {
            outputStream.close();
            inputStream.close();
            connection.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Method for displaying server messages
     * Change this method to display it in the way you want to a gui or to a file
     * @param message a string that captures the message
     */
    private void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * method that sends the message to the server
     * @param message string containing the actual message to be sent
     */
    public void sendMessage(String message) {
        try {
            outputStream.writeObject("SERVER - " + message);
            outputStream.flush();
            showMessage("SERVER - " + message);
        } catch (IOException ioException) {
            showMessage("something went wrong");
        }
    }

}
