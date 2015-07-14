package mumblers.tracers.server.receivers;

import mumblers.tracers.common.Packet;
import mumblers.tracers.common.PacketId;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.PlayerColour;
import mumblers.tracers.server.ClientConnection;
import mumblers.tracers.server.Server;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class PlayerConnectReceiver extends PacketReceiver{

    @Override
    public int getPacketId() {
        return PacketId.PLAYER_CONNECT;
    }

    @Override
    public void onPacket(Server server, ClientConnection clientConnection, Player player, Packet packet) {
        player.setName(packet.getData());
        if(server.getAvalableColours().size() == 0) {
            clientConnection.send(new Packet(PacketId.SERVER_FULL, ""));
            clientConnection.close();
        }

        PlayerColour newColor = server.getAvalableColours().get(0);
        clientConnection.send(new Packet(PacketId.PLAYER_COLOUR, PlayerColour.BLACK.ordinal() + ""));
    }

}
