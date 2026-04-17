package spaceinvaders.service;

import spaceinvaders.characters.Bullet;
import spaceinvaders.exception.GameExceptions;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Controls bullet visual type and rendering strategy (shape vs image).
 */
public class BulletService {

    private static final int IMAGE_BULLET_WIDTH = 16;
    private static final int IMAGE_BULLET_HEIGHT = 16;
    private static final int LAZER_WIDTH = 4;
    private static final int LAZER_HEIGHT = 14;
    private static final int LAZER_X_OFFSET = 2;

    private Image activeBulletImage;
    private String activeType = "Lazer";

    public BulletService() {
        setLazerType();
    }

    public void setLazerType() {
        activeType = "Lazer";
        activeBulletImage = null;
    }

    public void setTntType() {
        setImageType("TNT", "/spaceinvaders/resources/Images/bullets/tnt.png");
    }

    public void setNukeType() {
        setImageType("Nuke", "/spaceinvaders/resources/Images/bullets/nuke.png");
    }

    public boolean setCustomTypeFromPathOrUrl(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        String trimmedInput = input.trim();
        try {
            Image image;
            if (trimmedInput.startsWith("http://") || trimmedInput.startsWith("https://")) {
                image = ImageIO.read(new URI(trimmedInput).toURL());
            } else {
                File imageFile = new File(trimmedInput);
                if (!imageFile.isAbsolute()) {
                    imageFile = new File(System.getProperty("user.dir"), trimmedInput);
                }
                image = ImageIO.read(imageFile);
            }

            if (image == null) {
                GameExceptions.showErrorDialog("Custom bullet image could not be loaded.");
                return false;
            }

            activeType = "Custom";
            activeBulletImage = image;
            return true;
        } catch (URISyntaxException | IOException e) {
            GameExceptions.showErrorDialog("Failed to load custom bullet image: " + e.getMessage());
            return false;
        }
    }

    public void promptAndSetCustomType() {
        String input = JOptionPane.showInputDialog(
                null,
                "Enter bullet image URL or repo file path:",
                "Custom Bullet Image",
                JOptionPane.PLAIN_MESSAGE
        );
        setCustomTypeFromPathOrUrl(input);
    }

    public String getActiveType() {
        return activeType;
    }

    public void drawBullet(Graphics g, Bullet bullet, Component observer) {
        if (activeBulletImage != null) {
            g.drawImage(
                    activeBulletImage,
                    bullet.getX() - (IMAGE_BULLET_WIDTH / 2),
                    bullet.getY() - IMAGE_BULLET_HEIGHT,
                    IMAGE_BULLET_WIDTH,
                    IMAGE_BULLET_HEIGHT,
                    observer
            );
            return;
        }

        // Lazer default is a red rectangle.
        g.setColor(Color.RED);
        g.fillRect(
                bullet.getX() - LAZER_X_OFFSET,
                bullet.getY() - LAZER_HEIGHT,
                LAZER_WIDTH,
                LAZER_HEIGHT
        );
    }

    private void setImageType(String bulletType, String resourcePath) {
        try {
            Image image = ImageIO.read(BulletService.class.getResource(resourcePath));
            if (image == null) {
                GameExceptions.showErrorDialog("Failed to load " + bulletType + " bullet image.");
                return;
            }
            activeType = bulletType;
            activeBulletImage = image;
        } catch (IOException e) {
            GameExceptions.showErrorDialog("Failed to load " + bulletType + " bullet image: " + e.getMessage());
        }
    }
}
