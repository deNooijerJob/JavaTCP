package Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Job de Nooijer on 26/04/2018.
 * simple client code to use the TCP Server
 */

public class Client {

    private ServerSocket socket;
    private Socket connection;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String serverIP;
    private int portNumber;

    public void Client(String host, int portNumber) {
        this.serverIP = host;
        this.portNumber = portNumber;
    }

    /**
     * method that connects to the server
     */
    public void connectToServer () {
        try {
            connect();
            setupStreams();
            whileActiveStream();
        } catch (EOFException eofException) {
            showMessage("connection disconnected")
        } catch (IOException ioException) {
            ioException.printStackTrace();
            showMessage("an error occured");
        } finally {
            closeBackTrack();
        }
    }

    /**
     * Method that displays the massage
     * @param message a string to be displayed
     */
    public void showMessage (String message) {
        System.out.println(message);
    }

    /**
     * safely shuts down the connection
     */
    private void closeBackTrack () {
        showMessage("closing connection");
        try {

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Method that connects to a specific server
     * @throws IOException  when a error occurs
     */
    private void connect() throws IOException {
        showMessage("attempt connection");
        connection = new Socket(InetAddress.getByName(serverIP), portNumber);
        showMessage("You are now connected to = " + connection.getInetAddress().getHostName());
         
    }

}
