package mumblers.tracers.client;

import mumblers.tracers.common.Player;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class ClientPlayer extends Player {

    private ServerConnection socket;

    public ClientPlayer(String name) throws IOException {
        super(name);

        String ip = JOptionPane.showInputDialog("Server IP?");
        if(ip == null)
            return;
        this.socket = new ServerConnection(this, ip);
    }



}
