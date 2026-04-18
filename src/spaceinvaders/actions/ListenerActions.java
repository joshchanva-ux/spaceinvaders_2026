package spaceinvaders.actions;

import java.awt.event.*;
import spaceinvaders.ui.SpaceInvadersUI;

/**
 * ListenerActions handles all keyboard input events.
 * These events are processed on the AWT-EventQueue-0 thread.
 * 
 * Position calculations, spawning, and collision detection
 * are now handled by GameCalculator on a separate thread.
 */
public class ListenerActions {

    public void keyPressed(KeyEvent e, SpaceInvadersUI game) {
        int key = e.getKeyCode();
        synchronized (game) {
            if (key == KeyEvent.VK_LEFT) {
                game.moveLeft = true;
            }
            if (key == KeyEvent.VK_RIGHT) {
                game.moveRight = true;
            }
            if (key == KeyEvent.VK_SPACE) {
                game.spacePressed = true;
            }
        }
    }

    public void keyReleased(KeyEvent e, SpaceInvadersUI game) {
        int key = e.getKeyCode();
        synchronized (game) {
            if (key == KeyEvent.VK_LEFT) {
                game.moveLeft = false;
            }
            if (key == KeyEvent.VK_RIGHT) {
                game.moveRight = false;
            }
            if (key == KeyEvent.VK_SPACE) {
                game.spacePressed = false;
            }
        }
    }
}
