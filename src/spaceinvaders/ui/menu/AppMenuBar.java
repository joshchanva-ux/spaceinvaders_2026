package spaceinvaders.ui.menu;

import spaceinvaders.mode.ModeManager;

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
 *   - MusicMenu — track selection and music controls
 *
 * --- How to add a new top-level menu ---
 *   1. Create a new file in src/spaceinvaders/ui/menu/,
 *      e.g. ShooterMenu.java.
 *   2. Use the same pattern as MusicMenu.
 *   3. Register it in createMenuBar() below:
 *        bar.add(new ShooterMenu().getMenu());
 *
 * --- Planned menus (not yet implemented) ---
 *   - ShooterMenu.java — shooter speed, color, controls
 *   - InvaderMenu.java — invader count, speed, wave settings
 *   - BulletMenu.java  — bullet speed, size, fire rate
 *
 * --- File layout ---
 *   src/spaceinvaders/ui/menu/
 *   ├── AppMenuBar.java      (this file — composes menus into JMenuBar)
 *   ├── MusicMenu.java       (implemented)
 *   ├── ShooterMenu.java     (to be created)
 *   ├── InvaderMenu.java     (to be created)
 *   └── BulletMenu.java      (to be created)
 *
 * --- Wiring ---
 *   SpaceInvadersUI creates an AppMenuBar and exposes getAppMenuBar().
 *   Main.java attaches it to the JFrame via:
 *     frame.setJMenuBar(game.getAppMenuBar());
 */
public class AppMenuBar {

    private final JMenuBar menuBar;

    public AppMenuBar(ModeManager modeManager) {
        menuBar = createMenuBar(modeManager);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private JMenuBar createMenuBar(ModeManager modeManager) {
        JMenuBar bar = new JMenuBar();
        bar.add(new GameModeMenu(modeManager).getMenu());
        // Add new menus here, e.g.:
        // bar.add(new MusicMenu().getMenu());
        // bar.add(new ShooterMenu().getMenu());
        // bar.add(new InvaderMenu().getMenu());
        // bar.add(new BulletMenu().getMenu());
        return bar;
    }
}
