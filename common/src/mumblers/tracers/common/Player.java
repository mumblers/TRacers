package mumblers.tracers.common;

/**
 * Created by Sinius15 on 14-7-2015.
 * The player
 */
public class Player {

    private String name;
    private PlayerColour color = PlayerColour.BLACK;
    private int x = 0;
    private int y = 0;
    private int rotation = 0;

    public Player(PlayerColour colour, String name){
        this.name = name;
        this.color = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerColour getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void add(int a) {
        x+=a;
        y+=a;
    }

    public int getRotation() {
        return rotation;
    }

    public void setColour(PlayerColour colour) {
        this.color = colour;
    }
    
}
