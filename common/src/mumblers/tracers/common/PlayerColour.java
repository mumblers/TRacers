package mumblers.tracers.common;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public enum PlayerColour {

    BLACK("car_black.png"),
    GREEN("car_green.png"),
    YELLOW("car_yellow.png"),
    RED("car_red.png"),
    BLUE("car_blue.png");

    final String resourceLocation;

    PlayerColour(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public String getResource() {
        return resourceLocation;
    }
}
