package mumblers.tracers.common;

/**
 * Created by Sinius15 on 14-7-2015.
 * The player
 */
public class Player {

    private String name;
    private PlayerColour colour;

    public Player(PlayerColour colour, String name){
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColour(PlayerColour colour) {
        this.colour = colour;
    }

    public PlayerColour getColour() {
        return colour;
    }
}
