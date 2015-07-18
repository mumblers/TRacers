package mumblers.tracers.client;

import mumblers.tracers.server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Sinius on 16-7-2015.
 */
public class LauncherGui {

    private JSplitPane splitPane1;
    private JButton hostAndPlayButton;
    private JTextField textField1;
    private JButton connectToServerButton;

    private ActionListener hostAndPlayeButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Server server = null;
            try {
                server = new Server();
                server.startServer();
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Something Went Wrong." + e1.getMessage());
                System.exit(0);
            }
            new Client("localhost");
        }
    };

    public LauncherGui() {
        hostAndPlayButton.addActionListener(hostAndPlayeButtonAction);
    }


    private void createUIComponents() {
        // place custom component creation code here
    }
}
