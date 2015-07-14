package mumblers.tracers.common.network;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class PacketId {

    /**
     * ping pong
     */
    public static final int PING = 0;

    /**
     * This message is sent by the client to the server to let the server know
     * this client is successfully connected. The data sent by this packet is
     * the name of this player. The server will respond with PLAYER_COLOUR packet.
     *
     * The Server sends this message to all players except the new one
     * when a new player connects. The data of the message contains something like
     * '4;sinius'. So a player named sinius has connected with color 4.
     */
    public static final int PLAYER_CONNECT = 1;

    /**
     * Sent by the server to all players to inform that a has disconnected. The
     * data of this packet is the color of the player that is disconnected.
     */
    public static final int PLAYER_DISCONNECT = 2;

    /**
     * Sent by the server to 1 player to inform the player what color that player
     * has. This message is only sent to a player when the player color changes or
     * to new players.
     */
    public static final int PLAYER_COLOUR = 3;

    /**
     * This message is sent by the server to a new client when the server is full.
     * After this message is sent the server will disconnect the connection to
     * this client.
     */
    public static final int SERVER_FULL = 4;

    /**
     * Sends the new location of the player.
     */
    public static final int PLAYER_UPDATE = 5;



}
