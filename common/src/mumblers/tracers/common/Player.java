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
    private double velocity = 0;

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

    //temp
    public void setX(int x) {
        this.x = x;
    }

    //temp
    public void setY(int y) {
        this.y = y;
    }

    public int getRotation() {
        return rotation;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setColour(PlayerColour colour) {
        this.color = colour;
    }

    /**
     * Move based on the current velocity and rotation
     */
    public void move() {

    }


}
