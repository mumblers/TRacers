package mumblers.tracers.common;

/**
 * Created by Sinius15 on 14-7-2015.
 * The player
 */
public class Player {

    public static final double MAX_VELOCITY = 60.0;

    public static final double MIN_VEL = 0.9;
    private String name;
    private PlayerColour color = PlayerColour.BLACK;
    private double x = 0;
    private double y = 0;
    private double rotation = 0;
    private double velocity = 0;
    private int cooldown = 0;

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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public double getRotation() {
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
        if(cooldown > 0) {
            cooldown--;
            //we can return since velocity is zero
            return;
        }
        x += velocity * Math.cos(Math.toRadians(rotation));
        y += velocity * Math.sin(Math.toRadians(rotation));
        //show down form resistance with ground
        velocity -= MovementUtil.friction(velocity);
    }


    public void accelerate(double acc) {
        if(cooldown > 0) {
            return;
        }
//        double v = Math.abs(velocity) + 0.00001;
//        double nVelocity = velocity + acc * (1/v);
        double nVelocity = MovementUtil.accelerate(velocity, acc);
        if(nVelocity < 0.0 && velocity > 0.1 || nVelocity > 0.0 && velocity < -0.1) {
            nVelocity = 0.0;
            cooldown = 25;
        }
        velocity = nVelocity;
    }

    public void turn(int speed) {
        rotation += MovementUtil.rotate(speed, velocity);
    }
}
