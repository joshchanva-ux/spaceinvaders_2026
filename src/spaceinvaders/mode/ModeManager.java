package spaceinvaders.mode;

import spaceinvaders.ui.SpaceInvadersUI;

public class ModeManager extends Thread {

    private final SpaceInvadersUI game;
    private volatile GameMode currentMode;
    private volatile boolean toggleRequested = false;
    private volatile boolean running = true;
    private static final long POLL_INTERVAL_MS = 50;

    public ModeManager(SpaceInvadersUI game) {
        this.game = game;
        this.currentMode = new CreativeMode();
        setDaemon(true);
        setName("ModeManager-Thread");
    }

    @Override
    public void run() {
        while (running) {
            if (toggleRequested) {
                synchronized (game) {
                    if (currentMode instanceof SurvivalMode) {
                        currentMode = new CreativeMode();
                    } else {
                        currentMode = new SurvivalMode();
                    }
                    toggleRequested = false;
                }
            }
            try {
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void requestToggle() {
        toggleRequested = true;
    }

    public GameMode getCurrentMode() {
        return currentMode;
    }

    public void stopThread() {
        running = false;
    }
}
