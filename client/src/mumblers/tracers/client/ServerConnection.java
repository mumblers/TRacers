package mumblers.tracers.client;

import mumblers.tracers.common.Constants;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.network.Packet;
import mumblers.tracers.common.network.Connection;
import mumblers.tracers.common.network.PacketId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class ServerConnection extends Connection {

    public ServerConnection(Player player, String ip) throws IOException {
        super(new Socket(ip, Constants.SERVER_PORT));
    }

    @Override
    public void handlePacket(Packet packet) {
        switch (packet.getId()){
            case PacketId.PING: send(new Packet(PacketId.PING, "")); break;
            case PacketId.PLAYER_COLOUR: break;
            case PacketId.SERVER_FULL: System.exit(-1);
        }
    }
}
