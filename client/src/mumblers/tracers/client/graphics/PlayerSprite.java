package mumblers.tracers.client.graphics;

import mumblers.tracers.client.Client;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.PlayerColour;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * todo
 *
 * @author davidot
 */
public class PlayerSprite extends Sprite {

    private static HashMap<PlayerColour, BufferedImage> cars;

    private final Player player;
    private final BufferedImage img;

    public PlayerSprite(Player player) {
        this.player = player;
        img = getImage(player);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        renderRotated(g,x,y,player.getRotation());
    }

    @Override
    public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale, double yScale,
                              int xOff, int yOff) {
        AffineTransform trans = new AffineTransform();
        trans.setToTranslation(x, y);
        trans.setToScale(xScale, yScale);
        trans.rotate(Math.toRadians(angle), xOff, yOff);
        g.drawImage(img, trans, null);
    }

    private static BufferedImage getImage(Player player) {
        if(cars == null) {
            loadSprites(Client.class);
        }
        return cars.get(player.getColor());
    }

    public static void loadSprites(Class<?> clazz) {
        boolean error = false;
        cars = new HashMap<>(PlayerColour.values().length);
        for(PlayerColour color: PlayerColour.values()) {
            try {
                cars.put(color, ImageIO.read(clazz.getResourceAsStream(color.getResource())));
            } catch(IOException e) {
                e.printStackTrace();
                error = true;
            }
        }

        if(error) {
            JOptionPane.showMessageDialog(null, "COULD NOT LOAD ALL CARS ERROR");
            System.exit(-1);
        }
    }
}
