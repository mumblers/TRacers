package mumblers.tracers.client;

import mumblers.tracers.common.Player;
import mumblers.tracers.common.PlayerColour;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class ClientPlayer extends Player {

    private ServerConnection socket;

    public ClientPlayer(PlayerColour colour, String name, Client client) throws IOException {
        super(colour, name);

        String ip = JOptionPane.showInputDialog("Server IP?");
        if(ip == null)
            return;
        this.socket = new ServerConnection(client, ip);
    }



}
