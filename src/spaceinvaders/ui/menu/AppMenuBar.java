package spaceinvaders.ui.menu;

import spaceinvaders.ui.SpaceInvadersUI;

import javax.swing.JMenuBar;

/*
 * Assembles the application menu bar from individual menu classes.
 *
 * --- Architecture ---
 * Each top-level menu lives in its own class inside this package
 * (spaceinvaders.ui.menu). AppMenuBar only composes them into a
 * single JMenuBar — it does not define menu items itself.
 *
 * --- Current menus ---
 *   - ShooterMenu — shooter image selection
 *   - InvaderMenu — invader image selection
 *
 * --- How to add a new top-level menu ---
 *   1. Create a new file in src/spaceinvaders/ui/menu/,
 *      e.g. NewMenu.java.
 *   2. Use the same pattern as ShooterMenu or InvaderMenu.
 *   3. Register it in createMenuBar() below:
 *        bar.add(new NewMenu(gameUI).getMenu());
 *
 * --- File layout ---
 *   src/spaceinvaders/ui/menu/
 *   ├── AppMenuBar.java      (this file — composes menus into JMenuBar)
 *   ├── ShooterMenu.java     (implements shooter image selection)
 *   ├── InvaderMenu.java     (implements invader image selection)
 *   └── ...
 *
 * --- Wiring ---
 *   SpaceInvadersUI creates an AppMenuBar and exposes getAppMenuBar().
 *   Main.java attaches it to the JFrame via:
 *     frame.setJMenuBar(game.getAppMenuBar());
 */
public class AppMenuBar {

    private final JMenuBar menuBar;

    public AppMenuBar(SpaceInvadersUI gameUI) {
        menuBar = createMenuBar(gameUI);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private JMenuBar createMenuBar(SpaceInvadersUI gameUI) {
        JMenuBar bar = new JMenuBar();
        bar.add(new ShooterMenu(gameUI).getMenu());
        bar.add(new InvaderMenu(gameUI).getMenu());
        bar.add(new BulletTypeMenu(gameUI).getMenu());
        return bar;
    }
}
