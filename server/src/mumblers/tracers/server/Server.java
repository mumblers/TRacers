package mumblers.tracers.server;

import mumblers.tracers.common.Constants;
import mumblers.tracers.common.Packet;
import mumblers.tracers.common.PacketId;
import mumblers.tracers.common.PlayerColour;
import mumblers.tracers.server.receivers.PacketReceiver;
import mumblers.tracers.server.receivers.PlayerConnectReceiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sinius15 on 14-7-2015.
 * The server where games can be played
 */
public class Server implements Runnable{

    private ServerSocket serverSocket;
    private List<PacketReceiver> receivers;
    private List<ClientConnection> clients;
    private List<PlayerColour> avalableColours;
    boolean running = false;

    public Server() throws IOException {
        receivers = new ArrayList<>();
        receivers.add(new PlayerConnectReceiver());
        avalableColours = Arrays.asList(PlayerColour.values());
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(Constants.SERVER_PORT);
        running = true;
    }

    @Override
    public void run() {
        while(running){
            try {
                Socket socket = serverSocket.accept();
                ClientConnection client = new ClientConnection(socket, receivers);
                clients.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
