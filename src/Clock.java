import bagel.Input;
import bagel.Keys;

public class Clock {
    private static final int BASE_MULTIPLIER = 1;
    private static final int MAX_MULTIPLIER = 20;
    public static final int refreshRate = 144;

    public static int timeMultiplier = BASE_MULTIPLIER;
    private boolean timerFinished;
    private int timeCount;
    private int delay;

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean timerFinished() {
        return timerFinished;
    }

    public void beginCountdown() {
        timeCount = (int) ((double)delay/1000 * refreshRate);
        timerFinished = false;
    }

    public void updateMultiplier(Input input) {
        if (input.wasPressed(Keys.L) && timeMultiplier < MAX_MULTIPLIER) {
            timeMultiplier++;
        }
        else if (input.wasPressed(Keys.K) && timeMultiplier > BASE_MULTIPLIER) {
            timeMultiplier--;
        }
    }

    public void updateTimer() {
        timeCount -= timeMultiplier;
        if (timeCount <= 0) {
            timerFinished = true;
        }
    }
}
