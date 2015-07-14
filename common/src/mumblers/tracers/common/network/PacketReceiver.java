package mumblers.tracers.common.network;

import mumblers.tracers.common.Player;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public abstract class PacketReceiver {

    public abstract int getPacketId();

    public abstract void onPacket(Connection connection, Player player, Packet packet);

}
