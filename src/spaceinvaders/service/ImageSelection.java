package spaceinvaders.service;

import spaceinvaders.exception.GameExceptions;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

public class ImageSelection {
    private Image shooterImage;
    private Image invaderImage;

    public Image getShooterImage() {
        return shooterImage;
    }

    public Image getInvaderImage() {
        return invaderImage;
    }

    public void setGameImages() {
        shooterImage = loadDefaultImage("/spaceinvaders/resources/Images/ShooterImage.png");
        invaderImage = loadDefaultImage("/spaceinvaders/resources/Images/InvaderImage.png");
    }

    public void setShooterImage(Image shooterImage) {
        this.shooterImage = shooterImage;
    }

    public void setInvaderImage(Image invaderImage) {
        this.invaderImage = invaderImage;
    }

    public Image loadImageFromSource(String source, String defaultResourcePath) {
        if (source == null || source.trim().isEmpty()) {
            return loadDefaultImage(defaultResourcePath);
        }

        Image image = loadFileOrUrlImage(source);
        if (image != null) {
            return image;
        }

        GameExceptions.showErrorDialog("Failed to load image from '" + source + "'. Using default instead.");
        return loadDefaultImage(defaultResourcePath);
    }

    private static Image loadFileOrUrlImage(String source) {
        try {
            File file = new File(source);
            if (file.exists()) {
                return ImageIO.read(file);
            }
            URI uri = new URI(source);
            return ImageIO.read(uri.toURL());
        } catch (URISyntaxException | IOException e) {
            return null;
        }
    }

    private static Image loadDefaultImage(String resourcePath) {
        try {
            return ImageIO.read(ImageSelection.class.getResource(resourcePath));
        } catch (IOException e) {
            GameExceptions.showErrorDialog("Failed to load default image: " + e.getMessage());
            return null;
        }
    }
}
