package spaceinvaders.ui.menu;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MusicMenu {

    private final JMenu menu;

    public MusicMenu() {
        menu = createMenu();
    }

    public JMenu getMenu() {
        return menu;
    }

    private JMenu createMenu() {
        JMenu musicMenu = new JMenu("Music");

        musicMenu.add(createMenuItem("Track 1", e ->
                System.out.println("Track 1 selected")));

        musicMenu.add(createMenuItem("Track 2", e ->
                System.out.println("Track 2 selected")));

        musicMenu.add(createMenuItem("Track 3", e ->
                System.out.println("Track 3 selected")));

        musicMenu.addSeparator();

        musicMenu.add(createMenuItem("Custom Music...", e ->
                System.out.println("Custom Music selected")));

        musicMenu.add(createMenuItem("No Music", e ->
                System.out.println("No Music selected")));

        return musicMenu;
    }

    private JMenuItem createMenuItem(String title, ActionListener listener) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(listener);
        return item;
    }
}
