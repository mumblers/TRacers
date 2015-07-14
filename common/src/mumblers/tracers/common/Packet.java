package mumblers.tracers.common;

/**
 * Created by Sinius15 on 14-7-2015.
 * A package ready to be sent over the internet.
 */
public class Packet {

    private int id;

    private String data;

    public Packet(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String encode(){
        return id + ":" + data;
    }

    public static Packet decode(String message){
        String[] splitted = message.split(":", 1);
        if(splitted.length != 2)
            throw new IllegalArgumentException("The message is not valid. Message content: '" + message + "'.");
        int id;
        try{
            id = Integer.valueOf(splitted[0]);
        }catch(Exception e){
            throw new IllegalArgumentException("The message is not valid. Could not convert id to number.");
        }

        return new Packet(id, splitted[1]);
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id=" + id +
                ", data='" + data + '\'' +
                '}';
    }

    public static void main(String[] args){
        Packet original = new Packet(50, "adsf");
        System.out.println(original);

        String encoded = original.encode();
        System.out.println(encoded);

        Packet received = Packet.decode(encoded);
        System.out.println(received);
    }
}
