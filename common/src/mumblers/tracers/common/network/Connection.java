package mumblers.tracers.common.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public abstract class Connection implements Runnable{

    protected Socket socket;

    protected PrintWriter output;
    protected BufferedReader reader;

    public Connection(Socket s) throws IOException {
        this.socket = s;
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            String command;
            while((command = reader.readLine()) != null) {
                try{
                    Packet packet = Packet.decode(command);

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        } catch(IOException e) {
            //do not do anything because the connection is being closed.
        }
        this.close();
    }

    public void close() {
        if(socket.isClosed())
            return;
        try {
            socket.close();
            reader.close();
            if(output != null)
                output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //the receiving thread should not be closed because it will close automaticly
    }

    public void send(Packet message) {
        output.println(message.encode());
    }

    public abstract void handlePacket(Packet packet);

}
