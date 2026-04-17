package spaceinvaders.mode;

import spaceinvaders.ui.SpaceInvadersUI;

public final class SurvivalMode implements GameMode {

    private int hearts = 3;

    @Override
    public void onInvaderReachesShooter(SpaceInvadersUI game) {
        if (hearts > 0) {
            hearts--;
        }
        if (hearts == 0) {
            game.triggerGameOver();
        }
    }

    @Override
    public String getModeName() {
        return "Survival";
    }

    @Override
    public int getHearts() {
        return hearts;
    }

    public void resetHearts() {
        hearts = 3;
    }
}
