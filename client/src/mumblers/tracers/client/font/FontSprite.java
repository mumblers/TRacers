package mumblers.tracers.client.font;
import mumblers.tracers.common.Track;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Sinius on 19-8-2015.
 */
public class FontSprite{

    public static final int IMAGE_WIDTH = 128;
    public static final int IMAGE_HEIGHT = 128;
    public static final int TILE_SIZE = IMAGE_WIDTH;
    private final Track track;

    public FontSprite(Track track) {
        this.track = track;
    }

    public int getWidth() {
        return IMAGE_WIDTH * track.getWidth();
    }

    public int getHeight() {
        return IMAGE_HEIGHT * track.getHeight();
    }

    public void render(Graphics2D g) {
        for(int xx = 0; xx <= track.getWidth(); xx++) {
            for(int yy = 0; yy <= track.getHeight(); yy++) {
                int id = track.getTileAt(xx, yy);
                if(id == 1) {
                    g.drawImage(getRoad(xx, yy), xx*IMAGE_WIDTH, yy*IMAGE_HEIGHT, null);
                }
            }
        }
    }

    public static void loadSprites(Class<?> clazz) {
        for(int i = 0; i < road.length; i++) {
            try {
                road[i] = ImageIO.read(clazz.getResourceAsStream("road/" + i + ".png"));
            } catch(IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "ERROR COULD NOT LOAD ALL TRACKS");
                System.exit(-1);
            }
        }
    }

    private static BufferedImage[] road = new BufferedImage[16];

    private HashMap<Point, Integer> cache = new HashMap<>();

    private BufferedImage getRoad(int x,int y) {
        Point p = new Point(x,y);
        if(cache.containsKey(p)) {
            return road[cache.get(p)];
        }
        int result = getRoad(track.getTileAt(x-1,y) == 1, track.getTileAt(x+1,y) == 1,
                track.getTileAt(x,y-1) == 1, track.getTileAt(x,y+1) == 1);
        cache.put(p, result);
        return road[result];
    }

    private static int getRoad(boolean left, boolean right, boolean up, boolean down) {
        return (left ? 1 : 0) + (right ? 2 : 0) + (up ? 4: 0) + (down ? 8 :0);
    }

}
