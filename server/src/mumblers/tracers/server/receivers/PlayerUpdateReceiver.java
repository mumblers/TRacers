package mumblers.tracers.server.receivers;

import mumblers.tracers.common.Player;
import mumblers.tracers.common.network.Connection;
import mumblers.tracers.common.network.Packet;
import mumblers.tracers.common.network.PacketId;
import mumblers.tracers.common.network.PacketReceiver;
import mumblers.tracers.server.ClientConnection;

/**
 * Created by Sinius on 16-7-2015.
 */
public class PlayerUpdateReceiver extends PacketReceiver{
    @Override
    public int getPacketId() {
        return PacketId.PLAYER_UPDATE;
    }

    @Override
    public void onPacket(Connection connection, Player player, Packet packet) {
        //todo: check for valid movement

        ClientConnection clientConnection = (ClientConnection) connection;

        for(ClientConnection c : clientConnection.getServer().getConnections()){
            if(c.equals(clientConnection))
                continue;
            c.send(packet);
        }

    }
}
