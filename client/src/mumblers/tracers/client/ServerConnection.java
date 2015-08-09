package mumblers.tracers.client;

import mumblers.tracers.common.Constants;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.PlayerColor;
import mumblers.tracers.common.network.Connection;
import mumblers.tracers.common.network.Packet;
import mumblers.tracers.common.network.PacketId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class ServerConnection extends Connection {

    private String serverIp = "unknown";
    private Client client;

    public ServerConnection(Client client, String ip) throws IOException {
        super(new Socket(ip, Constants.SERVER_PORT));
        this.client = client;
        if(ip.equals("localhost") || ip.equals("127.0.0.1")){
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            serverIp = in.readLine();
        }else{
            serverIp = ip;
        }

    }

    @Override
    public void handlePacket(Packet packet) {
        switch (packet.getId()){
            case PacketId.PING: send(new Packet(PacketId.PING, "")); break;
            case PacketId.PLAYER_COLOUR: handlePlayerColour(packet); break;
            case PacketId.SERVER_FULL: System.exit(-1); break;
            case PacketId.PLAYER_UPDATE: handlePlayerUpdate(packet); break;
        }
    }

    private void handlePlayerColour(Packet packet) {
        PlayerColor playerColor = PlayerColor.values()[Integer.valueOf(packet.getData())];
        System.out.println("Set color to "  + playerColor.name());
        client.getTrackingPlayer().setColour(playerColor);
    }

    private void handlePlayerUpdate(Packet packet) {
        String[] dataParts = packet.getData().split(";", 5);
        if(dataParts.length != 5){
            throw new IllegalArgumentException("Could not decode PlayerUpdatePacket. content: " + packet.getData());
        }
        PlayerColor playerColor = PlayerColor.values()[Integer.valueOf(dataParts[0])];
        double x = Double.valueOf(dataParts[1]);
        double y = Double.valueOf(dataParts[2]);
        double r = Double.valueOf(dataParts[3]);
        String name = dataParts[4];

        Player player = client.getPlayerByColor(playerColor);
        if(player == null){
            player = new Player(playerColor, name);
            client.getPlayers().add(player);
        }
        player.setX(x);
        player.setY(y);
        player.setRotation(r);
    }

    public void sendPlayerUpdate() {
        Player player = client.getTrackingPlayer();
        send(new Packet(PacketId.PLAYER_UPDATE, player.getColor().ordinal() + ";" + player.getX() + ";" + player.getY() + ";" + player.getRotation() + ";" + player.getName()));
    }

    public void sendPlayerConnectMessage() {
        Player player = client.getTrackingPlayer();
        send(new Packet(PacketId.PLAYER_CONNECT, player.getName()));
    }

    public String getServerIp() {
        return serverIp;
    }
}
