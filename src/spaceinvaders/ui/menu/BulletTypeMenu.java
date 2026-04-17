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

    public BulletTypeMenu(SpaceInvadersUI gameUI) {
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

        bulletTypeMenu.add(lazerItem);
        bulletTypeMenu.add(tntItem);
        bulletTypeMenu.add(nukeItem);
        bulletTypeMenu.addSeparator();
        bulletTypeMenu.add(customItem);

        return bulletTypeMenu;
    }
}
