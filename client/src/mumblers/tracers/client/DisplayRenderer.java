package mumblers.tracers.client;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Created by Sinius on 16-7-2015.
 */
public interface DisplayRenderer {

    void render(Graphics2D g, Dimension size);

    void tick();
}
