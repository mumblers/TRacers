package mumblers.tracers.client;

import mumblers.tracers.common.Player;

/**
 * todo
 *
 * @author davidot
 */
public class PlayerController {

    private static final double ACC_FORWARD = 3.85;
    private static final double ACC_REVERSE = 1.85;
    private static final int TURN_SPEED = 3;

    private final Input input;
    private final Player player;

    public PlayerController(Input input, Player player) {
        this.input = input;
        this.player = player;
    }

    public void tick() {
        /*if(input.left.isPressed()) {
            if(!input.right.isPressed()) {
                carAngle -= TURN_SPEED;
            }
        } else if(input.right.isPressed()){
            carAngle += TURN_SPEED;
        }

        if(carVel < 0) {
            carVel = Math.min(carVel + CAR_ACC -1,0);
        } else if(carVel > 0) {
            carVel = Math.max(carVel - CAR_ACC +1, 0);
        }

        if(input.forward.isPressed()) {
            carVel += CAR_ACC + 1;
            carVel = Math.min(MAX_VEL, carVel);
        }

        if(input.reverse.isPressed()) {
            carVel -= CAR_ACC + 1;
            carVel = Math.max(-MAX_VEL, carVel);
        }
        */

        if(input.left.isPressed() && !input.right.isPressed()) {
            player.turn(-TURN_SPEED);
        } else if(input.right.isPressed() && !input.left.isPressed()) {
            player.turn(TURN_SPEED);
        }

        double acc = 0.0;
        if(input.forward.isPressed() && !input.reverse.isPressed()) {
            acc += ACC_FORWARD;
        }

        if(input.reverse.isPressed() && !input.forward.isPressed()) {
            acc -= ACC_REVERSE;
        }

        player.accelerate(acc);
        player.move();
    }


}
