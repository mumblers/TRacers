package mumblers.tracers.client.graphics;

import mumblers.tracers.client.Client;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.PlayerColor;

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

    private static HashMap<PlayerColor, BufferedImage> cars;

    private final Player player;
    private PlayerColor color;
    private BufferedImage img;

    public PlayerSprite(Player player) {
        this.player = player;
        getImage();
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        if(color!= player.getColor()) {
            getImage();
        }
        renderRotated(g,x,y, (int) player.getRotation());
    }

    @Override
    public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale, double yScale,
                              int xOff, int yOff) {
        AffineTransform trans = new AffineTransform();
        trans.setToTranslation(x, y);
//        trans.setToScale(xScale, yScale);
        trans.rotate(Math.toRadians(angle), xOff, yOff);
        g.drawImage(img, trans, null);
    }

    private void getImage() {
        img = getImage(player);
        color = player.getColor();
    }

    private static BufferedImage getImage(Player player) {
        if(cars == null) {
            loadSprites(Client.class);
        }
        return cars.get(player.getColor());
    }

    public static void loadSprites(Class<?> clazz) {
        boolean error = false;
        cars = new HashMap<>(PlayerColor.values().length);
        for(PlayerColor color: PlayerColor.values()) {
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
