package spaceinvaders.mode;

import spaceinvaders.ui.SpaceInvadersUI;

public final class CreativeMode implements GameMode {

    @Override
    public void onInvaderReachesShooter(SpaceInvadersUI game) {
        // No-op: Creative mode has no damage
    }

    @Override
    public String getModeName() {
        return "Creative";
    }

    @Override
    public int getHearts() {
        return -1;
    }
}
