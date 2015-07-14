package mumblers.tracers.server.receivers;

import mumblers.tracers.common.Packet;
import mumblers.tracers.common.Player;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public abstract class PacketReceiver {

    public abstract int getPacketId();

    public abstract void onPacket(Player serverConnection, Packet command);

}
