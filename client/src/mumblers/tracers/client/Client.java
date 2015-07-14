package mumblers.tracers.client;

import mumblers.tracers.client.graphics.PlayerSprite;
import mumblers.tracers.client.graphics.TrackSprite;
import mumblers.tracers.common.Player;
import mumblers.tracers.common.Track;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinius15 on 14-7-2015.
 */
public class Client extends Canvas implements Runnable{


    public static final int IMG_SIZE = 128;
    public static final int SCALE = 4;
    public static final int TILE_SIZE = IMG_SIZE * SCALE;

    private static final int CAR_ACC = 2;
    private static final int MAX_VEL = 80;
    private static int TURN_SPEED = 2;
    private static final String TITLE = "TRacers";
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

    private Player myPlayer = new Player("HOI");
    private Track track = new Track();


    private int carAngle = 0;
    private int carX;
    private int carY;
    private Input input;
    private int carVel = 0;
    private TrackSprite trackSprite;

    private List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public Client(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(width, height));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeFrame(e.getComponent().getSize());
            }
        });
        input = new Input(this);
        carX = width/2;
        carY = height/2;
        trackSprite = new TrackSprite(track, myPlayer);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable e) {
            e.printStackTrace();
        }

        final JFrame frame = new JFrame(Client.TITLE);

        Client game = new Client(frame);
        game.setIgnoreRepaint(true);

        //Make the frame
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
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
        //Hack for a way to display the fps on the frame
        game.start();
    }

    private void resizeFrame(Dimension size) {
        this.width = size.width;
        this.height = size.height;
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
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
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
                tick();
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
                    frame.setTitle(TITLE + " | " + frames + " fps | " + ticks + " ticks");
                }
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void init() {
        PlayerSprite.loadSprites(Client.class);
        TrackSprite.loadSprites(Client.class);
    }

    public void tick() {
        /*if(input.left.isPressed()) {
            if(!input.right.isPressed()) {
                carAngle -= TURN_SPEED;
            }
        } else if(input.right.isPressed()){
            carAngle += TURN_SPEED;
        }

        if(carVel < 0) {
            carVel = Math.min(carVel + CAR_ACC -1,0);
        } else if(carVel > 0) {
            carVel = Math.max(carVel - CAR_ACC +1, 0);
        }

        if(input.forward.isPressed()) {
            carVel += CAR_ACC + 1;
            carVel = Math.min(MAX_VEL, carVel);
        }

        if(input.reverse.isPressed()) {
            carVel -= CAR_ACC + 1;
            carVel = Math.max(-MAX_VEL, carVel);
        }

        carX += carVel * Math.cos(Math.toRadians(carAngle)) ;
        carY += carVel * Math.sin(Math.toRadians(carAngle)) ;

        if(carX < -120 || carY < -120 || carX > width + 120 || carY > height + 120) {
            carX = 0;
            carY = 0;
//            carVel = 0;
        }*/

        myPlayer.add(2);

        input.tick();
    }

    public void render() {
        BufferStrategy buffer = getBufferStrategy();
        if(buffer == null) {
            this.createBufferStrategy(2);
            requestFocus();
            return;
        }
        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        //put stuff to render
        g.setColor(new Color(69, 183, 34));
        g.fillRect(0, 0, width, height);
        trackSprite.render(g,0,0,width,height);

        g.setColor(Color.WHITE);
        g.drawString("y:" + myPlayer.getX(), 0, 24);
        g.drawString("x:" + myPlayer.getY(), 0, 48);


        //stop stuff to render
        g.dispose();
        buffer.show();
    }

}
