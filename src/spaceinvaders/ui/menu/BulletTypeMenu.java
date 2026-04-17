package spaceinvaders.ui.menu;

import spaceinvaders.ui.SpaceInvadersUI;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Blueprint menu for selecting bullet type visuals.
 *
 * Current scope: UI structure only (no behavior wired yet).
 * Planned behavior:
 * - Lazer / TNT / Nuke: choose pre-defined bullet shape or image
 * - Custom: open a picker for repo file path or URL image
 */
public class BulletTypeMenu {

    private final JMenu menu;
    private final SpaceInvadersUI gameUI;

    public BulletTypeMenu(SpaceInvadersUI gameUI) {
        this.gameUI = gameUI;
        this.menu = createMenu();
    }

    public JMenu getMenu() {
        return menu;
    }

    private JMenu createMenu() {
        JMenu bulletTypeMenu = new JMenu("Bullet Type");

        JMenuItem lazerItem = new JMenuItem("Lazer");
        JMenuItem tntItem = new JMenuItem("TNT");
        JMenuItem nukeItem = new JMenuItem("Nuke");
        JMenuItem customItem = new JMenuItem("Custom");

        lazerItem.addActionListener(e -> {
            gameUI.bulletService.setLazerType();
            gameUI.repaint();
        });
        tntItem.addActionListener(e -> {
            gameUI.bulletService.setTntType();
            gameUI.repaint();
        });
        nukeItem.addActionListener(e -> {
            gameUI.bulletService.setNukeType();
            gameUI.repaint();
        });
        customItem.addActionListener(e -> {
            gameUI.bulletService.promptAndSetCustomType();
            gameUI.repaint();
        });

        bulletTypeMenu.add(lazerItem);
        bulletTypeMenu.add(tntItem);
        bulletTypeMenu.add(nukeItem);
        bulletTypeMenu.addSeparator();
        bulletTypeMenu.add(customItem);

        return bulletTypeMenu;
    }
}
