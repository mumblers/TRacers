package mumblers.tracers.server.receivers;

import mumblers.tracers.common.*;
import mumblers.tracers.common.network.Connection;
import mumblers.tracers.common.network.Packet;
import mumblers.tracers.common.network.PacketId;
import mumblers.tracers.common.network.PacketReceiver;
import mumblers.tracers.server.ClientConnection;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class PlayerConnectReceiver extends PacketReceiver {

    @Override
    public int getPacketId() {
        return PacketId.PLAYER_CONNECT;
    }

    @Override
    public void onPacket(Connection connection, Player player, Packet packet) {
        ClientConnection clientConnection = (ClientConnection) connection;
        player.setName(packet.getData());

        if(clientConnection.getServer().getAvailableColours().size() == 0) {
            clientConnection.send(new Packet(PacketId.SERVER_FULL, ""));
            clientConnection.close();
        }

        PlayerColour newColour = clientConnection.getServer().getAvailableColours().get(0);
        player.setColour(newColour);
        clientConnection.send(new Packet(PacketId.PLAYER_COLOUR, PlayerColour.BLACK.ordinal() + ""));

        for(ClientConnection c : clientConnection.getServer().getConnections()){
            if(c.equals(clientConnection))
                continue;
            c.send(new Packet(PacketId.PLAYER_CONNECT, newColour.ordinal()+";"+player.getName()));
        }
    }

}
