package mumblers.tracers.common;

/**
 * todo
 *
 * @author davidot
 */
public class MovementUtil {

    public static double accelerate(double velocity, double acceleration) {
        return velocity + acceleration * (Math.abs(velocity) / (Player.MAX_VELOCITY * 4) + 0.5);
    }

    public static double rotate(double turnSpeed, double velocity) {
        double v = Math.abs(velocity) + 0.00001;
//        double dR = 0.2 * speed * (1 - Math.abs(velocity) / Player.MAX_VELOCITY) + 0.8 * speed;
        double dR = 0.2 * turnSpeed * (1/(v * v)) + 0.8 * turnSpeed;
        if(Math.abs(velocity) < Player.MIN_VEL * 2) {
            dR = 0;
        }
        return dR;
    }

    public static double friction(double velocity) {
//        double v = Math.abs(velocity) + 0.00001;
//        velocity -= velocity * 0.001 * (1/v);
        return velocity * 0.1 * Math.abs(velocity) / (Player.MAX_VELOCITY * 4);
//        if(Math.abs(velocity) < MIN_VEL) {
//            velocity = 0.0;
//        }
    }

}
