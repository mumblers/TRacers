package mumblers.tracers.client;

import mumblers.tracers.client.graphics.PlayerSprite;
import mumblers.tracers.client.graphics.TrackSprite;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Sinius on 16-7-2015.
 */
public class Display extends Canvas implements Runnable {

    public static final int IMG_SIZE = 128;
    public static final int SCALE = 4;
    public static final int TILE_SIZE = IMG_SIZE * SCALE;

    private static final int CAR_ACC = 2;
    private static final int MAX_VEL = 80;

    public static final int SLEEPTIME = 2;

    /**
     * The target amount of ticks per second
     */
    public static final int TARGET_TICKS = 60;

    /**
     * The amount of nanoseconds one tick can take
     */
    public static final double NS_TICKS = 1000000000.0 / TARGET_TICKS;

    private final JFrame frame;
    private int width = 720;
    private int height = 405;

    private boolean isRunning;

    private Thread mainThread;

    private String title;

    private DisplayRenderer renderer;

    public Display(String title) {
        this.title = title;
        this.frame = new JFrame(title);
        setPreferredSize(new Dimension(width, height));

        //Make the frame
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);


        //Make sure the frame is packed
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    frame.pack();
                }
            });
        } catch(InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeFrame(e.getComponent().getSize());
            }
        });


    }

    private void init() {
        PlayerSprite.loadSprites(Client.class);
        TrackSprite.loadSprites(Client.class);
    }

    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        int frames = 0;
        int ticks = 0;
        long lastTimer = System.currentTimeMillis();

        while(isRunning) {
            boolean shouldRender = false;
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / NS_TICKS;
            lastTime = now;
            if(unprocessed > TARGET_TICKS * 5) {
                double ticksLeft = TARGET_TICKS * 5;
                System.out.println("Skipping " + (int) (unprocessed - ticksLeft) +
                        " ticks, is the system overloaded?");
                unprocessed = ticksLeft;
            }
            while(unprocessed >= 1) {
                renderer.tick();
                ticks++;
                unprocessed--;
                shouldRender = true;
            }

            if(shouldRender) {
                frames++;
                render();
            }

            try {
                Thread.sleep(SLEEPTIME);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            if(System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                if(frame != null) {
                    frame.setTitle(title + " | " + frames + " fps | " + ticks + " ticks");
                }
                frames = 0;
                ticks = 0;
            }
        }
    }

    /**
     * Start the game
     */
    public void start() {
        if(isRunning) {
            return;
        }
        isRunning = true;
        System.out.println("Starting main thread");
        mainThread = new Thread(this, "TRacers");
        mainThread.start();
    }

    /**
     * Stops the game
     */
    public void stop() {
        if(!isRunning) {
            return;
        }
        isRunning = false;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    mainThread.join();
                    System.exit(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void render() {
        BufferStrategy buffer = getBufferStrategy();
        if(buffer == null) {
            this.createBufferStrategy(2);
            requestFocus();
            return;
        }
        renderer.render(buffer, new Dimension(width, height));

        buffer.show();
    }

    private void resizeFrame(Dimension size) {
        this.width = size.width;
        this.height = size.height;
        System.out.println("NEW FRAME SIZE::" + size);
    }

    public void setRenderer(DisplayRenderer renderer) {
        this.renderer = renderer;
    }

    public DisplayRenderer getRenderer() {
        return renderer;
    }
}
