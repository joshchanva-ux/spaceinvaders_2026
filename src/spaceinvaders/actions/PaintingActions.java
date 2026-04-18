package spaceinvaders.actions;

import spaceinvaders.characters.Bullet;
import spaceinvaders.characters.Invader;
import spaceinvaders.ui.SpaceInvadersUI;

import spaceinvaders.mode.GameMode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PaintingActions handles all rendering of game objects.
 * This is called on the AWT-EventQueue-0 thread during repainting.
 * Reads shared game state safely using synchronized access.
 */
public class PaintingActions {

    public PaintingActions() {

    }

    public void drawShooter(Graphics g, SpaceInvadersUI game) {
        Image shooter_image = game.imageSelection.getShooterImage();
        int shooter_height = game.getShooterHeight();
        int shooter_width = game.getShooterWidth();
        int shooter_X_Coordinate = game.getShooter_X_Coordinate();
        int shooter_Y_Coordinate = game.getHeight() - shooter_height;

        g.drawImage(shooter_image, shooter_X_Coordinate, shooter_Y_Coordinate, shooter_width, shooter_height, game);

    }

    public void drawInvaders(Graphics g, SpaceInvadersUI game, Image invaderImage) {
        // Create a safe copy of the invaders list to avoid concurrent modification issues
        List<Invader> invadersCopy;
        synchronized (game) {
            invadersCopy = new ArrayList<>(game.invaders);
        }
        
        for (Invader invader : invadersCopy) {
            g.drawImage(invaderImage, invader.getX(), invader.getY(), invader.getSize(),
                    invader.getSize(), game);
        }
    }

    public void drawBullets(Graphics g, SpaceInvadersUI game) {
        // Create a safe copy of the bullets list to avoid concurrent modification issues
        List<Bullet> bulletsCopy;
        synchronized (game) {
            bulletsCopy = new ArrayList<>(game.bullets);
        }
        
        for (Bullet bullet : bulletsCopy) {
            game.bulletService.drawBullet(g, bullet, game);
        }
    }

    public void drawModeHUD(Graphics g, SpaceInvadersUI game) {
        GameMode mode = game.modeManager.getCurrentMode();
        int hearts;
        synchronized (game) {
            hearts = mode.getHearts();
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(mode.getModeName(), game.getWidth() - 90, 20);

        if (hearts >= 0) {
            g.setColor(Color.RED);
            for (int i = 0; i < hearts; i++) {
                g.fillOval(10 + i * 25, 10, 20, 20);
            }
        }
    }
}
