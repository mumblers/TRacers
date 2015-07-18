package mumblers.tracers.client;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sinius on 16-7-2015.
 */
public class Launcher {

    private static ActionListener hostBtnAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame("TRacers Launcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(250, 300));
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Welcome to TRacers"));
        panel.add(Box.createHorizontalStrut(300));
        JButton hostBtn = new JButton("Host and Play");
        hostBtn.addActionListener(hostBtnAction);
        panel.add(hostBtn);

        panel.add(Box.createHorizontalStrut(300));

        JButton connectBtn = new JButton("Connect to");
        connectBtn.addActionListener(hostBtnAction);
        panel.add(connectBtn);
//
        new JTextField(20);
//        panel.add(ipField);

        frame.setContentPane(panel);



//
//        String ip = JOptionPane.showInputDialog("IP", "localhost");
//        if(ip == null) {
//            System.exit(0);
//        }

    }

}
