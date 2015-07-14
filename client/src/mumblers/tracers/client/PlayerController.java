package mumblers.tracers.client;

import mumblers.tracers.common.Player;

/**
 * todo
 *
 * @author davidot
 */
public class PlayerController {

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

        carX += carVel * Math.cos(Math.toRadians(carAngle)) ;
        carY += carVel * Math.sin(Math.toRadians(carAngle)) ;

        if(carX < -120 || carY < -120 || carX > width + 120 || carY > height + 120) {
            carX = 0;
            carY = 0;
//            carVel = 0;
        }*/
    }


}
