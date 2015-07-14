package mumblers.tracers.server;

import mumblers.tracers.common.Packet;
import mumblers.tracers.common.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class ServerConnection implements Runnable {

    private Player player;

    private PrintWriter output;
    private BufferedReader reader;
    private Socket socket;
    private List<ClientMessageListener> listeners;
    private Thread thread;

    public ServerConnection(Socket clientSocket) throws IOException {
            this.socket = clientSocket;
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            listeners = Collections.synchronizedList(new ArrayList<ClientMessageListener>(1));

    }


    public void send(Packet message) {
        output.println(message.encode());
    }

    public void addMessageListener(ClientMessageListener listener) {
        listeners.add(listener);
        //only start listening after the first listener has been added
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void removeMessageListener(ClientMessageListener listener) {
        listeners.remove(listener);
    }


    public void stopListening() {
        listen.set(false);
        send(new ConnectionClosedMessage());
    }

    public void stop() {
        listen.set(false);
    }

    @Override
    public void run() {
        try {
            String command;
            while(listen.get() && (command = reader.readLine()) != null) {

                if(command.equals("kill")) {
                    System.out.println("Stopping listening on client:" + address.toString());
                    break;
                }

                for(ClientMessageListener listener: listeners) {
                    listener.onMessage(this,command);
                }

            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
            reader.close();
            if(output != null)
                output.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
