package mumblers.tracers.client.graphics;

import mumblers.tracers.client.Client;
import mumblers.tracers.client.Display;
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
    public static final int TILE_SIZE = Display.TILE_SIZE;
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
    public void render(Graphics2D g, int x, int y, int windowWidth, int windowHeight) {
        int windowCenterX = windowWidth / 2;
        int windowCenterY = windowHeight / 2;

        int viewportPixelX = player.getX() - windowCenterX;
        int viewportPixelY = player.getY() - windowCenterY;

        int viewportTileX = viewportPixelX / TILE_SIZE;
        int viewportTileY = viewportPixelY / TILE_SIZE;

        int viewportTileWidth = windowWidth / TILE_SIZE + 1;
        int viewportTileHeight = windowHeight / TILE_SIZE + 1;

        int xRender;
        if(viewportPixelX < 0) {
            xRender = -viewportPixelX % TILE_SIZE;
        } else {
            xRender = -((viewportPixelX + TILE_SIZE) % TILE_SIZE);

        }
        int yBase;

        if(viewportPixelY < 0) {
            yBase = -viewportPixelY % TILE_SIZE;
        } else {
            yBase = -((viewportPixelY + TILE_SIZE) % TILE_SIZE);
        }

        int yRender = yBase;

        for(int xx = viewportTileX; xx <= viewportTileX + viewportTileWidth; xx++) {
            for(int yy = viewportTileY; yy <= viewportTileY + viewportTileHeight; yy++) {
                int id = track.getTileAt(xx, yy);
                if(id !=0) {
                    if(id == 1) {
                        g.drawImage(getRoad(xx, yy), xRender, yRender, TILE_SIZE, TILE_SIZE, null);
                    }
                }
                yRender += TILE_SIZE;
            }
            xRender += TILE_SIZE;
            yRender = yBase;

//            yRender = viewportPixelY < 0 ? viewportPixelY % TILE_SIZE : -((viewportPixelY + TILE_SIZE) % TILE_SIZE);
        }
        playerSprite.render(g,windowWidth/2,windowHeight/2);
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
