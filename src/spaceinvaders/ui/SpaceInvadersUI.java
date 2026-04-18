package spaceinvaders.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import spaceinvaders.actions.ListenerActions;
import spaceinvaders.actions.PaintingActions;
import spaceinvaders.characters.Bullet;
import spaceinvaders.characters.Invader;
import spaceinvaders.service.BulletService;
import spaceinvaders.service.GameCalculator;
import spaceinvaders.service.ImageSelection;
import spaceinvaders.service.ModeManager;
import spaceinvaders.service.MusicService;
import spaceinvaders.ui.menu.AppMenuBar;

public class SpaceInvadersUI extends JPanel implements KeyListener {

    private final Timer repaintTimer;
    public ArrayList<Invader> invaders;
    public ArrayList<Bullet> bullets;
    public Random random;
    public boolean moveLeft, moveRight;
    public boolean spacePressed;
    private final ListenerActions listenerActions;
    public final ImageSelection imageSelection;
    public final BulletService bulletService;
    private final PaintingActions paintingActions;
    private int shooter_width = 50;
    private int shooter_height = 60;
    private int shooter_X_Coordinate = 200;
    private GameCalculator gameCalculator;
    private final AppMenuBar appMenuBar;
    public ModeManager modeManager;
    public static int breakpointcounter = 0;

    // Constructor
    public SpaceInvadersUI() {

        // Start the game calculator thread for position/collision updates
        gameCalculator = new GameCalculator(this);
        gameCalculator.start();
        // Timer is used only for repainting (UI thread)
        repaintTimer = new Timer(20, e -> repaint()); // 20ms delay for smoother animations
        invaders = new ArrayList<>(); // Need to describe what ArrayList<> is.
        bullets = new ArrayList<>();
        random = new Random();
        gameCalculator = new GameCalculator(this);
        gameCalculator.start();
        moveLeft = false;
        moveRight = false;
        spacePressed = false;
        listenerActions = new ListenerActions();
        imageSelection = new ImageSelection();
        bulletService = new BulletService();
        paintingActions = new PaintingActions();
        modeManager = new ModeManager(this);
        appMenuBar = new AppMenuBar(this);
        // For debugging

        // Set images
        imageSelection.setGameImages();

        setFocusable(true);
        addKeyListener(this);

        // Start the game calculator thread for position/collision updates
        gameCalculator = new GameCalculator(this);
        gameCalculator.start();

        // Start background music alongside the UI
        MusicService.startDefaultMusic();
        

        // Start the mode manager thread
        modeManager.start();

        // Start the repaint timer (UI thread)
        repaintTimer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        listenerActions.keyPressed(e, this);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        listenerActions.keyReleased(e, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used. Not providing an implementation Violates Interface Segregation
        // Principle
        // Could be used for character keys.
    }

    @Override
    // Let's move these methods into a separate PaintUI class
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        // Draw shooter (rectangle)
        paintingActions.drawShooter(g, this);

        // Draw falling invaders (as images)
        paintingActions.drawInvaders(g, this, imageSelection.getInvaderImage());

        // Draw bullets (bullets)
        paintingActions.drawBullets(g, this);

        // Draw mode HUD (hearts and mode label)
        paintingActions.drawModeHUD(g, this);
    }

    public JMenuBar getAppMenuBar() {
        return appMenuBar.getMenuBar();
    }

    public int getShooterWidth() {
        return (shooter_width);
    }

    public int getShooterHeight() {
        return (shooter_height);
    }

    public int getShooter_X_Coordinate() {
        return (shooter_X_Coordinate);
    }

    public void setShooter_X_Coordinate(int shooter_X) {
        shooter_X_Coordinate = shooter_X;
    }

    /**
     * Gracefully shuts down all background threads and stops the repaint timer.
     * Call this before closing the application.
     */
    public void triggerGameOver() {
        gameCalculator.stopThread();
        repaintTimer.stop();
        if (modeManager != null) {
            modeManager.stopThread();
        }
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Game Over! You ran out of hearts.", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });
    }

    public void shutdown() {
        if (gameCalculator != null && gameCalculator.isAlive()) {
            gameCalculator.stopThread();
        }
        if (repaintTimer != null) {
            repaintTimer.stop();
        }
        if (modeManager != null) {
            modeManager.stopThread();
        }
    }

}
