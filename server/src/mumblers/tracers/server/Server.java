package mumblers.tracers.server;

import mumblers.tracers.common.Constants;
import mumblers.tracers.server.receivers.PacketReceiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinius15 on 14-7-2015.
 * The server where games can be played
 */
public class Server {

    private ServerSocket serverSocket;
    private List<PacketReceiver> receivers;

    public Server() throws IOException {
        receivers = new ArrayList<>();

        serverSocket = new ServerSocket(Constants.SERVER_PORT);
        serverSocket.accept();
    }

}
