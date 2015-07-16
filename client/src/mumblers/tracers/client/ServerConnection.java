package mumblers.tracers.client;

import mumblers.tracers.common.Constants;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.PlayerColour;
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

    Client client;

    public ServerConnection(Client client, String ip) throws IOException {
        super(new Socket(ip, Constants.SERVER_PORT));
        this.client = client;
    }

    @Override
    public void handlePacket(Packet packet) {
        switch (packet.getId()){
            case PacketId.PING: send(new Packet(PacketId.PING, "")); break;
            case PacketId.PLAYER_COLOUR: break;
            case PacketId.SERVER_FULL: System.exit(-1); break;
            case PacketId.PLAYER_UPDATE: handlePlayerUpdate(packet); break;
        }
    }

    private void handlePlayerUpdate(Packet packet) {
        String[] dataparts = packet.getData().split(";", 5);
        if(dataparts.length != 5){
            throw new IllegalArgumentException("Could not decode PlayerUpdatePacket. content: " + packet.getData());
        }
        PlayerColour playerColor = PlayerColour.values()[Integer.valueOf(dataparts[0])];
        double x = Double.valueOf(dataparts[1]);
        double y = Double.valueOf(dataparts[2]);
        double r = Double.valueOf(dataparts[3]);
        String name = dataparts[4];

        Player player = client.getPlayerByColor(playerColor);
        if(player == null){
            player = new Player(playerColor, name);
            client.getPlayers().add(player);
        }
        player.setX(x);
        player.setY(y);
        player.setRotation(r);
    }
}
