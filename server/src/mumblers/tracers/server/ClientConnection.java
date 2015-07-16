package mumblers.tracers.server;

import mumblers.tracers.common.Player;
import mumblers.tracers.common.PlayerColour;
import mumblers.tracers.common.network.Connection;
import mumblers.tracers.common.network.Packet;
import mumblers.tracers.common.network.PacketReceiver;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class ClientConnection extends Connection {

    private Player player;

    private Server server;
    private List<PacketReceiver> receivers;

    public ClientConnection(Server server, Socket clientSocket, List<PacketReceiver> receivers) throws IOException {
        super(clientSocket);
        this.player = new Player(PlayerColour.RED, "unknown");
        this.receivers = receivers;
        this.server = server;
    }

    @Override
    public void handlePacket(Packet packet) {
        for(PacketReceiver receiver : receivers) {
            if(receiver.getPacketId() == packet.getId())
                receiver.onPacket(this, this.player, packet);
        }
    }

    @Override
    public void close() {
        super.close();
        server.getConnections().remove(this);
        server.getAvailableColours().add(this.player.getColor());
    }

    public Server getServer(){
        return server;
    }
}
