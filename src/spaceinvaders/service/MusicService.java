package spaceinvaders.service;

import spaceinvaders.exception.MusicExceptions;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Coordinates music selection and playback on a dedicated audio thread
 * ("MusicPlayer-Thread"), keeping all audio I/O off both the
 * AWT-EventQueue-0 and GameCalculator-Thread.
 *
 * UI code should only call the public methods (playResource, playFile,
 * stop, shutdown) — they are non-blocking and safe to invoke from the EDT.
 */
public class MusicService {

    private static final String DEFAULT_TRACK = "/spaceinvaders/resources/music/dream-protocol.wav";
    private static final MusicService DEFAULT_SERVICE = new MusicService();

    private final ExecutorService audioThread;
    private Clip currentClip;

    public MusicService() {
        audioThread = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "MusicPlayer-Thread");
            t.setDaemon(true);
            return t;
        });
    }

    /** Returns the shared app-level music service instance. */
    public static MusicService getDefaultService() {
        return DEFAULT_SERVICE;
    }

    /** Starts default background music via the shared service instance. */
    public static void startDefaultMusic() {
        DEFAULT_SERVICE.playDefaultTrack();
    }

    /** Starts the default background track. */
    public void playDefaultTrack() {
        playResource(DEFAULT_TRACK);
    }

    /**
     * Plays a bundled classpath resource on loop, stopping any current track.
     *
     * @param classpathResource e.g. "/spaceinvaders/resources/music/boss.wav"
     */
    public void playResource(String classpathResource) {
        audioThread.submit(() -> {
            releaseClip();
            try {
                InputStream raw = getClass().getResourceAsStream(classpathResource);
                if (raw == null) {
                    reportMusicError("Music resource not found: " + classpathResource);
                    return;
                }
                AudioInputStream stream = AudioSystem.getAudioInputStream(
                        new BufferedInputStream(raw));
                openAndLoop(stream);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                reportMusicError("Failed to play resource: " + e.getMessage());
            }
        });
    }

    /**
     * Plays a local file on loop, stopping any current track.
     *
     * @param filePath absolute or relative path to a .wav file
     */
    public void playFile(String filePath) {
        audioThread.submit(() -> {
            releaseClip();
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    reportMusicError("File not found: " + filePath);
                    return;
                }
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                openAndLoop(stream);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                reportMusicError("Failed to play file: " + e.getMessage());
            }
        });
    }

    /** Stops current playback and releases audio resources. */
    public void stop() {
        audioThread.submit(this::releaseClip);
    }

    /** Stops playback, releases resources, and shuts down the audio thread. */
    public void shutdown() {
        audioThread.submit(this::releaseClip);
        audioThread.shutdown();
    }

    private void openAndLoop(AudioInputStream stream)
            throws LineUnavailableException, IOException {
        currentClip = AudioSystem.getClip();
        currentClip.open(stream);
        currentClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void releaseClip() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }

    private void reportMusicError(String message) {
        SwingUtilities.invokeLater(() -> MusicExceptions.showErrorDialog(message));
    }
}
