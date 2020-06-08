import bagel.Input;
import bagel.Keys;

import java.awt.*;

public class Clock {
    private static final int BASE_MULTIPLIER = 1;
    private static final int MAX_MULTIPLIER = 5;
    // refreshRate set to 60 by default. Should dynamically adjust, if program is not working, disable the call to
    // 'determineRefreshRate()' in constructor
    public static int refreshRate = 60;

    public static int timeMultiplier = BASE_MULTIPLIER;
    private boolean timerFinished;
    private int timeCount;
    private double delay;


    public Clock() {
        // Disable this if timings are strange
        determineRefreshRate();
    }

    public void determineRefreshRate() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getScreenDevices()[0];
        int rate = device.getDisplayMode().getRefreshRate();
        if (rate != DisplayMode.REFRESH_RATE_UNKNOWN) {
            refreshRate = rate;
        }
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public boolean timerFinished() {
        return timerFinished;
    }

    public void beginCountdown() {
        timeCount = (int) (delay/1000 * refreshRate);
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
