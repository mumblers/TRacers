package mumblers.tracers.server;

import mumblers.tracers.common.Constants;
import mumblers.tracers.common.PlayerColor;
import mumblers.tracers.common.network.PacketReceiver;
import mumblers.tracers.server.receivers.PlayerConnectReceiver;
import mumblers.tracers.server.receivers.PlayerUpdateReceiver;

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
    private List<PlayerColor> availableColours;
    boolean running = false;

    public Server() throws IOException {
        receivers = new ArrayList<>();
        receivers.add(new PlayerConnectReceiver());
        receivers.add(new PlayerUpdateReceiver());
        availableColours = new ArrayList<>(Arrays.asList(PlayerColor.values()));
        clients = new ArrayList<>();
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(Constants.SERVER_PORT);
        running = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(running){
            try {
                Socket socket = serverSocket.accept();
                if(socket == null)
                    continue;
                ClientConnection client = new ClientConnection(this, socket, receivers);
                clients.add(client);
                System.out.println("New client connected...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ClientConnection> getConnections(){
        return clients;
    }

    public List<PlayerColor> getAvailableColours() {
        return availableColours;
    }

    public static void main(String[] args){
        Server s = null;
        try {
            s = new Server();
            s.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
