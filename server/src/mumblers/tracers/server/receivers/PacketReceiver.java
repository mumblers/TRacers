package mumblers.tracers.server.receivers;

import mumblers.tracers.common.Packet;
import mumblers.tracers.common.Player;
import mumblers.tracers.server.ClientConnection;
import mumblers.tracers.server.Server;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public abstract class PacketReceiver {

    public abstract int getPacketId();

    public abstract void onPacket(Server server, ClientConnection clientConnection, Player player, Packet packet);

}
