package mumblers.tracers.client;

import mumblers.tracers.client.graphics.TrackSprite;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.PlayerColour;
import mumblers.tracers.common.Track;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class Client implements DisplayRenderer{

    public static final String TITLE = "TRacers";

    private Player myPlayer = new Player(PlayerColour.BLACK, "Davidot");
    private java.util.List<Player> players = new ArrayList<>();

    private Track track = new Track();
    private PlayerController playerController;

    private TrackSprite trackSprite;

    private Input input;

    public Client() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable e) {
            e.printStackTrace();
        }

        Display display = new Display(TITLE);
        display.setRenderer(this);
        display.setIgnoreRepaint(true);

        input = new Input(display);
        trackSprite = new TrackSprite(track, myPlayer);
        playerController = new PlayerController(input, myPlayer);

        //Hack for a way to display the fps on the frame
        display.start();
    }



    @Override
    public void tick() {
        input.tick();
        playerController.tick();
    }

    @Override
    public void render(BufferStrategy buffer, Dimension size) {

        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //put stuff to render
        g.setColor(new Color(69, 183, 34));
        g.fillRect(0, 0, size.width, size.height);
        trackSprite.render(g,0,0,size.width, size.height);

        g.setColor(Color.WHITE);
        g.drawString("y:" + myPlayer.getY(), 0, 24);
        g.drawString("x:" + myPlayer.getX(), 0, 36);
        g.drawString("v:" + myPlayer.getVelocity(), 0, 48);

        //stop stuff to render
        g.dispose();
    }

    public static void main(String[] args) {
        new Client();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByColor(PlayerColour color){
        for(Player p : getPlayers()){
            if(p.getColor() == color)
                return p;
        }
        return null;
    }

    public Player getMyPlayer(){
        return myPlayer;
    }

}
