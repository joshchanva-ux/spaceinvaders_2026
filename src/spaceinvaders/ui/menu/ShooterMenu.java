package spaceinvaders.ui.menu;

import spaceinvaders.ui.SpaceInvadersUI;

import javax.swing.*;
import java.awt.*;

public class ShooterMenu {

    private final SpaceInvadersUI gameUI;

    public ShooterMenu(SpaceInvadersUI gameUI) {
        this.gameUI = gameUI;
    }

    public JMenu getMenu() {
        JMenu shooterMenu = new JMenu("Shooter Image");
        shooterMenu.add(createMenuItem("Biden Blast", e -> setShooterImage("/spaceinvaders/resources/Images/BidenBlast.png")));
        shooterMenu.add(createMenuItem("Sniper", e -> setShooterImage("/spaceinvaders/resources/Images/Sniper.png")));
        shooterMenu.add(createMenuItem("AVGN", e -> setShooterImage("/spaceinvaders/resources/Images/AVGN.png")));
        shooterMenu.addSeparator();
        shooterMenu.add(createMenuItem("Custom Shooter Image...", e -> promptForCustomImage()));
        return shooterMenu;
    }

    private JMenuItem createMenuItem(String title, java.awt.event.ActionListener action) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(action);
        return item;
    }

    private void setShooterImage(String source) {
        Image image = gameUI.imageSelection.loadImageFromSource(source, "/spaceinvaders/resources/Images/ShooterImage.png");
        if (image != null) {
            gameUI.setShooterImage(image);
            gameUI.repaint();
        }
    }

    private void promptForCustomImage() {
        String source = JOptionPane.showInputDialog(gameUI,
                "Enter a file path or URL for the shooter image:",
                "Custom Shooter Image", JOptionPane.PLAIN_MESSAGE);
        if (source == null || source.trim().isEmpty()) {
            return;
        }
        source = source.trim().replace('\\', '/');
        setShooterImage(source);
    }
}
