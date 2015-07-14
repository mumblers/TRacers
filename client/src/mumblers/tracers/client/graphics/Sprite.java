package mumblers.tracers.client.graphics;

import java.awt.Graphics2D;

/**
 * todo
 *
 * @author davidot
 */
public abstract class Sprite {

    /**
     * A sprite which does not render anything <p> Useful when you need to return a sprite but don't
     * need anything rendered </p>
     */
    public static Sprite EMPTY_SPRITE = new EmptySprite();

    public abstract int getWidth();

    public abstract int getHeight();

    /**
     * Render this sprite at a certain location
     *
     * @param g the graphics to use
     * @param x the x coordinate to draw on
     * @param y the y coordinate to draw on
     */
    public void render(Graphics2D g, int x, int y) {
        this.render(g, x, y, getWidth(), getHeight());
    }

    /**
     * Render this sprite in a rectangle
     *
     * @param g      the graphics to use
     * @param x      the x coordinate to draw on
     * @param y      the y coordinate to draw on
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public abstract void render(Graphics2D g, int x, int y, int width, int height);


    /**
     * Render this sprite at a certain location with a certain scale
     *
     * @param g     the graphics to use
     * @param x     the x coordinate to draw on
     * @param y     the y coordinate to draw on
     * @param scale the scale at which to draw this sprite
     */
    public void renderScale(Graphics2D g, int x, int y, int scale) {
        this.render(g, x, y, scale * getWidth(), scale * getHeight());
    }

    /**
     * Renders a sprite rotated at a certain location <p> It will render the sprite at the position
     * it would normally draw the sprite (x,y right top) just rotated </p>
     *
     * @param g     the graphics to draw with
     * @param x     the x location to draw at
     * @param y     the y location to draw at
     * @param angle the angle to draw the sprite at
     */
    public void renderRotated(Graphics2D g, int x, int y, int angle) {
        renderRotated(g, x, y, angle, 1.0, 1.0);
    }

    /**
     * Renders a sprite rotated at a certain location with a certain scale
     *
     * @param g      the graphics to draw with
     * @param x      the x location to draw at
     * @param y      the y location to draw at
     * @param angle  the angle to draw the sprite at
     * @param xScale the scale in the x (width) direction
     * @param yScale the scale in the y (height) direction
     * @see #renderRotated(Graphics2D, int, int, int)
     */
    public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale, double yScale) {
        renderRotated(g, x, y, angle, xScale, yScale, getWidth() / 2, getHeight() / 2);
    }

    public void renderRotated(Graphics2D g, int x, int y, int angle, int xOff, int yOff) {
        renderRotated(g, x, y, angle, 1.0, 1.0, xOff, yOff);
    }


    public abstract void renderRotated(Graphics2D g, int x, int y, int angle, double xScale,
                                       double yScale,
                                       int xOff, int yOff);



    private static class EmptySprite extends Sprite {

        private EmptySprite() {
        }

        @Override
        public void renderRotated(Graphics2D g, int x, int y, int angle, double xScale,
                                  double yScale, int xOff, int yOff) {
            //don't
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
            //don't
        }
    }

}
