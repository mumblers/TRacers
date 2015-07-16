package mumblers.tracers.client;

import java.awt.Dimension;
import java.awt.image.BufferStrategy;

/**
 * Created by Sinius on 16-7-2015.
 */
public interface DisplayRenderer {

    public void render(BufferStrategy buffer, Dimension size);

    public void tick();
}
