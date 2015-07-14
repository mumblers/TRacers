package mumblers.tracers.server;

import mumblers.tracers.common.Packet;
import mumblers.tracers.common.Player;
import mumblers.tracers.server.receivers.PacketReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class ClientConnection implements Runnable {

    private Player player;

    private Server server;
    private PrintWriter output;
    private BufferedReader reader;
    private Socket socket;
    private List<PacketReceiver> receivers;
    private Thread thread;

    public ClientConnection(Server server, Socket clientSocket, List<PacketReceiver> receivers) throws IOException {
        this.socket = clientSocket;
        this.receivers = receivers;
        this.server = server;
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.thread = new Thread(this);
        this.thread.start();
    }


    public void send(Packet message) {
        output.println(message.encode());
    }

    @Override
    public void run() {
        try {
            String command;
            while((command = reader.readLine()) != null) {
                try{
                    Packet packet = Packet.decode(command);
                    for(PacketReceiver receiver : receivers) {
                        if(receiver.getPacketId() == packet.getId())
                            receiver.onPacket(this.server, this, this.player, packet);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        } catch(IOException e) {
            //do not do anything because the connection is being closed.
        }
        this.close();
    }

    public void close() {
        if(socket.isClosed())
            return;
        try {
            socket.close();
            reader.close();
            if(output != null)
                output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //the receiving thread should not be closed because it will close automaticly
    }
}
