package spaceinvaders.mode;

import spaceinvaders.ui.SpaceInvadersUI;

public interface GameMode {
    void onInvaderReachesShooter(SpaceInvadersUI game);
    String getModeName();
    int getHearts(); // -1 means no hearts (Creative mode)
}
