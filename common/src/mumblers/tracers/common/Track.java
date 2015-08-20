package mumblers.tracers.common;

/**
 * Created by Sinius15 on 14-7-2015.
 * This is where you drive on
 */
public class Track {

    private int[] track = {
            1,1,0,0,0,0,0,0,0,0,0,0,
            1,0,1,1,1,1,1,1,1,1,0,0,
            0,1,1,0,0,1,1,1,0,1,1,0,
            0,1,0,0,0,1,0,1,0,0,1,0,
            0,1,0,0,0,0,0,0,0,0,2,0,
            0,1,0,0,0,0,0,0,0,0,1,0,
            0,1,1,0,0,0,0,0,0,1,1,0,
            0,0,1,1,1,1,1,1,1,1,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0
    };

    private int width = 12;
    private int height = 9;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setTrack(int width, int height, int[] track) {
        this.track = track;
        this.width = width;
        this.height = height;
    }

    //todo error checking
    public int getTileAt(int x,int y) {
        if(x < 0 || y < 0 || x >= width || y >= height) {
            return 0;
        }
        return track[x + y * width];
    }


}
