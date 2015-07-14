package mumblers.tracers.client.graphics;

import mumblers.tracers.client.Client;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.Track;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * todo
 *
 * @author davidot
 */
public class TrackSprite extends Sprite {


    private static final int SCALE = 4;
    public static final int TILE_SIZE = Client.TILE_SIZE;
    private final Track track;
    private final Player player;
    private final PlayerSprite playerSprite;

    public TrackSprite(Track track, Player player) {
        this.track = track;
        this.player = player;
        this.playerSprite = new PlayerSprite(player);
    }

    @Override
    public int getWidth() {
        return SCALE * track.getWidth();
    }

    @Override
    public int getHeight() {
        return SCALE * track.getHeight();
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        int middleX = width/2;
        int middleY = height/2;

        int xLeft = player.getX() - middleX;
        int yUp = player.getY() - middleY;
        int tileX = xLeft / TILE_SIZE;
        int tileY = yUp / TILE_SIZE;
        int tileWidth = width / TILE_SIZE + 1;
        int tileHeight = height / TILE_SIZE + 1;
        int xRender = player.getX() % TILE_SIZE;
        int yRender = player.getY() % TILE_SIZE;
        for(int xx = 0; xx < tileX + tileWidth; xx++) {
            for(int yy = 0; yy < tileY + tileHeight; yy++) {
                int id = track.getTileAt(xx, yy);
                if(id !=0) {
                    if(id == 1) {
                        g.drawImage(getRoad(xx, yy), xRender, yRender, TILE_SIZE, TILE_SIZE, null);
                    }
                }
                xRender += TILE_SIZE;
                yRender += TILE_SIZE;
            }
        }
        playerSprite.render(g,width/2,height/2);
    }


    @Override
    public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale, double yScale,
                              int xOff, int yOff) {
        //don't
        render(g,x,y);
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
