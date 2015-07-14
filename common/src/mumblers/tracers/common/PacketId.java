package mumblers.tracers.common;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class PacketId {

    /**
     * ping pong
     */
    public static int PING = 0;

    /**
     * This message is sent by the client to the server to let the server know
     * this client is successfully connected. The data sent by this packet is
     * the name of this player. The server will respond with PLAYER_COLOUR packet
     */
        public static int PLAYER_CONNECT = 1;

    /**
     * Sent by the server to all players to inform that a has disconnected. The
     * data of this packet is the color of the player that is disconnected.
     */
    public static int PLAYER_DISCONNECT = 2;

    /**
     * Sent by the server to 1 player to inform the player what color that player
     * has. This message is only sent to a player when the player color changes or
     * to new players.
     */
    public static int PLAYER_COLOUR = 3;



}
