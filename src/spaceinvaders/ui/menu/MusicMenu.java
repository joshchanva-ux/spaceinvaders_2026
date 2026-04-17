package spaceinvaders.ui.menu;

import spaceinvaders.service.MusicService;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Builds the "Music" drop-down menu. All menu actions delegate to
 * {@link MusicService} — this class contains zero playback logic.
 */
public class MusicMenu {

    private static final String RESOURCE_BASE = "/spaceinvaders/resources/music/";
    private static final MusicService MUSIC_SERVICE = MusicService.getDefaultService();

    private final JMenu menu;
    private final MusicService musicService;

    public MusicMenu() {
        this.musicService = MUSIC_SERVICE;
        menu = createMenu();
    }

    public JMenu getMenu() {
        return menu;
    }

    public void startDefaultMusic() {
        musicService.playDefaultTrack();
    }

    private JMenu createMenu() {
        JMenu musicMenu = new JMenu("Music");

        musicMenu.add(createTrackItem("Dream Protocol", "dream-protocol.wav"));
        musicMenu.add(createTrackItem("Boss", "boss.wav"));
        musicMenu.add(createTrackItem("Teminite Amp", "teminite-amp.wav"));

        musicMenu.addSeparator();

        musicMenu.add(createMenuItem("Custom...", e -> promptCustomFile()));
        musicMenu.add(createMenuItem("No Music", e -> musicService.stop()));

        return musicMenu;
    }

    private JMenuItem createTrackItem(String title, String filename) {
        return createMenuItem(title, e ->
                musicService.playResource(RESOURCE_BASE + filename));
    }

    private JMenuItem createMenuItem(String title, ActionListener listener) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(listener);
        return item;
    }

    private void promptCustomFile() {
        String path = JOptionPane.showInputDialog(null,
                "Enter the path to a local music file (.wav):",
                "Custom Music",
                JOptionPane.PLAIN_MESSAGE);
        if (path != null && !path.trim().isEmpty()) {
            musicService.playFile(path.trim());
        }
    }
}
