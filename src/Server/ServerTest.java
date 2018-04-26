package Server;

/**
 * Created by jobde on 26/04/2018.
 */
public class ServerTest {

    static final int portNumber = 6789;
    static final int backLog = 100;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(portNumber, backLog);
        //server.sendMessage("hello world");
    }
}
