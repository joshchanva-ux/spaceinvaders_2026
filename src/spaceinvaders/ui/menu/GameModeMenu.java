package spaceinvaders.ui.menu;

import javax.swing.*;

import spaceinvaders.service.ModeManager;

public final class GameModeMenu {

    private final JMenu menu;

    public GameModeMenu(ModeManager modeManager) {
        menu = new JMenu("Mode");
        JMenuItem toggle = new JMenuItem("Toggle Creative/Survival");
        toggle.addActionListener(e -> modeManager.requestToggle());
        menu.add(toggle);
    }

    public JMenu getMenu() {
        return menu;
    }
}
