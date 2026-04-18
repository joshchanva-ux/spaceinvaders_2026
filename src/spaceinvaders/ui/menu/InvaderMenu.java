package spaceinvaders.ui.menu;

import spaceinvaders.ui.SpaceInvadersUI;

import javax.swing.*;
import java.awt.*;

public class InvaderMenu {

    private final SpaceInvadersUI gameUI;

    public InvaderMenu(SpaceInvadersUI gameUI) {
        this.gameUI = gameUI;
    }

    public JMenu getMenu() {
        JMenu invaderMenu = new JMenu("Invader Image");
        invaderMenu.add(createMenuItem("True", e -> setInvaderImage("/spaceinvaders/resources/Images/AdachiTrue.png")));
        invaderMenu.add(createMenuItem("Target", e -> setInvaderImage("/spaceinvaders/resources/Images/target.png")));
        invaderMenu.add(createMenuItem("Gato", e -> setInvaderImage("/spaceinvaders/resources/Images/Gato.png")));
        invaderMenu.addSeparator();
        invaderMenu.add(createMenuItem("Custom Invader Image...", e -> promptForCustomImage()));
        return invaderMenu;
    }

    private JMenuItem createMenuItem(String title, java.awt.event.ActionListener action) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(action);
        return item;
    }

    private void setInvaderImage(String source) {
        Image image = gameUI.imageSelection.loadImageFromSource(source, "/spaceinvaders/resources/Images/InvaderImage.png");
        if (image != null) {
            gameUI.imageSelection.setInvaderImage(image);
            gameUI.repaint();
        }
    }

    private void promptForCustomImage() {
        String source = JOptionPane.showInputDialog(gameUI,
                "Enter a file path or URL for the invader image:",
                "Custom Invader Image", JOptionPane.PLAIN_MESSAGE);
        if (source == null || source.trim().isEmpty()) {
            return;
        }
        source = source.trim().replace('\\', '/');
        setInvaderImage(source);
    }
}
