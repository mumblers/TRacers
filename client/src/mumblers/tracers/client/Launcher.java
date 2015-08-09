package mumblers.tracers.client;

import mumblers.tracers.common.Constants;
import mumblers.tracers.server.Server;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Sinius on 16-7-2015.
 */
public class Launcher {

    private JTextField ipField;
    JFrame frame;

    public Launcher(){
        frame = new JFrame("TRacers | " + Constants.VERSION);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(250, 300));
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Welcome to TRacers"));
        panel.add(Box.createHorizontalStrut(300));
        JButton hostBtn = new JButton("Host and Play");
        ActionListener hostBtnAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Server server = new Server();
                            server.startServer();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Something Went Wrong:" + e1.getMessage());
                            System.exit(0);
                        }
                        new Client("localhost");
                    }
                }).start();
                frame.dispose();
            }
        };
        hostBtn.addActionListener(hostBtnAction);
        panel.add(hostBtn);

        panel.add(Box.createHorizontalStrut(300));

        JButton connectBtn = new JButton("Connect to");
        ActionListener connectToAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                if (ip.isEmpty())
                    return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Client(ipField.getText());
                    }
                }).start();
                frame.dispose();
            }
        };
        connectBtn.addActionListener(connectToAction);
        panel.add(connectBtn);

        ipField = new JTextField(20);
        panel.add(ipField);

        frame.setContentPane(panel);
        frame.revalidate();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Launcher();
            }
        });
    }

}
