package spaceinvaders.service;

import spaceinvaders.exception.GameExceptions;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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

        source = source.trim();

        if (source.startsWith("/")) {
            Image resourceImage = loadResourceImage(source);
            if (resourceImage != null) {
                return resourceImage;
            }
            showError("Invalid resource path", source, null);
        } else {
            Image image = loadFileOrUrlImage(source);
            if (image != null) {
                return image;
            }
        }

        
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
        } catch (MalformedURLException e) {
            GameExceptions.showErrorDialog("Invalid URL: " + source + "\n" + e.getMessage());
        } catch (URISyntaxException e) {
            GameExceptions.showErrorDialog("Malformed URI: " + source + "\n" + e.getMessage());
        } catch (IOException e) {
            GameExceptions.showErrorDialog("Unable to read image from: " + source + "\n" + e.getMessage());
        } catch (IllegalArgumentException e) {
            GameExceptions.showErrorDialog("Invalid image source: " + source + "\n" + e.getMessage());
        }
        return null;
    }

    private static Image loadResourceImage(String resourcePath) {
        try {
            return ImageIO.read(ImageSelection.class.getResource(resourcePath));
        } catch (IOException e) {
            GameExceptions.showErrorDialog("Failed to load resource image: " + resourcePath + "\n" + e.getMessage());
            return null;
        }
    }

    private Image loadDefaultImage(String resourcePath) {
        Image defaultImage = loadResourceImage(resourcePath);
        if (defaultImage == null) {
            showError("Failed to load default image", resourcePath, null);
        }
        return defaultImage;
    }

    private void showError(String message, String source, Exception exception) {
        String details = source != null ? "\nSource: " + source : "";
        String exceptionText = exception != null ? "\n" + exception.getMessage() : "";
        JOptionPane.showMessageDialog(null,
                message + details + exceptionText,
                "Image Load Error", JOptionPane.ERROR_MESSAGE);
    }
}
