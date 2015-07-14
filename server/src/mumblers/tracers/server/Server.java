package mumblers.tracers.server;

import mumblers.tracers.common.Constants;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Sinius15 on 14-7-2015.
 * The server where games can be played
 */
public class Server {

    private ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(Constants.SERVER_PORT);
        serverSocket.accept();
    }

}
